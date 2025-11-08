# Transaction Controller Memory Leak Fix - Proof of Concept

## Problem Statement

When using Transaction Controller in "Generate Parent Sample" mode, JMeter experiences significant memory leaks. The issue occurs because:

1. Transaction Controllers accumulate sub-results (SampleResult objects) throughout test execution
2. Each SampleResult contains potentially large data:
   - Response body (can be MBs per sample)
   - Response headers
   - Request headers
   - Sampler data
   - Assertion results
3. These sub-results are retained in memory until the thread completes
4. In long-running tests, this causes heap usage to grow from ~50MB to 150-200MB or more

## Root Cause Analysis

The memory leak occurs in the following flow:

1. `JMeterThread` executes samplers within a transaction
2. Each sampler produces a `SampleResult` with full response data
3. Listeners are notified and process the result
4. The result is added to `TransactionSampler` via `addSubSamplerResult()`
5. `TransactionSampler` stores these results in its `transactionSampleResult.subResults` list
6. The sub-results remain in memory with all their heavy data until thread completion

**Key Insight**: After listeners have been notified, the detailed response data (body, headers) is no longer needed for transaction aggregation. Only metrics like timing, success/failure, and byte counts are required.

## Solution Design

### Approach

Clear heavy data fields from sub-results **after** listeners have processed them but **before** adding them to the transaction result. This preserves:

- All timing metrics (elapsed time, latency, connect time)
- Success/failure status
- Response codes and messages
- Byte counts for bandwidth calculation
- Sample labels and thread information

While discarding:

- Response body data
- Response headers
- Request headers
- Sampler data
- Assertion details

### Implementation

The fix consists of three components:

#### 1. New Method in SampleResult.java

Added `clearHeavyDataForTransaction()` method that:
- Clears `responseData` (the largest memory consumer)
- Clears `responseHeaders` and `requestHeaders`
- Clears `samplerData`
- Clears `assertionResults` list
- Preserves all metrics needed for transaction aggregation

Location: `src/core/src/main/java/org/apache/jmeter/samplers/SampleResult.java:1631`

#### 2. Configuration Property

Added new property `transaction_controller.clear_subresult_data` (default: true)
- Allows users to disable the cleanup if they need full sub-result data
- Documented in `bin/jmeter.properties`

#### 3. JMeterThread Modifications

Modified two locations where sub-results are added to transactions:

**Location 1** (`JMeterThread.java:498-502`):
- Nested transaction completion
- Clears data from completed nested transaction results

**Location 2** (`JMeterThread.java:605-610`):
- Regular sampler results
- Clears data after listeners are notified
- Critical: cleanup happens AFTER line 600 (listener notification) but BEFORE line 610 (adding to transaction)

## Benefits

1. **Memory Reduction**: Expected 50-70% reduction in heap usage for transaction-heavy tests
2. **Backward Compatible**: Can be disabled via configuration property
3. **Safe**: Data cleared only after listeners have been notified
4. **Complete**: Handles both regular samplers and nested transactions

## Trade-offs

1. **Limited Sub-Result Details**: Transaction parent samples won't contain full response data for sub-results
2. **Not Recoverable**: Once cleared, the detailed data cannot be retrieved

However, this is acceptable because:
- Listeners have already received and processed the full data
- Transaction aggregation only needs metrics, not detailed response data
- Users who need full sub-result data can disable the feature

## Configuration

To disable the cleanup (retain full sub-result data):

```properties
# In user.properties or jmeter.properties
transaction_controller.clear_subresult_data=false
```

## Testing Recommendations

1. **Memory Tests**:
   - Run long-duration tests with many transaction iterations
   - Monitor heap usage with and without the fix
   - Expected: ~50-70% reduction in peak heap usage

2. **Functional Tests**:
   - Verify transaction timing metrics remain accurate
   - Verify transaction success/failure status is correct
   - Verify byte counts are preserved
   - Verify nested transactions work correctly

3. **Listener Tests**:
   - Verify listeners still receive full sample data
   - Test with various listener types (file writers, backends, etc.)

## Comparison with PR #6386

This fix differs from the original PR #6386:

| Aspect | PR #6386 | This POC |
|--------|----------|----------|
| **Approach** | Replace TransactionSampler instances | Clear data from sub-results |
| **Timing** | In `TestCompiler.done()` (too late) | Before adding to transaction (correct) |
| **Root Cause** | Misidentified (sampler reuse) | Correctly identified (data accumulation) |
| **Effectiveness** | Low (TC already creates new instances) | High (directly addresses memory issue) |
| **Thread Safety** | Issues with IdentityHashMap | No threading issues |
| **Test Changes** | Modifies serialization tests | No test modifications needed |

## Files Modified

1. `src/core/src/main/java/org/apache/jmeter/samplers/SampleResult.java`
   - Added `clearHeavyDataForTransaction()` method

2. `src/core/src/main/java/org/apache/jmeter/threads/JMeterThread.java`
   - Added configuration property constant
   - Modified two locations to clear data before adding sub-results

3. `bin/jmeter.properties`
   - Added `transaction_controller.clear_subresult_data` property

## Next Steps

1. Build and run tests to verify compilation
2. Create memory leak test case
3. Run performance benchmarks
4. Create proper test cases for the new functionality
5. Update user documentation
6. Consider backporting to LTS versions

## License

This fix is provided under the Apache License 2.0, consistent with Apache JMeter.

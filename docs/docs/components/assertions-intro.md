---
title: 18.5 Assertions
---

# 18.5 Assertions

Assertions are used to perform additional checks on samplers, and are processed after **every sampler**
    in the same scope.
    To ensure that an Assertion is applied only to a particular sampler, add it as a child of the sampler.

:::note
Note: Unless documented otherwise, Assertions are not applied to sub-samples (child samples) -
    only to the parent sample.
    In the case of JSR223 and BeanShell Assertions, the script can retrieve sub-samples using the method
    `prev.getSubResults()` which returns an array of SampleResults.
    The array will be empty if there are none.
:::

Assertions can be applied to either the main sample, the sub-samples or both.
    The default is to apply the assertion to the main sample only.
    If the Assertion supports this option, then there will be an entry on the GUI which looks like the following:

or the followingIf a sub-sampler fails and the main sample is successful,
    then the main sample will be set to failed status and an Assertion Result will be added.
    If the JMeter variable option is used, it is assumed to relate to the main sample, and
    any failure will be applied to the main sample only.

:::note
The variable `JMeterThread.last_sample_ok` is updated to
    "`true`" or "`false`" after all assertions for a sampler have been run.
:::


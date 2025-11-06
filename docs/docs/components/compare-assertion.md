---
title: Compare Assertion
sidebar_position: 12
---

# Compare Assertion

![Compare Assertion](/img/images/screenshots/assertion/compare.png)

:::note
Compare Assertion **must not be used** during load test as it consumes a lot of resources (memory and CPU). Use it only for either functional testing or
during Test Plan debugging and Validation.
:::

The Compare Assertion can be used to compare sample results within its scope.
Either the contents or the elapsed time can be compared, and the contents can be filtered before comparison.
The assertion comparisons can be seen in the[Comparison Assertion Visualizer](#comparison-assertion-visualizer).
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Compare Content | Yes | Whether or not to compare the content (response data) |
| Compare Time | Yes | If the value is ≥0, then check if the response time difference is no greater than the value.     I.e. if the value is `0`, then the response times must be exactly equal. |
| Comparison Filters | No | Filters can be used to remove strings from the content comparison.     For example, if the page has a time-stamp, it might be matched with: "`Time: \d\d:\d\d:\d\d`" and replaced with a dummy fixed time "`Time: HH:MM:SS`". |


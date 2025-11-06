---
title: 18.8 Post-Processors
---

# 18.8 Post-Processors

As the name suggests, Post-Processors are applied after samplers. Note that they are
        applied to **all** the samplers in the same scope, so to ensure that a post-processor
        is applied only to a particular sampler, add it as a child of the sampler.

:::note
Note: Unless documented otherwise, Post-Processors are not applied to sub-samples (child samples) -
    only to the parent sample.
    In the case of JSR223 and BeanShell post-processors, the script can retrieve sub-samples using the method
    `prev.getSubResults()` which returns an array of SampleResults.
    The array will be empty if there are none.
:::

Post-Processors are run before Assertions, so they do not have access to any Assertion Results, nor will
    the sample status reflect the results of any Assertions. If you require access to Assertion Results, try
    using a Listener instead. Also note that the variable `JMeterThread.last_sample_ok` is set to "`true`" or "`false`"
    after all Assertions have been run.


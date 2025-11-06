---
title: 18.6 Timers
---

# 18.6 Timers

:::note
Since version 3.1, a new feature (in Beta mode as of JMeter 3.1 and subject to changes) has been implemented which provides the following feature.  

    You can apply a multiplication factor on the sleep delays computed by Random timer by setting property `timer.factor=float number` where float number is a decimal positive number.  

    JMeter will multiply this factor by the computed sleep delay. This feature can be used by:
:::

:::note
Note that timers are processed **before** each sampler in the scope in which they are found;
    if there are several timers in the same scope, **all** the timers will be processed **before
    each** sampler.
      

    Timers are only processed in conjunction with a sampler.
    A timer which is not in the same scope as a sampler will not be processed at all.
      

    To apply a timer to a single sampler, add the timer as a child element of the sampler.
    The timer will be applied before the sampler is executed.
    To apply a timer after a sampler, either add it to the next sampler, or add it as the
    child of a [Flow Control Action](#flow-control-action) Sampler.
:::


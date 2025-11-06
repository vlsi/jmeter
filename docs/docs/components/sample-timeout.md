---
title: Sample Timeout
sidebar_position: 11
---

# Sample Timeout

![Sample Timeout](/img/images/screenshots/sample_timeout.png)

This Pre-Processor schedules a timer task to interrupt a sample if it takes too long to complete.
The timeout is ignored if it is zero or negative.
For this to work, the sampler must implement Interruptible.
The following samplers are known to do so:  

AJP, BeanShell, FTP, HTTP, Soap, AccessLog, MailReader, JMS Subscriber, TCPSampler, TestAction, JavaSampler

The test element is intended for use where individual timeouts such as Connection Timeout or Response Timeout are insufficient,
or where the Sampler does not support timeouts.
The timeout should be set sufficiently long so that it is not triggered in normal tests, but short enough that it interrupts samples
that are stuck.

[By default, JMeter uses a Callable to interrupt the sampler.
This executes in the same thread as the timer, so if the interrupt takes a long while,
it may delay the processing of subsequent timeouts.
This is not expected to be a problem, but if necessary the property `InterruptTimer.useRunnable`
can be set to `true` to use a separate Runnable thread instead of the Callable.]


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree. |
| Sample Timeout | Yes | If the sample takes longer to complete, it will be interrupted. |


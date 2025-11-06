---
title: Constant Throughput Timer
sidebar_position: 4
---

# Constant Throughput Timer

![Constant Throughput Timer](/img/images/screenshots/timers/constant_throughput_timer.png)

This timer introduces variable pauses, calculated to keep the total throughput (in terms of samples per minute) as close as possible to a given figure. Of course the throughput will be lower if the server is not capable of handling it, or if other timers or time-consuming test elements prevent it.

N.B. although the Timer is called the Constant Throughput timer, the throughput value does not need to be constant.
It can be defined in terms of a variable or function call, and the value can be changed during a test.
The value can be changed in various ways:

- using a counter variable
- using a `__jexl3`, `__groovy` function to provide a changing value
- using the remote BeanShell server to change a JMeter property

See [Best Practices](best-practices.html) for further details.

Note that the throughput value should not be changed too often during a test
- it will take a while for the new value to take effect.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree. |
| Target Throughput | Yes | Throughput we want the timer to try to generate. |
| Calculate Throughput based on | Yes | this thread only - each thread will try to maintain the target throughput. The overall throughput will be proportional to the number of active threads.     all active threads in current thread group - the target throughput is divided amongst all the active threads in the group.     Each thread will delay as needed, based on when it last ran.     all active threads - the target throughput is divided amongst all the active threads in all Thread Groups.     Each thread will delay as needed, based on when it last ran.     In this case, each other Thread Group will need a Constant Throughput timer with the same settings.     all active threads in current thread group (shared) - as above, but each thread is delayed based on when any thread in the group last ran.     all active threads (shared) - as above; each thread is delayed based on when any thread last ran. |


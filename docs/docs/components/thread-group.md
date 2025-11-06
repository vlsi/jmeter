---
title: Thread Group
sidebar_position: 2
---

# Thread Group

![Thread Group](/img/images/screenshots/threadgroup.png)

A Thread Group defines a pool of users that will execute a particular test case against your server.  In the Thread Group GUI, you can control the number of users simulated (number of threads), the ramp up time (how long it takes to start all the threads), the number of times to perform the test, and optionally, a start and stop time for the test.

See also [tearDown Thread Group](#teardown-thread-group) and [setUp Thread Group](#setup-thread-group).

When using the scheduler, JMeter runs the thread group until either the number of loops is reached or the duration/end-time is reached - whichever occurs first.
Note that the condition is only checked between samples; when the end condition is reached, that thread will stop.
JMeter does not interrupt samplers which are waiting for a response, so the end time may be delayed arbitrarily.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Action to be taken after a Sampler error | No | Determines what happens if a sampler error occurs, either because the sample itself failed or an assertion failed.         The possible choices are:                  Continue - ignore the error and continue with the test         Start Next Thread Loop - ignore the error, start next loop and continue with the test         Stop Thread - current thread exits         Stop Test - the entire test is stopped at the end of any current samples.         Stop Test Now - the entire test is stopped abruptly. Any current samplers are interrupted if possible. |
| Number of Threads | Yes | Number of users to simulate. |
| Ramp-up Period | Yes | How long JMeter should take to get all the threads started.             If there are 10 threads and a ramp-up time of 100 seconds, then each thread will begin 10 seconds after             the previous thread started, for a total time of 100 seconds to get the test fully up to speed.             The first thread will always start directly, so if you configured one thread,                 the ramp-up time is effectively zero. For the same reason, the tenth thread in                 the above example will actually be started after 90 seconds and not 100 seconds. |
| Same user on each iteration | Yes | If selected, cookie and cache data from the first sampler response are used in subsequent requests (requires a global Cookie and Cache Manager respectively).                             If not selected, cookie and cache data from the first sampler response are not used in subsequent requests.             If not selected, a new connection will be opened between iterations which will result in increased response times and consume more resources (memory and cpu). |
| Loop Count | Yes, unless Infinite is selected | Number of times to perform the test case.  Alternatively, "`infinite`" can be selected causing the test to run until manually stopped or end of the thread lifetime is reached. |
| Same user on each iteration | Yes | If selected, cookie and cache data from the first sampler response are used in subsequent requests (requires a global Cookie and Cache Manager respectively).                             If not selected, cookie and cache data from the first sampler response are not used in subsequent requests.             If not selected, a new connection will be opened between iterations which will result in increased response times and consume more resources (memory and cpu). |
| Delay Thread creation until needed | Yes | If selected, threads are created only when the appropriate proportion of the ramp-up time has elapsed.         This is most appropriate for tests with a ramp-up time that is significantly longer than the time to execute a single thread.         I.e. where earlier threads finish before later ones start.                     If not selected, all threads are created when the test starts (they then pause for the appropriate proportion of the ramp-up time).         This is the original default, and is appropriate for tests where threads are active throughout most of the test. |
| Specify Thread lifetime | Yes | If selected, confines Thread operation time to the given bounds |
| Duration (seconds) | No | If the scheduler checkbox is selected, one can choose a relative end time.             JMeter will use this to calculate the End Time. |
| Startup delay (seconds) | No | If the scheduler checkbox is selected, one can choose a relative startup delay.             JMeter will use this to calculate the Start Time. |


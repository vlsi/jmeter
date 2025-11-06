---
title: Poisson Random Timer
sidebar_position: 10
---

# Poisson Random Timer

![Poisson Random Timer](/img/images/screenshots/timers/poisson_random_timer.png)

This timer pauses each thread request for a random amount of time, with most
of the time intervals occurring near a particular value.  The total delay is the
sum of the Poisson distributed value, and the offset value.

Note: if you want to model Poisson arrivals, consider using [Precise Throughput Timer](#precise-throughput-timer) instead.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree |
| Lambda | Yes | Lambda value in milliseconds. |
| Constant Delay Offset | Yes | Number of milliseconds to pause in addition to the random delay. |


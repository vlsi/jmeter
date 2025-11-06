---
title: Gaussian Random Timer
sidebar_position: 2
---

# Gaussian Random Timer

![Gaussian Random Timer](/img/images/screenshots/timers/gauss_random_timer.png)

This timer pauses each thread request for a random amount of time, with most
of the time intervals occurring near a particular value.
The total delay is the sum of the Gaussian distributed value (with mean `0.0` and standard deviation `1.0`) times
the deviation value you specify, and the offset value.
Another way to explain it, in Gaussian Random Timer, the variation around constant offset has a Gaussian curve distribution.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree |
| Deviation | Yes | Deviation in milliseconds. |
| Constant Delay Offset | Yes | Number of milliseconds to pause in addition to the random delay. |


---
title: Uniform Random Timer
sidebar_position: 3
---

# Uniform Random Timer

![Uniform Random Timer](/img/images/screenshots/timers/uniform_random_timer.png)

This timer pauses each thread request for a random amount of time, with
each time interval having the same probability of occurring. The total delay
is the sum of the random value and the offset value.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree. |
| Random Delay Maximum | Yes | Maximum random number of milliseconds to pause. |
| Constant Delay Offset | Yes | Number of milliseconds to pause in addition to the random delay. |


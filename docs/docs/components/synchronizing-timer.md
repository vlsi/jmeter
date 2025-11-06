---
title: Synchronizing Timer
sidebar_position: 6
---

# Synchronizing Timer

![Synchronizing Timer](/img/images/screenshots/timers/sync_timer.png)

The purpose of the SyncTimer is to block threads until X number of threads have been blocked, and
then they are all released at once.  A SyncTimer can thus create large instant loads at various
points of the test plan.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this timer that is shown in the tree. |
| Number of Simultaneous Users to Group by | Yes | Number of threads to release at once. Setting it to `0` is equivalent to setting it to Number of threads in Thread Group. |
| Timeout in milliseconds | No | If set to `0`, Timer will wait for the number of threads to reach the value in "`Number of Simultaneous Users to Group`". If superior to `0`, then timer will wait at max "`Timeout in milliseconds`" for the number of Threads. If after the timeout interval the number of users waiting is not reached, timer will stop waiting. Defaults to `0` |


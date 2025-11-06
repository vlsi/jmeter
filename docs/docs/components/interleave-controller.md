---
title: Interleave Controller
sidebar_position: 4
---

# Interleave Controller

![Interleave Controller](/img/images/screenshots/logic-controller/interleave-controller.png)

If you add Generative or Logic Controllers to an Interleave Controller, JMeter will alternate among each of the
other controllers for each loop iteration.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| name | No | Descriptive name for this controller that is shown in the tree. |
| ignore sub-controller blocks | No | If checked, the interleave controller will treat sub-controllers like single request elements and only allow one request per controller at a time. |
| Interleave across threads | No | If checked, the interleave controller will alternate among each of its children controllers for each loop iteration but across all threads, for example in a         configuration with 4 threads and 3 child controllers, on first iteration         thread 1 will run first child, thread 2 second child, thread 3 third child, thread 4 first child, on next iteration each thread will run the following child controller |


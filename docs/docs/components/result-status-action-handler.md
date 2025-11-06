---
title: Result Status Action Handler
sidebar_position: 5
---

# Result Status Action Handler

![Result Status Action Handler](/img/images/screenshots/resultstatusactionhandler.png)


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Action to be taken after a Sampler error | No | Determines what happens if a sampler error occurs, either because the sample itself failed or an assertion failed.    The possible choices are:        Continue - ignore the error and continue with the test    Start next thread loop - does not execute samplers following the sampler in error for the current iteration and restarts the loop on next iteration    Stop Thread - current thread exits    Stop Test - the entire test is stopped at the end of any current samples.    Stop Test Now - the entire test is stopped abruptly. Any current samplers are interrupted if possible. |


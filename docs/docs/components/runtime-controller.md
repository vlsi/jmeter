---
title: Runtime Controller
sidebar_position: 8
---

# Runtime Controller

![Runtime Controller](/img/images/screenshots/runtimecontroller.png)

The Runtime Controller controls how long its children will run.
        Controller will run its children until configured `Runtime(s)` is exceeded.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | Yes | Descriptive name for this controller that is shown in the tree, and used to name the transaction. |
| Runtime (seconds) | Yes | Desired runtime in seconds. 0 means no run. |


---
title: Critical Section Controller
sidebar_position: 17
---

# Critical Section Controller

![Critical Section Controller](/img/images/screenshots/logic-controller/critical-section-controller.png)

The Critical Section Controller ensures that its children elements (samplers/controllers, etc.) will be executed
by only one thread as a named lock will be taken before executing children of controller.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Lock Name | Yes | Lock that will be taken by controller, ensure you use different lock names for unrelated sections |


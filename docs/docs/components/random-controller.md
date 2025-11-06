---
title: Random Controller
sidebar_position: 5
---

# Random Controller

![Random Controller](/img/images/screenshots/logic-controller/random-controller.png)

The Random Logic Controller acts similarly to the Interleave Controller, except that
instead of going in order through its sub-controllers and samplers, it picks one
at random at each pass.

:::note
Interactions between multiple controllers can yield complex behavior.
This is particularly true of the Random Controller.  Experiment before you assume
what results any given interaction will give
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| ignore sub-controller blocks | No | If checked, the interleave controller will treat sub-controllers like single request elements and only allow one request per controller at a time. |


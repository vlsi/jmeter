---
title: Throughput Controller
sidebar_position: 7
---

# Throughput Controller

![Throughput Controller](/img/images/screenshots/throughput_controller.png)

The Throughput Controller allows the user to control how often it is executed.
There are two modes:

percent execution
total executions


  Percent executionscauses the controller to execute a certain percentage of the iterations through the test plan.
  Total executionscauses the controller to stop executing after a certain number of executions have occurred.

Like the Once Only Controller, this setting is reset when a parent Loop Controller restarts.

:::note
This controller is badly named, as it does not control throughput.
Please refer to the [Constant Throughput Timer](#constant-throughput-timer) for an element that can be used to adjust the throughput.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| Execution Style | Yes | Whether the controller will run in percent executions or total executions mode. |
| Throughput | Yes | A number.  For percent execution mode, a number from `0`-`100` that indicates the percentage of times the controller will execute.  "`50`" means the controller will execute during half the iterations through the test plan.  For total execution mode, the number indicates the total number of times the controller will execute. |
| Per User | No | If checked, per user will cause the controller to calculate whether it should execute on a per user (per thread) basis.  If unchecked, then the calculation will be global for all users.  For example, if using total execution mode, and uncheck "`per user`", then the number given for throughput will be the total number of executions made.  If "`per user`" is checked, then the total number of executions would be the number of users times the number given for throughput. |


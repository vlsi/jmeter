---
title: Debug PostProcessor
sidebar_position: 8
---

# Debug PostProcessor

![Debug PostProcessor](/img/images/screenshots/debug_postprocessor.png)

The Debug PostProcessor creates a subSample with the details of the previous Sampler properties,
JMeter variables, properties and/or System Properties.

The values can be seen in the [View Results Tree](#view-results-tree) Listener Response Data pane.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| JMeter Properties | Yes | Whether to show JMeter properties (default `false`). |
| JMeter Variables | Yes | Whether to show JMeter variables (default `false`). |
| Sampler Properties | Yes | Whether to show Sampler properties (default `true`). |
| System Properties | Yes | Whether to show System properties (default `false`). |


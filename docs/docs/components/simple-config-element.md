---
title: Simple Config Element
sidebar_position: 19
---

# Simple Config Element

![Simple Config Element](/img/images/screenshots/simple_config_element.png)

The Simple Config Element lets you add or override arbitrary values in samplers.  You can choose the name of the value
and the value itself.  Although some adventurous users might find a use for this element, it's here primarily for developers as a basic
GUI that they can use while developing new JMeter components.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | Yes | Descriptive name for this element that is shown in the tree. |
| Parameter Name | Yes | The name of each parameter.  These values are internal to JMeter's workings and   are not generally documented.  Only those familiar with the code will know these values. |
| Parameter Value | Yes | The value to apply to that parameter. |


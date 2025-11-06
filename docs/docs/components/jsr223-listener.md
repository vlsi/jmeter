---
title: JSR223 Listener
sidebar_position: 1
---

# JSR223 Listener

The JSR223 Listener allows JSR223 script code to be applied to sample results.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Language | Yes | The JSR223 language to be used |
| Parameters | No | Parameters to pass to the script.     The parameters are stored in the following variables:              Parametersstring containing the parameters as a single variable         argsString array containing parameters, split on white-space |
| Script file | No | A file containing the script to run, if a relative file path is used, then it will be relative to directory referenced by "`user.dir`" System property |
| Script compilation caching | No | Unique String across Test Plan that JMeter will use to cache result of Script compilation if language used supports `Compilable` interface (Groovy is one of these, java, beanshell and javascript are not).     See note in JSR223 Sampler Java System property if you're using Groovy without checking this option |
| Script | Yes (unless script file is provided) | The script to run. |


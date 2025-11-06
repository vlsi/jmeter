---
title: RegEx User Parameters
sidebar_position: 10
---

# RegEx User Parameters

![RegEx User Parameters](/img/images/screenshots/regex_user_params.png)

Allows to specify dynamic values for HTTP parameters extracted from another HTTP Request using regular expressions.
            RegEx User Parameters are specific to individual threads.

This component allows you to specify reference name of a regular expression that extracts names and values of HTTP request parameters.
            Regular expression group numbers must be specified for parameter's name and also for parameter's value.
            Replacement will only occur for parameters in the Sampler that uses this RegEx User Parameters which name matches


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Regular Expression Reference Name | Yes | Name of a reference to a regular expression |
| Parameter names regexp group number | Yes | Group number of regular expression used to extract parameter names |
| Parameter values regex group number | Yes | Group number of regular expression used to extract parameter values |


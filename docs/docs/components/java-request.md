---
title: Java Request
sidebar_position: 4
---

# Java Request

![Java Request](/img/images/screenshots/java_request.png)

This sampler lets you control a java class that implements the
`org.apache.jmeter.protocol.java.sampler.JavaSamplerClient` interface.
By writing your own implementation of this interface,
you can use JMeter to harness multiple threads, input parameter control, and
data collection.

The pull-down menu provides the list of all such implementations found by
JMeter in its classpath.  The parameters can then be specified in the
table below - as defined by your implementation.  Two simple examples (`JavaTest` and `SleepTest`) are provided.

The `JavaTest` example sampler can be useful for checking test plans, because it allows one to set
values in almost all the fields. These can then be used by Assertions, etc.
The fields allow variables to be used, so the values of these can readily be seen.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler          that is shown in the tree. |
| Classname | Yes | The specific implementation of         the JavaSamplerClient interface to be sampled. |
| Send Parameters with Request | No | A list of         arguments that will be passed to the sampled class.  All arguments         are sent as Strings. See below for specific settings. |


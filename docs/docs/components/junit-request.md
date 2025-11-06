---
title: JUnit Request
sidebar_position: 16
---

# JUnit Request

![JUnit Request](/img/images/screenshots/junit_sampler.png)

`oneTimeSetUp`and`oneTimeTearDown`. The sampler works like the[Java Request](#java-request)with some differences.- rather than use JMeter's test interface, it scans the jar files for classes extending JUnit's `TestCase` class. That includes any class or subclass.
- JUnit test jar files should be placed in `jmeter/lib/junit` instead of `/lib` directory.
You can also use the "`user.classpath`" property to specify where to look for `TestCase` classes.
- JUnit sampler does not use name/value pairs for configuration like the [Java Request](#java-request). The sampler assumes `setUp` and `tearDown` will configure the test correctly.
- The sampler measures the elapsed time only for the test method and does not include `setUp` and `tearDown`.
- Each time the test method is called, JMeter will pass the result to the listeners.
- Support for `oneTimeSetUp` and `oneTimeTearDown` is done as a method. Since JMeter is multi-threaded, we cannot call `oneTimeSetUp`/`oneTimeTearDown` the same way Maven does it.
- The sampler reports unexpected exceptions as errors.
There are some important differences between standard JUnit test runners and JMeter's
implementation. Rather than make a new instance of the class for each test, JMeter
creates 1 instance per sampler and reuses it.
This can be changed with checkbox "`Create a new instance per sample`".

The current implementation of the sampler will try to create an instance using the string constructor first. If the test class does not declare a string constructor, the sampler will look for an empty constructor. Example below:Empty Constructor:```

public class myTestCase {
  public myTestCase() {}
}

```

String Constructor:```

public class myTestCase {
  public myTestCase(String text) {
    super(text);
  }
}

```

By default, JMeter will provide some default values for the success/failure code and message. Users should define a set of unique success and failure codes and use them uniformly across all tests.:::note
General Guidelines
If you use `setUp` and `tearDown`, make sure the methods are declared public. If you do not, the test may not run properly.
  

Here are some general guidelines for writing JUnit tests so they work well with JMeter. Since JMeter runs multi-threaded, it is important to keep certain things in mind.

Write the setUp and tearDown methods so they are thread safe. This generally means avoid using static members.
Make the test methods discrete units of work and not long sequences of actions. By keeping the test method to a discrete operation, it makes it easier to combine test methods to create new test plans.
Avoid making test methods depend on each other. Since JMeter allows arbitrary sequencing of test methods, the runtime behavior is different than the default JUnit behavior.
If a test method is configurable, be careful about where the properties are stored. Reading the properties from the Jar file is recommended.
Each sampler creates an instance of the test class, so write your test so the setup happens in oneTimeSetUp and oneTimeTearDown.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Search for JUnit4 annotations | Yes | Select this to search for JUnit4 tests (`@Test` annotations) |
| Package filter |  | Comma separated list of packages to show. Example, `org.apache.jmeter`,`junit.framework`. |
| Class name | Yes | Fully qualified name of the JUnit test class. |
| Constructor string |  | String pass to the string constructor. If a string is set, the sampler will use the    string constructor instead of the empty constructor. |
| Test method | Yes | The method to test. |
| Success message |  | A descriptive message indicating what success means. |
| Success code |  | An unique code indicating the test was successful. |
| Failure message |  | A descriptive message indicating what failure means. |
| Failure code |  | An unique code indicating the test failed. |
| Error message |  | A description for errors. |
| Error code |  | Some code for errors. Does not need to be unique. |
| Do not call setUp and tearDown | Yes | Set the sampler not to call `setUp` and `tearDown`.    By default, `setUp` and `tearDown` should be called. Not calling those methods could affect the test and make it inaccurate.     This option should only be used with calling `oneTimeSetUp` and `oneTimeTearDown`. If the selected method is `oneTimeSetUp` or `oneTimeTearDown`,      this option should be checked. |
| Append assertion errors | Yes | Whether or not to append assertion errors to the response message. |
| Append runtime exceptions | Yes | Whether or not to append runtime exceptions to the response message. Only applies if "`Append assertion errors`" is not selected. |
| Create a new Instance per sample | Yes | Whether or not to create a new JUnit instance for each sample. Defaults to false, meaning JUnit `TestCase` is created one and reused. |


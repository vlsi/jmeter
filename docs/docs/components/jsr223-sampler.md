---
title: JSR223 Sampler
sidebar_position: 1
---

# JSR223 Sampler

![JSR223 Sampler](/img/images/screenshots/jsr223-sampler.png)

The JSR223 Sampler allows JSR223 script code to be used to perform a sample or some computation required to create/update variables.

If you don't want to generate a SampleResult when this sampler is run, call the following method:
SampleResult.setIgnore();
This call will have the following impact:

    SampleResult will not be delivered to SampleListeners like View Results Tree, Summariser ...
    SampleResult will not be evaluated in Assertions nor PostProcessors
    SampleResult will be evaluated to computing last sample status (${JMeterThread.last_sample_ok}),
    and ThreadGroup "Action to be taken after a Sampler error" (since JMeter 5.4)

The JSR223 test elements have a feature (compilation) that can significantly increase performance.
To benefit from this feature:

- Use Script files instead of inlining them. This will make JMeter compile them if this feature is available on ScriptEngine and cache them.
- Or Use Script Text and check `Cache compiled script if available` property.
    When using this feature, ensure your script code does not use JMeter variables or JMeter function calls directly in script code as caching would only cache first replacement. Instead use script parameters.
    To benefit from caching and compilation, the language engine used for scripting must implement JSR223 Compilable interface (Groovy is one of these, java, beanshell and javascript are not)
    When using Groovy as scripting language and not checking Cache compiled script if available (while caching is recommended), you should set this JVM Property -Dgroovy.use.classvalue=true
        due to a Groovy Memory leak as of version 2.4.6, see:
        
            GROOVY-7683
            GROOVY-7591
            JDK-8136353

Cache size is controlled by the following JMeter property (`jmeter.properties`):```
jsr223.compiled_scripts_cache_size=100
```

:::note
Unlike the [BeanShell Sampler](#beanshell-sampler), the interpreter is not saved between invocations.
:::

:::note
JSR223 Test Elements using Script file or Script text + checked `Cache compiled script if available` are now compiled if ScriptEngine supports this feature, this enables great performance enhancements.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree. |
| Scripting Language | Yes | Name of the JSR223 scripting language to be used.       There are other languages supported than those that appear in the drop-down list.         Others may be available if the appropriate jar is installed in the JMeter lib directory.         Notice that some languages such as Velocity may use a different syntax for JSR223 variables,         e.g. $log.debug("Hello " + $vars.get("a")); for Velocity. |
| Script File | No | Name of a file to be used as a JSR223 script, if a relative file path is used, then it will be relative to directory referenced by "`user.dir`" System property |
| Parameters | No | List of parameters to be passed to the script file or the script. |
| Cache compiled script if available | No | If checked (advised) and the language used supports `Compilable` interface (Groovy is one of these, java, beanshell and javascript are not), JMeter will compile the Script and cache it using its MD5 hash as unique cache key |
| Script | Yes (unless script file is provided) | Script to be passed to JSR223 language |


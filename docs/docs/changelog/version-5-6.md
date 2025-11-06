---
title: Version 5.6
---

# Version 5.6

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

### Improvements

### Thread Groups

- [Issue #5682](https://github.com/apache/jmeter/issues/5682)[PR #717](https://github.com/apache/jmeter/pull/717) Open Model Thread Group: avoid skipping rows from CSV Data Set Config

  - Support custom thread group implementations in "Add think time" and "Save as test fragment" actions

  - Open Model Thread Group: interrupt pending HTTP requests and other `Interruptible` test elements on test stop

### HTTP Samplers and Test Script Recorder

  - [PR #5911](https://github.com/apache/jmeter/pull/5911) Use Caffeine for caching HTTP headers instead of commons-collections4 LRUMap

  - [PR #5947](https://github.com/apache/jmeter/pull/5947) Fetch resources referenced in `&lt;link "rel"="preload"...&gt;` elements

  - [PR #5869](https://github.com/apache/jmeter/pull/5869) Allow more templates to format sampler names in the recorder: `#{url}`, `#{method}`, `#{scheme}`, `#{host}`, `#{port}`

### Other samplers

  - [PR #5909](https://github.com/apache/jmeter/pull/5909) Use Caffeine for caching compiled scripts in JSR223 samplers instead of commons-collections4 LRUMap

### General

  - [PR #5792](https://github.com/apache/jmeter/pull/5792)Add KeyStroke for start_no_timers (Start no pauses: CRTL+SHIFT+n)

  - [PR #5899](https://github.com/apache/jmeter/pull/5899)Speed up CPU-bound tests by skipping `recoverRunningVersion` for elements that are shared between threads (the ones that implement `NoThreadClone`)

  - [PR #5914](https://github.com/apache/jmeter/pull/5914)Use `Locale.ROOT` instead of default locale for `toUpperCase`, and `toLowerCase` to avoid surprises with dotless I in `tr_TR` locale

  - [PR #5885](https://github.com/apache/jmeter/pull/5885)Use Java's `ServiceLoader` for loading plugins instead of classpath scanning. It enables faster startup

  - [PR #5788](https://github.com/apache/jmeter/pull/5788)`FunctionProperty` no longer caches the value.
    Previously it cached the values based on iteration number only which triggered wrong results on concurrent executions.
    The previous behavior can be temporary restored with `function.cache.per.iteration` property.
  

  - [PR #5920](https://github.com/apache/jmeter/pull/5920)Improve HTTP HeaderManager performance when it contains many headers: skip reinitialization on each iteration

  - [PR #5920](https://github.com/apache/jmeter/pull/5920)Use AtomicInteger and AtomicLong instead of synchronized primitives for JMeterContextService#numberOfThreads

  - [PR #5920](https://github.com/apache/jmeter/pull/5920)Cache bean properties in `TestBeanHelper` and avoid synchronization, so test plans with `TestBean`-based elements is faster

  - [PR #5920](https://github.com/apache/jmeter/pull/5920)Improve computation when many threads actively produce samplers by using `LongAdder` and similar concurrency classes to avoid synchronization in `Calculator`

  - [PR #5920](https://github.com/apache/jmeter/pull/5920)Reduce synchronization contention on `AbstractTestElement` that are shared between threads (the ones that implement `NoThreadClone`)

  - [PR #5934](https://github.com/apache/jmeter/pull/5934)Added caching for date formatters for `__time` function

  - [PR #710](https://github.com/apache/jmeter/pull/710)[Issue #5666](https://github.com/apache/jmeter/issues/5666)Added Shortcut key event for Reset search: `ctrl + alt + F`, `cmd + alt + F`

  - [PR #5959](https://github.com/apache/jmeter/pull/5959)`TestElement` has been migrated to Kotlin, so nullable types are annotated better

  - [PR #5944](https://github.com/apache/jmeter/pull/5944)Add PI for declaring `TestElement` schemas so element properties are easier to access in code (see `TestElementSchema`, `TestElement#getSchema()`, `TestElement#getProps()`)

  - [PR #5944](https://github.com/apache/jmeter/pull/5944)Enable usage of `${...}` expressions for checkbox controls (see context menus for checkboxes, however, the individual components should be adapted individually)

  - [PR #678](https://github.com/apache/jmeter/pull/678)Experimental Kotlin and Java DSL for programmatic test plan generation (see [Creating a plan with Kotlin DSL](usermanual/build-programmatic-test-plan.html#treebuilder_kotlin_dsl))

### Non-functional changes

  - [PR #725](https://github.com/apache/jmeter/pull/725)Add Chinese Simplified Translation for Open Model Thread Group

  - [PR #5710](https://github.com/apache/jmeter/pull/5710)Add GitHub Issue templates

  - [PR #5910](https://github.com/apache/jmeter/pull/5910)Use Caffeine for caching customizers in TestBeanGUI instead of commons-collections4 LRUMap

  - [PR #5713](https://github.com/apache/jmeter/pull/5713)[PR #5931](https://github.com/apache/jmeter/pull/5931)Update Spock to 2.3-groovy-3.0 (from 2.1-groovy-3.0)

  - [Issue #5718](https://github.com/apache/jmeter/issues/5718)Update Apache commons-text to 1.10.0 (from 1.9)

  - [PR #5731](https://github.com/apache/jmeter/pull/5731)Update docs for `changeCase` function. `UPPER` is the default

  - [PR #5924](https://github.com/apache/jmeter/pull/5924)Update Apache commons-io to 2.12.0 (from 2.11.0)

  - [PR #5921](https://github.com/apache/jmeter/pull/5921)Update Jackson Core to 2.15.1 (from 2.13.3)

  - [PR #5921](https://github.com/apache/jmeter/pull/5921)Update Jackson Databind to 2.15.1 (from 2.13.3)

  - [PR #5725](https://github.com/apache/jmeter/pull/5725)Update Tika Parser to 1.28.5 (from 1.28.3)

  - [PR #5725](https://github.com/apache/jmeter/pull/5725)Update JSoup to 1.16.1 (from 1.15.1)

  - [PR #5725](https://github.com/apache/jmeter/pull/5725)Update Apache commons-net to 3.9.0 (from 3.8.0)

  - [PR #5725](https://github.com/apache/jmeter/pull/5725)Update XStream to 1.4.20 (from 1.4.19)

  - [PR #5763](https://github.com/apache/jmeter/pull/5763)[PR #5814](https://github.com/apache/jmeter/pull/5814)Updated Gradle to 8.1.1 (from 7.2)

  - [PR #5854](https://github.com/apache/jmeter/pull/5854)Added Apache Httpclient5 5.1.3

  - [PR #5833](https://github.com/apache/jmeter/pull/5833)Update Apache Freemarker to 2.3.32 (from 2.3.31)

  - [PR #5830](https://github.com/apache/jmeter/pull/5830)Update Apache Groovy to 3.0.17 (from 3.0.11)

  - [PR #5862](https://github.com/apache/jmeter/pull/5862)Update Apache Httpclient to 4.5.14 (from 4.5.13)

  - [PR #5880](https://github.com/apache/jmeter/pull/5880)Update Apache Xalan to 2.7.3 (from 2.7.2)

  - [PR #5854](https://github.com/apache/jmeter/pull/5854)Update Saxon-HE to 11.5 (from 11.5)

  - [PR #5840](https://github.com/apache/jmeter/pull/5840)[PR #5930](https://github.com/apache/jmeter/pull/5930)Update accessors-smart to 2.4.11 (from 2.4.8)

  - [PR #5837](https://github.com/apache/jmeter/pull/5837)Update asm to 9.5 (from 9.3)

  - [PR #5840](https://github.com/apache/jmeter/pull/5840)[PR #5930](https://github.com/apache/jmeter/pull/5930)Update json-smart to 2.4.11 (from 2.4.8)

  - [PR #5814](https://github.com/apache/jmeter/pull/5814)Update kotlin-stdlib to 1.8.21 (from 1.6.21)

  - [PR #5889](https://github.com/apache/jmeter/pull/5889)[PR #5918](https://github.com/apache/jmeter/pull/5918)[PR #5814](https://github.com/apache/jmeter/pull/5814)Update kotlinx-coroutines-core to 1.8.21 (from 1.6.21)

  - [PR #5889](https://github.com/apache/jmeter/pull/5889)[PR #5918](https://github.com/apache/jmeter/pull/5918)[PR #5814](https://github.com/apache/jmeter/pull/5814)Update kotlinx-coroutines-swing to 1.8.21 (from 1.6.21)

  - [PR #5907](https://github.com/apache/jmeter/pull/5907)Update lets-plot-batik to 3.2.0 (from 2.1.1)

  - [PR #5907](https://github.com/apache/jmeter/pull/5907)Update lets-plot-jvm to 4.3.0 (from 3.1.1)

  - [PR #5859](https://github.com/apache/jmeter/pull/5859)Update log4j-1.2-api to 2.20.0 (from 2.17.2)

  - [PR #5859](https://github.com/apache/jmeter/pull/5859)Update log4j-api to 2.20.0 (from 2.17.2)

  - [PR #5859](https://github.com/apache/jmeter/pull/5859)Update log4j-core to 2.20.0 (from 2.17.2)

  - [PR #5859](https://github.com/apache/jmeter/pull/5859)Update log4j-slf4j-impl to 2.20.0 (from 2.17.2)

  - [PR #5861](https://github.com/apache/jmeter/pull/5861)Update neo4j-java-driver to 4.4.11 (from 4.4.6)

  - [PR #5853](https://github.com/apache/jmeter/pull/5853)Update org.jetbrains:annotations to 24.0.1 (from 23.0.0)

  - [PR #5868](https://github.com/apache/jmeter/pull/5868)[PR #5886](https://github.com/apache/jmeter/pull/5886)Update ph-commons to 10.2.4 (from 10.1.6)

  - [PR #5861](https://github.com/apache/jmeter/pull/5861)Update reactive-streams to 1.0.4 (from 1.0.3)

  - [PR #5847](https://github.com/apache/jmeter/pull/5847)Update rsyntaxtextarea to 3.3.3 (from 3.2.0)

  - [PR #5839](https://github.com/apache/jmeter/pull/5839)Update svgSalamander to 1.1.4 (from 1.1.2.4)

  - [PR #5852](https://github.com/apache/jmeter/pull/5852)Update xmlgraphics-commons to 2.8 (from 2.7)

  - [PR #5854](https://github.com/apache/jmeter/pull/5854)Update xmlresolver to 4.6.4 (from 4.2.0)

  - [PR #693](https://github.com/apache/jmeter/pull/693)Added randomized test GitHub Actions matrix for better coverage of locales and time zones

  - [PR #5960](https://github.com/apache/jmeter/pull/5960)Add OpenJDK JMH for creating microbenchmarks in JMeter code

  - [Issue #5961](https://github.com/apache/jmeter/issues/5961)Deprecate TestElement.threadName as it is not related to TestElement

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

  - [PR #5901](https://github.com/apache/jmeter/pull/5901)Fix NumberFormatException when counter is empty or not a digit on Proxy Settings panel

  - [PR #5987](https://github.com/apache/jmeter/pull/5987)[Issue #4546](https://github.com/apache/jmeter/issues/4546)Encode unicode characters in filenames when sending files in HTTP Sampler

### Other Samplers

  - [PR #5736](https://github.com/apache/jmeter/pull/5736)[Issue #5733](https://github.com/apache/jmeter/issues/5733)Allow `SampleResult#setEndTime` be set in `JSR223Sampler`

### Listeners

  - [Issue #5740](https://github.com/apache/jmeter/issues/5740)[PR #5741](https://github.com/apache/jmeter/pull/5741)Fix Aggregated Graph component to cope with empty names of samplers

  - [Issue #5807](https://github.com/apache/jmeter/issues/5807)Fix an `ArrayIndexOutOfBoundsException` on HTTP parameters line on special case when key and value are empty,
    i.e.: "`k1=v1&amp;&#61;&amp;k2=v2`"

  - [Issue #5654](https://github.com/apache/jmeter/issues/5654)[PR #5785](https://github.com/apache/jmeter/pull/5785) Fix `InfluxDBRawBackendListenerClient` missing data. Allow InfluxDB to insert multiple entries with the
    same `timestamp` but with different `threadName`. Contributed by Victor Peralta (vperaltac at github)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

  - [PR #5717](https://github.com/apache/jmeter/pull/5717)Add jsonpath string to JSON Path Assertion error message so the error is easier to understand

  - [PR #723](https://github.com/apache/jmeter/pull/723)Use correct number format on JSON Path Assertion. Contributed by andreaslind01 (andreaslind01 at gmail.com)

### Report / Dashboard

  - 66140Guess the delimiter of the CSV source, when configured one seems wrong.
    This is in line with the behaviour of CSVSaveService.

### Documentation

  - [Issue #5694](https://github.com/apache/jmeter/issues/5694)Document changed formatter for . A warning will be logged,
  if the code `u` is found in the format string, as the meaning for that code has changed from *day-of-week*
  to *year*.

### General

  - 66157[PR #719](https://github.com/apache/jmeter/pull/719)Correct theme for darklaf on rsyntaxtextarea

  - [Issue #5872](https://github.com/apache/jmeter/issues/5872)[PR #5874](https://github.com/apache/jmeter/pull/5874)Trim name in Argument objects.

  - [PR #693](https://github.com/apache/jmeter/pull/693)Avoid wrong results when `Object.hashCode()` happen to collide. Use `IdentityHashMap` instead of `HashMap` when key is `TestElement`

  - Refresh UI when dragging JMeter window from one monitor to another, so rich syntax text areas are properly editable after window movement

  - [PR #5984](https://github.com/apache/jmeter/pull/5984)`AbstractTestElement#clone` might produce non-identical clones if element constructor adds a non-default property value

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Alex Schwartz, [@alexsch01](https://github.com/alexsch01)

  - Andreas Lind, [@andreaslind01](https://github.com/andreaslind01)

  - Arnout Engelen, [@raboof](https://github.com/raboof)

  - Clay Johnson, [@clayburn](https://github.com/clayburn)

  - David Getzlaff, [@dgetzlaf](https://github.com/dgetzlaf)

  - Kai Lehmann, [@lehmannk](https://github.com/lehmannk)

  - kaola89, [@kaola89](https://github.com/kaola89)

  - Matt Tansley, [@matthewt-assurity](https://github.com/matthewt-assurity)

  - Mohamed Ibrahim, [@rollno748](https://github.com/rollno748)

  - Ori Marko, [@orimarko](https://github.com/orimarko)

  - PJ Fanning, [@pjfanning](https://github.com/pjfanning)

  - Sandra Thieme, [@sandra-thieme](https://github.com/sandra-thieme)

  - Stefan Seide, [@sseide](https://github.com/sseide)

  - Victor Peralta, [@vperaltac](https://github.com/vperaltac)

  - Vincent DABURON, [@vdaburon](https://github.com/vdaburon)

We also thank bug reporters who helped us improve JMeter.

Apologies if we have omitted anyone else.

 

### Known problems and workarounds

  - [Issue #6008](https://github.com/apache/jmeter/issues/6008) ThreadGroups are running endlessly in non-gui mode (fixed in 5.6.1, see [PR #6011](https://github.com/apache/jmeter/pull/6011))

  - [Issue #6043](https://github.com/apache/jmeter/issues/6043) `Min` is always `0` in `Summary Report` (fixed in 5.6.3)

  - [PR #5987](https://github.com/apache/jmeter/pull/5987)HTTP sampler sends filenames with percent-encoded UTF-8, however it is not aligned with the browsers. The workaround is to refrain non-ASCII filenames

  - [Issue #6004](https://github.com/apache/jmeter/issues/6004)Java Request sampler cannot be enabled again after disabling in UI (fixed in 5.6.1, [PR #6012](https://github.com/apache/jmeter/pull/6012))

- The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

- 
The numbers that appear to the left of the green box are the number of active threads / total number of threads,
the total number of threads only applies to a locally run test, otherwise it will show `0` (see [Bug 55510](https://bz.apache.org/bugzilla/show_bug.cgi?id=55510)).

- 
Note that under some windows systems you may have this WARNING:
```

java.util.prefs.WindowsPreferences
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0
x80000002. Windows RegCreateKeyEx(&hellip;) returned error code 5.

```

The fix is to run JMeter as Administrator, it will create the registry key for you, then you can restart JMeter as a normal user and you won't have the warning anymore.

- 
You may encounter the following error:
```
java.security.cert.CertificateException: Certificates does not conform to algorithm constraints
```

 if you run a HTTPS request on a web site with a SSL certificate (itself or one of SSL certificates in its chain of trust) with a signature
 algorithm using MD2 (like `md2WithRSAEncryption`) or with a SSL certificate with a size lower than 1024 bits.
This error is related to increased security in Java 8+.

To allow you to perform your HTTPS request, you can downgrade the security of your Java installation by editing
the Java `jdk.certpath.disabledAlgorithms` property. Remove the MD2 value or the constraint on size, depending on your case.

This property is in this file:
```
JAVA_HOME/jre/lib/security/java.security
```

See  [Bug 56357](https://bz.apache.org/bugzilla/show_bug.cgi?id=56357) for details.

- 
Under Mac OSX Aggregate Graph will show wrong values due to mirroring effect on numbers.
This is due to a known Java bug, see Bug JDK-8065373
The fix is to use JDK8_u45 or later.

- 
View Results Tree may fail to display some HTML code under HTML renderer, see [Bug 54586](https://bz.apache.org/bugzilla/show_bug.cgi?id=54586).
This is due to a known Java bug which fails to parse "`px`" units in row/col attributes.
See Bug JDK-8031109
The fix is to use JDK9 b65 or later.

- 
JTable selection with keyboard (SHIFTup/down) is totally unusable with Java 7 on Mac OSX.
This is due to a known Java bug JDK-8025126
The fix is to use JDK 8 b132 or later.

- 
Since Java 11 the JavaScript implementation [Nashorn has been deprecated](https://openjdk.java.net/jeps/335).
Java will emit the following deprecation warnings, if you are using JavaScript based on Nashorn.
```

Warning: Nashorn engine is planned to be removed from a future JDK release

```

To silence these warnings, add `-Dnashorn.args=--no-deprecation-warning` to your Java arguments.
That can be achieved by setting the enviroment variable `JVM_ARGS`
```

export JVM_ARGS="-Dnashorn.args=--no-deprecation-warning"

```

- 
With Java 15 the JavaScript implementation [Nashorn has been removed](https://openjdk.java.net/jeps/372). To add back a JSR-223 compatible JavaScript engine you have two options:
  
    Use Mozilla Rhino
    Copy [rhino-engine-1.7.14.jar](https://github.com/mozilla/rhino/releases/download/Rhino1_7_14_Release/rhino-engine-1.7.14.jar) into `$JMETER_HOME/lib/ext`.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 (or later) and its dependencies and set the module path is outlined below:
      ```

mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.3/nashorn-core-15.3.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.5/asm-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.5/asm-commons-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.5/asm-util-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.5/asm-tree-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.5/asm-analysis-9.5.jar
popd
export JVM_ARGS="--module-path $PWD/lib/modules"
./bin/jmeter
      
```

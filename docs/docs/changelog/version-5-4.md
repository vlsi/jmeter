---
title: Version 5.4
---

# Version 5.4

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

Core improvements
Test Plan
Scripting / Debugging enhancements
Functions
-->
UX improvements

6217964658The splash screen is now application-modal rather than system-modal, so it does not block other
applications when JMeter is starting up.

### Incompatible changes

    - Remove LogKit logger functionality from some classes. This was intended to completely remove
      `LoggingManager` class (it has been deprecated since JMeter 3.2), but as jmeter-plugins
      depended on it, `LoggingManager` and our `LogKit`-adapter will remain for
      this version (but is still deprecated).

### Improvements

### HTTP Samplers and Test Script Recorder

    - 5384863527Implement a new setting to allow the exclusion of embedded URLs

    - 64696[PR #571](https://github.com/apache/jmeter/pull/571)[PR #595](https://github.com/apache/jmeter/pull/595)Freestyle format for names in (Default)SamplerCreater. Based on a patch by Vincent Daburon (vdaburon at gmail.com)

    - 64752Add GraphQL/HTTP Request Sampler. Contributed by woonsan.

### Other samplers

    - 64555Set JMSType header field through JMSProperties. Contributed by Daniel van den Ouden

### Controllers

### Listeners

    - [PR #544](https://github.com/apache/jmeter/pull/544)Add BackendListener that sends "raw" results to InfluxDB. Contributed by Graham Russell
      (graham at ham1.co.uk)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

### Functions

### I18N

### Report / Dashboard

  - 64824Dashboard/HTML Report: Rename `KO` to `FAIL`

  - 64936Increase generate_report_ui.generation_timeout to 5 minutes to handle large performance test

### General

  - 64446Better parse curl commands with backslash at line endings and support `PUT` method with data arguments

  - [PR #599](https://github.com/apache/jmeter/pull/599)Ensure all buttons added to the toolbar behave/look consistently. Contributed by Jannis Weis

  - 64581Allow `SampleResult#setIgnore` to influence behaviour on Sampler Error

  - 64680Fall back to `JMETER_HOME` on startup to detect JMeter's installation directory

  - 64787[PR #630](https://github.com/apache/jmeter/pull/630)Add Korean translation. Contributed by Woonsan Ko (woonsan at apache.org)

  - 64776Add the ability to install additional SecurityProvider. Contributed by Timo (ASF.Software.Timo at Leefers.eu)

### Non-functional changes

    - Build system upgraded from Gradle to 6.7 (from 6.6)

    - [PR #594](https://github.com/apache/jmeter/pull/594)Updated neo4j-java-driver to 4.2.0 (from 1.7.5)

    - 64454More precise error message, when no datasource value can be found in JDBC sampler

    - 64440Log exeptions reported via `JMeterUtils#reportToUser` even when in GUI mode

    - [PR #591](https://github.com/apache/jmeter/pull/591)Remove deprecated sudo flag from travis file. Deng Liming (liming.d.pro at gmail.com)

    - Updated Darklaf to 2.4.10 (from 2.1.1)

    - Updated Groovy to 3.0.5 (from 3.0.3)

    - [PR #596](https://github.com/apache/jmeter/pull/596)Use neutral words in documentation

    - 63809[PR #557](https://github.com/apache/jmeter/pull/557)Updated commons-collections to 4.4 (from 3.2.2) while keeping the jars for the old commons-collections 3.x for compatibility

    - [PR #598](https://github.com/apache/jmeter/pull/598)Add another option for creating diffs to the building page. Contributed by jmetertea (github.com/jmetertea)

    - [PR #609](https://github.com/apache/jmeter/pull/609)Make use of newer API for darklaf installation. Jannis Weis

    - [PR #612](https://github.com/apache/jmeter/pull/612)Correct typos in `README.md`. Based on patches by Pooja Chandak (poojachandak002 at gmail.com)

    - [PR #613](https://github.com/apache/jmeter/pull/613)Add documentation for Darklaf properties. Jannis Weis

    - Update SpotBugs to 4.1.2 (from 4.1.1), upgrade spotbugs-gradle-plugin to 4.5.0 (from 2.0.0)

    - Update org.sonarqube Gradle plugin to 3.0 (from 2.7.1)

    - Update Apache ActiveMQ to 5.16.0 (from 5.15.11)

    - Update Bouncycastle to 1.66 (from 1.64)

    - Update Apache commons-io to 2.7 (from 2.6)

    - Update Apache commons-lang3 to 3.11 (from 3.10)

    - Update Apache commons-net to 3.7 (from 3.6)

    - Update Apache commons-pool2 to 2.9.0 (from 2.8.0)

    - Update Apache commons-text to 1.9 (from 1.8)

    - Update equalsverifier to 3.4.2 (from 3.1.13)

    - Update junit5 to 5.6.2 (from 5.6.0)

    - Update Apache log4j2 to 2.13.3 (from 2.13.1)

    - Update rsyntaxtextarea to 3.1.1 (from 3.1.0)

    - Update JUnit5 to 5.7.0 (from 5.6.2)

    - Update Rhino to 1.7.13 (from 1.7.12)

    - Update XStream to 1.4.14 (from 1.4.14.1)

    - Update Apache commons-dbcp2 to 2.8.0 (from 2.7.0)

    - [PR #635](https://github.com/apache/jmeter/pull/635)Correct some image ratios in the documentation. Patch provided
      by Vincent Daburon (vdaburon at gmail.com)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

  - 64479Regression: HTTP(s) Script Recorder prevents proper shutdown in non-GUI mode

  - 64653Exclude Javascript and JSON from parsing for charsets from forms by proxy

### Other Samplers

### Controllers

    - 64795Generate summary report may not output a summary line in the configured interval (`summariser.interval`): Clarify documentation

### Listeners

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 64638JSON JMESPath Assertion / JSON Assertion: Opening GUI shows a horizontal scrollbar that keeps sliding

    - 64915JMeter Cache Manager misbehaving when "Use Cache-Control/Expires header" is checked

### Functions

### I18N

### Report / Dashboard

    - 64547Report/Dashboard: Ensure graphs Response codes per second is not broken by empty response code in SampleResult. Contributed by Ubik Load Pack (https://ubikloadpack.com)

    - 64617HTML report: In graph Response Time Percentiles Over Time 90,95,99th percentile correspond in reality to 0.90, 0.95 and 0.99 percentiles

    - 64553When using Transaction Controller, send Bytes and Received Bytes are displayed as 0 in the influxdb(BackendListener)

    - 64624Use less aggressive escaping for JSON Strings in reports error messages

### Documentation

    - [PR #571](https://github.com/apache/jmeter/pull/571)Correct documented name of generated CA when using proxy script recorder. Part of a bigger PR. Vincent Daburon (vdaburon at gmail.com)

    - Change documentation of the special header functionality of the mirror server to reflect the implementation.

### General

    - 64448User Defined Variable Duplication in Right Click Context Menu

    - 64499Exiting JMeter when `jmeterengine.stopfail.system.exit=true` takes too much time if threads are not stopped

    - 64510Darklaf- IntelliJ Theme throws NPE in DarkTreeUI on MacOS

    - 64594Unable to enter variable values instead of numeric values in components using PowerTableModel (Impacts 3rd party plugins like Throughput Shaping Timer)

    - 64475Menu Generate HTML Report: When report generation fails due to timeout, error message is not explicit. Contributed by Ubik Load Pack (https://ubikloadpack.com)

    - 64627Programmatic manipulation of the control flow via API methods of JMeterContext is not working as it used to before 5.0. Contributed by Till Neunast

    - 64647groovy-dateutil is missing in distribution

    - 64640Darklaf: NPE at com.github.weisj.darklaf.ui.DarkPopupFactory.getPopupType(DarkPopupFactory.java:96)

    - 64641Darklaf: NPE at com.github.weisj.darklaf.ui.tree.DarkTreeUI.isChildOfSelectionPath(DarkTreeUI.java:603) ~[darklaf-core-2.4.2-SNAPSHOT.jar:2.4.2-SNAPSHOT]

    - 64453Darklaf: Save Test Plan as New Folder failure

    - 64625Darklaf: trying to select a folder in Browse leads to an error popup and stacktrace

    - 64711Textarea Colors are not good in dark modes. Contributed by Jannis Weis

    - 64935A broken plugin class should not prevent JMeter from starting

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Michael Weidmann (https://github.com/michaelweidmann)

  - Deng Liming (liming.d.pro at gmail.com)

  - jmetertea (https://github.com/jmetertea)

  - Ubik Load Pack

  - [Jannis Weis](https://github.com/weisJ/darklaf)

  - [Daniel van den Ouden](https://github.com/topicus-pw-dvdouden)

  - Till Neunast (https://github.com/tilln)

  - Pooja Chandak (poojachandak002 at gmail.com)

  - Vincent Daburon (vdaburon at gmail.com)

  - Woonsan Ko (woonsan at apache.org)

  - Timo (ASF.Software.Timo at Leefers.eu)

  - Graham Russell (graham at ham1.co.uk)

We also thank bug reporters who helped us improve JMeter.

  - Hiroyoshi Mitsumori (mitsumori at mis.dev)

Apologies if we have omitted anyone else.

 

### Known problems and workarounds

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
    Copy [rhino-engine-1.7.13.jar](https://github.com/mozilla/rhino/releases/download/Rhino1_7_13_Release/rhino-engine-1.7.13.jar) into `$JMETER_HOME/lib/ext`.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 and its dependencies and set the module path is outlined below:
      ```

mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.0/nashorn-core-15.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.0/asm-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.0/asm-commons-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.0/asm-util-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.0/asm-tree-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.0/asm-analysis-9.0.jar
popd
export JVM_ARGS="--modulepath $PWD/lib/modules"
./bin/jmeter
      
```

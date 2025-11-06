---
title: Version 5.5
---

# Version 5.5

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

JMeter now supports Java 17

JMeter 5.5 ships with log4j2 2.17.2

Open Model Thread Group

New component: `[Open Model Thread Group](./usermanual/component_reference.html#Open_Model_Thread_Group)`
allows creating load profiles with variable load.

For example, if you need to gradually increase load from `0/sec` to `10/sec` during `minute`
you could previously use `Thread Group + Timer` combinations. However, then you need to compute
the expected number of threads, ensure they are created only when needed, and so on.

With `Open Model Thread Group` you can configure the same load profile as `rate(0/sec) random_arrivals(1&nbsp;minute) rate(10/sec)`.
The thread group would spawn threads as needed to drive the configured load.
The load profile can use properties, so you can launch the same script with slightly different load levels,
however, the profile can't be updated while the test is running.

The new thread group is experimental in JMeter 5.5, so please feel free to submit your feedback.

Open Model Thread Group sample

Preparing the deprecation of Oro Regex usage

Another experimental feature in JMeter 5.5 is the ability to replace the Oro based Regex implementation
  by the built-in Java based one. To choose the Java based one, set the JMeter property `jmeter.regex.engine`
  to the value `java`.

Core improvements
Kotlin language is now used in some core classes and tests (e.g. Open Model Thread Group).
    JMeter is compiled with `apiTarget=1.5`, and it ships with `kotlin-stdlib` 1.6.

[lets-plot-kotlin](https://github.com/JetBrains/lets-plot-kotlin) charting library is added,
    so it will be easier to refine and create new charts in UI in the future.

### Improvements

### Thread Groups

  - New component: `[Open Model Thread Group](./usermanual/component_reference.html#Open_Model_Thread_Group)`

### HTTP Samplers and Test Script Recorder

  - 65027Detect mime-type for files automatically when adding files to HTTP Sampler

  - 65020HTTP Sampler/Files upload tab &ndash; add missing buttons

  - [PR #650](https://github.com/apache/jmeter/pull/650)HTTP Sampler timestamp fix when exception is caught. Contributed by Konstantin Kalinin (konstantin at kkalinin.pro)

  - 65328[PR #666](https://github.com/apache/jmeter/pull/666)HTTP 308 Permanent Redirect is not supported. Contributed by
    Baptiste Gaillard (baptiste.gaillard at gmail.com)

### Other samplers

  - 65149[PR #644](https://github.com/apache/jmeter/pull/644)Encode the personal part of email addresses in SMTP Sampler

  - [PR #638](https://github.com/apache/jmeter/pull/638)Various additions to the Bolt Sampler. Added `transaction timeout`, `database`
    option required for Neo4j 4.x (with multi-database support) and `access mode` option, that allows running
    against a Neo4j Enterprise Causal Cluster. Contributed by David Pecollet (david.pecollet at gmail.com)

### Controllers

  - [PR #665](https://github.com/apache/jmeter/pull/665)Increase visible lines of code in `IfController` and `WhileController`.
    Based on an idea by David Getzlaff (david.getzlaff at t-systems.com>).

### Listeners

  - 64988Sort properties and variables in a human expected order for DebugPostProcessor and DebugSampler

  - 63061Sort View Results in Table in a human expected order

  - [PR #706](https://github.com/apache/jmeter/pull/706)Try to keep UI responsive when displaying large text results. Can be configured with the new property
      `view.results.tree.simple_view_limit`

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - [PR #638](https://github.com/apache/jmeter/pull/638)Bolt Connection Configuration: added `ConnectionPoolMaxSize` parameter. Contributed by
        David Pecollet (david.pecollet at gmail.com)

    - 65515Allow pooling of Prepared Statements in JDBC

    - 65299JSONPathAssertion attributes are out of order/Compare JSON objects and
        not their string representations.

### Report / Dashboard

  - 65353Make the estimator used for calculating percentiles on the dashboard configurable

### General

  - 61805[PR #663](https://github.com/apache/jmeter/pull/663)Add simple HTTP request template. Contributed by Ori Marko (orimarko at gmail.com)

  - 65611[PR #673](https://github.com/apache/jmeter/pull/673)Add support for IPv6 addresses when specifying a remote worker node. Based on a patch by Peter Wong (peter.wong at csexperts.com)

  - Reduce memory consumption by the logging panel (disable undo events for it)

  - 63620[PR #694](https://github.com/apache/jmeter/pull/694)Fix GUI freeze when viewing response body with long line breaks

  - [PR #699](https://github.com/apache/jmeter/pull/699)Add documentation for Graphite Backend Listener. Contributed by Ji Hun (jihunkimkw at gmail.com)

  - 57672[PR #700](https://github.com/apache/jmeter/pull/700)Add a switch (`jmeter.regex.engine`) to replace Oro Regex implementation by the built-in Java one.

### Non-functional changes

  - Added Kotlin 1.6.21 for JMeter engine implementation (apiVersion=1.5). The set of JSR 223 languages is intact.

  - 65128[PR #643](https://github.com/apache/jmeter/pull/643)Add missing documentation about `Same user on each iteration` for Thread Groups. Contributed by njkuzas.

  - [PR #648](https://github.com/apache/jmeter/pull/648)Updated xmlgraphics-commons to 2.6 (from 2.3). Contributed by Stefan Seide (stefan at trilobyte-se.de)

  - [PR #655](https://github.com/apache/jmeter/pull/655)[PR #667](https://github.com/apache/jmeter/pull/667)[PR #675](https://github.com/apache/jmeter/pull/675)[PR #698](https://github.com/apache/jmeter/pull/698)Updated x-stream to 1.4.19 (from 1.4.15). Contributed by Stefan Seide (stefan at trilobyte-se.de)

  - [PR #656](https://github.com/apache/jmeter/pull/656)[PR #668](https://github.com/apache/jmeter/pull/668)Updated json-smart to 2.4.8 (from 2.3), accessors-smart to 2.4.8 (from 1.2) and asm 9.3 (from 9.0). Contributed by Stefan Seide (stefan at trilobyte-se.de)

  - 64831Log truststore entries in debug level for logger `org.apache.jmeter.util.keystore.JmeterKeyStore`

  - 65232Hide splash screen when an error is displayed because the test plan could not be parsed.

  - Updated Groovy to 3.0.11 (from 3.0.7).

  - Updated Darklaf to 2.7.3 (from 2.5.4).

  - Updated Apache ActiveMQ to 15.6.4 (from 15.6.0).

  - Updated Asm to 9.2 (from 9.1).

  - Updated Bouncycastle to 1.70 (from 1.67).

  - Updated Caffeine to 2.9.3 (from 2.8.8).

  - Updated Apache commons-dbcp2 to 2.9.0 (from 2.8.0).

  - Updated Apache commons-io to 2.11.0 (from 2.8.0).

  - Updated Apache commons-lang3 to 3.12.0 (from 3.11).

  - Updated Apache commons-net to 3.8.0 (from 3.7.2).

  - Updated Apache commons-pool2 to 2.11.1 (from 2.9.0).

  - Updated equalsverifier to 3.10 (from 3.4.2).

  - Updated Apache Freemarker to 2.3.31 (from 2.3.30).

  - Updated hsqldb to 2.5.2 (from 2.5.0).

  - Updated Apache HttpClient to 4.5.13 (from 4.5.12).

  - Updated Apache HttpCore to 4.4.15 (from 4.4.13).

  - Updated jacoco to 0.8.7 (from 0.8.5).

  - Updated json-path to 2.7.0 (from 2.4.0).

  - Updated jsoup to 1.15.1 (from 1.13.1).

  - Updated JUnit to 4.13.2 and 5.8.2 (from 4.13.1 and 5.7.0).

  - Updated Apache log4j2 to 2.17.2 (from 2.13.3).

  - Updated Miglayout to 5.3 (from 5.2).

  - Updated Neo4j Java driver to 4.4.6 (from 4.2.0).

  - Updated Objenesis to 3.2 (from 2.6).

  - Updated ktlint to 0.40.0

  - Updated PH CSS and PH commons to 6.5.4 and 10.1.6 (from 6.2.3 and 9.5.1).

  - Updated RSyntaxTextArea to 3.2.0 (from 3.1.1).

  - Updated SLF4J to 1.7.36 (from 1.7.30).

  - Updated SvgSalamander to 1.1.2.4 (from 1.1.2.1).

  - [PR #698](https://github.com/apache/jmeter/pull/698)Updated Apache Tika to 1.28.3 (from 1.26).

  - Updated WireMock-JRE8 to 2.30.0 (from 2.24.1).

  - Updated com.github.vlsi.vlsi-release-plugins 1.76 (from 1.74).

  - Updated jackson to 2.13.3 (from 2.10.5)

  - Updated jmespath to 0.5.1

  - Updated Saxon-HE to 11.2 (from 9.9.1-8)

  - Updated Apache xmlgraphics commons to 2.7 (from 2.6)

  - [PR #671](https://github.com/apache/jmeter/pull/671)Move example definition of property `jmeter.reportgenerator.statistic_window`
     to `user.properties`, as it is read from that place.
     Contributed by Rithvik Patibandla (rithvikp98 at gmail.com)

  - 65456Updated commons-jexl 3 to 3.2.1 (from 3.1). Contributed by Ori Marko (orimarko at gmail.com>)

  - [PR #654](https://github.com/apache/jmeter/pull/654)Try do give better feedback while loading keystores

  - [PR #672](https://github.com/apache/jmeter/pull/672)Add more details to documentation for timeShift function. Contributed by Mariusz (mawasak at gmail.com)

  - Updated Gradle to 7.3 (from 7.2)

  - [PR #689](https://github.com/apache/jmeter/pull/689)Code clean up in StringFromFile. Contributed by Sampath Kumar Krishnasamy (sampathkumar.krishnasamykuppusamy at aexp.com)

  - [PR #690](https://github.com/apache/jmeter/pull/690)Refactor a few unit tests. Contributed by Sampath Kumar Krishnasamy (sampathkumar.krishnasamykuppusamy at aexp.com)

  - 692>Fix a few deprecation warnings for Gradle. Contributed by Sampath Kumar Krishnasamy (sampathkumar.krishnasamykuppusamy at aexp.com)

  - 697>Junit 5 tests to use asserts from Junit 5 API. Contributed by Sampath Kumar Krishnasamy (sampathkumar.krishnasamykuppusamy at aexp.com)

  - 65983[PR #707](https://github.com/apache/jmeter/pull/707)Use current screenshot for save-to-file listener in documentation. Based on patch by NaveenKumar Namachivayam (catch.nkn at gmail.com)

  - [PR #708](https://github.com/apache/jmeter/pull/708)Make errorprone happier. Based on patch by Wilson Kurniawan (wilson at visenze.com)>

  - Updated Rhino JavaScript to 1.7.14 (from 1.7.13)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

  - 65310Don't let users override `multipart/form-data` `content-type`
    header in HC4 sampler.

  - 65363`NullPointerException` in `HTTPHC4Impl$ManagedCredentialsProvider.getAuthorizationForAuthScope` when `401` response from remote and `httpclient4.auth.preemptive=false`

  - 65692HTTP(s) Test Script Recorder: Enable setting enabled cipher suite and enabled protocols on SSLContext/ Align SSL properties between Java and HC4 implementation

  - 65108Support JMeter variables in [GraphQL HTTP Request](./usermanual/component_reference.html#HTTP_Request)

  - 65864Catch `NullPointerException` from JSoup when recording a test plan

### Other Samplers

  - 65152OS Process Sampler &ndash; Cannot `Add from Clipboard` Command parameters

  - [PR #638](https://github.com/apache/jmeter/pull/638)Bolt Sampler: fixed error displaying results when "Record Query Results" is enabled. Contributed by
        David Pecollet (david.pecollet at gmail.com)

### Controllers

### Listeners

  - 64962Save CSV sub-results recursively from View Results Tree

  - 65784No Graphs displayed in Aggregate Report/Response Time Graph

  - 65884GUI doesn't display response for multipart request *manually* encoded

### Timers, Assertions, Config, Pre- &amp; Post-Processors

  - 65257JMESPathExtractor writes error log entries if JMESPath filter returns empty result

  - 65259JMESPathExtractor Attribute `Match No.` Required

  - 65269JSON Extractor and JSON JMESPath Extractor ignore sub-samples

  - 65352Warning logged when Boundary Extractor doesn't find any match

  - 65681Use default values for `null` values when extracting with JSONPostProcessor

  - Allow setters in ConstantThroughputTimer to update the values during run time

  - 65782Use correct message format for MessageFormat in HTMLAssertion

  - 65794JSON Assertion always successful with indefinite paths

### Functions

### I18N

### Report / Dashboard

### Documentation

  - [PR #658](https://github.com/apache/jmeter/pull/658)Improve javadoc. Contributed by Ori Marko (orimarko at gmail.com)

### General

  - 64318DNS Cache Manager &ndash; custom DNS resolver does not use system resolver by default

  - [PR #641](https://github.com/apache/jmeter/pull/641)[PR #698](https://github.com/apache/jmeter/pull/698)Updated xercesImpl to 2.12.2 (from 2.12.0). Based on patch by Stefan Seide (stefan at trilobyte-se.de).

  - [PR #645](https://github.com/apache/jmeter/pull/645)Add escaping for new lines in `AbstractInfluxdbMetricsSender`. Contributed by David Getzlaff (david.getzlaff at t-systems.com>)

  - 65198Can't copy generated function from FunctionHelper

  - [PR #661](https://github.com/apache/jmeter/pull/661)Fix wording in doc. Contributed by BugKing (wangzhen at fit2cloud.com)

  - [PR #664](https://github.com/apache/jmeter/pull/664)Allow whitespace in path. Contributed by Till Neunast (github.com/tilln)

  - 65270POST `application/x-www-form-urlencoded` cURL code generated from Postman is not imported correctly

  - Silence warnings of missing font Arial on startup under Linux

  - 65300`IllegalAccessError` when opening file dialog with Java 16

  - 65336Blank labels when different elements had the same name

  - 65522Restart doesn't work, when parameters contain spaces

  - 63914Simplify `:src:dist:clean` configuration, ensure `/lib/junit/test.jar` is removed on clean

  - [PR #696](https://github.com/apache/jmeter/pull/696)Keep JSyntaxTextArea text value for use in headless mode. Contributed by Peter Paul Bakker (peter.paul.bakker at stokpop.nl)

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Stefan Seide (stefan at trilobyte-se.de)

  - njzukas (github.com/njzukas)

  - David Getzlaff (david.getzlaff at t-systems.com>)

  - Konstantin Kalinin (konstantin at kkalinin.pro)

  - David Pecollet (david.pecollet at gmail.com)

  - Ori Marko (orimarko at gmail.com)

  - BugKing (wangzhen at fit2cloud.com)

  - Till Neunast (github.com/tilln)

  - Baptiste Gaillard (baptiste.gaillard at gmail.com)

  - Rithvik Patibandla (rithvikp98 at gmail.com)

  - Mariusz (mawasak at gmail.com)

  - peter.wong@csexperts.com

  - Woonsan Ko (woonsan.ko at bloomreach.com)

  - Chromico Rek (atech5122 at gmail.com)

  - Magnus Spångdal (magnus.spangdal as avanza.se)

  - Piotr Smietana (piotrsmietana1998 at gmail.com)

  - Sampath Kumar Krishnasamy (sampathkumar.krishnasamykuppusamy at aexp.com)

  - Ji Hun (jihunkimkw at gmail.com)

  - Peter Paul Bakker (peter.paul.bakker at stokpop.nl)

  - NaveenKumar Namachivayam (catch.nkn at gmail.com)

  - Wilson Kurniawan (wilson at visenze.com)

We also thank bug reporters who helped us improve JMeter.

  - Nikola Aleksic (nalexic at gmail.com)

  - Vladimir Rosu (rosuvladimir at gmail.com)

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
    Copy [rhino-engine-1.7.14.jar](https://github.com/mozilla/rhino/releases/download/Rhino1_7_14_Release/rhino-engine-1.7.14.jar) into `$JMETER_HOME/lib/ext`.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 (or later) and its dependencies and set the module path is outlined below:
      ```

mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.3/nashorn-core-15.3.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.2/asm-9.2.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.2/asm-commons-9.2.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.2/asm-util-9.2.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.2/asm-tree-9.2.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.2/asm-analysis-9.2.jar
popd
export JVM_ARGS="--module-path $PWD/lib/modules"
./bin/jmeter
      
```

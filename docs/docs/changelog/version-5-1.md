---
title: Version 5.1
---

# Version 5.1

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
JDBC testing has been improved with ability to set init SQL statements and add
compatibility with JDBC drivers that do not support QueryTimeout

- Various bug fixes have been implemented, like gathering the correct headers when recording requests through the HTTP(S) Test Script Recorder using HTTPS

- In version 5.0, JMeter was changed to rename Sub results using a custom Naming Policy ([Bug 62550](https://bz.apache.org/bugzilla/show_bug.cgi?id=62550)). This change could be annoying for Functional Testing, a new property
`subresults.disable_renaming=true` has been introduced to revert if needed to previous behaviour. An alternative is to check `Functional Test Mode` in Test Plan, see [Bug 63055](https://bz.apache.org/bugzilla/show_bug.cgi?id=63055)

UX improvements
Templates can provide parameters that are filled in on test plan generation,
`Recording` template uses this feature

A new `Tools` menu has been introduced to collect those entries,
that are used for general usage around JMeter, like:

    - `Function Helper Dialog`

    - `Export transactions for report`

    - `Generate Schematic View` which provides an overview as HTML of the Test plan

    - `Import from cURL` which allows you to create or update your test plan by importing a cURL command

    - `Compile JSR223 Test Elements`

    - `Create a heap dump`

    - `Create a thread dump`

Test Plan
Ability to create a Test plan from a cURL command.

Scripting / Debugging enhancements

- A menu item to compile all JSR223 Elements is now available in `Tools` menu

Live Reporting and Web Report

- A JSON file containing summary of a load test statistics is now generated when using `-e` or `-g` options.

- Percentiles computing graphed over time algorithm has been modified to restart for  each time slot

- More user-friendly behaviour when reporting folder does not exist or is not empty through `-f` command line option

Functions
-->

### Incompatible changes

- In `Response Time Percentiles Over Time (successful responses)` graph of the HTML report, before this version, percentile computation of each time slot used the percentile data
of previous time slot as a base. Starting with this version, each time slot is independant. See [Bug 62883](https://bz.apache.org/bugzilla/show_bug.cgi?id=62883)

- `ClientJMeterEngine#rsetProperties` signature has been changed to use `HashMap&lt;String,String&gt;` instead of Properties, see [Bug 63034](https://bz.apache.org/bugzilla/show_bug.cgi?id=63034)

- A new Menu item `Tools` has been introduced, some menu items that were in `Help` menu are now under this new menu item. See [Bug 63094](https://bz.apache.org/bugzilla/show_bug.cgi?id=63094)

- `slf4j-ext` has been removed from libraries (lib folder) and JMeter pom. It was not used by default and due to CVE-2018-8088 and unavailability of a stable version
containing a fix to this issue, we decided to remove it. If you still needed, you can add it in lib folder.

### Improvements

### HTTP Samplers and Test Script Recorder

    - 62840HTTP Request: Add option `httpclient4.gzip_relax_mode` to avoid error when unzipping what seems to be invalid streams

    - 63025Enhance Search &amp; Replace functionality for HTTP Request to include port and protocol field. Initial code fix by Mohamed Ibrahim (rollno748 at gmail.com)

### Other samplers

    - 62934Add compatibility for JDBC drivers that do not support QueryTimeout 

    - 62935Pass custom `mail.*` properties to Mail Reader Sampler. Implemented by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter.

    - 63055Don't rename SampleResult Label when test is running in Functional mode or property `subresults.disable_renaming=true`. Implemented by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter.

### Controllers

### Listeners

  - 62822[PR #407](https://github.com/apache/jmeter/pull/407)Render uninitialized min and max values in Summary Report as `#N/A`

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 62766Keystore Config: We should load all aliases by default. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62832JDBC Connection Configuration: Be able to set init SQL statements. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Functions

  - 63037When using `CSVRead` search the script base path for files, too.

### I18N

### Report / Dashboard

    - 62883Report / Dashboard: Change the way percentiles are computed for Response Time Percentiles Over Time (successful responses) graph

    - 63060Report Generator: A generator should only check for folder/files it generates and only delete those ones

    - 63059Create a new JsonExporter that exports as JSON the content of data computed for HTML Dashboard Statistics table. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63081Command line Option `-f` does not delete report folder when using generation only through command line option `-g`. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### General

    - 62959Ability to create a Test plan from a cURL command. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - [PR #394](https://github.com/apache/jmeter/pull/394)Allow `null` values in `FieldStringEditor`. Based on patch by Mingun (alexander_sergey at mail.ru)

    - 62826When changing LAF, make JMeter restart if user clicks yes to popup

    - 62257[PR #401](https://github.com/apache/jmeter/pull/401)Expand/Collapse short key - (minus sign) on numpad doesn't work. Contributed by Ori Marko (orimarko at gmail.com)

    - 62752Add to Documentation: `ctx.getThreadNum()` is zero-based while `${__threadNum}` is one-based

    - [PR #411](https://github.com/apache/jmeter/pull/411)Use `SHA-1` instead of `SHA1` in `org.apache.jmeter.save.SaveService`. Contributed by Paco (paco.xu at daocloud.io)

    - 62914Add a hint in Thread Group UI about duration of test

    - 62925Add support for ThreadDump to the JMeter non-GUI

    - 62870Templates: Add ability to provide parameters. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62829Allow specifying Proxy server scheme for HTTP request sampler, Advanced tab and command line option. Contributed by Hitesh Patel (hitesh.h.patel at gmail.com)

    - 59633Menus `Save Test Plan as`, `Save as Test Fragment` and `Save Selection as ...` should use a new file name in File Dialog

    - 61486Make jmeter-server and non GUI mode run headless

    - 63093Add `Compile JSR223 Test Elements` menu item. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63094Introduce a new Tools menu

    - 63101Propose a menu item to generate readable overview of Test Plan

    - 63144View listener tree take a long time to open response that has huge text. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Non-functional changes

    - [PR #408](https://github.com/apache/jmeter/pull/408)Log an informational message instead of an stack trace, when JavaFX is not found for the `RenderInBrowser` component.

    - [PR #412](https://github.com/apache/jmeter/pull/412)Update Chinese translation. Contributed by 刘士 (liushilive at outlook.com).

    - [PR #406](https://github.com/apache/jmeter/pull/406)Add a short paragraph on how to use a security manager with JMeter.

    - 62893Use StringEscapeUtils from commons-text (version 1.6) instead of the deprecated ones from commons-lang3.

    - 62972[PR #435](https://github.com/apache/jmeter/pull/435)Replace calls to deprecated method `Class#newInstance`.

    - 63034ClientJMeterEngine: Make rsetProperties use `HashMap&lt;String,String&gt;` instead of Properties

    - Updated to httpclient/httpmime 4.5.7 (from 4.5.6)

    - Updated to httpcore 4.4.11 (from 4.4.10)

    - Updated to httpcore-nio 4.4.11 (from 4.4.10)

    - Updated to tika-core and tika-parsers 1.20 (from 1.18)

    - Updated to commons-dbcp2-2.5.0 (from commons-dbcp2-2.4.0)

    - Updated to commons-lang3-3.8.1 (from commons-lang3-3.8)

    - Updated to groovy-all-2.4.16 (from groovy-all-2.4.15)

    - Updated to httpasyncclient-4.1.4.jar (from 4.1.3)

    - Updated to jsoup-1.11.3 (from 1.11.2)

    - Updated to cglib-nodep-3.2.9 (from cglib-nodep-3.2.7)

    - Updated to ph-commons-9.2.1 (from ph-commons-9.1.2)

    - Updated to log4j-2.11.1 (from log4j-2.11.0)

    - Updated to xmlgraphics-commons 2.3 (from 2.2)

    - 63033Updated to Saxon-HE 9.9.1-1 (from 9.8.0-12). Thanks at Saxonica

    - Updated to xstream 1.4.11 (from 1.4.10)

    - Updated to jodd 5.0.6 (from 4.1.4)

    - Updated to asm-7.0 (from 6.1)

    - Update to ActiveMQ 5.15.8 (from 5.5.16)

    - Updated to rsyntaxtextarea-3.0.2 (from 2.6.1)

    - Updated to apache-rat-0.13 (from 0.12)

    - Updated to jacocoant-0.8.3 (from 0.8.2)

    - Updated to hsqldb-2.4.1 (from 2.4.0)

    - Updated to mina-core-2.0.19 (from 2.0.16)

    - 62818Updated to xercesImpl to 2.12.0 (from 2.11.0). Reported by Stefan Seide (stefan at trilobyte-se.de)

    - 62744Upgrade jquery to version 3.3.1, jquery-ui to 1.12.1, bootstrap to 3.3.7

    - 62821[PR #405](https://github.com/apache/jmeter/pull/405)Use SHA-512 checksums instead of MD5 to verify jar downloads

    - 63053Remove referrals to never implemented internals from user documentation. Reported by U. Poblotzki (u.poblotzki at thalia.de)

    - 63082[PR #437](https://github.com/apache/jmeter/pull/437)Use utf-8 for properties files in source

    - 63177Rename NON GUI mode into CLI Mode in documentation

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 62785[PR #400](https://github.com/apache/jmeter/pull/400)Incomplete search path applied to the filenames used in the upload functionality of the HTTP sampler. Implemented by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter.

    - 62842HTTP(S) Test Script Recorder: Brotli compression is not supported leading to "`Content Encoding Error`"

    - 60424Hessian Burlap application: JMeter inserts `0x0D` before `0x0A` automatically (http binary post data)

    - 62940Use different `cn` and type of SAN extension when we are generating certificates based on IP addresses.

    - 62916HTTP Test Script Recorder fails with UnsupportedOperationException if recording is started after a distributed test has been run

    - 62987A TestBean element under HTTP(S) Test Script recorder does not work. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63015Abnormal NoHttpResponseException when running request through proxy HTTP(S) Test Script Recorder after a first failing request. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62852HTTP Request Header missing information when using a proxy. Thanks to Oleg Kalnichevski (olegk at apache.org)

    - 63048JMeter does not retrieve link resources of type "shortcut icon" or "icon". Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Other Samplers

    - 62775If many jars are in a folder referenced by `user.classpath`, startup can be extremely slow due to JUnit

    - 63031Incorrect JDBC driver class: `org.firebirdsql.jdbc.FBDrivery`. Contributed by Sonali (arora.sonali99 at gmail.com)

### Controllers

    - 62806ModuleController cloning by Run behaves differently whether in GUI or Non GUI mode. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62847If Controller cannot use variable for index exposed by LoopController/WhileController/ForEachController

    - 63064Ignore spaces at the end and beginning of expressions used in IfController

### Listeners

    - 62770Aggregate Graph throws `ArrayIndexOutOfBoundsException`

    - 63069ResultCollector does not write end of XML file if user exits while a Recording or a test is running. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63138InfluxDB BackendListenerClient: In case of error, log is in debug, it should be in error

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 62774XPath2Extractor: Scope variable is broken. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62860JSON Extractor: Avoid NPE and noisy error message "`Error processing JSON content in`" when variable is not found

### Functions

### I18N

### Report / Dashboard

    - 62777Web Report / Dashboard: Hide All in `Response Time Percentiles Over Time (successful responses)` fails.

    - 62780Web Report / Dashboard: Display All in `Response Time Vs Request` fails.

    - 62781Web Report / Dashboard: Display All in `Response Time Overview` fails.

    - 62782Web Report / Dashboard: Remove duplicate/unused dependencies

    - 62894Report / Dashboard: Throughput is in wrong column which is confusing as unit is millisecond

    - 63016Empty HTML report if source csv contains labels with quotes. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Documentation

    - Change `Test Action` (old name) to `Flow Control Action` in Component Reference documentation. Contributed by Ori Marko (orimarko at gmail.com)

### General

    - 62745Fix undefined disabled icon. Contributed by Till Neunast (https://github.com/tilln)

    - 62743Client auth must be enabled on distributed testing

    - 62767NPE when searching under certain conditions. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62790`ArrayIndexOutOfBoundsException` when calling replace without selecting the first match

    - 62795JMeter controller node sometimes ends distributed test even though some of the worker nodes have not finished

    - 62336[PR #396](https://github.com/apache/jmeter/pull/396)Some shortcuts are not working correctly on windows. Contributed by Michael Pavlov (michael.paulau at gmail.com)

    - 62889Format JSON Arrays when displayed with JSON Path Tester.

    - 62900ObjectProperty#getStringValue() can throw NullPointerException

    - 63099Escape commata in function helper dialog only outside of variable replacement structures.

    - 63105Export Transactions for Report: fix 2 bugs

    - 63106Apply naming policy does not refresh UI

    - 63180Apply Naming Policy allows multi selection but only considers first node

    - 63090Remove slf4j-ext due to CVE-2018-8088

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

    - Oleg Kalnichevski (olegk at apache.org)

    - Till Neunast (https://github.com/tilln)

    - Mingun (alexander_sergey at mail.ru)

    - [Ubik Load Pack](https://ubikloadpack.com)

    - Artem Fedorov (artem.fedorov at blazemeter.com)

    - Stefan Seide (stefan at trilobyte-se.de)

    - 刘士 (liushilive at outlook.com)

    - Michael Pavlov (michael.paulau at gmail.com)

    - Ori Marko (orimarko at gmail.com)

    - Paco (paco.xu at daocloud.io)

    - Hitesh Patel (hitesh.h.patel at gmail.com)

    - Sonali (arora.sonali99 at gmail.com)

    - Mohamed Ibrahim (rollno748 at gmail.com)

    - U. Poblotzki (u.poblotzki at thalia.de)

    - [Saxonica](https://www.saxonica.com)

We also thank bug reporters who helped us improve JMeter.

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

---
title: Version 5.0
---

# Version 5.0

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
Rest support has been improved in many fields

    - Multipart/form-data requests now work for `PUT`, `DELETE` &hellip;

    - It is now also possible to send a JSON Body with attached file

    - Parameters entered in Parameters Tab are now used in body instead of being ignored

In distributed testing, JMeter now automatically prefixes thread names with engine host and port, this makes the counting of threads correct in the HTML report without any other configuration as it was required before

XPath 2.0 is supported in a new element called `XPath2 extractor` providing easier XML namespaces handling, up to date XPath syntax and better performances

Upgrade to HTTP Components 4.6 last APIs has been completed and JMeter does not rely anymore on deprecated APIs of this library

It is now possible to control in an easier way Loop breaking and Loop switching to next iteration. This is available in `Flow Control Action` and `Result Status Action Handler` elements

While Controller now exports a variable containing its current index named `__jm__&lt;Name of your element&gt;__idx`. So for
example, if your While Controller is named WC, then you can access the looping index through `${__jm__WC__idx}`

Scripting / Debugging enhancements
Search feature has been improved to allow you to iterate in the tree over search results and do necessary replacements through `Next`/`Previous`/`Replace`/`Replace/Find` buttons

In View Results Tree, the request and response headers/body are clearly separated to allow you to better inspect requests and responses. You can also search in all those tabs for a particular value

Recording feature has been improved to provide a popup that is always on top when you navigate in browser allowing you to name transactions while you navigate in your application.

You can now restart JMeter from menu FileRestart

Live Reporting and Web Report
Reporting feature has been enhanced
A new Graph Total Transactions per second has been added to the HTML Web Report

It is now possible to graph over time custom metrics available as JMeter Variables through `sample_variables`. Those custom metrics graphs will be
available in the HTML Report in `Custom Graphs section`

Hits per second graph now takes into account the embedded resources

In Live reporting, the sent and received bytes are now sent to Backends (InfluxDB or Graphite)

Functions
A New function `` has been introduced to obtain ThreadGroup name.

### Incompatible changes

- Since JMeter 5.0, when using default HC4 Implementation, JMeter will reset HTTP state (SSL State + Connections) on each thread group iteration. If you don't want
this behaviour, set `httpclient.reset_state_on_thread_group_iteration=false`

- Since JMeter 5.0, in relation to above remark, `https.use.cached.ssl.context` is deprecated and not used anymore.

- Since JMeter 5.0, when using CSV output, sub results will now be also output to CSV file. To revert to previous behaviour set `jmeter.save.saveservice.subresults=false`, see [Bug 62470](https://bz.apache.org/bugzilla/show_bug.cgi?id=62470), [Bug 60917](https://bz.apache.org/bugzilla/show_bug.cgi?id=60917), [Bug 62550](https://bz.apache.org/bugzilla/show_bug.cgi?id=62550).

- Since JMeter 5.0, `CSS/JQuery Extractor` has been renamed to `CSS Selector Extractor`

- Since JMeter 5.0, `Test Action` has been renamed to `Flow Control Action`

- Since JMeter 5.0, JMeter renames subResults to `parentName-N` where N is a number to ensure that Hits Per Second graph includes resources downloads, see [Bug 62550](https://bz.apache.org/bugzilla/show_bug.cgi?id=62550), [Bug 62470](https://bz.apache.org/bugzilla/show_bug.cgi?id=62470) and [Bug 60917](https://bz.apache.org/bugzilla/show_bug.cgi?id=60917)

### Improvements

### HTTP Samplers and Test Script Recorder

    - 62260Improve Rest support. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 58757HTTP Request : Updated deprecated methods of HttpComponents to last APIs of httpclient-4.5.X. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62212Recorder : Improve UX by providing a popup above all windows to be able to change Transaction names and pauses while using Browser. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62248HTTP Request : Parameters entered in Parameters Tab should be used in body instead of being ignored. Partly based on a patch by Artem Fedorov contributed by Blazemeter.

    - 60015Multipart/form-data works only for `POST` using HTTPClient4 while it should for `PUT`, `DELETE`, &hellip; Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62317HTTP(S) Test Script Recorder: allow to add ResultSaver to created sampler

### Other samplers

  - [PR #376](https://github.com/apache/jmeter/pull/376)JUnitSampler logs exceptions except assertion-failures from test cases as warnings. Contributed by Davide Angelocola (davide.angelocola at fisglobal.com)

  - 62244Rename `Test Action` to `Flow Control Action`

  - 62302Move JSR223 Sampler up the menu. Contributed by Ori Marko (orimarko at gmail.com)

  - 62595SMTPSampler does not allow configuring the SSL/TLS protocols to be used on handshake. Contributed by Felipe Cuozzo (felipe.cuozzo at gmail.com)

### Controllers

    - 62237While Controller : Export variable containing current index of iteration. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Listeners

    - 62195Save Responses to a file : Improve component and UI. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62209InfluxBackendListenerClient: First Assertion Failure Message must be sent if error code and response code are empty or OK

    - 62269Bug 62269 - View Results Tree : Response and Request Tabs should contains Header and Body tabs. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62270View Results Tree : Allow searching in Request headers, Response Headers, and Request body. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62276InfluxDBBackendListenerClient / GraphiteBackendListenerClient : Add sent and received bytes to metrics. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 62320Counter : Reference Name property is not clear

    - 60991XPath Extractor : Implement XPath 2.0. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62593Rename CSS/JQuery Extractor to CSS Selector Extractor. Contributed by Ori Marko (orimarko at gmail.com)

### Functions

    - 62178Add default value to `` function. Contributed by Ori Marko (orimarko at gmail.com)

    - 62178Add function `` function to obtain ThreadGroup name. Mainly contributed by Ori Marko (orimarko at gmail.com)

    - 62533Allow use epoch time as Date String value in function `` 

    - 62541Allow ``, `` functions to support new syntax as `var x;`. Contributed by Ori Marko (orimarko at gmail.com)

    - 61834Function Helper Dialog : Improve tests by showing variables and keeping them available between evaluations

### I18N

### Report / Dashboard

    - 62243Dashboard : make option "`--forceDeleteResultFile`"/"`-f`" option delete folder referenced by "`-o`" option

    - 62367HTML Report Generator: Add Graph Total Transactions per Second. Contributed mainly by Martha Laks (laks.martha at gmail.com)

    - 62166Report/Dashboard: Provide ability to register custom graphs and metrics in the JMeter Dashboard. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62542Report/Dashboard : Display more information on filters when graph is empty. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62426Optimize performance of report generation. Based on feedback by Allen (444104595 at qq.com)

    - 62550Modify SubResult Naming Policy

    - 60917Load Test with embedded resources download : Hits per seconds does not take into account the downloaded resources

### General

  - 62684Distributed Testing : Add automatically to thread name a prefix to identify engine. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 62155Search Feature: Make Search text field get focus

  - 62156Search Feature : Distinguish between node that matches search and node that contains a child that matches search

  - 62234Search/Replace Feature : Enhance UX and add Replace/Next/Previous/Replace &amp; Find features. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 62238Add ability to Switch to next iteration of Current Loop. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 62239Add ability to Break Current Loop

  - 61635Add a menu to restart JMeter

  - 62470CSV Output : Enable logging of sub results when `jmeter.save.saveservice.subresults=true`. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 62473Setting "`saveservice_properties`" has counter intuitive behaviour

  - 62354Correct calculation and usage of units for second per user (reported by jffagot05 at gmail.com)

  - 62700Introduce `jsr223.init.file` to allow calling a JSR-223 script on JMeter startup

  - 62128Try to guess `JMETER_HOME` correctly, when `jmeter.bat` is called from a batch file in another directory. Contributed by logox01 (logox01 at gmx.at)

  - [PR #386](https://github.com/apache/jmeter/pull/386)Add parameter support for RMI keystore creation scripts. Contributed by Logan Mauzaize (t524467 at airfrance.fr)

  - 62065Use Maven artifact for JAF Module instead of embedded module

  - 61714Update Real-time results documentation

  - [PR #382](https://github.com/apache/jmeter/pull/382)Correct typo in documentation. Reported by Perze Ababa (perze.ababa at gmail.com>)

  - [PR #392](https://github.com/apache/jmeter/pull/392)Correct typo in documentation. Reported by Aaron Levin

  - [PR #379](https://github.com/apache/jmeter/pull/379) Improve chinese translations. Contributed by XmeterNet

### Non-functional changes

    - [PR #358](https://github.com/apache/jmeter/pull/358)[PR #365](https://github.com/apache/jmeter/pull/365)[PR #366](https://github.com/apache/jmeter/pull/366)[PR #375](https://github.com/apache/jmeter/pull/375)Updated to latest checkstyle (v8.8). Expanded Checkstyle to files
      in `src` and `test`; fixed newly checked files. Based on contribution
      by Graham Russell (graham at ham1.co.uk)

    - 62095Correct description for right boundary parameter in Boundary Extractor. Contributed by Ori Marko (orimarko at gmail.com)

    - 62113Updated to latest Bouncycastle (v1.60). Based on contribution by Olaf Flebbe (oflebbe at apache.org)

    - 62171Remove `.md5` checksums and keep only `.sha512` checksums for source and binary archives

    - Updated to groovy-all-2.4.15 (from groovy-all-2.4.13)

    - Updated to asm-6.1 (from 6.0)

    - Updated to tika-core and tika-parsers 1.18 (from 1.17)

    - 62482Sync documentation to the implementation of the ForEachController. Based on contribution by Ori Marko (orimarko at gmail.com)

    - 62529Updated to httpclient-4.5.6 (from httpclient 4.5.5) and updated to freemarker-2.3.28 (from freemarker-2.3.23). Based on patch by Ori Marko (orimarko at gmail.com)

    - Updated to httpmime-4.5.6 (from httpmime-4.5.5)

    - Updated to caffeine-2.6.2 (from caffeine-2.6.1)

    - Updated to cglib-nodep-3.2.7 (from cglib-nodep-3.2.6)

    - Updated to commons-dbcp2-2.4.0 (from commons-dbcp2-2.2.0)

    - Updated to commons-pool2-2.6.0 (from commons-pool2-2.5.0)

    - Updated to httpcore-4.4.10 (from httpcore-4.4.9)

    - Updated to httpcore-nio-4.4.10 (from httpcore-nio-4.4.9)

    - Updated to log4j-2.11.0 (from log4j-2.10.0)

    - Updated to ph-css-6.1.1 (from ph-css-6.0.0)

    - Updated to ph-commons-9.1.2 (from ph-commons-9.0.0)

    - Updated to rhino-1.7.10 (from +rhino-1.7.7.2)

    - Updated to commons-lang3-3.8 (from commons-lang3-3.7)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 62114HTTP(S) Test Script Recorder : Client certificate authentication uses the first SSLManager created. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61058HTTP Request : Add option `httpclient4.deflate_relax_mode` to avoid "Unexpected end of ZLIB input stream" when deflating what seems to be invalid streams. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 43612HTTP PUT does not honor request parameters. Implemented by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter Ltd.

    - 60190Content-Type is added for `POST` unconditionally. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62462[PR #387](https://github.com/apache/jmeter/pull/387)Make delegation of credentials in SPNEGO possible again.

    - 58807`Reset SSL State on Thread Group iteration only (was https.use.cached.ssl.context=false` is broken)

    - 62716When Recording, JMeter removes Authorization from generated Header Manager when using Bearer Token

### Other Samplers

    - 62235Java 9 - illegal reflective access by org.apache.jmeter.util.HostNameSetter

    - 62464Set start- and end-time on JMS publisher sampler, even if initialization fails.

    - 62616FTPSampler: Upload file-size is not counted in sentBytes

### Controllers

    - 62265ModuleController behaves strangely

### Listeners

  - 62097Update JTable in Aggregate Report only when new data has arrived. That way selections of rows will be kept longer around.

  - 62203Influxdb BackendListener client: store user tags to annotation and internal transaction. Contributed by Sergey Batalin (sergey_batalin at mail.ru)

  - 62251TextGraphiteMetricsSender does not invalidate lost connections in case of network errors 

  - 60705Fix headers of Aggregate Reports and friends when columns are moved around.

  - 62463Distributed client/server setup: use different RMI ports for the remote objects when using SSL

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 61664HTTP Authorization Manager : Digest works only with legacy ,  is not implemented. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62252HTTP header merging logic does not correspond to the documentation

    - 62554BoundaryExtractor : Field to check is not reset

    - 62553Random element might return same value even if property "Per thread user (User)" is set to TRUE

    - 62637Take scheduler into account when calcuting delay for Synchronizing Timer

### Functions

### I18N

    - 62310French translation of Precise Throughput Timer label

### Report / Dashboard

    - 62333Report Dashboard - When one series contains no value, the graph colors logic is wrong. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62283Report Dashboard - Date is not correctly displayed on chart when granularity is &le;&nbsp;1 day

    - 62520The tool-tip text when we hover on the point in 'Latency Vs Request' graph should be 'Median Latency'

### Documentation

    - 62211Fix HTTP Request Server Documentation. Contributed by Ori Marko (orimarko at gmail.com)

    - [PR #388](https://github.com/apache/jmeter/pull/388)Fix a typo. Contributed by Giancarlo Romeo (giancarloromeo at gmail.com)

### General

  - 62107JMeter fails to start under Windows when `JM_LAUNCH` contains spaces

  - 62110A broken JUnit class (due to missing dependency) breaks JMeter menus. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - [PR #377](https://github.com/apache/jmeter/pull/377)Small fix of the docs. Contributed by Peter Doornbosch (peter.doornbosch at luminis.eu)

  - 62124Recording templates : Add more exclusions and use Transaction Name by default

  - 62127Store filename as String instead of File in FileEditor. This will prevent conversion of filenames from Unix style path separators to Windows style when used for example in CSV Data Set Config.

  - 56150Keep the index right, when scrolling through the menu items.

  - 62240If SampleMonitor implementation is a TestBean if will not be initialized correctly

  - 62295Correct order of elements when duplicating a selection of multiple elements.

  - 62397Don't break lines at commata when using JSON Path Tester

  - 62281Prevent NPE in MapProperty. Patch by belugabehr (dam6923 at gmail.com)

  - 62457In usermanual, the UUID Function's example is wrong. Contributed by helppass (onegaicimasu at hotmail.com)

  - 62478Escape commata in parameters when constructing function strings in the GUI function helper. Reported by blue414 (blue414 at 163.com)

  - 62463Fix usage of ports, when `client.rmi.localport` is set for distributed runs.

  - 62545Don't use a colon as part of the "tab" string when indenting JSON in RenderAsJSON.

  - Part of 62637 Avoid Integer overrun when dealing with very large values in `TimerService#adjustDelay`

  - 62683Error dialog has no text when user opens completely invalid test plan.

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

    - Graham Russell (graham at ham1.co.uk)

    - Ori Marko (orimarko at gmail.com)

    - Davide Angelocola (davide.angelocola at fisglobal.com)

    - [Ubik Load Pack](https://ubikloadpack.com)

    - Olaf Flebbe (oflebbe at apache.org)

    - Peter Doornbosch (peter.doornbosch at luminis.eu)

    - logox01 (logox01 at gmx.at)

    - Sergey Batalin (sergey_batalin at mail.ru)

    - [XMeter](https://www.xmeter.net)

    - Imane Ankhila (iankhila at ahlane.net)

    - jffagot05 (jffagot05 at gmail.com)

    - Perze Ababa (perze.ababa at gmail.com)

    - Martha Laks (laks.martha at gmail.com)

    - Logan Mauzaize (t524467 at airfrance.fr)

    - belugabehr (dam6923 at gmail.com)

    - Giancarlo Romeo (giancarloromeo at gmail.com)

    - helppass (onegaicimasu at hotmail.com)

    - blue414 (blue414 at 163.com)

    - Aaron Levin

    - Allen (444104595 at qq.com)

    - Felipe Cuozzo (felipe.cuozzo at gmail.com)

    - bangnab (ambrosetti.nicola at gmail.com)

We also thank bug reporters who helped us improve JMeter.

Apologies if we have omitted anyone else.

 

### Known problems and workarounds

- View Results Tree may freeze rendering large response particularly if this response has no spaces, see [Bug 60816](https://bz.apache.org/bugzilla/show_bug.cgi?id=60816).
This is due to an identified Java Bug [UI stuck when calling `JEditorPane.setText()` or `JTextArea.setText()` with long text without space](https://bugs.openjdk.java.net/browse/JDK-8172336).

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

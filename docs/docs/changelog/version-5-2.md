---
title: Version 5.2
---

# Version 5.2

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

This release is a major release. Please see the [Changes history page](changes_history.html)
to view the last release notes of version 5.1.1.

Core improvements
UX improvements
Test Plan
Scripting / Debugging enhancements
-->
Functions
-->

### Incompatible changes

    - HTTP(S) Test Script Recorder now appends number at end of names, while previously it added it at beginning. See [Bug 63450](https://bz.apache.org/bugzilla/show_bug.cgi?id=63450)

    - When using XPath Assertion with an XPath expression returning a boolean, `True if nothing matches` had no effect and always returned true, see [Bug 63455](https://bz.apache.org/bugzilla/show_bug.cgi?id=63455)

    - XML parsing now refuses unsecure XML, this has impacts on the following features:
        
            - XMLAssertion

            - XMLSchemAssertion

            - XPath function

            - XPath 1 &amp; 2 Extractors

            - XPath 1 &amp; 2 Assertions

        

     

### Improvements

### HTTP Samplers and Test Script Recorder

    - 63450HTTP(S) Test Script Recorder: Put number at end instead of beginning

    - 63790Embedded Resources download: Optimize CSS parsing by removing source location

### Other samplers

    - 63406JDBC connection configuration: new option for pre-initialize to initialize the connection pool. Contributed by Franz Schwab (franz.schwab at exasol.com)

    - 63561JDBC Request: Allow to only fetch a certain number of rows. Contributed by Franz Schwab (franz.schwab at exasol.com)

    - 63801Add Bolt protocol support for Neo4j database. Contributed by GraphAware (www.graphaware.com)

### Controllers

    - 63565If Controller: GC issue with JMeter during the endurance run when using with "Interpret Condition as Variable Expression?" unchecked => Improve documentation

### Listeners

    - 63720BackendListener: InfluxDBBackendListenerClient Add support for InfluxDB 2. Contributed by Jakub Bednář (https://github.com/bednar)

    - 63770View Results Tree: Add JMESPath Tester. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

  - 62863Enable PKCS11 keystores for usage with KeyStore Manager. Based on patch by Clifford Harms (clifford.harms at gmail.com).

  - [PR #457](https://github.com/apache/jmeter/pull/457)Slight performance improvement in PoissonRandomTimer by using ThreadLocalRandom. Based on a patch by Xia Li.

  - 62787New `XPath2 Assertion` supporting XPath2 with better performances than `XPath Assertion`. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 63643Skip BOM on files opened through `FileServer` and use the BOM to detect the character encoding,
      if none is given explicitly. Reported by Havlicek Honza (havlicek.honza at gmail.com)

  - 63727New `JMESPath Extractor` element to ease extraction from JSON using [JMESPath](http://jmespath.org) technology. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 63763New `JMESPath Assertion` element to ease assertion on JSON using [JMESPath](http://jmespath.org) technology. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - 63775Allow Boundary Extractor to accept empty boundaries

### Functions

  - 63219New function `__StringToFile` to save/append a string into a file. Contributed by Ubik Load Pack (support at ubikloadpack.com)

  - Use `AtomicInteger` for `__counter` instead of synchronization on our own

### I18N

### Report / Dashboard

  - 63471`StringConverter`s used for report generation should ignore white space around numbers.

### General

    - 63396JSR223 Test Elements: Description of Parameters is misleading, same for Script

    - 63480XPathAssertion and XPathAssertion2: Improve test coverage for input coming from variable. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63452Tools / Import from cURL: Complete coverage of all command line options that are valid in JMeter use case. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63419Tools / Import from cURL: Add ability to import a set of cURL commands from a file. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63760JOrphanUtils: add random alphanumeric password generator

    - 63355View Results Tree: Browser view option is not Available since Java 11, document how to make it available, see [this](./usermanual/hints_and_tips.html#browser_renderer_view_results_tree)

    - 62861Thread Group: Provide ability to configure whether a new iteration is a new user or same user (Would be applied on Cookie Manager, Cache Manager and httpclient.reset_state_on_thread_group_iteration). Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63616Fix Javadoc: ```
JMeterContext#getThreadNum()
```
 starts at 0 and not 1. Contributed by Ori Marko (orimarko at gmail.com)

    - Updated to httpclient/httpmime 4.5.10 (from 4.5.7)

    - Updated to dnsjava 2.1.9 (from 2.1.8)

    - Updated to jsoup 1.12.1 (from 1.11.3)

    - Updated to rsyntaxtextarea 3.0.4 (from 3.0.2)

    - Updated to caffeine 2.8.0 (from 2.6.2)

    - Updated to commons-codec 1.13 (from 1.11)

    - Updated to commons-lang3 3.9 (from 3.8.1)

    - Updated to commons-pool 2.7 (from 2.6)

    - Updated to commons-text 1.8 (from 1.6)

    - Updated to freemarker 2.3.29 (from 2.3.28)

    - Updated to httpcore/httpcore-nio 4.12 (from 4.11)

    - Updated to jodd 5.0.13 (from 5.0.6)

    - Updated to log4j 2.12.1 (from 2.11.1)

    - Updated to ph-commons 9.3.7 (from 9.2.1)

    - Updated to ph-css 6.2.0 (from 6.1.1)

    - Updated to Mozilla Rhino 1.7.11 (from 1.7.10)

    - Updated to Saxon-HE 9.9.1-5 (from 9.9.1-1)

    - Updated to slf4j 1.7.28 (from 1.7.25)

    - Updated to tika-core and tika-parsers 1.22 (from 1.21)

    - Updated jackson-annotations, jackson-core and jackson-databind to 2.9.10 (from 2.9.8)

### Non-functional changes

    - Migrated from subversion to [Git](https://github.com/apache/jmeter)

    - 63630Switch build from Apache Ant to Gradle

    - 63529Add more unit tests for org.apache.jorphan.util.JOrphanUtils. Contributed by John Bergqvist(John.Bergqvist at diffblue.com)

    - Updated to latest checkstyle (version 8.22)

    - Clean-up of code in `CompareAssertion` and other locations. Based on patch by Graham Russell (graham at ham1.co.uk)

    - [PR #491](https://github.com/apache/jmeter/pull/491)Increase Graphite metrics coverage. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #520](https://github.com/apache/jmeter/pull/520)Replace anonymous classes with lambda expressions. Contributed by Graham Russell (graham at ham1.co.uk).

    - [PR #524](https://github.com/apache/jmeter/pull/524)Migration from JUnit 4 to JUnit 5. Contributed by Graham Russell (graham at ham1.co.uk).

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 63298HTTP Requests with encoded URLs are being sent in decoded format

    - 63364When setting `subresults.disable_renaming=true`, sub results are still renamed using their parent SampleLabel while they shouldn't. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63129JMeter can not identify encoding during first time page submission. Based partly on analysis and PR made by Naveen Nandwani (naveen.nandwani at india.nec.com)

    - 62672HTTP Request sends double requests when using proxy with authentication. Based on patch by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter.

    - 63574HTTP Cache Manager does not cache resource if `Cache-Control` header is missing.

### Other Samplers

    - 63442Reduce scanning for `LogParser` implementations in AccessLogSamplerBeanInfo.

    - 63563LdapExtSampler: When sampler fails with exception differing from NamingException, no SampleResult is generated

    - 63469JMSPublisher: Race condition in jms.client.ClientPool#clearClient

### Controllers

### Listeners

    - 63319`ArrayIndexOutOfBoundsException` in Aggregate Graph when selecting 90&nbsp;% or 95&nbsp;% columns

    - 63423Selection of table rows in Aggregate Graph gets lost too often

    - 63347View result tree: The search field is so small that even a single character is not visible on Windows 7

    - 63433ListenerNotifier: Detected problem in Listener NullPointerException if filename is null. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63674Strip results with subresults deeper in their hierarchy when DataStripping is enabled

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 63455XPath Assertion: `True if nothing matches` does not work if XPath expression returns a boolean. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Functions

### I18N

### Report / Dashboard

### Documentation

    - 63513Add MariaDB examples to JDBC documentation. Contributed by Ori Marko (orimarko at gmail.com)

    - 63484Add notes to use Apache Velocity as JSR223 script language. Based on a patch by Ori Marko (orimarko at gmail.com)

    - 63519[PR #471](https://github.com/apache/jmeter/pull/471)Use correct method `getLabelResource()` in JMeter tutorial. Contributed by Sun Tao (buzzerrookie at hotmail.com>)

### General

    - 63394JMeter should fail with non-zero when test execution fails (due to missing test plan or other reason). Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63464image/svg+xml is wrongly considered as binary

    - 63490At end of scheduler duration lots of Samplers gets executed at the same time

    - [PR #480](https://github.com/apache/jmeter/pull/480)[PR #482](https://github.com/apache/jmeter/pull/482)Fix a few typos in comments and log messages. Based on patch by Anass Benomar (anassbenomar at gmail.com)

    - 63751Correct a typo in Chinese translations. Reported by Jinliang Wang (wjl31802 at 126.com)

    - 63723Distributed testing: JMeter controller node ends distributed test though some threads still are active

    - 63614Distributed testing: Unable to generate Dashboard report at end of load test

    - 63862 Search Dialog / Search in View Results Tree: Uncaught exception if regex is checked and regex is invalid

    - 63793Fix unsecure XML Parsing

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Clifford Harms (clifford.harms at gmail.com)

  - [Ubik Load Pack](https://ubikloadpack.com)

  - Xia Li

  - Naveen Nandwani (naveen.nandwani at india.nec.com)

  - Artem Fedorov (artem.fedorov at blazemeter.com)

  - Ori Marko (orimarko at gmail.com)

  - Sun Tao (buzzerrookie at hotmail.com)

  - John Bergqvist (John.Bergqvist at diffblue.com)

  - Franz Schwab (franz.schwab at exasol.com)

  - Graham Russell (graham at ham1.co.uk)

  - Anass Benomar (anassbenomar at gmail.com)

  - [Jakub Bednář](https://github.com/bednar)

  - Pascal Schumacher (pascalschumacher at apache.org)

  - [GraphAware](https://graphaware.com/)

We also thank bug reporters who helped us improve JMeter.

    - Sergiy Iampol (sergiy.iampol at playtech.com)

    - Brian Tully (brian.tully at acquia.com)

    - Amer Ghazal (amerghazal at gmail.com)

    - Stefan Seide (stefan at trilobyte-se.de)

    - Havlicek Honza (havlicek.honza at gmail.com)

    - Pierre Astruc (pierre.astruc at evertest.com)

    - Jinliang Wang (wjl31802 at 126.com)

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

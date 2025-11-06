---
title: Version 3.3
---

# Version 3.3

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

JMeter does not yet support JAVA 9, next JMeter version will support it, you can help and follow progress on this item in [Bug 61529](https://bz.apache.org/bugzilla/show_bug.cgi?id=61529).
Using last minor version of JAVA 8 is advised to avoid facing any JDK bug.
Sample category -->
Sample title -->
 -->
Core improvements
HTTP Sampler now supports Brotli decompression.

CacheManager now completely supports Vary header.

InfluxDB BackendListener now supports sending results to InfluxDB through UDP protocol.

It has also been enhanced to send number of errors by response code and message for each transaction

TCP Sampler now computes latency, see [Bug 60156](https://bz.apache.org/bugzilla/show_bug.cgi?id=60156)

Upgraded dependencies to last available versions bringing performance improvements and bug fixes

Continued to improve the quality of our code and tests coverage. See Quality report

UX improvements
More work has been done to better support HiDPI.

Some bugs, that crept in with the work on lowering the memory usage of View Results Tree, were fixed.

The constant `DEFAULT_IMPLEMENTATION` was removed from CookieManager,
as it lost it purpose with the removal of the alternate HTTP Client implementation in the last release

JDBC Sampler UX has been improved by adding select boxes for drivers and validation queries.

If Controller and While Controller UX have been improved

Report/Dashboard improvements
A new Help menu item has been added to simplify configuration of report generation.

Documentation improvements
Incorporated feedback about unclear documentation.

Functions
Function Helper Dialog: a new field that shows execution result has been added.

New functions:

- `` - return a date in various formats with the specified amount of seconds/minutes/hours/days added.

- `` - generate random date within a specific date range.

### Incompatible changes

- In InfluxDbBackendListenerClient, `statut` property has been renamed to `status`

- In CookieManager, `DEFAULT_POLICY` and `DEFAULT_IMPLEMENTATION` constants are now private.
If you're using `ignorecookies` with HC3CookieHandler (&lt; JMeter 3.1)  configuration will be reset, ensure you put it back.

- JMeter will not truncate anymore by default responses exceeding 10 MB. If you want to enable this truncation, see property `httpsampler.max_bytes_to_store_per_request`

- `org.apache.jmeter.protocol.tcp.sampler.TCPClient.read(InputStream)` has been deprecated in favor or org.apache.jmeter.protocol.tcp.sampler.TCPClient.read(InputStream, SampleResult),
ensure you update your implementation to be able to compute latency, see [Bug 60156](https://bz.apache.org/bugzilla/show_bug.cgi?id=60156)

### Removed elements or functions

    - `_StringFromFile` function has been dropped, use `` instead

### Improvements

### HTTP Samplers and Test Script Recorder

    - 61056HTTP : Support brotli decoding

    - 61135CookieManager : Drop Implementation select box and cleanup class

    - 61492HTTP(S) Test Script Recorder : Add the possibility to change the value of proxy.pause in the GUI

### Other samplers

    - 61320Test Action : Set duration to `0` by default

    - 61504JDBC Connection Configuration : Set Max Number of Connections to `0` by default

    - 61505JDBC Connection Configuration : Set "Validation Query" to `empty` by default to use `isValid` method of JDBC driver

    - 61506JDBC Connection Configuration : Add a list for main databases validation queries for "Validation Query" attribute

    - 61507JDBC Connection Configuration : Add a list for main databases JDBC driver class name for "JDBC Driver class" attribute

    - 61525OS Process Sampler : Add browser button to Command and Working directory fields

    - 60156TCPSampler : Latency is not measured for TCP Sampler. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61039CSV data set config : Add browser button to Filename field

    - 61527CSV data set config : Add a list for main file encoding values for File encoding attribute

### Controllers

    - 61131IfController and WhileController : Improve UX

### Listeners

    - 61167InfluxdbBackendListener : add number of errors by response code and message for each transaction

    - 61068Introduce property `resultcollector.action_if_file_exists` to control the popup "File already exists" when starting a test

    - 61457InfluxDB backend listener client : Support sending result to InfluxDB through UDP protocol. Partly based on [PR #302](https://github.com/apache/jmeter/pull/302) by Junlong Wu (github id mybreeze77)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 61176[PR #298](https://github.com/apache/jmeter/pull/298) Cache responses that have `vary` header in the `CacheManager`.

### Functions

    - 61040Add a time shifting function

    - 61126Function Helper Dialog : Add a field that shows execution result

    - 61508Add a random date within a specific date range function

### I18N

    - 61509Better label/translation/documentation for labels start and max for Counter element

### Report / Dashboard

    - 61481Help Menu Item to export transaction for Web report

### General

    - When looking for classes in `ActionRouter`, fall back to location of the jar,
       where `ActionRouter` is loaded from. Provided by Emilian Bold (emi at apache.org)

    - 61510Set 'Max Number of Connections' to `0` into 'JDBC Connection Configuration' for the 'JDBC Load Test template'

    - 61399Make some bin and extras scripts Shellcheck compatible. Contributed by Wolfgang Wagner (internetwolf2000 at hotmail.com)

### Non-functional changes

    - Updated to groovy 2.4.12 (from 2.4.10)

    - Updated to caffeine 2.5.5 (from 2.4.0)

    - Updated to commons-jexl3 3.1 (from 3.0)

    - Updated to ph-css 5.0.4 (from 5.0.3)

    - Updated to ph-commons 8.6.6 (from 8.6.0)

    - Updated to log4j2 2.8.2 (from 2.8.1)

    - Updated to xmlgraphics-commons 2.2 (from 2.1)

    - Updated to jodd 3.8.6 (from 3.8.1)

    - Updated to xstream 1.4.10 (from 1.4.9)

    - Updated to Apache Tika 1.16 (from 1.14)

    - Updated to jsoup-1.10.3 (from 1.10.2)

    - Updated to commons-lang3 3.6 (from 3.5)

    - Updated to json-path 2.4.0 (from 2.2.0)

    - Updated to httpcore 4.4.7 (from 4.4.6)

    - 61438Change the cryptographic signature of packages from sha-1 to sha-512

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

  - 61384Don't set the charset on enclosing `multipart/form-data` header. It irritates some servers.
     The charset was added sometime back while refactoring to use a newer API of http client.
     See 56141 for more info.

  - 61456`java.lang.ArrayIndexOutOfBoundsException` when recording with JMeter and weird Basic Auth Authorization header

  - 61395Large server response truncation can impact recording

### Other Samplers

    - 60889JMeter JDBC sample calls `SELECT USER()` when testing with MySQL JDBC due to `Connection#toString` call for response headers.

    - 61259JDBC Request : since JMeter 3.0, when JDBC auto-commit is `false`, a rollback statement happens each time a Request is executed. Partly contributed by Liu XP (liu_xp2003 at sina.com)

    - 61319Fix regression: SMTP Sampler could not send mails, when no attachments were specified.

### Controllers

    - 61375Use system DNS resolver as last resort, when resolving entries in the static host table.

### Listeners

    - 61005View Results Tree - Browser Response Data is not clearing

    - 61121InfluxdbBackendListenerClient: Only all percentiles are sent, not `KO` and `OK`

    - 60961Try to keep status of selected and expanded elements in View Results Tree when new elements are added.

    - 61198Backend Listener does not work properly in main script when included scripts also contain Backend Listener

    - 61493Max/Min threads are interchanged in Graphite and InfluxDB backend listener

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 58743[PR #293](https://github.com/apache/jmeter/pull/293) TableEditor can't be saved, when using two or more instances. Bugfix provided by Emilian Bold (emi at apache.org)

    - 61314HTTP URL Re-writing Modifier doesn't replace existing `jsessionid` in http sampler, but adds it to the end

    - 61336BeanShell Assertion : mistake in Chinese translation

### Functions

    - 61258StringFromFile function is mentioned twice in the Function helper dialog

    - 61260`` function returns null despite XPath checker founds matches

    - 58876TestPlanName function returns `null` for a newly saved Test Plan and uses previously opened one for a new one

### I18N

### Report / Dashboard

    - 61129Report/Dashboard : If response code is empty but a `failureMessage` is present, Errors and Top 5 Errors are not accurate. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61151Report/Dashboard : Top 5 Errors by Sampler and Errors : If assertion contains html code, the html part is hidden

### General

    - 60743Stopping / Shutting down Test might create a deadlock due to HTTPCORE-446, fixed by HttpCore upgrade to 4.4.7

    - 60994Fix some typo in comments or log messages. [PR #289](https://github.com/apache/jmeter/pull/289) and [PR #290](https://github.com/apache/jmeter/pull/290)

    - 61011Replace occurrences count is not correct (Path and Host replacement are counted twice)

    - 61026Cannot run program "keytool": CreateProcess error=2 when starting JMeter 3.2 in GUI mode

    - 61054Endless loop in `JOrphanUtils#replaceAllWithRegex` when regex is contained in replacement

    - 60995HTTP Test Script Recorder: Port field is very small under some L&amp;F

    - 61073HTTP(S) Test Script Recorder panel have some fields with bad size on HiDPI screen or GTK+ L&amp;F on Linux/XWayland

    - 57958Fix transaction sample not generated if thread stops/restarts. Implemented by Artem Fedorov (artem at blazemeter.com) and contributed by BlazeMeter Ltd.

    - 61050Handle uninitialized RessourceBundle more gracefully, when calling `JMeterUtils#getResString`.

    - 61100Invalid GC Log Filename on Windows

    - 57962Allow to use variables (from User Defined Variables only) in all listeners in worker node mode

    - 61270Fixed width fonts too small in text areas to read under HiDPI (user manual bug)

    - 61292Make processing of samples in reporter more robust.

    - 61359When cutting an element from Tree, Test plan is not marked as dirty

    - 61380JMeter shutdown using timers releases thundering herd of interrupted samplers

    - 57055CheckDirty.doAction should clear previousGuiItems for `SUB_TREE_SAVED`

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

- Anass Benomar (abenomar at umanis.com, Mithrandir0407 at github)

- Anthony Kearns (anthony.kearns atrightside.co)

- Emilian Bold (emi at apache.org)

- Liu XP (liu_xp2003 at sina.com)

- [Ubik Load Pack](http://ubikloadpack.com)

- Wolfgang Wagner (internetwolf2000 at hotmail.com)

- Junlong Wu (github id mybreeze77)

We also thank bug reporters who helped us improve JMeter. 
For this release we want to give special thanks to the following reporters for the clear reports and tests made after our fixes:

- Liu XP (liu_xp2003 at sina.com)

- Alexander Podelko (apodelko at yahoo.com)

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

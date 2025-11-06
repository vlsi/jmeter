---
title: Version 5.1.1
---

# Version 5.1.1

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

This release is mainly a bugfix release. Please see the [Changes history page](changes_history.html)
to view the last major behaviors with the version 5.1.

Core improvements
UX improvements
Test Plan
Scripting / Debugging enhancements
-->
Live Reporting and Web Report
A new menu entry has been added to the **Tools** menu. It's allow to generate
a results report from a previous CSV/JTL file.

Functions
-->

### Incompatible changes

### Improvements

### HTTP Samplers and Test Script Recorder

    - 62977Allow sending HTTP requests without a default User-Agent header

### Other samplers

    - 63185LDAP related elements: Add option to implicitly trust SSL/TLS connections/Disable hostname verification. Based on contribution by Brian Wolfe (wolfebrian2120 at gmail.com)

### Controllers

### Listeners

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 63178CSS Selector Extractor: Improve performance of JODD (JoddExtractor) based implementation

### Functions

### I18N

### Report / Dashboard

    - 59896 Report / Dashboard: Add a menu entry to generate a report on demand from a CSV file. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### General

    - [PR #444](https://github.com/apache/jmeter/pull/444)Update to latest Spock v1.2 (was 1.0). Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #446](https://github.com/apache/jmeter/pull/446)Improve Unit tests readability and use of Spock. Contributed by Graham Russell (graham at ham1.co.uk)

### Non-functional changes

    - 63203Unit Tests: Replace use of `@Deprecated` by `@VisibleForTesting` for methods/constructors/classes made public for Unit Testing only

    - [PR #449](https://github.com/apache/jmeter/pull/449)Refactor and Test ResponseTimePercentilesOverTimeGraphConsumer. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #450](https://github.com/apache/jmeter/pull/450)Abstract graph consumer improvements. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #451](https://github.com/apache/jmeter/pull/451)Improve a few unit tests and classes. Contributed by Graham Russell (graham at ham1.co.uk)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

### Other Samplers

    - 63202JMS Publisher: ObjectMessageRenderer creates XStream instance with uninitialized security

### Controllers

### Listeners

    - 63204`RenderAsJSON#prettyJSON`: `JSONParser#parse` cannot return JSONValue

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 62446Counter documentation is wrong in required fieds. Contributed by orimarko at gmail.com

    - 62327TestPlan: In library table if path is modified and plan saved, the modification is lost on file reload

### Functions

    - 63241`__threadGroupName` causes a NullPointerException if called from non Test threads

### I18N

### Report / Dashboard

    - 63198Response Time Vs Request and Latency Vs Request graphs don't line up with throughput. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### Documentation

### General

    - 63201SearchTreeDialog disappears behind master JFrame. Contributed by Benoit Vatan (benoit.vatan at gmail.com)

    - 63220`Function Helper Dialog`, `Export transactions for report` and `Import from cURL` disappear being master JFrame. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 63207java.lang.NullPointerException: null when run JMeter 5.1 with proxy options

    - 58183Rampup may not be respected if thread take time to start leading to threads continuing to start post ramp up time

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

    - [Ubik Load Pack](https://ubikloadpack.com)

    - Benoit Vatan (benoit.vatan at gmail.com)

    - Graham Russell (graham at ham1.co.uk)

    - Brian Wolfe (wolfebrian2120 at gmail.com)

    - orimarko at gmail.com

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

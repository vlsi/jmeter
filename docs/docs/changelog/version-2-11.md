---
title: Version 2.11
---

# Version 2.11

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Known bugs](#Known bugs)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Thanks](#Thanks)

### New and Noteworthy

Improvements -->
HTTP(S) Test Script Recorder improvements

Following improvements have been made since major changes introduced in JMeter 2.10 on HTTP(S) Test Script Recorder:

- Better detection of missing or invalid configuration of keytool utility

- New system property `keytool.directory` (see `system.properties`) lets you configure directory containing keytool in case on non-standard installation

JMS Publisher/Point to Point : Add ability to set typed values in JMS header properties
In the samplers JMS Publisher and JMS Point-to-Point, you can now set up the class of values for the JMS header properties. Previously only String was possible.

View Results Tree : Add an XPath Tester 
In View Results Tree listener, a new XPath tester can be used to test XPATH expressions.

Ability to choose the client alias for the cert key in JsseSslManager such that Mutual SSL auth testing can be made more flexible
When testing client based certificate authentications you have now better control on certificate you use through a new field "Variable name holding certificate alias", this
field lets you select the certificate you want to send to server to authenticate. You can use a CSV Data Set as a holder for the variable value.

Add a "Save as Test Fragment" option 
In the file menu, a new option allow to save a group of elements as a Test fragment.

Summariser is be enabled by default in Non GUI mode
When you run JMeter from command line, now JMeter displays some statistics from the Summariser mode.

Transaction Controller:Change default property "Include duration of timer&hellip;" for newly created element
Starting from 2.11, Transaction Controller is configured by default to exclude processing time of pre/post processors as long as timers pause.

Sample title

- Sample text

Sample title
Sample text

 -->

### Known bugs

- The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

- Listeners don't show iteration counts when a If Controller has a condition which is always false from the first iteration (see [Bug 52496](https://bz.apache.org/bugzilla/show_bug.cgi?id=52496)).
A workaround is to add a sampler at the same level as (or superior to) the If Controller.
For example a Test Action sampler with 0 wait time (which doesn't generate a sample),
or a Debug Sampler with all fields set to False (to reduce the sample size).

- 
The numbers that appear to the left of the green box are the number of active threads / total number of threads,
the total number of threads only applies to a locally run test, otherwise it will show 0 (see [Bug 55510](https://bz.apache.org/bugzilla/show_bug.cgi?id=55510)).

- 
Note that there is a [bug in Java](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6396599 )
on some Linux systems that manifests itself as the following error when running the test cases or JMeter itself:

 [java] WARNING: Couldn't flush user prefs:
 java.util.prefs.BackingStoreException:
 java.lang.IllegalArgumentException: Not supported: indent-number

This does not affect JMeter operation. This issue is fixed since Java 7b05.

- 
With Java 1.6 and Gnome 3 on Linux systems, the JMeter menu may not work correctly (shift between mouse's click and the menu).
This is a known Java bug (see  54477 ).
A workaround is to use a Java 7 runtime (OpenJDK or Oracle JDK).

- 
With Oracle Java 7 and Mac Book Pro Retina Display, the JMeter GUI may look blurry.
This is a known Java bug, see Bug JDK-8000629.
A workaround is to use a Java 7 update 40 runtime which fixes this issue.

### Incompatible changes

- When creating a new Transaction Controller, property "Include duration of timer and pre-post processors in generated sample" will be unchecked starting from version 2.11

- In Non GUI mode, since 2.11 summariser is enabled with a 30 seconds frequency

- JMeter is more lenient with redirect handling and relaxes on RFC2616 by allowing relative locations. See property "`jmeter.httpclient.strict_rfc2616`" in `jmeter.properties` to change this behaviour, see [Bug 55717](https://bz.apache.org/bugzilla/show_bug.cgi?id=55717)

- When creating a new Response Assertion, property "Pattern Matching Rules" now defaults to Substring starting from version 2.11

### Bug fixes

### HTTP Samplers and Test Script Recorder

- [Bug 55815](https://bz.apache.org/bugzilla/show_bug.cgi?id=55815) - Proxy#getDomainMatch does not handle wildcards correctly

- [Bug 55717](https://bz.apache.org/bugzilla/show_bug.cgi?id=55717) - Bad handling of Redirect when URLs are in relative format by HttpClient4 and HttpClient3.1

### Other Samplers

- [Bug 55685](https://bz.apache.org/bugzilla/show_bug.cgi?id=55685) - OS Sampler: timeout option don't save and restore correctly value and don't init correctly timeout

### Controllers

- [Bug 55816](https://bz.apache.org/bugzilla/show_bug.cgi?id=55816) - Transaction Controller with "Include duration of timer&hellip;" unchecked does not ignore processing time of last child sampler

### Listeners

- [Bug 55826](https://bz.apache.org/bugzilla/show_bug.cgi?id=55826) - Unsynchronised concurrent accesses to list in field RespTimeGraphVisualizer.internalList 

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 55694](https://bz.apache.org/bugzilla/show_bug.cgi?id=55694) - Assertions and Extractors : Avoid NullPointerException when scope is variable and variable is missing

- [Bug 55721](https://bz.apache.org/bugzilla/show_bug.cgi?id=55721) - HTTP Cache Manager - no-store directive is wrongly interpreted

### Functions

- [Bug 55871](https://bz.apache.org/bugzilla/show_bug.cgi?id=55871) - Wrong result with intSum() function when a space character is present before/after the number. Contributed by Milamber based on a proposal by James Liang.

### I18N

### General

- [Bug 55739](https://bz.apache.org/bugzilla/show_bug.cgi?id=55739) - Remote Test : Total threads in GUI mode shows invalid total number of threads

### Improvements

### HTTP Samplers and Proxy

### Other samplers

- [Bug 55589](https://bz.apache.org/bugzilla/show_bug.cgi?id=55589) - JMS Publisher/Point to Point : Add ability to set typed values in JMS header properties.

### Controllers

- [Bug 55854](https://bz.apache.org/bugzilla/show_bug.cgi?id=55854) - Transaction Controller:Change default property "Include duration of timer&hellip;" for newly created element

### Listeners

- [Bug 55610](https://bz.apache.org/bugzilla/show_bug.cgi?id=55610) - View Results Tree : Add an XPath Tester

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 55908](https://bz.apache.org/bugzilla/show_bug.cgi?id=55908) - Response assertion : Change Pattern Matching Rules default to Substring on creation for better performances

- [Bug 54977](https://bz.apache.org/bugzilla/show_bug.cgi?id=54977) - Ability to choose the client alias for the cert key in JsseSslManager such that Mutual SSL auth testing can be made more flexible. Contributed by UBIK Load Pack (support at ubikloadpack.com)

### Functions

### I18N

### General

- [Bug 55693](https://bz.apache.org/bugzilla/show_bug.cgi?id=55693) - Add a "Save as Test Fragment" option

- [Bug 55753](https://bz.apache.org/bugzilla/show_bug.cgi?id=55753) - Improve FilePanel behaviour to start from the value set in Filename field if any. Contributed by UBIK Load Pack (support at ubikloadpack.com)

- [Bug 55756](https://bz.apache.org/bugzilla/show_bug.cgi?id=55756) - HTTP Mirror Server : Add ability to set Headers

- [Bug 55852](https://bz.apache.org/bugzilla/show_bug.cgi?id=55852) - Be more lenient in parsing when charset value is surrounded with single quotes

- [Bug 55857](https://bz.apache.org/bugzilla/show_bug.cgi?id=55857) - Performance : AbstractProperty should test for emptiness to avoid Exception throwing

- [Bug 55858](https://bz.apache.org/bugzilla/show_bug.cgi?id=55858) - Startup Performance : On Startup, BeanInfoSupport should test for key availability instead of throwing

- [Bug 55865](https://bz.apache.org/bugzilla/show_bug.cgi?id=55865) - Performance :Disable stale check by default in HttpClient 4 and 3.1

- [Bug 55512](https://bz.apache.org/bugzilla/show_bug.cgi?id=55512) - Summariser should be enabled by default in Non GUI mode

### Non-functional changes

- Updated to rsyntaxtextarea-2.5.1.jar (from 2.5.0)

- Updated to jodd-core-3.4.9.jar from (3.4.8) and jodd-lagarto-3.4.9.jar (from 3.4.9)

- Updated to jsoup-1.7.3.jar (from 1.7.2)

- Updated to mail-1.5.0-b01 (from 1.4.4)

- Updated to mongo-java-driver-2.11.3 (from 2.11.2)

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

- James Liang (jliang at andera.com)

- UBIK Load Pack (support at ubikloadpack.com)

We also thank bug reporters who helped us improve JMeter. 
For this release we want to give special thanks to the following reporters for the clear reports and tests made after our fixes:

- John Natsioulas (john_natsioulas at yahoo.com.au)

- Antonio Gomes Rodrigues (ra0077 at gmail.com)

Apologies if we have omitted anyone else.

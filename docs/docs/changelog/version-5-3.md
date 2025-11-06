---
title: Version 5.3
---

# Version 5.3

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
-->
UX improvements
Added [Darklaf](https://github.com/weisJ/darklaf) look and feel that improves several components.

Tree indentation level is easier to follow:

JMeter tree with Darklaf Darcula theme
JMeter tree with Darklaf IntelliJ theme

New look and feel themes. Light: IntellJ, Solarized Light, HighContrast Light.
    Dark: OneDark, Solarized Dark, HighContrast Dark.

When an element in tree is disabled, all its descendants are shown in gray.
For instance, `While Contoller` is disabled in the following tree, so its children
are gray. It is purely a UI change, and the behavior is not altered.

While controller is disabled, so its children are gray

Tree context menu is shown even in case the node selection is changed. Previously
    the popup did disappear and it was required to select a node first and only then launch popup.

Look and feel can now be updated without a restart

Use CTRLALTwheel for zooming
    fonts. Previous shortcut was CTRLSHIFTwheel,
    however, it conflicted with horizontal scrolling.

In-app zoom is more consistent (e.g. sometimes not all the labels or even panels were scaled).
For instance: log viewer, JSR223 code editor were not previously scaled with zoom-in/out feature

Tree context menu is shown for the full row, not for the label only

Undo and redo support for editable fields. Keystrokes are CTRLZ /
    CTRLSHIFTZ, or
    CMDZ/
    CMDSHIFTZ depending on the operating system.
Undo is implemented on a field level basis (each fields has its own history), and the history is
invalidated when tree selection changes.

Mark the currently selected language in the options menu.

Mark the currently selected log level in the options menu.

Rework of many Test Element UI (JUnit Request, ForEach Controller, If Controller, Throughput Controller, WhileController,
Counter Config, XPath2 Extractor, Function Helper Dialog, Search popup, JMS Elements)

Functions
-->

### Incompatible changes

    - Default value of `httpclient4.time_to_live` has been modified from `2000` to `60000`,
    this means HTTP connections will live longer than before. This
    has impact on connection creation and SSL handshake, see [Bug 64289](https://bz.apache.org/bugzilla/show_bug.cgi?id=64289)

    - The update to Groovy 3 ([PR #590](https://github.com/apache/jmeter/pull/590)) might break some old Groovy code of your tests. Have a look
    at [the update notes for Groovy 3](https://groovy-lang.org/releasenotes/groovy-3.0.html)

### Improvements

### HTTP Samplers and Test Script Recorder

    - 64160Test HTTP/S Test Script Recorder: Name transaction controller/ simple controller using prefix without "`-XXXX`" suffix

    - 64289Make `httpclient4.time_to_live` to `60000` to be closer to typical browser behavior

### Other samplers

    - 64288JUnit Request: Improve UX

    - 64407Improve JMS Publisher UX. Contributed by Ubik Load Pack (https://ubikloadpack.com)

    - 64408Improve JMS Subscriber UX. Contributed by Ubik Load Pack (https://ubikloadpack.com)

### Controllers

    - 64277ForEach Controller: Improve UX

    - 64280If Controller: Improve UX

    - 64282Throughput Controller: Improve UX

    - 64287WhileController: Improve UX

### Listeners

    - 64150View Results Tree: Allow editing of response data in testers

    - 63822View Results Tree: Keep position of split pane while switching renderer mode

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 64091Precise Throughput Timer schedule generation is improved significantly (e.g. 2 seconds for 10M samples)

    - 64281Counter Config: Improve UX

    - 64283XPath2 Extractor: Improve UX

### Functions

  - 64070`_timeshift` function does not work with offset formatters

  - 64275Function Helper Dialog: Improve UX

### I18N

  - 64102Add Chinese translation for Tools menu. Contributed by Liu XP (liu_xp2003 at sina.com)

### Report / Dashboard

  - 64380Add a '`Median`' field to the dashboard and make the response time percentile fields support floating-point numbers. Contributed by Keith Mo(https://github.com/keithmork)

  - 64378HTML report generation should not fail if a plugin has registered a graph and is not more present in classpath, issue a warning instead

### General

  - 63458[PR #551](https://github.com/apache/jmeter/pull/551)Add new template "Functional Testing Test Plan [01]". Contributed by Sebastian Boga (sebastian.boga at endava.com)

  - 64119Use first renderer from `view.results.tree.renderers_order` property as default in View Results Tree

  - 64148Use gray icons for disabled elements in the tree, display subtree as gray

  - 64198Allow spaces in `${...}` expressions around functions.

  - 64276Search popup: Improve UX

  - [PR #573](https://github.com/apache/jmeter/pull/573)Improve the startup time: skip test plan UI initialization

  - [PR #585](https://github.com/apache/jmeter/pull/585)Added JEXL3 as a syntax alias for JSyntaxTextArea. Contributed by drivera-armedia (https://github.com/drivera-armedia)

  - [PR #590](https://github.com/apache/jmeter/pull/590)Update Groovy to 3.0.3.

### Non-functional changes

    - Build system upgraded from Gradle to 6.3 (from 6.1), Java 14 can be used now for the build

    - 63963[PR #546](https://github.com/apache/jmeter/pull/546)Updated jackson to 2.10.3 (from 2.9.10)

    - 64120Updated jsoup to 1.13.1 (from 1.12.1)

    - 63809Updated commons-dbcp2 to 2.7.0 (from 2.5.0)

    - Updated Apache ActiveMQ to 5.15.11 (from 5.15.8)

    - Updated bouncycastle to 1.64 (from 1.60)

    - Updated asm to 7.3.1 (from 7.1)

    - Updated Apache commons-codec to 1.14 (from 1.13)

    - Updated Apache commons-pool to 2.8.0 (from 2.7.0)

    - Updated equalsverifier to 3.1.9 (from 3.1.12)

    - Updated Apache Groovy to 2.4.18 (from 2.4.16)

    - Updated hsqldb to 2.5.0 (from 2.4.1)

    - Updated hamcrest to 2.2 (from 2.1)

    - Updated Apache httpclient and httpmime to 4.5.12 (from 4.5.10)

    - Updated Apache httpcore and httpcore-nio to 4.4.13 (from 4.4.12)

    - Updated Apache Tika to 1.24.1 (from 1.22)

    - Updated jmespath to 0.5.0 (from 0.3.0)

    - Updated Apache log4j to 2.13.1 (from 2.12.1)

    - Updated junit4 to 4.13 (from 4.12)

    - Updated junit5 to 5.6.0 (from 5.5.1)

    - Updated slf4j to 1.7.30 (from 1.7.28)

    - Updated ph-commons to 9.4.1 (from 9.3.7)

    - Updated ph-css to 6.2.2 (from 6.2.0)

    - Updated rsyntaxtextarea to 3.1.0 (from 3.0.4)

    - Updated rhino to 1.7.12 (from 1.7.11)

    - Updated SaxonHE to 9.9.1-7 (from 9.9.1-5)

    - Updated cglib to 3.2.12 (from 3.2.9)

    - Updated commons-lang3 to 3.10 (from 3.9)

    - Updated freemarker to 2.3.30 (from 2.3.29)

    - Updated hamcrest-date to 2.0.7 (from 2.0.4)

    - Updated equalsverifier to 3.1.13 (from 3.1.12)

    - Updated xstream to 1.4.11.1 (from 1.4.11)

    - [PR #559](https://github.com/apache/jmeter/pull/559)Add a note to the source of TrustAllSSLSocketFactory, that it is not secure to trust everyone. Based on a PR from YYTVicky (yytvicky at github)

    - [PR #588](https://github.com/apache/jmeter/pull/588)Add documentation on usage of InfluxDB v2 for real-time results. Based on PR from Jakub Bednář (jakub.bednar at gmail.com)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 64400Make sorting recorded samples into transaction controllers more predictable

    - 64267When preemptive auth is disabled HTTP Sampler does not automatically respond to Basic Auth challenge

### Other Samplers

### Controllers

### Listeners

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 64091Precise Throughput Timer might produce less samples when low test duration is used

    - 64142Presence of DebugPostProcessor in Test plan breaks ActiveThread Over time in report due to missing information

    - 64196Recurse into sub samplers more deeply when checking assertions

    - 64196Recurse into sampleResults for `AbstractScopedTestElement#getSampleList`

    - 64381PreciseThroughputTimer: On termination, log message contains negative value

### Functions

### I18N

### Report / Dashboard

    - 64059Response Time Percentiles Over Time, unable to change the percentiles

### Documentation

  - [PR #547](https://github.com/apache/jmeter/pull/547)Correct Log level documentation. Contributed by jmetertea

  - [PR #548](https://github.com/apache/jmeter/pull/548)Correct typos in documentation. Contributed by jmetertea

  - 64022Correct Chinese translation for "Ignore Sub-Controller blocks". Provided by yangxiaofei77 (yangxiaofei77 at gmail.com)

  - [PR #552](https://github.com/apache/jmeter/pull/552)Fix `client.rmi.localport` port allocation description. Contributed by anant-93

  - [PR #543](https://github.com/apache/jmeter/pull/543)Clarify documentation of `__StringToFile` function regarding default value of `Append to file?` parameter. Contributed by Ori Marko

  - 64302Correct links to JMeter API in printable docs and BeanShell best practices and to JavaFX implementation website in all docs.
     Reported by 2477441814 (2477441814 at qq.com)

### General

  - 63945NPE when opening a file after file system change

  - 64034Shell scripts fail if space in `JAVA_HOME` path. Contributed by ray7219 (ray7219 at hotmail.com)

  - 63856Set `connectTime` on parent samples when using a transaction controller

  - 64227Error when loading Templates on Windows

  - TestPlan UI: skip adding the entry to the classpath if the user clicks cancel

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - [Jannis Weis](https://github.com/weisJ/darklaf)

  - Stefan Seide (stefan at trilobyte-se.de)

  - jmetertea

  - ray7219

  - Sebastian Boga (sebastian.boga at endava.com)

  - Liu XP (liu_xp2003 at sina.com)

  - anant-93 (https://github.com/anant-93)

  - Ori Marko (orimarko at gmail.com)

  - Keith Mo(https://github.com/keithmork)

  - drivera-armedia (https://github.com/drivera-armedia)

  - Ubik Load Pack

  - Jakub Bednář (jakub.bednar at gmail.com)

We also thank bug reporters who helped us improve JMeter.

  - Michael McDermott (mcdermott.michaelj at gmail.com)

  - yangxiaofei77 (yangxiaofei77 at gmail.com)

  - Markus Wolf (wolfm at t-systems.com)

  - Pierre Astruc (pierre.astruc at evertest.com)

  - YYTVicky (yytvicky at github)

  - 2477441814 at qq.com

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

---
title: Version 4.0
---

# Version 4.0

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

Sample category -->
Sample title -->
 -->
Core improvements
JMeter now supports JAVA 9.

New [`Boundary Extractor`](usermanual/component_reference.html#Boundary_Extractor) element available which provides easy extraction with better performances

New [`JSON Assertion`](usermanual/component_reference.html#JSON_Assertion) element available to assert on JSON responses.

New [`Precise Throughput Timer`](usermanual/component_reference.html#Precise_Throughput_Timer) element available which produces Poisson arrivals with given constant throughput.

JMS Point-to-Point sampler has been enhanced with `read`, `browse`, `clear` options.

Best property values are now selected on many Test Elements to ensure best practices are the defaults:
    
        - Newly added `If Controller` now uses by default Expression which is the most performing option.
        
        

        - Newly added JSR223 Test Element now cache compiled script by default if language used provides this feature.
        

    

[`Loop controller`](usermanual/component_reference.html#Loop_Controller) and
 [`ForEach Controller`](usermanual/component_reference.html#ForEach_Controller)
 now expose their current iteration as a variable named `__jm__&lt;Name of your element&gt;__idx` that
 you can use like this for example for a Loop Controller named `MyLoopController`:
 ```
${__jm__MyLoopController__idx}
```
.
 See [Bug 61802](https://bz.apache.org/bugzilla/show_bug.cgi?id=61802)

Cookies are now shown in View Results Tree during recording. They were previously always shown as empty.

[`Response Assertion`](usermanual/component_reference.html#Response_Assertion) now allows you to customize assertion message and assert on Request Data.

UX improvements
JMeter now uses [Darcula LAF](https://github.com/bulenkov/Darcula) by default

Workbench has been dropped from UI, you can now use Non Test Elements as immediate children of Test Plan.

Menu UX have been improved to make most used elements available more rapidly.

HTTP(S) Test Script Recorder now allows you to name your transactions while recording in a more human readable way.

UX improvements made on, among the most notable :

    - Module Controller informs user at least one Controller is required

    - Function Helper Dialog (The wizard that helps using and testing functions) has been improved in many fields.
    

    - Switch Controller trims text to avoid issues when a space is introduced before/after name

    - Test Plan is now saved before running the test plan

Functions
New Function [`__digest`](usermanual/functions.html#__digest) provides easy computing of SHA-XXX, MDX hashes:
```
${__digest(MD5,Apache JMeter 4.0 rocks !,,,)}
```
 will return `0e16c3ce9b6c9971c69ad685fd875d2b`

New Function [`__dateTimeConvert`](usermanual/functions.html#__dateTimeConvert) provides easy conversion between date formats:
```
${__dateTimeConvert(01 Jan 2017,dd MMM yyyy,dd/MM/yyyy,)}
```
 will return `01/01/2017`

New Function [`__changeCase`](usermanual/functions.html#__changeCase) provides ability to switch to Upper / Lower / Capitalized cases
```
${__changeCase(Avaro omnia desunt\, inopi pauca\, sapienti nihil,UPPER,)}
```
 will return `AVARO OMNIA DESUNT, INOPI PAUCA, SAPIENTI NIHIL`

New Functions [`__isVarDefined`](usermanual/functions.html#__isVarDefined)
and [`__isPropDefined`](usermanual/functions.html#__isPropDefined) provide testing of properties and variables availability
```
${__isPropDefined(START.HMS)}
```
 will return `true`
```
${__isVarDefined(JMeterThread.last_sample_ok)}
```
 will return `true`

Scripting and Plugin Development
You can now call `SampleResult#setIgnore()` if you don't want your sampler to be visible in results

`JavaSamplerContext` used by `AbstractJavaSamplerClient` has been enhanced with new methods to easy plugin development.

JMeter now distributes additional Maven sources and javadoc artifacts into [Maven repository](https://repo1.maven.org/maven2/org/apache/jmeter/ApacheJMeter_core/4.0/)

Plugins can now register listeners to be notified when a Test Plan is opened/closed

Live Reporting and Web Report
InfluxDB backend listener now allows you to add custom tags by adding them with prefix `TAG_`, see [Bug 61794](https://bz.apache.org/bugzilla/show_bug.cgi?id=61794)

In Web Report responseTime distribution graph is more precise

Some bugfixes have been made on report generation, see [Bug 61900](https://bz.apache.org/bugzilla/show_bug.cgi?id=61900), [Bug 61900](https://bz.apache.org/bugzilla/show_bug.cgi?id=61900)61956, [Bug 61899](https://bz.apache.org/bugzilla/show_bug.cgi?id=61899).
Graphs *Latency Vs Request* and *Response Time Vs Request* did not exceed 1000 RPS due to [Bug 61962](https://bz.apache.org/bugzilla/show_bug.cgi?id=61962)

Configuration of JMeter environment
JVM settings for the JMeter start scripts can be placed in a separate file (`bin/setenv.sh` on Unix
and `bin\setenv.bat` on Windows), that gets called on startup. The startup script
itself does not have to be edited anymore.

### Incompatible changes

    - `Start time` and `End date` of Thread Group have been removed, see [Bug 61549](https://bz.apache.org/bugzilla/show_bug.cgi?id=61549)

    - In distributed testing, mode `Hold` has been removed. Use alternative and more efficient modes

    - For 3rd party plugins, the following method in `org.apache.jmeter.gui.tree.JMeterTreeNode` has been dropped for migration to Java 9 ([Bug 61529](https://bz.apache.org/bugzilla/show_bug.cgi?id=61529))
    ```
public Enumeration&lt;JMeterTreeNode&gt; children()
```

    

    - `tearDown Thread Group` will now run on stop and shutdown of a test by default. If you don't want this behaviour,
    uncheck `Run tearDown Thread Groups after shutdown of main threads` on `Test Plan` element, see [Bug 61656](https://bz.apache.org/bugzilla/show_bug.cgi?id=61656)

    - Properties `sampleresult.getbytes.headers_size` and `sampleresult.getbytes.body_real_size` have been dropped, see [Bug 61587](https://bz.apache.org/bugzilla/show_bug.cgi?id=61587)

    - JMeter will now save your test plan whenever you run it. This behaviour can be controlled by property `save_automatically_before_run`, see [Bug 61731](https://bz.apache.org/bugzilla/show_bug.cgi?id=61731)

    - Workbench element has been dropped, you now directly add `Non Test Element` as children of Test Plan. When loading a Test Plan that contains the element
    JMeter will move the `Mirror Server`, `Property Display` and HTTP(s) `Test Script Recorder` elements as direct children of Test Plan. For
    any other element, it will create a `Test Fragment` element called `Workbench Test Fragment and move the elements in it`.

    - Following classes have been dropped (`org.apache.jmeter.functions.util.ArgumentEncoder`, `org.apache.jmeter.functions.util.ArgumentDecoder`), see [PR #335](https://github.com/apache/jmeter/pull/335)

    - In JMS Point-to-Point sampler, setting timeout to 0 will now mean infinite timeout while previously it would be switched to 2000 ms, see [Bug 61829](https://bz.apache.org/bugzilla/show_bug.cgi?id=61829)

    - When Assertions are at different scopes, they are executed starting with the most OUTER one to the most INNER one. See [Bug 61846](https://bz.apache.org/bugzilla/show_bug.cgi?id=61846)

    - JMeter now starts by default using English locale. This change is due to missing translations in many supported languages.
        You can change locale by modifying in jmeter and jmeter.bat (or preferably setenv.sh/setenv.bat) the `JVM_ARGS` system property values.
        We'd also be very grateful if you can contribute translations in supported languages.
    

    - SwitchController now trims by default the content of switch to avoid issue related to unwanted spaces. See [Bug 61771](https://bz.apache.org/bugzilla/show_bug.cgi?id=61771)

    - JMeter JVM heap settings have changed from `-Xms512m -Xmx512m` to `-Xms1g -Xmx1g`

    - Beanshell version has been upgraded to bsh-2.0b6 which introduces breaking changes and more strict parsing rules

### Improvements

### HTTP Samplers and Test Script Recorder

    - [PR #316](https://github.com/apache/jmeter/pull/316)Warn about empty truststore loading. Contributed by Vincent Herilier (https://github.com/vherilier)

    - 61639HTTP(S) Test Script Recorder: In request filtering tab, uncheck by default "Notify Child Listeners of filtered samplers"

    - 61672HTTP(S) Test Script Recorder: Have the ability to choose the sampler name while keeping the ability to just add a prefix

    - 53957HTTP Request: In Parameters tab, allow pasting of content coming from Firefox and Chrome (unparsed)

    - 61587Drop properties `sampleresult.getbytes.headers_size` and `sampleresult.getbytes.body_real_size`

    - 61843HTTP(S) Test Script Recorder: Add SAN to JMeter generated CA Certificate. Contributed by Matthew Buckett

    - 61901Support for `https.cipherSuites` System property. Contributed by Jeremy Arnold (jeremy at arnoldzoo.org)

### Other samplers

    - 61544JMS Point-to-Point Sampler: Enhance communication styles with read, browse, clear. Based on a contribution by Benny van Wijngaarden (benny at smaragd-it.nl)

    - 61829JMS Point-to-Point: If Receive Queue is empty and a timeout is set, it is not taken into account. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61739Java Request / JavaSamplerClient: Improve `org.apache.jmeter.protocol.java.sampler.JavaSamplerContext`

    - 61762Start Next Thread Loop should be used everywhere

### Controllers

    - 61675If Controller: Use expression by default and add a warning when the other mode is used. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61770Module Controller: Inform user in UI that he needs to have at least one Controller in his plan. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61771SwitchController: Switch field should be trimmed by safety

### Listeners

    - 57760View Results Tree: Cookie Header is wrongly shown as empty (no cookies) when viewing a recorder Sample Result. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61769View Results Tree: Use syntax highlighter in XPath Tester, JSON Path Tester and CSS/JQuery Tester. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61776View Results Tree: Expansion of `Add expand/collapse all` menu in render XML view. Contributed by Maxime Chassagneux and Graham Russell

    - 61852View Results Tree: Add a Boundary Extractor Tester

    - 61794Influxdb backend: Add as many custom tags as wanted by just create new lines and prefix theirs name by "`TAG_`" on the GUI backend listener

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 60213New component: Boundary based extractor

    - 61845New Component JSON Assertion based on AtlanBH JSON Path Assertion donated to JMeter-Plugins and migrated into JMeter core by Artem Fedorov (artem at blazemeter.com)

    - 61931New Component: Precise Throughput Timer, timer that produces Poisson arrivals with given constant throughput. Contributed by Vladimir Sitnikov (sitnikov.vladimir at gmail.com)

    - 61644HTTP Cache Manager: "Use Cache-Control/Expires header when processing GET requests" should be checked by default

    - 61645Response Assertion: Add ability to assert on Request Data

    - 51140Response Assertion: add ability to set a specific error/failure message that is later shown in the Assertion Result. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61534Convert AssertionError to a failed assertion, allowing users to use assert in their code. Fixing a regression introduced in 3.2

    - 61756Extractors: Improve label name "Reference name" to make it clear what it makes

    - 61758`Apply to:` field in Extractors, Assertions: When entering a value in `JMeter Variable Name`, the radio box `JMeter Variable Name` should be selected by default. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61846Scoped Assertion should follow same order of evaluation as Post Processors

### Functions

    - 61724Add `__digest` function to provide computing of Hashes (SHA-XXX, MDX). Based on a contribution by orimarko at gmail.com

    - 61735Add `__dateTimeConvert` function to provide date formats conversions. Based on a contribution by orimarko at gmail.com

    - 61760Add `__isPropDefined` and `__isVarDefined` functions to know if property or variable exist. Contributed by orimarko at gmail.com

    - 61759Add `__changeCase` function to change different cases of a string. Based on a contribution by orimarko at gmail.com

    - 61561Function helper dialog should display exception in result

    - 61738Function Helper Dialog: Add Copy in Generate and clarify labels. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 62027Help: Introduce property `help.local` to allow choosing between local (offline) documentation and online documentation

    - 61593Remove Detail, Add, Add from Clipboard, Delete buttons in Function Helper GUI

### I18N

    - 61606Translate button `Browse&hellip;` in some elements (which use FileEditor class)

    - 61747HTTP(S) Test Script Recorder: add the missing doc to "Create transaction after request (ms)"

### Report / Dashboard

    - 61871Reduce jmeter.reportgenerator.graph.responseTimeDistribution.property.set_granularity default value from 500ms to 100ms

    - 61879Remove useless files in HTML report template

### General

    - 61591Drop Workbench from test tree. Implemented by Artem Fedorov (artem at blazemeter.com) and contributed by BlazeMeter Ltd.

    - 61549Thread Group: Remove start and end date

    - 61529Migration to Java 9. Partly contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61709SampleResult: Add a method `setIgnore()` to make JMeter ignore the SampleResult and not send it to listeners

    - 61806Allow to use `SampleResult#setIgnore()` in post-processors and assertions script (JSR223 elements)

    - 61607Add browse button in all BeanShell elements to select BeanShell script

    - 61627Don't clear LogView anymore when clicking on Warning/Errors Indicator

    - 61629Add Think Times to Children menu should not consider disabled elements

    - 61655SampleSender: Drop HoldSampleSender implementation

    - 61656`tearDown Thread Group` should run by default at stop or shutdown of test

    - 61659`JMeterVariables#get()` should apply `toString()` on non string objects

    - 61555Metaspace should be restricted as default

    - 61693JMeter aware of Docker (`-XX:+UnlockExperimentalVMOptions` `-XX:+UseCGroupMemoryLimitForHeap`)

    - 61694Add `-server` option in `jmeter.bat`

    - 61697Introduce Darcula Look And Feel to make JMeter UI more attractive

    - 61704Toolbar: Improve a bit the right part

    - 61731Enhance Test plan Backup with option to save before run. Based on a contribution by orimarko at gmail.com

    - 61640JSR223 Test Elements: Enable by default caching. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61785Add HelpUseful links to create issues and download nightly build

    - 61808Fix main frame position. Implemented by Artem Fedorov (artem at blazemeter.com) and contributed by BlazeMeter Ltd.

    - 61802Loop / ForEach Controller should expose a variable for current iteration. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - [PR #349](https://github.com/apache/jmeter/pull/349)Add i18n resources(zh_CN). Contributed by Helly Guo (https://github.com/hellyguo)

    - [PR #351](https://github.com/apache/jmeter/pull/351)Fixed about dialog position on first view. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #352](https://github.com/apache/jmeter/pull/352)Menu bar - added mnemonics to more menu items. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #353](https://github.com/apache/jmeter/pull/353)Re-wrote some existing tests in Spock. Contributed by Graham Russell (graham at ham1.co.uk)

    - 61919UX: Reorder Menus. Contributed by Graham Russell (graham at ham1.co.uk)

    - 61920Plugins: Add ability to listen to Test Plan loading/closing. Contributed by Peter Doornbosch (https://bitbucket.org/pjtr/)

    - 61935Plugins: Let GUI component (dynamically) decide whether it can be added via the menu or not. Contributed by Peter Doornbosch (https://bitbucket.org/pjtr/)

    - 61969When changing LAF through GUI, user should be informed that it is better to restart

    - 61970JMeter now uses English as default locale to avoid missing translations in some locales make UI look weird

    - 56368Create and Deploy source artifacts to Maven central

    - 61973Create and Deploy javadoc artifacts to Maven central

    - [PR #371](https://github.com/apache/jmeter/pull/371)Fix example in documentation for . Contributed by Konstantin Kalinin (kkalinin at hotmail.com)

    - 62039Distributed testing: Provide ability to use SSL

### Non-functional changes

    - Updated to bsh-2.0b6 (from bsh-2.0b5)

    - Updated to groovy-all-2.4.13 (from groovy-all-2.4.12)

    - Updated to rhino-1.7.7.2 (from rhino-1.7.7.1)

    - Updated to tika-core and tika-parsers 1.17 (from 1.16)

    - Updated to commons-dbcp2-2.2.0 (from 2.1.1)

    - Updated to caffeine 2.6.1 (from 2.5.5)

    - Updated to commons-codec-1.11 (from 1.10)

    - Updated to commons-io-2.6 (from 2.5)

    - Updated to commons-lang3-3.7 (from 3.6)

    - Updated to commons-pool2-2.5.0 (from 2.4.2)

    - Updated to asm-6.0 (from 5.2)

    - Updated to jsoup-1.11.2 (from 1.10.3)

    - Updated to cglib-nodep-3.2.6 (from 3.2.5)

    - Updated to ph-css 6.0.0 (from 5.0.4)

    - Updated to ph-commons 9.0.0 (from 8.6.6)

    - Updated to log4j2 2.10.0 (from 2.8.2)

    - Updated to httpcore 4.4.9 (from 4.4.7)

    - Updated to httpclient 4.5.5 (from 4.5.3)

    - Updated to jodd 4.1.4 (from 3.8.6)

    - 61642Improve FTP test coverage

    - 61641Improve JMS test coverage

    - 61651Improve TCP test coverage

    - 61651Improve OS test coverage. Partly contributed by Aleksei Balan (abalanonline at gmail.com)

    - [PR #319](https://github.com/apache/jmeter/pull/319)Removed commented out code. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #322](https://github.com/apache/jmeter/pull/322)General JavaDoc cleanup. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #323](https://github.com/apache/jmeter/pull/323)Extracted method and used streams to improve readability. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #324](https://github.com/apache/jmeter/pull/324)Save backup refactor. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #327](https://github.com/apache/jmeter/pull/327)Utilising more modern Java, simplifying code and formatting code and comments. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #332](https://github.com/apache/jmeter/pull/332)Add the spock framework for groovy unit tests. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #334](https://github.com/apache/jmeter/pull/334)Enable running of JUnit tests from within IntelliJ with default config. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #335](https://github.com/apache/jmeter/pull/335)Removed `functions.util.*` as they don't seem to be used (for many years). Contributed by Graham Russell (graham at ham1.co.uk)

    - 61867[PR #345](https://github.com/apache/jmeter/pull/345)Updated to latest checkstyle (v8.5), Added many more rules to checkstyle, Included checking of test files and more file types. Contributed by Graham Russell (graham at ham1.co.uk)

    - [PR #350](https://github.com/apache/jmeter/pull/350)Parallelised unit tests. Contributed by Graham Russell (graham at ham1.co.uk)

    - 61966Setup Test Results Analyzer in jenkins

    - [PR #343](https://github.com/apache/jmeter/pull/343)Reduce the size of some images in the documentation. Contributed by Graham Russell (graham at ham1.co.uk)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 61569JMS Point-to-Point Test Plan: Synchronization issue when putting reply. Contributed by Igor Panainte (panainte.i at gmail.com)

### Other Samplers

    - 61698Test Action: It stop is selected, samplers following Test Action can run

    - 61707Test Action: Target is ignored when pause is selected, so it should be disabled

    - 61827JMSPublisher: Don't add new line at the end of the file. Contributed by Graham Russell (graham at ham1.co.uk)

### Controllers

    - 61556Clarify in documentation performance impacts of `${}` var usage in IfController and groovy. Contributed by Justin McCartney (be_strew at yahoo.co.uk)

    - 61713Test Fragment has option to Change Controller and Insert Parent. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61965Module and Include Controller should not allow to add meaningless elements in their context.

    - 62062ThroughputController: StackOverFlowError triggered when throughput=0 (Total Executions or Percentage Executions) Partly implemented by Artem Fedorov (artem.fedorov at blazemeter.com) and contributed by BlazeMeter Ltd.

### Listeners

    - 61742BackendListener: fix default value for `backend_graphite.send_interval`

    - 61878BackendListener: NPE if BackendListenerClient#getDefaultParameters returns null

    - 61950View Results Tree: Content-Type `audio/mpegurl` is wrongly considered as binary

### Timers, Assertions, Config, Pre- &amp; Post-Processors

    - 61716Header Manager: When pasting Headers from Firefox or Chrome spaces are introduced as first character of value

### Functions

    - 61588Better log message for  function

    - 61619In Function Helper Dialog, the 1st function doesn't display default parameters

    - 61628If split string has empty separator default separator is not used

    - 61752`__RandomDate`: Function does not allow missing last parameter used for variable name

### I18N

### Report / Dashboard

    - 61807Web Report: fix error in `getTop5ErrorMetrics`. Contributed by Graham Russell (graham at ham1.co.uk)

    - 61900Report Generator: Report generation fails if separator is a regex reserved char like `|`

    - 61925CsvSampleReader does not increment row in nextSample(). Contributed by Graham Russell (graham at ham1.co.uk)

    - 61956Report Generation: `-f` of `-forceDeleteResultFile` option does not work. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61899Report Generation: When `jmeter.save.saveservice.print_field_names` is false and `sample_variables` are set report generation fails. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 61962Latency Vs Request and Response Time Vs Request graphs do not exceed 1000 RPS. Contributed by Ubik Load Pack (support at ubikloadpack.com)

### General

    - 61661Avoid startup/shutdown problems due to 3rd party Thread Listener plugins throwing RuntimeException

    - 61625File Editor used in BeanInfo behaves strangely under all LAFs with impact on CSVDataSet, JSR223, BSF, Beanshell Element

    - 61844Maven pom.xml: Libraries used in testing should have scope test

    - 61842Saving with no changes causes a save and duplicate, identical backup file

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

    - Igor Panainte (panainte.i at gmail.com)

    - Emilian Bold (emi at apache.org)

    - [Ubik Load Pack](https://ubikloadpack.com)

    - Justin McCartney (be_strew at yahoo.co.uk)

    - Vincent Herilier (https://github.com/vherilier)

    - Aleksei Balan (abalanonline at gmail.com)

    - Graham Russell (graham at ham1.co.uk)

    - orimarko at gmail.com

    - Artem Fedorov (artem at blazemeter.com)

    - [BlazeMeter Ltd](https://www.blazemeter.com)

    - Benny van Wijngaarden (benny at smaragd-it.nl)

    - Matthew Buckett (https://github.com/buckett)

    - Helly Guo (https://github.com/hellyguo)

    - Peter Doornbosch (https://bitbucket.org/pjtr/)

    - Jeremy Arnold (jeremy at arnoldzoo.org)

    - Vladimir Sitnikov (sitnikov.vladimir at gmail.com)

    - Konstantin Kalinin (kkalinin at hotmail.com)

We also thank bug reporters who helped us improve JMeter. 
For this release we want to give special thanks to the following reporters for the clear reports and tests made after our fixes:

    - user7294900 on Stackoverflow (orimarko at gmail.com)

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

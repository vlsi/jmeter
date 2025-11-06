---
title: Version 3.0
---

# Version 3.0

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Known bugs](#Known bugs)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Thanks](#Thanks)

### New and Noteworthy

Test plan creation and debugging improvements
New Search Feature in View Results Tree to allow searching for text / regexp in Request/Responses/Headers/Cookies/&hellip; This will ease correlation and Test plans creation

New JSON Post Processor to better extract data from JSON content using user friendly JSON-PATH syntax
JSON is now a first class citizen in JMeter with the introduction of a new [JSONPath](http://goessner.net/articles/JsonPath/" target="_blank) post processor.
This post processor is very similar to Regular Expression Post Processor but is well suited for JSON code.
It is based on Jayway JSON Path library 

New validation feature, in one click run a selection of Thread Groups with `1` user, no pause and `1` iteration

JSR223 Test Elements do not require a Cache Compilation Key anymore
Just check `Cache compiled script if available` checkbox and the elements (Pre-Processor, Post-Processor, Assertions, Listeners, &hellip;)
will pre-compile the script and cache the compiled code if the underlying language supports it

Nashorn can now be used as Javascript engine providing better performance and easier usage

To enable Nashorn, you need to set in `user.properties`:

```
javascript.use_rhino=false
```

Nashorn can be used with Java 8 in the following elements:

- IfController

- JSR223 Test elements with `javascript` language selected

- `__javaScript` function

Jexl3 has been integrated. It provides new scripting features and much better documentation

[JEXL3](http://commons.apache.org/proper/commons-jexl/" target="_blank) can now be used thanks to a new function `__jexl3`.
JEXL is a language very similar to JSTL.

Simplified HTTP Request UI
A new "`Advanced`" tab has been added to HTTP Request to simplify configuration. The file upload feature has been moved into a dedicated tab.
This increases the space available for parameters in UI and simplifies the UX.

HTTP Request Defaults improvements
You can now configure Source Address (IP Spoofing like feature) and "`Save response as MD5 hash`" in Advanced Tab

Reporting improvements

New Reporting Feature generating dynamic Graphs in HTML pages (APDEX, Summary report and Graphs)
A dynamic HTML report can now be generated either at the end of a load test or from a result file whenever you want.
See [Generating dashboard](./usermanual/generating-dashboard.html" target="_blank) for more details.
This report provides the following metrics:

    - [APDEX](https://en.wikipedia.org/wiki/Apdex" target="_blank) (Application Performance Index) table that computes the APDEX based on configurable values for tolerated and satisfied thresholds

    - A request summary graph showing the Success and failed transaction percentage: 

    - A Statistics table providing in one table a summary of all metrics per transaction including 3 configurable percentiles : 

    - An error table providing a summary of all errors and their proportion in the total requests : 

    - Zoomable chart where you can check/uncheck every transaction to show/hide it for:
        
            - Response times Over Time : 

            - Bytes throughput Over Time : 

            - Latencies Over Time : 

            - Hits per second : 

            - Response codes per second : 

            - Transactions per second : 

            - Response Time vs Request per second : 

            - Latency vs Request per second : 

            - Response times percentiles : 

            - Active Threads Over Time : 

            - Times vs Threads : 

            - Response Time Distribution : 

        

    

GraphiteBackendListener has a new Server Hits metric
Summariser displays a more readable duration

Now duration are display in the format `hours:minutes:seconds`

```

Generate Summary Results +      1 in 00:00:01 =    1.7/s Avg:     1 Min:     1 Max:     1 Err:     0 (0.00%) Active: 1 Started: 1 Finished: 0
Generate Summary Results +    138 in 00:00:09 =   16.2/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%) Active: 9 Started: 9 Finished: 0
Generate Summary Results =    139 in 00:00:09 =   15.3/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%)
Generate Summary Results +    467 in 00:00:10 =   47.0/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%) Active: 19 Started: 19 Finished: 0
Generate Summary Results =    606 in 00:00:19 =   31.9/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%)
&vellip;
Generate Summary Results +   1662 in 00:00:10 =  166.1/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%) Active: 50 Started: 50 Finished: 0
Generate Summary Results =  28932 in 00:03:19 =  145.4/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%)
Generate Summary Results +   1664 in 00:00:10 =  166.4/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%) Active: 50 Started: 50 Finished: 0
Generate Summary Results =  30596 in 00:03:29 =  146.4/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%)
Generate Summary Results +   1661 in 00:00:10 =  166.1/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%) Active: 50 Started: 50 Finished: 0
Generate Summary Results =  32257 in 00:03:39 =  147.3/s Avg:     0 Min:     0 Max:     1 Err:     0 (0.00%)

```

BackendListener now allows you to define sampler list as a regular expression
You can now use a regular expression to select the samplers you want to filter.
Use parameter: `useRegexpForSamplersList=true` and put a regex in parameter `samplersList`

Protocols and Load Testing improvements
Migration to HttpClient 4.5.2 has been started. Although not completely finished, it improves many areas in JMeter
Migration to HttpClient 4.5.2 improves the following fields of JMeter:

- Support of recent RFC like [HTTP State Management Mechanism RFC-6265 for Cookies](https://tools.ietf.org/html/rfc6265" target="_blank), you should use now `HC4CookieHandler` in HTTP Cookie Manager component and select `standard` Cookie policy

- [Server Name Indication (SNI)](https://en.wikipedia.org/wiki/Server_Name_Indication" target="_blank) support for HttpClient4 implementation

- Improved and better performing validation mechanism for Stale connections and Keep-Alive management, see properties `httpclient4.validate_after_inactivity` and `httpclient4.time_to_live`

- Many bug fixes since previous version 4.2.6 used in JMeter 2.13, see [HttpClient 4.5.X release notes](http://www.apache.org/dist/httpcomponents/httpclient/RELEASE_NOTES-4.5.x.txt" target="_blank)

- Better support of HTTP RFC 2616 / RFC 7230 and fixes to issues with `deflate` compression management

Parallel Downloads is now realistic and scales much better:

- Parsing of CSS imported files (through `@import`) or embedded resources (background, images, &hellip;)

- Lazy initialization of SSL context: For 15 Threads 138% more sampling in 5 minutes for HTTP only tests. Gain increases as number of threads increases

- Rework of Connection management for Parallel Download: This better simulates current browser behaviour and improves throughput. For 15 Threads 135% extra samples in 5 minutes.

- Reuse of Threads used for Parallel downloads through a ThreadPool: This improves throughput and increases JMeter scalability for such tests

- Total of 750% more throughput found on test with 15 threads, the more threads you have the more the gain

- You can now compute and store just the MD5 of embedded resources instead of storing the entire response, this can be done by setting the property `httpsampler.embedded_resources_use_md5=true`

Introduction of Sample Timeout feature
This new  Pre-Processor allows you to apply a Timeout on the elements that are in its scope.
In the screenshot below the 10 second timeout applies to the `Debug Sampler` and `HTTP Request` elements.

JDBC request now uses DBCP2 pool
JDBC Request and JDBC Connection Configuration have been updated to replace old Excalibur Pool by Apache Commons DBCP2 pool. As a consequence properties have been migrated to equivalent
when available and UI has been updated.
Note that unlike Excalibur, Commons DBCP uses the validation query when creating the pool.
So make sure the query is valid.
The default query suits many databases, but not all - for example Oracle requires '`SELECT 1 FROM DUAL`' or similar.

UX Improvements:

Better display in HiDPI screens
See [JMeter with a HiDPI screen on Linux or Windows](usermanual/hints_and_tips.html#hidpi) in Hints and Tips section in user manual

New Icon look and Logo
JMeter has a new Logo created by Felix Schumacher. Icons have also been refreshed to give a more modern style and make them more meaningful

Lots of fixes of annoying little bugs
Around 40 UI fixes have been made to either fix buggy, confusing behaviour or simplify usage by not allowing incompatible options to be selected

Improved Thread Group UI and related actions (`Start`, `Start No Timers`, `Validate`)

Creating and testing a Test Plan before Load Test has been much simplified by allowing you to only start a selection of Thread Group, start them without applying Timers (thus gaining time)
or start them using a new Validation mode. This validation mode allows you to start a Thread Group (without modifying it) with 1 thread, 1 iteration and without applying timers.
This validation mode can be customized.

New shortcuts

- Add most used elements
    (
        Ctrl
        0
    
    &hellip;
    
        Ctrl
        9
    ),
    configurable through `gui.quick_*XXX*` properties

- Shortcuts to expand nodes

Core improvements

Configuration simplification with better defaults
Default values for many properties have been modified to make JMeter configuration optimal Out of the box. Read "Incompatible changes" section for more details.

Apache Groovy bundled with JMeter
[Apache Groovy](http://www.groovy-lang.org/" target="_blank), the well-known JVM scripting language, is now bundled with Apache JMeter in lib folder.
This allows you to use it immediately through JSR223 Elements by selecting the Groovy language.

Superfluous and old properties removed
Old properties that existed to maintain backward compatibility or to offer some superfluous customization have been removed.
Read "Incompatible changes" section to see which properties have been removed.

Code and documentation improvements

- Migration to Java7 source code and use of its syntactic sugar

- Major code cleanups

- Full review of documentation and improvement both in content and presentation

Improvements to unit tests

- Migration of many tests to JUnit 4

- Better management of Headless tests

- More Unit Tests

Dependencies refresh

Deprecated Libraries dropped or replaced by up to date ones:

- Excalibur replaced by commons-dbcp

- htmllexer, htmlparser removed

- soap removed

- jdom removed

Slf4j can now be used within Plugins and core code
You can now use [SLF4J](http://www.slf4j.org/" target="_blank) logging wrapper in your custom plugins or `org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient` subclasses.

### Incompatible changes

    - Since version 3.0, Groovy-2.4.6 is bundled with JMeter (`lib` folder), ensure you remove old version or referenced versions through properties `search_paths` or `user.classpath`

    - Since version 3.0, `jmeter.save.saveservice.assertion_results_failure_message` property value is true, meaning CSV file for results will contain an additional column containing assertion result response message, see [Bug 58978](https://bz.apache.org/bugzilla/show_bug.cgi?id=58978)

    - Since version 3.0, `jmeter.save.saveservice.print_field_names` property value is true, meaning CSV file for results will contain field names as first line in CSV, see [Bug 58991](https://bz.apache.org/bugzilla/show_bug.cgi?id=58991)

    - Since version 3.0, `jmeter.save.saveservice.idle_time` property value is true, meaning CSV/XML result files will contain an additional column containing idle time between samplers, see [Bug 57182](https://bz.apache.org/bugzilla/show_bug.cgi?id=57182)

    - In RandomTimer class, protected instance `timer` field has been replaced by `getTimer()` protected method, this is related to [Bug 58100](https://bz.apache.org/bugzilla/show_bug.cgi?id=58100). This may impact 3rd party plugins.

    - Since version 3.0, you can use Nashorn Engine (default javascript engine is Rhino) under Java8 for Elements that use Javascript Engine (`__javaScript`, `IfController`). If you want to use it, use property `javascript.use_rhino=false`, see [Bug 58406](https://bz.apache.org/bugzilla/show_bug.cgi?id=58406).
    Note: in a future version, we will switch to Nashorn by default. Users are encouraged to report any issue related to using Nashorn instead of Rhino.
    

    - Since version 3.0, JMS Publisher will reload contents of file if Message source is "`From File`" and the "`Filename`" field changes (e.g. if it uses a variable that has changed)

    - `org.apache.jmeter.gui.util.ButtonPanel` has been removed, if you use it in your 3rd party plugin or custom development ensure you update your code. See [Bug 58687](https://bz.apache.org/bugzilla/show_bug.cgi?id=58687)

    - Property `jmeterthread.startearlier` has been removed. See [Bug 58726](https://bz.apache.org/bugzilla/show_bug.cgi?id=58726)

    - Property `jmeterengine.startlistenerslater` has been removed. See [Bug 58728](https://bz.apache.org/bugzilla/show_bug.cgi?id=58728)

    - Property `jmeterthread.reversePostProcessors` has been removed. See [Bug 58728](https://bz.apache.org/bugzilla/show_bug.cgi?id=58728)

    - Property `jmeter.toolbar.display` has been removed, the toolbar is now always displayed. See [Bug 59236](https://bz.apache.org/bugzilla/show_bug.cgi?id=59236)

    - Property `jmeter.errorscounter.display` has been removed, the errors/warnings counter is now always displayed. See [Bug 59236](https://bz.apache.org/bugzilla/show_bug.cgi?id=59236)

    - Property `xml.parser` has been removed, it is not used anymore as `org.apache.jmeter.util.JMeterUtils#getXMLParser` has been deprecated and is not used either. See [Bug 59236](https://bz.apache.org/bugzilla/show_bug.cgi?id=59236)

    - Summariser listener now shows the duration in the format `HH:mm:ss` (Hour:Minute:Second), it previously showed the duration in seconds. See [Bug 58776](https://bz.apache.org/bugzilla/show_bug.cgi?id=58776)

    - `org.apache.jmeter.protocol.http.visualizers.RequestViewHTTP.getQueryMap` signature has changed, if you use it ensure you update your code. See [Bug 58845](https://bz.apache.org/bugzilla/show_bug.cgi?id=58845)

    - JMS Subscriber will consider a sample to be an error if the number of received messages is not equal to expected number of messages. It previously considered a sample OK if at least 1 message was received. See [Bug 58980](https://bz.apache.org/bugzilla/show_bug.cgi?id=58980)

    - Since version 3.0, HTTP(S) Test Script recorder defaults to using port `8888` (as configured when using Recording Template). See [Bug 59006](https://bz.apache.org/bugzilla/show_bug.cgi?id=59006)

    - Since version 3.0, the parser for embedded resources (replaced since 2.10 by Lagarto based implementation) which relied on the htmlparser library (HtmlParserHTMLParser) has been dropped along with its dependencies.

    - Since version 3.0, support for reading old Avalon format JTL (result) files has been removed, see [Bug 59064](https://bz.apache.org/bugzilla/show_bug.cgi?id=59064)

    - Since version 3.0, the default property value for `http.java.sampler.retries` has been changed to `0` (no retry by default) to align it with the behaviour of HttpClient4.
    Note also that its meaning has changed: before 3.0, `http.java.sampler.retries=1` meant `No Retry` (i.e. total tries = 1), since 3.0 `http.java.sampler.retries=1` means `1` retry.
    (Note: this only applies to the Java HTTP Sampler)
    See [Bug 59103](https://bz.apache.org/bugzilla/show_bug.cgi?id=59103)

    - Since 3.0, the following deprecated classes have been dropped
    
        - org.apache.jmeter.protocol.http.modifier.UserParameterXMLContentHandler

        - org.apache.jmeter.protocol.http.modifier.UserParameterXMLErrorHandler

        - org.apache.jmeter.protocol.http.modifier.UserParameterXMLParser

    

    

    - `httpsampler.await_termination_timeout` has been replaced by `httpsampler.parallel_download_thread_keepalive_inseconds` which is now the keep alive time for the parallel download threads (in seconds).

    - JDBC Request has been updated to use commons-dbcp2, since then the behaviour is slightly different, ensure you have a correct "Validation Query" for your database. See [Bug 58786](https://bz.apache.org/bugzilla/show_bug.cgi?id=58786)

    - The following jars have been removed:
    
        - excalibur-datasource-2.1.jar (see [Bug 59156](https://bz.apache.org/bugzilla/show_bug.cgi?id=59156))

        - excalibur-instrument-1.0.jar (see [Bug 58786](https://bz.apache.org/bugzilla/show_bug.cgi?id=58786))

        - excalibur-pool-api-2.1.jar (see [Bug 58786](https://bz.apache.org/bugzilla/show_bug.cgi?id=58786))

        - excalibur-pool-impl-2.1.jar (see [Bug 58786](https://bz.apache.org/bugzilla/show_bug.cgi?id=58786))

        - excalibur-pool-instrumented-2.1.jar (see [Bug 58786](https://bz.apache.org/bugzilla/show_bug.cgi?id=58786))

        - htmllexer-2.1.jar (see [Bug 59037](https://bz.apache.org/bugzilla/show_bug.cgi?id=59037))

        - htmlparser-2.1.jar (see [Bug 59037](https://bz.apache.org/bugzilla/show_bug.cgi?id=59037))

        - soap-2.3.1.jar

        - jdom-1.1.3.jar (see [Bug 59156](https://bz.apache.org/bugzilla/show_bug.cgi?id=59156))

    

    

    - Maximum number of redirects allowed by JMeter is now 20, it was previously 5. This can be changed with the property `httpsampler.max_redirects`. See [Bug 59382](https://bz.apache.org/bugzilla/show_bug.cgi?id=59382)

### Deprecated and removed elements

    - MongoDB elements (MongoDB Source Config, MongoDB Script) have been deprecated and will be removed in the next version of JMeter. They do not appear anymore in the menu, if you need them modify `not_in_menu` property. The JMeter team advises not to use them anymore. See [Bug 58772](https://bz.apache.org/bugzilla/show_bug.cgi?id=58772)

    - WebService(SOAP) Request and HTML Parameter Mask which were deprecated in 2.13 version, have now been removed following our [deprecation strategy](./usermanual/best-practices.html#deprecation).
    Classes and properties which were only used by those elements have been dropped:
    
        - `org.apache.jmeter.protocol.http.util.DOMPool`

        - `org.apache.jmeter.protocol.http.util.WSDLException`

        - `org.apache.jmeter.protocol.http.util.WSDLHelper`

        - Property `soap.document_cache`

        - JAR soap-2.3.1 has been also removed

    

    

    - `__jexl` function (i.e. JEXL 1) has been deprecated and will be removed in next version. See [Bug 58903](https://bz.apache.org/bugzilla/show_bug.cgi?id=58903)

    - Spline Visualizer listener and Distribution Graph listener have been deprecated and will be removed in the next version of JMeter. They do not appear anymore in the menu, if you need them modify `not_in_menu` property. JMeter team advises not to use them anymore. See [Bug 58791](https://bz.apache.org/bugzilla/show_bug.cgi?id=58791)

### Improvements

### HTTP Samplers and Test Script Recorder

    - 57696HTTP Request : Improve responseMessage when resource download fails. Contributed by Ubik Load Pack (support at ubikloadpack.com)

    - 57995Use FileServer for HTTP Request files. Implemented by Andrey Pokhilko (andrey at blazemeter.com) and contributed by BlazeMeter Ltd.

    - 58843Improve the usable space in the HTTP sampler GUI. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58852Use less memory for `PUT` requests. The uploaded data will no longer be stored in the Sampler.
        This is the same behaviour as with `POST` requests.

    - 58860HTTP Request : Add automatic variable generation in HTTP parameters table by right click. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58923normalize URIs when downloading embedded resources.

    - 59005HTTP Sampler : Added WebDAV verb (`SEARCH`).

    - 59006Change Default proxy recording port to `8888` to align it with Recording Template. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

    - 58099Performance : Lazily initialize HttpClient SSL Context to avoid its initialization even for HTTP only scenarios

    - 57577HttpSampler : Retrieve All Embedded Resources, add property "`httpsampler.embedded_resources_use_md5`" to only compute md5 and not keep response data. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59023HttpSampler UI : rework the embedded resources labels and change default number of parallel downloads to `6`. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59028Use `SystemDefaultDnsResolver` singleton. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59036FormCharSetFinder : Use JSoup instead of deprecated HTMLParser

    - 59034Parallel downloads connection management is not realistic. Contributed by Benoit Wiart (benoit dot wiart at gmail.com) and Philippe Mouawad

    - 59060HTTP Request GUI : Move File Upload to a new Tab to have more space for parameters and prevent incompatible configuration. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59103HTTP Request Java Implementation: Change default "`http.java.sampler.retries`" to align it on HttpClient behaviour and make the name meaningful

    - 59083HTTP Request : Make Method field editable so that additional methods (WebDAV) can be added easily

    - 59118Add comment in recorded think time by proxy recorder. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

    - 59116Add the possibility to setup a prefix to sampler name recorded by proxy. Partly based on a patch by Antonio Gomes Rodrigues (ra0077 at gmail.com)

    - 59129HTTP Request : Simplify GUI with simple/advanced Tabs

    - 59033Parallel Download : Rework Parser classes hierarchy to allow plug-in parsers for different mime types

    - 52073Embedded Resources Parallel download : Improve performances by avoiding shutdown of ThreadPoolExecutor at each sample. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59190HTTP(S) Test Script Recorder : Suggested excludes should ignore case. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

    - 59140Parallel Download : Add CSS Parsing to extract links from CSS files

    - 59249Http Request Defaults : Add "`Source address`" and "`Save responses as MD5`"

    - 59382More realistic default value for `httpsampler.max_redirects`

### Other samplers

    - 57928Add ability to define protocol (http/https) to AccessLogSampler GUI. Contributed by Jérémie Lesage (jeremie.lesage at jeci.fr)

    - 58300Make existing Java Samplers implement Interruptible

    - 58160JMS Publisher : reload file content if file name changes. Based partly on a patch contributed by Maxime Chassagneux (maxime.chassagneux at gmail.com)

    - 58786JDBC Sampler : Replace Excalibur DataSource by more up to date library commons-dbcp2

    - 59205TCP Sampler: Set connect time in sampler when connection is established.

    - 59381JMSPublisher : FileChooserDialog filter does not work for browser buttons. Based partly on a patch contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

### Controllers

    - 58406IfController : Allow use of Nashorn Engine if available for JavaScript evaluation

    - 58281RandomOrderController : Improve randomization algorithm performance. Contributed by Graham Russell (jmeter at ham1.co.uk)

    - 58675Module controller : error message can easily be missed. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58673Module controller : when the target element is disabled the default jtree icons are displayed. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58674Module controller : it should not be possible to select more than one node in the tree. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58680Module Controller : ui enhancement. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58989Record controller gui : add a button to clear all the recorded samples. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

### Listeners

- 58041Tree View Listener should show sample data type

- 58122GraphiteBackendListener : Add Server Hits metric. Partly based on a patch from Amol Moye (amol.moye at thomsonreuters.com)

- 58681GraphiteBackendListener : Don't send data if no sampling occurred

- 58776Summariser should display a more readable duration

- 58791Deprecate listeners: Distribution Graph (alpha) and Spline Visualizer

- 58849View Results Tree : Add a search panel to the request http view to be able to search in the parameters table. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58857View Results Tree : the request view http does not allow to resize the parameters table first column. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58955Request view http does not correctly display http parameters in multipart/form-data. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 55597View Results Tree: Add a search feature to search in recorded samplers

- 59102View Results Tree: Better default value for "`view.results.tree.max_size`"

- 59099Backend listener : Add the possibility to consider samplersList as a Regular Expression. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

- 59424Visualizer : Add "Clear" in popup menu

### Timers, Assertions, Config, Pre- &amp; Post-Processors

  - 58303Change usage of bouncycastle api in SMIMEAssertion to get rid of deprecation warnings.

  - 58515New JSON related components : JSON-PATH Extractor and JSON-PATH Renderer in View Results Tree. Donated by Ubik Load Pack (support at ubikloadpack.com).

  - 58698Correct parsing of auth-files in HTTP Authorization Manager.

  - 58756CookieManager : Cookie Policy select box content must depend on Cookie implementation.

  - 56358Cookie manager supports cross port cookies and RFC6265. Thanks to Oleg Kalnichevski (olegk at apache.org)

  - 58773TestCacheManager : Add tests for CacheManager that use HttpClient 4

  - 58742CompareAssertion : Reset data in TableEditor when switching between different CompareAssertions in gui.
      Based on a patch by Vincent Herilier (vherilier at gmail.com)

  - 59108TableEditor: Allow rows to be moved up and down. Contributed by Vincent Herilier (vherilier at gmail.com)

  - 58848Argument Panel : when adding an argument (add button or from clipboard) scroll the table to the new line. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

  - 58865Allow empty default value in the Regular Expression Extractor. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

  - 59156XMLAssertion : drop jdom dependency by using XMLReader

  - 59328Better tooltip for Variable Names in CSVDataSet. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

### Functions

    - 58477 __javaScript function : Allow use of Nashorn engine for Java8 and later versions

    - 58903Provide __jexl3 function that uses commons-jexl3 and deprecated __jexl (1.1) function

### I18N

### General

- 58736Add Sample Timeout support

- 57913Automated backups of last saved JMX files. Contributed by Benoit Vatan (benoit.vatan at gmail.com)

- 57988Shortcuts (Ctrl1 &hellip;
    Ctrl9) to quickly add elements into test plan.
    Implemented by Andrey Pokhilko (andrey at blazemeter.com) and contributed by BlazeMeter Ltd.

- 58100Performance enhancements : Replace Random by ThreadLocalRandom.

- 58677`TestSaveService#testLoadAndSave` use the wrong set of files. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58689Add shortcuts to expand / collapse a part of the tree. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58696Create Ant task to setup Eclipse project

- 58653New JMeter Dashboard/Report with Dynamic Graphs, Tables to help analyzing load test results. Developed by Ubik-Ingenierie and contributed by Decathlon S.A. and Ubik-Ingenierie / UbikLoadPack

- 58699Workbench changes neither saved nor prompted for saving upon close. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58728Drop old behavioural properties

- 57319Upgrade to HttpClient 4.5.2. With the big help from Oleg Kalnichevski (olegk at apache.org) and Gary Gregory (ggregory at apache.org).

- 58772Deprecate MongoDB related elements

- 58782ThreadGroup : Improve ergonomy

- 58165Show the time elapsed since the start of the load test in GUI mode. Partly based on a contribution from Maxime Chassagneux (maxime.chassagneux at gmail.com)

- 58814JVM no longer recognizes option `MaxLiveObjectEvacuationRatio`; remove from comments

- 58810Config Element Counter (and others): Check Boxes Toggle Area Too Big

- 56554JSR223 Test Element : Generate compilation cache key automatically. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58911Header Manager : it should be possible to copy/paste between Header Managers. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58864Arguments Panel : when moving parameter with up / down, ensure that the selection remains visible. Based on a contribution by Benoit Wiart (benoit dot wiart at gmail.com)

- 58968Add a new template to allow to record script with think time included. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

- 58978Settings defaults : Switch "`jmeter.save.saveservice.assertion_results_failure_message`" to true (after 2.13)

- 58991Settings defaults : Switch "`jmeter.save.saveservice.print_field_names`" to true (after 2.13)

- 57182Settings defaults : Switch "`jmeter.save.saveservice.idle_time`" to true (after 2.13)

- 58870TableEditor: minimum size is too small. Contributed by Vincent Herilier (vherilier at gmail.com)

- 58933JSyntaxTextArea : Ability to set font.  Contributed by Denis Kirpichenkov (denis.kirpichenkov at gmail.com)

- 58793Create developers page explaining how to build and contribute

- 59046JMeter Gui Replace controller should keep the name and the selection. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59038Deprecate HTTPClient 3.1 related elements

- 59094Drop support of old JMX file format

- 59082Remove the "`TestCompiler.useStaticSet`" parameter. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59093Option parsing error message can be '*lost*'

- 58715Feature request: Bundle `groovy-all` with JMeter

- 58426Improve display of JMeter on high resolution devices (HiDPI) (part 1 of enhancement)

- 59105TableEditor : Add ability to paste rows from clipboard and delete multiple selection. Contributed by Vincent Herilier (vherilier at gmail.com)

- 59197Thread Group : it should be possible to only run a single threadgroup or a selection of threadgroups with a popup menu. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59207Change the font color of `errorsOrFatalsLabel` to red when an error occurs. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

- 58941Create a new Starter that runs thread groups in validation mode (`1` thread only, `1` iteration, no pause all customizable)

- 59236JMeter Properties : Make some cleanup

- 59240Introduce a slf4j adapter for Logkit (this allows using slf4j within plugins and core code)

- 59153Stop test if CSVDataSet is accessing non-existing file. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

- 59320Better tooltip in GUI with GenericTestBeanCustomizer (CSV Data Set Config, JDBC Connection Configuration, Keystore Configuration, &hellip;) . Based on a patch by Antonio Gomes Rodrigues (ra0077 at gmail.com)

- 59171Sample Result SaveConfig Dialog is generated in random order

- 59425Display error about missing help page inside the help pane

### Non-functional changes

- Updated to httpclient, httpmime 4.5.2 (from 4.2.6)

- Updated to tika-core and tika-parsers 1.12 (from 1.7)

- Updated to commons-math3 3.6.1 (from 3.4.1)

- Updated to commons-pool2 2.4.2 (from 2.3)

- Updated to commons-lang 3.4 (from 3.3.2)

- Updated to rhino-1.7.7.1 (from 1.7R5)

- Updated to jodd-3.6.7.jar (from 3.6.4)

- Updated to jsoup-1.8.3 (from 1.8.1)

- Updated to rsyntaxtextarea-2.5.8 (from 2.5.6)

- Updated to slf4j-1.7.12 (from 1.7.10)

- Updated to xmlgraphics-commons-2.0.1 (from 1.5)

- Updated to commons-collections-3.2.2 (from 3.2.1)

- Updated to commons-net 3.4 (from 3.3)

- Updated to slf4j 1.7.13 (from 1.7.12)

- 57981Require a minimum of Java 7. Partly contributed by Graham Russell (jmeter at ham1.co.uk)

- 58684JMeterColor does not need to extend `java.awt.Color`. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58687ButtonPanel should die. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58705Make `org.apache.jmeter.testelement.property.MultiProperty` iterable. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58729Cleanup extras folder for maintainability

- 57110Fixed spelling+grammar, formatting, removed commented out code etc. Contributed by Graham Russell (jmeter at ham1.co.uk)

- Correct instructions on running JMeter in `help.txt`. Contributed by Pascal Schumacher (pascalschumacher at gmx.net)

- 58704Non regression testing : Ant task batchtest fails if tests and run in a non `en_EN` locale and use a JMX file that uses a CSV DataSet

- 58897Improve JUnit Test code. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58949Cleanup of LDAP code. Based on a patch by Benoit Wiart (benoit dot wiart at gmail.com)

- 58897Improve JUnit Test code. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58967Use JUnit categories to exclude tests that need a gui. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59003`ClutilTestCase` `testSingleArg8` and `testSingleArg9` are identical

- 59064Remove OldSaveService which supported very old Avalon format JTL (result) files

- 59165RSyntaxTextArea not compatible with headless testing

- 59021Use `Double#compare` instead of reimplementing it in `NumberProperty#compareTo`

- 59037Drop HtmlParserHTMLParser and dependencies on htmlparser and htmllexer

- 58465JMS Read response field is badly named and documented

- 58601Change check for modification of `saveservice.properties` from `SVN Revision ID` to sha1 sum of the file itself.

- 58726Remove the `jmeterthread.startearlier` parameter. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58784Make `JMeterUtils#runSafe` sync/async awt invocation configurable and change the visualizers to use the async version.

- 58790Issue in CheckDirty and its relation to ActionRouter

- 59095Remove UserParameterXMLParser that was deprecated eight years ago. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59262Add list of binary jars to LICENSE; use that for unit tests

- 59353Add "Deprecated and removed elements" in "Incompatible changes" part in changes.xml. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

    - 57806"`audio/x-mpegurl`" mime type is erroneously considered as binary by ViewResultsTree. Contributed by Ubik Load Pack (support at ubikloadpack.com).

    - 57858Don't call `sampleEnd` twice in HTTPHC4Impl when a `RuntimeException` or an `IOException` occurs in the sample method.

    - 57921HTTP/1.1 without keep-alive "`Connection`" response header no longer uses infinite keep-alive.

    - 57956The `hc.parameters` reference in `jmeter.properties` doesn't work when JMeter is not started in `bin`.

    - 58137JMeter fails to download embedded URLs that contain illegal characters in URL (it does not escape them).

    - 58201Make usage of port in the host header more consistent across the different http samplers.

    - 58453HTTP Test Script Recorder : `NullPointerException` when disabling Capture HTTP Headers 

    - 57804HTTP Request doesn't reuse cached SSL context when using Client Certificates in HTTPS (only fixed for HttpClient4 implementation)

    - 58800`proxy.pause` default value: fix documentation

    - 58844Buttons enable / disable is broken in the arguments panel. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58861When clicking on up, down or detail while in a cell of the argument panel, newly added content is lost. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 57935SSL SNI extension not supported by HttpClient 4.2.6

    - 59044Http Sampler : It should not be possible to select the multipart encoding if the method is not `POST`. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59008Http Sampler: Infinite recursion SampleResult on frame depth limit reached

    - 58881HTTP Request : HTTPHC4Impl shows exception when server uses "`deflate`" compression

    - 58583HTTP client fails to close connection if server misbehaves by not sending "`connection: close`", violating HTTP RFC 2616 / RFC 7230

    - 58950`NoHttpResponseException` when Pause between samplers exceeds keepalive sent by server

    - 59085Http file panel : data lost on browse cancellation. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 56141Application does not behave correctly when using HTTP Recorder. With the help of Dan (java.junkee at yahoo.com)

    - 59079"`httpsampler.max_redirects`" property is not enforced when "`Redirect Automatically`" is used

    - 58811When pasting arguments between http samplers the column "Encode" and "Include Equals" are lost. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

### Other Samplers

    - 58013Enable all protocols that are enabled on the default SSLContext for usage with the SMTP Sampler.

    - 58209JMeter hang when testing javasampler because `HashMap.put()` is called from multiple threads without sync.

    - 58301Use typed methods such as `setInt`, `setDouble`, `setDate`, &hellip; for prepared statement #27

    - 58851Add a dependency on hamcrest-core to allow JUnit tests with annotations to work

    - 58947Connect metric is wrong when `ConnectException` occurs

    - 58980JMS Subscriber will return successful as long as 1 message is received. Contributed by Harrison Termotto (harrison dot termotto at stonybrook.edu)

    - 59075JMS Publisher: `NumberFormatException` is thrown if priority or expiration field is empty

    - 59345SMTPSampler connection leak. Based on a patch by Luca Maragnani (luca dot maragnani at gmail dot com)

### Controllers

    - 58600Display correct filenames, when they are searched by IncludeController

    - 58678Module Controller : limit target element selection. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58714Module controller : it should not be possible to add a timer as child. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59067JMeter fails to iterate over Controllers that are children of a TransactionController having "`Generate parent sample`" checked after an assertion error occurs on a Thread Group with "`Start Next Thread Loop`". Contributed by Benoit Wiart(benoit dot wiart at gmail.com)

    - 59076Test should fail if a module controller cannot find its replacement subtree

### Listeners

- 58033SampleResultConverter should note that it cannot record non-TEXT data

- 58845Request http view doesn't display all the parameters. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 58413ViewResultsTree : Request HTTP Renderer does not show correctly parameters that contain ampersand (&amp;). Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

- 59172SampleResult SaveConfig does not allow some fields to be disabled

- 58329Response Time Graph and Aggregate Graph : Save graph to file does not take into account the settings changed since last click on Graph. Contributed by David Coppens (d.l.coppens at gmail.com)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- 58079Do not cache HTTP samples that have a `Vary` header when using a HTTP CacheManager.

- 58912Response assertion gui : Deleting more than 1 selected row deletes only one row. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

### Functions

- 57825__Random function fails if `min` value is equal to `max` value (regression related to [Bug 54453](https://bz.apache.org/bugzilla/show_bug.cgi?id=54453))

### I18N

### General

    - 54826Don't fail on long strings in JSON responses when displaying them as JSON in View Results Tree.

    - 57734Maven transient dependencies are incorrect for 2.13 (Fixed group ids for Commons Pool and Math)

    - 57731`TESTSTART.MS` has always the value of the first Test started in Server mode in NON GUI Distributed testing

    - 58016 Error type casting using external SSL Provider. Contributed by Kirill Yankov (myworkpostbox at gmail.com)

    - 58293SOAP/XML-RPC Sampler file browser generates NullPointerException

    - 58685JDatefield : Make the modification of the date with up/down arrow work. Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 58693Fix "Cannot nest output folder 'jmeter/build/components' inside output folder 'jmeter/build'" when setting up eclipse

    - 58781Command line option "`-?`" shows Unknown option

    - 57821Command-line option "`-X --remoteexit`" doesn't work since 2.13 (regression related to [Bug 57500](https://bz.apache.org/bugzilla/show_bug.cgi?id=57500))

    - 58795NPE may occur in `GuiPackage#getTestElementCheckSum` with some 3rd party plugins

    - 58913When closing JMeter should not interpret cancel as "*destroy my test plan*". Contributed by Benoit Wiart (benoit dot wiart at gmail.com)

    - 59096Search Feature : Case insensitive search is not really case insensitive

    - 59193`ant run_gui` fails with  `ClassNotFoundException` or `IllegalAccessError` when accessing classes from dependencies not loaded through `Thread.currentThread().getContextClassLoader()`

    - 59225Bad display of running indicator icon. Contributed by Antonio Gomes Rodrigues (ra0077 at gmail.com)

    - 56927Disable language change during a test

    - 59391In Distributed mode, the client exits abnormally at the end of test

    - 59397`build.xml` does not make dist.executables executable on Unix systems

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

- [Ubik Load Pack](http://ubikloadpack.com)

- Benoit Vatan (benoit.vatan at gmail.com)

- Jérémie Lesage (jeremie.lesage at jeci.fr)

- Kirill Yankov (myworkpostbox at gmail.com)

- Amol Moye (amol.moye at thomsonreuters.com)

- Samoht-fr (https://github.com/Samoht-fr)

- Graham Russell (jmeter at ham1.co.uk)

- Maxime Chassagneux (maxime.chassagneux at gmail.com)

- Benoit Wiart (benoit.wiart at gmail.com)

- [Decathlon S.A.](http://www.decathlon.com)

- [Ubik-Ingenierie S.A.S.](http://www.ubik-ingenierie.com)

- Oleg Kalnichevski (olegk at apache.org)

- Pascal Schumacher (pascalschumacher at gmx.net)

- Vincent Herilier (vherilier at gmail.com)

- Florent Sabbe (f dot sabbe at ubik-ingenierie.com)

- Antonio Gomes Rodrigues (ra0077 at gmail.com)

- Harrison Termotto (harrison dot termotto at stonybrook.edu

- Denis Kirpichenkov (denis.kirpichenkov at gmail.com)

- Gary Gregory (ggregory at apache.org)

- David Coppens (d.l.coppens at gmail.com)

- Luca Maragnani (luca dot maragnani at gmail dot com)

- Philip Helger (http://www.helger.com) for his [CSS Parser](https://github.com/phax" target="_blank) and for taking into account our bug reports very rapidly

- Irek Pastusiak (the.automatic.tester at gmail.com)

We also thank bug reporters who helped us improve JMeter. 
For this release we want to give special thanks to the following reporters for the clear reports and tests made after our fixes:

- purnasatyap at gmail dot com  for the tests and reports on nightly build

- Sergey Batalin (sergey_batalin at mail dot ru) for the tests and reports on nightly build

- Vincent Daburon (vdaburon at gmail dot com) for the tests and reports on nightly build

Apologies if we have omitted anyone else.
 

 

### Known problems and workarounds

- The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

- 
The numbers that appear to the left of the green box are the number of active threads / total number of threads,
the total number of threads only applies to a locally run test, otherwise it will show `0` (see [Bug 55510](https://bz.apache.org/bugzilla/show_bug.cgi?id=55510)).

- 
Note that there is a [bug in Java](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6396599 )
on some Linux systems that manifests itself as the following error when running the test cases or JMeter itself:
```

 [java] WARNING: Couldn't flush user prefs:
 java.util.prefs.BackingStoreException:
 java.lang.IllegalArgumentException: Not supported: indent-number

```

This does not affect JMeter operation. This issue is fixed since Java 7b05.

- 
Note that under some windows systems you may have this WARNING:
```

java.util.prefs.WindowsPreferences
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0
x80000002. Windows RegCreateKeyEx(&hellip;) returned error code 5.

```

The fix is to run JMeter as Administrator, it will create the registry key for you, then you can restart JMeter as a normal user and you won't have the warning anymore.

- 
With Oracle Java 7 and Mac Book Pro Retina Display, the JMeter GUI may look blurry.
This is a known Java bug, see Bug JDK-8000629.
A workaround is to use a Java 7 update 40 runtime which fixes this issue.

- 
You may encounter the following error:
```
java.security.cert.CertificateException: Certificates does not conform to algorithm constraints
```

 if you run a HTTPS request on a web site with a SSL certificate (itself or one of SSL certificates in its chain of trust) with a signature
 algorithm using MD2 (like md2WithRSAEncryption) or with a SSL certificate with a size lower than 1024 bits.
This error is related to increased security in Java 7 version u16 (MD2) and version u40 (Certificate size lower than 1024 bits), and Java 8 too.

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
The fix is to use JDK7_u79, JDK8_u45 or later.

- 
View Results Tree may fail to display some HTML code under HTML renderer, see [Bug 54586](https://bz.apache.org/bugzilla/show_bug.cgi?id=54586).
This is due to a known Java bug which fails to parse "`px`" units in row/col attributes.
See Bug JDK-8031109
The fix is to use JDK9 b65 or later.

- 
JTable selection with keyboard (SHIFTup/down) is totally unusable with JAVA 7 on Mac OSX.
This is due to a known Java bug JDK-8025126
The fix is to use JDK 8 b132 or later.

---
title: Version 2.6
---

# Version 2.6

## New and Noteworthy

### Toolbar

A new toolbar on JMeter's main window

### JMeter start test button

A new menu option and button allow to start a test ignoring the Pause Timers

### JMeter GUI Look and Feel

Allow System or CrossPlatform LAF to be set from options menu

### JMeter GUI - duplicate node

Add "duplicate node" in context menu

### JMeter tree view - search facility

Functionality to search by keyword in Samplers Tree View

### HTTP Request - raw request pane

Improve HTTP Request GUI to better show parameters without name (GWT RPC request or SOAP request for example)

### HTTP Request - other changes

- Allow multiple selection in arguments panel

- Allow to add (paste) entries from the clipboard to an arguments list

- Ability to move variables up or down in HTTP Request

### HTTP Request - file protocol

Better support for file: protocol in HTTP sampler

Retrieve embedded resources with file: protocol

### HTTP Request - Ignore embedded resources failed

Enable "ignore failed" for embedded resources

Parent success with a embedded resource failed

### View Results in Table - child sample display

Add option to TableVisualiser to display child samples instead of parent

### Key Store - multiple certificates

Allowing multiple certificates (JKS)

### Aggregate graph improvements

Some improvements on Aggregate Graph Listener:
- new GUI for settings

- dynamic graph size

- allow to change fonts for title graph and legend

- allow to change bar color (background and text values)

- allow to draw or not bars outlines

- allow to select only some samplers by a regexp filter

- allow to define Y axis maximum scale

Aggregate Graph bar

### Counter - new reset option

Add an option to reset counter on each Thread Group iteration

### Functions

- Add a new function __RandomString to generate random Strings

- Add a new function __TestPlanName returning the name of the current "Test Plan"

- Add a new function __machineIP returning IP address

- Add a new function  __jexl2 to support Jexl2

### User Defined Variable improvements

- Add a comment field in User Defined Variables

- Allow to add (paste) entries from the clipboard to an arguments list

- Ability to move up or down variables in User Defined Variables

### View Results Tree

In View Results Tree rather than showing just a message if the results are to big, show as much of the result as are configured

### Controllers - change elements

Add ability to Change Controller elements

### JDBC pre- and post-processor

Add JDBC pre- and post-processor

### JDBC transaction isolation option

Allow to set the transaction isolation in the JDBC Connection Configuration

### Poisson Timer

Add a Poisson based timer

### GUI and OS interaction

Support for file Drag and Drop.

### Confirm Remove Dialog box

Add a dialog box to confirm removing the element(s) when Remove action is called

The dialogue can be skipped by setting the JMeter property `confirm.delete.skip=true`

### Remote batching support

Use external store to hold samples during distributed testing,
Added DiskStore remote sample sender: like Hold, but saves samples to disk until end of test

### JMS Subscriber sampler

With JMS Subscriber, ability to use Selectors

### New Logger Panel

A new Log Viewer  has been added to the GUI and can be enabled from menu Options &rarr; Log Viewer:

This Log Viewer shows the jmeter.log file, and useful (for example) to debug BeanShell/BSF scripts:

### The menu item Options / Choose Language is now fully functional

The menu item Options / Choose Language now changes all the displayed text to the new language provided
all messages are translated. You can help on this by translating into your language.

### Legacy JMX and JTL Avalon format support restored

Support for reading/writing the original Avalon XML format of JMX (script) and JTL (sample result) files was dropped in JMeter version 2.4.
JMeter can now read the Avalon format files again, however there is no support for saving files in the old format.

### JMeter jars available from Maven repository

JMeter jars are now available from Maven repository.

## Known bugs

The Include Controller has some problems in non-GUI mode (see Bugs 40671, 41286, 44973, 50898).
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

Listeners don't show iteration counts when a If Controller has a condition which is always false from the first iteration (see [Bug 52496](https://bz.apache.org/bugzilla/show_bug.cgi?id=52496)).
A workaround is to add a sampler at the same level as (or superior to) the If Controller.
For example a Test Action sampler with 0 wait time (which doesn't generate a sample),
or a Debug Sampler with all fields set to False (to reduce the sample size).

## Incompatible changes

JMeter versions since 2.1 failed to create a container sample when loading embedded resources.
This has been corrected; can still revert to the [Bug 51939](https://bz.apache.org/bugzilla/show_bug.cgi?id=51939) behaviour by setting the following property:
`httpsampler.separate.container=false`

Mirror server now uses default port 8081, was 8080 before 2.5.1.

TCP Sampler handles SocketTimeoutException, SocketException and InterruptedIOException differently since 2.6, when
these occurs, Sampler is marked as failed.

Sample Sender implementations now resolve their configuration on Client side since 2.6.
This behaviour can be changed with property sample_sender_client_configured (set it to false).

The HTTP User Parameter Modifier test element has been removed; it has been deprecated for a long time.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 51932](https://bz.apache.org/bugzilla/show_bug.cgi?id=51932) - CacheManager does not handle cache-control header with any attributes after max-age

- [Bug 51918](https://bz.apache.org/bugzilla/show_bug.cgi?id=51918) - GZIP compressed traffic produces errors, when multiple connections allowed

- [Bug 51939](https://bz.apache.org/bugzilla/show_bug.cgi?id=51939) - Should generate new parent sample if necessary when retrieving embedded resources

- [Bug 51942](https://bz.apache.org/bugzilla/show_bug.cgi?id=51942) - Synchronisation issue on CacheManager when Concurrent Download is used

- [Bug 51957](https://bz.apache.org/bugzilla/show_bug.cgi?id=51957) - Concurrent get can hang if a task does not complete

- [Bug 51925](https://bz.apache.org/bugzilla/show_bug.cgi?id=51925) - Calling Stop on Test leaks executor threads when concurrent download of resources is on

- [Bug 51980](https://bz.apache.org/bugzilla/show_bug.cgi?id=51980) - HtmlParserHTMLParser double-counts images used in links

- [Bug 52064](https://bz.apache.org/bugzilla/show_bug.cgi?id=52064) - OutOfMemory Risk in CacheManager

- [Bug 51919](https://bz.apache.org/bugzilla/show_bug.cgi?id=51919) - Random ConcurrentModificationException or NoSuchElementException in CookieManager#removeMatchingCookies when using Concurrent Download

- [Bug 52126](https://bz.apache.org/bugzilla/show_bug.cgi?id=52126) - HttpClient4 does not clear cookies between iterations

- [Bug 52129](https://bz.apache.org/bugzilla/show_bug.cgi?id=52129) - Reported Body Size is wrong when using HTTP Client 4 and Keep Alive connection

- [Bug 52137](https://bz.apache.org/bugzilla/show_bug.cgi?id=52137) - Problems with HTTP Cache Manager

- [Bug 52221](https://bz.apache.org/bugzilla/show_bug.cgi?id=52221) - Nullpointer Exception with use Retrieve Embedded Resource without HTTP Cache Manager

- [Bug 52310](https://bz.apache.org/bugzilla/show_bug.cgi?id=52310) - variable in IPSource failed HTTP request if "Concurrent Pool Size" is enabled

- [Bug 52371](https://bz.apache.org/bugzilla/show_bug.cgi?id=52371) - API Incompatibility - Methods in HTTPSampler2 now require PostMethod instead of HttpMethod[Base]. Reverted to original types.

- [Bug 49950](https://bz.apache.org/bugzilla/show_bug.cgi?id=49950) - Proxy : IndexOutOfBoundsException when recording with Proxy server

- [Bug 52409](https://bz.apache.org/bugzilla/show_bug.cgi?id=52409) - HttpSamplerBase#errorResult modifies sampleResult passed as parameter;
fix code which assumes that a new instance is created (i.e. when adding a sub-sample)

- [Bug 52507](https://bz.apache.org/bugzilla/show_bug.cgi?id=52507) - Delete Http User Parameters modifier (deprecated, obsolete)

### Other Samplers

- [Bug 51996](https://bz.apache.org/bugzilla/show_bug.cgi?id=51996) - JMS Initial Context leak newly created Context when Multiple Thread enter InitialContextFactory#lookupContext at the same time

- [Bug 51691](https://bz.apache.org/bugzilla/show_bug.cgi?id=51691) - Authorization does not work for JMS Publisher and JMS Subscriber

- [Bug 52036](https://bz.apache.org/bugzilla/show_bug.cgi?id=52036) - Durable Subscription fails with ActiveMQ due to missing clientId field

- [Bug 52044](https://bz.apache.org/bugzilla/show_bug.cgi?id=52044) - JMS Subscriber used with many threads leads to javax.naming.NamingException: Something already bound with ActiveMQ

- [Bug 52072](https://bz.apache.org/bugzilla/show_bug.cgi?id=52072) - LengthPrefixedBinaryTcpClientImpl may end a sample prematurely

- [Bug 52390](https://bz.apache.org/bugzilla/show_bug.cgi?id=52390) - AbstractJDBCTestElement:Memory leak and synchronization issue in perConnCache

### Controllers

- [Bug 51865](https://bz.apache.org/bugzilla/show_bug.cgi?id=51865) - Infinite loop inside thread group does not work properly if "Start next loop after a Sample error" option set

- [Bug 51868](https://bz.apache.org/bugzilla/show_bug.cgi?id=51868) - A lot of exceptions in jmeter.log while using option "Start next loop" for thread

- [Bug 51866](https://bz.apache.org/bugzilla/show_bug.cgi?id=51866) - Counter under loop doesn't work properly if "Start next loop on error" option set for thread group

- [Bug 52296](https://bz.apache.org/bugzilla/show_bug.cgi?id=52296) - TransactionController + Children ThrouputController or InterleaveController leads to ERROR sampleEnd called twice java.lang.Throwable: Invalid call sequence when TPC does not run sample

- [Bug 52330](https://bz.apache.org/bugzilla/show_bug.cgi?id=52330) - With next-Loop-On-Error after error samples are not executed in next loop

### Listeners

- [Bug 52357](https://bz.apache.org/bugzilla/show_bug.cgi?id=52357) - View results in Table does not allow for multiple result samples

- [Bug 52491](https://bz.apache.org/bugzilla/show_bug.cgi?id=52491) - Incorrect parsing of Post data parameters in Tree Listener / Http Request view

### Assertions

- [Bug 52519](https://bz.apache.org/bugzilla/show_bug.cgi?id=52519) - XMLSchemaAssertion uses JMeter JVM file.encoding instead of response encoding

### Functions

- The CRLF example for the char function was wrong; CRLF=(0xD,0xA), not (0xC,0xA)

### I18N

### General

- [Bug 51937](https://bz.apache.org/bugzilla/show_bug.cgi?id=51937) - JMeter does not handle missing TestPlan entry well

- [Bug 51988](https://bz.apache.org/bugzilla/show_bug.cgi?id=51988) - CSV Data Set Configuration does not resolve default delimiter for header parsing when variables field is empty

- [Bug 52003](https://bz.apache.org/bugzilla/show_bug.cgi?id=52003) - View Results Tree "Scroll automatically" does not scroll properly in case nodes are expanded

- [Bug 27112](https://bz.apache.org/bugzilla/show_bug.cgi?id=27112) - User Parameters should use scrollbars

- [Bug 52029](https://bz.apache.org/bugzilla/show_bug.cgi?id=52029) - Command-line shutdown only gets sent to last engine that was started

- [Bug 52093](https://bz.apache.org/bugzilla/show_bug.cgi?id=52093) - Toolbar ToolTips don't switch language

- [Bug 51733](https://bz.apache.org/bugzilla/show_bug.cgi?id=51733) - SyncTimer is messed up if you a interrupt a test plan

- [Bug 52118](https://bz.apache.org/bugzilla/show_bug.cgi?id=52118) - New toolbar : shutdown and stop buttons not disabled when no test is running

- [Bug 52125](https://bz.apache.org/bugzilla/show_bug.cgi?id=52125) - StatCalculator.addAll(StatCalculator calc) joins incorrect if there are more samples with the same response time in one of the TreeMap

- [Bug 52339](https://bz.apache.org/bugzilla/show_bug.cgi?id=52339) - JMeter Statistical mode in distributed testing shows wrong response time

- [Bug 52215](https://bz.apache.org/bugzilla/show_bug.cgi?id=52215) - Confusing synchronization in StatVisualizer, SummaryReport ,Summariser and issue in StatGraphVisualizer

- [Bug 52216](https://bz.apache.org/bugzilla/show_bug.cgi?id=52216) - TableVisualizer : currentData field is badly synchronized

- [Bug 52217](https://bz.apache.org/bugzilla/show_bug.cgi?id=52217) - ViewResultsFullVisualizer : Synchronization issues on root and treeModel

- [Bug 43294](https://bz.apache.org/bugzilla/show_bug.cgi?id=43294) - XPath Extractor namespace problems

- [Bug 52224](https://bz.apache.org/bugzilla/show_bug.cgi?id=52224) - TestBeanHelper does not support NOT_UNDEFINED == Boolean.FALSE

- [Bug 52279](https://bz.apache.org/bugzilla/show_bug.cgi?id=52279) - Switching to another language loses icons in Tree and logs error Can't obtain GUI class from &hellip;

- [Bug 52280](https://bz.apache.org/bugzilla/show_bug.cgi?id=52280) - The menu item Options / Choose Language does not change all the displayed text to the new language

- [Bug 52376](https://bz.apache.org/bugzilla/show_bug.cgi?id=52376) - StatCalculator#addValue(T val, int sampleCount) should use long, not int

- [Bug 49374](https://bz.apache.org/bugzilla/show_bug.cgi?id=49374) - Encoding of embedded element URLs depend on the file.encoding property

- [Bug 52399](https://bz.apache.org/bugzilla/show_bug.cgi?id=52399) - URLRewritingModifier uses default file.encoding to match text content

- [Bug 50438](https://bz.apache.org/bugzilla/show_bug.cgi?id=50438) - code calculates average with integer math, expecting double value

- [Bug 52469](https://bz.apache.org/bugzilla/show_bug.cgi?id=52469) - Changes in Support of SSH-Tunneling of RMI traffic for Remote Testing

- [Bug 52466](https://bz.apache.org/bugzilla/show_bug.cgi?id=52466) - Upgrade Test Plan feature : NameUpdater does not upgrade properties

- [Bug 52503](https://bz.apache.org/bugzilla/show_bug.cgi?id=52503) - Unify File&rarr;Close and Window close file saving behaviour

- [Bug 52537](https://bz.apache.org/bugzilla/show_bug.cgi?id=52537) - Help does not scroll to correct anchor when file is first loaded

## Improvements

### HTTP Samplers

- [Bug 51981](https://bz.apache.org/bugzilla/show_bug.cgi?id=51981) - Better support for file: protocol in HTTP sampler

- [Bug 52033](https://bz.apache.org/bugzilla/show_bug.cgi?id=52033) - Allowing multiple certificates (JKS)

- [Bug 52352](https://bz.apache.org/bugzilla/show_bug.cgi?id=52352) - Proxy : Support IPv6 URLs capture

- [Bug 44301](https://bz.apache.org/bugzilla/show_bug.cgi?id=44301) - Enable "ignore failed" for embedded resources

### Other samplers

- [Bug 51419](https://bz.apache.org/bugzilla/show_bug.cgi?id=51419) - JMS Subscriber: ability to use Selectors

- [Bug 52088](https://bz.apache.org/bugzilla/show_bug.cgi?id=52088) - JMS Sampler : Add a selector when REQUEST / RESPONSE is chosen

- [Bug 52104](https://bz.apache.org/bugzilla/show_bug.cgi?id=52104) - TCP Sampler handles badly errors

- [Bug 52087](https://bz.apache.org/bugzilla/show_bug.cgi?id=52087) - TCPClient interface does not allow for partial reads

- [Bug 52115](https://bz.apache.org/bugzilla/show_bug.cgi?id=52115) - SOAP/XML-RPC should not send a POST request when file to send is not found

- [Bug 40750](https://bz.apache.org/bugzilla/show_bug.cgi?id=40750) - TCPSampler : Behaviour when sockets are closed by remote host

- [Bug 52396](https://bz.apache.org/bugzilla/show_bug.cgi?id=52396) - TCP Sampler in "reuse connection mode" reuses previous sampler's connection even if it's configured with other host, port, user or password

- [Bug 52048](https://bz.apache.org/bugzilla/show_bug.cgi?id=52048) - BSFSampler, BSFPreProcessor and BSFPostProcessor should share the same GUI

### Controllers

### Listeners

- [Bug 52022](https://bz.apache.org/bugzilla/show_bug.cgi?id=52022) - In View Results Tree rather than showing just a message if the results are to big, show as much of the result as are configured

- [Bug 52201](https://bz.apache.org/bugzilla/show_bug.cgi?id=52201) - Add option to TableVisualiser to display child samples instead of parent 

- [Bug 52214](https://bz.apache.org/bugzilla/show_bug.cgi?id=52214) - Save Responses to a file - improve naming algorithm

- [Bug 52340](https://bz.apache.org/bugzilla/show_bug.cgi?id=52340) - Allow remote sampling mode to be changed at run-time

- [Bug 52452](https://bz.apache.org/bugzilla/show_bug.cgi?id=52452) - Improvements on Aggregate Graph Listener (GUI and settings)

- Resurrected OldSaveService to allow reading Avalon format JTL (result) files

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 52128](https://bz.apache.org/bugzilla/show_bug.cgi?id=52128) - Add JDBC pre- and post-processor

- [Bug 52183](https://bz.apache.org/bugzilla/show_bug.cgi?id=52183) - SyncTimer could be improved (performance+reliability)

- [Bug 52317](https://bz.apache.org/bugzilla/show_bug.cgi?id=52317) - Counter : Add option to reset counter on each Thread Group iteration

- [Bug 37073](https://bz.apache.org/bugzilla/show_bug.cgi?id=37073) - Add a Poisson based timer

- [Bug 52497](https://bz.apache.org/bugzilla/show_bug.cgi?id=52497) - Improve DebugSampler and DebugPostProcessor

### Functions

- [Bug 52006](https://bz.apache.org/bugzilla/show_bug.cgi?id=52006) - Create a function RandomString to generate random Strings

- [Bug 52016](https://bz.apache.org/bugzilla/show_bug.cgi?id=52016) - It would be useful to support Jexl2

- __char() function now supports octal values

- New function __machineIP returning IP address

- [Bug 51091](https://bz.apache.org/bugzilla/show_bug.cgi?id=51091) - New function returning the name of the current "Test Plan"

### I18N

### General

- [Bug 51892](https://bz.apache.org/bugzilla/show_bug.cgi?id=51892) - Default mirror port should be different from default proxy port

- [Bug 51817](https://bz.apache.org/bugzilla/show_bug.cgi?id=51817) - Moving variables up and down in User Defined Variables control

- [Bug 51876](https://bz.apache.org/bugzilla/show_bug.cgi?id=51876) - Functionality to search in Samplers TreeView

- [Bug 52019](https://bz.apache.org/bugzilla/show_bug.cgi?id=52019) - Add menu option to Start a test ignoring Pause Timers

- [Bug 52027](https://bz.apache.org/bugzilla/show_bug.cgi?id=52027) - Allow System or CrossPlatform LAF to be set from options menu

- [Bug 52037](https://bz.apache.org/bugzilla/show_bug.cgi?id=52037) - Remember user-set LaF over restarts.

- [Bug 51861](https://bz.apache.org/bugzilla/show_bug.cgi?id=51861) - Improve HTTP Request GUI to better show parameters without name (GWT RPC requests for example) (UNDER DEVELOPMENT)

- [Bug 52040](https://bz.apache.org/bugzilla/show_bug.cgi?id=52040) - Add a toolbar in JMeter main window

- [Bug 51816](https://bz.apache.org/bugzilla/show_bug.cgi?id=51816) - Comment Field in User Defined Variables control.

- [Bug 52052](https://bz.apache.org/bugzilla/show_bug.cgi?id=52052) - Using a delimiter to separate result-messages for JMS Subscriber

- [Bug 52103](https://bz.apache.org/bugzilla/show_bug.cgi?id=52103) - Add automatic scrolling option to table visualizer

- [Bug 52097](https://bz.apache.org/bugzilla/show_bug.cgi?id=52097) - Save As should point to same folder that was used to open a file if MRU list is used

- [Bug 52085](https://bz.apache.org/bugzilla/show_bug.cgi?id=52085) - Allow multiple selection in arguments panel

- [Bug 52099](https://bz.apache.org/bugzilla/show_bug.cgi?id=52099) - Allow to set the transaction isolation in the JDBC Connection Configuration

- [Bug 52116](https://bz.apache.org/bugzilla/show_bug.cgi?id=52116) - Allow to add (paste) entries from the clipboard to an arguments list

- [Bug 52160](https://bz.apache.org/bugzilla/show_bug.cgi?id=52160) - Don't display TestBeanGui items which are flagged as hidden

- [Bug 51886](https://bz.apache.org/bugzilla/show_bug.cgi?id=51886) - SampleSender configuration resolved partly on client and partly on server

- [Bug 52161](https://bz.apache.org/bugzilla/show_bug.cgi?id=52161) - Enable plugins to add own translation rules in addition to upgrade.properties.
Loads any additional properties found in META-INF/resources/org.apache.jmeter.nameupdater.properties files

- [Bug 42538](https://bz.apache.org/bugzilla/show_bug.cgi?id=42538) - Add "duplicate node" in context menu

- [Bug 46921](https://bz.apache.org/bugzilla/show_bug.cgi?id=46921) - Add Ability to Change Controller elements

- [Bug 52240](https://bz.apache.org/bugzilla/show_bug.cgi?id=52240) - TestBeans should support Boolean, Integer and Long

- [Bug 52241](https://bz.apache.org/bugzilla/show_bug.cgi?id=52241) - GenericTestBeanCustomizer assumes that the default value is the empty string

- [Bug 52242](https://bz.apache.org/bugzilla/show_bug.cgi?id=52242) - FileEditor does not allow output to be saved in a File 

- [Bug 51093](https://bz.apache.org/bugzilla/show_bug.cgi?id=51093) - when loading a selection previously stored by "Save Selection As", show the file name in the blue window bar

- [Bug 50086](https://bz.apache.org/bugzilla/show_bug.cgi?id=50086) - Password fields not Hidden in JMS Publisher, JMS Subscriber, Mail Reader sampler, SMTP sampler and Database Configuration

- [Bug 29352](https://bz.apache.org/bugzilla/show_bug.cgi?id=29352) - Use external store to hold samples during distributed testing, Added DiskStore remote sample sender: like Hold, but saves samples to disk until end of test.

- [Bug 52333](https://bz.apache.org/bugzilla/show_bug.cgi?id=52333) - Reduce overhead in calculating SampleResult#nanoTimeOffset

- [Bug 52346](https://bz.apache.org/bugzilla/show_bug.cgi?id=52346) - Shutdown detects if there are any non-daemon threads left which prevent JVM exit.

- [Bug 52281](https://bz.apache.org/bugzilla/show_bug.cgi?id=52281) - Support for file Drag and Drop

- [Bug 52471](https://bz.apache.org/bugzilla/show_bug.cgi?id=52471) - Improve Mirror Server performance by Using Pool of threads instead of launching a Thread for each request

- Resurrected OldSaveService to allow reading Avalon format JMX files (removed in 2.4)

- Add a dialog box to confirm removing the element(s) when Remove action is called

- [Bug 41788](https://bz.apache.org/bugzilla/show_bug.cgi?id=41788) - Log viewer (console window) needed as an option

- Add option to change the pause time (default 2000ms) in the daemon thread which checks for successful JVM exit.
The thread is not now started unless the pause time is greater than 0.

## Non-functional changes

- fixes to build.xml: support scripts; localise re-usable property names

- [Bug 51923](https://bz.apache.org/bugzilla/show_bug.cgi?id=51923) - Counter function bug or documentation issue ? (fixed docs)

- Update velocity.jar to 1.7 (from 1.6.2)

- Update js.jar to 1.7R3 (from 1.6R5)

- Update commons-codec 1.5 &rArr; 1.6

- Update commons-io 2.0.1 &rArr; 2.1

- Update commons-jexl 2.0.1 &rArr; 2.1.1

- Update jdom 1.1 &rArr; 1.1.2

- Update junit 4.9 &rArr; 4.10

- [Bug 51954](https://bz.apache.org/bugzilla/show_bug.cgi?id=51954) - Generated documents include &lt;/br&gt; entries which cause extra blank lines 

- [Bug 52075](https://bz.apache.org/bugzilla/show_bug.cgi?id=52075) - JMeterProperty.clone() currently returns Object; it should return JMeterProperty

- Updated httpcore to 4.1.4

- [Bug 49753](https://bz.apache.org/bugzilla/show_bug.cgi?id=49753) - Please publish jMeter artifacts on Maven central repository

---
title: Version 2.8
---

# Version 2.8

## New and Noteworthy

### Core Improvements:

Thread Group: New Option *Delay thread creation until needed*
New Option "Delay thread creation until needed" that will create and start threads when needed instead of creating them on Test startup
**This new feature allows running tests with a huge number of short lived threads.**

HTTP Cookie Manager (IPv6 support)
Add HTTPClient 4 cookie implementation in JMeter. 
Cookie Manager has now the default HC3.1 implementation and a new choice HC4 implementation (compliant with IPv6 address)

Memory and performance improvements
Significant improvements have been done in this version on memory usage of JMeterThread

JSR223 Elements (enable using Groovy, scala, &hellip; as scripting languages) have been improved to enable:

    - usage of Compilable interface when available to boost CPU usage

    - caching of Compilation when scripts are used as Files

See [JMeter Performances across versions](https://cwiki.apache.org/confluence/display/JMETER/JMeterPerformance)

OS Process Sampler
Allow defining files for stdout/stderr/stdin.

HTTP Request: PATCH verb
Add PATCH verb to HTTP sampler

HTTP Request: HTTPClient 4 is now the default implementation
HTTPClient 4 is now the default HTTP Request implementation (and for Proxy element when generating HTTP requests).
Previously the default was the HTTP Java implementation (i.e. the implementation provided by the JVM)

HTTP Request
Add Embedded URL Filter to HTTP Request Defaults Control (it was already present for HTTP Requests)

Miscellaneous

    - CSV Dataset : Embedded new lines are now supported in quoted data

    - JMX files now contain the version of JMeter that created the file

    - JMeter Version is now available as property "jmeter.version"

### Reporting Improvements:

Response Time Graph
Add a new visualizer Response Time Graph to draw a line graph showing the evolution of response time for a test

Settings for Response Time Graph

View Results in Table
Add latency to View Result in Table listener

Aggregate Graph
Small improvements: legend at left or right is now on 1 column (instead of 1 large line), &hellip;

### GUI and ergonomy Improvements:

HTTP Proxy Server simplifications
HTTPS Spoofing options have been removed from Proxy as HTTPS recording is directly available since JMeter 2.4.

HTTP Proxy Server
Allow URL Filters to be pasted from clipboard

Find in JMeter
CTRL + F for the new Find feature

ESC key now closes popups.

User Interface in GNOME 3
Display 'Apache JMeter' title in app title bar in Gnome 3

## Known bugs

The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

Listeners don't show iteration counts when a If Controller has a condition which is always false from the first iteration (see [Bug 52496](https://bz.apache.org/bugzilla/show_bug.cgi?id=52496)).
A workaround is to add a sampler at the same level as (or superior to) the If Controller.
For example a Test Action sampler with 0 wait time (which doesn't generate a sample),
or a Debug Sampler with all fields set to False (to reduce the sample size).

Changing language can break part of the configuration of the following elements (see [Bug 53679](https://bz.apache.org/bugzilla/show_bug.cgi?id=53679)):

    - CSV Data Set Config (sharing mode will be lost)

    - Constant Throughput Timer (Calculate throughput based on will be lost)

Note that there is a bug in Java on some Linux systems that manifests
itself as the following error when running the test cases or JMeter itself:

 [java] WARNING: Couldn't flush user prefs:
 java.util.prefs.BackingStoreException:
 java.lang.IllegalArgumentException: Not supported: indent-number

This does not affect JMeter operation.

## Incompatible changes

When using CacheManager, JMeter now caches responses for GET queries provided header Cache-Control is different from "no-cache" as described in specification.
Furthermore it doesn't put anymore in Cache deprecated entries for "no-cache" responses. See [Bug 53521](https://bz.apache.org/bugzilla/show_bug.cgi?id=53521) and [Bug 53522](https://bz.apache.org/bugzilla/show_bug.cgi?id=53522)

A major change has occurred on JSR223 Test Elements, previously variables set up before script execution where stored in ScriptEngineManager which was created once per execution,
now ScriptEngineManager is a singleton shared by all JSR223 elements and only ScriptEngine is created once per execution, variables set up before script execution are now stored
in Bindings created on each execution, see [Bug 53365](https://bz.apache.org/bugzilla/show_bug.cgi?id=53365).

JSR223 Test Elements using Script file are now Compiled if ScriptEngine supports this feature, see [Bug 53520](https://bz.apache.org/bugzilla/show_bug.cgi?id=53520).

Shortcut for Function Helper Dialog is now CTRL+F1 (CMD + F1 for Mac OS), CTRL+F (CMD+F1 for Mac OS) now opens Search Dialog.

By default, the TestCompiler now stores details of which pairs it has seen in Controller instances rather than in a static Set.
[[Bug 53796](https://bz.apache.org/bugzilla/show_bug.cgi?id=53796)]
This gives much better memory behaviour for delayed start test plans, as memory used is proportional to the number of concurrent threads.
With the static Set memory usage was proportional to the total thread count.
This change is very unlikely to cause a problem.
The original behaviour can be restored by setting the property `TestCompiler.useStaticSet=true`

HTTPS Spoofing options have been removed from Proxy as HTTPS recording is directly available since JMeter 2.4.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 53521](https://bz.apache.org/bugzilla/show_bug.cgi?id=53521) - Cache Manager should cache content with Cache-control=private

- [Bug 53522](https://bz.apache.org/bugzilla/show_bug.cgi?id=53522) - Cache Manager should not store at all response with header "no-cache" and store other types of Cache-Control having max-age value

- [Bug 53838](https://bz.apache.org/bugzilla/show_bug.cgi?id=53838) - Pressing "Stop" does not interrupt the TCP sampler

- [Bug 53911](https://bz.apache.org/bugzilla/show_bug.cgi?id=53911) - JmeterKeystore does not allow for key down the list of certificate

### Other Samplers

- [Bug 53348](https://bz.apache.org/bugzilla/show_bug.cgi?id=53348) - JMeter JMS Point-to-Point Request-Response sampler doesn't work when Request-queue and Receive-queue are different

- [Bug 53357](https://bz.apache.org/bugzilla/show_bug.cgi?id=53357) - JMS Point to Point reports too high response times in Request Response Mode

- [Bug 53440](https://bz.apache.org/bugzilla/show_bug.cgi?id=53440) - SSL connection leads to ArrayStoreException on JDK 6 with some KeyManagerFactory SPI

- [Bug 53511](https://bz.apache.org/bugzilla/show_bug.cgi?id=53511) - access log sampler SessionFilter throws NullPointerException - cookie manager not initialized properly

- [Bug 53715](https://bz.apache.org/bugzilla/show_bug.cgi?id=53715) - JMeter does not load WSDL

### Controllers

### Listeners

- [Bug 53742](https://bz.apache.org/bugzilla/show_bug.cgi?id=53742) - When jmeter.save.saveservice.sample_count is set to true, elapsed time read by listener is always equal to 0

- [Bug 53774](https://bz.apache.org/bugzilla/show_bug.cgi?id=53774) - RequestViewRaw does not show headers unless samplerData is non-null

- [Bug 53802](https://bz.apache.org/bugzilla/show_bug.cgi?id=53802) - IdleTime values are not saved to CSV log

- [Bug 53874](https://bz.apache.org/bugzilla/show_bug.cgi?id=53874) - View Results Tree : If some parameter containing special characters like % is not encoded, RequestViewHTTP fails with java.lang.IllegalArgumentException: URLDecoder: Illegal hex characters in escape (%) pattern and Response is not displayed

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 51512](https://bz.apache.org/bugzilla/show_bug.cgi?id=51512) - Cookies aren't inserted into HTTP request with IPv6 Host header

### Functions

### I18N

### General

- [Bug 53365](https://bz.apache.org/bugzilla/show_bug.cgi?id=53365) - JSR223TestElement should cache ScriptEngineManager

- [Bug 53520](https://bz.apache.org/bugzilla/show_bug.cgi?id=53520) - JSR223 Elements : Use Compilable interface to improve performances on File scripts

- [Bug 53501](https://bz.apache.org/bugzilla/show_bug.cgi?id=53501) - Synchronization timer blocks test end.

- [Bug 53750](https://bz.apache.org/bugzilla/show_bug.cgi?id=53750) - TestCompiler saves unnecessary entries in pairing collection

- [Bug 52266](https://bz.apache.org/bugzilla/show_bug.cgi?id=52266) - Code:Inconsistent synchronization

- [Bug 53841](https://bz.apache.org/bugzilla/show_bug.cgi?id=53841) - CSVSaveService reads file using JVM default file encoding instead of using the one configured in saveservice.properties

- [Bug 53953](https://bz.apache.org/bugzilla/show_bug.cgi?id=53953) New: Typo in monitor test plan documentation

## Improvements

### HTTP Samplers

- [Bug 53675](https://bz.apache.org/bugzilla/show_bug.cgi?id=53675) - Add PATCH verb to HTTP sampler

- [Bug 53931](https://bz.apache.org/bugzilla/show_bug.cgi?id=53931) - Define HTTPClient 4 for the default HTTP Request (and Proxy element to generate the HTTP requests). Before the default, it was the HTTP Java Sampler

- [Bug 53934](https://bz.apache.org/bugzilla/show_bug.cgi?id=53934) - Removes HTTPS spoofing options in JMeter HTTP Proxy Server. Since JMeter 2.4, the HTTPS protocol is directly supported by the proxy

### Other samplers

- [Bug 55310](https://bz.apache.org/bugzilla/show_bug.cgi?id=55310) - TestAction should implement Interruptible

- [Bug 53318](https://bz.apache.org/bugzilla/show_bug.cgi?id=53318) - Add Embedded URL Filter to HTTP Request Defaults Control 

- [Bug 53782](https://bz.apache.org/bugzilla/show_bug.cgi?id=53782) - Enhance JavaSampler handling of JavaSamplerClient cleanup to use less memory

- [Bug 53168](https://bz.apache.org/bugzilla/show_bug.cgi?id=53168) - OS Process - allow specification of stdout/stderr/stdin

- [Bug 53844](https://bz.apache.org/bugzilla/show_bug.cgi?id=53844) - JDBC related elements should check class of Variable Name supposed to contain JDBC Connection Configuration to avoid ClassCastException 

### Controllers

- [Bug 53671](https://bz.apache.org/bugzilla/show_bug.cgi?id=53671) - tearDown thread group to run even if shutdown test happens

### Listeners

- [Bug 53566](https://bz.apache.org/bugzilla/show_bug.cgi?id=53566) - Don't log partial responses to the jmeter log

- [Bug 53716](https://bz.apache.org/bugzilla/show_bug.cgi?id=53716) - Small improvements in aggregate graph: legend at left or right is now on 1 column (instead of 1 large line), no border to the reference's square color, reduce width on some fields

- [Bug 53718](https://bz.apache.org/bugzilla/show_bug.cgi?id=53718) - Add a new visualizer 'Response Time Graph' to draw a line graph showing the evolution of response time for a test

- [Bug 53738](https://bz.apache.org/bugzilla/show_bug.cgi?id=53738) - Keep track of number of threads started and finished

- [Bug 53753](https://bz.apache.org/bugzilla/show_bug.cgi?id=53753) -  Summariser: no point displaying fractional time in most cases

- [Bug 53749](https://bz.apache.org/bugzilla/show_bug.cgi?id=53749) - TestListener interface could perhaps be split up.
This should reduce per-thread memory requirements and processing,
as only test elements that actually use testIterationStart functionality now need to be handled.

- [Bug 53941](https://bz.apache.org/bugzilla/show_bug.cgi?id=53941) - Add latency to View Result table listener

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 53755](https://bz.apache.org/bugzilla/show_bug.cgi?id=53755) - Adding a HttpClient 4 cookie implementation in JMeter.
Cookie Manager has now the default HC3.1 implementation and a new choice HC4 implementation (compliant with IPv6 address)

### Functions

- [Bug 51527](https://bz.apache.org/bugzilla/show_bug.cgi?id=51527) - __time() function : add another option to __time() to provide *seconds* since epoch

### I18N

### General

- [Bug 53364](https://bz.apache.org/bugzilla/show_bug.cgi?id=53364) - Sort list of Functions in Function Helper Dialog

- [Bug 53418](https://bz.apache.org/bugzilla/show_bug.cgi?id=53418) - New Option "Delay thread creation until needed" that will create and start threads when needed instead of creating them on Test startup

- [Bug 42245](https://bz.apache.org/bugzilla/show_bug.cgi?id=42245) - Show clear passwords in HTTP Authorization Manager

- [Bug 53616](https://bz.apache.org/bugzilla/show_bug.cgi?id=53616) - Display 'Apache JMeter' title in app title bar in Gnome 3

- [Bug 53759](https://bz.apache.org/bugzilla/show_bug.cgi?id=53759) - ClientJMeterEngine performs unnecessary traverse using SearchByClass(TestListener)

- [Bug 52601](https://bz.apache.org/bugzilla/show_bug.cgi?id=52601) - CTRL + F for the new Find feature

- [Bug 53796](https://bz.apache.org/bugzilla/show_bug.cgi?id=53796) - TestCompiler uses static Set which can grow huge

- [Bug 53673](https://bz.apache.org/bugzilla/show_bug.cgi?id=53673) - Add JMeter version in the jmx file

- Add support for HeapDump to the JMeter non-GUI and GUI client

- [Bug 53862](https://bz.apache.org/bugzilla/show_bug.cgi?id=53862) - Would be nice to have the JMeter Version available as a property

- [Bug 53806](https://bz.apache.org/bugzilla/show_bug.cgi?id=53806) -  FileServer should provide thread-safe parsing

- [Bug 53807](https://bz.apache.org/bugzilla/show_bug.cgi?id=53807) - CSV Dataset does not handle embedded new lines in quoted data

- [Bug 53879](https://bz.apache.org/bugzilla/show_bug.cgi?id=53879) - GUI : Allow Popups to be closed with ESC key

- [Bug 53876](https://bz.apache.org/bugzilla/show_bug.cgi?id=53876) - Allow URL Filters (HTTP Proxy) to be pasted from clipboard

## Non-functional changes

- [Bug 53311](https://bz.apache.org/bugzilla/show_bug.cgi?id=53311) - JMeterUtils#runSafe should not throw Error when interrupted

- Updated to commons-net-3.1 (from 3.0.1)

- Updated to HttpComponents Core 4.2.2 (from 4.1.4) and HttpComponents Client 4.2.1 (from 4.1.3)

- [Bug 53765](https://bz.apache.org/bugzilla/show_bug.cgi?id=53765) - Switch to commons-lang3-3.1

- [Bug 53884](https://bz.apache.org/bugzilla/show_bug.cgi?id=53884) - wrong Maven groupId for commons-lang

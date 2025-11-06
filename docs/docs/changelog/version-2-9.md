---
title: Version 2.9
---

# Version 2.9

## New and Noteworthy

### Core Improvements:

* A new Extractor that uses CSS or jquery-like selector syntax has been introduced,
it allows using either JODD or JSOUP implementations

Result: the title of the page in a JMeter variable

* JMeter can now handle different types of documents (PDF, MsOffice files, Apache OpenOffice's files, &hellip;)
 within different elements 

    - Regular Expression Extractor, extract text from documents

    - Assertion Response, check text in documents

    - View Results Tree, view as a text the documents

* A new Regex User Parameters Pre-Processor that enables injecting input parameter names and values
using a reference extracted by Regular Expression Extractor from a previous response

* TCP Sampler: new options
TCP Sampler has been enhanced with new options to allow setting **Close Connection**,
 **SO_LINGER** and  **End of line(EOL) byte value**

* A new function *__escapeOroRegexpChars(,)* has been introduced quote ORO regexp meta characters
* ForEach Controller: new fields
ForEach Controller has now 2 new fields to control start and end of loop

* Result Status Action Handler now has a new option to "Start next thread loop"

* JMS Publisher: new option
JMS Publisher can now send Bytes Messages

* Memory and performance improvements
Significant improvements have been done in this version on memory usage per Thread and CPU when more
than one Post Processor is used as child of a Sampler

JSR223 Elements (enable using Groovy, Scala, &hellip; as scripting languages) have been improved to enable caching
of Compilation results when scripts are passed in Text area

Some configuration defaults have changed to improve performances by default(see [Bug 54412](https://bz.apache.org/bugzilla/show_bug.cgi?id=54412)),
see description in New and Noteworthy section.

    - Distributed testing now uses MODE_STRIPPED_BATCH, which returns samples in batch mode (every 100 samples
     or every minute by default). Note also that MODE_STRIPPED_BATCH strips response data from SampleResult,
     so if you need it change to another mode (mode property in jmeter.properties)

    - Result data are now saved to CSV by default (jmeter.save.saveservice.output_format in jmeter.properties)

* XPath Assertion now enables using a JMeter variable as input

### GUI and ergonomy Improvements:

* Search feature has been improved to search within more internal fields of elements and expand search results
* Copy/paste is now possible between 2 JMeter instances &gt;= 2.9 version
Copy element(s) from one JMeter instance:

Paste element(s) into a second JMeter instance:

* HTTP Header Manager
Allow copy from clipboard to HeaderPanel, headers are supposed to be separated by new line
 and have the following form *name:value*

* Module Controller
Module Controller has been improved to better render referenced controller and expand it by clicking on a new button

* HTTP Proxy Server
HTTP Proxy Server now has a button to add a set of default exclusions for URL patterns,
this list can be configured through property : *proxy.excludes.suggested*

* Rendering of target controller has been improved in HTTP Proxy Server

### HTTP Proxy Server recording:

* HTTP Proxy Server now automatically uses HTTP Request with Raw Post Body mode for
samples that only have one unnamed argument (JSON, XML, GWT, &hellip;)

* HTTP Proxy Server does not force user to select the type of Sampler in HTTP Sampler Settings,
 this allows easier switch between implementations as Sampler do not have this information set anymore

 

* SamplerCreator interface has been enriched to meet new requirements for plug-in providers

* It is now possible to create binary sampler for x-www-form-urlencoded POST request by
modifying *proxy.binary.types* property to add application/x-www-form-urlencoded 

* Improved timestamp format auto-detection when reading CSV files

## Known bugs

The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

Listeners don't show iteration counts when a If Controller has a condition which is always false from the first iteration (see [Bug 52496](https://bz.apache.org/bugzilla/show_bug.cgi?id=52496)).
A workaround is to add a sampler at the same level as (or superior to) the If Controller.
For example a Test Action sampler with 0 wait time (which doesn't generate a sample),
or a Debug Sampler with all fields set to False (to reduce the sample size).

Webservice sampler does not consider the HTTP response status to compute the status of a response, thus a response 500 containing a non empty body will be considered as successful, see [Bug 54006](https://bz.apache.org/bugzilla/show_bug.cgi?id=54006).
To workaround this issue, ensure you always read the response and add a Response Assertion checking text inside the response.

Changing language can break part of the configuration of the following elements (see [Bug 53679](https://bz.apache.org/bugzilla/show_bug.cgi?id=53679)):

    - CSV Data Set Config (sharing mode will be lost)

    - Constant Throughput Timer (Calculate throughput based on will be lost)

The numbers that appear to the left of the green box are the number of active threads / total number of threads,
these only apply to a locally run test; they do not include any threads started on remote systems when using client-server mode, (see [Bug 54152](https://bz.apache.org/bugzilla/show_bug.cgi?id=54152)).

Note that there is a bug in Java on some Linux systems that manifests
itself as the following error when running the test cases or JMeter itself:

 [java] WARNING: Couldn't flush user prefs:
 java.util.prefs.BackingStoreException:
 java.lang.IllegalArgumentException: Not supported: indent-number

This does not affect JMeter operation.

## Incompatible changes

**JMeter requires now a Java 6 runtime or higher.**

Some configuration defaults have changed to improve performances by default (see [Bug 54412](https://bz.apache.org/bugzilla/show_bug.cgi?id=54412)),
see description in New and Noteworthy section.

Webservice sampler now adds to request the headers that are set through Header Manager, these were previously ignored

*jdbcsampler.cachesize* property has been removed, it previously limited the size of a per connection cache of Map &lt; String,
PreparedStatement &gt; , it also limited the size of this
map which held the PreparedStatement for SQL queries. This limitation provoked a bug [Bug 53995](https://bz.apache.org/bugzilla/show_bug.cgi?id=53995).
It has been removed so now size of these 2 maps is not limited anymore. This change changes behaviour as starting from
this version no PreparedStatement will be closed during the test.

Starting with this version, there are some important changes on JSR223 Test Elements:

    - JSR223 Test Elements that have an invalid filename (not existing or unreadable) will make test fail instead of
    making the element silently work

    - In JSR223 Test Elements: responseCodeOk, responseMessageOK and successful are set before
     script is executed, if responseData is set it will not be overridden anymore by a toString() on script return value

View Results Tree now considers response with missing content type as text.

In remote Test mode, JMeter now exits in error if one of the remote engines cannot be configured,
previously it started the test with available engines.

## Bug fixes

### HTTP Samplers and Proxy

- Don't log spurious warning messages when using concurrent pool embedded downloads with Cache Manager or CookieManager

- [Bug 54057](https://bz.apache.org/bugzilla/show_bug.cgi?id=54057)- Proxy option to set user and password at startup (-u and -a) not working with HTTPClient 4

- [Bug 54187](https://bz.apache.org/bugzilla/show_bug.cgi?id=54187) - Request tab does not show headers if request fails

- [Bug 53840](https://bz.apache.org/bugzilla/show_bug.cgi?id=53840) - Proxy Recording : Response message: URLDecoder: Illegal hex characters in escape (%) pattern - For input string: "" "

- [Bug 54351](https://bz.apache.org/bugzilla/show_bug.cgi?id=54351) - HC4 and URI fragments is failing

### Other Samplers

- [Bug 53997](https://bz.apache.org/bugzilla/show_bug.cgi?id=53997) - LDAP Extended Request: Escape ampersand (&amp;), left angle bracket (&lt;)
and right angle bracket (&gt;) in search filter tag in XML response data

- [Bug 53995](https://bz.apache.org/bugzilla/show_bug.cgi?id=53995) - AbstractJDBCTestElement shares PreparedStatement between multi-threads

- [Bug 54119](https://bz.apache.org/bugzilla/show_bug.cgi?id=54119) - HTTP 307 response is not redirected

- [Bug 54326](https://bz.apache.org/bugzilla/show_bug.cgi?id=54326) - AjpSampler send file in post throws FileNotFoundException

- [Bug 54331](https://bz.apache.org/bugzilla/show_bug.cgi?id=54331) - AjpSampler throws null pointer on GET request that are protected

### Controllers

### Listeners

- [Bug 54088](https://bz.apache.org/bugzilla/show_bug.cgi?id=54088) - The type video/f4m is text, not binary

- [Bug 54166](https://bz.apache.org/bugzilla/show_bug.cgi?id=54166) - ViewResultsTree could not render the HTML response: handle failure to parse HTML

- [Bug 54287](https://bz.apache.org/bugzilla/show_bug.cgi?id=54287) - Incorrect Timestamp in Response Time Graph when using a date with time in Date format field

- [Bug 54451](https://bz.apache.org/bugzilla/show_bug.cgi?id=54451) - Response Time Graph reports wrong times when the are many samples for same time

- [Bug 54459](https://bz.apache.org/bugzilla/show_bug.cgi?id=54459) - CSVSaveService does not handle date parsing very well

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 54058](https://bz.apache.org/bugzilla/show_bug.cgi?id=54058) - In HTTP Request Defaults, the value of field "Embedded URLs must match: is not saved if the check box "Retrieve All  Embedded Resources" is not checked.

- [Bug 54375](https://bz.apache.org/bugzilla/show_bug.cgi?id=54375) - Regular Expression Extractor : When regex syntax is wrong, post processing is stopped

### Functions

### I18N

### General

- [Bug 53975](https://bz.apache.org/bugzilla/show_bug.cgi?id=53975) - Variables replacement doesn't work with option "Delay thread creation until needed"

- [Bug 54055](https://bz.apache.org/bugzilla/show_bug.cgi?id=54055) - View Results tree: = signs are stripped from parameter values at HTTP tab

- [Bug 54129](https://bz.apache.org/bugzilla/show_bug.cgi?id=54129) - Search Feature does not find text although existing in elements 

- [Bug 54023](https://bz.apache.org/bugzilla/show_bug.cgi?id=54023) - Unable to start JMeter from a root directory and if the full path of JMeter installation contains one or more spaces (Unix/linux)

- [Bug 54172](https://bz.apache.org/bugzilla/show_bug.cgi?id=54172) - Duplicate shortcut key not working and CTRL+C / CTRL+V / CTRL+V do not cancel default event

- [Bug 54057](https://bz.apache.org/bugzilla/show_bug.cgi?id=54057) - Proxy option to set user and password at startup (-u and -a) not working with HTTPClient 4

- [Bug 54267](https://bz.apache.org/bugzilla/show_bug.cgi?id=54267) - Start Next Thread Loop setting doesn't work in custom thread groups

- [Bug 54413](https://bz.apache.org/bugzilla/show_bug.cgi?id=54413) - DataStrippingSampleSender returns 0 for number of bytes of any response

## Improvements

### HTTP Samplers

- [Bug 54185](https://bz.apache.org/bugzilla/show_bug.cgi?id=54185) - Allow query strings in paths that start with HTTP or HTTPS

### Other samplers

- [Bug 54004](https://bz.apache.org/bugzilla/show_bug.cgi?id=54004) - Webservice Sampler : Allow adding headers to request with Header Manager

- [Bug 54106](https://bz.apache.org/bugzilla/show_bug.cgi?id=54106) - JSR223TestElement should check for file existence when a filename is set instead of using Text Area content 

- [Bug 54107](https://bz.apache.org/bugzilla/show_bug.cgi?id=54107) - JSR223TestElement : Enable compilation and caching of Script Text

- [Bug 54109](https://bz.apache.org/bugzilla/show_bug.cgi?id=54109) - JSR223TestElement : SampleResult properties should be set before entering script to allow user setting different code

- [Bug 54230](https://bz.apache.org/bugzilla/show_bug.cgi?id=54230) - TCP Sampler, additions of "Close Connection", "SO_LINGER" and "End of line(EOL) byte value" options

- [Bug 54182](https://bz.apache.org/bugzilla/show_bug.cgi?id=54182) - Support sending of ByteMessage for JMS Publisher.

### Controllers

- [Bug 54131](https://bz.apache.org/bugzilla/show_bug.cgi?id=54131) - ForEach Controller : Add start and end index for looping over variables

- [Bug 54132](https://bz.apache.org/bugzilla/show_bug.cgi?id=54132) - Module Controller GUI : Improve rendering of referenced controller

- [Bug 54155](https://bz.apache.org/bugzilla/show_bug.cgi?id=54155) - ModuleController : Add a shortcut button to unfold the tree up to referenced controller and highlight it

### Listeners

- [Bug 54200](https://bz.apache.org/bugzilla/show_bug.cgi?id=54200) - Add support of several document types (like Apache OpenOffice's files, MS Office's files, PDF's files, etc.)
to the elements View Results Tree, Assertion Response and Regular Expression Extractor (using Apache Tika)

- [Bug 54226](https://bz.apache.org/bugzilla/show_bug.cgi?id=54226) - View Results Tree : Show response even when server does not return ContentType header

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 54259](https://bz.apache.org/bugzilla/show_bug.cgi?id=54259) - Introduce a new Extractor that uses CSS or jquery-like selector syntax

- [Bug 45772](https://bz.apache.org/bugzilla/show_bug.cgi?id=45772) - RegEx User Parameters Post Processor

- [Bug 54160](https://bz.apache.org/bugzilla/show_bug.cgi?id=54160) - Add support for xpath assertion to apply to a JMeter variable.

### Functions

- [Bug 54189](https://bz.apache.org/bugzilla/show_bug.cgi?id=54189) - Add a function to quote ORO regexp meta characters

- [Bug 54418](https://bz.apache.org/bugzilla/show_bug.cgi?id=54418) - UUID Function

### I18N

### General

- [Bug 54005](https://bz.apache.org/bugzilla/show_bug.cgi?id=54005) - HTTP Mirror Server : Add special headers "X-" to control Response status and response content

- [Bug 53875](https://bz.apache.org/bugzilla/show_bug.cgi?id=53875) - Include suggested defaults for URL filters on HTTP Proxy

- [Bug 54031](https://bz.apache.org/bugzilla/show_bug.cgi?id=54031) - Add tooltip to running/total threads indicator 

- Webservice (SOAP) Request has been deprecated

- [Bug 54161](https://bz.apache.org/bugzilla/show_bug.cgi?id=54161) - Proxy : be able to create binary sampler for x-www-form-urlencoded POST request

- [Bug 54154](https://bz.apache.org/bugzilla/show_bug.cgi?id=54154) - HTTP Proxy Server should not force user to select the type of Sampler in HTTP Sampler Settings

- [Bug 54165](https://bz.apache.org/bugzilla/show_bug.cgi?id=54165) - Proxy Server: Improve rendering of target controller

- [Bug 46677](https://bz.apache.org/bugzilla/show_bug.cgi?id=46677) - Copying Test Elements between test plans

- [Bug 54204](https://bz.apache.org/bugzilla/show_bug.cgi?id=54204) - Result Status Action Handler : Add start next thread loop option

- [Bug 54232](https://bz.apache.org/bugzilla/show_bug.cgi?id=54232) - Search Feature : Add a button to search and expand results

- [Bug 54251](https://bz.apache.org/bugzilla/show_bug.cgi?id=54251) - Add tristate checkbox implementation

- [Bug 54257](https://bz.apache.org/bugzilla/show_bug.cgi?id=54257) - Enhance SamplerCreator interface to meet new requirements

- [Bug 54258](https://bz.apache.org/bugzilla/show_bug.cgi?id=54258) - Proxy : Use Raw Post Body when Sampler has one unnamed argument, useful for Samplers using POST method by of type JSON, XML, GWT body

- [Bug 54268](https://bz.apache.org/bugzilla/show_bug.cgi?id=54268) - Improve CPU and memory usage

- [Bug 54376](https://bz.apache.org/bugzilla/show_bug.cgi?id=54376) - ScopePanel : Allow configuring more precisely scopes

- [Bug 54412](https://bz.apache.org/bugzilla/show_bug.cgi?id=54412) - Changing JMeter defaults to ensure better performances by default

- [Bug 54414](https://bz.apache.org/bugzilla/show_bug.cgi?id=54414) - Remote Test should not start if one of the engines fails to start correctly

## Non-functional changes

- [Bug 53956](https://bz.apache.org/bugzilla/show_bug.cgi?id=53956) - Add ability to paste (a list of values) from clipboard for Header Manager

- Updated to HttpComponents Client 4.2.3 (from 4.2.1)

- Updated to HttpComponents Core 4.2.3 (from 4.2.2)

- [Bug 54110](https://bz.apache.org/bugzilla/show_bug.cgi?id=54110) - BSFTestElement and JSR223TestElement should use shared super-class for common fields

- [Bug 54199](https://bz.apache.org/bugzilla/show_bug.cgi?id=54199) - Move to Java 6

- Upgraded to rhino 1.7R4

---
title: Version 2.5
---

# Version 2.5

## Summary of main changes

- The HTTP implementation can now be selected at run-time, and JMeter now also supports Apache HttpComponents HttpClient 4.x.
Note that Commons HttpClient 3.1 is no longer actively developed, and support may be removed from JMeter in a future release.

- The HTTP sampler now allows concurrent downloads of embedded resources in an HTML page

- The HTTP Sampler can now report the size of a request before decompression.

- The JMS and Mail samplers have been improved.

- The new Test Fragment Test Element makes using Include Controllers easier

- There are various improvements to the View Results Tree Listener

- [Bug 30563](https://bz.apache.org/bugzilla/show_bug.cgi?id=30563) - Thread Group should have a start next loop option on Sample Error

- There are two new Thread Group types - setUp and tearDown - which are run before and after the main Thread groups.

- Client-Server mode now supports external stop/shutdown via UDP
multiple JMeter server instances can be started on the same host without needing to change the port property.

- [Bug 50516](https://bz.apache.org/bugzilla/show_bug.cgi?id=50516) - "Host" header in HTTP Header Manager is not included in generated HTTP request

## Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The menu item Options / Choose Language does not change all the displayed text to the new language.
[The behaviour has improved, but language change is still not fully working]
To override the default local language fully, set the JMeter property "language" before starting JMeter.

## Incompatible changes

Unsupported methods are no longer converted to GET by the Commons HttpClient sampler.

Removed method public static long currentTimeInMs().
This has been replaced by the instance method public long currentTimeInMillis().

ProxyControl.getSamplerTypeName() now returns a String rather than an int.
This is internal to the workings of the JMeter Proxy &amp; its GUI, so should not affect any user code.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 50178](https://bz.apache.org/bugzilla/show_bug.cgi?id=50178) - HeaderManager added as child of Thread Group can create concatenated HeaderManager names and OutOfMemoryException

- [Bug 50392](https://bz.apache.org/bugzilla/show_bug.cgi?id=50392) - value is trimmed when sending the request in Multipart

- [Bug 50686](https://bz.apache.org/bugzilla/show_bug.cgi?id=50686) - HeaderManager logging too verbose when merging instances

- [Bug 50963](https://bz.apache.org/bugzilla/show_bug.cgi?id=50963) - AjpSampler throws java.lang.StringIndexOutOfBoundsException

- [Bug 50516](https://bz.apache.org/bugzilla/show_bug.cgi?id=50516) - "Host" header in HTTP Header Manager is not included in generated HTTP request

- [Bug 50544](https://bz.apache.org/bugzilla/show_bug.cgi?id=50544) - In Apache Common Log the HEAD requests cause problems.

- [Bug 51268](https://bz.apache.org/bugzilla/show_bug.cgi?id=51268) - HTTPS request through an invalid proxy causes NullPointerException and does not show in result tree.
Rather than delegating to the JMeter thread handler for "unexpected" failures, ensure all Exceptions generate a sample error.

- [Bug 51275](https://bz.apache.org/bugzilla/show_bug.cgi?id=51275) - Cookie Panel clearGui() sets incorrect default policy in Java 1.6

### Other Samplers

- [Bug 50173](https://bz.apache.org/bugzilla/show_bug.cgi?id=50173) - JDBCSampler discards ResultSet from a PreparedStatement

- Ensure JSR223 Sampler has access to the current SampleResult

- [Bug 50977](https://bz.apache.org/bugzilla/show_bug.cgi?id=50977) - Unable to set TCP Sampler for individual samples

### Controllers

- [Bug 50032](https://bz.apache.org/bugzilla/show_bug.cgi?id=50032) - Last_Sample_Ok along with other controllers doesn't work correctly when the threadgroup has multiple loops

- [Bug 50080](https://bz.apache.org/bugzilla/show_bug.cgi?id=50080) - Transaction controller incorrectly creates samples including timer duration

- [Bug 50134](https://bz.apache.org/bugzilla/show_bug.cgi?id=50134) - TransactionController : Reports bad response time when it contains other TransactionControllers

### Listeners

- [Bug 50367](https://bz.apache.org/bugzilla/show_bug.cgi?id=50367) - Clear / Clear all in View results tree does not clear selected element

### Assertions

- [Bug 51488](https://bz.apache.org/bugzilla/show_bug.cgi?id=51488) - Assertion: Variable name scope is shared among all assertions (and [Bug 51255](https://bz.apache.org/bugzilla/show_bug.cgi?id=51255))

### Functions

- [Bug 50568](https://bz.apache.org/bugzilla/show_bug.cgi?id=50568) - Function __FileToString(): Could not read file when encoding option is blank/empty

### I18N

- [Bug 50811](https://bz.apache.org/bugzilla/show_bug.cgi?id=50811) - Incomplete Spanish translation

### General

- [Bug 49734](https://bz.apache.org/bugzilla/show_bug.cgi?id=49734) - Null pointer exception on stop Threads command (Run &rarr; Stop)

- [Bug 49666](https://bz.apache.org/bugzilla/show_bug.cgi?id=49666) - CSV Header read as data after EOF

- [Bug 45703](https://bz.apache.org/bugzilla/show_bug.cgi?id=45703) - Synchronizing Timer

- [Bug 50088](https://bz.apache.org/bugzilla/show_bug.cgi?id=50088) - fix getAvgPageBytes in SamplingStatCalculator so it returns what it should

- [Bug 50203](https://bz.apache.org/bugzilla/show_bug.cgi?id=50203) Cannot set property "jmeter.save.saveservice.default_delimiter=\t"

- mirror-server.sh - fix classpath to use : separator (not ;)

- [Bug 50286](https://bz.apache.org/bugzilla/show_bug.cgi?id=50286) - URL Re-writing Modifier: extracted jsessionid value is incorrect when is between XML tags

- 
System.nanoTime() tends to drift relative to System.currentTimeMillis().
Change SampleResult to recalculate offset each time.
Also enable reversion to using System.currentTimeMillis() only.

- [Bug 50425](https://bz.apache.org/bugzilla/show_bug.cgi?id=50425) - Remove thread groups from Controller add menu

- 
[Bug 50675](https://bz.apache.org/bugzilla/show_bug.cgi?id=50675) - CVS Data Set Config incompatible with Remote Start
Fixed RMI startup to provide location of JMX file relative to user.dir.

- [Bug 50221](https://bz.apache.org/bugzilla/show_bug.cgi?id=50221) - Renaming elements in the tree does not resize label

- [Bug 51002](https://bz.apache.org/bugzilla/show_bug.cgi?id=51002) - Stop Thread if CSV file is not available. JMeter now treats IOError as EOF.

- Define sun.net.http.allowRestrictedHeaders=true by default. This fixes [Bug 51238](https://bz.apache.org/bugzilla/show_bug.cgi?id=51238).

- [Bug 51645](https://bz.apache.org/bugzilla/show_bug.cgi?id=51645) - CSVDataSet does not read UTF-8 files when file.encoding is UTF-8

## Improvements

### HTTP Samplers

- AJP Sampler now implements Interruptible

- Allow HTTP implementation to be selected at run-time

- [Bug 50684](https://bz.apache.org/bugzilla/show_bug.cgi?id=50684) - Optionally disable Content-Type and Transfer-Encoding in Multipart POST

- [Bug 50943](https://bz.apache.org/bugzilla/show_bug.cgi?id=50943) - Allowing concurrent downloads of embedded resources in html page

- [Bug 50170](https://bz.apache.org/bugzilla/show_bug.cgi?id=50170) - Bytes reported by http sampler is after GUnZipAdd optional properties to allow change the method to get response size

- Hiding the proxy password on HTTP Sampler (just on GUI, not in JMX file)

### Other samplers

- [Bug 49622](https://bz.apache.org/bugzilla/show_bug.cgi?id=49622) - Allow sending messages without a subject (SMTP Sampler)

- [Bug 49603](https://bz.apache.org/bugzilla/show_bug.cgi?id=49603) - Allow accepting expired certificates on Mail Reader Sampler

- [Bug 49775](https://bz.apache.org/bugzilla/show_bug.cgi?id=49775) - Allow sending messages without a body

- [Bug 49862](https://bz.apache.org/bugzilla/show_bug.cgi?id=49862) - Improve SMTPSampler Request output.

- [Bug 50268](https://bz.apache.org/bugzilla/show_bug.cgi?id=50268) - Adds static and dynamic destinations to JMS Publisher

- JMS Subscriber - Add dynamic destination

- [Bug 50666](https://bz.apache.org/bugzilla/show_bug.cgi?id=50666) - JMSSubscriber: support for durable subscriptions

- [Bug 50937](https://bz.apache.org/bugzilla/show_bug.cgi?id=50937) - TCP Sampler does not provide for / honor connect timeout

- [Bug 50569](https://bz.apache.org/bugzilla/show_bug.cgi?id=50569) - Jdbc Request Sampler to optionally store result set object data

- [Bug 51011](https://bz.apache.org/bugzilla/show_bug.cgi?id=51011) - Mail Reader: upon authentication failure, tell what you tried

### Controllers

- [Bug 50475](https://bz.apache.org/bugzilla/show_bug.cgi?id=50475) - Introduction of a Test Fragment Test Element for a better Include flow

### Listeners

- View Results Tree - Add a dialog's text box on "Sampler result tab &rarr; Parsed" to display the long value with a double click on cell

- [Bug 37156](https://bz.apache.org/bugzilla/show_bug.cgi?id=37156) - Formatted view of Request in Results Tree

- [Bug 49365](https://bz.apache.org/bugzilla/show_bug.cgi?id=49365) - Allow result set to be written to file in a path relative to the loaded script

- [Bug 50579](https://bz.apache.org/bugzilla/show_bug.cgi?id=50579) - Error count is long, sample count is int. Changed sample count to long.

- View Results Tree - Add new size fields: response headers and response body (in bytes) - derived from [Bug 43363](https://bz.apache.org/bugzilla/show_bug.cgi?id=43363)

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 48015](https://bz.apache.org/bugzilla/show_bug.cgi?id=48015) - Proposal new icons for pre-processor, post-processor and assertion elements

- [Bug 50962](https://bz.apache.org/bugzilla/show_bug.cgi?id=50962) - SizeAssertionGui validation prevents the use of variables for the size

- Size Assertion - Add response size scope (full, headers, body, code, message) - derived from [Bug 43363](https://bz.apache.org/bugzilla/show_bug.cgi?id=43363)

### Functions

- [Bug 49975](https://bz.apache.org/bugzilla/show_bug.cgi?id=49975) - New function returning the name of the current sampler

### I18N

- Add French translation for the new labels and reduce size for some labels (by abbreviation) on HTTP Sample

### General

- [Bug 30563](https://bz.apache.org/bugzilla/show_bug.cgi?id=30563) - Thread Group should have a start next loop option on Sample Error

- [Bug 50347](https://bz.apache.org/bugzilla/show_bug.cgi?id=50347) - Eclipse setup instructions should remind user to download dependent jars

- [Bug 50490](https://bz.apache.org/bugzilla/show_bug.cgi?id=50490) - Setup and Post Thread Group enhancements for better test flow.

- All BeanShell test elements now have the script variables "prev" and "Label" defined.

- [Bug 50708](https://bz.apache.org/bugzilla/show_bug.cgi?id=50708) - Classpath jar order in NewDriver not alphabetically

- [Bug 50659](https://bz.apache.org/bugzilla/show_bug.cgi?id=50659) - JMeter server does not support concurrent tests - prevent client from starting another

- Added remote shutdown functionality

- Client JMeter engine now supports external stop/shutdown via UDP

- UDP shutdown can now use a range of ports, from jmeterengine.nongui.port=4445 to jmeterengine.nongui.maxport=4455,
allowing multiple JMeter instances on the same host without needing to change the port property.

- Updated to httpcore 4.1.3 and httpclient 4.1.2

## Non-functional changes

- [Bug 50008](https://bz.apache.org/bugzilla/show_bug.cgi?id=50008) - Allow BatchSampleSender to be subclassed

- [Bug 50450](https://bz.apache.org/bugzilla/show_bug.cgi?id=50450) - use System.array copy in jacobi solver as, being native, is more performant.

- [Bug 50487](https://bz.apache.org/bugzilla/show_bug.cgi?id=50487) - runSerialTest verifies objects that never need persisting

- Use Thread.setDefaultUncaughtExceptionHandler() instead of private ThreadGroup

- Update to Commons Net 3.0

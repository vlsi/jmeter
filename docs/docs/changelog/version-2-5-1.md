---
title: Version 2.5.1
---

# Version 2.5.1

## Summary of main changes

- HttpClient4 sampler now re-uses connections properly (previously it would use one per sample, which could quickly cause resource exhaustion).

- Various fixes to JMS samplers

- Functions are no longer spuriously invoked when used with a Configuration element

- WebService sampler GUI has been re-organized for better design and more user-friendliness. Some improvements on WSDL configuration assistant

- Better handling of test shutdown. System.exit now only called if there is no other option; even this can be disabled.

## Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The If Controller may cause an infinite loop if the condition is always false from the first iteration.
A workaround is to add a sampler at the same level as (or superior to) the If Controller.
For example a Test Action sampler with 0 wait time (which doesn't generate a sample),
or a Debug Sampler with all fields set to False (to reduce the sample size).

The menu item Options / Choose Language does not change all the displayed text to the new language.
[The behaviour has improved, but language change is still not fully working]
To override the default local language fully, set the JMeter property "language" before starting JMeter.

## Incompatible changes

The HttpClient4 and Commons HttpClient 3.1 samplers previously used a retry count of 3.
This has been changed to default to 1, to be compatible with the Java implementation.
The retry count can be overridden by setting the relevant JMeter property, for example:

httpclient4.retrycount=3
httpclient3.retrycount=3

## Bug fixes

### HTTP Samplers and Proxy

- Fix HttpClient 4 sampler so it reuses HttpClient instances and connections where possible.

- Temporary fix to HC4 sampler to work round HTTPCLIENT-1120.

- [Bug 51863](https://bz.apache.org/bugzilla/show_bug.cgi?id=51863) - Lots of ESTABLISHED connections with HttpClient 4 implementation (vs HttpClient 3.1 impl)

- [Bug 51750](https://bz.apache.org/bugzilla/show_bug.cgi?id=51750) - Retrieve all embedded resources doesn't follow IFRAME

- [Bug 51752](https://bz.apache.org/bugzilla/show_bug.cgi?id=51752) - HTTP Cache is broken when using "Retrieve all embedded resources" with concurrent pool

- [Bug 39219](https://bz.apache.org/bugzilla/show_bug.cgi?id=39219) - HTTP Server: You can't stop it after File&rarr;Open

- [Bug 51775](https://bz.apache.org/bugzilla/show_bug.cgi?id=51775) - Port number duplicates in Host header when capturing by HttpClient (3.1 and 4.x)

- [Bug 50617](https://bz.apache.org/bugzilla/show_bug.cgi?id=50617) - Monitor Results legend show "dead" server although values from the server are retrieved

### Other Samplers

- [Bug 50424](https://bz.apache.org/bugzilla/show_bug.cgi?id=50424) - Web Methods drop down list box inconsistent

- [Bug 43293](https://bz.apache.org/bugzilla/show_bug.cgi?id=43293) - Java Request fields not cleared when creating new sampler

- [Bug 51830](https://bz.apache.org/bugzilla/show_bug.cgi?id=51830) - Webservice Soap Request triggers too many popups when Webservice WSDL URL is down

- WebService(SOAP) request - add a connect timeout to get the wsdl used to populate Web Methods when server doesn't response

- [Bug 51841](https://bz.apache.org/bugzilla/show_bug.cgi?id=51841) - JMS : If an error occurs in ReceiveSubscriber constructor or Publisher, then Connections will stay open

- [Bug 51691](https://bz.apache.org/bugzilla/show_bug.cgi?id=51691) - Authorization does not work for JMS Publisher and JMS Subscriber

- [Bug 51840](https://bz.apache.org/bugzilla/show_bug.cgi?id=51840) - JMS : Cache of InitialContext has some issues

- [Bug 47888](https://bz.apache.org/bugzilla/show_bug.cgi?id=47888) - JUnit Sampler re-uses test object

### Controllers

- If Controller - Fixed two regressions introduced by [Bug 50032](https://bz.apache.org/bugzilla/show_bug.cgi?id=50032) (see [Bug 50618](https://bz.apache.org/bugzilla/show_bug.cgi?id=50618) too)

- If Controller - Catches a StackOverflowError when a condition returns always false (after at least one iteration with return true) See [Bug 50618](https://bz.apache.org/bugzilla/show_bug.cgi?id=50618)

- [Bug 51869](https://bz.apache.org/bugzilla/show_bug.cgi?id=51869) - NullPointer Exception when using Include Controller

### Listeners

### Assertions

### Functions

- [Bug 48943](https://bz.apache.org/bugzilla/show_bug.cgi?id=48943) - Functions are invoked additional times when used in combination with a Config Element

### I18N

- WebService(SOAP) request - add I18N for some labels

### General

- [Bug 51831](https://bz.apache.org/bugzilla/show_bug.cgi?id=51831) - Cannot disable UDP server or change the maximum UDP port

- [Bug 51821](https://bz.apache.org/bugzilla/show_bug.cgi?id=51821) - Add short-cut for Enabling / Disabling (sub)tree or branches in test plan.

- [Bug 47921](https://bz.apache.org/bugzilla/show_bug.cgi?id=47921) - Variables not released for GC after JMeterThread exits.

- [Bug 51839](https://bz.apache.org/bugzilla/show_bug.cgi?id=51839) - "&hellip; end of run" printed prematurely

- [Bug 51847](https://bz.apache.org/bugzilla/show_bug.cgi?id=51847) - Some JUnit tests are Locale sensitive and fail if Locale is different from US

- [Bug 51855](https://bz.apache.org/bugzilla/show_bug.cgi?id=51855) - Parent samples may have slightly inaccurate elapsed times

- [Bug 51880](https://bz.apache.org/bugzilla/show_bug.cgi?id=51880) - The shutdown command is not working if I invoke it before all the thread are started

- Remote Shut host menu item was not being enabled.

- [Bug 51888](https://bz.apache.org/bugzilla/show_bug.cgi?id=51888) - Occasional deadlock when stopping a testplan

## Improvements

### HTTP Samplers

- [Bug 51380](https://bz.apache.org/bugzilla/show_bug.cgi?id=51380) - Control reuse of cached SSL Context from iteration to iteration

- [Bug 51882](https://bz.apache.org/bugzilla/show_bug.cgi?id=51882) - HTTPHC3Client uses a default retry count of 3, make it configurable; default is now 1

- Change the default HttpClient 4 sampler retry count to 1

### Other samplers

- Beanshell Sampler now supports Interruptible interface

- [Bug 51605](https://bz.apache.org/bugzilla/show_bug.cgi?id=51605) - WebService(SOAP) Request - WebMethod field value changes surreptitiously for all the requests when a value is selected in a request

- WebService(SOAP) Request - Reorganized GUI for better design and more user-friendliness

### Controllers

### Listeners

- [Bug 42246](https://bz.apache.org/bugzilla/show_bug.cgi?id=42246) - Need for a 'auto-scroll' option in "View Results Tree" and "Assertion Results"

- View Results Tree: Regexp Tester - little improvements on user interface

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 51885](https://bz.apache.org/bugzilla/show_bug.cgi?id=51885) - Allow a JMeter Variable as input to XPathExtractor

### Functions

### I18N

### General

- [Bug 51822](https://bz.apache.org/bugzilla/show_bug.cgi?id=51822) - (part 1) save 1 invocation of GuiPackage#getCurrentGui

- Added AsynchSampleSender which sends samples from server to client asynchronously.

- Upgraded to htmlparser 2.1; JavaMail 1.4.4; JUnit 4.9

## Non-functional changes

- [Bug 49976](https://bz.apache.org/bugzilla/show_bug.cgi?id=49976) - FormCharSetFinder visibility is default instead of public. 

- [Bug 50917](https://bz.apache.org/bugzilla/show_bug.cgi?id=50917) - Property CookieManager.save.cookies not honored when set from test plan

- Improve error logging when Javascript errors are detected.

- Updated documentation footer

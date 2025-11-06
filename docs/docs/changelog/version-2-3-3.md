---
title: Version 2.3.3
---

# Version 2.3.3

## Summary of main changes

The handling of test closedown is much improved.
The gradual "Shutdown" command now waits until all threads have stopped,
and does not report an error if threads don't stop within 5 seconds.
The immediate "Stop" command can now be used if "Shutdown" takes too long.
Also the immediate "Stop" command is able to interrupt samplers which support the new Interruptible interface (e.g. HTTP and SOAP, FTP).
This allows immediate completion of pending responses.
Non-GUI mode tests can also now be sent a "Shutdown" or "Stop" message.
 now supports a "Stop Now" action,
as do the  and  Post Processor elements.

HTTP Cookie handling is improved, and HTTP POST can now use variable file names correctly.
HTTP, SOAP/XML-RPC and WebService(SOAP) sampler character encodings updated to be more consistent.
HTTP Samplers now support connection and response timeouts (requires JVM 1.5 for the HTTP Java sampler).
Together with the closedown improvements described above, this should avoid most cases where a test run hangs.
Multiple Header Manager elements are now supported for a single HTTP sampler.
The Proxy Server is improved, and no longer stores "Host" headers by default.

JDBC Request can optionally save the results of Select statements to variables.
JDBC Request now handles quoted strings and UTF-8, and can handle arbitrary variable types.

There are several new [functions](usermanual/functions.html):
__char() function: allows arbitrary Unicode characters to be entered in fields.
__unescape() function: allows Java-escaped strings to be used.
_unescapeHtml() function: decodes Html-encoded text.
__escapeHtml() function: encodes text using Html-encoding.
A reference to a missing function - e.g. ${__missing(a)} - is now treated the same as a missing variable.
Previously the function name - and leading { - were dropped. This makes it easier to debug test plans.

Some Assertions can now be applied to sub-samples as well as (or instead of) just the parent sample.
There is a new  Configuration element.

JMS samplers are much improved (see details below). The  now supports some additional clients and is a bit more flexible.

Client-server mode has been improved, and the server can optionally use a fixed RMI port, which should help with setting up firewalls.

Various I18N changes have been made; language change works better (though not perfect yet).
There are improved French translations as well as new Polish and Brazilian Portuguese translations.

The BeanShell jar is now included with the binary archive; there is no need to download it separately.

## Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a `NullPointerException` if there are two include controllers with the same name.

Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The menu item OptionsChoose Language
does not change all the displayed text to the new language.
[The behaviour has improved, but language change is still not fully working]
To override the default local language fully, set the JMeter property "`language`" before starting JMeter.

## Incompatible changes

When loading sample results from a file, previous results are no longer cleared.
This allows one to merge multiple files.
If the previous behaviour is required,
use the menu item 
  Run
  Clear
  
    
      CtrlShiftE
    
  
 or 
  Run
  Clear All
  
    
     CtrlE
    
  
 before loading the file.

The test elements "Save Results to a file" and "Generate Summary Results" are now shown as Listeners.
They were previously shown as Post-Processors, even though they are implemented as Listeners.

The Cookie Manager no longer saves incoming cookies as variables by default.
To save cookies as variables, define the property "`CookieManager.save.cookies=true`".
Also, cookies names are prefixed with "`COOKIE_`" before they are stored (this avoids accidental corruption of local variables)
To revert to the original behaviour, define the property "`CookieManager.name.prefix= `" (one or more spaces).

The Counter element is now shown as a Configuration element.
It was previously shown as a Pre-Processor, even though it is implemented as a Config item.

The above changes only affect the icons that are displayed and the locations in the GUI pop-up menus.
They do not affect test plans or test behaviour.

The PreProcessors are now invoked directly by the JMeterThread class,
rather than by the TestCompiler#configureSampler() method. (JMeterThread handles the PostProcessors).
This does not affect test plans or behaviour, but could perhaps affect 3rd party add-ons (very unlikely).

Moved the Scoping Rules sub-section from Section 3. "Building a Test Plan"  to Section 4. "Elements of a test plan"

The While controller now trims leading and trailing spaces from the condition value before it is compared
with `LAST`, blank or false.

The "threadName" variable in the _jexl() and __javaScript() functions was previously misspelt as "theadName".

The following deprecated methods were removed from JOrphanUtils: booleanToString(boolean) and valueOf(boolean).
Java 1.4+ has these methods in the Boolean class.

The TestElement interface has some new methods:

- void setProperty(String key, String value, String dflt)

- void setProperty(String key, boolean value, boolean dflt)

- void setProperty(String key, int value)

- void setProperty(String key, int value, int dflt)

- int getPropertyAsInt(String key, int defaultValue)

These are implemented in the AbstractTestElement class which all elements should extend so this is unlikely to cause a problem.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 46332](https://bz.apache.org/bugzilla/show_bug.cgi?id=46332) - HTTP Cookie Manager ignores manually defined cookies (bug introduced in r707810)

- Cookie Manager was not passing cookie policy to runtime threads so they always used compatibility mode

- Add version attribute to JMeter Cookie class (needed for proper cookie support)

- Cookie Manager now saves/restores cookie versions

- Check validity of cookies before storing them.

- HTTPSamplers can now use variables in POSTed file names

- Fix processing of first file name in HTTP POST so functions/variables work (bug introduced with multiple file support)

- [Bug 45831](https://bz.apache.org/bugzilla/show_bug.cgi?id=45831) - WS Sampler reports incorrect throughput if SOAP packet creation fails

- HTTP, SOAP/XML-RPC and WebService(SOAP) sampler character encodings updated to be more consistent

- [Bug 46148](https://bz.apache.org/bugzilla/show_bug.cgi?id=46148) - HTTP sampler fails on SSL requests when logging for jmeter.util is set to DEBUG

- Fix Java 1.6 https error: java.net.SocketException: Unconnected sockets not implemented

- [Bug 46838](https://bz.apache.org/bugzilla/show_bug.cgi?id=46838) - if there was no data, still need to set latency in HTTPSampler

- [Bug 46993](https://bz.apache.org/bugzilla/show_bug.cgi?id=46993) - Saving from Header Manager generates ClassCastException

- 
[Bug 46690](https://bz.apache.org/bugzilla/show_bug.cgi?id=46690) - handling of 302 redirects with invalid relative paths.
JMeter now removes extraneous leading "../" segments (as do many browsers)

- [Bug 44521](https://bz.apache.org/bugzilla/show_bug.cgi?id=44521) - empty variables for a POST in the HTTP Request don't get ignored

- [Bug 46977](https://bz.apache.org/bugzilla/show_bug.cgi?id=46977) - JMeter does not handle HTTP headers not delimited by whitespace

- Fix bug in HTTP file: handling - read bytes, not characters in the default encoding.

- Remove Host from headers saved by the Proxy server, as that will normally be generated by the HTTP stack

- [Bug 45199](https://bz.apache.org/bugzilla/show_bug.cgi?id=45199) - don't try to replace blank variables in Proxy recording

- Change HTTPS spoofing so https: links are replaced even when URL match fails

- [Bug 46436](https://bz.apache.org/bugzilla/show_bug.cgi?id=46436) - Improve error reporting in Proxy Gui

- [Bug 46435](https://bz.apache.org/bugzilla/show_bug.cgi?id=46435) - More verbose error msg for error 501 (Proxy Server)

### Other Samplers

- The "prev" and "sampler" objects are now defined for BSF test elements

- Fix NPE (in DataSourceElement) when using JDBC in client-server mode

- [Bug 45425](https://bz.apache.org/bugzilla/show_bug.cgi?id=45425) - JDBC Request does not support Unicode (changed sampler to use UTF-8)

- [Bug 46522](https://bz.apache.org/bugzilla/show_bug.cgi?id=46522) - Incorrect "Response data" in JDBC sample when column names are missing

- [Bug 46821](https://bz.apache.org/bugzilla/show_bug.cgi?id=46821) - JDBC select request doesn't store the first column in the variables

- [Bug 43791](https://bz.apache.org/bugzilla/show_bug.cgi?id=43791) - ensure QueueReceiver is closed in JMS Point to Point sampler

- [Bug 46016](https://bz.apache.org/bugzilla/show_bug.cgi?id=46016) - avoid possible NPE in JMSSampler

- [Bug 46142](https://bz.apache.org/bugzilla/show_bug.cgi?id=46142) - JMS Receiver now uses MessageID

- [Bug 45458](https://bz.apache.org/bugzilla/show_bug.cgi?id=45458) - Point to Point JMS in combination with authentication

- [Bug 45460](https://bz.apache.org/bugzilla/show_bug.cgi?id=45460) - JMS TestPlan elements depend on resource property

- Various ReceiveSubscriber thread-safety fixes

- JMSPublisher and Subscriber fixes: thread-safety, support dynamic locale changes, locale independence for JMX attribute values

- FTP Sampler now logs out before disconnecting.

- TCP sampler now calls setupTest() and teardownTest() methods

- [Bug 45887](https://bz.apache.org/bugzilla/show_bug.cgi?id=45887) - TCPSampler: timeout property incorrectly set

### Controllers

- Fix NPE when using nested Transaction Controllers with parent samples

- Fix processing of Transaction Controller parent mode so current sampler is set to actual sampler

- [Bug 44941](https://bz.apache.org/bugzilla/show_bug.cgi?id=44941) - Throughput controllers should not share global counters

- [Bug 47120](https://bz.apache.org/bugzilla/show_bug.cgi?id=47120) - Throughput Controller: change percent executions to total executions, the value is stored in a String and interpreted as 1 execution

- [Bug 47150](https://bz.apache.org/bugzilla/show_bug.cgi?id=47150) - ThreadGroup with a loop count of zero causes infinite loop

- [Bug 47009](https://bz.apache.org/bugzilla/show_bug.cgi?id=47009) - Insert parent caused child controller name to be reset

- [Bug 47165](https://bz.apache.org/bugzilla/show_bug.cgi?id=47165) - Using duplicate Module Controller names in command line mode causes NPE

### Listeners

- Mailer Visualizer documentation now agrees with code i.e. failure/success counts need to be exceeded to trigger the mail.

- Mailer Visualizer now shows the failure count

- Mailer Visualiser - fix parsing of multiple e-mail address when using Test button

- [Bug 45976](https://bz.apache.org/bugzilla/show_bug.cgi?id=45976) - incomplete result file when using remote testing with more than 1 server

- Fix Summariser so it works in client server mode

- [Bug 34096](https://bz.apache.org/bugzilla/show_bug.cgi?id=34096) - Duplicate samples not eliminated when writing to CSV files

- Save "Include group Name in Label" setting in Aggregate and Summary reports

- The JMeter variable "sample_variables" is sent to all server instances to ensure the data is available to the client.

- CSVSaveService - check for EOF while reading quoted string

### Assertions

- [Bug 45749](https://bz.apache.org/bugzilla/show_bug.cgi?id=45749) - Response Assertion does not work with a substring that happens to be an invalid RE

- [Bug 45904](https://bz.apache.org/bugzilla/show_bug.cgi?id=45904) - Allow 'Not' Response Assertion to succeed with null sample

### Functions

- Fix regex function - was failing to process $m$mid$n$ correctly

- Protect against possible NPE in RegexFunction if called during test shutdown.

- Avoid NPE if XPath function does not match any nodes

- Correct the variable name "theadName" to "threadName" in the __jexl() and __javaScript() functions

- A reference to a missing function - e.g. ${__missing(a)} - is now treated the same as a missing variable. Previously the function name - and leading { - were dropped.

### I18N

- Fixed language change handling for menus (does not yet work for TestBeans)

- Add HeaderAsPropertyRenderer to support header resource names; use this to fix locale changes in various GUI elements

- [Bug 46424](https://bz.apache.org/bugzilla/show_bug.cgi?id=46424) - corrections to French translation

- [Bug 46844](https://bz.apache.org/bugzilla/show_bug.cgi?id=46844) -  "Library" label in test plan are not I18N

- [Bug 47064](https://bz.apache.org/bugzilla/show_bug.cgi?id=47064) - fixes for Mac LAF

- [Bug 47127](https://bz.apache.org/bugzilla/show_bug.cgi?id=47127) -  Unable to change language to pl_PL

- [Bug 47137](https://bz.apache.org/bugzilla/show_bug.cgi?id=47137) - Labels in View Results Tree aren't I18N

- [Bug 46423](https://bz.apache.org/bugzilla/show_bug.cgi?id=46423) - I18N of Proxy Recorder

- [Bug 45928](https://bz.apache.org/bugzilla/show_bug.cgi?id=45928) - AJP/1.3 Sampler doesn't retrieve its label from messages.properties

### General

- Prompt to overwrite an existing file when first saving a new test plan

- Amend TestBeans to show the correct popup menu for Listeners

- [Bug 45185](https://bz.apache.org/bugzilla/show_bug.cgi?id=45185) - CSV dataset blank delimiter causes OOM

- Fix incorrect GUI classifications:
"Save Results to a file" and "Generate Summary Results" are now shown as Listeners.
"Counter" is now shown as a Configuration element.

- [Bug 41608](https://bz.apache.org/bugzilla/show_bug.cgi?id=41608) - misleading warning log message removed

- [Bug 46359](https://bz.apache.org/bugzilla/show_bug.cgi?id=46359) - BSF JavaScript Preprocessor cannot access sampler variable on first iteration (Implement temporary work-round for BSF-22)

- [Bug 46407](https://bz.apache.org/bugzilla/show_bug.cgi?id=46407) - BSF elements do not load script files, attempt to interpret filename as script

- Better handling of Exceptions during test shutdown

- Fix potential thread safety issue in JMeterThread class

- [Bug 46491](https://bz.apache.org/bugzilla/show_bug.cgi?id=46491) - Incorrect value for the last variable in "CSV Data Set Config" (error in processing quoted strings)

## Improvements

### HTTP Samplers

- [Bug 45479](https://bz.apache.org/bugzilla/show_bug.cgi?id=45479) - Support for multiple HTTP Header Manager nodes

- HTTP Samplers now support connection and request timeouts (requires Java 1.5 for Java Http sampler)

- Apache SOAP 2.3.1 does not give access to HTTP response code/message, so WebService sampler now treats an empty response as an error

- Mirror server now supports "X-Sleep" header - if this is set, the responding thread will wait for the specified number of milliseconds

- [Bug 45694](https://bz.apache.org/bugzilla/show_bug.cgi?id=45694) - Support GZIP compressed logs in Access Log Sampler

### Other samplers

- JDBC Request can optionally save the results of Select statements to variables.

- JDBC Request now handles quoted strings.

- JDBC Request now handles arbitrary variable types.

- LDAP result data now formatted with line breaks

- [Bug 45200](https://bz.apache.org/bugzilla/show_bug.cgi?id=45200) - MailReaderSampler: store the whole MIME message in the SamplerResult

- [Bug 45571](https://bz.apache.org/bugzilla/show_bug.cgi?id=45571) - JMS Sampler correlation enhancement

- [Bug 46030](https://bz.apache.org/bugzilla/show_bug.cgi?id=46030) - Extend TCP Sampler to Support Length-Prefixed Binary Data

- Add classname field to TCP Sampler GUIs

### Controllers

- Allow If Controller to use variable expressions (not just Javascript)

- Trim spaces from While Controller condition before comparing against LAST, blank or false

### Listeners

- Save Responses to a file can save the generated filename(s) to variables.

- Add option to skip suffix generation in Save Responses to a File

- [Bug 43119](https://bz.apache.org/bugzilla/show_bug.cgi?id=43119) - Save Responses to file: optionally omit the file number

- Add BSF Listener element

- [Bug 47176](https://bz.apache.org/bugzilla/show_bug.cgi?id=47176) -  Monitor Results : improve load status graphic

- [Bug 40045](https://bz.apache.org/bugzilla/show_bug.cgi?id=40045) - Allow Results monitor to select a specific connector

- Read XML JTL files more efficiently - pass samples to visualizers as they are read, rather than saving them all and then processing them

### Assertions, Config, Pre- &amp; Post-Processors

- [Bug 45903](https://bz.apache.org/bugzilla/show_bug.cgi?id=45903) - allow Assertions to apply to sub-samples

- Add Body (unescaped) source option to Regular Expression Extractor.

- Random Variable - new configuration element to create random numeric variables

### Functions

- Add OUT and log variables to __jexl() function

- Use Script to evaluate __jexl() function so can have multiple statements.

- Add log variable to the __javaScript() function

- Added __char() function: allows arbitrary Unicode characters to be entered in fields.

- Added __unescape() function: allows Java-escaped strings to be used.

- Added __unescapeHtml() function: decodes Html-encoded text.

- Added __escapeHtml() function: encodes text using Html-encoding.

### I18N

- [Bug 45929](https://bz.apache.org/bugzilla/show_bug.cgi?id=45929) - improved French translations

- [Bug 47132](https://bz.apache.org/bugzilla/show_bug.cgi?id=47132) - Brazilian Portuguese translations

- [Bug 46900](https://bz.apache.org/bugzilla/show_bug.cgi?id=46900) - Polish translations

- Added locales.add property to allow for new Locales

### General

- Allow spaces in JMeter path names (apply work-round for [Java Bug 4496398](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4496398))

- Process JVM_ARGS last in script files so users can override default settings

- [Bug 46636](https://bz.apache.org/bugzilla/show_bug.cgi?id=46636) - Allow server mode to optionally use a fixed rmi port

- Make some samplers interruptible: HTTP (both), SoapSampler, FTPSampler

- Test Action now supports "Stop Now" action, as do the Thread Group and Result Status Post Processor elements

- The Menu items Stop and Shutdown now behave better. Shutdown will now wait until all threads exit.
In GUI mode it can be cancelled and Stop run instead.
Stop now reports if some threads will not exit, and exits if running in non-GUI mode

- Add UDP server to wait for shutdown message if running in non-GUI mode; add UDP client to send the message.

- [Bug 41209](https://bz.apache.org/bugzilla/show_bug.cgi?id=41209) - JLabeled* and ToolTips

- Include BeanShell 2.0b4 jar in binary download.

## Non-functional changes

- Introduce AbstractListenerGui class to make it easier to create Listeners with no visual output

- Assertions are run after PostProcessors; change order of pop-up menus accordingly

- Remove unnecessary clone() methods from function classes

- Moved PreProcessor invocation to JMeterThread class

- Made HashTree Map field final

- Improve performance of calling ResultCollector#isSampleWanted() for multiple samples

- Updated to new versions of: xmlgraphics-commons (1.3.1), jdom (1.1), xstream (1.3.1), velocity (1.6.2)

## Version 2.3.2

### Summary of main changes

Bug fixes

Version 2.3.1 changed the way binary and text content types were determined as far as the View Results Tree Listener was concerned:
originally everything except "image/" content types were considered text, but 2.3.1 introduced a check
for specific content types. This has caused problems,
as several popular types were omitted and these were no longer shown by default in the Response tab.
Rather than try to list all the possible text types, JMeter now just checks for the following binary types:

- image/*

- audio/*

- video/*

All other types are now assumed to be text.

JMeter 2.3.1 introduced a bug in the Cookie Manager
- if "Clear Cookie each iteration" was selected, all threads would see the same cookies.
This bug has been corrected.

Improvements

The Proxy server can now record binary requests.
By default the content types
application/x-amf and application/x-java-serialized-object
will be treated as binary and saved in a file.
To change the content types, update the property **proxy.binary.types**.

The CSV Dataset configuration element has new file sharing options: per thread group, per thread, per identifier.
This allows for more flexible file processing, e.g. each thread can process the same data in the same order.

Switch Controller now works properly with functions and variables,
and the condition can now be a name instead of a number.
Simple Controller now works properly under a While Controller

CSV fields in JTL files can now contain delimiters.
CSV and XML files can now contain additional variables (define the JMeter property **sample_variables**).

Response Assertion can now match on substrings (i.e. not regular expression).
Regex extractor can operate on variables.

XPath processing is improved; Tidy errors are handled better.

Save Table Data buttons added to Summary and Aggregate reports to allow easy saving of the calculated data.

HTTP samplers can now save just the MD5 hash of responses, rather than the entire response.
As a special case, if the HTTP Sampler path starts with "http://" or "https://" then this is used as the full URL,
overriding the host and port fields.
The HTTP Samplers can now POST multiple files.
Webservice(SOAP) Sampler can now load local WSDL files using the "file:" protocol.

A simple HTTP Cache Manager has been added. This needs further development.

View Results Tree Listener now uses Tidy to display XML.
This should allow more content to be displayed successfully.
It also avoids the need to download remote DTD files, which can slow the rendering considerably.

MailReader sampler now supports POP3S and IMAPS protocols. Individual mails are now added as sub-samples.

Various improvements to the BSF Sampler: now supports Jexl, and Javascript bug works properly.
Added BSF PreProcessor, PostProcessor and Assertion test elements.
All now have access to "props" JMeter Properties object.

Number of classes loaded in non-GUI mode is much reduced.

### Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

Once Only controller behaves OK under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The menu item Options / Choose Language does not change all the displayed text to the new language.
To override the default local language, set the JMeter property "language" before starting JMeter.

### Incompatible changes

- 
To reduce the number of classes loaded in non-GUI mode,
Functions will only be found if their classname contains the string
'.functions.' and does not contain the string '.gui.'.
All existing JMeter functions conform to this restriction.
To revert to earlier behaviour, comment or change the properties classfinder.functions.* in jmeter.properties.

- The reference value parameter for intSum() is now optional.
As a consequence, if a variable name is used, it must not be a valid integer.

- The supplied TCPClient implementation no longer treats tcp.eolByte=0 as special.
To skip EOL checking, set tcp.eolByte=1000 (or some other value which is not a valid byte)

- 
Leading and trailing spaces are trimmed from variable names in function calls.
For example, ${__Random(1,63,&nbsp;LOTTERY&nbsp;)} will use the variable 'LOTTERY' rather than '&nbsp;LOTTERY&nbsp;'.

- 
Synchronization has been removed from the RunningSample class (it was not fully threadsafe anyway).
Developers of 3rd party add-ons that use the class may need to synchronize access.

### Bug fixes

- Check that the CSV delimiter is reasonable.

- Fix Switch Controller to work properly with functions and variables

- [Bug 44011](https://bz.apache.org/bugzilla/show_bug.cgi?id=44011) - application/soap+xml not treated as a text type

- [Bug 43427](https://bz.apache.org/bugzilla/show_bug.cgi?id=43427) - Simple Controller is only partly executed in While loop

- [Bug 33954](https://bz.apache.org/bugzilla/show_bug.cgi?id=33954) - Stack Overflow in If/While controllers (may have been fixed previously)

- [Bug 44022](https://bz.apache.org/bugzilla/show_bug.cgi?id=44022) - Memory Leak when closing test plan

- [Bug 44042](https://bz.apache.org/bugzilla/show_bug.cgi?id=44042) - Regression in Cookie Manager (Bug introduced in 2.3.1)

- [Bug 41028](https://bz.apache.org/bugzilla/show_bug.cgi?id=41028) - JMeter server doesn't alert the user when the host is defined as a loopback address

- [Bug 44142](https://bz.apache.org/bugzilla/show_bug.cgi?id=44142) - Function __machineName causes NPE if parameters are omitted.

- [Bug 44144](https://bz.apache.org/bugzilla/show_bug.cgi?id=44144) - JMS point-to-point: request response test does not work

- [Bug 44314](https://bz.apache.org/bugzilla/show_bug.cgi?id=44314) - Not possible to add more than one SyncTimer

- Capture Tidy console error output and log it

- Fix problems using Tidy(tolerant parser) in XPath Assertion and XPath Extractor

- [Bug 44374](https://bz.apache.org/bugzilla/show_bug.cgi?id=44374) - improve timer calculation

- Regular Expression Extractor now deletes all stale variables from previous matches.

- [Bug 44707](https://bz.apache.org/bugzilla/show_bug.cgi?id=44707) - Running remote test changes internal test plan

- [Bug 44625](https://bz.apache.org/bugzilla/show_bug.cgi?id=44625) - Cannot have two or more FTP samplers with different "put" and "get" actions

- [Bug 40850](https://bz.apache.org/bugzilla/show_bug.cgi?id=40850) - BeanShell memory leak

- Ensure ResponseCode and ResponseMessage are set for successful JDBC samples

- FTPSampler now detects and reports failure to open the remote file

- Class directories defined in search_paths and user.classpath no longer need trailing "/"

- [Bug 44852](https://bz.apache.org/bugzilla/show_bug.cgi?id=44852) SOAP/ XML-RPC Request does not show Request details in View Results Tree

- WebService(SOAP) Sampler ResponseData now includes the EOLs sent by server

- [Bug 44910](https://bz.apache.org/bugzilla/show_bug.cgi?id=44910) - close previous socket (if any) in TCP Sampler

- [Bug 44912](https://bz.apache.org/bugzilla/show_bug.cgi?id=44912) - Filter not working in Log Parser

- The BeanShell and BSF component documentation made some incorrect references to the "SampleResponse" object;
this has been corrected to "SampleResult"

- BSF Sampler now works properly with Javascript

- Test Action "Stop Test" now works

- [Bug 42833](https://bz.apache.org/bugzilla/show_bug.cgi?id=42833) - Argument class uses LinkedHashMap in getArgumentsAsMap() to preserve ordering

- [Bug 45093](https://bz.apache.org/bugzilla/show_bug.cgi?id=45093) - SizeAssertion did not call getBytes()

- [Bug 45007](https://bz.apache.org/bugzilla/show_bug.cgi?id=45007) - Rewrite Location headers when using Proxy HTTPS spoofing

- Use CRLF rather than LF in Proxy when returning headers to the client

- [Bug 45007](https://bz.apache.org/bugzilla/show_bug.cgi?id=45007) - fix content length header if content may have been changed

### Improvements

- CSV files can now handle fields with embedded delimiters.

- longSum() function added

- [Bug 43382](https://bz.apache.org/bugzilla/show_bug.cgi?id=43382) - configure Tidy output (warnings, errors) for XPath Assertion and Post-Processor

- [Bug 43984](https://bz.apache.org/bugzilla/show_bug.cgi?id=43984) - trim spaces from port field

- Add optional comment to __log() function

- Make Random function variable name optional

- Reduce class loading in non-GUI mode by only looking for Functions in class names
that contain '.functions.' and don't contain '.gui.'

- [Bug 43379](https://bz.apache.org/bugzilla/show_bug.cgi?id=43379) - Switch Controller now supports selection by name as well as number

- Can specify list of variable names to be written to JTL files (CSV and XML format)

- Now checks that the remoteStart options -r and -R are only used with non_GUI -n option

- [Bug 44184](https://bz.apache.org/bugzilla/show_bug.cgi?id=44184) - Allow header to be saved with Aggregate Graph data

- Added "Save Table Data" buttons to Aggregate and Summary Reports - save table as CSV format with header

- Allow most functions to be used on the Test Plan.
Note __evalVar(), __split() and __regex() cannot be used on the Test Plan.

- Allow Global properties to be loaded from a file, e.g. -Gglobal.properties

- Add "Substring" option to Response Assertion

- [Bug 44378](https://bz.apache.org/bugzilla/show_bug.cgi?id=44378) - Turkish localisation

- Add optional output variable name to Jexl function

- Add application/vnd.wap.xhtml+xml as a text type

- Add means to override maximum display size in View Results Tree - set the property: view.results.tree.max_size

- Use Tidy to display XML in View Results Tree Listener (avoids fetching DTDs)

- [Bug 44487](https://bz.apache.org/bugzilla/show_bug.cgi?id=44487) - German translation

- 
As a special case, if the HTTP Sampler path starts with "http://" or "https://" then this is used as the full URL.

- [Bug 44575](https://bz.apache.org/bugzilla/show_bug.cgi?id=44575) - Result Saver can now save only successful results

- [Bug 44650](https://bz.apache.org/bugzilla/show_bug.cgi?id=44650) - CSV Dataset now handles quoted column values

- [Bug 44600](https://bz.apache.org/bugzilla/show_bug.cgi?id=44600) - 1-ms resolution timer when running with Java 1.5+

- [Bug 44632](https://bz.apache.org/bugzilla/show_bug.cgi?id=44632) - Text input enhancement to FTP Sampler

- [Bug 42204](https://bz.apache.org/bugzilla/show_bug.cgi?id=42204) - add thread group name to Aggregate and Summary reports

- FTP Sampler sets latency = time to login

- FTP Sampler sets a URL if it can

- [Bug 41921](https://bz.apache.org/bugzilla/show_bug.cgi?id=41921) - add option for samplers to store MD5 of response; done for HTTP Samplers.

- Regex Function can now also be applied to a variable rather than just the previous sample result.

- Remove HTML Parameter Mask,HTTP User Parameter Modifier from menus as they are deprecated

- [Bug 44807](https://bz.apache.org/bugzilla/show_bug.cgi?id=44807) - allow session ids to be terminated by backslash

- [Bug 44784](https://bz.apache.org/bugzilla/show_bug.cgi?id=44784) - allow for broken server returning additional charset

- Added TESTSTART.MS property / variable = test start time in milliseconds

- Add POP3S and IMAPS protocols to Mail Reader Sampler.

- Mail Reader Sampler now creates a sub-sample for each mail.

- The supplied TCPClient implementation no longer treats tcp.eolByte=0 as special.
To skip EOL checking, set tcp.eolByte=1000 (or some other value which is not a valid byte)

- JUnit sampler GUI now also finds Test classes defined in user.classpath

- 
Leading and trailing spaces are trimmed from variable names in function calls.
For example, ${__Random(1,63,&nbsp;LOTTERY&nbsp;)} will use the variable 'LOTTERY' rather than '&nbsp;LOTTERY&nbsp;'

- Webservice(SOAP) Sampler can now load local WSDL files using the file: protocol

- [Bug 44872](https://bz.apache.org/bugzilla/show_bug.cgi?id=44872) - Add "All Files" filter to Open File dialogs

- Mirror server can now be run independently (mirror-server.cmd and mirror-server.sh)

- [Bug 19128](https://bz.apache.org/bugzilla/show_bug.cgi?id=19128) - Added multiple file POST support to HTTP Samplers

- Allow use of special name LAST to mean the last test run; applies to -t, -l, -j flags

- [Bug 44418](https://bz.apache.org/bugzilla/show_bug.cgi?id=44418)/42178 - CSV Dataset file handling improvements

- Give BeanShell, Javascript and Jexl functions access to JMeter properties via the "props" object

- Give BSF Sampler access to JMeter Properties via "props" object

- Add Jexl as a supported BSF Sampler language

- Give Beanshell test elements access to JMeter Properties via "props" object

- Added BSF PreProcessor, PostProcessor and Assertion test elements

- All BSF elements now have access to System.out via the variable "OUT"

- Summariser updated to handle variable names

- Synchronisation added to Summary and Aggregate Report to try to prevent occasional lost samples

- [Bug 44808](https://bz.apache.org/bugzilla/show_bug.cgi?id=44808),[Bug 39641](https://bz.apache.org/bugzilla/show_bug.cgi?id=39641) - Proxy support for binary requests

- [Bug 28502](https://bz.apache.org/bugzilla/show_bug.cgi?id=28502) - HTTP Resource Cache

### Non-functional changes

- Better handling of MirrorServer startup problems and improved unit test.

- Build process now detects missing 3rd party libraries and reports need for both binary and source archives

- Skip BeanShell tests if jar is not present

- Update to Xerces 2.9.1, Xalan 2.7.1, Commons IO 1.4, Commons Lang 2.4, Commons-Logging 1.1.1, XStream 1.3, XPP3 1.1.4c

- Use properties for log/logn function descriptions

- Check that all jmx files in the demos directory can be loaded OK

- Update copyright to 2008; use copy tag instead of numeric character in HTML output

- Methods called from constructors must not be overridable: make GUI init methods private

- Make static variables final if possible

- Split changes into current and previous

### Version 2.3.1

Summary of changes

JMeter Proxy

The Proxy spoof function was broken in 2.3; it has been fixed.
Spoof now supports an optional parameter to limit spoofing to particular URLs.
This is useful for HTTPS pages that have insecure content - e.g. images/stylesheets may be accessed using HTTP.
Spoofed responses now drop the default port (443) from https links to make them work better.

Ignored proxy samples are now visible in Listeners - the label is enclosed in [ and ] as an indication.
Proxy documentation has been improved.

GUI changes

The Add menus show element types in the order in which they are processed
- see [Test Plan Execution Order](usermanual/test_plan.html#executionorder).
It is no longer possible to add test elements to inappropriate parts of the tree
- e.g. samplers cannot be added directly under a test plan.
This also applies to Paste and drag and drop.

The File menu now supports a "Revert" option, which reloads the current file.
Also the last few file names used are remembered for easy reloading.

The Options Menu now supports Collapse All and Expand All items to collapse and expand the test tree.

Remote testing

The JMeter server now starts the RMI server directly (by default).
This simplifies testing, and means that the RMI server will be stopped when the server stops.

Functions can now be used in Listener filenames (variables do not work).

Command-line option -G can now be used to define properties for remote servers.
Option -X can be used to stop a remote server after a non-GUI run.
Server can be set to automatically exit after a single test (set property server.exitaftertest=true).

Other enhancements

JMeter startup no longer loads as many classes; this should reduce memory requirements.

Parameter and file support added to all BeanShell elements.
Javascript function now supports access to JMeter objects;
Jexl function always did have access, but the documentation has now been included.
New functions __eval() and __evalVar() for evaluating variables.

CSV files with the correct header column names are now automatically recognised when loaded.
There is no need to configure the properties.

The hostname can now be saved in CSV and XML output files.
New "Successes only" option added when saving result files.
Errors / Successes only option is now supported when loading XML and CSV files.

General documentation improvements.

HTTP

PUT and DELETE should now work properly.
Cookie Manager no longer clears manually entered cookies.

Now handles the META tag http-equiv charset

JDBC

JDBC Sampler now allows INOUT and OUT parameters for Called procedures.
JDBC Sampler now allows per-thread connections - set Max Connections = 0 in JDBC Config.

Incompatible changes

- JMeter server now creates the RMI registry by default.
If the RMI registry has already been started externally, this will generate a warning message, but the server will continue.
This should not affect JMeter testing.
However, if you are also using the RMI registry for other applications there may be problems.
For example, when the JMeter server shuts down it will stop the RMI registry.
Also user-written command files may need to be adjusted (the ones supplied with JMeter have been updated).
To revert to the earlier behaviour, define the JMeter property: **server.rmi.create=false**.

- The Proxy server removes If-Modified-Since and If-None-Match headers from generated Header Managers.
To revert to the previous behaviour, define the property proxy.headers.remove with no value

Bug fixes

- [Bug 43430](https://bz.apache.org/bugzilla/show_bug.cgi?id=43430) - Count of active threads is incorrect for remote samples

- Throughput Controller was not working for "all thread" counts

- If a POST body is built from parameter values only, these are now encoded if the checkbox is set.

- [Bug 43584](https://bz.apache.org/bugzilla/show_bug.cgi?id=43584) - Assertion Failure Message contains a comma that is also used as the delimiter for CSV files

- HTTP Mirror Server now always returns the exact same content, it used to return incorrect data if UTF-8 encoding was used for HTTP POST body, for example

- [Bug 43612](https://bz.apache.org/bugzilla/show_bug.cgi?id=43612) - HTTP PUT does not honor request parameters

- [Bug 43694](https://bz.apache.org/bugzilla/show_bug.cgi?id=43694) - ForEach Controller (empty collection processing error)

- [Bug 42012](https://bz.apache.org/bugzilla/show_bug.cgi?id=42012) - Variable Listener filenames do not get processed in remote tests.
Filenames can now include function references; variable references do not work.

- Ensure Listener nodes get own save configuration when copy-pasted

- Correct Proxy Server include and exclude matching description - port and query are included, contrary to previously documented.

- Aggregate Graph and Aggregate Report Column Header is KB/Sec; fixed the values to be KB rather than bytes

- 
Fix SamplingStatCalculator so it no longer adds elapsed time to endTime, as this is handled by SampleResult.
This corrects discrepancies between Summary Report and Aggregate Report throughput calculation.

- Default HTTPSampleResult to ISO-8859-1 encoding

- Fix default encoding for blank encoding

- Fix Https spoofing (port problem) which was broken in 2.3

- Fix HTTP (Java) sampler so http.java.sampler.retries means retries, i.e. does not include initial try

- Fix SampleResult dataType checking to better detect TEXT documents

Improvements

- Add run_gui Ant target, to package and then start the JMeter GUI from Ant

- Add File&rarr;Revert to easily drop the current changes and reload the project file currently loaded

- [Bug 31366](https://bz.apache.org/bugzilla/show_bug.cgi?id=31366) - Remember recently opened file(s)

- [Bug 43351](https://bz.apache.org/bugzilla/show_bug.cgi?id=43351) - Add support for Parameters and script file to all BeanShell test elements

- SaveService no longer needs to instantiate classes

- New functions: __eval() and __evalVar()

- Menu items now appear in execution order

- Test Plan items can now only be dropped/pasted/merged into parts of the tree where they are allowed

- Property Display to show the value of System and JMeter properties and allow them to be changed

- [Bug 43451](https://bz.apache.org/bugzilla/show_bug.cgi?id=43451) - Allow Regex Extractor to operate on Response Code/Message

- JDBC Sampler now allows INOUT and OUT parameters for Called procedures

- JDBC Sampler now allows per-thread connections

- Cookie Manager not longer clears cookies defined in the GUI

- HTTP Parameters without names are ignored (except for POST requests with no file)

- "Save Selection As" added to main menu; now checks only item is selected

- Test Plan now has Paste menu item (paste was already supported via ^V)

- If the default delimiter does not work when loading a CSV file, guess the delimiter by analysing the header line.

- Add optional "loopback" protocol for HttpClient sampler

- HTTP Mirror Server now supports blocking waiting for more data to appear, if content-length header is present in request

- HTTP Mirror Server GUI now has the Start and Stop buttons in a more visible place

- Server mode now creates the RMI registry; to disable set the JMeter property server.rmi.create=false

- HTTP Sampler now supports using MIME Type field to specify content-type request header when body is constructed from parameter values

- Enable exit after a single server test - define JMeter property server.exitaftertest=true

- Added -G option to set properties in remote servers

- Added -X option to stop remote servers after non-GUI run

- [Bug 43485](https://bz.apache.org/bugzilla/show_bug.cgi?id=43485) - Ability to specify keep-alive on SOAP/XML-RPC request

- [Bug 43678](https://bz.apache.org/bugzilla/show_bug.cgi?id=43678) - Handle META tag http-equiv charset

- [Bug 42555](https://bz.apache.org/bugzilla/show_bug.cgi?id=42555) - [I18N] Proposed corrections for the french translation

- [Bug 43727](https://bz.apache.org/bugzilla/show_bug.cgi?id=43727) - Test Action does not support variables or functions

- The Proxy server removes If-Modified-Since and If-None-Match headers from generated Header Managers by default.
To change the list of removed headers, define the property proxy.headers.remove as a comma-separated list of headers to remove

- The javaScript function now has access to JMeter variables and context etc. See [JavaScript function](usermanual/functions.html#__javaScript)

- Use drop-down list for BSF Sampler language field

- Add hostname to items that can be saved in CSV and XML output files.

- Errors only flag is now supported when loading XML and CSV files

- Ensure ResultCollector uses SaveService encoding

- Proxy now rejects attempts to use it with https

- Proxy spoofing can now use RE matching to determine which urls to spoof (useful if images are not https)

- Proxy spoofing now drops the default HTTPS port (443) when converting https: links to http:

- Add Successes Only logging and display

- The JMeter log file name is formatted as a SimpleDateFormat (applied to the current date) if it contains paired single-quotes,  .e.g. 'jmeter_'yyyyMMddHHmmss'.log'

- Added Collapse All and Expand All Option menu items

- Allow optional definition of extra content-types that are viewable as text

Non-functional Improvements

- Functor code tightened up; Functor can now be used with interfaces, as well as pre-defined targets and parameters.

- Save graphics function now prompts before overwriting an existing file

- Debug Sampler and Debug PostProcessor added.

- Fixed up method names in Calculator and SamplingStatCalculator

- Tidied up Listener documentation.

### Version 2.3

### Fixes since 2.3RC4

Bug fixes

- Fix NPE in SampleResultConverter - XStream PrettyPrintWriter cannot handle nulls

- If Java HTTP sampler sees null ResponseMessage, replace with HTTP header

- [Bug 43332](https://bz.apache.org/bugzilla/show_bug.cgi?id=43332) - 2.3RC4 does not clear Guis based on TestBean

- [Bug 42948](https://bz.apache.org/bugzilla/show_bug.cgi?id=42948) - Problems with Proxy gui table fields in Java 1.6

- Fixup broken jmeter-server script

- [Bug 43364](https://bz.apache.org/bugzilla/show_bug.cgi?id=43364) - option to revert If Controller to pre 2.3RC3 behaviour

- [Bug 43449](https://bz.apache.org/bugzilla/show_bug.cgi?id=43449) - Statistical Remote mode does not handle Latency

- [Bug 43450](https://bz.apache.org/bugzilla/show_bug.cgi?id=43450) (partial fix) - Allow SampleCount and ErrorCount to be saved to/restored from files

Improvements

- Add nameSpace option to XPath extractor

- Add NULL parameter option to JDBC sampler

- Add documentation links for Rhino and BeanShell to functions; clarify variables and properties

- Ensure uncaught exceptions are logged

- Look for user.properties and system.properties in JMeter bin directory if not found locally

Fixes since 2.3RC3

- Fixed NPE in Summariser (bug introduced in 2.3RC3)

- Fixed setup of proxy port (bug introduced in 2.3RC3)

- Fixed errors when running non-GUI on a headless host (bug introduced in 2.3RC3)

- [Bug 43054](https://bz.apache.org/bugzilla/show_bug.cgi?id=43054) - SSLManager causes stress tests to saturate and crash (bug introduced in 2.3RC3)

- Clarified HTTP Request Defaults usage of the port field

- [Bug 43006](https://bz.apache.org/bugzilla/show_bug.cgi?id=43006) - NPE if icon.properties file not found

- [Bug 42918](https://bz.apache.org/bugzilla/show_bug.cgi?id=42918) - Size Assertion now treats an empty response as having zero length

- [Bug 43007](https://bz.apache.org/bugzilla/show_bug.cgi?id=43007) - Test ends before all threadgroups started

- Fix possible NPE in HTTPSampler2 if 302 does not have Location header.

- [Bug 42919](https://bz.apache.org/bugzilla/show_bug.cgi?id=42919) - Failure Message blank in CSV output [now records first non-blank message]

- Add link to Extending JMeter PDF

- Allow for quoted charset in Content-Type parsing

- [Bug 39792](https://bz.apache.org/bugzilla/show_bug.cgi?id=39792) - ClientJMeter synchronisation needed

- [Bug 43122](https://bz.apache.org/bugzilla/show_bug.cgi?id=43122) - GUI changes not always picked up when short-cut keys used (bug introduced in 2.3RC3)

- [Bug 42947](https://bz.apache.org/bugzilla/show_bug.cgi?id=42947) - TestBeanGUI changes not picked up when short-cut keys used

- Added serializer.jar (needed for update to xalan 2.7.0)

- [Bug 38687](https://bz.apache.org/bugzilla/show_bug.cgi?id=38687) - Module controller does not work in non-GUI mode

Improvements since 2.3RC3

- Add stop thread option to CSV Dataset

- Updated commons-httpclient to 3.1

- [Bug 28715](https://bz.apache.org/bugzilla/show_bug.cgi?id=28715) - allow variable cookie values (set CookieManager.allow_variable_cookies=false to disable)

- [Bug 40873](https://bz.apache.org/bugzilla/show_bug.cgi?id=40873) - add JMS point-to-point non-persistent delivery option

- [Bug 43283](https://bz.apache.org/bugzilla/show_bug.cgi?id=43283) - Save action adds .jmx if not present; checks for existing file on Save As

- ControlA key does not work for Save All As;
    changed to ControlShiftS

- [Bug 40991](https://bz.apache.org/bugzilla/show_bug.cgi?id=40991) - Allow Assertions to check Headers

### Version 2.3RC3

Known problems/restrictions:

The JMeter remote server does not support multiple concurrent tests - each remote test should be run in a separate server.
Otherwise tests may fail with random Exceptions, e.g. ConcurrentModification Exception in StandardJMeterEngine.
See [Bug 43168](https://bz.apache.org/bugzilla/show_bug.cgi?id=43168).

The default HTTP Request (not HTTPClient) sampler may not work for HTTPS connections via a proxy.
This appears to be due to a Java bug, see [Bug 39337](https://bz.apache.org/bugzilla/show_bug.cgi?id=39337).
To avoid the problem, try a more recent version of Java, or switch to the HTTPClient version of the HTTP Request sampler.

Transaction Controller parent mode does not support nested Transaction Controllers.
Doing so may cause a Null Pointer Exception in TestCompiler.

Thread active counts are always zero in CSV and XML files when running remote tests.

The property file_format.testlog=2.1 is treated the same as 2.2.
However JMeter does honour the 3 testplan versions.

[Bug 22510](https://bz.apache.org/bugzilla/show_bug.cgi?id=22510) - JMeter always uses the first entry in the keystore.

Remote mode does not work if JMeter is installed in a directory where the path name contains spaces.

BeanShell test elements leak memory.
This can be reduced by using a file instead of including the script in the test element.

Variables and functions do not work in Listeners in client-server (remote) mode so they cannot be used
to name log files in client-server mode.

CSV Dataset variables are defined after configuration processing is completed,
so they cannot be used for other configuration items such as JDBC Config.
(see [Bug 40394](https://bz.apache.org/bugzilla/show_bug.cgi?id=40394))

Summary of changes (for more details, see below)

Some of the main enhancements are:

- Htmlparser 2.0 now used for parsing

- HTTP Authorization now supports domain and realm

- HttpClient options can be specified via httpclient.parameters file

- HttpClient now behaves the same as Java Http for SSL certificates

- HTTP Mirror Server to allow local testing of HTTP samplers

- HTTP Proxy supports XML-RPC recording, and other proxy improvements

- __V() function allows support of nested variable references

- LDAP Ext sampler optionally parses result sets and supports secure mode

- FTP Sampler supports Ascii/Binary mode and upload

- Transaction Controller now optionally generates a Sample with subresults

- HTTPS session contexts are now per-thread, rather than shared. This gives better emulation of multiple users

- BeanShell elements now support ThreadListener and TestListener interfaces

- Coloured icons in Tree View Listener and elsewhere to better differentiate failed samples.

The main bug fixes are:

- HTTPS (SSL) handling now much improved

- Various Remote mode bugs fixed

- ControlC
    and ControlV now work in the test tree

- Latency and Encoding now available in CSV log output

- Test elements no longer default to previous contents; test elements no longer cleared when changing language.

Incompatible changes (usage):

N.B. The javax.net.ssl properties have been moved from jmeter.properties to system.properties,
and will no longer work if defined in jmeter.properties.

The new arrangement is more flexible, as it allows arbitrary system properties to be defined.

SSL session contexts are now created per-thread, rather than being shared.
This generates a more realistic load for HTTPS tests.
The change is likely to slow down tests with many SSL threads.
The original behaviour can be enabled by setting the JMeter property:

https.sessioncontext.shared=true

The LDAP Extended Sampler now uses the same panel for both Thread Bind and Single-Bind tests.
This means that any tests using the Single-bind test will need to be updated to set the username and password.

[Bug 41140](https://bz.apache.org/bugzilla/show_bug.cgi?id=41140): JMeterThread behaviour was changed so that PostProcessors are run in forward order
(as they appear in the test plan) rather than reverse order as previously.
The original behaviour can be restored by setting the following JMeter property:

jmeterthread.reversePostProcessors=true

The HTTP Authorization Manager now has extra columns for domain and realm,
so the temporary work-round of using '\' and '@' in the username to delimit the domain and realm
has been removed.

ControlZ no longer used for Remote Start All - this
now uses ControlShiftR

HttpClient now uses pre-emptive authentication.
This can be changed by setting the following:

jmeter.properties:
httpclient.parameters.file=httpclient.parameters

httpclient.parameters:
http.authentication.preemptive$Boolean=false

The port field in HTTP Request Defaults is no longer ignored for https samplers if it is set to 80.

Incompatible changes (development):

**N.B.**The clear() method was defined in the following interfaces: Clearable, JMeterGUIComponent and TestElement.
The methods serve different purposes, so two of them were renamed:
the Clearable method is now clearData() and the JMeterGUIComponent method is now clearGui().
3rd party add-ons may need to be rebuilt.

Calculator and SamplingStatCalculator classes no longer provide any formatting of their data.
Formatting should now be done using the jorphan.gui Renderer classes.

Removed deprecated method JMeterUtils.split() - use JOrphanUtils version instead.

Removed method saveUsingJPEGEncoder() from SaveGraphicsService.
It was unused so far, and used the only Sun-specific class in JMeter.

New functionality/improvements:

- Add Domain and Realm support to HTTP Authorization Manager

- HttpClient now behaves the same as the JDK http sampler for invalid certificates etc

- Added httpclient.parameters.file to allow HttpClient parameters to be defined

- [Bug 33964](https://bz.apache.org/bugzilla/show_bug.cgi?id=33964) - Http Requests can send a file as the entire post body if name/type are omitted

- [Bug 41705](https://bz.apache.org/bugzilla/show_bug.cgi?id=41705) - add content-encoding option to HTTP samplers for POST requests

- [Bug 40933](https://bz.apache.org/bugzilla/show_bug.cgi?id=40933),[Bug 40945](https://bz.apache.org/bugzilla/show_bug.cgi?id=40945) - optional RE matching when retrieving embedded resource URLs

- [Bug 27780](https://bz.apache.org/bugzilla/show_bug.cgi?id=27780) - (patch 19936) create multipart/form-data HTTP request without uploading file

- [Bug 42098](https://bz.apache.org/bugzilla/show_bug.cgi?id=42098) - Use specified encoding for parameter values in HTTP GET

- [Bug 42506](https://bz.apache.org/bugzilla/show_bug.cgi?id=42506) - JMeter threads now use independent SSL sessions

- [Bug 41707](https://bz.apache.org/bugzilla/show_bug.cgi?id=41707) - HTTP Proxy XML-RPC support

- [Bug 41880](https://bz.apache.org/bugzilla/show_bug.cgi?id=41880) - Add content-type filtering to HTTP Proxy Server

- [Bug 41876](https://bz.apache.org/bugzilla/show_bug.cgi?id=41876) - Add more options to control what the HTTP Proxy generates

- [Bug 42158](https://bz.apache.org/bugzilla/show_bug.cgi?id=42158) - Improve support for multipart/form-data requests in HTTP Proxy server

- [Bug 42173](https://bz.apache.org/bugzilla/show_bug.cgi?id=42173) - Let HTTP Proxy handle encoding of request, and undecode parameter values

- [Bug 42674](https://bz.apache.org/bugzilla/show_bug.cgi?id=42674) - default to pre-emptive HTTP authorisation if not specified

- Support "file" protocol in HTTP Samplers

- Http Autoredirects are now enabled by default when creating new samplers

- [Bug 40103](https://bz.apache.org/bugzilla/show_bug.cgi?id=40103) - various LDAP enhancements

- [Bug 40369](https://bz.apache.org/bugzilla/show_bug.cgi?id=40369) - LDAP: Stable search results in sampler

- [Bug 40381](https://bz.apache.org/bugzilla/show_bug.cgi?id=40381) - LDAP: more descriptive strings

- BeanShell Post-Processor no longer ignores samples with zero-length result data

- Added beanshell.init.file property to run a BeanShell script at startup

- [Bug 39864](https://bz.apache.org/bugzilla/show_bug.cgi?id=39864) - BeanShell init files now found from current or bin directory

- BeanShell elements now support ThreadListener and TestListener interfaces

- BSF Sampler passes additional variables to the script

- Added timeout for WebService (SOAP) Sampler

- [Bug 40825](https://bz.apache.org/bugzilla/show_bug.cgi?id=40825) - Add JDBC prepared statement support

- Extend JDBC Sampler: Commit, Rollback, AutoCommit

- [Bug 41457](https://bz.apache.org/bugzilla/show_bug.cgi?id=41457) - Add TCP Sampler option to not re-use connections

- [Bug 41522](https://bz.apache.org/bugzilla/show_bug.cgi?id=41522) - Use JUnit sampler name in sample results

- [Bug 42223](https://bz.apache.org/bugzilla/show_bug.cgi?id=42223) - FTP Sampler can now upload files

- [Bug 40804](https://bz.apache.org/bugzilla/show_bug.cgi?id=40804) - Change Counter default to max = Long.MAX_VALUE

- Use property jmeter.home (if present) to override user.dir when starting JMeter

- New -j option to easily change jmeter log file

- HTTP Mirror Server Workbench element

- [Bug 41253](https://bz.apache.org/bugzilla/show_bug.cgi?id=41253) - extend XPathExtractor to work with non-NodeList XPath expressions

- [Bug 42088](https://bz.apache.org/bugzilla/show_bug.cgi?id=42088) - Add XPath Assertion for booleans

- Added __V variable function to resolve nested variable names

- [Bug 40369](https://bz.apache.org/bugzilla/show_bug.cgi?id=40369) - Equals Response Assertion

- [Bug 41704](https://bz.apache.org/bugzilla/show_bug.cgi?id=41704) - Allow charset encoding to be specified for CSV DataSet

- [Bug 41259](https://bz.apache.org/bugzilla/show_bug.cgi?id=41259) - Comment field added to all test elements

- Add standard deviation to Summary Report

- [Bug 41873](https://bz.apache.org/bugzilla/show_bug.cgi?id=41873) - Add name to AssertionResult and display AssertionResult in ViewResultsFullVisualizer

- [Bug 36755](https://bz.apache.org/bugzilla/show_bug.cgi?id=36755) - Save XML test files with UTF-8 encoding

- Use ISO date-time format for Tree View Listener (previously the year was not shown)

- Improve loading of CSV files: if possible, use header to determine format; guess timestamp format if not milliseconds

- [Bug 41913](https://bz.apache.org/bugzilla/show_bug.cgi?id=41913) - TransactionController now creates samples as sub-samples of the transaction

- [Bug 42582](https://bz.apache.org/bugzilla/show_bug.cgi?id=42582) - JSON pretty printing in Tree View Listener

- [Bug 40099](https://bz.apache.org/bugzilla/show_bug.cgi?id=40099) - Enable use of object variable in ForEachController

- [Bug 39693](https://bz.apache.org/bugzilla/show_bug.cgi?id=39693) - View Result Table uses icon instead of check box

- [Bug 39717](https://bz.apache.org/bugzilla/show_bug.cgi?id=39717) - use icons in the results tree

- [Bug 42247](https://bz.apache.org/bugzilla/show_bug.cgi?id=42247) - improve HCI

- Allow user to cancel out of Close dialogue

Non-functional improvements:

- Functor calls can now be unit tested

- Replace com.sun.net classes with javax.net

- Extract external jar definitions into build.properties file

- Use specific jar names in build classpaths so errors are detected sooner

- Tidied up ORO calls; now only one cache, size given by oro.patterncache.size, default 1000

- [Bug 42326](https://bz.apache.org/bugzilla/show_bug.cgi?id=42326) - Order of elements in .jmx files changes

External jar updates:

- Htmlparser 2.0-20060923

- xstream 1.2.1/xpp3_min-1.1.3.4.O

- Batik 1.6

- BSF 2.4.0

- commons-collections 3.2

- commons-httpclient-3.1-rc1

- commons-jexl 1.1

- commons-lang-2.3 (added)

- JUnit 3.8.2

- velocity 1.5

- commons-io 1.3.1 (added)

Bug fixes:

- [Bug 39773](https://bz.apache.org/bugzilla/show_bug.cgi?id=39773) - NTLM now needs local host name - fix other call

- [Bug 40438](https://bz.apache.org/bugzilla/show_bug.cgi?id=40438) - setting "httpclient.localaddress" has no effect

- [Bug 40419](https://bz.apache.org/bugzilla/show_bug.cgi?id=40419) - Chinese messages translation fix

- [Bug 39861](https://bz.apache.org/bugzilla/show_bug.cgi?id=39861) - fix typo

- [Bug 40562](https://bz.apache.org/bugzilla/show_bug.cgi?id=40562) - redirects no longer invoke RE post processors

- [Bug 40451](https://bz.apache.org/bugzilla/show_bug.cgi?id=40451) - set label if not set by sampler

- Fix NPE in CounterConfig.java in Remote mode

- [Bug 40791](https://bz.apache.org/bugzilla/show_bug.cgi?id=40791) - Calculator used by Summary Report

- [Bug 40772](https://bz.apache.org/bugzilla/show_bug.cgi?id=40772) - correctly parse missing fields in CSV log files

- [Bug 40773](https://bz.apache.org/bugzilla/show_bug.cgi?id=40773) - XML log file timestamp not parsed correctly

- [Bug 41029](https://bz.apache.org/bugzilla/show_bug.cgi?id=41029) - JMeter -t fails to close input JMX file

- [Bug 40954](https://bz.apache.org/bugzilla/show_bug.cgi?id=40954) - Statistical mode in distributed testing shows wrong results

- Fix ClassCast Exception when using sampler that returns null, e..g TestAction

- [Bug 41140](https://bz.apache.org/bugzilla/show_bug.cgi?id=41140) - Post-processors are run in reverse order

- [Bug 41277](https://bz.apache.org/bugzilla/show_bug.cgi?id=41277) - add Latency and Encoding to CSV output

- [Bug 41414](https://bz.apache.org/bugzilla/show_bug.cgi?id=41414) - Mac OS X may add extra item to -jar classpath

- Fix NPE when saving thread counts in remote testing

- [Bug 34261](https://bz.apache.org/bugzilla/show_bug.cgi?id=34261) - NPE in HtmlParser (allow for missing attributes)

- [Bug 40100](https://bz.apache.org/bugzilla/show_bug.cgi?id=40100) - check FileServer type before calling close

- [Bug 39887](https://bz.apache.org/bugzilla/show_bug.cgi?id=39887) - jmeter.util.SSLManager: Couldn't load keystore error message

- [Bug 41543](https://bz.apache.org/bugzilla/show_bug.cgi?id=41543) - exception when webserver returns "500 Internal Server Error" and content-length is 0

- [Bug 41416](https://bz.apache.org/bugzilla/show_bug.cgi?id=41416) - don't use chunked input for text-box input in SOAP-RPC sampler

- [Bug 39827](https://bz.apache.org/bugzilla/show_bug.cgi?id=39827) - SOAP Sampler content length for files

- Fix Class cast exception in Clear.java

- [Bug 40383](https://bz.apache.org/bugzilla/show_bug.cgi?id=40383) - don't set content-type if already set

- Mailer Visualiser test button now works if test plan has not yet been saved

- [Bug 36959](https://bz.apache.org/bugzilla/show_bug.cgi?id=36959) - Shortcuts "ctrl c" and "ctrl v" don't work on the tree elements

- [Bug 40696](https://bz.apache.org/bugzilla/show_bug.cgi?id=40696) - retrieve embedded resources from STYLE URL() attributes

- [Bug 41568](https://bz.apache.org/bugzilla/show_bug.cgi?id=41568) - Problem when running tests remotely when using a 'Counter'

- Fixed various classes that assumed timestamps were always end time stamps:

- SamplingStatCalculator

- JTLData

- RunningSample

- [Bug 40325](https://bz.apache.org/bugzilla/show_bug.cgi?id=40325) - allow specification of proxyuser and proxypassword for WebServiceSampler

- Change HttpClient proxy definition to use NTCredentials; added http.proxyDomain property for this

- [Bug 40371](https://bz.apache.org/bugzilla/show_bug.cgi?id=40371) - response assertion "pattern to test" scrollbar problem

- [Bug 40589](https://bz.apache.org/bugzilla/show_bug.cgi?id=40589) - Unescape XML entities in embedded URLs

- [Bug 41902](https://bz.apache.org/bugzilla/show_bug.cgi?id=41902) - NPE in HTTPSampler when responseCode = -1

- [Bug 41903](https://bz.apache.org/bugzilla/show_bug.cgi?id=41903) - ViewResultsFullVisualizer : status column looks bad when you do copy and paste

- [Bug 41837](https://bz.apache.org/bugzilla/show_bug.cgi?id=41837) - Parameter value corruption in proxy

- [Bug 41905](https://bz.apache.org/bugzilla/show_bug.cgi?id=41905) - Can't cut/paste/select Header Manager fields in Java 1.6

- [Bug 41928](https://bz.apache.org/bugzilla/show_bug.cgi?id=41928) - Make all request headers sent by HTTP Request sampler appear in sample result

- [Bug 41944](https://bz.apache.org/bugzilla/show_bug.cgi?id=41944) - Subresults not handled recursively by ResultSaver

- [Bug 42022](https://bz.apache.org/bugzilla/show_bug.cgi?id=42022) - HTTPSampler does not allow multiple headers of same name

- [Bug 42019](https://bz.apache.org/bugzilla/show_bug.cgi?id=42019) - Content type not stored in redirected HTTP request with subresults

- [Bug 42057](https://bz.apache.org/bugzilla/show_bug.cgi?id=42057) - connection can be null if method is null

- [Bug 41518](https://bz.apache.org/bugzilla/show_bug.cgi?id=41518) - JMeter changes the HTTP header Content Type for POST request

- [Bug 42156](https://bz.apache.org/bugzilla/show_bug.cgi?id=42156) - HTTPRequest HTTPClient incorrectly urlencodes parameter value in POST

- [Bug 42184](https://bz.apache.org/bugzilla/show_bug.cgi?id=42184) - Number of bytes for subsamples not added to sample when sub samples are added

- [Bug 42185](https://bz.apache.org/bugzilla/show_bug.cgi?id=42185) - If a HTTP Sampler follows a redirect, and is set up to download images, then images are downloaded multiple times

- [Bug 39808](https://bz.apache.org/bugzilla/show_bug.cgi?id=39808) - Invalid redirect causes incorrect sample time

- [Bug 42267](https://bz.apache.org/bugzilla/show_bug.cgi?id=42267) - Concurrent GUI update failure in Proxy Recording

- [Bug 30120](https://bz.apache.org/bugzilla/show_bug.cgi?id=30120) - Name of simple controller is resetted if a new simple controller is added as child

- [Bug 41078](https://bz.apache.org/bugzilla/show_bug.cgi?id=41078) - merge results in name change of test plan

- [Bug 40077](https://bz.apache.org/bugzilla/show_bug.cgi?id=40077) - Creating new Elements copies values from Existing elements

- [Bug 42325](https://bz.apache.org/bugzilla/show_bug.cgi?id=42325) - Implement the "clear" method for the LogicControllers

- [Bug 25441](https://bz.apache.org/bugzilla/show_bug.cgi?id=25441) - TestPlan changes sometimes detected incorrectly (isDirty)

- [Bug 39734](https://bz.apache.org/bugzilla/show_bug.cgi?id=39734) - Listeners shared after copy/paste operation

- [Bug 40851](https://bz.apache.org/bugzilla/show_bug.cgi?id=40851) - Loop controller with 0 iterations, stops evaluating the iterations field

- [Bug 24684](https://bz.apache.org/bugzilla/show_bug.cgi?id=24684) - remote startup problems if spaces in the path of the jmeter

- Use Listener configuration when loading CSV data files

- Function methods setParameters() need to be synchronized

- Fix CLI long optional argument to require "=" (as for short options)

- Fix SlowSocket to work properly with Httpclient (both http and https)

- [Bug 41612](https://bz.apache.org/bugzilla/show_bug.cgi?id=41612) - Loop nested in If Controller behaves erratically

- [Bug 42232](https://bz.apache.org/bugzilla/show_bug.cgi?id=42232) - changing language clears UDV contents

- Jexl function did not allow variables

### Version 2.2

Incompatible changes:

The time stamp is now set to the sampler start time (it was the end).
To revert to the previous behaviour, change the property **sampleresult.timestamp.start** to false (or comment it)

The JMX output format has been simplified and files are not backwards compatible

The JMeter.BAT file no longer changes directory to JMeter home, but runs from the current working directory.
The jmeter-n.bat and jmeter-t.bat files change to the directory containing the input file.

Listeners are now started slightly later in order to allow variable names to be used.
This may cause some problems; if so define the following in jmeter.properties:

jmeterengine.startlistenerslater=false

The GUI now expands the tree by default when loading a test plan.
This can be disabled by setting the JMeter property **onload.expandtree=false**

Known problems:

- Post-processors run in reverse order (see [Bug 41140](https://bz.apache.org/bugzilla/show_bug.cgi?id=41140))

- Module Controller does not work in non-GUI mode

- Aggregate Report and some other listeners use increasing amounts of memory as a test progresses

- Does not always handle non-default encoding properly

- Spaces in the installation path cause problems for client-server mode

- Change of Language does not propagate to all test elements

- SamplingStatCalculator keeps a List of all samples for calculation purposes;
this can cause memory exhaustion in long-running tests

- Does not properly handle server certificates if they are expired or not installed locally

New functionality:

- Report function

- XPath Extractor Post-Processor. Handles single and multiple matches.

- Simpler JMX file format (2.2)

- BeanshellSampler code can update ResponseData directly

- [Bug 37490](https://bz.apache.org/bugzilla/show_bug.cgi?id=37490) - Allow UDV as delay in Duration Assertion

- Slow connection emulation for HttpClient

- Enhanced JUnitSampler so that by default assert errors and exceptions are not appended to the error message.
Users must explicitly check append in the sampler

- Enhanced the documentation for webservice sampler to explain how it works with CSVDataSet

- Enhanced the documentation for javascript function to explain escaping comma

- Allow CSV Data Set file names to be absolute

- Report Tree compiler errors better

- Don't reset Regex Extractor variable if default is empty

- includecontroller.prefix property added

- Regular Expression Extractor sets group count

- Can now save entire screen as an image, not just the right-hand pane

- [Bug 38901](https://bz.apache.org/bugzilla/show_bug.cgi?id=38901) - Add optional SOAPAction header to SOAP Sampler

- New BeanShell test elements: Timer, PreProcessor, PostProcessor, Listener

- __split() function now clears next variable, so it can be used with ForEach Controller

- [Bug 38682](https://bz.apache.org/bugzilla/show_bug.cgi?id=38682) - add CallableStatement functionality to JDBC Sampler

- Make it easier to change the RMI/Server port

- Add property jmeter.save.saveservice.xml_pi to provide optional xml processing instruction in JTL files

- Add bytes and URL to items that can be saved in sample log files (XML and CSV)

- The Post-Processor "Save Responses to a File" now saves the generated file name with the
sample, and the file name can be included in the sample log file.

- Change jmeter.bat DOS script so it works from any directory

- New -N option to define nonProxyHosts from command-line

- New -S option to define system properties from input file

- [Bug 26136](https://bz.apache.org/bugzilla/show_bug.cgi?id=26136) - allow configuration of local address

- Expand tree by default when loading a test plan - can be disabled by setting property onload.expandtree=false

- [Bug 11843](https://bz.apache.org/bugzilla/show_bug.cgi?id=11843) - URL Rewriter can now cache the session id

- Counter Pre-Processor now supports formatted numbers

- Add support for HEAD PUT OPTIONS TRACE and DELETE methods

- Allow default HTTP implementation to be changed

- Optionally save active thread counts (group and all) to result files

- Variables/functions can now be used in Listener file names

- New __time() function; define START.MS/START.YMD/START.HMS properties and variables

- Add Thread Name to Tree and Table Views

- Add debug functions: What class, debug on, debug off

- Non-caching Calculator - used by Table Visualiser to reduce memory footprint

- Summary Report - similar to Aggregate Report, but uses less memory

- [Bug 39580](https://bz.apache.org/bugzilla/show_bug.cgi?id=39580) - recycle option for CSV Dataset

- [Bug 37652](https://bz.apache.org/bugzilla/show_bug.cgi?id=37652) - support for Ajp Tomcat protocol

- [Bug 39626](https://bz.apache.org/bugzilla/show_bug.cgi?id=39626) - Loading SOAP/XML-RPC requests from file

- [Bug 39652](https://bz.apache.org/bugzilla/show_bug.cgi?id=39652) - Allow truncation of labels on AxisGraph

- Allow use of htmlparser 1.6

- [Bug 39656](https://bz.apache.org/bugzilla/show_bug.cgi?id=39656) - always use SOAP action if it is provided

- Automatically include properties from user.properties file

- Add __jexl() function - evaluates Commons JEXL expressions

- Optionally load JMeter properties from user.properties and system properties from system.properties.

- [Bug 39707](https://bz.apache.org/bugzilla/show_bug.cgi?id=39707) - allow Regex match against URL

- Add start time to Table Visualiser

- HTTP Samplers can now extract embedded resources for any required media types

Bug fixes:

- Fix NPE when no module selected in Module Controller

- Fix NPE in XStream when no ResponseData present

- Remove ?xml prefix when running with Java 1.5 and no x-jars

- [Bug 37117](https://bz.apache.org/bugzilla/show_bug.cgi?id=37117) - setProperty() function should return ""; added optional return of original setting

- Fix CSV output time format

- [Bug 37140](https://bz.apache.org/bugzilla/show_bug.cgi?id=37140) - handle encoding better in RegexFunction

- Load all cookies, not just the first; fix class cast exception

- Fix default Cookie path name (remove page name)

- Fixed resultcode attribute name

- [Bug 36898](https://bz.apache.org/bugzilla/show_bug.cgi?id=36898) - apply encoding to RegexExtractor

- Add properties for saving subresults, assertions, latency, samplerData, responseHeaders, requestHeaders &amp; encoding

- [Bug 37705](https://bz.apache.org/bugzilla/show_bug.cgi?id=37705) - Synch Timer now works OK after run is stopped

- [Bug 37716](https://bz.apache.org/bugzilla/show_bug.cgi?id=37716) - Proxy request now handles file Post correctly

- HttpClient Sampler now saves latency

- Fix NPE when using JavaScript function on Test Plan

- Fix Base Href parsing in htmlparser

- [Bug 38256](https://bz.apache.org/bugzilla/show_bug.cgi?id=38256) - handle cookie with no path

- [Bug 38391](https://bz.apache.org/bugzilla/show_bug.cgi?id=38391) - use long when accumulating timer delays

- [Bug 38554](https://bz.apache.org/bugzilla/show_bug.cgi?id=38554) - Random function now uses long numbers

- [Bug 35224](https://bz.apache.org/bugzilla/show_bug.cgi?id=35224) - allow duplicate attributes for LDAP sampler

- [Bug 38693](https://bz.apache.org/bugzilla/show_bug.cgi?id=38693) - Webservice sampler can now use https protocol

- [Bug 38646](https://bz.apache.org/bugzilla/show_bug.cgi?id=38646) - Regex Extractor now clears old variables on match failure

- [Bug 38640](https://bz.apache.org/bugzilla/show_bug.cgi?id=38640) - fix WebService Sampler pooling

- [Bug 38474](https://bz.apache.org/bugzilla/show_bug.cgi?id=38474) - HTML Link Parser doesn't follow frame links

- [Bug 36430](https://bz.apache.org/bugzilla/show_bug.cgi?id=36430) - Counter now uses long rather than int to increase the range

- [Bug 38302](https://bz.apache.org/bugzilla/show_bug.cgi?id=38302) - fix XPath function

- [Bug 38748](https://bz.apache.org/bugzilla/show_bug.cgi?id=38748) - JDBC DataSourceElement fails with remote testing

- [Bug 38902](https://bz.apache.org/bugzilla/show_bug.cgi?id=38902) - sometimes -1 seems to be returned unnecessarily for response code

- [Bug 38840](https://bz.apache.org/bugzilla/show_bug.cgi?id=38840) - make XML Assertion thread-safe

- [Bug 38681](https://bz.apache.org/bugzilla/show_bug.cgi?id=38681) - Include controller now works in non-GUI mode

- Add write(OS,IS) implementation to TCPClientImpl

- Sample Result converter saves response code as "rc". Previously it saved as "rs" but read with "rc"; it will now also read with "rc".
The XSL stylesheets also now accept either "rc" or "rs"

- Fix counter function so each counter instance is independent (previously the per-user counters were shared between instances of the function)

- Fix TestBean Examples so that they work

- Fix JTidy parser so it does not skip body tags with background images

- Fix HtmlParser parser so it catches all background images

- [Bug 39252](https://bz.apache.org/bugzilla/show_bug.cgi?id=39252) set SoapSampler sample result from XML data

- [Bug 38694](https://bz.apache.org/bugzilla/show_bug.cgi?id=38694) - WebServiceSampler not setting data encoding correctly

- Result Collector now closes input files read by listeners

- [Bug 25505](https://bz.apache.org/bugzilla/show_bug.cgi?id=25505) - First HTTP sampling fails with "HTTPS hostname wrong: should be 'localhost'"

- [Bug 25236](https://bz.apache.org/bugzilla/show_bug.cgi?id=25236) - remove double scrollbar from Assertion Result Listener

- [Bug 38234](https://bz.apache.org/bugzilla/show_bug.cgi?id=38234) - Graph Listener divide by zero problem

- [Bug 38824](https://bz.apache.org/bugzilla/show_bug.cgi?id=38824) - clarify behaviour of Ignore Status

- [Bug 38250](https://bz.apache.org/bugzilla/show_bug.cgi?id=38250) - jmeter.properties "language" now supports country suffix, for zh_CN and zh_TW etc

- jmeter.properties file is now closed after it has been read

- [Bug 39533](https://bz.apache.org/bugzilla/show_bug.cgi?id=39533) - StatCalculator added wrong items

- [Bug 39599](https://bz.apache.org/bugzilla/show_bug.cgi?id=39599) - ConcurrentModificationException

- HTTPSampler2 now handles Auto and Follow redirects correctly

- [Bug 29481](https://bz.apache.org/bugzilla/show_bug.cgi?id=29481) - fix reloading sample results so subresults not counted twice

- [Bug 30267](https://bz.apache.org/bugzilla/show_bug.cgi?id=30267) - handle AutoRedirects properly

- [Bug 39677](https://bz.apache.org/bugzilla/show_bug.cgi?id=39677) - allow for space in JMETER_BIN variable

- Use Commons HttpClient cookie parsing and management. Fix various problems with cookie handling.

- [Bug 39773](https://bz.apache.org/bugzilla/show_bug.cgi?id=39773) - NTCredentials needs host name

Other changes

- Updated to HTTPClient 3.0 (from 2.0)

- Updated to Commons Collections 3.1

- Improved formatting of Request Data in Tree View

- Expanded user documentation

- Added MANIFEST, NOTICE and LICENSE to all jars

- Extract htmlparser interface into separate jarfile to make it possible to replace the parser

- Removed SQL Config GUI as no longer needed (or working!)

- HTTPSampler no longer logs a warning for Page not found (404)

- StringFromFile now callable as __StringFromFile (as well as _StringFromFile)

- Updated to Commons Logging 1.1

### Version 2.1.1

New functionality:

- New Include Controller allows a test plan to reference an external jmx file

- New JUnitSampler added for using JUnit Test classes

- New Aggregate Graph listener is capable of graphing aggregate statistics

- Can provide additional classpath entries using the property user.classpath and on the Test Plan element

Bug fixes:

- AccessLog Sampler and JDBC test elements populated correctly from 2.0 test plans

- BSF Sampler now populates filename and parameters from saved test plan

- [Bug 36500](https://bz.apache.org/bugzilla/show_bug.cgi?id=36500) - handle missing data more gracefully in WebServiceSampler

- [Bug 35546](https://bz.apache.org/bugzilla/show_bug.cgi?id=35546) - add merge to right-click menu

- [Bug 36642](https://bz.apache.org/bugzilla/show_bug.cgi?id=36642) - Summariser stopped working in 2.1

- [Bug 36618](https://bz.apache.org/bugzilla/show_bug.cgi?id=36618) - CSV header line did not match saved data

- JMeter should now run under JVM 1.3 (but does not build with 1.3)

### Version 2.1

New functionality:

- New Test Script file format - smaller, more compact, more readable

- New Sample Result file format - smaller, more compact

- XSchema Assertion

- XML Tree display

- CSV DataSet Config item

- New JDBC Connection Pool Config Element

- Synchronisation Timer

- setProperty function

- Save response data on error

- Ant JMeter XSLT now optionally shows failed responses and has internal links

- Allow JavaScript variable name to be omitted

- Changed following Samplers to set sample label from sampler name

- All Test elements can be saved as a graphics image to a file

- [Bug 35026](https://bz.apache.org/bugzilla/show_bug.cgi?id=35026) - add RE pattern matching to Proxy

- [Bug 34739](https://bz.apache.org/bugzilla/show_bug.cgi?id=34739) - Enhance constant Throughput timer

- [Bug 25052](https://bz.apache.org/bugzilla/show_bug.cgi?id=25052) - use response encoding to create comparison string in Response Assertion

- New optional icons

- Allow icons to be defined via property files

- New stylesheets for 2.1 format XML test output

- Save samplers, config element and listeners as PNG

- Enhanced support for WSDL processing

- New JMS sampler for topic and queue messages

- How-to for JMS samplers

- [Bug 35525](https://bz.apache.org/bugzilla/show_bug.cgi?id=35525) - Added Spanish localisation

- [Bug 30379](https://bz.apache.org/bugzilla/show_bug.cgi?id=30379) - allow server.rmi.port to be overridden

- enhanced the monitor listener to save the calculated stats

- Functions and variables now work at top level of test plan

Bug fixes:

- [Bug 34586](https://bz.apache.org/bugzilla/show_bug.cgi?id=34586) - XPath always remained as /

- BeanShellInterpreter did not handle null objects properly

- Fix Chinese resource bundle names

- Save field names if required to CSV files

- Ensure XML file is closed

- Correct icons now displayed for TestBean components

- Allow for missing optional jar(s) in creating menus

- Changed Samplers to set sample label from sampler name as was the case for HTTP

- Fix various samplers to avoid NPEs when incomplete data is provided

- Fix Cookie Manager to use seconds; add debug

- [Bug 35067](https://bz.apache.org/bugzilla/show_bug.cgi?id=35067) - set up filename when using -t option

- Don't substitute TestElement.* properties by UDVs in Proxy

- [Bug 35065](https://bz.apache.org/bugzilla/show_bug.cgi?id=35065) - don't save old extensions in File Saver

- [Bug 25413](https://bz.apache.org/bugzilla/show_bug.cgi?id=25413) - don't enable Restart button unnecessarily

- [Bug 35059](https://bz.apache.org/bugzilla/show_bug.cgi?id=35059) - Runtime Controller stopped working

- Clear up any left-over connections created by LDAP Extended Sampler

- [Bug 23248](https://bz.apache.org/bugzilla/show_bug.cgi?id=23248) - module controller didn't remember stuff between save and reload

- Fix Chinese locales

- [Bug 29920](https://bz.apache.org/bugzilla/show_bug.cgi?id=29920) - change default locale if necessary to ensure default properties are picked up when English is selected.

- Bug fixes for Tomcat monitor captions

- Fixed webservice sampler so it works with user defined variables

- Fixed screen borders for LDAP config GUI elements

- [Bug 31184](https://bz.apache.org/bugzilla/show_bug.cgi?id=31184) - make sure encoding is specified in JDBC sampler

- TCP sampler - only share sockets with same host:port details; correct the manual

- Extract src attribute for embed tags in JTidy and Html Parsers

### Version 2.0.3

New functionality:

- XPath Assertion and XPath Function

- Switch Controller

- ForEach Controller can now loop through sets of groups

- Allow CSVRead delimiter to be changed (see jmeter.properties)

- [Bug 33920](https://bz.apache.org/bugzilla/show_bug.cgi?id=33920) - allow additional property files

- [Bug 33845](https://bz.apache.org/bugzilla/show_bug.cgi?id=33845) - allow direct override of Home dir

Bug fixes:

- Regex Extractor nested constant not put in correct place [Bug 32395](https://bz.apache.org/bugzilla/show_bug.cgi?id=32395)

- Start time reset to now if necessary so that delay works OK.

- Missing start/end times in scheduler are assumed to be now, not 1970

- [Bug 28661](https://bz.apache.org/bugzilla/show_bug.cgi?id=28661) - 304 responses not appearing in listeners

- DOS scripts now handle different disks better

- [Bug 32345](https://bz.apache.org/bugzilla/show_bug.cgi?id=32345) - HTTP Rewriter does not work with HTTP Request default

- Catch Runtime Exceptions so an error in one Listener does not affect others

- [Bug 33467](https://bz.apache.org/bugzilla/show_bug.cgi?id=33467) - __threadNum() extracted number wrongly 

- [Bug 29186](https://bz.apache.org/bugzilla/show_bug.cgi?id=29186),33299 - fix CLI parsing of "-" in second argument

- Fix CLI parse bug: -D arg1=arg2. Log more startup parameters.

- Fix JTidy and HTMLParser parsers to handle form src= and link rel=stylesheet

- JMeterThread now logs Errors to jmeter.log which were appearing on console

- Ensure WhileController condition is dynamically checked

- [Bug 32790](https://bz.apache.org/bugzilla/show_bug.cgi?id=32790) ensure If Controller condition is re-evaluated each time

- [Bug 30266](https://bz.apache.org/bugzilla/show_bug.cgi?id=30266) - document how to display proxy recording responses

- [Bug 33921](https://bz.apache.org/bugzilla/show_bug.cgi?id=33921) - merge should not change file name

- Close file now gives chance to save changes

- [Bug 33559](https://bz.apache.org/bugzilla/show_bug.cgi?id=33559) - fixes to Runtime Controller

Other changes:

- To help with variable evaluation, JMeterThread sets "sampling started" a bit earlier (see jmeter.properties)

- [Bug 33796](https://bz.apache.org/bugzilla/show_bug.cgi?id=33796) - delete cookies with null/empty values

- Better checking of parameter count in JavaScript function

- Thread Group now defaults to 1 loop instead of forever

- All Beanshell access is now via a single class; only need BSH jar at run-time

- [Bug 32464](https://bz.apache.org/bugzilla/show_bug.cgi?id=32464) - document Direct Draw settings in jmeter.bat

- [Bug 33919](https://bz.apache.org/bugzilla/show_bug.cgi?id=33919) - increase Counter field sizes

- [Bug 32252](https://bz.apache.org/bugzilla/show_bug.cgi?id=32252) - ForEach was not initialising counters

### Version 2.0.2

New functionality:

- While Controller

- BeanShell initialisation scripts

- Result Saver can optionally save failed results only

- Display as HTML has option not to download frames and images etc

- Multiple Tree elements can now be enabled/disabled/copied/pasted at once

- __split() function added

- [Bug 28699](https://bz.apache.org/bugzilla/show_bug.cgi?id=28699) allow Assertion to regard unsuccessful responses - e.g. 404 - as successful

- [Bug 29075](https://bz.apache.org/bugzilla/show_bug.cgi?id=29075) Regex Extractor can now extract data out of http response header as well as the body

- __log() functions can now write to stdout and stderr

- URL Modifier can now optionally ignore query parameters

Bug fixes:

- If controller now works after the first false condition [Bug 31390](https://bz.apache.org/bugzilla/show_bug.cgi?id=31390)

- Regex GUI was losing track of Header/Body checkbox [Bug 29853](https://bz.apache.org/bugzilla/show_bug.cgi?id=29853)

- Display as HTML now handles frames and relative images

- Right-click open replaced by merge

- Fix some drag and drop problems

- Fixed foreach demo example so it works

- [Bug 30741](https://bz.apache.org/bugzilla/show_bug.cgi?id=30741) SSL password prompt now works again 

- StringFromFile now closes files at end of test; start and end now optional as intended

- [Bug 31342](https://bz.apache.org/bugzilla/show_bug.cgi?id=31342) Fixed text of SOAP Sampler headers

- Proxy must now be stopped before it can be removed [Bug 25145](https://bz.apache.org/bugzilla/show_bug.cgi?id=25145)

- Link Parser now supports BASE href [Bug 25490](https://bz.apache.org/bugzilla/show_bug.cgi?id=25490)

- [Bug 30917](https://bz.apache.org/bugzilla/show_bug.cgi?id=30917) Classfinder ignores duplicate names

- [Bug 22820](https://bz.apache.org/bugzilla/show_bug.cgi?id=22820) Allow Counter value to be cleared

- [Bug 28230](https://bz.apache.org/bugzilla/show_bug.cgi?id=28230) Fix NPE in HTTP Sampler retrieving embedded resources

- Improve handling of StopTest; catch and log some more errors

- ForEach Controller no longer runs any samples if first variable is not defined

- [Bug 28663](https://bz.apache.org/bugzilla/show_bug.cgi?id=28663) NPE in remote JDBC execution

- [Bug 30110](https://bz.apache.org/bugzilla/show_bug.cgi?id=30110) Deadlock in stopTest processing

- [Bug 31696](https://bz.apache.org/bugzilla/show_bug.cgi?id=31696) Duration not working correctly when using Scheduler

- JMeterContext now uses ThreadLocal - should fix some potential NPE errors

### Version 2.0.1

Bug fix release. TBA.

### Version 2.0

    - HTML parsing improved; now has choice of 3 parsers, and most embedded elements can now be detected and downloaded.

- Redirects can now be delegated to URLConnection by defining the JMeter property HTTPSamper.delegateRedirects=true (default is false) 

- Stop Thread and Stop Test methods added for Samplers and Assertions etc. Samplers can call setStopThread(true) or setStopTest(true) if they detect an error that needs to stop the thread of the test after the sample has been processed 

- Thread Group Gui now has an extra pane to specify what happens after a Sampler error: Continue (as now), Stop Thread or Stop Test.
    This needs to be extended to a lower level at some stage. 

- Added Shutdown to Run Menu. This is the same as Stop except that it lets the Threads finish normally (i.e. after the next sample has been completed) 

- Remote samples can be cached until the end of a test by defining the property hold_samples=true when running the server.
More work is needed to be able to control this from the GUI 

- Proxy server has option to skip recording browser headers 

- Proxy restart works better (stop waits for daemon to finish) 

- Scheduler ignores start if it has already passed 

- Scheduler now has delay function 

- added Summariser test element (mainly for non-GUI) testing. This prints summary statistics to System.out and/or the log file every so often (3 minutes by default). Multiple summarisers can be used; samples are accumulated by summariser name. 

- Extra Proxy Server options:
Create all samplers with keep-alive disabled
Add Separator markers between sets of samples
Add Response Assertion to first sampler in each set 

- Test Plan has a comment field

    - Help Page can now be pushed to background

    - Separate Function help page

    - New / amended functions

    
      - __property() and __P() functions

      - __log() and __logn() - for writing to the log file

      - _StringFromFile can now process a sequence of files, e.g. dir/file01.txt, dir/file02.txt etc 

      - _StringFromFile() function can now use a variable or function for the file name 

    

    - New / amended Assertions

    
        - Response Assertion now works for URLs, and it handles null data better 

        - Response Assertion can now match on Response Code and Response message as well 

        - HTML Assertion using JTidy to check for well-formed HTML

    

    - If Controller (not fully functional yet)

    - Transaction Controller (aggregates the times of its children)

    - New Samplers

        
            - Basic BSF Sampler (optional)

            - BeanShell Sampler (optional, needs to be downloaded from www.beanshell.org

            - Basic TCP Sampler

        

     - Optionally start BeanShell server (allows remote access to JMeter variables and methods) 

### Version 1.9.1

TBA

### Version 1.9

- Sample result log files can now be in CSV or XML format

- New Event model for notification of iteration events during test plan run

- New Javascript function for executing arbitrary javascript statements

- Many GUI improvements

- New Pre-processors and Post-processors replace Modifiers and Response-Based Modifiers. 

- Compatible with jdk1.3

- JMeter functions are now fully recursive and universal (can use functions as parameters to functions)

- Integrated help window now supports hypertext links

- New Random Function

- New XML Assertion

- New LDAP Sampler (alpha code)

- New Ant Task to run JMeter (in extras folder)

- New Java Sampler test implementation (to assist developers)

- More efficient use of memory, faster loading of .jmx files

- New SOAP Sampler (alpha code)

- New Median calculation in Graph Results visualizer

- Default config element added for developer benefit

- Various performance enhancements during test run

- New Simple File recorder for minimal GUI overhead during test run

- New Function: StringFromFile - grabs values from a file

- New Function: CSVRead - grabs multiple values from a file

- Functions now longer need to be encoded - special values should be escaped
with "\" if they are literal values

- New cut/copy/paste functionality

- SSL testing should work with less user-fudging, and in non-gui mode

- Mailer Model works in non-gui mode

- New Throughput Controller

- New Module Controller

- Tests can now be scheduled to run from a certain time till a certain time

- Remote JMeter servers can be started from a non-gui client.  Also, in gui mode, all remote servers can be started with a single click

- ThreadGroups can now be run either serially or in parallel (default)

- New command line options to override properties

- New Size Assertion

### Version 1.8.1

- Bug Fix Release.  Many bugs were fixed.

- Removed redundant "Root" node from test tree.

- Re-introduced Icons in test tree.

- Some re-organization of code to improve build process.

- View Results Tree has added option to view results as web document (still buggy at this point).

- New Total line in Aggregate Listener (still buggy at this point).

- Improvements to ability to change JMeter's Locale settings.

- Improvements to SSL Manager.

### Version 1.8

- Improvement to Aggregate report's calculations.

- Simplified application logging.

- New Duration Assertion.

- Fixed and improved Mailer Visualizer.

- Improvements to HTTP Sampler's recovery of resources (sockets and file handles).

- Improving JMeter's internal handling of test start/stop.

- Fixing and adding options to behavior of Interleave and Random Controllers.

- New Counter config element.

- New User Parameters config element.

- Improved performance of file opener.

- Functions and other elements can access global variables.

- Help system available within JMeter's GUI.

- Test Elements can be disabled.

- Language/Locale can be changed while running JMeter (mostly).

- View Results Tree can be configured to record only errors.

- Various bug fixes.

### Version 1.7.3

- New Functions that provide more ability to change requests dynamically during test runs.

- New language translations in Japanese and German.

- Removed annoying Log4J error messages.

- Improved support for loading JMeter 1.7 version test plan files (.jmx files).

- JMeter now supports proxy servers that require username/password authentication.

- Dialog box indicating test stopping doesn't hang JMeter on problems with stopping test.

- GUI can run multiple remote JMeter servers (fixes GUI bug that prevented this).

- Dialog box to help created function calls in GUI.

- New Keep-alive switch in HTTP Requests to indicate JMeter should or should not use Keep-Alive for sockets.

- HTTP Post requests can have GET style arguments in Path field.  Proxy records them correctly now.

- New User-defined test-wide static variables.

- View Results Tree now displays more information, including name of request (matching the name
in the test tree) and full request and POST data.

- Removed obsolete View Results Visualizer (use View Results Tree instead).

- Performance enhancements.

- Memory use enhancements.

- Graph visualizer GUI improvements.

- Updates and fixes to Mailer Visualizer.

### Version 1.7.2

- JMeter now notifies user when test has stopped running.

- HTTP Proxy server records HTTP Requests with re-direct turned off.

- HTTP Requests can be instructed to either follow redirects or ignore them.

- Various GUI improvements.

- New Random Controller.

- New SOAP/XML-RPC Sampler.

### Version 1.7.1

- JMeter's architecture revamped for a more complete separation between GUI code and
test engine code.

- Use of Avalon code to save test plans to XML as Configuration Objects

- All listeners can save data to file and load same data at later date.

### Version 1.7Beta

    - Better XML support for special characters (Tushar Bhatia) 

    - Non-GUI functioning  &amp; Non-GUI test plan execution  (Tushar Bhatia)

    - Removing Swing dependence from base JMeter classes

    - Internationalization (Takashi Okamoto)

    - AllTests bug fix (neth6@atozasia.com)

    - ClassFinder bug fix (neth6@atozasia.com)

    - New Loop Controller

    - Proxy Server records HTTP samples from browser
        (and documented in the user manual)
 - Multipart Form support

    - HTTP Header class for Header customization

    - Extracting HTTP Header information from responses (Jamie Davidson)

    - Mailer Visualizer re-added to JMeter

    - JMeter now url encodes parameter names and values

    - listeners no longer give exceptions if their gui's haven't been initialized

    - HTTPS and Authorization working together

    - New Http sampling that automatically parses HTML response
        for images to download, and includes the downloading of these
        images in total time for request (Neth neth6@atozasia.com) 

    - HTTP responses from server can be parsed for links and forms,
        and dynamic data can be extracted and added to test samples
        at run-time (documented)

    - New Ramp-up feature (Jonathan O'Keefe)

    - New visualizers (Neth)

    - New Assertions for functional testing

### Version 1.6.1

    - Fixed saving and loading of test scripts (no more extra lines)

    - Can save and load special characters (such as &quot;&amp;&quot; and &quot;&lt;&quot;).

    - Can save and load timers and listeners.

    - Minor bug fix for cookies (if you cookie value
        contained an &quot;=&quot;, then it broke).

    - URL's can sample ports other than 80, and can test HTTPS,
        provided you have the necessary jars (JSSE)

### Version 1.6 Alpha

    - New UI

    - Separation of GUI and Logic code

    - New Plug-in framework for new modules

    - Enhanced performance

    - Layering of test logic for greater flexibility

    - Added support for saving of test elements

    - Added support for distributed testing using a single client

### Version 1.5.1

    - Fixed bug that caused cookies not to be read if header name case not as expected.

    - Clone entries before sending to sampler - prevents relocations from messing up
        information across threads

    - Minor bug fix to convenience dialog for adding parameters to test sample.
        Bug prevented entries in dialog from appearing in test sample.

    - Added xerces.jar to distribution

    - Added junit.jar to distribution and created a few tests.

    - Started work on new framework.  New files in cvs, but do not effect program yet.

    - Fixed bug that prevent HTTPJMeterThread from delaying according to chosen timer.

### Version 1.5

    - Abstracted out the concept of the Sampler, SamplerController, and TestSample.
        A Sampler represents code that understands a protocol (such as HTTP,
        or FTP, RMI,   SMTP, etc..).  It is the code that actually makes the
        connection to whatever is   being tested.   A SamplerController
        represents code that understands how to organize and run a group
        of test samples.  It is what binds together a Sampler and its test
        samples and runs them.   A TestSample represents code that understands
        how to gather information from the   user about a particular test.
        For a website, it would represent a URL and any   information to be sent
        with the URL.

    - The UI has been updated to make entering test samples more convenient.

    - Thread groups have been added, allowing a user to setup multiple test to run
        concurrently, and to allow sharing of test samples between those tests.

    - It is now possible to save and load test samples.

    - &hellip; and many more minor changes/improvements &hellip;

**Apache JMeter 1.4.1-dev**

   - Cleaned up URLSampler code after tons of patches for better readability. (SM)

   - Made JMeter send a special &quot;user-agent&quot; identifier. (SM)

   - Fixed problems with redirection not sending cookies and authentication info and removed
     a warning with jikes compilation. Thanks to Wesley Tanaka for the patches (SM)

   - Fixed a bug in the URLSampler that caused to skip one URL when testing lists of URLs and
     a problem with Cookie handling. Thanks to Graham Johnson for the patches (SM)

   - Fixed a problem with POST actions. Thanks to Stephen Schaub for the patch (SM)

 

 

 
 **Apache JMeter 1.4** - Jul 11 1999
  
   - Fixed a problem with POST actions. Thanks to Brendan Burns for the patch (SM)

   - Added close button to the About box for those window managers who don't provide it.
     Thanks to Jan-Henrik Haukeland for pointing it out. (SM)

   - Added the simple Spline sample visualizer (JPN)

 

  **Apache JMeter 1.3** - Apr 16 1999

   - Run the Garbage Collector and run finalization before starting to sampling to ensure
     same state every time (SM)

   - Fixed some NullPointerExceptions here and there (SM)

   - Added HTTP authentication capabilities (RL)

   - Added windowed sample visualizer (SM)

   - Fixed stupid bug for command line arguments. Thanks to Jorge Bracer for pointing this out (SM)

 

  **Apache JMeter 1.2** - Mar 17 1999

   - Integrated cookie capabilities with JMeter (SM)

   - Added the Cookie manager and Netscape file parser (SD)

   - Fixed compilation error for JDK 1.1 (SD)
 
 

 **Apache JMeter 1.1** - Feb 24 1999

   - Created the opportunity to create URL aliasing from the properties file as well as the
     ability to associate aliases to URL sequences instead of single URLs (SM) Thanks to
     Simon Chatfield for the very nice suggestions
     and code examples.

   - Removed the TextVisualizer and replaced it with the much more useful FileVisualizer (SM)

   - Added the known bug list (SM)

   - Removed the Java Apache logo (SM)

   - Fixed a couple of typos (SM)

   - Added UNIX makefile (SD)
 
 

 **Apache JMeter 1.0.1** - Jan 25 1999

   - Removed pending issues doc issues (SM)

   - Fixed the unix script (SM)

   - Added the possibility of running the JAR directly using &quot;java -jar
     ApacheJMeter.jar&quot; with Java 2 (SM)

   - Some small updates: fixed Swing location after Java 2(tm) release, license update and
     small cleanups (SM)

 

 **Apache JMeter 1.0** - Dec 15 1998

   - Initial version. (SM)

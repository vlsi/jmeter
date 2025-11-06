---
title: Version 2.4
---

# Version 2.4

## Summary of main changes

- JMeter now requires at least Java 1.5.

- HTTP Proxy can now record HTTPS sessions.

- JUnit sampler now supports JUnit4 annotations.

- Added JSR223 (javax.script) test elements.

- MailReader Sampler can now use any protocol supported by the underlying implementation.

- An SMTP Sampler has been added.

- JMeter now allows users to provide their own Thread Group implementations.

- View Results Tree now supports more display options, including search and Regex Testing.

- StatCalculator performance is much improved; Aggregate Report etc. need far less memory.

- 
JMS samplers have been extensively reworked, and should no longer lose messages.
Correlation processing is improved.
JMS Publisher and Subscriber now support both Topics and Queues.

- Many other improvements have been made, please see below and in the manual.

## Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The menu item Options / Choose Language does not change all the displayed text to the new language.
[The behaviour has improved, but language change is still not fully working]
To override the default local language fully, set the JMeter property "language" before starting JMeter.

## Incompatible changes

HTTP Redirect now defaults to "Follow Redirects" rather than "Redirect Automatically".
This is to enable JMeter to track cookies that may be sent during redirects.
This does not affect existing test plans; it only affects the default for new HTTP Samplers.

The Avalon file format for JMX and JTL files is no longer supported.
Any such files will need to be converted by reading them in JMeter 2.3.4 and resaving them.

The XPath Assertion and XPath Extractor elements no longer fetch external DTDs by default; this can be changed in the GUI.

JMSConfigGui has been renamed as JMSSamplerGui.
This does not affect existing test plans.

The constructor public SampleResult(SampleResult res) has been changed to become a true "copy constructor".
It no longer calls addSubResult(). This may possibly affect some 3rd party add-ons.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 47445](https://bz.apache.org/bugzilla/show_bug.cgi?id=47445) - Using Proxy with https-spoofing secure cookies need to be unsecured

- [Bug 47442](https://bz.apache.org/bugzilla/show_bug.cgi?id=47442) - Missing replacement of https by http for certain conditions using https-spoofing

- [Bug 48451](https://bz.apache.org/bugzilla/show_bug.cgi?id=48451) - Error in: SoapSampler.setPostHeaders(PostMethod post) in the else branch

- [Bug 48542](https://bz.apache.org/bugzilla/show_bug.cgi?id=48542) - SoapSampler uses wrong response header field to decide if response is gzip encoded

- [Bug 48568](https://bz.apache.org/bugzilla/show_bug.cgi?id=48568) - CookieManager broken for AjpSampler

- [Bug 48570](https://bz.apache.org/bugzilla/show_bug.cgi?id=48570) - AjpSampler doesn't support query parameters (GET/POST)

- [Bug 46901](https://bz.apache.org/bugzilla/show_bug.cgi?id=46901) - HTTP Sampler does not process var/func refs correctly in first file parameter

- [Bug 43678](https://bz.apache.org/bugzilla/show_bug.cgi?id=43678) - Handle META tag http-equiv charset?

- [Bug 49294](https://bz.apache.org/bugzilla/show_bug.cgi?id=49294) - Images not downloaded from redirected-to pages

- [Bug 49560](https://bz.apache.org/bugzilla/show_bug.cgi?id=49560) - wrong "size in bytes" when following redirections

### Other Samplers

- [Bug 47420](https://bz.apache.org/bugzilla/show_bug.cgi?id=47420) - LDAP extended request not closing connections during add request

- [Bug 48573](https://bz.apache.org/bugzilla/show_bug.cgi?id=48573) - LDAPExtSampler directory context handling

- [Bug 47870](https://bz.apache.org/bugzilla/show_bug.cgi?id=47870) - JMSSubscriber fails due to NPE

- [Bug 47899](https://bz.apache.org/bugzilla/show_bug.cgi?id=47899) - NullPointerExceptions in JMS ReceiveSubscriber constructor

- [Bug 48144](https://bz.apache.org/bugzilla/show_bug.cgi?id=48144) - NPE in JMS OnMessageSubscriber

- [Bug 47992](https://bz.apache.org/bugzilla/show_bug.cgi?id=47992) - JMS Point-to-Point Request - Response option doesn't work

- [Bug 48579](https://bz.apache.org/bugzilla/show_bug.cgi?id=48579) - Single Bind does not show config information when LdapExt Sampler is accessed

- [Bug 49111](https://bz.apache.org/bugzilla/show_bug.cgi?id=49111) - "Message With ID Not Found" Error on JMS P2P sampler.

- [Bug 47949](https://bz.apache.org/bugzilla/show_bug.cgi?id=47949) - JMS Subscriber never receives all the messages

- [Bug 46142](https://bz.apache.org/bugzilla/show_bug.cgi?id=46142) - JMS Point-to-Point correlation problems

- [Bug 48747](https://bz.apache.org/bugzilla/show_bug.cgi?id=48747) - TCP Sampler swallows exceptions

- [Bug 48709](https://bz.apache.org/bugzilla/show_bug.cgi?id=48709) - TCP Sampler Config setting "classname" has no effect

### Controllers

- [Bug 47385](https://bz.apache.org/bugzilla/show_bug.cgi?id=47385) - TransactionController should set AllThreads and GroupThreads

- [Bug 47940](https://bz.apache.org/bugzilla/show_bug.cgi?id=47940) - Module controller incorrectly creates the replacement Sub Tree

- [Bug 47592](https://bz.apache.org/bugzilla/show_bug.cgi?id=47592) - Run Thread groups consecutively with "Stop test" on error, JMeter will not mark to finished

- [Bug 48786](https://bz.apache.org/bugzilla/show_bug.cgi?id=48786) - Run Thread groups consecutively: with "Stop test now" on error or manual stop, JMeter leaves the green box active

- [Bug 48727](https://bz.apache.org/bugzilla/show_bug.cgi?id=48727) - Cannot stop test if all thread groups are disabled

### Listeners

- [Bug 48603](https://bz.apache.org/bugzilla/show_bug.cgi?id=48603) - Mailer Visualiser sends two emails for a single failed response

- Correct calculation of min/max/std.dev for aggregated samples (Summary Report)

- [Bug 48889](https://bz.apache.org/bugzilla/show_bug.cgi?id=48889) - Wrong response time with mode=Statistical and num_sample_threshold &gt; 1

- [Bug 47398](https://bz.apache.org/bugzilla/show_bug.cgi?id=47398) - SampleEvents are sent twice over RMI in distributed testing and non gui mode

### Assertions

### Functions

### I18N

### General

- [Bug 47646](https://bz.apache.org/bugzilla/show_bug.cgi?id=47646) -  NullPointerException in the "Random Variable" element

- Disallow adding any child elements to JDBC Configuration

- BeanInfoSupport now caches getBeanDescriptor() - should avoid an NPE on non-Sun JVMs when using CSVDataSet (and some other TestBeans)

- [Bug 48350](https://bz.apache.org/bugzilla/show_bug.cgi?id=48350) - Deadlock on distributed testing with 2 clients

- [Bug 48901](https://bz.apache.org/bugzilla/show_bug.cgi?id=48901) - Endless wait by adding Synchronizing Timer

- [Bug 49149](https://bz.apache.org/bugzilla/show_bug.cgi?id=49149) - usermanual/index.html has typo in link to "Regular Expressions" page

- [Bug 49394](https://bz.apache.org/bugzilla/show_bug.cgi?id=49394) - Classcast Exception in ActionRouter.postActionPerformed

- [Bug 48136](https://bz.apache.org/bugzilla/show_bug.cgi?id=48136) - Essential files missing from source tarball.
Source archives now contain all source files, including source files previously only provided in the binary archives.

- [Bug 48331](https://bz.apache.org/bugzilla/show_bug.cgi?id=48331) - XpathExtractor does not return XML string representations for a Nodeset

## Improvements

### HTTP Samplers

- [Bug 47622](https://bz.apache.org/bugzilla/show_bug.cgi?id=47622) - enable recording of HTTPS sessions

- Allow Proxy Server to be specified on HTTP Sampler GUI and HTTP Config GUI

- [Bug 47461](https://bz.apache.org/bugzilla/show_bug.cgi?id=47461) - Update Cache Manager to handle Expires HTTP header

- [Bug 48153](https://bz.apache.org/bugzilla/show_bug.cgi?id=48153) - Support for Cache-Control and Expires headers

- [Bug 47946](https://bz.apache.org/bugzilla/show_bug.cgi?id=47946) - Proxy should enable Grouping inside a Transaction Controller

- [Bug 48300](https://bz.apache.org/bugzilla/show_bug.cgi?id=48300) - Allow override of IP source address for HTTP HttpClient requests

- [Bug 49083](https://bz.apache.org/bugzilla/show_bug.cgi?id=49083) - collapse '/pathsegment/..' in redirect URLs

### Other samplers

- JUnit sampler now supports JUnit4 tests (using annotations)

- [Bug 47900](https://bz.apache.org/bugzilla/show_bug.cgi?id=47900) - Allow JMS SubscriberSampler to be interrupted

- Added JSR223 Sampler

- [Bug 47556](https://bz.apache.org/bugzilla/show_bug.cgi?id=47556) - JMS-PointToPoint-Sampler Timeout field should use Strings

- [Bug 47947](https://bz.apache.org/bugzilla/show_bug.cgi?id=47947) - Mail Reader Sampler should allow port to be overridden

- [Bug 48155](https://bz.apache.org/bugzilla/show_bug.cgi?id=48155) - Multiple problems / enhancements with JMS protocol classes

- Allow MailReader sampler to use arbitrary protocols

- [Bug 45053](https://bz.apache.org/bugzilla/show_bug.cgi?id=45053) - SMTP-Sampler for JMeter

- [Bug 49552](https://bz.apache.org/bugzilla/show_bug.cgi?id=49552) - Add Message Headers on SMTPSampler

- 
JMS Publisher and Subscriber now support both Topics and Queues.
Added read Timeout to JMS Subscriber.
General clean-up of JMS code.

### Controllers

- [Bug 47909](https://bz.apache.org/bugzilla/show_bug.cgi?id=47909) - TransactionController should sum the latency

- [Bug 41418](https://bz.apache.org/bugzilla/show_bug.cgi?id=41418) - Exclude timer duration from Transaction Controller runtime in report

- [Bug 48749](https://bz.apache.org/bugzilla/show_bug.cgi?id=48749) - Allowing custom Thread Groups

- [Bug 43389](https://bz.apache.org/bugzilla/show_bug.cgi?id=43389) - Allow Include files to be found relative to the current JMX file

### Listeners

- Added DataStrippingSample sender - supports "Stripped" and "StrippedBatch" modes.

- Added Comparison Assertion Visualizer

- [Bug 47907](https://bz.apache.org/bugzilla/show_bug.cgi?id=47907) - Improvements (enhancements and I18N) Comparison Assertion and Comparison Visualizer

- [Bug 36726](https://bz.apache.org/bugzilla/show_bug.cgi?id=36726) - add search function to Tree View Listener

- [Bug 47869](https://bz.apache.org/bugzilla/show_bug.cgi?id=47869) - Ability to cleanup fields of SampleResult

- [Bug 47952](https://bz.apache.org/bugzilla/show_bug.cgi?id=47952) - Added JSR223 Listener

- [Bug 47474](https://bz.apache.org/bugzilla/show_bug.cgi?id=47474) - View Results Tree support for plugin renderers

- Allow Idle Time to be saved to sample log files

- [Bug 48259](https://bz.apache.org/bugzilla/show_bug.cgi?id=48259) - Improve StatCalculator performance by using TreeMap

- Listeners using SamplingStatCalculator have much reduced memory needs
as the Sample cache has been moved to the new CachingStatCalculator class.
In particular, Aggregate Report can now handle large numbers of samples.

- Aggregate Report and Summary Report now allow column headers to be optionally excluded

- [Bug 49506](https://bz.apache.org/bugzilla/show_bug.cgi?id=49506) - Add .csv File Extension in open dialog box from "read from file" functionality of listeners

- [Bug 49545](https://bz.apache.org/bugzilla/show_bug.cgi?id=49545) - Formatted (parsed) view of Sample Result in Results Tree

### Timers, Assertions, Config, Pre- &amp; Post-Processors

- [Bug 47338](https://bz.apache.org/bugzilla/show_bug.cgi?id=47338) - XPath Extractor forces retrieval of document DTD

- Added Comparison Assertion

- [Bug 47952](https://bz.apache.org/bugzilla/show_bug.cgi?id=47952) - Added JSR223 PreProcessor and PostProcessor

- Added JSR223 Assertion

- Added BSF Timer and JSR223 Timer

- [Bug 48511](https://bz.apache.org/bugzilla/show_bug.cgi?id=48511) - add parent,child,all selection to regex extractor

- Add Sampler scope selection to XPathExtractor

- Regular Expression Extractor, Response Assertion and Size Assertion can now be applied to a JMeter variable

- [Bug 46790](https://bz.apache.org/bugzilla/show_bug.cgi?id=46790) - CSV Data Set Config should be able to parse CSV headers

### Functions

- [Bug 47565](https://bz.apache.org/bugzilla/show_bug.cgi?id=47565) - [Function] FileToString

### I18N

- [Bug 47938](https://bz.apache.org/bugzilla/show_bug.cgi?id=47938) -  Adding some French translations for new elements

- [Bug 48714](https://bz.apache.org/bugzilla/show_bug.cgi?id=48714) -  add new French messages

### General

- [Bug 47223](https://bz.apache.org/bugzilla/show_bug.cgi?id=47223) - Slow Aggregate Report Performance (StatCalculator)

- [Bug 47980](https://bz.apache.org/bugzilla/show_bug.cgi?id=47980) - hostname resolves to 127.0.0.1 - specifying IP not possible

- [Bug 47943](https://bz.apache.org/bugzilla/show_bug.cgi?id=47943) - DisabledComponentRemover is not used in Start class

- HeapDumper class for runtime generation of dumps

- Basic read-only JavaMail provider implementation for reading raw mail files

- [Bug 49540](https://bz.apache.org/bugzilla/show_bug.cgi?id=49540) - Sort "Add" menus alphabetically

## Non-functional changes

- Beanshell, JavaMail and JMS API (Apache Geronimo) jars are now included in the binary archive.

- Add TestBean Table Editor support

- Removed all external libraries from SVN; added download_jars Ant target

- Updated various jar files:

- BeanShell - 2.0b4 &rArr; 2.0b5

- Commons Codec - 1.3 &rArr; 1.4

- Commons-Collections - 3.2 &rArr; 3.2.1

- JTidy &rArr; r938

- JUnit - 3.8.2 &rArr; 4.8.1

- Logkit - 1.2 &rArr; 2.0

- Xalan Serializer = 2.7.1 (previously erroneously shown as 2.9.1)

- Xerces xml-apis = 1.3.04 (previously erroneously shown as 2.9.1)

- Some jar files were renamed.

---
title: "Apache JMeter™"
---

# Apache JMeter™

The **Apache JMeter™** application is open source software,
      a 100% pure Java application designed
      to load test functional behavior and measure performance.  It was
      originally designed for testing Web Applications but has
      since expanded to other test functions.

## What can I do with it?

Apache JMeter may be used to test performance both on static and dynamic
      resources, Web dynamic applications.   

      It can be used to simulate a heavy load on a server, group of servers,
      network or object to test its strength or to analyze overall performance
      under different load types.

Apache JMeter features include:

- Ability to load and performance test many different applications/server/protocol types:
        
        Web - HTTP, HTTPS (Java, NodeJS, PHP, ASP.NET, …)
        SOAP / REST Webservices
        FTP
        Database via JDBC
        LDAP
        Message-oriented middleware (MOM) via JMS
        Mail - SMTP(S), POP3(S) and IMAP(S)
        Native commands or shell scripts
        TCP
        Java Objects
- Full featured Test IDE that allows fast Test Plan **recording (from Browsers or native applications), building and debugging**.
- **[CLI mode (Command-line mode (previously called Non GUI) / headless mode)](usermanual/get-started.html#non_gui)** to load test from any Java compatible OS (Linux, Windows, Mac OSX, …)
- A complete and **[ready to present dynamic HTML report](usermanual/generating-dashboard.html)**
- Easy correlation through ability to extract data from most popular response formats, **[HTML](usermanual/component_reference.html#CSS/JQuery_Extractor), [JSON](usermanual/component_reference.html#JSON_Extractor),
        [XML](usermanual/component_reference.html#XPath_Extractor) or [any textual format](usermanual/component_reference.html#Regular_Expression_Extractor)**
- Complete portability and **100% Java purity**.
- Full **multi-threading** framework allows concurrent sampling by many threads and
        simultaneous sampling of different functions by separate thread groups.
- Caching and offline analysis/replaying of test results.
- **Highly Extensible core:**
      
        Pluggable Samplers allow unlimited testing capabilities.
        Scriptable Samplers (JSR223-compatible languages like Groovy and BeanShell)
        Several load statistics may be chosen with pluggable timers.
        Data analysis and visualization plugins allow great extensibility
        as well as personalization.
        Functions can be used to provide dynamic input to a test or provide data manipulation.
        Easy Continuous Integration through 3rd party Open Source libraries for Maven, Gradle and Jenkins.

## How do I do it?

- [Using JMeter](usermanual/index.html) to understand how to use it
- [Component reference](usermanual/component_reference.html) to have detailed information for every Test element
- [Functions reference](usermanual/functions.html) to have detailed information and examples for every function
- [Properties reference](usermanual/properties_reference.html) for all properties that allow you to customize JMeter
- [Javadoc API documentation](api/index.html)
- [JMeter FAQ (Wiki)](https://cwiki.apache.org/confluence/display/JMETER/JMeterFAQ)
- [JMeter Wiki](https://cwiki.apache.org/confluence/display/JMETER/Home)
- [Building JMeter and Add-Ons](building.html) for advanced usage

## JMeter is not a browser

JMeter is not a browser, it works at protocol level.
As far as web-services and remote services are concerned, JMeter looks like a browser (or rather, multiple browsers);
however JMeter does not perform all the actions supported by browsers.
In particular, JMeter does not execute the Javascript found in HTML pages.
Nor does it render the HTML pages as a browser does
(it's possible to view the response as HTML etc., but the timings are not included in any samples, and only one sample in one thread is ever displayed at a time).

## Tutorials

- [Distributed Testing](usermanual/jmeter_distributed_testing_step_by_step.html)
- [Recording Tests](usermanual/jmeter_proxy_step_by_step.html)
- [JUnit Sampler](usermanual/junitsampler_tutorial.html)
- [Access Log Sampler](usermanual/jmeter_accesslog_sampler_step_by_step.html)
- [Extending JMeter](usermanual/jmeter_tutorial.html)

## Further Information About JMeter

- [Changes List](changes.html)
- [Read about existing Issues (Bugs or Enhancements) or reporting new ones (please do it !)](issues.html)
- [License](https://www.apache.org/licenses/)
- [Mailing Lists](mail.html)
- [Source Repositories](svnindex.html)
- [Contributors](https://cwiki.apache.org/confluence/display/JMETER/JMeterCommitters)


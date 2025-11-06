---
title: Version 2.3.4
---

# Version 2.3.4

## Summary of main changes

This is a minor bug-fix release, mainly to correct some bugs that were accidentally added in 2.3.3.

## Known bugs

The Include Controller has some problems in non-GUI mode.
In particular, it can cause a NullPointerException if there are two include controllers with the same name.

Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

The menu item Options / Choose Language does not change all the displayed text to the new language.
[The behaviour has improved, but language change is still not fully working]
To override the default local language fully, set the JMeter property "language" before starting JMeter.

## Bug fixes

### HTTP Samplers and Proxy

- [Bug 47321](https://bz.apache.org/bugzilla/show_bug.cgi?id=47321) -  HTTPSampler2 response timeout not honored

### Other Samplers

- [Bug 47290](https://bz.apache.org/bugzilla/show_bug.cgi?id=47290) -  Infinite loop on connection factory lookup (JMS)
- JDBC Sampler should not close Prepared or Callable statements as these are cached

### Controllers

- [Bug 39509](https://bz.apache.org/bugzilla/show_bug.cgi?id=39509) -  Once-only controller running twice

### Listeners

- Change ResultCollector to only warn if the directory was not created
- Fix some synchronisation issues in ResultCollector and SampleResult (wrong locks were being used)

### I18N

- Fixed bug introduced in 2.3.3: JMeter does not start up if there is no messages.properties file for the default Locale.

### General

- Fix problems with remote clients - bug introduced in 2.3.3
- [Bug 47377](https://bz.apache.org/bugzilla/show_bug.cgi?id=47377) - Make ClassFinder more robust and close zipfile resources
- Fix some errors in generating the documentation (latent bug revealed in 2.3.3 when Velocity was upgraded)

## Improvements

### Other samplers

- [Bug 47266](https://bz.apache.org/bugzilla/show_bug.cgi?id=47266) - FTP Request Sampler: allow specifying an FTP port, other than the default


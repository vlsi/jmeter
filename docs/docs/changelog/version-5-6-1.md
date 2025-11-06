---
title: Version 5.6.1
---

# Version 5.6.1

Summary

- [New and Noteworthy](#New and Noteworthy)

- [Incompatible changes](#Incompatible changes)

- [Bug fixes](#Bug fixes)

- [Improvements](#Improvements)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### New and Noteworthy

### Improvements

### HTTP Samplers and Test Script Recorder

  - [PR #6010](https://github.com/apache/jmeter/pull/6010)Use UTF-8 as a default encoding in HTTP sampler. It enables sending parameter names, and filenames with unicode characters

  - [PR #6010](https://github.com/apache/jmeter/pull/6010)Test Recorder will use UTF-8 encoding by default, so it will infer human-readable arguments rather than percent-encoded ones

### Non-functional changes

  - [PR #6000](https://github.com/apache/jmeter/pull/6000)Add release-drafter for populating GitHub releases info based on the merged PRs

  - [PR #5989](https://github.com/apache/jmeter/pull/5989)Use Gradle toolchains for JDK provisioning, enable building and testing with different JDKs, start testing with Java 21

  - [PR #5991](https://github.com/apache/jmeter/pull/5991)Update jackson-core, jackson-databind, jackson-annotations to 2.15.2 (from 2.15.1)

  - [PR #5993](https://github.com/apache/jmeter/pull/5993)Update ph-commons to 10.2.5 (from 10.2.4)

  - [PR #6017](https://github.com/apache/jmeter/pull/6017)Update kotlin-stdlib to 1.8.22 (from 1.8.21)

  - [PR #6020](https://github.com/apache/jmeter/pull/6020)Update error_prone_annotations to 2.20.0 (from 2.19.1)

  - [PR #6023](https://github.com/apache/jmeter/pull/6023)Update checker-qual to 3.35.0 (from 3.34.0)

### Other Samplers

  - [PR #6028](https://github.com/apache/jmeter/pull/6028) Change default value for `sampleresult.default.encoding` to UTF-8 (it inherits default HTTP encoding which was modified in [PR #6010](https://github.com/apache/jmeter/pull/6010))

 

### Bug fixes

### Thread Groups

  - [PR #6011](https://github.com/apache/jmeter/pull/6011)Regression since 5.6: ThreadGroups are running endlessly in non-gui mode: use default value
    for LoopController.continue_forever rather than initializing it in the constructor

### Other Samplers

  - [PR #6012](https://github.com/apache/jmeter/pull/6012) Java Request sampler cannot be enabled again after disabling in UI (regression since 5.6)

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Alex Schwartz, [@alexsch01](https://github.com/alexsch01)

We also thank bug reporters who helped us improve JMeter.

  - David Getzlaff, [@dgetzlaf](https://github.com/dgetzlaf)

  - LeeBaul, [@libaolu](https://github.com/libaolu)

Apologies if we have omitted anyone else.

 

### Known problems and workarounds

  - `pom.xml` misses `&lt;version&gt;` tags for `auto-service-annotations`, so Maven can't infer transitive dependencies. The issue is resolved in 5.6.2

  - [Issue #6043](https://github.com/apache/jmeter/issues/6043) `Min` is always `0` in `Summary Report` (fixed in 5.6.3)

- The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).

- 
The numbers that appear to the left of the green box are the number of active threads / total number of threads,
the total number of threads only applies to a locally run test, otherwise it will show `0` (see [Bug 55510](https://bz.apache.org/bugzilla/show_bug.cgi?id=55510)).

- 
Note that under some windows systems you may have this WARNING:
```

java.util.prefs.WindowsPreferences
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0
x80000002. Windows RegCreateKeyEx(&hellip;) returned error code 5.

```

The fix is to run JMeter as Administrator, it will create the registry key for you, then you can restart JMeter as a normal user and you won't have the warning anymore.

- 
You may encounter the following error:
```
java.security.cert.CertificateException: Certificates does not conform to algorithm constraints
```

 if you run a HTTPS request on a web site with a SSL certificate (itself or one of SSL certificates in its chain of trust) with a signature
 algorithm using MD2 (like `md2WithRSAEncryption`) or with a SSL certificate with a size lower than 1024 bits.
This error is related to increased security in Java 8+.

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
The fix is to use JDK8_u45 or later.

- 
View Results Tree may fail to display some HTML code under HTML renderer, see [Bug 54586](https://bz.apache.org/bugzilla/show_bug.cgi?id=54586).
This is due to a known Java bug which fails to parse "`px`" units in row/col attributes.
See Bug JDK-8031109
The fix is to use JDK9 b65 or later.

- 
JTable selection with keyboard (SHIFTup/down) is totally unusable with Java 7 on Mac OSX.
This is due to a known Java bug JDK-8025126
The fix is to use JDK 8 b132 or later.

- 
Since Java 11 the JavaScript implementation [Nashorn has been deprecated](https://openjdk.java.net/jeps/335).
Java will emit the following deprecation warnings, if you are using JavaScript based on Nashorn.
```

Warning: Nashorn engine is planned to be removed from a future JDK release

```

To silence these warnings, add `-Dnashorn.args=--no-deprecation-warning` to your Java arguments.
That can be achieved by setting the enviroment variable `JVM_ARGS`
```

export JVM_ARGS="-Dnashorn.args=--no-deprecation-warning"

```

- 
With Java 15 the JavaScript implementation [Nashorn has been removed](https://openjdk.java.net/jeps/372). To add back a JSR-223 compatible JavaScript engine you have two options:
  
    Use Mozilla Rhino
    Copy [rhino-engine-1.7.14.jar](https://github.com/mozilla/rhino/releases/download/Rhino1_7_14_Release/rhino-engine-1.7.14.jar) into `$JMETER_HOME/lib/ext`.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 (or later) and its dependencies and set the module path is outlined below:
      ```

mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.3/nashorn-core-15.3.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.5/asm-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.5/asm-commons-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.5/asm-util-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.5/asm-tree-9.5.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.5/asm-analysis-9.5.jar
popd
export JVM_ARGS="--module-path $PWD/lib/modules"
./bin/jmeter
      
```

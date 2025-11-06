---
title: Changes
---

# Changes

:::note
**This page details the changes made in the current version only.**
  

Earlier changes are detailed in the [History of Previous Changes](changes_history.html).
:::

  
:::note
JMeter 6.x requires Java 17 or later for execution (Java 21 is recommended).
:::

# Version 6.0.0

Summary

- [Changes](#Changes)


### Changes

### General

- [PR #6220](https://github.com/apache/jmeter/pull/6220) Require Java 17 or later for running JMeter
- [PR #6274](https://github.com/apache/jmeter/pull/6274) Change references to old MySQL driver to new class `com.mysql.cj.jdbc.Driver`
- [Issue #6352](https://github.com/apache/jmeter/issues/6352) Calculate delays in Open Model Thread Group and Precise Throughput
        Timer relative to start of Thread Group instead of the start of the test.
- [Issue #6357](https://github.com/apache/jmeter/issues/6357)[PR #6358](https://github.com/apache/jmeter/pull/6358) Ensure writable directories when copying template files while report generation.


### Thanks

We thank all contributors mentioned in bug and improvement sections above:



We also thank bug reporters who helped us improve JMeter.



Apologies if we have omitted anyone else.


### Known problems and workarounds

- The Once Only controller behaves correctly under a Thread Group or Loop Controller,
but otherwise its behaviour is not consistent (or clearly specified).
- The numbers that appear to the left of the green box are the number of active threads / total number of threads,
the total number of threads only applies to a locally run test, otherwise it will show `0` (see [Bug 55510](https://bz.apache.org/bugzilla/show_bug.cgi?id=55510)).
- Note that under some windows systems you may have this WARNING:

java.util.prefs.WindowsPreferences
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0
x80000002. Windows RegCreateKeyEx(…) returned error code 5.

The fix is to run JMeter as Administrator, it will create the registry key for you, then you can restart JMeter as a normal user and you won't have the warning anymore.
- You may encounter the following error:
java.security.cert.CertificateException: Certificates does not conform to algorithm constraints
 if you run a HTTPS request on a web site with a SSL certificate (itself or one of SSL certificates in its chain of trust) with a signature
 algorithm using MD2 (like `md2WithRSAEncryption`) or with a SSL certificate with a size lower than 1024 bits.
This error is related to increased security in Java 8+.
  

To allow you to perform your HTTPS request, you can downgrade the security of your Java installation by editing
the Java `jdk.certpath.disabledAlgorithms` property. Remove the MD2 value or the constraint on size, depending on your case.
  

This property is in this file:
JAVA_HOME/jre/lib/security/java.security
See  [Bug 56357](https://bz.apache.org/bugzilla/show_bug.cgi?id=56357) for details.
- Since Java 11 the JavaScript implementation [Nashorn has been deprecated](https://openjdk.java.net/jeps/335).
Java will emit the following deprecation warnings, if you are using JavaScript based on Nashorn.

Warning: Nashorn engine is planned to be removed from a future JDK release

To silence these warnings, add `-Dnashorn.args=--no-deprecation-warning` to your Java arguments.
That can be achieved by setting the enviroment variable `JVM_ARGS`

export JVM_ARGS="-Dnashorn.args=--no-deprecation-warning"
- With Java 15 the JavaScript implementation [Nashorn has been removed](https://openjdk.java.net/jeps/372). To add back a JSR-223 compatible JavaScript engine you have two options:
  
    Use Mozilla Rhino
    Copy rhino-engine-1.7.14.jar into $JMETER_HOME/lib/ext.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 (or later) and its dependencies and set the module path is outlined below:
      
mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.4/nashorn-core-15.4.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.6/asm-9.6.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.6/asm-commons-9.6.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.6/asm-util-9.6.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.6/asm-tree-9.6.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.6/asm-analysis-9.6.jar
popd
export JVM_ARGS="--module-path $PWD/lib/modules"
./bin/jmeter


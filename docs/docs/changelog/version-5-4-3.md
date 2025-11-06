---
title: Version 5.4.3
---

# Version 5.4.3

Summary

This version is a fix release against the vulnerability CVE-2021-45105: Apache Log4j2 versions 2.0-alpha1 through 2.16.0 (excluding 2.12.3) did not protect from uncontrolled recursion from self-referential lookups. This allows an attacker with control over Thread Context Map data to cause a denial of service when a crafted string is interpreted.

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

### Non-functional changes

    - Updated Apache log4j2 to 2.17.0 (from 2.16.0).

 

### Known problems and workarounds

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
    Copy [rhino-engine-1.7.13.jar](https://github.com/mozilla/rhino/releases/download/Rhino1_7_13_Release/rhino-engine-1.7.13.jar) into `$JMETER_HOME/lib/ext`.
    Use OpenJDK Nashorn
    
      The OpenJDK Nashorn implementation comes as a module. To use it, you will have to download it and add it to the module path. A hacky way to download the version 15.0 and its dependencies and set the module path is outlined below:
      ```

mkdir lib/modules
pushd lib/modules
wget https://repo1.maven.org/maven2/org/openjdk/nashorn/nashorn-core/15.0/nashorn-core-15.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm/9.0/asm-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.0/asm-commons-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-util/9.0/asm-util-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.0/asm-tree-9.0.jar
wget https://repo1.maven.org/maven2/org/ow2/asm/asm-analysis/9.0/asm-analysis-9.0.jar
popd
export JVM_ARGS="--modulepath $PWD/lib/modules"
./bin/jmeter
      
```

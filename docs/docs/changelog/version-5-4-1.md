---
title: Version 5.4.1
---

# Version 5.4.1

Summary

- [Incompatible changes](#Incompatible changes)

- [Non-functional changes](#Non-functional changes)

- [Known problems and workarounds](#Known problems and workarounds)

- [Thanks](#Thanks)

### Incompatible changes

    - Restart after LAF change has been reinstated, it had been removed in JMeter 5.3

### Improvements

### General

  - 65028Add documentation for the property `client.rmi.localport`

  - 65012Better handling of displaying long comments in the GUI

### Non-functional changes

    - Updated SaxonHE to 9.9.1-8 (from 9.9.1-7)

    - Updated asm to 9.0 (from 7.3.1)

    - Updated bouncycastle to 1.67 (from 1.66)

    - Updated caffeine to 2.8.8 (from 2.8.0)

    - Updated commons-codec to 1.15 (from 1.14)

    - Updated commons-io to 2.8.0 (from 2.7)

    - Updated commons-net to 3.7.2 (from 3.7)

    - Updated jackson to 2.10.5 (from 2.10.3)

    - Updated junit to 4.13.1 (from 4.13)

    - Updated ph-commons to 9.5.1 (from 9.4.1)

    - Updated ph-css to 6.2.3 (from 6.2.1)

    - Updated groovy to 3.0.7 (from 3.0.5)

    - Updated xstream to 1.4.15 (from 1.4.14)

 

### Bug fixes

### HTTP Samplers and Test Script Recorder

  - 64955Keystore password not reset on reload

  - 65002HTTP(S) Test Script recorder creates an invalid Basic authentication URL. Contributed by Ubik Load Pack (https://ubikloadpack.com)

  - 65004HTTP(S) Test Script recorder computes wrong HTTP Request breaking the application. Contributed by Ubik Load Pack (https://ubikloadpack.com)

  - 64543On MacOSX, Darklaf- IntelliJ Theme throws NPE in javax.swing.ToolTipManager.initiateToolTip

  - 65024Sending mime type with parameter throws IllegalArgumentException

  - 65029Try harder to correctly guess the URL for applets, when download embedded URLs is enabled

### Other Samplers

  - 65034Ignore `SocketTimeoutException` on `BinaryTCPClientImpl`, when no EOM Byte is set. Regression
     introduced by commit c190641e4f0474a34a366a72364b0a8dd25bfc81 which fixed 52104. That bug was bout handling
     the case of waiting for an EOM.

### Listeners

  - 64821When importing XML formatted jtl files, sub samplers will get renamed

  - 65052XPath2 Tester and JSON JMESPath Tester are missing in `view.results.tree.renderers_order` property

### Documentation

  - 64960Change scheduler reference in Thread Group documentation. Contributed by Ori Marko

  - 65006Illustration for completed HTTP Request Defaults element (Figure 4.4) contains misleading info

### General

  - 64957When importing example test plan JMeter displays an NullPointerException

  - 64961Darklaf: On Windows 7, NPE in BasicEditorPaneUI.cleanDisplayProperties with Darklaf Intellij

  - 64963Blank comment tooltip is visible

  - 64969RemoteJMeterEngineImpl#rexit doesn't unexport RemoteJMeterEngineImpl on exit. Contributed by luo_isaiah at qq.com

  - 64984Darklaf LAF: Selecting a Test element does not work under certain screen resolutions on Windows. With the help of Jannis Weis

  - 65008SampleResult.setIgnore() called from PostProcessor is not considered

  - 64993Daklaf LAF: Menu navigation not working with keyboard shortcuts. With the help of Jannis Weis

  - 65013POST multipart/form-data cURL code with quoted arguments is not imported correctly

 

### Thanks

We thank all contributors mentioned in bug and improvement sections above:

  - Ori Marko (orimarko at gmail.com)

  - 罗寅卓 (luo_isaiah at qq.com)

  - Ubik Load Pack

  - [Jannis Weis](https://github.com/weisJ/darklaf)

We also thank bug reporters who helped us improve JMeter.

Apologies if we have omitted anyone else.

 

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

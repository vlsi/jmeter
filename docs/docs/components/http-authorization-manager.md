---
title: HTTP Authorization Manager
sidebar_position: 4
---

# HTTP Authorization Manager

![HTTP Authorization Manager](/img/images/screenshots/http-config/http-auth-manager.png)

The Authorization Manager lets you specify one or more user logins for web pages that are
restricted using server authentication.  You see this type of authentication when you use
your browser to access a restricted page, and your browser displays a login dialog box.  JMeter
transmits the login information when it encounters this type of page.

The Authorization headers may not be shown in the Tree View Listener "`Request`" tab.
The Java implementation does pre-emptive authentication, but it does not
return the Authorization header when JMeter fetches the headers.
The HttpComponents (HC 4.5.X) implementation defaults to pre-emptive since 3.2 and the header will be shown.
To disable this, set the values as below, in which case authentication will only be performed in response to a challenge.

In the file `jmeter.properties` set `httpclient4.auth.preemptive=false`

:::note
Note: the above settings only apply to the HttpClient sampler.
:::

:::note
When looking for a match against a URL, JMeter checks each entry in turn, and stops when it finds the first match.
Thus the most specific URLs should appear first in the list, followed by less specific ones.
Duplicate URLs will be ignored.
If you want to use different usernames/passwords for different threads, you can use variables.
These can be set up using a [CSV Data Set Config](#csv-data-set-config) Element (for example).
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Clear auth on each iteration? | Yes | Used by Kerberos authentication. If checked, authentication will be done on each iteration of Main Thread Group loop even if it has already been done in a previous one.         This is usually useful if each main thread group iteration represents behaviour of one Virtual User. |
| Base URL | Yes | A partial or complete URL that matches one or more HTTP Request URLs.  As an example, say you specify a Base URL of "`http://localhost/restricted/`" with a `Username` of "`jmeter`" and a `Password` of "`jmeter`".  If you send an HTTP request to the URL "`http://localhost/restricted/ant/myPage.html`", the Authorization Manager sends the login information for the user named, "`jmeter`". |
| Username | Yes | The username to authorize. |
| Password | Yes | The password for the user. (N.B. this is stored unencrypted in the test plan) |
| Domain | No | The domain to use for NTLM. |
| Realm | No | The realm to use for NTLM. |
| Mechanism | No | Type of authentication to perform. JMeter can perform different types of authentications based on used Http Samplers:  JavaBASIC HttpClient 4BASIC, DIGEST and Kerberos |


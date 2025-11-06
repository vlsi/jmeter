---
title: HTTP URL Re-writing Modifier
sidebar_position: 2
---

# HTTP URL Re-writing Modifier

![HTTP URL Re-writing Modifier](/img/images/screenshots/url_rewriter.png)

This modifier works similarly to the HTML Link Parser, except it has a specific purpose for which
it is easier to use than the HTML Link Parser, and more efficient.  For web applications that
use URL Re-writing to store session ids instead of cookies, this element can be attached at the
ThreadGroup level, much like the [HTTP Cookie Manager](#http-cookie-manager).  Simply give it the name
of the session id parameter, and it will find it on the page and add the argument to every
request of that ThreadGroup.

Alternatively, this modifier can be attached to select requests and it will modify only them.
Clever users will even determine that this modifier can be used to grab values that elude the
[HTML Link Parser](#html-link-parser).


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name given to this element in the test tree. |
| Session Argument Name | Yes | The name of the parameter to grab from         previous response.  This modifier will find the parameter anywhere it exists on the page, and         grab the value assigned to it, whether it's in an HREF or a form. |
| Path Extension | No | Some web apps rewrite URLs by appending         a semi-colon plus the session id parameter.  Check this box if that is so. |
| Do not use equals in path extension | No | Some web apps rewrite URLs without using an "`=`" sign between the parameter name and value (such as Intershop Enfinity). |
| Do not use questionmark in path extension | No | Prevents the query string to end up in the path extension (such as Intershop Enfinity). |
| Cache Session Id? | Yes | Should the value of the session Id be saved for later use when the session Id is not present? |
| URL Encode | No | URL Encode value when writing parameter |


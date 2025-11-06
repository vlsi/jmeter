---
title: "User's Manual: Building an Advanced Web Test Plan"
---

# 5. Building an Advanced Web Test Plan

In this section, you will learn how to create advanced
[Test Plans](build-test-plan.html) to test a Web site.

For an example of a basic Test Plan, see
[Building a Web Test Plan](build-web-test-plan.html).

# 5.1 Handling User Sessions With URL Rewriting

If your web application uses URL rewriting rather than cookies to save session information,
then you'll need to do a bit of extra work to test your site.

To respond correctly to URL rewriting, JMeter needs to parse the HTML
received from the server and retrieve the unique session ID.  Use the appropriate [HTTP URL Re-writing Modifier](#http-url-re-writing-modifier)
to accomplish this.  Simply enter the name of your session ID parameter into the modifier, and it
will find it and add it to each request.  If the request already has a value, it will be replaced.
If "Cache Session Id?" is checked, then the last found session id will be saved,
and will be used if the previous HTTP sample does not contain a session id.

Download [this example](../demos/URLRewritingExample.jmx). In Figure 1 is shown a
test plan using URL rewriting.  Note that the URL Re-writing modifier is added to the SimpleController,
thus assuring that it will only affect requests under that SimpleController.

In Figure 2, we see the URL Re-writing modifier GUI, which just has a field for the user to specify
the name of the session ID parameter.  There is also a checkbox for indicating that the session ID should
be part of the path (separated by a ";"), rather than a request parameter

# 5.2 Using a Header Manager

The [HTTP Header Manager](#http-header-manager) lets you customize what information
JMeter sends in the HTTP request header.  This header includes properties like "User-Agent",
"Pragma", "Referer", etc.

The [HTTP Header Manager](#http-header-manager), like the [HTTP Cookie Manager](#http-cookie-manager),
should probably be added at the Thread Group level, unless for some reason you wish to
specify different headers for the different [HTTP Request](#http-request) objects in
your test.


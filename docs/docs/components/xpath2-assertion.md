---
title: XPath2 Assertion
sidebar_position: 16
---

# XPath2 Assertion

![XPath2 Assertion](/img/images/screenshots/xpath_assertion.png)

The XPath2 Assertion tests a document for well formedness.  Using "`/`" will match any well-formed
document, and is the default XPath2 Expression.
The assertion also supports boolean expressions, such as "`count(//*error)=2`".

Some sample expressions:- `//title[text()='Text to match']` - matches `<title>Text to match</title>` anywhere in the response
- `/title[text()='Text to match']` - matches `<title>Text to match</title>` at root level in the response


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Namespaces aliases list | No | List of namespaces aliases you want to use to parse the document, one line per declaration.     You must specify them as follow: `prefix=namespace`. This implementation makes it easier to     use namespaces than with the old XPathExtractor version. |
| XPath2 Assertion | Yes | XPath to match in the document. |
| Invert assertion | No | Will fail if xpath expression returns true or matches, succeed otherwise |
| Namespace aliases list | No | List of namespace aliases prefix=full namespace (one per line) |


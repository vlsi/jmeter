---
title: HTTP Header Manager
sidebar_position: 8
---

# HTTP Header Manager

![HTTP Header Manager](/img/images/screenshots/http-config/http-header-manager.png)

The Header Manager lets you add or override HTTP request headers.

**JMeter now supports multiple Header Managers**. The header entries are merged to form the list for the sampler.
If an entry to be merged matches an existing header name, it replaces the previous entry.
This allows one to set up a default set of headers, and apply adjustments to particular samplers.
Note that an empty value for a header does not remove an existing header, it justs replace its value.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Name (Header) | No (You should have at least one, however) | Name of the request header.         Two common request headers you may want to experiment with are "`User-Agent`" and "`Referer`". |
| Value | No (You should have at least one, however) | Request header value. |
| Add Button | N/A | Add an entry to the header table. |
| Delete Button | N/A | Delete the currently selected table entry. |
| Load Button | N/A | Load a previously saved header table and add the entries to the existing header table entries. |
| Save As Button | N/A | Save the current header table to a file. |


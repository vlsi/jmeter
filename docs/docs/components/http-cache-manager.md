---
title: HTTP Cache Manager
sidebar_position: 5
---

# HTTP Cache Manager

![HTTP Cache Manager](/img/images/screenshots/http-config/http-cache-manager.png)

The HTTP Cache Manager is used to add caching functionality to HTTP requests within its scope to simulate browser cache feature.
Each Virtual User thread has its own Cache. By default, Cache Manager will store up to 5000 items in cache per Virtual User thread, using LRU algorithm.
Use property "`maxSize`" to modify this value. Note that the more you increase this value the more HTTP Cache Manager will consume memory, so be sure to adapt the `-Xmx` JVM option accordingly.

If a sample is successful (i.e. has response code `2xx`) then the `Last-Modified` and `Etag` (and `Expired` if relevant) values are saved for the URL.
Before executing the next sample, the sampler checks to see if there is an entry in the cache,
and if so, the `If-Last-Modified` and `If-None-Match` conditional headers are set for the request.

Additionally, if the "`Use Cache-Control/Expires header`" option is selected, then the `Cache-Control`/`Expires` value is checked against the current time.
If the request is a `GET` request, and the timestamp is in the future, then the sampler returns immediately,
without requesting the URL from the remote server. This is intended to emulate browser behaviour.
Note that if `Cache-Control` header is "`no-cache`", the response will be stored in cache as pre-expired,
so will generate a conditional `GET` request.
If `Cache-Control` has any other value,
the "`max-age`" expiry option is processed to compute entry lifetime, if missing then expire header will be used, if also missing entry will be cached
as specified in RFC 2616 section 13.2.4 using `Last-Modified` time and response Date.

:::note
If the requested document has not changed since it was cached, then the response body will be empty.
Likewise if the `Expires` date is in the future.
This may cause problems for Assertions.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Clear cache each iteration | Yes | If selected, then the cache is cleared at the start of the thread. |
| Use Cache Control/Expires header when processing GET requests | Yes | See description above. |
| Max Number of elements in cache | Yes | See description above. |


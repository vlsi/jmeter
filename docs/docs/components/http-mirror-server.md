---
title: HTTP Mirror Server
sidebar_position: 6
---

# HTTP Mirror Server

![HTTP Mirror Server](/img/images/screenshots/mirrorserver.png)

The HTTP Mirror Server is a very simple HTTP server - it simply mirrors the data sent to it.
This is useful for checking the content of HTTP requests.

It uses default port `8081`.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Port | Yes | Port on which Mirror server listens, defaults to `8081`. |
| Max Number of threads | No | If set to a value > `0`, number of threads serving requests will be limited to the configured number, if set to a value ≤ `0`         a new thread will be created to serve each incoming request. Defaults to `0` |
| Max Queue size | No | Size of queue used for holding tasks before they are executed by Thread Pool, when Thread pool is exceeded, incoming requests will         be held in this queue and discarded when this queue is full. This parameter is only used if Max Number of Threads is greater than `0`. Defaults to `25` |


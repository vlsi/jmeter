---
title: Backend Listener
sidebar_position: 21
---

# Backend Listener

![Backend Listener](/img/images/screenshots/backend_listener.png)

BackendListenerClient.
By default, a Graphite implementation is provided.
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | Yes | Descriptive name for this element that is shown in the tree. |
| Backend Listener implementation | Yes | Class of the `BackendListenerClient` implementation. |
| Async Queue size | Yes | Size of the queue that holds the SampleResults while they are processed asynchronously. |
| Parameters | Yes | Parameters of the `BackendListenerClient` implementation. |


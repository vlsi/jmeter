---
title: Bolt Connection Configuration
sidebar_position: 21
---

# Bolt Connection Configuration

![Bolt Connection Configuration](/img/images/screenshots/bolt-connection-config.png)

[Bolt Request](#bolt-request)Sampler)
        from the supplied Connection settings.
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree. |
| Comments | No | Free text for additional details. |
| Bolt URI | Yes | The database URI. |
| Username | No | User account. |
| Password | No | User credentials. |
| Connection Pool Max Size | Yes | Max size of the Neo4j driver Bolt connection pool.             Raise the value if running large number of concurrent threads, so that JMeter threads are not blocked waiting             for a connection to be released to the pool. |


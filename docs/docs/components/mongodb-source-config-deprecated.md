---
title: MongoDB Source Config (DEPRECATED)
sidebar_position: 20
---

# MongoDB Source Config (DEPRECATED)

![MongoDB Source Config (DEPRECATED)](/img/images/screenshots/mongodb-source-config.png)

[MongoDB Script](#mongodb-script)Sampler)
     from the supplied Connection settings. Each thread gets its own connection.
     The connection configuration name is used by the JDBC Sampler to select the appropriate
     connection.You can then access `com.mongodb.DB` object in Beanshell or JSR223 Test Elements through the element MongoDBHolder
     using this code

```

import com.mongodb.DB;
import org.apache.jmeter.protocol.mongodb.config.MongoDBHolder;
DB db = MongoDBHolder.getDBFromSource("value of property MongoDB Source",
            "value of property Database Name");
…
    
```


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for the connection configuration that is shown in the tree. |
| Server Address List | Yes | Mongo DB Servers |
| MongoDB Source | Yes | The name of the variable the connection is tied to.         Each name must be different. If there are two configuration elements using the same name, only one will be saved. |
| Keep Trying | No | If `true`, the driver will keep trying to connect to the same server in case that the socket cannot be established.                There is maximum amount of time to keep retrying, which is 15s by default.   This can be useful to avoid some exceptions being thrown when a server is down temporarily by blocking the operations.                It can also be useful to smooth the transition to a new primary node (so that a new primary node is elected within the retry time).                Note that when using this flag                                for a replica set, the driver will try to connect to the old primary node for that time, instead of failing over to the new one right away                  this does not prevent exception from being thrown in read/write operations on the socket, which must be handled by application.                              Even if this flag is false, the driver already has mechanisms to automatically recreate broken connections and retry the read operations.                          Default is `false`. |
| Maximum connections per host | No |  |
| Connection timeout | No | The connection timeout in milliseconds.   It is used solely when establishing a new connection `Socket.connect(java.net.SocketAddress, int)`   Default is `0` and means no timeout. |
| Maximum retry time | No | The maximum amount of time in milliseconds to spend retrying to open connection to the same server.   Default is `0`, which means to use the default 15s if `autoConnectRetry` is on. |
| Maximum wait time | No | The maximum wait time in milliseconds that a thread may wait for a connection to become available.   Default is `120,000`. |
| Socket timeout | No | The socket timeout in milliseconds It is used for I/O socket read and write operations `Socket.setSoTimeout(int)`   Default is `0` and means no timeout. |
| Socket keep alive | No | This flag controls the socket keep alive feature that keeps a connection alive through firewalls `Socket.setKeepAlive(boolean)`                Default is `false`. |
| ThreadsAllowedToBlockForConnectionMultiplier | No | This multiplier, multiplied with the connectionsPerHost setting, gives the maximum number of threads that may be waiting for a connection to become available from the pool.            All further threads will get an exception right away.            For example if `connectionsPerHost` is `10` and `threadsAllowedToBlockForConnectionMultiplier` is `5`, then up to 50 threads can wait for a connection.            Default is `5`. |
| Write Concern: Safe | No | If `true` the driver will use a `WriteConcern` of `WriteConcern.SAFE` for all operations.                If `w`, `wtimeout`, `fsync` or `j` are specified, this setting is ignored.                Default is `false`. |
| Write Concern: Fsync | No | The `fsync` value of the global `WriteConcern`.                Default is `false`. |
| Write Concern: Wait for Journal | No | The `j` value of the global `WriteConcern`.                Default is `false`. |
| Write Concern: Wait for servers | No | The `w` value of the global `WriteConcern`.   Default is `0`. |
| Write Concern: Wait timeout | No | The `wtimeout` value of the global `WriteConcern`.   Default is `0`. |
| Write Concern: Continue on error | No | If batch inserts should continue after the first error |


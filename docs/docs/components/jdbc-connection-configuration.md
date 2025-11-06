---
title: JDBC Connection Configuration
sidebar_position: 10
---

# JDBC Connection Configuration

![JDBC Connection Configuration](/img/images/screenshots/jdbc-config/jdbc-conn-config.png)

[JDBC Request](#jdbc-request)Sampler)
     from the supplied JDBC Connection settings. The connection may be optionally pooled between threads.
     Otherwise each thread gets its own connection.
     The connection configuration name is used by the JDBC Sampler to select the appropriate
     connection.
     The used pool is DBCP, see[BasicDataSource Configuration Parameters](https://commons.apache.org/proper/commons-dbcp/configuration.html)
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for the connection configuration that is shown in the tree. |
| Variable Name for created pool | Yes | The name of the variable the connection is tied to.         Multiple connections can be used, each tied to a different variable, allowing JDBC Samplers         to select the appropriate connection.         Each name must be different. If there are two configuration elements using the same name,         only one will be saved. JMeter logs a message if a duplicate name is detected. |
| Max Number of Connections | Yes | Maximum number of connections allowed in the pool.         In most cases, **set this to zero (0)**.         This means that each thread will get its own pool with a single connection in it, i.e.         the connections are not shared between threads.                     If you really want to use shared pooling (why?), then set the max count to the same as the number of threads         to ensure threads don't wait on each other. |
| Max Wait (ms) | Yes | Pool throws an error if the timeout period is exceeded in the         process of trying to retrieve a connection, see [BasicDataSource.html#getMaxWaitMillis](https://commons.apache.org/proper/commons-dbcp/api-2.1.1/org/apache/commons/dbcp2/BasicDataSource.html#getMaxWaitMillis--) |
| Time Between Eviction Runs (ms) | Yes | The number of milliseconds to sleep between runs of the idle object evictor thread. When non-positive, no idle object evictor thread will be run. (Defaults to "`60000`", 1 minute).         See [BasicDataSource.html#getTimeBetweenEvictionRunsMillis](https://commons.apache.org/proper/commons-dbcp/api-2.1.1/org/apache/commons/dbcp2/BasicDataSource.html#getTimeBetweenEvictionRunsMillis--) |
| Auto Commit | Yes | Turn auto commit on or off for the connections. |
| Transaction isolation | Yes | Transaction isolation level |
| Pool Prepared Statements | Yes | Max number of Prepared Statements to pool per connection. `"-1`" disables the pooling and "`0`" means unlimited number of Prepared Statements to pool. (Defaults to "`-1`") |
| Preinit Pool | No | The connection pool can be initialized instantly. If set to `False` (default), the JDBC request samplers using this pool might measure higher response times for the first queries – as the connection establishment time for the whole pool is included. |
| Init SQL statements separated by new line | No | A Collection of SQL statements that will be used to initialize physical connections when they are first created. These statements are executed only once - when the configured connection factory creates the connection. |
| Test While Idle | Yes | Test idle connections of the pool, see [BasicDataSource.html#getTestWhileIdle](https://commons.apache.org/proper/commons-dbcp/api-2.1.1/org/apache/commons/dbcp2/BasicDataSource.html#getTestWhileIdle--).         Validation Query will be used to test it. |
| Soft Min Evictable Idle Time(ms) | Yes | Minimum amount of time a connection may sit idle in the pool before it is eligible for eviction by the idle object evictor, with the extra condition that at least `minIdle` connections remain in the pool.         See [BasicDataSource.html#getSoftMinEvictableIdleTimeMillis](https://commons.apache.org/proper/commons-dbcp/api-2.1.1/org/apache/commons/dbcp2/BasicDataSource.html#getSoftMinEvictableIdleTimeMillis--).         Defaults to 5000 (5 seconds) |
| Validation Query | No | A simple query used to determine if the database is still responding.         This defaults to the '`isValid()`' method of the jdbc driver, which is suitable for many databases.         However some may require a different query; for example Oracle something like '`SELECT 1 FROM DUAL`' could be used.         The list of the validation queries can be configured with jdbc.config.check.query property and are by default:                            hsqldbselect 1 from INFORMATION_SCHEMA.SYSTEM_USERS               Oracleselect 1 from dual               DB2select 1 from sysibm.sysdummy1               MySQL or MariaDBselect 1               Microsoft SQL Server (MS JDBC driver)select 1               PostgreSQLselect 1               Ingresselect 1               Derbyvalues 1               H2select 1               Firebirdselect 1 from rdb$database               Exasolselect 1                      The list come from stackoverflow entry on different database validation queries and it can be incorrect         Note this validation query is used on pool creation to validate it even if "Test While Idle" suggests query would only be used on idle connections.         This is DBCP behaviour. |
| Database URL | Yes | JDBC Connection string for the database. |
| JDBC Driver class | Yes | Fully qualified name of driver class. (Must be in         JMeter's classpath - easiest to copy `.jar` file into JMeter's `/lib` directory).         The list of the preconfigured jdbc driver classes can be configured with jdbc.config.jdbc.driver.class property and are by default:                            hsqldborg.hsqldb.jdbc.JDBCDriver               Oracleoracle.jdbc.OracleDriver               DB2com.ibm.db2.jcc.DB2Driver               MySQL                                com.mysql.cj.jdbc.Driver                 com.mysql.jdbc.Driver (deprecated)                              Microsoft SQL Server (MS JDBC driver)                                com.microsoft.sqlserver.jdbc.SQLServerDriver or                 com.microsoft.jdbc.sqlserver.SQLServerDriver                              PostgreSQLorg.postgresql.Driver               Ingrescom.ingres.jdbc.IngresDriver               Derbyorg.apache.derby.jdbc.ClientDriver               H2org.h2.Driver               Firebirdorg.firebirdsql.jdbc.FBDriver               Apache Derbyorg.apache.derby.jdbc.ClientDriver               MariaDBorg.mariadb.jdbc.Driver               SQLiteorg.sqlite.JDBC               Sybase AESnet.sourceforge.jtds.jdbc.Driver               Exasolcom.exasol.jdbc.EXADriver |
| Username | No | Name of user to connect as. |
| Password | No | Password to connect with. (N.B. this is stored unencrypted in the test plan) |
| Connection Properties | No | Connection Properties to set when establishing connection (like `internal_logon=sysdba` for Oracle for example) |


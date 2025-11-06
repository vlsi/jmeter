---
title: MongoDB Script (DEPRECATED)
sidebar_position: 21
---

# MongoDB Script (DEPRECATED)

![MongoDB Script (DEPRECATED)](/img/images/screenshots/mongodb-script.png)

This sampler lets you send a Request to a MongoDB.

Before using this you need to set up a
[MongoDB Source Config](#mongodb-source-config) Configuration element

:::note
This Element currently uses `com.mongodb.DB#eval` which takes a global write lock causing a performance impact on the database, see [db.eval()](http://docs.mongodb.org/manual/reference/method/db.eval/).
So it is better to avoid using this element for load testing and use JSR223+Groovy scripting using MongoDBHolder instead.
MongoDB Script is more suitable for functional testing or test setup (setup/teardown threads)
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree. |
| MongoDB Source | Yes | Name of the JMeter variable that the MongoDB connection is bound to.         This must agree with the '`MongoDB Source`' field of a MongoDB Source Config. |
| Database Name | Yes | Database Name, will be used in your script |
| Username | No |  |
| Password | No |  |
| Script | Yes | Mongo script as it would be used in MongoDB shell |


### See also



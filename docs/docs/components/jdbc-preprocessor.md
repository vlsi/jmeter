---
title: JDBC PreProcessor
sidebar_position: 9
---

# JDBC PreProcessor

The JDBC PreProcessor enables you to run some SQL statement just before a sample runs.
This can be useful if your JDBC Sample requires some data to be in DataBase and you cannot compute this in a setup Thread group.
For details, see [JDBC Request](#jdbc-request).

See the following Test plan:


### See also

- [Test Plan using JDBC Pre/Post Processor](../demos/JDBC-Pre-Post-Processor.jmx)

In the linked test plan, "`Create Price Cut-Off`" JDBC PreProcessor calls a stored procedure to create a Price Cut-Off in Database,
this one will be used by "`Calculate Price cut off`".


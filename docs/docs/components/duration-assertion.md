---
title: Duration Assertion
sidebar_position: 2
---

# Duration Assertion

![Duration Assertion](/img/images/screenshots/duration_assertion.png)

The Duration Assertion tests that each response was received within a given amount
of time.  Any response that takes longer than the given number of milliseconds (specified by the
user) is marked as a failed response.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Duration in Milliseconds | Yes | The maximum number of milliseconds         each response is allowed before being marked as failed. |


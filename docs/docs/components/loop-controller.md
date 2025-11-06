---
title: Loop Controller
sidebar_position: 2
---

# Loop Controller

![Loop Controller](/img/images/screenshots/logic-controller/loop-controller.png)

If you add Generative or Logic Controllers to a Loop Controller, JMeter will
loop through them a certain number of times, in addition to the loop value you
specified for the Thread Group.  For example, if you add one HTTP Request to a
Loop Controller with a loop count of two, and configure the Thread Group loop
count to three, JMeter will send a total of `2 * 3 = 6` HTTP Requests.
JMeter will expose the looping index as a variable named __jm__<Name of your element>__idx. So for
example, if your Loop Controller is named LC, then you can access the looping index through ${__jm__LC__idx}.
Index starts at 0


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| Loop Count | Yes, unless "Forever" is checked | The number of times the subelements of this controller will be iterated each time                 through a test run.                 The value -1 is equivalent to checking the Forever toggle.                 Special Case: The Loop Controller embedded in the Thread Group                 element behaves slightly different.  Unless set to forever, it stops the test after                 the given number of iterations have been done.                 When using a function in this field, be aware it may be evaluated multiple times.                 Example using  will evaluate it to a different value for each child samplers of Loop Controller and result into unwanted behaviour. |


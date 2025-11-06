---
title: While Controller
sidebar_position: 10
---

# While Controller

![While Controller](/img/images/screenshots/whilecontroller.png)

The While Controller runs its children until the condition is "`false`".
JMeter will expose the looping index as a variable named __jm__<Name of your element>__idx. So for
example, if your While Controller is named WC, then you can access the looping index through ${__jm__WC__idx}.
Index starts at 0

Possible condition values:

- blank - exit loop when last sample in loop fails
- `LAST` - exit loop when last sample in loop fails.
If the last sample just before the loop failed, don't enter loop.
- Otherwise - exit (or don't enter) the loop when the condition is equal to the string "`false`"

:::note
The condition can be any variable or function that eventually evaluates to the string "`false`".
This allows the use of ``, `` function, properties or variables as needed.
:::

  
:::note
Note that the condition is evaluated twice, once before starting sampling children and once at end of children sampling, so putting
non idempotent functions in Condition (like ``) can introduce issues.
:::

  
For example:- `${VAR}` - where `VAR` is set to false by some other test element
- `${__jexl3(${C}==10)}`
- `${__jexl3("${VAR2}"=="abcd")}`
- `${_P(property)}` - where property is set to "`false`" somewhere else


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree, and used to name the transaction. |
| Condition | No | blank, `LAST`, or variable/function |


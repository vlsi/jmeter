---
title: ForEach Controller
sidebar_position: 12
---

# ForEach Controller

![ForEach Controller](/img/images/screenshots/logic-controller/foreach-controller.png)

A ForEach controller loops through the values of a set of related variables.
When you add samplers (or controllers) to a ForEach controller, every sample (or controller)
is executed one or more times, where during every loop the variable has a new value.
The input should consist of several variables, each extended with an underscore and a number.
Each such variable must have a value.
So for example when the input variable has the name `inputVar`, the following variables should have been defined:

- `inputVar_1 = wendy`
- `inputVar_2 = charles`
- `inputVar_3 = peter`
- `inputVar_4 = john`

Note: the "`_`" separator is now optional.

When the return variable is given as "`returnVar`", the collection of samplers and controllers under the ForEach controller will be executed `4` consecutive times,
with the return variable having the respective above values, which can then be used in the samplers.

JMeter will expose the looping index as a variable named __jm__<Name of your element>__idx. So for
example, if your Loop Controller is named FEC, then you can access the looping index through ${__jm__FEC__idx}.
Index starts at 0

It is especially suited for running with the regular expression post-processor.
This can "create" the necessary input variables out of the result data of a previous request.
By omitting the "`_`" separator, the ForEach Controller can be used to loop through the groups by using
the input variable `refName_g`, and can also loop through all the groups in all the matches
by using an input variable of the form `refName_${C}_g`, where `C` is a counter variable.

:::note
The ForEach Controller does not run any samples if `inputVar_1` is `null`.
This would be the case if the Regular Expression returned no matches.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| Input variable prefix | No | Prefix for the variable names to be used as input. Defaults to an empty string as prefix. |
| Start index for loop | No | Start index (exclusive) for loop over variables (first element is at start index + 1) |
| End index for loop | No | End index (inclusive) for loop over variables |
| Output variable | No | The name of the variable which can be used in the loop for replacement in the samplers. Defaults to an empty variable name, which is most probably not wanted. |
| Use Separator | Yes | If not checked, the "`_`" separator is omitted. |


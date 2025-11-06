---
title: BeanShell Assertion
sidebar_position: 5
---

# BeanShell Assertion

![BeanShell Assertion](/img/images/screenshots/beanshell_assertion.png)

The BeanShell Assertion allows the user to perform assertion checking using a BeanShell script.

**For full details on using BeanShell, please see the [BeanShell website.](http://www.beanshell.org/)**
Migration to +Groovy is highly recommended for performance, support of new Java features and limited maintenance of the BeanShell library.

Note that a different Interpreter is used for each independent occurrence of the assertion
in each thread in a test script, but the same Interpreter is used for subsequent invocations.
This means that variables persist across calls to the assertion.

All Assertions are called from the same thread as the sampler.

If the property "`beanshell.assertion.init`" is defined, it is passed to the Interpreter
as the name of a sourced file. This can be used to define common methods and variables.
There is a sample init file in the `bin` directory: `BeanShellAssertion.bshrc`

The test element supports the `ThreadListener` and `TestListener` methods.
These should be defined in the initialisation file.
See the file `BeanShellListeners.bshrc` for example definitions.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree.     The name is stored in the script variable `Label` |
| Reset bsh.Interpreter before each call | Yes | If this option is selected, then the interpreter will be recreated for each sample.     This may be necessary for some long running scripts.     For further information, see [Best Practices - BeanShell scripting](best-practices.html#bsh_scripting). |
| Parameters | No | Parameters to pass to the BeanShell script.     The parameters are stored in the following variables:              Parameters - string containing the parameters as a single variable         bsh.args - String array containing parameters, split on white-space |
| Script file | No | A file containing the BeanShell script to run. This overrides the script.     The file name is stored in the script variable `FileName` |
| Script | Yes (unless script file is provided) | The BeanShell script to run. The return value is ignored. |


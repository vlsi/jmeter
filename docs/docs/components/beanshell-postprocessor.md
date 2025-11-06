---
title: BeanShell PostProcessor
sidebar_position: 6
---

# BeanShell PostProcessor

![BeanShell PostProcessor](/img/images/screenshots/beanshell_postprocessor.png)

The BeanShell PreProcessor allows arbitrary code to be applied after taking a sample.

BeanShell Post-Processor no longer ignores samples with zero-length result data

**For full details on using BeanShell, please see the [BeanShell website.](http://www.beanshell.org/)**
Migration to +Groovy is highly recommended for performance, support of new Java features and limited maintenance of the BeanShell library.

The test element supports the `ThreadListener` and `TestListener` methods.
These should be defined in the initialisation file.
See the file `BeanShellListeners.bshrc` for example definitions.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree.     The name is stored in the script variable `Label` |
| Reset bsh.Interpreter before each call | Yes | If this option is selected, then the interpreter will be recreated for each sample.     This may be necessary for some long running scripts.     For further information, see [Best Practices - BeanShell scripting](best-practices.html#bsh_scripting). |
| Parameters | No | Parameters to pass to the BeanShell script.     The parameters are stored in the following variables:              Parameters - string containing the parameters as a single variable         bsh.args - String array containing parameters, split on white-space |
| Script file | No | A file containing the BeanShell script to run.     The file name is stored in the script variable `FileName` |
| Script | Yes (unless script file is provided) | The BeanShell script. The return value is ignored. |


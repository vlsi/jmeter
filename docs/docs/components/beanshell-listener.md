---
title: BeanShell Listener
sidebar_position: 15
---

# BeanShell Listener

![BeanShell Listener](/img/images/screenshots/beanshell_listener.png)

The BeanShell Listener allows the use of BeanShell for processing samples for saving etc.

**For full details on using BeanShell, please see the [BeanShell website.](http://www.beanshell.org/)**
Migration to +Groovy is highly recommended for performance, support of new Java features and limited maintenance of the BeanShell library.

The test element supports the `ThreadListener` and `TestListener` methods.
These should be defined in the initialisation file.
See the file `BeanShellListeners.bshrc` for example definitions.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree.     The name is stored in the script variable Label |
| Reset bsh.Interpreter before each call | Yes | If this option is selected, then the interpreter will be recreated for each sample.     This may be necessary for some long running scripts.     For further information, see [Best Practices - BeanShell scripting](best-practices.html#bsh_scripting). |
| Parameters | No | Parameters to pass to the BeanShell script.     The parameters are stored in the following variables:              Parametersstring containing the parameters as a single variable         bsh.argsString array containing parameters, split on white-space |
| Script file | No | A file containing the BeanShell script to run.     The file name is stored in the script variable `FileName` |
| Script | Yes (unless script file is provided) | The BeanShell script to run. The return value is ignored. |


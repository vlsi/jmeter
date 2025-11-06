---
title: BeanShell Sampler
sidebar_position: 10
---

# BeanShell Sampler

![BeanShell Sampler](/img/images/screenshots/beanshellsampler.png)

This sampler allows you to write a sampler using the BeanShell scripting language.

**For full details on using BeanShell, please see the [BeanShell website.](http://www.beanshell.org/)**
Migration to +Groovy is highly recommended for performance, support of new Java features and limited maintenance of the BeanShell library.

The test element supports the `ThreadListener` and `TestListener` interface methods.
These must be defined in the initialisation file.
See the file `BeanShellListeners.bshrc` for example definitions.

The BeanShell sampler also supports the `Interruptible` interface.
The `interrupt()` method can be defined in the script or the init file.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree.     The name is stored in the script variable Label |
| Reset bsh.Interpreter before each call | Yes | If this option is selected, then the interpreter will be recreated for each sample.     This may be necessary for some long running scripts.     For further information, see [Best Practices - BeanShell scripting](best-practices.html#bsh_scripting). |
| Parameters | No | Parameters to pass to the BeanShell script.     This is intended for use with script files; for scripts defined in the GUI, you can use whatever     variable and function references you need within the script itself.     The parameters are stored in the following variables:              Parametersstring containing the parameters as a single variable         bsh.argsString array containing parameters, split on white-space |
| Script file | No | A file containing the BeanShell script to run.     The file name is stored in the script variable `FileName` |
| Script | Yes (unless script file is provided) | The BeanShell script to run.     The return value (if not `null`) is stored as the sampler result. |


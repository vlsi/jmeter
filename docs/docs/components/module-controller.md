---
title: Module Controller
sidebar_position: 13
---

# Module Controller

![Module Controller](/img/images/screenshots/module_controller.png)

The Module Controller provides a mechanism for substituting test plan fragments into the current test plan at run-time.

A test plan fragment consists of a Controller and all the test elements (samplers etc.) contained in it.
The fragment can be located in any Thread Group.
If the fragment is located in a Thread Group, then its Controller can be disabled to prevent the fragment being run
except by the Module Controller.
Or you can store the fragments in a dummy Thread Group, and disable the entire Thread Group.

There can be multiple fragments, each with a different series of
samplers under them.  The module controller can then be used to easily switch between these multiple test cases simply by choosing
the appropriate controller in its drop down box.  This provides convenience for running many alternate test plans quickly and easily.

A fragment name is made up of the Controller name and all its parent names.
For example:

Test Plan / Protocol: JDBC / Control / Interleave Controller (Module1)Any **fragments used by the Module Controller must have a unique name**,
as the name is used to find the target controller when a test plan is reloaded.
For this reason it is best to ensure that the Controller name is changed from the default
- as shown in the example above -
otherwise a duplicate may be accidentally created when new elements are added to the test plan.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| Module to Run | Yes | The module controller provides a list of all controllers loaded into the gui.  Select         the one you want to substitute in at runtime. |


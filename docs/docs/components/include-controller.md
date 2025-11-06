---
title: Include Controller
sidebar_position: 14
---

# Include Controller

![Include Controller](/img/images/screenshots/includecontroller.png)

The include controller is designed to use an external JMX file. To use it, create a Test Fragment
underneath the Test Plan and add any desired samplers, controllers etc. below it.
Then save the Test Plan.  The file is now ready to be included as part of other Test Plans.

For convenience, a [Thread Group](#thread-group) can also be added in the external JMX file for debugging purposes.
A [Module Controller](#module-controller) can be used to reference the Test Fragment.  The [Thread Group](#thread-group) will be ignored during the
include process.

If the test uses a Cookie Manager or User Defined Variables, these should be placed in the top-level
test plan, not the included file, otherwise they are not guaranteed to work.

:::note
This element does not support variables/functions in the filename field.  

However, if the property `includecontroller.prefix` is defined,
the contents are used to prefix the pathname.
:::

:::note
When using Include Controller and including the same JMX file, ensure you name the Include Controller differently to avoid facing known issue [Bug 50898](https://bz.apache.org/bugzilla/show_bug.cgi?id=50898).
:::

If the file cannot be found at the location given by `prefix`+`Filename`, then the controller
attempts to open the `Filename` relative to the JMX launch directory.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Filename | Yes | The file to include. |


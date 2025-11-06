---
title: Transaction Controller
sidebar_position: 15
---

# Transaction Controller

![Transaction Controller](/img/images/screenshots/transactioncontroller.png)

The Transaction Controller generates an additional
        sample which measures the overall time taken to perform the nested test elements.

:::note
Note: when the check box "`Include duration of timer and pre-post processors in generated sample`" is checked,
        the time includes all processing within the controller scope, not just the samples.
:::

There are two modes of operation:

- additional sample is added after the nested samples
- additional sample is added as a parent of the nested samples

The generated sample time includes all the times for the nested samplers excluding by default (since 2.11) timers and processing time of pre/post processors
        unless checkbox "`Include duration of timer and pre-post processors in generated sample`" is checked.
        Depending on the clock resolution, it may be slightly longer than the sum of the individual samplers plus timers.
        The clock might tick after the controller recorded the start time but before the first sample starts.
        Similarly at the end.

The generated sample is only regarded as successful if all its sub-samples are successful.

In parent mode, the individual samples can still be seen in the Tree View Listener,
        but no longer appear as separate entries in other Listeners.
        Also, the sub-samples do not appear in CSV log files, but they can be saved to XML files.

:::note
In parent mode, Assertions (etc.) can be added to the Transaction Controller.
        However by default they will be applied to both the individual samples and the overall transaction sample.
        To limit the scope of the Assertions, use a Simple Controller to contain the samples, and add the Assertions
        to the Simple Controller.
        Parent mode controllers do not currently properly support nested transaction controllers of either type.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | Yes | Descriptive name for this controller that is shown in the tree, and used to name the transaction. |
| Generate Parent Sample | Yes | If checked, then the sample is generated as a parent of the other samples,     otherwise the sample is generated as an independent sample. |
| Include duration of timer and pre-post processors in generated sample | Yes | Whether to include timer, pre- and post-processing delays in the generated sample.     Default is `false` |


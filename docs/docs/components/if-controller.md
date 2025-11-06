---
title: If Controller
sidebar_position: 9
---

# If Controller

![If Controller](/img/images/screenshots/if_controller_expression.png)

The If Controller allows the user to control whether the test elements below it (its children) are run or not.

By default, the condition is evaluated only once on initial entry, but you have the option to have it evaluated for every runnable element contained in the controller.

The best option (default one) is to check `Interpret Condition as Variable Expression?`, then in the condition field you have 2 options:
        
            Option 1: Use a variable that contains true or false
            If you want to test if last sample was successful, you can use ${JMeterThread.last_sample_ok}
            If Controller using Variable
            
            
            Option 2: Use a function (${__jexl3()} is advised) to evaluate an expression that must return true or false
            If Controller using expression
            
        
        For example, previously one could use the condition:
        `${__jexl3(${VAR} == 23)}` and this would be evaluated as `true`/`false`, the result would then be passed to JavaScript
        which would then return `true`/`false`. If the Variable Expression option is selected, then the expression is evaluated
        and compared with "`true`", without needing to use JavaScript.

:::note
To test if a variable is undefined (or null) do the following, suppose var is named `myVar`, expression will be:
        "${myVar}" == "\${myVar}"
        Or use:
        "${myVar}" != "\${myVar}"
        to test if a variable is defined and is not null.
:::

If you uncheck`Interpret Condition as Variable Expression?`,`If Controller`will internally use javascript to evaluate the condition
        which has a performance penalty that can be very big and make your test less scalable.
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |
| Condition (default JavaScript) | Yes | By default the condition is interpreted as **JavaScript** code that returns "`true`" or "`false`",     but this can be overridden (see below) |
| Interpret Condition as Variable Expression? | Yes | If this is selected, then the condition must be an expression that evaluates to "`true`" (case is ignored).     For example, `${FOUND}` or `${__jexl3(${VAR} > 100)}`.     Unlike the JavaScript case, the condition is only checked to see if it matches "`true`" (case is ignored).     Checking this and using  or  function in Condition is advised for performances |
| Evaluate for all children | Yes | Should condition be evaluated for all children?     If not checked, then the condition is only evaluated on entry. |


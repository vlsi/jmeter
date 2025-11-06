---
title: Random Variable
sidebar_position: 17
---

# Random Variable

![Random Variable](/img/images/screenshots/random_variable.png)

The Random Variable Config Element is used to generate random numeric strings and store them in variable for use later.
It's simpler than using [User Defined Variables](#user-defined-variables) together with the `` function.

The output variable is constructed by using the random number generator,
and then the resulting number is formatted using the format string.
The number is calculated using the formula `minimum+Random.nextInt(maximum-minimum+1)`.
`Random.nextInt()` requires a positive integer.
This means that `maximum-minimum` - i.e. the range - must be less than `2147483647`,
however the `minimum` and `maximum` values can be any `long` values so long as the range is OK.

:::note
As the random value is evaluated at the start of each iteration, it is probably not a good idea
to use a variable other than a property as a value for the minimum or maximum. It would be zero on the first iteration.
:::


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | Yes | Descriptive name for this element that is shown in the tree. |
| Variable Name | Yes | The name of the variable in which to store the random string. |
| Format String | No | The `java.text.DecimalFormat` format string to be used.   For example "`000`" which will generate numbers with at least 3 digits,   or "`USER_000`" which will generate output of the form `USER_nnn`.   If not specified, the default is to generate the number using `Long.toString()` |
| Minimum Value | Yes | The minimum value (`long`) of the generated random number. |
| Maximum Value | Yes | The maximum value (`long`) of the generated random number. |
| Random Seed | No | The seed for the random number generator.   If you use the same seed value with Per Thread set to `true`, you will get the same value for each Thread as per   [Random](http://docs.oracle.com/javase/8/docs/api/java/util/Random.html) class.   If no seed is set, Default constructor of Random will be used. |
| Per Thread(User)? | Yes | If `False`, the generator is shared between all threads in the thread group.   If `True`, then each thread has its own random generator. |


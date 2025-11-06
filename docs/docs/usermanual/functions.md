---
title: "User's Manual: Functions and Variables"
---

# 20. Functions and Variables

JMeter functions are special values that can populate fields of any Sampler or other
element in a test tree.  A function call looks like this:

`${__functionName(var1,var2,var3)}`

Where "__functionName" matches the name of a function.
  

Parentheses surround the parameters sent to the function, for example `${__time(YMD)}`
The actual parameters vary from function to function.
Functions that require no parameters can leave off the parentheses, for example `${__threadNum}`.

If a function parameter contains a comma, then be sure to escape this with "`\`", otherwise JMeter will treat it as a parameter delimiter.
For example:

${__time(EEE\, d MMM yyyy)}

If the comma is not escaped - e.g. `${__javaScript(Math.max(2,5))}` - you will get an error such as:

ERROR - jmeter.functions.JavaScript: Error processing Javascript: [Math.max(2]
    org.mozilla.javascript.EvaluatorException: missing ) after argument list (<cmd>#1)
 
 This is because the string "`Math.max(2,5)`" is treated as being two parameters to the __javascript function:  

 `Math.max(2` and `5)`  

 Other error messages are possible.

Variables are referenced as follows:

${VARIABLE}

**If an undefined function or variable is referenced, JMeter does not report/log an error - the reference is returned unchanged.
For example if `UNDEF` is not defined as a variable, then the value of `${UNDEF}` is `${UNDEF}`.**
Variables, functions (and properties) are all case-sensitive.
**JMeter trims spaces from variable names before use, so for example
`${__Random(1,63, LOTTERY )}` will use the variable '`LOTTERY`' rather than '` LOTTERY `'.**

:::note
Properties are not the same as variables.
Variables are local to a thread; properties are common to all threads,
and need to be referenced using the `__P` or `__property` function.
:::

:::note
When using `\` before a variable for a windows path for example `C:\test\${test}`, ensure you escape the `\`
otherwise JMeter will not interpret the variable, example:
`C:\\test\\${test}`.
  

Alternatively, just use `/` instead for the path separator - e.g. `C:/test/${test}` - Windows JVMs will convert the separators as necessary.
:::

List of functions, loosely grouped into types.

| Type of function | Name | Comment | Since |
| --- | --- | --- | --- |
| Information | [threadNum](#__threadNum) | get thread number | 1.X |
| Information | [threadGroupName](#__threadGroupName) | get thread group name | 4.1 |
| Information | [samplerName](#__samplerName) | get the sampler name (label) | 2.5 |
| Information | [machineIP](#__machineIP) | get the local machine IP address | 2.6 |
| Information | [machineName](#__machineName) | get the local machine name | 1.X |
| Information | [time](#__time) | return current time in various formats | 2.2 |
| Information | [timeShift](#__timeShift) | return a date in various formats with the specified amount of seconds/minutes/hours/days added | 3.3 |
| Information | [log](#__log) | log (or display) a message (and return the value) | 2.2 |
| Information | [logn](#__logn) | log (or display) a message (empty return value) | 2.2 |
| Input | [StringFromFile](#__StringFromFile) | read a line from a file | 1.9 |
| Input | [FileToString](#__FileToString) | read an entire file | 2.4 |
| Input | [CSVRead](#__CSVRead) | read from CSV delimited file | 1.9 |
| Input | [XPath](#__XPath) | Use an XPath expression to read from a file | 2.0.3 |
| Input | [StringToFile](#__StringToFile) | write a string to a file | 5.2 |
| Calculation | [counter](#__counter) | generate an incrementing number | 1.X |
| Formatting | [dateTimeConvert](#__dateTimeConvert) | Convert a date or time from source to target format | 4.0 |
| Calculation | [digest](#__digest) | Generate a digest (SHA-1, SHA-256, MD5...) | 4.0 |
| Calculation | [intSum](#__intSum) | add int numbers | 1.8.1 |
| Calculation | [longSum](#__longSum) | add long numbers | 2.3.2 |
| Calculation | [Random](#__Random) | generate a random number | 1.9 |
| Calculation | [RandomDate](#__RandomDate) | generate random date within a specific date range | 3.3 |
| Calculation | [RandomFromMultipleVars](#__RandomFromMultipleVars) | extracts an element from the values of a set of variables separated by `|` | 3.1 |
| Calculation | [RandomString](#__RandomString) | generate a random string | 2.6 |
| Calculation | [UUID](#__UUID) | generate a random type 4 UUID | 2.9 |
| Scripting | [groovy](#__groovy) | run an Apache Groovy script | 3.1 |
| Scripting | [BeanShell](#__BeanShell) | run a BeanShell script | 1.X |
| Scripting | [javaScript](#__javaScript) | process JavaScript (Nashorn) | 1.9 |
| Scripting | [jexl2](#__jexl2) | evaluate a Commons Jexl2 expression | jexl2(2.1.1) |
| Scripting | [jexl3](#__jexl3) | evaluate a Commons Jexl3 expression | jexl3 (3.0) |
| Properties | [isPropDefined](#__isPropDefined) | Test if a property exists | 4.0 |
| Properties | [property](#__property) | read a property | 2.0 |
| Properties | [P](#__P) | read a property (shorthand method) | 2.0 |
| Properties | [setProperty](#__setProperty) | set a JMeter property | 2.1 |
| Variables | [split](#__split) | Split a string into variables | 2.0.2 |
| Variables | [eval](#__eval) | evaluate a variable expression | 2.3.1 |
| Variables | [evalVar](#__evalVar) | evaluate an expression stored in a variable | 2.3.1 |
| Properties | [isVarDefined](#__isVarDefined) | Test if a variable exists | 4.0 |
| Variables | [V](#__V) | evaluate a variable name | 2.3RC3 |
| String | [char](#__char) | generate Unicode char values from a list of numbers | 2.3.3 |
| String | [changeCase](#__changeCase) | Change case following different modes | 4.0 |
| String | [escapeHtml](#__escapeHtml) | Encode strings using HTML encoding | 2.3.3 |
| String | [escapeOroRegexpChars](#__escapeOroRegexpChars) | quote meta chars used by ORO regular expression | 2.9 |
| String | [escapeXml](#__escapeXml) | Encode strings using XMl encoding | 3.2 |
| String | [regexFunction](#__regexFunction) | parse previous response using a regular expression | 1.X |
| String | [unescape](#__unescape) | Process strings containing Java escapes (e.g. \n & \t) | 2.3.3 |
| String | [unescapeHtml](#__unescapeHtml) | Decode HTML-encoded strings | 2.3.3 |
| String | [urldecode](#__urldecode) | Decode a application/x-www-form-urlencoded string | 2.10 |
| String | [urlencode](#__urlencode) | Encode a string to a application/x-www-form-urlencoded string | 2.10 |
| String | [TestPlanName](#__TestPlanName) | Return name of current test plan | 2.6 |



There are two kinds of functions: user-defined static values (or variables), and built-in functions.  

User-defined static values allow the user to define variables to be replaced with their static value when
a test tree is compiled and submitted to be run.  This replacement happens once at the beginning of the test
run.  This could be used to replace the DOMAIN field of all HTTP requests, for example - making it a simple
matter to change a test to target a different server with the same test.

Note that variables cannot currently be nested; i.e. `${Var${N}}` does not work.
The `__V` (variable) function can be used to do this: `${__V(Var${N})}`.
You can also use `${__BeanShell(vars.get("Var${N}")}`.

This type of replacement is possible without functions, but was less convenient and less intuitive.
It required users to create default config elements that would fill in blank values of Samplers.
Variables allow one to replace only part of any given value, not just filling in blank values.

With built-in functions users can compute new values at run-time based on previous response data, which
thread the function is in, the time, and many other sources.  These values are generated fresh for every
request throughout the course of the test.

:::note
Functions are shared between threads.
Each occurrence of a function call in a test plan is handled by a separate function instance.
:::

Functions and variables can be written into any field of any test component (apart from the TestPlan - see below).
Some fields do not allow random strings
because they are expecting numbers, and thus will not accept a function.  However, most fields will allow
functions.

Functions which are used on the Test Plan have some restrictions.
JMeter thread variables will have not been fully set up when the functions are processed,
so variable names passed as parameters will not be set up, and variable references will not work,
so `split()` and `regex()` and the variable evaluation functions won't work.
The `threadNum()` function won't work (and does not make sense at test plan level).
The following functions should work OK on the test plan:

intSum
longSum
machineName
BeanShell
groovy
javaScript
jexl2/jexl3
random
time
property functions
log functions

Configuration elements are processed by a separate thread.
Therefore functions such as `__threadNum` do not work properly in elements such as User Defined Variables.
Also note that variables defined in a UDV element are not available until the element has been processed.

:::note
When using variable/function references in SQL code (etc.),
remember to include any necessary quotes for text strings,
i.e. use

SELECT item from table where name='${VAR}'

**not**

SELECT item from table where name=${VAR}

(unless `VAR` itself contains the quotes)
:::

Referencing a variable in a test element is done by bracketing the variable name with '`${`' and '`}`'.

Functions are referenced in the same manner, but by convention, the names of
functions begin with "`__`" to avoid conflict with user value names<sup>*</sup>.  Some functions take arguments to
configure them, and these go in parentheses, comma-delimited.  If the function takes no arguments, the parentheses can
be omitted.

**Argument values that themselves contain commas should be escaped as necessary.
If you need to include a comma in your parameter value, escape it like so: '`\,`'.**
This applies for example to the scripting functions - Javascript, Beanshell, Jexl, groovy - where it is necessary to escape any commas
that may be needed in script method calls - e.g.

${__BeanShell(vars.put("name"\,"value"))}Alternatively, you can define your script as a variable, e.g. on the Test Plan:
SCRIPT          vars.put("name","value")
The script can then be referenced as follows:
${__BeanShell(${SCRIPT})}
There is no need to escape commas in the `SCRIPT` variable because the function call is parsed before the variable is replaced with its value.
This works well in conjunction with the JSR223 or BeanShell Samplers, as these can be used to test Javascript, Jexl and BeanShell scripts.

Functions can reference variables and other functions, for example
`${__XPath(${__P(xpath.file),${XPATH})}`
will use the property "`xpath.file`" as the file name
and the contents of the variable `XPATH` as the expression to search for.

JMeter provides a tool to help you construct
function calls for various built-in functions, which you can then copy-paste.
It will not automatically escape values for you, since functions can be parameters to other functions, and you should only escape values you intend as literal.

:::note
If a string contains a backslash('`\`') and also contains a function or variable reference, the backslash will be removed if
it appears before '`$`' or '`,`' or '`\`'.
This behaviour is necessary to allow for nested functions that include commas or the string `${`.
Backslashes before '`$`' or '`,`' or '`\`' are not removed if the string does not contain a function or variable reference.
:::

**The value of a variable or function can be reported** using the [__logn()](#__logn) function.
The `__logn()` function reference can be used anywhere in the test plan after the variable has been defined.
Alternatively, the Java Request sampler can be used to create a sample containing variable references;
the output will be shown in the appropriate Listener.
Note there is a [Debug Sampler](#debug-sampler)
that can be used to display the values of variables etc. in the Tree View Listener.

:::note
<sup>*</sup>If you define a user-defined static variable with the same name as a built-in function, your static
variable will override the built-in function.
:::

The Function Helper dialog is available from JMeter's Tools menu.

Using the Function Helper, you can select a function from the pull down, and assign
values for its arguments.  The left column in the table provides a brief description of the
argument, and the right column is where you write in the value for that argument.  Different
functions take different arguments.

Once you have done this, click the "generate" button, and the appropriate string is generated
for you to copy-paste into your test plan wherever you like.

Most variables are set by calling functions or by test elements such as User Defined Variables;
in which case the user has full control over the variable name that is used.
However some variables are defined internally by JMeter. These are listed below.

- `COOKIE_cookiename` - contains the cookie value (see [HTTP Cookie Manager](#http-cookie-manager))
- `JMeterThread.last_sample_ok` - whether or not the last sample was OK - `true`/`false`.
Note: this is updated after PostProcessors and Assertions have been run.
- `START` variables (see next section)

The set of JMeter properties is initialised from the system properties defined when JMeter starts;
additional JMeter properties are defined in `jmeter.properties`, `user.properties` or on the command line.

Some built-in properties are defined by JMeter. These are listed below.
For convenience, the `START` properties are also copied to variables with the same names.

- `START.MS` - JMeter start time in milliseconds
- `START.YMD` - JMeter start time as `yyyyMMdd`
- `START.HMS` - JMeter start time as `HHmmss`
- `TESTSTART.MS` - test start time in milliseconds

Please note that the `START` variables / properties represent JMeter startup time, not the test start time.
They are mainly intended for use in file names etc.


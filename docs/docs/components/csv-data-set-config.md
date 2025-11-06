---
title: CSV Data Set Config
sidebar_position: 1
---

# CSV Data Set Config

![CSV Data Set Config](/img/images/screenshots/csvdatasetconfig.png)

CSV Data Set Config is used to read lines from a file, and split them into variables.
    It is easier to use than the `` and `` functions.
    It is well suited to handling large numbers of variables, and is also useful for testing with
    "random" and unique values.

Generating unique random values at run-time is expensive in terms of CPU and memory, so just create the data
    in advance of the test. If necessary, the "random" data from the file can be used in conjunction with
    a run-time parameter to create different sets of values from each run - e.g. using concatenation - which is
    much cheaper than generating everything at run-time.

JMeter allows values to be quoted; this allows the value to contain a delimiter.
    If "`allow quoted data`" is enabled, a value may be enclosed in double-quotes.
    These are removed. To include double-quotes within a quoted field, use two double-quotes.
    For example:

```

1,"2,3","4""5" =>
1
2,3
4"5

```

JMeter supports CSV files which have a header line defining the column names.
    To enable this, leave the "`Variable Names`" field empty. The correct delimiter must be provided.

JMeter supports CSV files with quoted data that includes new-lines.

By default, the file is only opened once, and each thread will use a different line from the file.
    However the order in which lines are passed to threads depends on the order in which they execute,
    which may vary between iterations.
    Lines are read at the start of each test iteration.
    The file name and mode are resolved in the first iteration.

See the description of the Share mode below for additional options.
    If you want each thread to have its own set of values, then you will need to create a set of files,
    one for each thread. For example `test1.csv`, `test2.csv`, …, `testn.csv`. Use the filename
    `test${__threadNum}.csv` and set the "`Sharing mode`" to "`Current thread`".

:::note
CSV Dataset variables are defined at the start of each test iteration.
    As this is after configuration processing is completed,
    they cannot be used for some configuration items - such as JDBC Config -
    that process their contents at configuration time (see [Bug 40394](https://bz.apache.org/bugzilla/show_bug.cgi?id=40394))
    However the variables do work in the HTTP Auth Manager, as the `username` etc. are processed at run-time.
:::

As a special case, the string "`\t`" (without quotes) in the delimiter field is treated as a Tab.

When the end of file (`EOF`) is reached, and the recycle option is `true`, reading starts again with the first line of the file.

If the recycle option is `false`, and stopThread is `false`, then all the variables are set to `<EOF>` when the end of file is reached.
    This value can be changed by setting the JMeter property `csvdataset.eofstring`.

If the Recycle option is `false`, and Stop Thread is `true`, then reaching `EOF` will cause the thread to be stopped.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name |  | Descriptive name for this element that is shown in the tree. |
| Filename | Yes | Name of the file to be read.   **Relative file names are resolved with respect to the path of the active test plan.**   **For distributed testing, the CSV file must be stored on the server host system in the correct relative directory to where the JMeter server is started.**   Absolute file names are also supported, but note that they are unlikely to work in remote mode,   unless the remote server has the same directory structure.   If the same physical file is referenced in two different ways - e.g. `csvdata.txt` and `./csvdata.txt` -   then these are treated as different files.   If the OS does not distinguish between upper and lower case, `csvData.TXT` would also be opened separately. |
| File Encoding | No | The encoding to be used to read the file, if not the platform default. |
| Variable Names | No | List of variable names. The names must be separated by the delimiter character. They can be quoted using double-quotes.   JMeter supports CSV header lines:   if the variable name field empty, then the first line of the file is read and interpreted as the list of column names. |
| Use first line as Variable Names | No | Ignore first line of CSV file, it will only be used if Variable Names is not empty,   if Variable Names is empty the first line must contain the headers. |
| Delimiter | Yes | Delimiter to be used to split the records in the file.   If there are fewer values on the line than there are variables the remaining variables are not updated -   so they will retain their previous value (if any). |
| Allow quoted data? | Yes | Should the CSV file allow values to be quoted?   If enabled, then values can be enclosed in `"` - double-quote - allowing values to contain a delimiter. |
| Recycle on EOF? | Yes | Should the file be re-read from the beginning on reaching `EOF`? (default is `true`) |
| Stop thread on EOF? | Yes | Should the thread be stopped on `EOF`, if Recycle is false? (default is `false`) |
| Sharing mode | Yes | All threads - (the default) the file is shared between all the threads.   Current thread group - each file is opened once for each thread group in which the element appears   Current thread - each file is opened separately for each thread   Identifier - all threads sharing the same identifier share the same file.   So for example if you have 4 thread groups, you could use a common id for two or more of the groups   to share the file between them.   Or you could use the thread number to share the file between the same thread numbers in different thread groups. |


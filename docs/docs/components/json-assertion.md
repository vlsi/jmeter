---
title: JSON Assertion
sidebar_position: 14
---

# JSON Assertion

![JSON Assertion](/img/images/screenshots/assertion/json_assertion.png)

This component allows you to perform validations of JSON documents.
            First, it will parse the JSON and fail if the data is not JSON.
            Second, it will search for specified path, using syntax from [Jayway JsonPath 1.2.0](https://github.com/json-path/JsonPath). If the path is not found, it will fail.
            Third, if JSON path was found in the document, and validation against expected value was requested, it will perform validation. For the `null` value there is special checkbox in the GUI.
            Note that if the path will return array object, it will be iterated and if expected value is found, the assertion will succeed. To validate empty array use `[]` string. Also, if patch will return dictionary object, it will be converted to string before comparison.
           When using indefinite JSON Paths
             you must assert the value due to the existing JSON library implementation, otherwise the assertion could always
             return successful.
             Since JMeter version 5.5 the assertion will fail, if an indefinite path is given, an empty list is extracted and
             no assertion value is set.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Assert JSON Path exists | Yes | Path to JSON element for assert. |
| Additionally assert value | No | Select checkbox if you want make assert with some value |
| Match as regular expression | No | Select checkbox if you want use regular expression |
| Expected Value | No | Value for assert or regular expression for match |
| Expect null | No | Select checkbox if you expect null |
| Invert assertion (will fail if above conditions met) | No | Invert assertion (will fail if above conditions met) |


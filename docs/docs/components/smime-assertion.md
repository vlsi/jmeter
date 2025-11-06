---
title: SMIME Assertion
sidebar_position: 13
---

# SMIME Assertion

![SMIME Assertion](/img/images/screenshots/assertion/smime.png)

`JMETER_HOME/lib`:  
- `bcmail-xxx.jar` (BouncyCastle SMIME/CMS)
- `bcprov-xxx.jar` (BouncyCastle Provider)

These need to be[downloaded from BouncyCastle.](http://www.bouncycastle.org/latest_releases.html)If using the [Mail Reader Sampler](#mail-reader-sampler),
please ensure that you select "`Store the message using MIME (raw)`" otherwise the Assertion won't be able to process the message correctly.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| Verify Signature | Yes | If selected, the assertion will verify if it is a valid signature according to the parameters defined in the `Signer Certificate` box. |
| Message not signed | Yes | Whether or not to expect a signature in the message |
| Signer Certificate | Yes | "`No Check`" means that it will not perform signature verification. "`Check values`" is used to verify the signature against the inputs provided. And "`Certificate file`" will perform the verification against a specific certificate file. |
| Message Position | Yes | The Mail sampler can retrieve multiple messages in a single sample.     Use this field to specify which message will be checked.     Messages are numbered from `0`, so `0` means the first message.     Negative numbers count from the LAST message; `-1` means LAST, `-2` means penultimate etc. |


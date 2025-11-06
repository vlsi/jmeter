---
title: FTP Request
sidebar_position: 1
---

# FTP Request

![FTP Request](/img/images/screenshots/ftptest/ftp-request.png)

[FTP Request Defaults](#ftp-request-defaults)Configuration
Element so you do not have to enter the same information for each FTP Request Generative
Controller. When downloading a file, it can be stored on disk (Local File) or in the Response Data, or both.Latency is set to the time it takes to login.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree. |
| Server Name or IP | Yes | Domain name or IP address of the FTP server. |
| Port | No | Port to use. If this is `>0`, then this specific port is used,            otherwise JMeter uses the default FTP port. |
| Remote File: | Yes | File to retrieve or name of destination file to upload. |
| Local File: | Yes, if uploading (*) | File to upload, or destination for downloads (defaults to remote file name). |
| Local File Contents: | Yes, if uploading (*) | Provides the contents for the upload, overrides the Local File property. |
| get(RETR) / put(STOR) | Yes | Whether to retrieve or upload a file. |
| Use Binary mode? | Yes | Check this to use Binary mode (default ASCII) |
| Save File in Response? | Yes, if downloading | Whether to store contents of retrieved file in response data.         If the mode is ASCII, then the contents will be visible in the [View Results Tree](#view-results-tree). |
| Username | Usually | FTP account username. |
| Password | Usually | FTP account password. N.B. This will be visible in the test plan. |


### See also

- [Assertions](test_plan.html#assertions)
- [Building an FTP Test Plan](build-ftp-test-plan.html)


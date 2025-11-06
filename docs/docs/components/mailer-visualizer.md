---
title: Mailer Visualizer
sidebar_position: 14
---

# Mailer Visualizer

![Mailer Visualizer](/img/images/screenshots/mailervisualizer.png)

The mailer visualizer can be set up to send email if a test run receives too many
failed responses from the server.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this element that is shown in the tree. |
| From | Yes | Email address to send messages from. |
| Addressee(s) | Yes | Email address to send messages to, comma-separated. |
| Success Subject | No | Email subject line for success messages. |
| Success Limit | Yes | Once this number of successful responses is exceeded         **after previously reaching the failure limit**, a success email         is sent.  The mailer will thus only send out messages in a sequence of failed-succeeded-failed-succeeded, etc. |
| Failure Subject | No | Email subject line for fail messages. |
| Failure Limit | Yes | Once this number of failed responses is exceeded, a failure         email is sent - i.e. set the count to `0` to send an e-mail on the first failure. |
| Host | No | IP address or host name of SMTP server (email redirector)         server. |
| Port | No | Port of SMTP server (defaults to `25`). |
| Login | No | Login used to authenticate. |
| Password | No | Password used to authenticate. |
| Connection security | No | Type of encryption for SMTP authentication (SSL, TLS or none). |
| Test Mail | No | Press this button to send a test mail |
| Failures | No | A field that keeps a running total of number         of failures so far received. |


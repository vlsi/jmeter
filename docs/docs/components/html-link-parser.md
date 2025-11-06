---
title: HTML Link Parser
sidebar_position: 1
---

# HTML Link Parser

![HTML Link Parser](/img/images/screenshots/html_link_parser.png)

This modifier parses HTML response from the server and extracts
links and forms.  A URL test sample that passes through this modifier will be examined to
see if it "matches" any of the links or forms extracted
from the immediately previous response.  It would then replace the values in the URL
test sample with appropriate values from the matching link or form.  Perl-type regular
expressions are used to find matches.


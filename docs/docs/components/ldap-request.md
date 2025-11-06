---
title: LDAP Request
sidebar_position: 7
---

# LDAP Request

![LDAP Request](/img/images/screenshots/ldap_request.png)

`Add`,`Modify`,`Delete`and`Search`) to an LDAP server.If you are going to send multiple requests to the same LDAP server, consider
      using an [LDAP Request Defaults](#ldap-request-defaults)
      Configuration Element so you do not have to enter the same information for each
      LDAP Request.

The same way the[Login Config Element](#login-config-element)also using for Login and password.
### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this sampler that is shown in the tree. |
| Server Name or IP | Yes | Domain name or IP address of the LDAP server.       JMeter assumes the LDAP server is listening on the default port (`389`). |
| Port | Yes | Port to connect to (default is `389`). |
| root DN | Yes | Base DN to use for LDAP operations |
| Username | Usually | LDAP server username. |
| Password | Usually | LDAP server password. (N.B. this is stored unencrypted in the test plan) |
| Entry DN | Yes, if User Defined Test and Add Test or Modify Test is selected | the name of the context to create or Modify; may not be empty.      You have to set the right attributes of the object yourself. So if you want to add cn=apache,ou=test       you have to add in the table name and value to cn and apache. |
| Delete | Yes, if User Defined Test and Delete Test is selected | the name of the context to Delete; may not be empty |
| Search base | Yes, if User Defined Test and Search Test is selected | the name of the context or object to search |
| Search filter | Yes, if User Defined Test and Search Test is selected | the filter expression to use for the search; may not be null |
| add test | Yes, if User Defined Test and add Test is selected | Use these `name`, `value` pairs for creation of the new object in the given context |
| modify test | Yes, if User Defined Test and Modify Test is selected | Use these `name`, `value` pairs for modification of the given context object |


### See also

- [Building an LDAP Test Plan](build-ldap-test-plan.html)


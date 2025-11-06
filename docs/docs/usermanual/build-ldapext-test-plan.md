---
title: "User's Manual: Building an Extended LDAP Test Plan"
---

# 8b. Building an Extended LDAP Test Plan

In this section, you will learn how to create a basic Test Plan to test an LDAP
server.

As the Extended LDAP Sampler is highly configurable, this also means that it takes
some time to build a correct testplan. You can however tune it exactly up to your
needs.

You will create 1 user that send requests for nine tests on the LDAP server. Also, you will tell
the users to run their tests one time. So, the total number of requests is `(1 user) x (9 requests) x
(repeat 1 time) = 9` LDAP requests. To construct the Test Plan, you will use the following elements:  

[Thread Group](test_plan.html#thread_group),  

[Adding LDAP Extended Request Defaults](#adding-ldap-extended-request-defaults),  

[Adding LDAP Requests](#adding-ldap-requests), and  

[Adding a Listener to View/Store the Test Results](#adding-a-listener-to-viewstore-the-test-results)

This example assumes that the LDAP Server is available at `ldap.test.com`.

For the less experienced LDAP users, I build a [small
LDAP tutorial](ldapops_tutor.html) which shortly explains
the several LDAP operations that can be used in building a complex testplan.

Take care when using LDAP special characters in the distinguished name, in that case (e.g. you want to use a `+` sign in a
distinguished name) you need to escape the character by adding an "`\`" sign before that character.
Extra exception: if you want to add a `\` character in a distinguished name (in an add or rename operation), you need to use 4 backslashes.

Examples:

`cn=dolf\+smits`to add/search an entry with the name like`cn=dolf+smits``cn=dolf \\ smits`to search an entry with the name`cn=dolf \ smits``cn=c:\\\\log.txt`to add an entry with a name like`cn=c:\log.txt`The first step you want to do with every JMeter Test Plan is to add a Thread Group element.
The Thread Group tells JMeter the number of users you want to simulate, how often the users should send
requests, and the how many requests  they should send.

Go ahead and add the `Thread Group` element by first selecting the `Test Plan`, clicking your
right mouse button to get the `Add` menu, and then select `Add`→`Threads (Users)`→`Thread Group`.
You should now see the `Thread Group` element under `Test Plan`. If you do not see the element, then "expand" the Test Plan tree by
clicking on the Test Plan element.

Figure 8b.1. Thread Group with Default Values

Begin by selecting the LDAP Ext Users element. Click your right mouse button
to get the `Add` menu, and then select `Add`→`Config Element`→`LDAP Extended Request Defaults`. Then,
select this new element to view its Control Panel.

Like most JMeter elements, the `LDAP Extended Request Defaults` Control Panel has a name
field that you can modify. In this example, leave this field with the default value.

Figure 8b.2 LDAP Defaults for our Test Plan

For each of the different operations, some default values can be filled in.
            In All cases, when a default is filled in, this is used for the LDAP extended requests.
            For each request, you can override the defaults by filling in the values in the LDAP extended request sampler.
            When no value is entered which is necessary for a test, the test will fail in an unpredictable way!

We will not enter any default values here, as we will build a very small testplan, so we will explain all the different fields when we add the LDAP Extended samplers.In our Test Plan, we want to use all 9 LDAP requests.

1. Thread bind
2. Search Test
3. Compare Test
4. Single bind/unbind Test
5. Add Test
6. Modify Test
7. Rename entry (moddn)
8. Delete Test
9. Thread unbind

JMeter sends requests in the order that you add them to the tree.

Adding a requests always start by:  

Adding the `LDAP Extended Request` to the LDAP Ext Users element (`Add`→
`Sampler`→`LDAP Ext Request`). Then, select the `LDAP Ext Request` element in the tree
and edit the following properties.

Rename the element: "1. Thread bind"


Select the "Thread bind" button.


Enter the hostname value from the LDAP server in the Servername field


Enter the portnumber from the LDAP server (636 : ldap over SSL) in the port field


(Optional) Enter the baseDN in the DN field, this baseDN will be used as the starting point for searches, add, deletes, etc.
take care that this must be the uppermost shared level for all your request, e.g. when all information is stored under ou=Users, dc=test, dc=com, you can use this value in the basedn.


(Optional) Enter the distinguished name from the user you want to use for authentication.
When this field is kept empty, an anonymous bind will be established.


(Optional) Enter the password for the user you want to authenticate with, an empty password will also lead to an anonymous bind.


(Optional) Enter a value for the connection timeout with LDAP


(Optional) Check the box Use Secure LDAP Protocol if you access with LDAP over SSL (ldaps)


(Optional) Check the box TrustAll if you want the client to trust all certificates

Figure 8b.3.1. Thread Bind example

Rename the element: "2. Search Test"


Select the "Search Test" button.


(Optional) enter the searchbase under which you want to perform the search, relative to the basedn, used in the thread bind request.
When left empty, the basedn is used as a search base, this files is important if you want to use a "base-entry" or "one-level" search (see below)


Enter the searchfilter, any decent LDAP search filter will do, but for now, use something simple, like (sn=Doe) or (cn=*)


(Optional) Enter the scope in the scope field, it has three options:
     
     baseobject searchonly the given searchbase is used, only for checking attributes or existence.
     
     onelevel searchOnly search in one level below given searchbase is used
     
     subtree search Searches for object at any point below the given basedn
     


(Optional) Size limit, specifies the maximum number of returned entries,


(Optional) Time limit, specifies the maximum number of milliseconds, the SERVER can use for performing the search. It is NOT the maximum time the application will wait.
When a very large returnset is returned, from a very fast server, over a very slow line, you may have to wait for ages for the completion of the search request, but this parameter will not influence this.

(Optional) Attributes you want in the search answer. This can be used to limit the size of the answer, especially when an object has very large attributes (like jpegPhoto). There are three possibilities:
Leave empty (the default setting must also be empty) This will return all attributes.

Put in one empty value (""), it will request a non-existent attributes, so in reality it returns no attributes

Put in the attributes, separated by a semi-colon. It will return only the requested attributes


(Optional) Return object. Checked will return all java-object attributes, it will add these to the requested attributes, as specified above.
Unchecked will mean no java-object attributes will be returned.


(Optional) Dereference aliases. Checked will mean it will follow references, Unchecked says it will not.


(Optional) Parse the search results?. Checked will mean it gets all results in response data, Unchecked says it will not.

Figure 8b.3.2. search request example

Rename the element: "3. Compare Test"


Select the "Compare" button.


enter the entryname form the object on which you want the compare operation to work, relative to the basedn, e.g. "cn=jdoe,ou=Users"


Enter the compare filter, this must be in the form "attribute=value", e.g. "mail=jdoe@test.com"

Figure 8b.3.3. Compare example

Rename the element: "4. Single bind/unbind Test"


Select the "Single bind/unbind" button.


Enter the FULL distinguished name from the user you want to use for authentication.
E.g. cn=jdoe,ou=Users,dc=test,dc=com
When this field is kept empty, an anonymous bind will be established.


Enter the password for the user you want to authenticate with, an empty password will also lead to an anonymous bind.


**Take care**: This single bind/unbind is in reality two separate operations but cannot easily be split!

Figure 8b.3.4. Single bind/unbind example

Rename the element: "5. Add Test"


Select the "Add" button.


Enter the distinguished name for the object to add, relative to the basedn.


Add a line in the "add test" table, fill in the attribute and value.
When you need the same attribute more than once, just add a new line, add the attribute again, and a different value.
All necessary attributes and values must be specified to pass the test, see picture!
(sometimes the server adds the attribute "objectClass=top", this might give a problem.

Figure 8b.3.5. Add request example

Rename the element: "6. Modify Test"


Select the "Modify test" button.


Enter the distinguished name for the object to modify, relative to the basedn.


Add a line in the "modify test" table, with the "add" button.


You need to enter the attribute you want to modify, (optional) a value, and the opcode. The meaning of this opcode:

addthis will mean that the attribute value (not optional in this case) will be added to the attribute.
When the attribute is not existing, it will be created and the value added
When it is existing, and defined multi-valued, the new value is added.
when it is existing, but single valued, it will fail.
replace
This will overwrite the attribute with the given new value (not optional here)
When the attribute is not existing, it will be created and the value added
When it is existing, old values are removed, the new value is added.
delete
When no value is given, all values will be removed
When a value is given, only that value will be removed
 when the given value is not existing, the test will fail




(Optional) Add more modifications in the "modify test" table.
All modifications which are specified must succeed, to let the modification test pass. When one modification fails,
NO modifications at all will be made and the entry will remain unchanged.

Figure 8b.3.6. Modify example

Rename the element: "7. Rename entry (moddn)"


Select the "Rename Entry" button.


Enter the name of the entry, relative to the baseDN, in the "old entry name"-Field.
that is, if you want to rename "cn=Little John Doe,ou=Users", and you set the baseDN to "dc=test,dc=com",
you need to enter "cn=John Junior Doe,ou=Users" in the old entry name-Field.


Enter the new name of the entry, relative to the baseDN, in the "new distinguished name"-Field.
when you only change the RDN, it will simply rename the entry
when you also add a different subtree, e.g. you change from cn=john doe,ou=Users to cn=john doe,ou=oldusers, it will move the entry.
You can also move a complete subtree (If your LDAP server supports this!), e.g. ou=Users,ou=retired, to ou=oldusers,ou=users,
this will move the complete subtree, plus all retired people in the subtree to the new place in the tree.

Figure 8b.3.7. Rename example

Rename the element: "8. Delete Test"


Select the "Delete" button.


Enter the name of the entry, relative to the baseDN, in the Delete-Field.
that is, if you want to remove "cn=John Junior Doe,ou=Users,dc=test,dc=com", and you set the baseDN to "dc=test,dc=com",
you need to enter "cn=John Junior Doe,ou=Users" in the Delete-field.

Figure 8b.3.8. Delete example

Rename the element: "9. Thread unbind"


Select the "Thread unbind" button.
This will be enough as it just closes the current connection.
The information which is needed is already known by the system

Figure 8b.3.9. Unbind example

The final element you need to add to your Test Plan is a Listener.
 This element is responsible for storing all of the results of your LDAP
requests in a file  and presenting a visual model of the data. Select the Thread group
element and add a `View Results Tree` (`Add`→`Listener`→`View Results Tree`)

Figure 8b.4. View Result Tree Listener

In this listener you have three tabs to view, the sampler result, the request and the response data.


The sampler result just contains the response time, the returncode and return message


The request gives a short description of the request that was made, in practice no relevant information
is contained here.


The response data contains the full details of the sent request, as well the full details of the received answer,
this is given in a (self defined) xml-style.
The full description can be found here.


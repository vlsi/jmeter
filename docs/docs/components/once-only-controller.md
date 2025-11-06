---
title: Once Only Controller
sidebar_position: 3
---

# Once Only Controller

![Once Only Controller](/img/images/screenshots/logic-controller/once-only-controller.png)

The Once Only Logic Controller tells JMeter to process the controller(s) inside it only once per Thread, and pass over any requests under it
during further iterations through the test plan.

The Once Only Controller will now execute always during the first iteration of any looping parent controller.
Thus, if the Once Only Controller is placed under a Loop Controller specified to loop 5 times, then the Once Only Controller will execute only on the first iteration through the Loop Controller
(i.e. every 5 times).

Note this means the Once Only Controller will still behave as previously expected if put under a Thread Group (runs only once per test per Thread),
but now the user has more flexibility in the use of the Once Only Controller.

For testing that requires a login, consider placing the login request in this controller since each thread only needs
to login once to establish a session.


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Name | No | Descriptive name for this controller that is shown in the tree. |


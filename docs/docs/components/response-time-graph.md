---
title: Response Time Graph
sidebar_position: 13
---

# Response Time Graph

![Response Time Graph](/img/images/screenshots/response_time_graph.png)


### Properties

| Property | Required | Description |
| --- | --- | --- |
| Interval (ms) | Yes | The time in milliseconds for X axis interval. Samples are grouped according to this value.         Before display the graph, click on `Apply interval` button to refresh internal data. |
| Sampler label selection | No | Filter by result label. A regular expression can be used, ex. `.*Transaction.*`.         Before display the graph, click on `Apply filter` button to refresh internal data. |
| Title | No | Define the graph's title on the head of chart. Empty value is the default value: "`Response Time Graph`".         The button `Synchronize with name` define the title with the label of the listener. And define font settings for graph title |
| Line settings | Yes | Define the width of the line. Define the type of each value point. Choose `none` to have a line without mark |
| Graph size | No | Compute the graph size by  the width and height depending of the current JMeter's window size.         Use `Width` and `Height` fields to define a custom size. The unit is pixel. |
| X Axis settings | No | Customize the date format of  X axis label.         The syntax is the Java [SimpleDateFormat API](http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html). |
| Y Axis settings | No | Define a custom maximum value for Y Axis in milli-seconds. Define the increment for the scale (in ms) Show or not the number grouping in Y Axis labels. |
| Legend | Yes | Define the placement and font settings for chart legend |


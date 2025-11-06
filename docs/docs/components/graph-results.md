---
title: Graph Results
sidebar_position: 3
---

# Graph Results

![Graph Results](/img/images/screenshots/graph_results.png)

:::note
Graph Results MUST NOT BE USED during load test as it consumes a lot of resources (memory and CPU). Use it only for either functional testing or
during Test Plan debugging and Validation.
:::

The Graph Results listener generates a simple graph that plots all sample times.  Along
the bottom of the graph, the current sample (black), the current average of all samples (blue), the
current standard deviation (red), and the current throughput rate (green) are displayed in milliseconds.

The throughput number represents the actual number of requests/minute the server handled.  This calculation
includes any delays you added to your test and JMeter's own internal processing time.  The advantage
of doing the calculation like this is that this number represents something
real - your server in fact handled that many requests per minute, and you can increase the number of threads
and/or decrease the delays to discover your server's maximum throughput.  Whereas if you made calculations
that factored out delays and JMeter's processing, it would be unclear what you could conclude from that
number.


---
title: Apache JMeter Distributed Testing Step-by-step
---

# 25. Apache JMeter Distributed Testing Step-by-step

This short tutorial explains how to use multiple systems to perform stress testing. Before we start, there are a couple of things to check.

- the firewalls on the systems are turned off or correct ports are opened.
- all the clients are on the same subnet.
- the server is in the same subnet, if `192.x.x.x` or `10.x.x.x` IP addresses are used.
      If the server doesn't use `192.xx` or `10.xx` IP address, there shouldn't be any problems.
- Make sure JMeter can access the server.
- Make sure you use the same version of JMeter and Java on all the systems. Mixing versions will not work correctly.
- You have [setup SSL for RMI](remote-test.html#setup_ssl) or disabled it.

Once you've made sure the systems are ready, it's time to setup remote testing. The tutorial assumes you already
have JMeter installed on all the systems. The way JMeter works is one controller node initiates the test on multiple worker nodes.

:::note
In this tutorial we use GUI Mode just for demonstration. In real life you should use CLI mode (NON GUI) to start your load test
:::

Before we dive into the step-by-step instructions, it's a good idea to define the terms and make sure the definition is clear.

Controller Nodethe system running JMeter GUI, which controls the testWorker Nodethe system running`jmeter-server`, which takes commands from
      the GUI and send requests to the target system(s)Targetthe webserver we plan to stress test1. On the worker nodes, go to `jmeter/bin` directory and execute
     `jmeter-server.bat` (`jmeter-server` on unix).
2. On controller node acting as the console, open windows explorer and go to
      `jmeter/bin` directory
3. Open `jmeter.properties` in a text editor
4. Edit the line `remote_hosts=127.0.0.1`
5. Add the IP address. For example, if I have JMeter server running on `192.168.0.10`,
    …, `192.168.0.15`, the entry would look like this:
    remote_hosts=192.168.0.10,192.168.0.11,192.168.0.12,192.168.0.13,192.168.0.14
6. Start JMeter.
7. Open the test plan you want to use

At this point, you are ready to start load testing. If you want to double check
the worker nodes are working, open `jmeter.log` in your editor. You should see the following in the log.

```

Writing log file to: /XXXX/XXXXX/bin/jmeter-server.log
Created remote object: UnicastServerRef [liveRef: [endpoint:[192.X.X.X:XXXXX](local),objID:[-6a665beb:15a2c8b9419:-7fff, 3180474504933847586]]]

```

If you do not see this message, it means `jmeter-server` did not start correctly. For tips on
debugging the issue, [go to the tips section](#tips). There are two ways to
initiate the test: a single system and all systems.

1. Click Run at the top
2. Select Remote Start
3. Select the IP address

1. Click Run at the top
2. Select Remote Start all or use Ctrl+Shift+R

There are some basic limitations for distributed testing. Here's the list of the known items in no specific order.

1. RMI cannot communicate across subnets without a proxy; therefore neither can JMeter without a proxy.
2. Since version 2.9, JMeter sends all the test results stripping Response data to the controlling console, this allows
      us to reduce impact on network IO. Ensure you monitor your network traffic so that this traffic does not incur contention
3. A single JMeter client running on a 2-3 GHz CPU (recent CPU) can handle 1000-2000 threads depending on the type of test.

[Wiki page on remote testing](https://cwiki.apache.org/confluence/display/JMETER/JMeterFAQ#JMeterFAQ-Howtodoremotetestingthe'properway'?)

[Remote Testing in the user manual](remote-test.html)

In some cases, the firewall may still be blocking RMI traffic.

### Anti Virus and Firewall

:::note
Antivirus should be stopped during a Load Test as it can drastically impact timings leading to wrong results.
:::

Firewall needs to be stopped from windows services or at least some ports need to be opened.

1. Open control panel
2. Open administrative tools
3. Double click services
4. Go to down to Symantec anti virus, right click and select stop

### Windows firewall

1. Open network connections
2. Select the network connection
3. Right click and select properties
4. Select advanced tab
5. Uncheck internet connection firewall

### Linux

On Linux, iptables might be turned on by default. For instructions, please refer to the
  [Remote Testing in the user manual](remote-test.html)

On RedHat (or derivatives), iptables is turned on by default. Execute
service iptables stop
to stop the Linux firewall or ensure you open the correct ports.


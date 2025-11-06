---
title: Extending JMeter
---

# Extending JMeter

**Note to developers: JMeter is undergoing large changes.  The following
description of JMeter's architecture will likely change in the near future.  If you
would like your changes to work with an upcoming JMeter 1.6, please join our mailing
list, and we will work with you and your modifications.****Customizing JMeter to suit your needs.**

### Extensible Interfaces

There are five basic objects in JMeter which provide extensibility:


Visualizers represent the sampling data which is recorded.
Timers specify the delay between samples.
SamplerControllers hold information about all the test cases to be sampled,
and overall information about how the test is conducted.
Samplers are the classes that actually do the sampling of a particular protocol.
TestSamples hold information about a particular test case to be sampled.

[]()
Visualizers
The `Visualizer` interface exists in the `org.apache.jmeter.visualizers` package.
JMeter maintains an instance of each visualizer it is aware of for each thread group currently available to the user.
A visualizer provides a method of recording the data which JMeter generates.
A visualizer may represent the data graphically (GraphVisualizer),
persistently (FileVisualizer) or both (TBD).
The visualizer contains three methods:

add(SampleResult result) adds data to the visualization.

JMeter calls the add method to include new data in the visualizer.
The visualizer should add the data into its data representation.

clear() clears all data in the visualizer currently

JMeter calls clear when the user requests that all visualizers be cleared. When the clear method is called the visualizer should clear all data from its representation and re-initialize itself.

getControlPanel() obtains the GUI for the visualizer

JMeter calls getControlPanel at start up time to prepare the
visualizer for display.


[]()

Timers
Timers provide a framework for delaying in between samples.  This is important in order to obtain a true balanced load on a function rather than a calm-STORM-calm-STORM-calm-… pattern.  Timers contain two methods:

delay() wait for a Timer specific amount of time
JMeter calls this function prior to every sample.  The Timer should wait for a period of time and then return.
set() prepare for sampling
JMeter calls this function prior to the beginning of a test session.  The timer should initialize itself, read any values from its UI and prepare for operation.

[]()
SamplerControllers
The sampler controller is by far the most complicated, but also the most powerful, interface in JMeter.  It allows a user to customize what, where and when JMeter tests.  It provides six methods:

start()

JMeter calls this immediately prior to starting a test.  It is most often use to disable the SamplerController's GUI.

stop()

JMeter calls this when a user requests a stop to a test.  It is most often used to re-enable the SamplerController's GUI.

getControlPanel() Get the GUI for the SamplerController

JMeter calls this at start up to create the its GUI.  This is how a user enters
information into the SamplerController.

getName() Get the SamplerController's display name.

JMeter uses this name in the list of SamplerControllers that it displays.

getDefaultThreadGroups()
Gets the default list of threadgroups.
getSampleThreads(String threadGroup,int numThreads)
When the user hits start, use this method to get all the JMeterThread objects you want for a threadgroup.  Each JMeterThread
object implements Runnable and it is used to sample the test entries.

[]()
Samplers
  Samplers are simple - they are the objects that know the protocol of that which
  you wish to sample.  The HTTPSampler knows how to request a URL from a web server, for
  instance.

  The interface for Sampler is as follows:
  
  public SampleResult sample(Entry e)
  JMeterThread implementations will loop through all the test samples given
  to them, and call the sample method on the Sampler (also given to them) for each
  test entry.  SampleResult is essentially a Map containing information about
  the sampling (timing data is included, as well as the test response from the url).

[]()
TestSample
  TestSamples are objects that collect information from users about each test sample
  the user wants to test.  The TestSample object is also responsible for serving
  up its test entries.  The interface:
  
  public java.awt.Container getGUI()
  Returns the GUI used to collect information from the user.
  public Entry[] getEntries()
  Gets a list of entries to be sampled from the TestSample object
  public String[] getThreadGroups()
  Get all the thread groups the user selected for this TestSample
  public void setThreadGroups(String[] threadGroups)
  Set the thread groups the user may choose from
  public String getName()
  Get a name for this TestSample
  public void setName(String name)
  Set the name for this TestSample
  public void reset()
  inform the test sample that a sampling run is starting


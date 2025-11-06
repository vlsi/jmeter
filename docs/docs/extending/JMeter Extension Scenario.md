---
title: Extending JMeter
---

# JMeter Extension Scenario

The purpose of this tutorial is to
describe the general steps involved in a JMeter extension scenario. The
[JMeter documentation](index.html) describes what must be done on a microscopic level but does
not provide an overall idea of the process. That is the intent of this brief
article. The JMeter extension documentation should be consulted for details.

The high level procedure followed these steps.PlanningCode the configuration
  objectCode the configuration
  GUI objectCode the controller
  objectCode the controller GUI
  objectCode the Sampler objectInstall your
  extensionTipsPlanningI've found planning a JMeter extension to
involve three aspects:What you want the sampler to doWhat information is needed for the sampler to workHow the information is to be acquired from the userYou'll notice that the coding steps are somewhat backwards from the planning
steps (the sampler is coded last). The coding order was determined by which
classes could be tested earliest. The config/gui can be tested in isolation. The
controller can be tested with the config element. Neither of these requires a
Sampler to be present initially.Configuration ObjectThe role of the configuration
object is to supply parameters to the Sampler that can vary from sample to
sample. In the case of theUrlConfigobject, this would be information
such as the host name, port, GET or PUT and various parameters.The configuration object usually inherits fromorg.apache.jmeter.config.AbstractConfigElement. It implements many of
the methods oforg.apache.jmeter.gui.JMeterComponentModelthat are
needed to effectively interact with JMeter.Constructor- In the constructor you should at least define the
  name of your configuration element. This is best delegated to the base class'ssetNamemethod.Property Name Strings- You should define a static final string for
  each property you wish to define. These strings will serve as keys into a hash
  table maintained byAbstractConfigElement. For example:public static final HOST_NAME = "hostname";would define a property
  in the hash table for storing a host name.Getters/Setters- For each property name you define in the previous
  step, define the appropriate accessor methods. The implementation of these
  accessors should usually delegate toAbstractConfigElement. For
  example:public void setHostname(String hostname)
    { setProperty(HOST_NAME, hostname); }

    public String getHostname()
    { return (String)getProperty(HOST_NAME); }Some accessor implementations may be more complex. See theUrlConfigobject for a more involved example.String getClassLabel()- This is the label that will
  display in the drop-down menu for adding your configuration element.clone()- Your configuration element is expected to be
  cloneable.addConfigElement(ConfigElement)- A typical implementation
  of this method looks likepublic void addConfigElement(ConfigElement config) {
        if (config instanceof MyConfig)
            updatePropertyIfAbsent((MyConfig)config);
    }whereupdatePropertyIfAbsentis handled by the super class.getGuiClass- return the name of the this class'scorresponding GUI class.Configuration GUIEach configuration element you
define can have a companion GUI class. It helps to have a little knowledge of
Swing for this. Extend Swing'sJPanelclass and implement JMeter'sorg.apache.jmeter.gui.ModelSupportedinterface. Remember that you can
review theUrlConfigGuiexample for hints if you get stuck.Data Members- You should possess at least two data members: a
  reference to your partner configuration element and a reference to aorg.apache.jmeter.gui.NamePanel. You will likely have several others
  depending on how sophisticated your GUI is.Add Panels- The layout manager used for many of the panels used in
  JMeter isorg.apache.jmeter.gui.VerticalLayout. As the name implies,
  it supports arranging other panels in a vertical fashion. You can define each
  of your panels in agetmethod and add them to the configuration GUI
  in a method calledinit. Once again, refer toUrlConfigGuifor an example.Implement Listeners- Implement listeners for your GUI components.
  TheUrlConfigGuiserves as a satisfactory example.setModel- Use this method to have the model data member
  set on your GUI instance. Runinitfrom inside this method also.updateGui- Use this method to set the GUI fields from the
  model.Generative ControllerA generative controller is a
controller that generates anEntryobject for use by a Sampler.createEntry- This method is the raison d'etre of theorg.apache.jmeter.control.SamplerControllerinterface. The general
  idea is to construct anEntryobject and populate it with config
  objects.clone- After you perform you cloning duties, be sure to
  pass the cloned instance to thestandardCloneProcmethod so that base
  class cloning activities can complete.getClassLabel- This is the label displayed by the
  drop-down menu for the controller.getGuiClass- This should return a Class object for theassociated GUI class.Generative Controller GUIA generative
controller GUI class should extendJPaneland implementModelSupported. If your controller GUI doesn't involve anything beyond
the configuration GUI, you might be able to get away with inheriting from the
configuration gui class you created a couple steps ago. If you do this, you need
to at least override thesetModelmethod to make sure that the correct
model is set on the class. You'll be passed a controller object but you'll want
to extract the config element from the controller to be used as the model for
your base class (the config gui).SamplerThe sampler is responsible for actually
performing the work using the information provided in the configuration element.
The method of importance ispublic SampleResult sample(Entry e)It is here that you extract
configuration elements from the entry object you are passed. Then use these
configuration elements to perform the task you extension is suppose to do.InstallationFollow these steps to install your
extension.Package the class files into a JAR file.Place the JAR file into theextsubdirectory of the JMeter root
  install directory.Edit thebin/jmeter.propertiesfile of the JMeter installation.
  Find thesearch_pathsentry and add your JAR to the list. It should
  look likesearch_paths=ApacheJMeter.jar;classes;../ext/YourJar.jarRun JMeter and watch the magic.TipsYou might consider using slf4j as your logging utility since that's what
  JMeter uses. It's helpful for figuring out what's going on.If you do decide to use slf4j and you set the priority (or level, as it
  will soon be called) to debug, you will probably see way more than you need to
  know. You can filter the JMeter stuff by adding  tolog4j2.xmlin the JMeter'sbindirectory.```

    
  
```

Note that the root (default) debugging has been set toinfo.
  This eliminates most slf4j output from JMeter. The new line specifies the name
  of the package containing JMeter extensions. (com.yourfirm.jmeter) in
  this example. Note that it is not necessary to specify a particular class
  name. Also, note thatnoappenders are specified - just the trailing
  comma. If you specify Root_Appender here you'll see your message appear twice
  (because you specified the same appender twice). All you really want to do is
  override the priority.Implementclonecarefully. This is an often overlooked method for
  a lot of folks. JMeter makes heavy use of cloning. Check out some of the
  JMeter configuration elements and controllers to see how they do it. Notice
  that in most cases, a special method is usually invoked to perform base class
  cloning activities. For configuration elements, this isconfigureClone. For controllers, it isstandardCloneProc.
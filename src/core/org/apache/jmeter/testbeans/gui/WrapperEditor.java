/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by the
 * Apache Software Foundation (http://www.apache.org/)."
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 * "Apache JMeter" must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 * "Apache JMeter", nor may "Apache" appear in their name, without
 * prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * 
 * @author <a href="mailto:jsalvata@apache.org">Jordi Salvat i Alabart</a>
 * @version $Id$
 */
package org.apache.jmeter.testbeans.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JComboBox;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * This class implements a property editor that provides a suitable GUI
 * component (custom editor) for simple property editors that don't have one
 * of their own. The resulting editor will be able to handle values of the
 * type of the property and Strings containing JMeter variables.
 * <p>
 * The provided GUI is a combo box with:
 * <ul>
 * <li>An option for "undefined" (corresponding to the null value or NullProperty).
 * <li>An option for each value returned by the getTags() method on the wrapped
 * 	editor or from the "tags" attribute of the property descriptor.
 * <li>The possibility to write your own value, which will be parsed by the
 *		wrapped editor to convert into the edited type unless (and this is an
 *		heuristic) it contains the string "${", in which case it will be
 *		assumed to be an 'expression' containing JMeter variables and will
 *		not be parsed at all, but just handled as a string.
 * </ul>
 */
class WrapperEditor extends PropertyEditorSupport implements ItemListener
{
    private static Logger log= LoggingManager.getLoggerForClass();

    private static Object UNDEFINED= new Object()
    {
        public String toString()
        {
            return "Undefined"; // TODO: should be a resource.
        }
    };
    // The above is a funny hack: if you use a plain String, 
    // entering the text of the string in the editor will make the
    // combo revert to that option -- which actually amounts to
    // making that string 'reserved'. I preferred to avoid this by
    // using a different type, but an object that has the same
    // .toString().
    // TODO: use a renderer that paints
    // the field distinct from when the same string is typed in.

	private static Object EDIT= new Object()
	{
		public String toString()
		{
			return "Edit"; // TODO: should be a resource.
		}
	};
	
	/**
	 * Base PropertyEditor for the property at hand. Most methods in this class
	 * are delegated from this one.
	 */
    private PropertyEditor editor;

    /**
     * Property descriptor for the property to be edited by this editor.
     */
    private PropertyDescriptor descriptor;

	/**
	 * The type of the objects that will be assigned to or obtained from this
	 * property. That is: the type of the property or, if this is a primitive 
	 * type (e.g. int), the corresponding wrapping type (e.g. Integer).
	 */
	private Class type;
	
    /**
     * The editor's combo box. 
     */
    private JComboBox combo= null;

	/**
	 * Create an editor wrapping one which does not provide a custom editor.
	 * 
	 * @param editor A property editor which doesn't have a custom editor.
	 * @param descriptor the descriptor for the property being edited.
	 */
    WrapperEditor(PropertyEditor editor, PropertyDescriptor descriptor)
    {
        this.editor= editor;
        this.descriptor= descriptor;
        
        type= descriptor.getPropertyType();
        if (type.isPrimitive())
        {
        	// Sorry for this -- I have not found a better way:
        	if (type == boolean.class) type= Boolean.class;
        	else if (type == char.class) type= Character.class;
        	else if (type == byte.class) type= Byte.class;
			else if (type == short.class) type= Short.class;
        	else if (type == int.class) type= Integer.class;
        	else if (type == long.class) type= Long.class;
        	else if (type == float.class) type= Float.class;
			else if (type == double.class) type= Double.class;
			else if (type == void.class) type= Void.class;
			else
			{
				log.error("Class "+type+" is an unknown primitive type.");
            	throw new Error("Class "+type+" is an unknown primitive type");
            		// programming error: bail out.
            }
        }

		// Build the list of available values for this property:
		Vector options= new Vector();

		// The first available value is "undefined" (null).
		options.add(UNDEFINED);

		// Add the list of property-specific values:
		String[] tags= getTags();
		if (tags != null)
		{
			options.addAll(Arrays.asList(tags));
		}
            
		// The last option is to edit a value manually:
		options.add(EDIT);
		
		// Create the combo box we will use to edit this property:
		combo= new JComboBox(options);
		combo.addItemListener(this);
		combo.setEditable(false);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getCustomEditor()
     */
    public Component getCustomEditor()
    {
        return combo;
    }

    /**
     * Get the list of tags returned by the wrapped editor, plus those in the
     * "tags" attribute of the property descriptor.
     * 
     * @see java.beans.PropertyEditor#getTags()
     */
    public String[] getTags()
    {
        String[] tags1= editor.getTags();
		String[] tags2= (String[])descriptor.getValue("tags");
		
		if (tags1 == null) return tags2;
		else if (tags2 == null) return tags1;
		else {
			LinkedList l= new LinkedList();
			l.addAll(Arrays.asList(tags1));
			l.addAll(Arrays.asList(tags2));
			return (String[])l.toArray(new String[0]);
		}
    }

    /**
     * @see java.beans.PropertyEditor#getValue()
     * @see org.apache.jmeter.testelement.property.JMeterProperty
     */
    public Object getValue()
    {
        Object value= combo.getSelectedItem();
        
		if (value == UNDEFINED)
		{
			value= null;
		}
		else {
			String text= (String) value;
			if (combo.getSelectedIndex() > 0
				|| text.indexOf("${") == -1)
			{
				// if it's not a JMeter 'expression'...
				try
				{
					editor.setAsText(text);
					value= editor.getValue();
				}
				catch (IllegalArgumentException e)
				{
					// TODO: how to warn the user?
					// Maybe we should do this check earlier in the edit
					// process, maybe upon ItemChangeEvents?
					value= null;
				}
			}
		}

        if (log.isDebugEnabled())
        {
            log.debug(
                descriptor.getName()
                    + "->"
                    + (value != null ? value.getClass().getName() : "NULL")
                    + ":"
                    + value);
        }
        return value;
    }

	/* (non-Javadoc)
	 * @see java.beans.PropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value)
	{
		if (log.isDebugEnabled())
		{
			log.debug(
				descriptor.getName()
					+ "<-"
					+ (value != null ? value.getClass().getName() : "NULL")
					+ ":"
					+ value);
		}

		if (value == null)
		{
			value= UNDEFINED;
		}
		else if (type.isInstance(value))
		{
			editor.setValue(value);
			value= editor.getAsText();
		}
		else
		{
			// Not a type specific to the property, so it is a String...
			// ... but, just in case, I'll check:
			if (! (value instanceof String))
			{
				log.error("When editing property "+descriptor.getName()
					+", of type "+type
					+" got value of type "+value.getClass());
				throw new Error("String expected, got "+value.getClass());
					// programming error, so bail out.
			} 
		}
		combo.setSelectedItem(value);
		firePropertyChange();
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyEditor#getAsText()
	 */
	public String getAsText()
	{
		String text;
		
		Object value= combo.getSelectedItem();
		if (value == UNDEFINED)
		{
			text= null;
		}
		else {
			text= (String) value;
			if (combo.getSelectedIndex() > 0
				|| text.indexOf("${") == -1)
			{
				// if it's not a JMeter 'expression'...
				editor.setAsText(text);
				text= editor.getAsText();
			}
		}
		if (log.isDebugEnabled())
		{
			log.debug(descriptor.getName() + "->\"" + text + "\"");
		}
		return text;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyEditor#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException
	{
		if (log.isDebugEnabled())
		{
			log.debug(
				descriptor.getName()
					+ (text == null ? "<-null" : "<-\"" + text + "\""));
		}
		if (text == null)
		{
			combo.setSelectedItem(UNDEFINED);
		}
		else 
		{
			combo.setSelectedItem(text);
		}
		firePropertyChange();
	}

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#isPaintable()
     */
    public boolean isPaintable()
    {
        return editor.isPaintable();
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#paintValue(java.awt.Graphics, java.awt.Rectangle)
     */
    public void paintValue(Graphics gfx, Rectangle box)
    {
        editor.paintValue(gfx, box);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#supportsCustomEditor()
     */
    public boolean supportsCustomEditor()
    {
        return true;
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			if (e.getItem() == EDIT) {
				combo.setEditable(true);
				combo.setSelectedItem("");
				combo.getEditor().getEditorComponent().requestFocus();
			} 
			else if (combo.getSelectedIndex() >= 0) combo.setEditable(false); 
		}
	}

	public static class Test extends junit.framework.TestCase
	{
		public Test(String name)
		{
			super(name);
		}
		
		private abstract class ABean {
			public abstract void setB(boolean b);
			public abstract boolean getB();
			public abstract void setS(String b);
			public abstract String getS();
		}
		
		private void testSetGet(WrapperEditor e, Object value) throws Exception
		{
			e.setValue(value);
			assertEquals(value, e.getValue());
		}
		private void testSetGetAsText(WrapperEditor e, String text) throws Exception
		{
			e.setAsText(text);
			assertEquals(text, e.getAsText());
		}
		public void testSetGetOnSimpleEditor() throws Exception
		{
			WrapperEditor e= new WrapperEditor(
				PropertyEditorManager.findEditor(boolean.class), 
				new PropertyDescriptor("B", ABean.class));
				
			testSetGet(e, new Boolean(true));
			testSetGet(e, new Boolean(false));
			testSetGet(e, null);
			testSetGet(e, "${var}");
			
			e.setValue("true");
			assertEquals(new Boolean(true), e.getValue());

			e.setValue("True");
			assertEquals(new Boolean(true), e.getValue());
		}
		public void testSetGetAsTextOnSimpleEditor() throws Exception
		{
			WrapperEditor e= new WrapperEditor(
				PropertyEditorManager.findEditor(boolean.class), 
				new PropertyDescriptor("B", ABean.class));
				
			testSetGetAsText(e, "True");
			testSetGetAsText(e, "False");
			testSetGetAsText(e, null);
			testSetGetAsText(e, "${var}");
			
			e.setAsText("true");
			assertEquals(new Boolean(true), e.getValue());

			e.setAsText("True");
			assertEquals(new Boolean(true), e.getValue());
			
			e.setAsText("invalid");
			assertEquals(null, e.getValue());
		}
		public void testSetGetAsTextOnString() throws Exception
		{
			WrapperEditor e= new WrapperEditor(
				PropertyEditorManager.findEditor(String.class), 
				new PropertyDescriptor("S", ABean.class));
				
			testSetGetAsText(e, "any string");
			testSetGetAsText(e, "");
			testSetGetAsText(e, null);
			testSetGetAsText(e, "${var}");

			// Check "Undefined" does not become a "reserved word":
			e.setAsText(UNDEFINED.toString());
			assertNotNull(e.getAsText());
		}
	}
}
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
import javax.swing.text.JTextComponent;

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

    private static Object UNDEFINED= new UniqueObject("Undefined"); //TODO: should be a resource
    private static Object EDIT= new UniqueObject("Edit");//TODO: this should be a resource

	/**
	 * Base PropertyEditor for the property at hand. Most methods in this class
	 * are delegated from this one.
	 */
    private PropertyEditor editor;

	/**
	 * The type of the objects that will be assigned to or obtained from this
	 * property. That is: the type of the property or, if this is a primitive 
	 * type (e.g. int), the corresponding wrapping type (e.g. Integer).
	 */
	private Class type;
	
	/**
	 * The list of options to be offered by this editor in adition to those
	 * defined in the wrapped editor.
	 */
	private String[] additionalTags;

	/**
	 * True iif the editor should not accept (nor produce) a null value.
	 */
	private boolean noUndefined;
	
	/**
	 * True iif the editor should not accept (nor produce) any non-null
	 * values different from the provided tags.
	 */
	private boolean noEdit;

    /**
     * The editor's combo box. 
     */
    private JComboBox combo= null;

	/**
	 * The edited property's default value.
	 */
	private Object defaultValue;

	/**
	 * Create an editor wrapping one which does not provide a custom editor.
	 * 
	 * @param editor A property editor which doesn't have a custom editor.
	 * @param descriptor the descriptor for the property being edited.
	 */
    WrapperEditor(PropertyEditor editor, PropertyDescriptor descriptor)
    {        
		this(
			editor,
			objectType(descriptor.getPropertyType()),
			(String[])descriptor.getValue("tags"),
			Boolean.TRUE.equals(descriptor.getValue("noUndefined")),
			Boolean.TRUE.equals(descriptor.getValue("noEdit")),
			descriptor.getValue("default"));
    }
    
    private static Class objectType(Class type)
    {
		// Sorry for this -- I have not found a better way:
        if (! type.isPrimitive()) return type;
		else if (type == boolean.class) return Boolean.class;
		else if (type == char.class) return Character.class;
		else if (type == byte.class) return Byte.class;
		else if (type == short.class) return Short.class;
		else if (type == int.class) return Integer.class;
		else if (type == long.class) return Long.class;
		else if (type == float.class) return Float.class;
		else if (type == double.class) return Double.class;
		else if (type == void.class) return Void.class;
		else
		{
			log.error("Class "+type+" is an unknown primitive type.");
			throw new Error("Class "+type+" is an unknown primitive type");
				// programming error: bail out.
		}
    }

    /**
     * @param editor
     * @param type
     * @param strings
     */
    protected WrapperEditor(
    	PropertyEditor editor, Class type, String[] additionalTags,
    	boolean noUndefined, boolean noEdit, Object defaultValue)
    {
		this.editor= editor;
		this.type= type;
		this.additionalTags= additionalTags;
		this.noUndefined= noUndefined;
		this.noEdit= noEdit;
		this.defaultValue= defaultValue;

		// Build the list of available values for this property:
		Vector options= new Vector();

		// The first available value is "undefined" (null).
		if (! noUndefined) options.add(UNDEFINED);

		// Add the list of property-specific values:
		String[] tags= getTags();
		if (tags != null)
		{
			options.addAll(Arrays.asList(tags));
		}
            
		// The last option is to edit a value manually:
		if (! noEdit) options.add(EDIT);
		
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
        String[] tags= editor.getTags();
		
		if (tags == null) return additionalTags;
		else if (additionalTags == null) return tags;
		else {
			LinkedList l= new LinkedList();
			l.addAll(Arrays.asList(tags));
			l.addAll(Arrays.asList(additionalTags));
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
					// URGENT: this can return null on noUndefined editors!!!!
					value= null;
				}
			}
		}

        if (log.isDebugEnabled())
        {
            log.debug(
                "->"
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
				"<-"
					+ (value != null ? value.getClass().getName() : "NULL")
					+ ":"
					+ value);
		}

		combo.setEditable(true);
		if (value == null)
		{
			if (noUndefined) throw new IllegalArgumentException();
			value= UNDEFINED;
		}
		else if (type.isInstance(value))
		{
			editor.setValue(value);
			value= editor.getAsText();
		}
		else
		{
			if (noEdit) throw new IllegalArgumentException();
			// Not a type specific to the property, so it is a String...
			// ... but, just in case, I'll check:
			if (! (value instanceof String))
			{
				log.error("When editing property of type "+type
					+", got value of type "+value.getClass());
				throw new Error("String expected, got "+value.getClass());
					// programming error, so bail out.
			} 
		}
		combo.setSelectedItem(value);
		if (combo.getSelectedIndex() >= 0) combo.setEditable(false);
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
			log.debug("->\"" + text + "\"");
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
			log.debug(text == null ? "<-null" : "<-\"" + text + "\"");
		}
		combo.setEditable(true);
		if (text == null)
		{
			if (noUndefined) throw new IllegalArgumentException();
			combo.setSelectedItem(UNDEFINED);
		}
		else 
		{
			combo.setSelectedItem(text);
		}

		if (combo.getSelectedIndex() >= 0) combo.setEditable(false);
		else if (noEdit) throw new IllegalArgumentException();
		
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
    
    /**
     * Keep track of the item that was just unselected... we will need it:
     */
	private Object lastValidValue= null;

	/**
	 * True iif we're currently processing an itemStateChanged event.
	 */
	private boolean processingItemEvent= false;

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if (log.isDebugEnabled())
		{
			log.debug("itemStatChanged:"
				+" processingItemEvent= "+processingItemEvent
				+" event= "+e);
		}

		// Avoid reentrancy, or things become really messy.
		if (processingItemEvent) return;
		processingItemEvent= true;
	
		if (e.getStateChange() == ItemEvent.DESELECTED)
		{
			lastValidValue= e.getItem();
		}
		else if (e.getStateChange() == ItemEvent.SELECTED)
		{
			if (e.getItem() == EDIT) {
				combo.setEditable(true);
				combo.getEditor().getEditorComponent().requestFocus();

				// Obtain the editor so that we can properly initialize it for
				// the editing (convenient selection & caret position):
				JTextComponent textEditor= null;
				Component c= combo.getEditor().getEditorComponent();
				if (c instanceof JTextComponent) textEditor= (JTextComponent)c;

				// We need a *valid* value to start the editing....

				if (defaultValue != null)
				{
					// The default value looks like the best choice to me.
					// At least it's something the property author can control:
					combo.setSelectedItem(defaultValue);
					if (textEditor != null) textEditor.selectAll();
				}
				else if (isValidValue(""))
				{
					// The empty string is not a bad choice, either:
					combo.setSelectedItem("");
				}
				else if (lastValidValue != UNDEFINED)
				{
					// The previously selected item may be useful...

					combo.setSelectedItem(lastValidValue);
					if (textEditor != null) textEditor.selectAll();
				}
				else if (getTags() != null)
				{
					// Close to last resort... the first tag:
					combo.setSelectedItem(getTags()[0]);
					if (textEditor != null) textEditor.selectAll();
				}
				else
				{
					// Last resort: 'expressions' are always valid on
					// editable fields:
					combo.setSelectedItem("${}");
					// Position the cursor inside the brackets, if possible:
					if (textEditor != null) textEditor.setCaretPosition(2);
				}
				// TODO: I don't really like this solution. We could make a
				// more convenient approach if we knew whether the property
				// accepts expressions or not, and whether it accepts any 
				// values beyond the provided tags or not. 
			} 
			else if (combo.getSelectedIndex() >= 0) 
			{
				combo.setEditable(false);
			}
			else if (! isValidValue((String)e.getItem()))
			{
				// TODO: warn the user. Maybe with a pop-up? A bell?
				// Revert to the previously unselected (presumed valid!) value:
				combo.setSelectedItem(lastValidValue);
				if (combo.getSelectedIndex() >= 0) combo.setEditable(false);
			}
		}
			
		processingItemEvent= false;
	}
	
	/**
	 * Determine whether a string is a valid value for the property.
	 * 
	 * @param text the value to be checked
	 * @return true iif text is a valid value
	 */
	private boolean isValidValue(String text)
	{
		if (text.indexOf("${") != -1 && ! noEdit)
		{
			// JMeter 'expressions' are valid on editable fields.
			return true;
		}

		// if it's not a JMeter 'expression' ...
		try
		{
			editor.setAsText(text);
		}
		catch (IllegalArgumentException e1)
		{
			// setAsText failed: not valid
			return false;
		}
		// setAsText succeeded: valid
		return true;
	}

	/**
	 * This is a funny hack: if you use a plain String, 
	 * entering the text of the string in the editor will make the
	 * combo revert to that option -- which actually amounts to
	 * making that string 'reserved'. I preferred to avoid this by
	 * using a different type having a controlled .toString().
	 */
	private static class UniqueObject
	{
		private String s;
		
		UniqueObject(String s)
		{
			this.s= s;
		}
		
		public String toString()
		{
			return s;
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
			// don't separate these two sub-tests
			e.setAsText("invalid");
			assertEquals(new Boolean(true), e.getValue());
				// reverts to las known good, that's the "true" above
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
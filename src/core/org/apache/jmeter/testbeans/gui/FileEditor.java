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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.apache.jmeter.gui.util.FileDialoger;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * A property editor for File properties.
 * <p>
 * Note that it never gives out File objects, but always Strings. This is
 * because JMeter is now too dumb to handle File objects (there's no
 * FileProperty).
 */
public class FileEditor extends PropertyEditorSupport implements ActionListener
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

    /**
     * The editor's combo box. 
     */
    private JComboBox combo= null;

	/**
	 * The editor panel.
	 */
	private JPanel panel= null;

    public FileEditor()
    {
		// Build the list of available values for this property:
		Vector options= new Vector();

		// The only predefined value is "undefined" (null).
		options.add(UNDEFINED);

		// Create the combo box we will use to edit this property:
		combo= new JComboBox(options);
		combo.setEditable(true);
		
		// Create a button to trigger the file chooser:
		JButton button= new JButton("Browse...");
		button.addActionListener(this);
		
		// Put both in a panel:
		panel= new JPanel(new BorderLayout(5,0));
		panel.add(combo, BorderLayout.CENTER);
		panel.add(button, BorderLayout.LINE_END);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getCustomEditor()
     */
    public Component getCustomEditor()
    {
        return panel;
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
				value= new File(text).getPath();
					// TODO: remove the ".getPath()" as soon as JMeter is
					// capable of handling File objects (maybe when we get rid
					// of JMeterProperties...)
			}
		}
        return value;
    }

	/* (non-Javadoc)
	 * @see java.beans.PropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value)
	{
		if (value == null)
		{
			value= UNDEFINED;
		}
		else if (value instanceof File)
		{
			value= ((File)value).getPath();
		}
		else
		{
			// Not a type specific to the property, so it is a String...
			// ... but, just in case, I'll check:
			if (! (value instanceof String))
			{
				log.error("When editing a file property, got value of type "+value.getClass());
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
				text= new File(text).getPath();
			}
		}
		return text;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyEditor#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException
	{
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
     * @see java.beans.PropertyEditor#supportsCustomEditor()
     */
    public boolean supportsCustomEditor()
    {
        return true;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
		JFileChooser chooser = FileDialoger.promptToOpenFile();

		File file = chooser.getSelectedFile();

		setValue(file);
    }
 
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 *//*
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
	}*/
}
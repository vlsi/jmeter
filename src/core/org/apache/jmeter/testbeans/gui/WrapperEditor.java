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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * This class implements a property editor that wraps another PropertyEditor
 * so that:
 * <ul>
 * <li>It handles JMeterProperty values.
 * <li>It provides a suitable GUI component (custom editor) for simple
 * property editors that don't have one of their own.
 * </ul>
 */
class WrapperEditor
    implements PropertyEditor, FocusListener
{
    private static Logger log = LoggingManager.getLoggerForClass();

    PropertyEditor editor;
    PropertyDescriptor descriptor;

    /**
     * The swing component doing the actual GUI work.
     */
    Component component;

    /**
     * Copy of component if it's a text field.
     */
    JTextField field= null;
     
    WrapperEditor(PropertyEditor editor, PropertyDescriptor descriptor)
    {
        this.editor= editor;
        this.descriptor= descriptor;

        if (editor.supportsCustomEditor())
        {
            component= editor.getCustomEditor();
        }
        else
        {
            field= new JTextField();
            field.addFocusListener(this);
            component= field;
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        editor.addPropertyChangeListener(listener);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getAsText()
     */
    public String getAsText()
    {
        String result= editor.getAsText();
        if (log.isDebugEnabled())
        {
            log.debug(descriptor.getName()+"->\""+result+"\"");
        }
        return result;
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getCustomEditor()
     */
    public Component getCustomEditor()
    {
        return component;
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getJavaInitializationString()
     */
    public String getJavaInitializationString()
    {
        return editor.getJavaInitializationString();
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getTags()
     */
    public String[] getTags()
    {
        return editor.getTags();
    }

    /**
     * This bean editor always returns a JMeterProperty.
     * 
     * @see java.beans.PropertyEditor#getValue()
     * @see org.apache.jmeter.testelement.property.JMeterProperty
     */
    public Object getValue()
    {
        String name= descriptor.getName();
        JMeterProperty result;
            
        result= TestBean.wrapInProperty(editor.getValue());
        result.setName(name);
        if (log.isDebugEnabled())
        {
            log.debug(descriptor.getName()+"->"
                +(result!=null?result.getClass().getName():"NULL")
                +":"+result);
        }
        return result;
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
     * @see java.beans.PropertyEditor#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        editor.removePropertyChangeListener(listener);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#setAsText(java.lang.String)
     */
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (log.isDebugEnabled())
        {
            log.debug(descriptor.getName()+"<-\""+text+"\"");
        }
        if (field != null)
        {
            field.setText(text);
        }
        editor.setAsText(text);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#setValue(java.lang.Object)
     */
    public void setValue(Object value)
    {
        if (log.isDebugEnabled())
        {
            log.debug(descriptor.getName()+"<-"
                +(value!=null?value.getClass().getName():"NULL")
                +":"+value);
        }
        if (value instanceof JMeterProperty)
        {
            value= TestBean.unwrapProperty(
                (JMeterProperty)value, descriptor.getPropertyType());
        }
        editor.setValue(value);
        if (field != null)
        {
            field.setText(editor.getAsText());
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#supportsCustomEditor()
     */
    public boolean supportsCustomEditor()
    {
        return true;
    }

    /* (non-Javadoc)
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    public void focusGained(FocusEvent e)
    {
        // no-op
    }

    /* (non-Javadoc)
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    public void focusLost(FocusEvent e)
    {
        // Copy our data back to the property editor:
            
        Document d= field.getDocument();
        String text;
        try
        {
            text= d.getText(0, d.getLength());
        }
        catch (BadLocationException e1)
        {
            log.error("This can't happen!", e1);
            throw new Error(e1); // Bail out.
        }

        if (log.isDebugEnabled())
        {
            log.debug(descriptor.getName()+": "+text);
        }

        try
        {
            editor.setAsText(text);
        }
        catch (IllegalArgumentException e1)
        {
            // TODO: report to the user??
        }
    }
}

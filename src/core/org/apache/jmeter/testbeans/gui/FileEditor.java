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
import java.beans.PropertyEditorSupport;
import java.io.File;

import javax.swing.JButton;
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
public class FileEditor extends WrapperEditor implements ActionListener
{
    private static Logger log= LoggingManager.getLoggerForClass();

	// Implementation big picture: implementing this requires both a
	// wrapped editor (to implement the text<==>File conversion)
	// and a 'wrapping' one to provide the "Browse..." button.
	// This class implements the wrapping one, the SimpleFileEditor
	// defined below implements the wrapped one. Hope it's clear.
	
	/**
	 * The editor's panel.
	 */
	private JPanel panel= null;

    public FileEditor()
    {
    	super(new SimpleFileEditor(), File.class, null);
    	
		// Create a button to trigger the file chooser:
		JButton button= new JButton("Browse...");
		button.addActionListener(this);
		
		// Create a panel containing the combo and the button:
		panel= new JPanel(new BorderLayout(5,0));
		panel.add(super.getCustomEditor(), BorderLayout.CENTER);
		panel.add(button, BorderLayout.LINE_END);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditor#getCustomEditor()
     */
    public Component getCustomEditor()
    {
        return panel;
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
    
	private static class SimpleFileEditor extends PropertyEditorSupport
	{
        /* (non-Javadoc)
         * @see java.beans.PropertyEditor#getAsText()
         */
        public String getAsText()
        {
            return ((File)super.getValue()).getPath();
        }

        /* (non-Javadoc)
         * @see java.beans.PropertyEditor#setAsText(java.lang.String)
         */
        public void setAsText(String text) throws IllegalArgumentException
        {
            setValue(new File(text));
        }
        
        /*
         * Oh, I forgot: JMeter doesn't support File properties yet. Need to work
         * on this as a String :-(
         */
        public Object getValue()
        {
        	return getAsText();
        }
	}
}
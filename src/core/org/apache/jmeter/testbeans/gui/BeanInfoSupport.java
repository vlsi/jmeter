/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2004 The Apache Software Foundation.  All rights
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

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Support class for test bean beanInfo objects. It will help using the
 * introspector to get most of the information, then modify it at will.
 * <p>
 * To use, subclass it, create a subclass with a parameter-less constructor
 * that:
 * <ol>
 * <li>Calls super(beanClass)
 * <li>Uses the property(String), ... methods to get and modify any property
 * descriptor that needs to be changed.
 * </ol>
 */
public abstract class BeanInfoSupport implements BeanInfo {

	private static transient Logger log = LoggingManager.getLoggerForClass();

	private BeanInfo rootBeanInfo;

	private PropertyDescriptor[] properties;

	/**
	 * Construct a BeanInfo for the given class.
	 */
	protected BeanInfoSupport(Class beanClass) {
		try {
			rootBeanInfo= Introspector.getBeanInfo(
				beanClass,
				Introspector.IGNORE_IMMEDIATE_BEANINFO);
		} catch (IntrospectionException e) {
			log.error("Can't introspect.", e);
			throw new Error(e); // Programming error: bail out.
		}

		properties= rootBeanInfo.getPropertyDescriptors();
	}

	protected PropertyDescriptor property(String name) {
		for (int i=0; i<properties.length; i++)
		{
			if (properties[i].getName().equals(name)) {
				return properties[i];
			}
		}
		return null;
	}

	public BeanInfo[] getAdditionalBeanInfo() {
		return rootBeanInfo.getAdditionalBeanInfo();
	}

	public BeanDescriptor getBeanDescriptor() {
		return rootBeanInfo.getBeanDescriptor();
	}

	public int getDefaultEventIndex() {
		return rootBeanInfo.getDefaultEventIndex();
	}

	public int getDefaultPropertyIndex() {
		return rootBeanInfo.getDefaultPropertyIndex();
	}

	public EventSetDescriptor[] getEventSetDescriptors() {
		return rootBeanInfo.getEventSetDescriptors();
	}

	public Image getIcon(int iconKind) {
		return rootBeanInfo.getIcon(iconKind);
	}

	public MethodDescriptor[] getMethodDescriptors() {
		return rootBeanInfo.getMethodDescriptors();
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		return properties;
	}
}

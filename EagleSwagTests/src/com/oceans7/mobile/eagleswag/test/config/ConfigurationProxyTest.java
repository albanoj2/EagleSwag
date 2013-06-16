/*
 * EagleSwag Android Mobile Application
 * Copyright (C) 2013 Oceans7
 * Oceans7 Mobile Applications Development Team
 * 
 * This software is free and governed by the terms of the GNU General Public
 * License as published by the Free Software Foundation. This software may be
 * redistributed and/or modified in accordance with version 3, or any later
 * version, of the GNU General Public License.
 * 
 * This software is distributed without any warranty; without even the implied
 * warranty of merchantability or fitness for a particular purpose. For further
 * detail, refer to the GNU General Public License, which can be found in the
 * LICENSE.txt file at the root directory of this project, or online at:
 * 
 * <http://www.gnu.org/licenses/>
 */

package com.oceans7.mobile.eagleswag.test.config;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobile.eagleswag.config.ConfigurationController;
import com.oceans7.mobile.eagleswag.config.ConfigurationProxy;

/**
 * Test cases for {@link com.oceans7.mobile.eagleswag.config.ConfigurationProxy}.
 * 
 * @author Justin Albano
 */
public class ConfigurationProxyTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used for the test cases.
	 */
	private Context context;
	
	/**
	 * The configuration controller under test.
	 */
	private ConfigurationController controller;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp () throws Exception {
		super.setUp();

		// Establish the context for the test case
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");

		// Setup the controller as a proxy
		this.controller = new ConfigurationProxy();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationProxy#getQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetQuestionTypeOnce () {
		// Parse once to ensure the parser obtains the data
		HelperMethods.ensureDataIsParsedCorrectly(this.controller, this.context);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationProxy#getQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetQuestionTypeTwice () {

		for (int i = 0; i < 2; i++) {
			// Repeat the operation twice to ensure no errors occur when the
			// cached configuration values are used in the proxy
			HelperMethods.ensureDataIsParsedCorrectly(this.controller, this.context);
		}

	}

}

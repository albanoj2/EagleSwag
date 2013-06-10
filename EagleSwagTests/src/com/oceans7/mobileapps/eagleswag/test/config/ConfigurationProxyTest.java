/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigProxyTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Test fixture for ConfigurationProxy.
 * 
 * @see com.oceans7.mobileapps.eagleswag.config.ConfigurationProxy
 * 
 *      FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.test.config;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationController;
import com.oceans7.mobileapps.eagleswag.config.ConfigurationProxy;

public class ConfigurationProxyTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Context context;
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationProxy#getQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetQuestionTypeOnce () {
		// Parse once to ensure the parser obtains the data
		HelperMethods.ensureDataIsParsedCorrectly(this.controller, this.context);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationProxy#getQuestionTypes(android.content.Context)}
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

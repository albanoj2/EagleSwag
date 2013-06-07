/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeParserTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Test fixture for ConfigurationParser.
 * 
 * @see com.oceans7.mobileapps.eagleswag.config.ConfigurationParser
 */

package com.oceans7.mobileapps.eagleswag.test.config;

import java.util.Map;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationController;
import com.oceans7.mobileapps.eagleswag.config.ConfigurationParser;
import com.oceans7.mobileapps.eagleswag.config.QuestionType;
import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy;

public class ConfigurationParserTest extends InstrumentationTestCase {

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
		
		// Establish the context for this test case
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");

		// Setup the controller as the configuration parser
		this.controller = new ConfigurationParser();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationParser#getQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetQuestionType () {
		// Ensure the data is parsed correctly by the parser
		HelperMethods.ensureDataIsParsedCorrectly(this.controller, this.context);
	}

}

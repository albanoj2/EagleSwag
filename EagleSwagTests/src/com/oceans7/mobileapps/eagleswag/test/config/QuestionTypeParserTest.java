/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeParserTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 * TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.test.config;

import java.util.Map;

import android.test.InstrumentationTestCase;

import com.oceans7.mobileapps.eagleswag.config.QuestionType;
import com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigController;
import com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigParser;
import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;

public class QuestionTypeParserTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private QuestionTypeConfigController controller;
	
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

		// Setup the controller as the configuration parser
		this.controller = new QuestionTypeConfigParser();
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigParser#getQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetQuestionType () {

		// Obtain the types from the test configuration file
		Map<Class<? extends Question>, QuestionType> map = this.controller.getQuestionTypes(this.getInstrumentation().getContext());

		// Ensure that the data is correct for the test general question type
		QuestionType generalType = map.get(GeneralQuestion.class);
		assertEquals("General => data asset:", "data/questions.json", generalType.getDataAsset());
		assertEquals("General => JSON ID:", "generalQuestions", generalType.getJsonId());
		assertEquals("General => table:", "GeneralQuestions", generalType.getSqliteTable());

		// Ensure that the data is correct for the test engineer question type
		QuestionType engineeringType = map.get(EngineeringQuestion.class);
		assertEquals("Engineering => data asset:", "data/questions.json", engineeringType.getDataAsset());
		assertEquals("Engineering => JSON ID:", "engineeringQuestions", engineeringType.getJsonId());
		assertEquals("Engineering => table:", "EngineeringQuestions", engineeringType.getSqliteTable());
	}

}

/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigProxyTest.java
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
import com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigProxy;
import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;

public class QuestionTypeConfigProxyTest extends InstrumentationTestCase {

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

		// Setup the controller as a proxy
		this.controller = new QuestionTypeConfigProxy();
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigProxy#getQuestionType(android.content.Context)}
	 * .
	 */
	public void testGetQuestionTypeOnce () {

		// Obtain the types from the test configuration file
		Map<Class<? extends Question>, QuestionType> map = this.controller.getQuestionType(this.getInstrumentation().getContext());

		// Ensure that the data is correct for the test general question type
		QuestionType generalType = map.get(GeneralQuestion.class);
		assertEquals("General => data asset:", "data/general-questions.json", generalType.getDataAsset());
		assertEquals("General => table:", "GeneralQuestions", generalType.getSqliteTable());

		// Ensure that the data is correct for the test engineer question type
		QuestionType engineeringType = map.get(EngineeringQuestion.class);
		assertEquals("Engineering => data asset:", "data/engineering-questions.json", engineeringType.getDataAsset());
		assertEquals("Engineering => table:", "EngineeringQuestions", engineeringType.getSqliteTable());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigProxy#getQuestionType(android.content.Context)}
	 * .
	 */
	public void testGetQuestionTypeTwice () {

		// Obtain the types from the test configuration file
		Map<Class<? extends Question>, QuestionType> map = this.controller.getQuestionType(this.getInstrumentation().getContext());

		for (int i = 0; i < 2; i++) {
			// Repeat the operation twice to ensure no errors occur when the
			// cached configuration values are used in the proxy

			// Ensure that the data is correct for the test general type
			QuestionType generalType = map.get(GeneralQuestion.class);
			assertEquals("General => data asset:", "data/general-questions.json", generalType.getDataAsset());
			assertEquals("General => table:", "GeneralQuestions", generalType.getSqliteTable());

			// Ensure that the data is correct for the test engineer type
			QuestionType engineeringType = map.get(EngineeringQuestion.class);
			assertEquals("Engineering => data asset:", "data/engineering-questions.json", engineeringType.getDataAsset());
			assertEquals("Engineering => table:", "EngineeringQuestions", engineeringType.getSqliteTable());
		}

	}

}

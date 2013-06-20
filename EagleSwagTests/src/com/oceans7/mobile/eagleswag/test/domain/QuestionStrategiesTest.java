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

package com.oceans7.mobile.eagleswag.test.domain;

import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobile.eagleswag.domain.questions.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.PilotQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.Question;
import com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType;
import com.oceans7.mobile.eagleswag.domain.roundtype.PilotRoundType;

/**
 * Test cases for {@link com.oceans7.mobile.eagleswag.domain.roundtype.RoundType}.
 * 
 * @author Justin Albano
 * 
 * @see com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType
 * @see com.oceans7.mobile.eagleswag.domain.roundtype.PilotRoundType
 */
public class QuestionStrategiesTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used for the test cases.
	 */
	private Context context;

	/**
	 * The asset location of the question controller configuration resource.
	 */
	private static final String QUESTION_CONTROLLER_CONFIG_ASSET = "config/domain/question-distribution.cfg";

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

		// Obtain the target context
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Ensure that the engineering queue is created and is iterable.
	 * 
	 * @throws Exception
	 */
	public void testCreateEngineeringQueue () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new EngineeringRoundType().getQuestions(this.context);

		for (Question question : questions) {
			// Iterate through the queue and pop off each element

			try {
				// Try to obtain a simple piece of data from the
				question.getId();
			}
			catch (NullPointerException e) {
				// The queue is not fluid and is either null or has null values
				assertTrue(false);
			}
		}
	}

	/**
	 * Ensure that the correct number of total questions are returned, based on
	 * the configuration specified in the configuration file stored in 'res/raw'
	 * directory.
	 * 
	 * @throws Exception
	 */
	public void testCreateCorrectTotalNumberOfEngineeringQuestions () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new EngineeringRoundType().getQuestions(this.context);

		// Obtain the number of questions that "should" be loaded
		Properties properties = new Properties();
		properties.load(this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET));

		// Retrieve the number of engineering questions
		int theoEngineeringQuestions = Integer.parseInt(properties.getProperty("questions.distribution.engineer.specific"));
		Log.d(this.getClass().getName(), "(" + theoEngineeringQuestions + ") engineering questions should be loaded");

		// Retrieve the number of general questions
		int theoGeneralQuestions = Integer.parseInt(properties.getProperty("questions.distribution.engineer.general"));
		Log.d(this.getClass().getName(), "(" + theoGeneralQuestions + ") general questions should be loaded");

		// Total count of questions
		int questionCount = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Iterate through the queue and pop off each element
			questionCount++;
		}

		// Ensure the the correct number of questions where loaded
		assertEquals(theoEngineeringQuestions + theoGeneralQuestions, questionCount);
	}

	/**
	 * Ensures that correct number of general and engineering questions are
	 * mixed within the question queue. The correctness of this test is verified
	 * by comparing the actual results to the configuration file for the
	 * question controller.
	 * 
	 * @throws Exception
	 */
	public void testCreateCorrectDistributionOfEngineeringQuestions () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new EngineeringRoundType().getQuestions(this.context);

		// Obtain the number of questions that "should" be loaded
		Properties properties = new Properties();
		properties.load(this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET));

		// Retrieve the number of engineering questions
		int theoEngineeringQuestions = Integer.parseInt(properties.getProperty("questions.distribution.engineer.specific"));
		Log.d(this.getClass().getName(), "(" + theoEngineeringQuestions + ") engineering questions should be loaded");

		// Retrieve the number of general questions
		int theoGeneralQuestions = Integer.parseInt(properties.getProperty("questions.distribution.engineer.general"));
		Log.d(this.getClass().getName(), "(" + theoGeneralQuestions + ") general questions should be loaded");

		// Record the number of general and engineering questions
		int engineeringQuestions = 0;
		int generalQuestions = 0;

		for (Question question : questions) {
			// Loop through the queue of questions

			if (question instanceof EngineeringQuestion) {
				// The question on the queue is an engineering question
				engineeringQuestions++;
			}
			else if (question instanceof GeneralQuestion) {
				// The question on the queue is a general question
				generalQuestions++;
			}
		}

		// Ensure the correct distribution of questions
		assertEquals(theoEngineeringQuestions, engineeringQuestions);
		assertEquals(theoGeneralQuestions, generalQuestions);
	}

	/**
	 * Ensure that the pilot queue is created and is iterable.
	 * 
	 * @throws Exception
	 */
	public void testCreatePilotQueue () throws Exception {

		// Obtain the pilot questions
		List<Question> questions = new PilotRoundType().getQuestions(this.context);

		for (Question question : questions) {
			// Iterate through the queue and pop off each element

			try {
				// Try to obtain a simple piece of data from the
				question.getId();
			}
			catch (NullPointerException e) {
				// The queue is not fluid and is either null or has null values
				assertTrue(false);
			}
		}
	}

	/**
	 * Ensure that the correct number of total questions are returned, based on
	 * the configuration specified in the configuration file stored in 'res/raw'
	 * directory.
	 * 
	 * @throws Exception
	 */
	public void testCreateCorrectTotalNumberOfPilotQuestions () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new PilotRoundType().getQuestions(this.context);

		// Obtain the number of questions that "should" be loaded
		Properties properties = new Properties();
		properties.load(this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET));

		// Retrieve the number of pilot questions
		int theoPilotQuestions = Integer.parseInt(properties.getProperty("questions.distribution.pilot.specific"));
		Log.d(this.getClass().getName(), "(" + theoPilotQuestions + ") pilot questions should be loaded");

		// Retrieve the number of general questions
		int theoGeneralQuestions = Integer.parseInt(properties.getProperty("questions.distribution.pilot.general"));
		Log.d(this.getClass().getName(), "(" + theoGeneralQuestions + ") general questions should be loaded");

		// Total count of questions
		int questionCount = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Iterate through the queue and pop off each element
			questionCount++;
		}

		// Ensure the the correct number of questions where loaded
		assertEquals(theoPilotQuestions + theoGeneralQuestions, questionCount);
	}

	/**
	 * Ensures that correct number of general and pilot questions are mixed
	 * within the question queue. The correctness of this test is verified by
	 * comparing the actual results to the configuration file for the question
	 * controller.
	 * 
	 * @throws Exception
	 */
	public void testCreateCorrectDistributionOfPilotQuestions () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new PilotRoundType().getQuestions(this.context);

		// Obtain the number of questions that "should" be loaded
		Properties properties = new Properties();
		properties.load(this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET));

		// Retrieve the number of pilot questions
		int theoPilotQuestions = Integer.parseInt(properties.getProperty("questions.distribution.pilot.specific"));
		Log.d(this.getClass().getName(), "(" + theoPilotQuestions + ") pilot questions should be loaded");

		// Retrieve the number of general questions
		int theoGeneralQuestions = Integer.parseInt(properties.getProperty("questions.distribution.pilot.general"));
		Log.d(this.getClass().getName(), "(" + theoGeneralQuestions + ") general questions should be loaded");

		// Record the number of general and engineering questions
		int pilotQuestions = 0;
		int generalQuestions = 0;

		for (Question question : questions) {
			// Loop through the queue of questions

			if (question instanceof PilotQuestion) {
				// The question on the queue is an pilot question
				pilotQuestions++;
			}
			else if (question instanceof GeneralQuestion) {
				// The question on the queue is a general question
				generalQuestions++;
			}
		}

		// Ensure the correct distribution of questions
		assertEquals(theoPilotQuestions, pilotQuestions);
		assertEquals(theoGeneralQuestions, generalQuestions);
	}

	/**
	 * Ensure that the questions obtained from the engineering question queue
	 * are valid.
	 * 
	 * @throws Exception
	 */
	public void testValidEngineeringQuestions () throws Exception {

		// Obtain the engineering questions
		List<Question> questions = new EngineeringRoundType().getQuestions(this.context);

		for (Question question : questions) {
			// Loop through each question and ensure the data is not null

			assertTrue("ID is greater than zero", question.getId() > 0);
			assertNotNull("Question string is not null", question.getQuestionString());
			assertTrue("Question string is not blank", !question.getQuestionString().equals(""));
			assertTrue("Yes point value is non-negative", question.getYesPointValue() >= 0);
			assertTrue("No point value is non-negative", question.getNoPointValue() >= 0);
			assertTrue("Used count is non-negative", question.getUsedCount() >= 0);
		}
	}

	/**
	 * Ensure that the questions obtained from the pilot question queue are
	 * valid.
	 * 
	 * @throws Exception
	 */
	public void testValidPilotQuestions () throws Exception {

		// Obtain the pilot questions
		List<Question> questions = new PilotRoundType().getQuestions(this.context);

		for (Question question : questions) {
			// Loop through each question and ensure the data is not null

			assertTrue("ID is greater than zero", question.getId() > 0);
			assertNotNull("Question string is not null", question.getQuestionString());
			assertTrue("Question string is not blank", !question.getQuestionString().equals(""));
			assertTrue("Yes point value is non-negative", question.getYesPointValue() >= 0);
			assertTrue("No point value is non-negative", question.getNoPointValue() >= 0);
			assertTrue("Used count is non-negative", question.getUsedCount() >= 0);
		}
	}

}

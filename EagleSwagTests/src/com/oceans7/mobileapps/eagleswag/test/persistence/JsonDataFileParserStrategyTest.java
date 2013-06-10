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

package com.oceans7.mobileapps.eagleswag.test.persistence;

import java.util.Queue;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy;

/**
 * Test fixture for the JSON data file parser.
 * 
 * TODO Complete documentation
 * 
 * @author Justin Albano
 */
public class JsonDataFileParserStrategyTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	
	private Context context;
	private JsonDataFileParserStrategy parser;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.AndroidTestCase#setUp()
	 */
	@Override
	protected void setUp () throws Exception {
		super.setUp();
		
		// Establish the context for the test
		this.context = this.getInstrumentation().getContext();

		// Instantiate the parser
		this.parser = new JsonDataFileParserStrategy();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.AndroidTestCase#tearDown()
	 */
	@Override
	protected void tearDown () throws Exception {
		super.tearDown();
	}
	
	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy#getGeneralQuestions()}
	 * 
	 * Parsers a test JSON file with the same format as the actual JSON file
	 * containing the questions data and ensures that the correct data for the
	 * questions are retrieved.
	 */
	public void testGetGeneralQuestions () {

		// Obtain the parsed queue from the JSON data file parser
		Queue<GeneralQuestion> questions = this.parser.getQuestions(GeneralQuestion.class, this.context);

		// Ensure that the list contains data
		assertEquals("General queue contains data", questions.size(), 2);

		// Ensure that the data for the first element is correct
		GeneralQuestion question1 = questions.remove();
		assertEquals("General question 1", question1.getQuestionString());
		assertEquals(10, question1.getYesPointValue());
		assertEquals(0, question1.getNoPointValue());
		assertEquals(0, question1.getUsedCount());

		// Ensure that the data for the second element is correct
		GeneralQuestion question2 = questions.remove();
		assertEquals("General question 2", question2.getQuestionString());
		assertEquals(5, question2.getYesPointValue());
		assertEquals(5, question2.getNoPointValue());
		assertEquals(2, question2.getUsedCount());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy#getEngineeringQuestions()}
	 * 
	 * Parses a test JSON file containing data in the same format as the actual
	 * JSON data file that contains the questions, and ensures that the data is
	 * parsed correctly.
	 */
	public void testGetEngineeringQuestions () {

		// Obtain the parsed queue from the JSON data file parser
		Queue<EngineeringQuestion> questions = this.parser.getQuestions(EngineeringQuestion.class, this.context);

		// Ensure that the list contains data
		assertEquals("Engineering queue contains data", questions.size(), 2);

		// Ensure that the data for the first element is correct
		EngineeringQuestion question1 = questions.remove();
		assertEquals("Engineering question 1", question1.getQuestionString());
		assertEquals(0, question1.getYesPointValue());
		assertEquals(5, question1.getNoPointValue());
		assertEquals(0, question1.getUsedCount());

		// Ensure that the data for the second element is correct
		EngineeringQuestion question2 = questions.remove();
		assertEquals("Engineering question 2", question2.getQuestionString());
		assertEquals(10, question2.getYesPointValue());
		assertEquals(0, question2.getNoPointValue());
		assertEquals(3, question2.getUsedCount());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy#getPilotQuestions()}
	 * 
	 * Parses a test JSON file containing data in the same format as the actual
	 * JSON data file that contains the questions, and ensures that the data is
	 * parsed correctly.
	 */
	public void testGetPilotQuestions () {

		// Obtain the parsed queue from the JSON data file parser
		Queue<PilotQuestion> questions = this.parser.getQuestions(PilotQuestion.class, this.context);

		// Ensure that the list contains data
		assertEquals("Pilot queue contains data", questions.size(), 2);

		// Ensure that the data for the first element is correct
		PilotQuestion question1 = questions.remove();
		assertEquals("Pilot question 1", question1.getQuestionString());
		assertEquals(10, question1.getYesPointValue());
		assertEquals(0, question1.getNoPointValue());
		assertEquals(0, question1.getUsedCount());

		// Ensure that the data for the second element is correct
		PilotQuestion question2 = questions.remove();
		assertEquals("Pilot question 2", question2.getQuestionString());
		assertEquals(10, question2.getYesPointValue());
		assertEquals(2, question2.getNoPointValue());
		assertEquals(10, question2.getUsedCount());
	}

}

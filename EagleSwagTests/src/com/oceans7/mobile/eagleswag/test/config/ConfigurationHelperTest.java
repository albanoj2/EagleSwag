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

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobile.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobile.eagleswag.config.QuestionType;
import com.oceans7.mobile.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.PilotQuestion;
import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.persistence.JsonDataFileParserStrategy;

/**
 * TODO Documentation
 * 
 * @author Justin Albano
 */
public class ConfigurationHelperTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Context context;
	private ArrayList<Class<? extends Question>> questionClasses;
	private ArrayList<String> tableNames;
	private ArrayList<String> jsonIds;

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

		// Build the list of question classes
		this.questionClasses = new ArrayList<Class<? extends Question>>();
		this.questionClasses.add(GeneralQuestion.class);
		this.questionClasses.add(EngineeringQuestion.class);
		this.questionClasses.add(PilotQuestion.class);

		// Build the list of table names
		this.tableNames = new ArrayList<String>();
		this.tableNames.add("GeneralQuestions");
		this.tableNames.add("EngineeringQuestions");
		this.tableNames.add("PilotQuestions");

		// Build the list of JSON IDs
		this.jsonIds = new ArrayList<String>();
		this.jsonIds.add("generalQuestions");
		this.jsonIds.add("engineeringQuestions");
		this.jsonIds.add("pilotQuestions");
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
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationHelper#getAllQuestionTypes(android.content.Context)}
	 * .
	 */
	public void testGetAllQuestionTypes () {

		// Get all question types from the helper
		Map<Class<? extends Question>, QuestionType> questionTypes = ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);

		for (Class<? extends Question> clazz : this.questionClasses) {
			// Ensure all question types are set
			assertNotNull(clazz.getCanonicalName() + " type not null:", questionTypes.get(clazz));
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationHelper#getDataAsset(java.lang.Class, android.content.Context)}
	 * .
	 */
	public void testGetDataAsset () {

		for (Class<? extends Question> clazz : this.questionClasses) {
			// Ensure the data asset is correctly set for the question types
			assertEquals(clazz.getCanonicalName() + " data asset:",
				"data/questions.json",
				ConfigurationHelper.getInstance().getDataAsset(clazz, this.context));
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationHelper#getParserStrategy(java.lang.Class, android.content.Context)}
	 * .
	 */
	public void testGetParserStrategy () {

		for (Class<? extends Question> clazz : this.questionClasses) {
			// Ensure the parser strategy is correct for the question types
			assertEquals(clazz.getCanonicalName() + " parser strategy:",
				JsonDataFileParserStrategy.class.getName(),
				ConfigurationHelper.getInstance().getParserStrategy(clazz, this.context).getName());
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationHelper#getTableName(java.lang.Class, android.content.Context)}
	 * .
	 */
	public void testGetTableName () {

		// Index of current iteration
		int i = 0;

		for (Class<? extends Question> clazz : this.questionClasses) {
			// Ensure the table name is correct for the question types
			assertEquals(clazz.getCanonicalName() + " SQLite table name:",
				this.tableNames.get(i),
				ConfigurationHelper.getInstance().getTableName(clazz, this.context));

			i++;
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.config.ConfigurationHelper#getJsonId(java.lang.Class, android.content.Context)}
	 * .
	 */
	public void testGetJsonId () {

		// Index of current iteration
		int i = 0;

		for (Class<? extends Question> clazz : this.questionClasses) {
			// Ensure the JSON ID is correct for the question types
			assertEquals(clazz.getCanonicalName() + " JSON ID:",
				this.jsonIds.get(i),
				ConfigurationHelper.getInstance().getJsonId(clazz, this.context));

			i++;
		}
	}

}

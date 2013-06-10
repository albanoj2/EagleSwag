/**
 * @author Justin Albano
 * @date Jun 6, 2013
 * @file ConfigurationHelperTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO Documentation
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.test.config;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.config.QuestionType;
import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.JsonDataFileParserStrategy;

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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper#getAllQuestionTypes(android.content.Context)}
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper#getDataAsset(java.lang.Class, android.content.Context)}
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper#getParserStrategy(java.lang.Class, android.content.Context)}
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper#getTableName(java.lang.Class, android.content.Context)}
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
	 * {@link com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper#getJsonId(java.lang.Class, android.content.Context)}
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

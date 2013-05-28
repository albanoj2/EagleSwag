/**
 * @author Justin Albano
 * @date May 21, 2013
 * @file SQLiteRoundTest.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 * 
 * @see com.oceans7.mobileapps.eagleswag.domain.Round
 *
 */

package com.oceans7.mobileapps.eagleswag.test.persistence.sqlite;

import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Round;
import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataController;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerConstants;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries;

public class SQLiteRoundTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context of the application under test.
	 */
	private Context context;
	
	/**
	 * A reference to the data controller managing questions data.
	 */
	private DataController controller;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp () throws Exception {
		super.setUp();

		// Set the context of the test fixture
		this.context = this.getInstrumentation().getTargetContext();
		
		// Set the reference to the database
		this.controller = DataControllerFactory.getInstance().getDataController(this.context);
		this.controller.open(this.context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown () throws Exception {
		super.tearDown();
		
		// Close the controller
		this.controller.close();
	}

	/***************************************************************************
	 * Helper Methods
	 **************************************************************************/
	
	/**
	 * Helper method that supplies the logic for checking that the used count
	 * for a question is updated in the database. Note that this method is
	 * strictly for use with the SQLiteDataController. If the implementation
	 * (the data controller) for the system is changed (another data controller
	 * is specified in the configuration), this test case is no longer relevant.
	 * 
	 * @param key
	 *            The used to retrieve the database table in the SQLite data
	 *            controller.
	 */
	public <T extends Question> void helperSavedRoundIncrementsUsedCountInTable (Class<T> key) {
		
		// The round object to store the results of this round
		Round round = new Round();
		
		// Obtain two questions of the type specified
		Queue<T> questions = this.controller.<T>getQuestions(key, 2);
		
		// Obtain the questions and their original used count
		T question1 = questions.remove();
		T question2 = questions.remove();
		long originalUsedCount1 = question1.getUsedCount();
		long originalUsedCount2 = question2.getUsedCount();
		
		// Log the obtained data
		Log.d(this.getClass().getName(), "Question 1 ID: " + question1.getId());
		Log.d(this.getClass().getName(), "Question 2 ID: " + question2.getId());
		Log.d(this.getClass().getName(), "Original used count 1: " + originalUsedCount1);
		Log.d(this.getClass().getName(), "Original used count 2: " + originalUsedCount2);
		
		// Submit one of the questions as 'yes' and the other as 'no'
		round.submitYesQuestion(question1);
		round.submitNoQuestion(question2);
		
		// Save the round
		round.save(this.context);
		
		// The table to obtain the questions from
		String table = ((SQLiteDataController) this.controller).getClassToTableMap().get(key);
		
		// Retrieve the questions from the database table
		Cursor cursor1 = ((SQLiteDataController) this.controller).getDatabase().rawQuery("SELECT * FROM " + table + " WHERE _id = ?", new String[] {"" + question1.getId()});
		Cursor cursor2 = ((SQLiteDataController) this.controller).getDatabase().rawQuery("SELECT * FROM " + table + " WHERE _id = ?", new String[] {"" + question2.getId()});
		cursor1.moveToFirst();
		cursor2.moveToFirst();
		
		// Retrieve the used count from the questions
		long newUsedCount1 = cursor1.getLong(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());
		long newUsedCount2 = cursor2.getLong(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());
		Log.d(this.getClass().getName(), "New used count 1: " + newUsedCount1);
		Log.d(this.getClass().getName(), "New used count 2: " + newUsedCount2);
		
		// Ensure the used count was updated in the database
		assertEquals("Used count 1 updated:", originalUsedCount1 + 1, newUsedCount1);
		assertEquals("Used count 2 updated:", originalUsedCount2 + 1, newUsedCount2);
		
		// Reset the used count for the obtained questions
		question1.setUsedCount(originalUsedCount1);
		question2.setUsedCount(originalUsedCount2);
		
		// Update the questions (to the original used count)
		SQLiteDataControllerQueries.updateQuestion(((SQLiteDataController) this.controller).getDatabase(), table, question1);
		SQLiteDataControllerQueries.updateQuestion(((SQLiteDataController) this.controller).getDatabase(), table, question2);
	
	}
	
	/***************************************************************************
	 * Test Cases
	 **************************************************************************/
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#save(android.content.Context)}
	 * 
	 * General questions test case for ensuring that the used count value in
	 * database is incremented when the round is saved.
	 */
	public void testSavedRoundIncrementUsedCountGeneralTable () {
		this.<GeneralQuestion>helperSavedRoundIncrementsUsedCountInTable(GeneralQuestion.class);
	}
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#save(android.content.Context)}
	 * 
	 * Engineering questions test case for ensuring that the used count value in
	 * database is incremented when the round is saved.
	 */
	public void testSavedRoundIncrementUsedCountEngineerTable () {
		this.<EngineeringQuestion>helperSavedRoundIncrementsUsedCountInTable(EngineeringQuestion.class);
	}
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#save(android.content.Context)}
	 * 
	 * Pilot questions test case for ensuring that the used count value in
	 * database is incremented when the round is saved.
	 */
	public void testSavedRoundIncrementUsedCountPilotTable () {
		this.<PilotQuestion>helperSavedRoundIncrementsUsedCountInTable(PilotQuestion.class);
	}
}

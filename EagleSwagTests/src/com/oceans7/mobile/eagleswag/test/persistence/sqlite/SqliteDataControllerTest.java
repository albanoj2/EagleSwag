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

package com.oceans7.mobile.eagleswag.test.persistence.sqlite;

import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobile.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobile.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.PilotQuestion;
import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.Score;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerConstants;
import com.oceans7.mobile.eagleswag.util.LoadingListener;

/**
 * Test fixture for SqliteDataController.
 * 
 * @author Justin Albano
 * 
 * @see com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController
 */
public class SqliteDataControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Context context;
	private SqliteDataController sqliteDataController;
	private boolean listener1Called;
	private boolean listener2Called;

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

		// Establish the context to access the SQLite database
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");
		;

		// Create the data controller
		this.sqliteDataController = new SqliteDataController();
		this.sqliteDataController.open(this.context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();

		// Close the data controller
		this.sqliteDataController.close();
	}

	/***************************************************************************
	 * Helper Methods
	 **************************************************************************/

	private <T extends Question> int helperQuestionsFound (Class<T> key, int number) throws Exception {

		// The queue to store the pilot questions
		Queue<T> questions = this.sqliteDataController.getQuestions(key, number);

		// Number of questions in the queue
		int questionsFound = 0;

		for (@SuppressWarnings("unused")
		Question question : questions) {
			// Loop through the queue
			questionsFound++;
		}

		return questionsFound;
	}

	/**
	 * Helper method that obtains more questions then available for a question
	 * type. The key specifies which type of questions are loaded. For example,
	 * to test loading more general questions than available, use
	 * GeneralQuestion.class as the key.
	 * 
	 * @param key
	 *            The type of question to test.
	 */
	public <T extends Question> void helperObtainMoreQuestionsThenAvailable (Class<T> key) throws Exception {

		// Obtain the table name for the key
		String table = ConfigurationHelper.getInstance().getTableName(key, this.context);

		// Number of questions in the engineering questions table
		long numberOfQuestionsInDatabase = DatabaseUtils.queryNumEntries(this.sqliteDataController.getDatabase(), table);

		// Number of questions actually found
		int requestedQuestions = (int) (numberOfQuestionsInDatabase + 1);
		int numberOfQuestionsFound = this.helperQuestionsFound(key, requestedQuestions);

		Log.d(this.getClass().getName(),
			"Requested " + requestedQuestions + " questions from engineering questions table and obtained " + numberOfQuestionsFound);
		assertEquals(numberOfQuestionsInDatabase, numberOfQuestionsFound);
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 */
	public void testCorrectNumberOfGeneralQuestions () throws Exception {
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, 3), 3);
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, -1), 0);
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, 0), 0);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 * 
	 * Ensures that positive (greater than 0) engineering questions are
	 * requested, 0 are returned.
	 */
	public void testCorrectNumberOfEngineeringQuestionsAboveZero () throws Exception {
		assertEquals(this.helperQuestionsFound(EngineeringQuestion.class, 3), 3);
		assertEquals(this.helperQuestionsFound(EngineeringQuestion.class, -1), 0);
		assertEquals(this.helperQuestionsFound(EngineeringQuestion.class, 0), 0);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 * 
	 * Ensures that positive (greater than 0) pilot questions are requested, 0
	 * are returned.
	 */
	public void testCorrectNumberOfPilotQuestionsAboveZero () throws Exception {
		assertEquals(this.helperQuestionsFound(PilotQuestion.class, 3), 3);
		assertEquals(this.helperQuestionsFound(PilotQuestion.class, -1), 0);
		assertEquals(this.helperQuestionsFound(PilotQuestion.class, 0), 0);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 * 
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 */
	public void testObtainMoreGeneralQuestionsThenAvailable () throws Exception {
		this.helperObtainMoreQuestionsThenAvailable(GeneralQuestion.class);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 * 
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 */
	public void testObtainMoreEngineeringQuestionsThenAvailable () throws Exception {
		this.helperObtainMoreQuestionsThenAvailable(EngineeringQuestion.class);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobile.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 * 
	 * Ensures that if more questions are requested from the database than are
	 * available, only the maximum number of questions in the database are
	 * actually returned.
	 */
	public void testObtainMorePilotQuestionsThenAvailable () throws Exception {
		this.helperObtainMoreQuestionsThenAvailable(PilotQuestion.class);
	}

	/**
	 * TODO Documentation
	 */
	public void testSavedScore () {

		// Insert a test score into the database
		Score score = new Score(10);
		score.setTimestamp(0);
		this.sqliteDataController.saveRoundScore(score, "test");

		// Obtain the score that was just placed in the database
		Cursor cursor = this.sqliteDataController.getDatabase().rawQuery("SELECT * FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + "" + " WHERE " + SqliteDataControllerConstants.SCORE_SCORE_COLUMN + " = ?",
			new String[] { "10.0" });

		// Ensure the data is correct
		cursor.moveToFirst();
		assertEquals("Correct score:", 10.0, cursor.getDouble(SqliteDataControllerConstants.ScoresColumns.SCORE.ordinal()));
		assertEquals("Correct type:", "test", cursor.getString(SqliteDataControllerConstants.ScoresColumns.TYPE.ordinal()));
		assertEquals("Correct timestamp:", 0, cursor.getLong(SqliteDataControllerConstants.ScoresColumns.TIMESTAMP.ordinal()));

	}

	/**
	 * TODO Documentation
	 */
	public void testTotalScore () {

		// Insert a few test scores into the database
		this.sqliteDataController.saveRoundScore(new Score(0), "test");
		this.sqliteDataController.saveRoundScore(new Score(50), "test");
		this.sqliteDataController.saveRoundScore(new Score(100), "test");

		// Obtain the total score for the entries placed in the database
		int totalScore = this.sqliteDataController.getTotalScore("test");

		// Ensure the total score is correct
		assertEquals("Total score is correct:", 150, totalScore);
	}

	/**
	 * TODO Documentation
	 */
	public void testAverageScore () {

		// Insert a few test scores into the database
		this.sqliteDataController.saveRoundScore(new Score(0), "test");
		this.sqliteDataController.saveRoundScore(new Score(50), "test");
		this.sqliteDataController.saveRoundScore(new Score(100), "test");

		// Obtain the average score for the entries placed in the database
		int average = this.sqliteDataController.getAverageScore("test");

		// Ensure the average score is correct
		assertEquals("Average score is correct:", 50, average);
	}

	/**
	 * TODO Documentation
	 */
	public void testAverageScoreRounding () {

		// Insert a few test scores into the database
		this.sqliteDataController.saveRoundScore(new Score(10), "test");
		this.sqliteDataController.saveRoundScore(new Score(10), "test");
		this.sqliteDataController.saveRoundScore(new Score(20), "test");

		// Obtain the average score for the entries placed in the database
		int average = this.sqliteDataController.getAverageScore("test");

		// Ensure the average score is correct
		assertEquals("Average score is correct:", 13, average);
	}
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController#addLoadingListener(com.oceans7.mobile.eagleswag.util.LoadingListener)}
	 * .
	 */
	public void testAddLoadingListener () {

		// Create loading listener 1
		LoadingListener ll1 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener1Called = true;
			}
		};

		// Create loading listener 2
		LoadingListener ll2 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener2Called = true;
			}
		};

		// Add loading listeners to the helper
		this.sqliteDataController.addLoadingListener(ll1);
		this.sqliteDataController.addLoadingListener(ll2);

		// Call the loading listeners
		this.sqliteDataController.updateLoadingListeners(0, 0);

		// Ensure the listeners were called
		assertTrue("Loading listener 1 was called:", listener1Called);
		assertTrue("Loading listener 2 was called:", listener2Called);
	}
	
	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController#removeLoadingListener(com.oceans7.mobile.eagleswag.util.LoadingListener)}
	 * .
	 */
	public void testRemoveLoadingListener () {

		// Create loading listener 1
		LoadingListener ll1 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener1Called = true;
			}
		};

		// Create loading listener 2
		LoadingListener ll2 = new LoadingListener() {

			@Override
			public void update (int total, int current) {
				listener2Called = true;
			}
		};

		// Add loading listeners to the helper
		this.sqliteDataController.addLoadingListener(ll1);
		this.sqliteDataController.addLoadingListener(ll2);
		
		// Remove the loading listener from the helper
		this.sqliteDataController.removeLoadingListener(ll1);
		this.sqliteDataController.removeLoadingListener(ll2);

		// Call the loading listeners
		this.sqliteDataController.updateLoadingListeners(0, 0);

		// Ensure the listeners were not called
		assertFalse("Loading listener 1 was not called:", listener1Called);
		assertFalse("Loading listener 2 was not called:", listener2Called);
	}
}

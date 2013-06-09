/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SqliteDataControllerTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Test fixture for SqliteDataController.
 * 
 * @see com.oceans7.mobileapps.eagleswag.persistence.sqlite.SqliteDataController
 */

package com.oceans7.mobileapps.eagleswag.test.persistence.sqlite;

import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Score;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SqliteDataController;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SqliteDataControllerConstants;

public class SqliteDataControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private Context context;
	private SqliteDataController sqliteDataController;

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
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
	 * extends Question>, int)}.
	 */
	public void testCorrectNumberOfGeneralQuestions () throws Exception {
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, 3), 3);
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, -1), 0);
		assertEquals(this.helperQuestionsFound(GeneralQuestion.class, 0), 0);
	}

	/**
	 * Test method for {@link
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
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
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
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
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
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
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
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
	 * com.oceans7.mobileapps.eagleswag.domain.Round#getQuestions(Class<?
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
		Cursor cursor = this.sqliteDataController.getDatabase().rawQuery(
			"SELECT * FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + "" + " WHERE " + SqliteDataControllerConstants.SCORE_SCORE_COLUMN + " = ?",
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
}

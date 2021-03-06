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

import java.lang.reflect.Constructor;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.Score;
import com.oceans7.mobile.eagleswag.domain.questions.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.PilotQuestion;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerConstants;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerHelper;
import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries;

/**
 * Test cases for
 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries}
 * .
 * TODO Add a test case for creating scores table
 * 
 * @author Justin Albano
 */
public class SqliteDataControllerQueriesTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used for the test cases.
	 */
	private Context context;

	/**
	 * The database under test.
	 */
	private SQLiteDatabase db;

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

		// Establish the context
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");

		// Obtain a reference to the database
		SqliteDataControllerHelper helper = new SqliteDataControllerHelper(this.context);
		this.db = helper.getWritableDatabase();
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
	 * Helper Methods
	 **************************************************************************/

	/**
	 * Creates a test question and inserts it into the specified table (that
	 * corresponds to the key provided).
	 * 
	 * @param key
	 *            The class that is used as the key to determine which SQLite
	 *            database table to insert the question into; the key are
	 *            determines the type of the question.
	 */
	public <T extends Question> void helperInsertIntoQuestionsTable (Class<T> key) throws Exception {

		// A test question
		String text = "[testing] A test question";
		int yesValue = 10;
		int noValue = 5;
		int usedCount = 2;

		// Create a test question
		Class<?>[] argTypes = new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class };
		Constructor<T> constructor = key.getDeclaredConstructor(argTypes);
		Object[] originalArgs = new Object[] { 0, text, yesValue, noValue, usedCount };
		T testQuestion = constructor.newInstance(originalArgs);

		// Obtain the name of the questions table
		String table = SqliteDataController.generateTableName(key);

		// Ensure the questions table has been created
		SqliteDataControllerQueries.createQuestionsTable(this.db, table);

		// Insert the test question into the data base
		long id = SqliteDataControllerQueries.insertIntoQuestionsTable(this.db, table, testQuestion);

		// Log the ID of the newly created row
		Log.d(this.getClass().getName(), "ID of added quetion: " + id);

		// Attempt to retrieve the data from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + table + "" + " WHERE " + SqliteDataControllerConstants.QuestionsColumns.QUESTION + " = ?",
			new String[] { text });

		// Reset cursor
		cursor.moveToFirst();

		// Log the ID of the retrieved
		Log.d(this.getClass().getName(), "ID of retrieved question: " + cursor.getString(0));

		// Iterate through the fields
		assertEquals("Question text is equal", text, cursor.getString(1));
		assertEquals("Yes point value is equal", yesValue, cursor.getLong(2));
		assertEquals("No point value is equal", noValue, cursor.getLong(3));
		assertEquals("Used count is equal", usedCount, cursor.getLong(4));

		// Remove the item from the database
		this.db.delete(table, SqliteDataControllerConstants.QuestionsColumns.QUESTION + "='" + text + "'", null);

		// Close the cursor
		cursor.close();
	}

	/**
	 * A helper method that provides a framework for testing the update of each
	 * question type. In order to customize this helper method for each type of
	 * question, the class must be provided as both a key and a generic
	 * parameter.
	 * 
	 * @param table
	 *            The table to update.
	 * @param key
	 *            The class to use as the questions to insert into the database.
	 *            For example, if a general question should be inserted into the
	 *            database (for the general test case), GeneralQuestion.class
	 *            should be supplied.
	 * @throws Exception
	 */
	public <T extends Question> void helperUpdateQuestionTable (Class<T> key) throws Exception {

		// Parameters of old and new questions
		String originalText = "A test question";
		int originalYesValue = 10;
		int originalNoValue = 0;
		int originalUsedCount = 0;
		String newText = "A new text question";
		int newYesValue = 0;
		int newNoValue = 10;
		int newUsedCount = 3;

		// Obtain the name of the questions table
		String table = SqliteDataController.generateTableName(key);

		// Ensure the questions table has been created
		SqliteDataControllerQueries.createQuestionsTable(this.db, table);

		// Create a new question to insert into the database (using reflection)
		Class<?>[] argTypes = new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class };
		Constructor<T> constructor = key.getDeclaredConstructor(argTypes);
		Object[] originalArgs = new Object[] { 0, originalText, originalYesValue, originalNoValue, originalUsedCount };
		T originalQuestion = constructor.newInstance(originalArgs);

		// Add the new question to the database
		SqliteDataControllerQueries.insertIntoQuestionsTable(this.db, table, originalQuestion);

		// Obtain the ID of the question that was just inserted; this is used to
		// ensure that the new question has the same ID as the question that was
		// just created, ensuring that the new question updates the question
		// that was just inserted
		Cursor cursorId = this.db.rawQuery("SELECT * FROM " + table + " WHERE " + SqliteDataControllerConstants.QuestionsColumns.QUESTION + " = '" + originalText + "'",
			null);
		cursorId.moveToFirst();
		int idOfInsertedQuestion = cursorId.getInt(SqliteDataControllerConstants.QuestionsColumns.ID.ordinal());

		// Create a new question (using reflection)
		Object[] newArgs = new Object[] { idOfInsertedQuestion, newText, newYesValue, newNoValue, newUsedCount };
		T newQuestion = constructor.newInstance(newArgs);

		// Store the update in the database
		SqliteDataControllerQueries.updateQuestion(this.db, table, newQuestion);

		// Retrieve the same question from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + table + " WHERE " + SqliteDataControllerConstants.QuestionsColumns.ID + " = ?",
			new String[] { "" + idOfInsertedQuestion });
		cursor.moveToFirst();

		assertEquals("Question string updated:", newText, cursor.getString(SqliteDataControllerConstants.QuestionsColumns.QUESTION.ordinal()));
		assertEquals("Yes value updated:", newYesValue, cursor.getInt(SqliteDataControllerConstants.QuestionsColumns.YES_VALUE.ordinal()));
		assertEquals("No value updated:", newNoValue, cursor.getInt(SqliteDataControllerConstants.QuestionsColumns.NO_VALUE.ordinal()));
		assertEquals("Used count updated:", newUsedCount, cursor.getInt(SqliteDataControllerConstants.QuestionsColumns.USED_COUNT.ordinal()));

		// Remove the test entry from the database
		this.db.execSQL("DELETE FROM " + table + " WHERE _id = " + idOfInsertedQuestion);
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#getNonSystemTableNames(android.database.sqlite.SQLiteDatabase)}
	 * .
	 */
	public void testGetAllTableNames () {

		// Create a few test tables
		SqliteDataControllerQueries.createQuestionsTable(this.db, "test1");
		SqliteDataControllerQueries.createQuestionsTable(this.db, "test2");
		SqliteDataControllerQueries.createQuestionsTable(this.db, "test3");

		// Obtain the names of the database tables
		List<String> tables = SqliteDataControllerQueries.getNonSystemTableNames(this.db);

		// Ensure the names of the tables are correct
		assertTrue("Correct number of tables:", tables.size() >= 3);
		assertTrue("Table 1:", tables.contains("test1"));
		assertTrue("Table 2:", tables.contains("test2"));
		assertTrue("Table 3:", tables.contains("test3"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#isTableExists(android.database.sqlite.SQLiteDatabase)}
	 * .
	 */
	public void testIsTableExists () {

		// Ensure the table does not yet exist
		assertFalse("Table not created:", SqliteDataControllerQueries.isTableExists(this.db, "test"));

		// Create the table
		SqliteDataControllerQueries.createQuestionsTable(this.db, "test");

		// Ensure the table now exists
		assertTrue("Table created:", SqliteDataControllerQueries.isTableExists(this.db, "test"));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#createQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String)}
	 * .
	 * <p/>
	 * Ensures that a table (formatted for questions) can be properly written to
	 * the database. This test case creates a questions table, and then removes
	 * the table once the test has been completed.
	 */
	public void testCreateQuestionsTableQuery () {

		// The name of the test database table
		String table = "testTableRememberToRemove";

		// Create the database table
		SqliteDataControllerQueries.createQuestionsTable(this.db, table);

		// Check if the table was created
		Cursor cursor = this.db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type=? AND name=?", new String[] { "table", table });
		cursor.moveToFirst();
		int tablesFound = cursor.getInt(0);
		cursor.close();

		// Ensure one table was found
		assertEquals("Table was created:", 1, tablesFound);

		// Clean up: delete the table
		this.db.execSQL("DROP TABLE IF EXISTS '" + table + "'");
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)}
	 * .
	 */
	public void testInsertIntoGeneralQuestionsTable () throws Exception {
		this.helperInsertIntoQuestionsTable(GeneralQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)
	 * .
	 */
	public void testInsertIntoEngineeringQuestionsTable () throws Exception {
		this.helperInsertIntoQuestionsTable(EngineeringQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)}
	 * .
	 */
	public void testInsertIntoPilotQuestionsTable () throws Exception {
		this.helperInsertIntoQuestionsTable(PilotQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)}
	 * .
	 */
	public void testUpdateGeneralQuestion () throws Exception {
		this.helperUpdateQuestionTable(GeneralQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)}
	 * .
	 */
	public void testUpdateEngineeringQuestion () throws Exception {
		this.helperUpdateQuestionTable(EngineeringQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobile.eagleswag.domain.Question)}
	 * .
	 */
	public void testUpdatePilotQuestion () throws Exception {
		this.helperUpdateQuestionTable(PilotQuestion.class);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#insertIntoScoreTable(android.database.sqlite.SQLiteDatabase, String, String)}
	 * .
	 */
	public void testInsertScore () {

		// Insert a test score into the database
		Score score = new Score(10);
		score.setTimestamp(0);
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", score);

		// Obtain the score that was just placed in the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + "" + " WHERE " + SqliteDataControllerConstants.ScoresColumns.SCORE + " = ?",
			new String[] { "10.0" });

		// Ensure the data is correct
		cursor.moveToFirst();
		assertEquals("Correct score:", 10.0, cursor.getDouble(SqliteDataControllerConstants.ScoresColumns.SCORE.ordinal()));
		assertEquals("Correct type:", "test", cursor.getString(SqliteDataControllerConstants.ScoresColumns.TYPE.ordinal()));
		assertEquals("Correct timestamp:", 0, cursor.getLong(SqliteDataControllerConstants.ScoresColumns.TIMESTAMP.ordinal()));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#getTotalScore(android.database.sqlite.SQLiteDatabase, String)}
	 * .
	 */
	public void testGetTotalScore () {

		// Insert a few test scores into the database
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(0));
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(100));
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(50));

		// Obtain the total score for the entries placed in the database
		int totalScore = SqliteDataControllerQueries.getTotalScore(this.db, "test");

		// Ensure the total score is correct
		assertEquals("Total score is correct:", 150, totalScore);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataControllerQueries#getNumberOfScores(android.database.sqlite.SQLiteDatabase, String)}
	 * .
	 */
	public void testGetNumberOfScores () {

		// Insert a few test scores into the database
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(0));
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(100));
		SqliteDataControllerQueries.insertIntoScoreTable(this.db, "test", new Score(50));

		// Obtain the number of score entries in the database
		long entries = SqliteDataControllerQueries.getNumberOfScores(this.db, "test");

		// Ensure the number of scores is correct
		assertEquals("Number of scores is correct:", 3, entries);
	}

}
/**
 * @author Justin Albano
 * @date May 19, 2013
 * @file SQLiteDataControllerQueriesTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.test.persistence.sqlite;

import java.lang.reflect.Constructor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataController;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerConstants;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerHelper;
import com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries;

public class SQLiteDataControllerQueriesTest extends InstrumentationTestCase {
	
	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The database under test.
	 */
	private SQLiteDatabase db;
	
	/**
	 * Data controller used to test the update query.
	 */
	private SQLiteDataController controller;
	
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

		// Obtain a reference to the database
		SQLiteDataControllerHelper helper = new SQLiteDataControllerHelper(this.getInstrumentation().getTargetContext());
		this.db = helper.getWritableDatabase();
		
		// Setup the data controller
		this.controller = new SQLiteDataController();
		this.controller.open(this.getInstrumentation().getTargetContext());
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
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#createQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String)}
	 * 
	 * Ensures that a table (formatted for questions) can be properly written to
	 * the database. This test case creates a questions table, and then removes
	 * the table once the test has been completed.
	 */
	public void testCreateDatabaseQuery () {
		
		// The name of the test database table
		String table = "testTableRememberToRemove";
		
		// Create the database table
		SQLiteDataControllerQueries.createQuestionsTable(this.db, table);
		
		// Check if the table was created
		Cursor cursor = this.db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type=? AND name=?", new String[] {"table", table});
		cursor.moveToFirst();
		int tablesFound = cursor.getInt(0);
		cursor.close();
		
		// Ensure one table was found
		assertEquals("Table was created:", 1, tablesFound);
		
		// Clean up: delete the table
		this.db.execSQL("DROP TABLE IF EXISTS '" + table + "'");
	}
	
	/**
	 * A helper method that provides a framework for testing the update of each
	 * question type. In order to customize this helper method for each type of
	 * question, the class must be provided as both a key and a generic
	 * parameter.
	 * 
	 * @param table
	 *            The table to update.
	 * @param questionClass
	 *            The class to use as the questions to insert into the database.
	 *            For example, if a general question should be inserted into the
	 *            database (for the general test case), GeneralQuestion.class
	 *            should be supplied.
	 * @throws Exception
	 */
	public <T extends Question> void helperUpdateQuestionTable (String table, Class<T> questionClass) throws Exception {
		
		// Parameters of old and new questions
		String originalText = "A test question";
		int originalYesValue = 10;
		int originalNoValue = 0;
		int originalUsedCount = 0;
		String newText = "A new text question";
		int newYesValue = 0;
		int newNoValue = 10;
		int newUsedCount = 3;
		
		// Create a new question to insert into the database (using reflection)
		Class<?>[] argTypes = new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class };
		Constructor<T> constructor = questionClass.getDeclaredConstructor(argTypes);
		Object[] originalArgs = new Object[] { 0, originalText, originalYesValue, originalNoValue, originalUsedCount };
		T originalQuestion = constructor.newInstance(originalArgs);
		
		// Add the new question to the database
		SQLiteDataControllerQueries.insertIntoQuestionsTable(this.db, table, originalQuestion);
	
		// Obtain the ID of the question that was just inserted; this is used to
		// ensure that the new question has the same ID as the question that was
		// just created, ensuring that the new question updates the question
		// that was just inserted
		Cursor cursorId = this.db.rawQuery("SELECT * FROM " + table + " WHERE " + SQLiteDataControllerConstants.QUESTION_COLUMN + " = '" + originalText + "'", null);
		cursorId.moveToFirst();
		int idOfInsertedQuestion = cursorId.getInt(SQLiteDataControllerConstants.Columns.ID.ordinal());
		
		// Create a new question (using reflection)
		Object[] newArgs = new Object[] { idOfInsertedQuestion, newText, newYesValue, newNoValue, newUsedCount };
		T newQuestion = constructor.newInstance(newArgs);
		
		// Store the update in the database
		SQLiteDataControllerQueries.updateQuestion(this.db, table, newQuestion);
		
		// Retrieve the same question from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + table + " WHERE " + SQLiteDataControllerConstants.ID_COLUMN + " = ?", new String[] {"" + idOfInsertedQuestion});
		cursor.moveToFirst();
		
		assertEquals("Question string updated:", newText, cursor.getString(SQLiteDataControllerConstants.Columns.QUESTION.ordinal()));
		assertEquals("Yes value updated:", newYesValue, cursor.getInt(SQLiteDataControllerConstants.Columns.YES_VALUE.ordinal()));
		assertEquals("No value updated:", newNoValue, cursor.getInt(SQLiteDataControllerConstants.Columns.NO_VALUE.ordinal()));
		assertEquals("Used count updated:", newUsedCount, cursor.getInt(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal()));
	
		// Remove the test entry from the database
		this.db.execSQL("DELETE FROM " + table + " WHERE _id = " + idOfInsertedQuestion);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 * .
	 */
	public void testInsertIntoGeneralQuestionsTable () {

		// Question name
		String questionName = "[testing] A test question";
		int yesVal = 10;
		int noVal = 5;
		int usedCount = 2;

		// Create a test general question
		GeneralQuestion testQuestion = new GeneralQuestion(0, questionName, yesVal, noVal, usedCount);

		// Insert the test question into the data base
		long id = SQLiteDataControllerQueries.insertIntoQuestionsTable(this.db, SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE, testQuestion);

		// Log the ID of the newly created row
		Log.d(this.getClass().getName(), "ID of added general quetion: " + id);

		// Attempt to retrieve the data from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE + "" + " WHERE " + SQLiteDataControllerConstants.QUESTION_COLUMN + " = ?", new String[] { questionName });

		// Reset cursor
		cursor.moveToFirst();

		// Log the ID of the retrieved
		Log.d(this.getClass().getName(), "ID of retrieved general question: " + cursor.getString(0));

		// Iterate through the fields
		assertEquals("Question text is equal", questionName, cursor.getString(1));
		assertEquals("Yes point value is equal", yesVal, cursor.getLong(2));
		assertEquals("No point value is equal", noVal, cursor.getLong(3));
		assertEquals("Used count is equal", usedCount, cursor.getLong(4));

		// Remove the item from the database
		this.db.delete(SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE, SQLiteDataControllerConstants.QUESTION_COLUMN + "='" + questionName + "'", null);

		// Close the cursor
		cursor.close();
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 * .
	 */
	public void testInsertIntoEngineeringQuestionsTable () {

		// Question name
		String questionName = "[testing] A test question";
		int yesVal = 7;
		int noVal = 10;
		int usedCount = 18;

		// Create a test general question
		EngineeringQuestion testQuestion = new EngineeringQuestion(0, questionName, yesVal, noVal, usedCount);

		// Insert the test question into the data base
		long id = SQLiteDataControllerQueries.insertIntoQuestionsTable(this.db, SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE, testQuestion);

		// Log the ID of the newly created row
		Log.d(this.getClass().getName(), "ID of added engineering quetion: " + id);

		// Attempt to retrieve the data from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE + "" + " WHERE " + SQLiteDataControllerConstants.QUESTION_COLUMN + " = ?", new String[] { questionName });

		// Reset cursor
		cursor.moveToFirst();

		// Log the ID of the retrieved
		Log.d(this.getClass().getName(), "ID of retrieved engineering question: " + cursor.getString(0));

		// Iterate through the fields
		assertEquals("Question text is equal", questionName, cursor.getString(1));
		assertEquals("Yes point value is equal", yesVal, cursor.getLong(2));
		assertEquals("No point value is equal", noVal, cursor.getLong(3));
		assertEquals("Used count is equal", usedCount, cursor.getLong(4));

		// Remove the item from the database
		this.db.delete(SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE, SQLiteDataControllerConstants.QUESTION_COLUMN + "='" + questionName + "'", null);

		// Close the cursor
		cursor.close();
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#insertIntoQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 * .
	 */
	public void testInsertIntoPilotQuestionsTable () {

		// Question name
		String questionName = "[testing] A test question";
		int yesVal = 10;
		int noVal = 2;
		int usedCount = 32;

		// Create a test general question
		PilotQuestion testQuestion = new PilotQuestion(0, questionName, yesVal, noVal, usedCount);

		// Insert the test question into the data base
		long id = SQLiteDataControllerQueries.insertIntoQuestionsTable(this.db, SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE, testQuestion);

		// Log the ID of the newly created row
		Log.d(this.getClass().getName(), "ID of added pilot quetion: " + id);

		// Attempt to retrieve the data from the database
		Cursor cursor = this.db.rawQuery("SELECT * FROM " + SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE + "" + " WHERE " + SQLiteDataControllerConstants.QUESTION_COLUMN + " = ?", new String[] { questionName });

		// Reset cursor
		cursor.moveToFirst();

		// Log the ID of the retrieved
		Log.d(this.getClass().getName(), "ID of retrieved pilot question: " + cursor.getString(0));

		// Iterate through the fields
		assertEquals("Question text is equal", questionName, cursor.getString(1));
		assertEquals("Yes point value is equal", yesVal, cursor.getLong(2));
		assertEquals("No point value is equal", noVal, cursor.getLong(3));
		assertEquals("Used count is equal", usedCount, cursor.getLong(4));

		// Remove the item from the database
		this.db.delete(SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE, SQLiteDataControllerConstants.QUESTION_COLUMN + "='" + questionName + "'", null);

		// Close the cursor
		cursor.close();
	}
	
	/**
	 * Test method for
	 *
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 */
	public void testUpdateGeneralQuestion () throws Exception {
		this.<GeneralQuestion>helperUpdateQuestionTable(SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE, GeneralQuestion.class);
	}
	
	/**
	 * Test method for
	 *
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 */
	public void testUpdateEngineeringQuestion () throws Exception {
		this.<EngineeringQuestion>helperUpdateQuestionTable(SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE, EngineeringQuestion.class);
	}
	
	/**
	 * Test method for
	 *
	 * {@link com.oceans7.mobileapps.eagleswag.persistence.sqlite.SQLiteDataControllerQueries#updateQuestionsTable(android.database.sqlite.SQLiteDatabase, java.lang.String, com.oceans7.mobileapps.eagleswag.domain.Question)}
	 */
	public void testUpdatePilotQuestion () throws Exception {
		this.<PilotQuestion>helperUpdateQuestionTable(SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE, PilotQuestion.class);
	}
}

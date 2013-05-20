/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerConstants.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A lookup dictionary for queries and table/column names to be used in
 *       the SQLiteDataController.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;


public class SQLiteDataControllerConstants {

	/***************************************************************************
	 * Static Attributes
	 **************************************************************************/

	public static final String DATABASE_NAME = "eagleswag.db";
	public static final int DATABASE_VERSION = 8;

	public static final String GENERAL_QUESTIONS_TABLE = "GeneralQuestions";
	public static final String ENGINEERING_QUESTIONS_TABLE = "EngineeringQuestions";
	public static final String PILOT_QUESTIONS_TABLE = "PilotQuestions";
	public static final String[] TABLES = { GENERAL_QUESTIONS_TABLE, ENGINEERING_QUESTIONS_TABLE, PILOT_QUESTIONS_TABLE };

	public static enum Columns {ID, QUESTION, YES_VALUE, NO_VALUE, USED_COUNT};

	public static final String ID_COLUMN = "_id";
	public static final String QUESTION_COLUMN = "question";
	public static final String YES_VALUE_COLUMN = "yesValue";
	public static final String NO_VALUE_COLUMN = "noValue";
	public static final String USED_COUNT_COLUMN = "usedCount";

	public static final String CREATE_DATABASE_QUERY = "CREATE DATABASE " + DATABASE_NAME;

	/***************************************************************************
	 * Static Methods
	 **************************************************************************/

	/**
	 * Obtains a query for the creation of a questions table.
	 * 
	 * @param tableName
	 *            The name of the questions table.
	 * @return
	 *         A query string to create a questions table.
	 */
	//public static String getCreateQuestionsTableQuery (String tableName) {
		//return "CREATE TABLE " + tableName + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTION_COLUMN + " TEXT NOT NULL," + YES_VALUE_COLUMN + " INTEGER NOT NULL," + NO_VALUE_COLUMN + " INTEGER NOT NULL," + USED_COUNT_COLUMN + " INTEGER NOT NULL" + ");";
	//}

	/**
	 * Obtains a query string for the least frequently used questions
	 * (the number of returned questions is based on the number supplied).
	 * 
	 * @param number
	 *            The number of questions to obtain.
	 * @return
	 *         The a query string to obtain last 'number' least frequently used
	 *         questions from a questions table.
	 */
	//public static String getLFUQuestionsQuery (String table, int number) {
		//return "SELECT * FROM " + table + " " + "ORDER BY " + USED_COUNT_COLUMN + " ASC " + "LIMIT " + number;
	//}

	/**
	 * Insert a question into one of the specified question tables. Insertion is
	 * completed using the Android SQLite API and logged using the standard
	 * logging mechanism.
	 * 
	 * @param db
	 *            The database to insert the question into.
	 * @param table
	 *            The question table to insert the question into.
	 * @param question
	 *            The question to insert into the database at the given table.
	 * @return
	 *         The ID of the newly inserted question.
	 */
	/*public static long insertIntoQuestionsTable (SQLiteDatabase db, String table, Question question) {

		// Extract the JSON values
		String text = question.getQuestionString();
		String yesValue = "" + question.getYesPointValue();
		String noValue = "" + question.getNoPointValue();
		String usedCount = "" + question.getUsedCount();

		// Create a column-value map for the insertion
		ContentValues values = new ContentValues();
		values.put(QUESTION_COLUMN, text);
		values.put(YES_VALUE_COLUMN, yesValue);
		values.put(NO_VALUE_COLUMN, noValue);
		values.put(USED_COUNT_COLUMN, usedCount);

		// Add the questions to the database
		long id = db.insert(table, null, values);

		Log.i(SQLiteDataControllerConstants.class.getName(),
			"Inserting question into table '" + table + "':" + 
			"[" + ID_COLUMN + ": " + id + "] " + 
			"[" + QUESTION_COLUMN + ": " + text + "] " + 
			"[" + YES_VALUE_COLUMN + ": " + yesValue + "] " + 
			"[" + NO_VALUE_COLUMN + ": " + noValue + "]" + 
			"[" + USED_COUNT_COLUMN + ": " + usedCount + "]");

		return id;
	}*/
}

/**
 * @author Justin Albano
 * @date May 20, 2013
 * @file SqliteDataControllerQueries.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A catalog of queries for the SQLite database. This catalog contains
 *       static methods for creating tables, storing data in tables, retrieving
 *       data from tables, etc. This catalog has been created in order to
 *       encapsulate the queries requires to perform common tasks in a single
 *       location.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class SqliteDataControllerQueries {

	/**
	 * Creates a new questions table in the database supplied. The table
	 * provided specifies the name of the newly created table.
	 * 
	 * @param db
	 *            The database to create the new table in.
	 * @param table
	 *            The name of the questions table.
	 * @return
	 *         A query string to create a questions table.
	 */
	public static void createQuestionsTable (SQLiteDatabase db, String table) {

		// The query used to create the table
		String query = "CREATE TABLE IF NOT EXISTS " + table + " (" + SqliteDataControllerConstants.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + SqliteDataControllerConstants.QUESTION_COLUMN + " TEXT NOT NULL," + SqliteDataControllerConstants.YES_VALUE_COLUMN + " INTEGER NOT NULL," + SqliteDataControllerConstants.NO_VALUE_COLUMN + " INTEGER NOT NULL," + SqliteDataControllerConstants.USED_COUNT_COLUMN + " INTEGER NOT NULL" + ");";

		try {
			// Execute the SQL command on the database
			db.execSQL(query);
		}
		catch (SQLException e) {
			// An SQL exception occurred while trying to create the database
			// table
			Log.e(SqliteDataControllerQueries.class.getName(),
				"An error occurred while attempting to create the table '" + table + "' in the database: " + e);
		}

		// Log the creation of the database table
		Log.i(SqliteDataControllerQueries.class.getName(), "Created table '" + table + "' in database '" + db + "' using the query: " + query);
	}

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
	public static long insertIntoQuestionsTable (SQLiteDatabase db, String table, Question question) {

		// Extract the JSON values
		String text = question.getQuestionString();
		String yesValue = "" + question.getYesPointValue();
		String noValue = "" + question.getNoPointValue();
		String usedCount = "" + question.getUsedCount();

		// Create a column-value map for the insertion
		ContentValues values = new ContentValues();
		values.put(SqliteDataControllerConstants.QUESTION_COLUMN, text);
		values.put(SqliteDataControllerConstants.YES_VALUE_COLUMN, yesValue);
		values.put(SqliteDataControllerConstants.NO_VALUE_COLUMN, noValue);
		values.put(SqliteDataControllerConstants.USED_COUNT_COLUMN, usedCount);

		// Add the questions to the database
		long id = db.insert(table, null, values);

		Log.i(
			SqliteDataControllerConstants.class.getName(),
			"Inserting question into table '" + table + "':" + "[" + SqliteDataControllerConstants.ID_COLUMN + ": " + id + "] " + "[" + SqliteDataControllerConstants.QUESTION_COLUMN + ": " + text + "] " + "[" + SqliteDataControllerConstants.YES_VALUE_COLUMN + ": " + yesValue + "] " + "[" + SqliteDataControllerConstants.NO_VALUE_COLUMN + ": " + noValue + "]" + "[" + SqliteDataControllerConstants.USED_COUNT_COLUMN + ": " + usedCount + "]");

		return id;
	}

	/**
	 * Obtains a specified number of questions from a table within a given
	 * database. The strategy used to obtain questions is dictated by the SQLite
	 * configuration file.
	 * 
	 * @param db
	 *            The database to extract the data from.
	 * @param table
	 *            The table to extract the questions from.
	 * @param number
	 *            The number of questions to retrieve.
	 * @return
	 *         A cursor containing the data retrieved from the specified
	 *         database table.
	 */
	public static Cursor getQuestions (Context context, SQLiteDatabase db, String table, int number) {

		// Obtain the strategy from factory for getting questions
		RetrieveQuestionsStrategy strategy = RetrieveQuestionsStrategyFactory.getInstance().getRetrieveQuestionsStrategy(context);

		// The query to obtain the questions
		String query = strategy.getQuery(table, number);

		// Execute the query against the database
		Cursor cursor = db.rawQuery(query, null);

		// Log the execution of the select query
		Log.i(SqliteDataControllerQueries.class.getName(), "Retrieved questions from table '" + table + "' using the query:" + query);

		return cursor;
	}

	/**
	 * Updates a question in the database, based on the table supplied.
	 * 
	 * @param db
	 *            The database to execute the update on.
	 * @param table
	 *            The table to update.
	 * @param question
	 *            The question object containing the data to be updated.
	 */
	public static void updateQuestion (SQLiteDatabase db, String table, Question question) {

		// Create the mapping of values for the question
		ContentValues content = new ContentValues();
		content.put(SqliteDataControllerConstants.QUESTION_COLUMN, question.getQuestionString());
		content.put(SqliteDataControllerConstants.YES_VALUE_COLUMN, question.getYesPointValue());
		content.put(SqliteDataControllerConstants.NO_VALUE_COLUMN, question.getNoPointValue());
		content.put(SqliteDataControllerConstants.USED_COUNT_COLUMN, question.getUsedCount());

		// Update the database
		db.update(table, content, SqliteDataControllerConstants.ID_COLUMN + " = ?", new String[] { "" + question.getId() });

		// Log the update
		Log.i(SqliteDataControllerQueries.class.getName(),
			"Updated question with id [" + question.getId() + "] in table '" + table + "' : " + content);
	}
}

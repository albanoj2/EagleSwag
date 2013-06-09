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
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Score;

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
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS " + table + " (");
		builder.append(SqliteDataControllerConstants.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		builder.append(SqliteDataControllerConstants.QUESTION_COLUMN + " TEXT NOT NULL,");
		builder.append(SqliteDataControllerConstants.YES_VALUE_COLUMN + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.NO_VALUE_COLUMN + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.USED_COUNT_COLUMN + " INTEGER NOT NULL");
		builder.append(");");

		try {
			// Execute the SQL command on the database
			db.execSQL(builder.toString());
		}
		catch (SQLException e) {
			// An SQL exception occurred while trying to create the database
			// table
			Log.e(SqliteDataControllerQueries.class.getName(),
				"An error occurred while attempting to create the table '" + table + "' in the database: " + e);
		}

		// Log the creation of the database table
		Log.i(SqliteDataControllerQueries.class.getName(), "Created table '" + table + "' in database '" + db + "' using the query: " + builder);
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

		// Build the SQL statement
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO " + table + " (");
		builder.append(SqliteDataControllerConstants.QUESTION_COLUMN + ", ");
		builder.append(SqliteDataControllerConstants.YES_VALUE_COLUMN + ", ");
		builder.append(SqliteDataControllerConstants.NO_VALUE_COLUMN + ", ");
		builder.append(SqliteDataControllerConstants.USED_COUNT_COLUMN + " ");
		builder.append(") VALUES (?,?,?,?)");

		// Create a compiled SQL statement
		SQLiteStatement statement = db.compileStatement(builder.toString());

		// Bind the values to the statement
		statement.bindString(1, question.getQuestionString());
		statement.bindLong(2, question.getYesPointValue());
		statement.bindLong(3, question.getNoPointValue());
		statement.bindLong(4, question.getUsedCount());

		// Execute the statement
		long id = statement.executeInsert();

		Log.i(SqliteDataControllerConstants.class.getName(),
			"Inserting question into '" + table + "' where id -> " + id + ": " + builder + " -> (" + question.getQuestionString() + ", " + question.getYesPointValue() + ", " + question.getNoPointValue() + ", " + question.getUsedCount() + ")");

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

	/**
	 * TODO Documentation
	 * 
	 * @param db
	 */
	public static void createScoreTable (SQLiteDatabase db) {

		// The query used to create the table
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS " + SqliteDataControllerConstants.SCORE_TABLE_NAME + " (");
		builder.append(SqliteDataControllerConstants.SCORE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		builder.append(SqliteDataControllerConstants.SCORE_SCORE_COLUMN + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.SCORE_TIMESTAMP_COLUMN + " BIGINT NOT NULL,");
		builder.append(SqliteDataControllerConstants.SCORE_TYPE_COLUMN + " TEXT NOT NULL");
		builder.append(");");

		try {
			// Execute the SQL command on the database
			db.execSQL(builder.toString());
		}
		catch (SQLException e) {
			// An SQL exception occurred while trying to create database table
			Log.e(SqliteDataControllerQueries.class.getName(), "An error occurred while attempting to create the scores table in the database: " + e);
		}

		// Log the creation of the database table
		Log.i(SqliteDataControllerQueries.class.getName(), "Created scores table in database '" + db + "' using the query: " + builder);

	}

	/**
	 * TODO Complete documentation
	 * 
	 * @param db
	 * @param type
	 * @param score
	 * @return
	 */
	public static long insertIntoScoreTable (SQLiteDatabase db, String type, Score score) {

		// Build the SQL statement
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO " + SqliteDataControllerConstants.SCORE_TABLE_NAME + " (");
		builder.append(SqliteDataControllerConstants.SCORE_SCORE_COLUMN + ", ");
		builder.append(SqliteDataControllerConstants.SCORE_TIMESTAMP_COLUMN + ", ");
		builder.append(SqliteDataControllerConstants.SCORE_TYPE_COLUMN + " ");
		builder.append(") VALUES (?,?,?)");

		// Create a compiled SQL statement
		SQLiteStatement statement = db.compileStatement(builder.toString());

		// Bind the values to the statement
		statement.bindLong(1, score.getScore());
		statement.bindLong(2, score.getTimestamp());
		statement.bindString(3, type);

		// Execute the statement
		long id = statement.executeInsert();

		// Log the insertion
		Log.i(SqliteDataControllerConstants.class.getName(),
			"Inserting score into '" + SqliteDataControllerConstants.SCORE_TABLE_NAME + "' where id -> " + id + ": " + builder + " -> (" + score.getScore() + ", " + score.getTimestamp() + ", " + type + ")");

		return id;
	}

	/**
	 * TODO Complete method
	 * 
	 * @param db
	 * @param type
	 * @return
	 */
	public static int getTotalScore (SQLiteDatabase db, String type) {

		if (db == null) {
			// The database was not set
			Log.e(SqliteDataControllerQueries.class.getName(), "Database is null");
		}

		// Obtain the sum of the scores for a type
		String query = "SELECT SUM(" + SqliteDataControllerConstants.SCORE_SCORE_COLUMN + ") FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + " WHERE type = ?";
		Cursor cursor = db.rawQuery(query, new String[] { type });
		
		// Obtained the sum data from the database
		Log.i(SqliteDataControllerQueries.class.getName(), "Obtained sum of scores using the query: " + query);

		// Default value
		int totalScore = 0;

		if (cursor.moveToFirst()) {
			// Reassign the value if one is present
			totalScore = cursor.getInt(0);
			cursor.close();
		}

		return totalScore;
	}

	/**
	 * TODO Complete method
	 * 
	 * @param db
	 * @param type
	 * @return
	 */
	public static long getNumberOfScores (SQLiteDatabase db, String type) {
		return DatabaseUtils.queryNumEntries(db, SqliteDataControllerConstants.SCORE_TABLE_NAME, "type=?", new String[] { type });
	}
}

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

/**
 * A catalog of queries for the SQLite database. This catalog contains static
 * methods for creating tables, storing data in tables, retrieving data from
 * tables, etc. This catalog has been created in order to encapsulate the
 * queries requires to perform common tasks in a single location.
 * 
 * @author Justin Albano
 */
public class SqliteDataControllerQueries {

	/***************************************************************************
	 * Static Methods
	 **************************************************************************/

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
	 * Creates a SQLite database table to store scores.
	 * 
	 * @param db
	 *            The database to in which to create the scores table.
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
	 * Inserts a score into the scores table in the SQLite database.
	 * 
	 * @param db
	 *            The database to add the score to.
	 * @param key
	 *            The key that associates the score with a type of round (a
	 *            round for an engineer, for example).
	 * @param score
	 *            The score object to store in the SQLite database.
	 * @return
	 *         The ID of the newly stored score object.
	 */
	public static long insertIntoScoreTable (SQLiteDatabase db, String key, Score score) {

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
		statement.bindString(3, key);

		// Execute the statement
		long id = statement.executeInsert();

		// Log the insertion
		Log.i(SqliteDataControllerConstants.class.getName(),
			"Inserting score into '" + SqliteDataControllerConstants.SCORE_TABLE_NAME + "' where id -> " + id + ": " + builder + " -> (" + score.getScore() + ", " + score.getTimestamp() + ", " + key + ")");

		return id;
	}

	/**
	 * Obtains the total score for all scores associated with a key.
	 * 
	 * @param db
	 *            The database storing the scores.
	 * @param key
	 *            The key associate with the scores for which the total is being
	 *            calculated.
	 * @return
	 *         The total score for all scores associated with the provided key.
	 */
	public static int getTotalScore (SQLiteDatabase db, String key) {

		if (db == null) {
			// The database was not set
			Log.e(SqliteDataControllerQueries.class.getName(), "Database is null");
		}

		// Obtain the sum of the scores for a type
		String query = "SELECT SUM(" + SqliteDataControllerConstants.SCORE_SCORE_COLUMN + ") FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + " WHERE type = ?";
		Cursor cursor = db.rawQuery(query, new String[] { key });

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
	 * Obtains the number of scores associated with the provided key in the
	 * provided database.
	 * 
	 * @param db
	 *            The database to obtain the scores from.
	 * @param key
	 *            The key associated with the scores for which the number of
	 *            elements are being counted.
	 * @return
	 *         The number of scores associated with the provided key in the
	 *         provided database.
	 */
	public static long getNumberOfScores (SQLiteDatabase db, String key) {
		return DatabaseUtils.queryNumEntries(db, SqliteDataControllerConstants.SCORE_TABLE_NAME, "type=?", new String[] { key });
	}
}

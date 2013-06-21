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

package com.oceans7.mobile.eagleswag.persistence.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.Score;

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
	 * Obtains the names of all the tables in the database provided.
	 * Citation: http://stackoverflow.com/a/15384267/2403253
	 * 
	 * @param db
	 *            The database to search for tables.
	 * @return
	 *         The names of the all the tables in the database.
	 */
	public static List<String> getNonSystemTableNames (SQLiteDatabase db) {

		// Stub to store the table names in
		List<String> tables = new ArrayList<String>();

		// Obtain the cursor for the names of the tables
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

		if (c.moveToFirst()) {
			// The cursor is iterable
			String name;

			while (!c.isAfterLast()) {
				name = c.getString(0);

				if (!name.equals("android_metadata") && !name.equals("sqlite_sequence")) {
					// Add the table name to the list
					tables.add(c.getString(0));
				}

				c.moveToNext();
			}
		}

		Log.e("TEST", tables.toString());

		return tables;
	}

	/**
	 * Queries if a table exists in the database.
	 * Citation: http://stackoverflow.com/a/8827554/2403253
	 * 
	 * @param db
	 *            The database to query for the table.
	 * @param tableName
	 *            The name of the table to find in the database.
	 * @return
	 *         True if the table exists; false otherwise.
	 */
	public static boolean isTableExists (SQLiteDatabase db, String tableName) {

		if (tableName == null || db == null || !db.isOpen()) {
			// Table name or database is null, or database is not open
			return false;
		}

		// A cursor from the query to the database
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] { "table", tableName });

		if (!cursor.moveToFirst()) {
			// If the cursor is not iterable
			return false;
		}

		// Obtain the data from the cursor
		int count = cursor.getInt(0);
		cursor.close();
		return count > 0;
	}

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
		builder.append(SqliteDataControllerConstants.QuestionsColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.QUESTION + " TEXT NOT NULL,");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.YES_VALUE + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.NO_VALUE + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.USED_COUNT + " INTEGER NOT NULL");
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
		builder.append(SqliteDataControllerConstants.QuestionsColumns.QUESTION + ", ");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.YES_VALUE + ", ");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.NO_VALUE + ", ");
		builder.append(SqliteDataControllerConstants.QuestionsColumns.USED_COUNT + " ");
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
		RetrievalStrategy strategy = RetrievalStrategies.getInstance().getRetrieveQuestionsStrategy();

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
		content.put(SqliteDataControllerConstants.QuestionsColumns.QUESTION.toString(), question.getQuestionString());
		content.put(SqliteDataControllerConstants.QuestionsColumns.YES_VALUE.toString(), question.getYesPointValue());
		content.put(SqliteDataControllerConstants.QuestionsColumns.NO_VALUE.toString(), question.getNoPointValue());
		content.put(SqliteDataControllerConstants.QuestionsColumns.USED_COUNT.toString(), question.getUsedCount());

		// Update the database
		db.update(table, content, SqliteDataControllerConstants.QuestionsColumns.ID + " = ?", new String[] { "" + question.getId() });

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
		builder.append(SqliteDataControllerConstants.ScoresColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		builder.append(SqliteDataControllerConstants.ScoresColumns.SCORE + " INTEGER NOT NULL,");
		builder.append(SqliteDataControllerConstants.ScoresColumns.TIMESTAMP + " BIGINT NOT NULL,");
		builder.append(SqliteDataControllerConstants.ScoresColumns.TYPE + " TEXT NOT NULL");
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
		builder.append(SqliteDataControllerConstants.ScoresColumns.SCORE + ", ");
		builder.append(SqliteDataControllerConstants.ScoresColumns.TIMESTAMP + ", ");
		builder.append(SqliteDataControllerConstants.ScoresColumns.TYPE + " ");
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
		String query = "SELECT SUM(" + SqliteDataControllerConstants.ScoresColumns.SCORE + ") FROM " + SqliteDataControllerConstants.SCORE_TABLE_NAME + " WHERE type = ?";
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

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

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A helper class used to create, update, and management the SQLite database.
 * This helper class is an implementation of the SQLiteOpenHelper class provided
 * by the SQLite framework for Android. This class deals with updating and
 * crating the SQLite database used by the application. When the version number
 * of the database is incremented in the SqliteDataControllerConstants class,
 * this helper automatically updates the database.
 * 
 * @author Justin Albano
 * 
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class SqliteDataControllerHelper extends SQLiteOpenHelper {

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Creates the helper that is used for aiding in database management.
	 * 
	 * @param context
	 *            The context used for the database helper.
	 */
	public SqliteDataControllerHelper (Context context) {
		super(context, SqliteDataControllerConstants.DATABASE_NAME, null, SqliteDataControllerConstants.DATABASE_VERSION);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * The configuration data from the question type configuration file is used
	 * to specify the name of the table the data for each question type is
	 * placed into. The configuration data is obtained from the configuration
	 * controller and a questions table in the database is created for each
	 * question type.
	 * <p/>
	 * {@inheritDoc}
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate (SQLiteDatabase db) {

		// // Obtain a data file parser and question types from configuration
		// DataFileParserStrategy parser =
		// DataFileParserStrategies.getInstance().getDataFileParserStrategy(this.context);
		// Map<Class<? extends Question>, QuestionType> qtMap =
		// ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);
		//
		// // A list to store the queues of questions (and counter of questions)
		// List<Queue<? extends Question>> questionQueueList = new
		// ArrayList<Queue<? extends Question>>();
		// int totalNumberOfQuestions = 0;
		// int questionsLoaded = 0;
		//
		// // A list of table names for each question type
		// List<String> tables = new ArrayList<String>();
		//
		// for (Class<? extends Question> key : qtMap.keySet()) {
		// // Loop through each of the question types in configuration
		//
		// // Obtain questions from data file parser and add them to the list
		// Queue<? extends Question> questions = parser.getQuestions(key);
		// questionQueueList.add(questions);
		// totalNumberOfQuestions += questions.size();
		//
		// // Obtain the table name for each question type
		// String table = ConfigurationHelper.getInstance().getTableName(key,
		// this.context);
		// tables.add(table);
		// }
		//
		// try {
		// // Begin SQL insertion transaction
		// db.beginTransaction();
		//
		// for (int i = 0; i < questionQueueList.size(); i++) {
		// // Loop through the list and add each queue of questions
		//
		// // Create the database table for this set of questions
		// SqliteDataControllerQueries.createQuestionsTable(db, tables.get(i));
		//
		// for (Question question : questionQueueList.get(i)) {
		// // Loop through the questions within the queue
		//
		// // Insert the question into the database
		// SqliteDataControllerQueries.insertIntoQuestionsTable(db,
		// tables.get(i), question);
		// Log.i(this.getClass().getName(), "Inserted question into the " +
		// tables.get(i) + " table: " + question);
		//
		// // Increment loaded count
		// questionsLoaded++;
		//
		// if (questionsLoaded % UPDATE_THRESHOLD == 0 || questionsLoaded ==
		// totalNumberOfQuestions) {
		// // Notify observers if needed
		// this.updateLoadingListeners(totalNumberOfQuestions, questionsLoaded);
		// }
		// }
		// }
		//
		// // Mark SQL insertion transaction as successfully completed
		// db.setTransactionSuccessful();
		// }
		// catch (SQLException e) {
		// // An exception occurred while trying to create the database
		// Log.e(this.getClass().getName(),
		// "SQL Error while creating the database: " + e);
		// }
		// finally {
		// // End SQL insertion transaction
		// db.endTransaction();
		// }

		// Create scores table
		SqliteDataControllerQueries.createScoreTable(db);

	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      int, int)
	 */
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

		// Warning that the database will be upgraded
		Log.w(this.getClass().getName(), "Database is about to be upgraded [" + oldVersion + " => " + newVersion + "]. All data will lost");

		try {
			// Obtain the list of non-system table names
			List<String> tables = SqliteDataControllerQueries.getNonSystemTableNames(db);

			for (String table : tables) {
				// Drop all non-system tables
				db.delete(table, null, null);
			}

			// Recreate the database
			this.onCreate(db);
		}
		catch (SQLException e) {
			// An exception occurred while dropping database tables
			Log.e(this.getClass().getName(), "Error while dropping tables from the database: " + e);
		}
	}
}

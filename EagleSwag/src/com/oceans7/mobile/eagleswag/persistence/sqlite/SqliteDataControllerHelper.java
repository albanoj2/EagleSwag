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
import java.util.Map;
import java.util.Queue;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oceans7.mobile.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobile.eagleswag.config.QuestionType;
import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategies;
import com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy;
import com.oceans7.mobile.eagleswag.util.LoadingListener;

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
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used to access the data file containing the questions to be
	 * loaded into the database upon startup.
	 */
	private Context context;

	/**
	 * The number of questions inserted into the database before the observer is
	 * notified of the insertion progress. For example, if the threshold is set
	 * to 5, every 5th insert (5, 10, 15, ...) will trigger a notification to
	 * the registered observers.
	 */
	private static final int UPDATE_THRESHOLD = 5;

	/**
	 * The list of observers that are notified when the threshold for loading
	 * questions is met.
	 */
	private ArrayList<LoadingListener> observers;

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

		// Save the context
		this.context = context;
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

		// Obtain a data file parser and question types from configuration
		DataFileParserStrategy parser = DataFileParserStrategies.getInstance().getDataFileParserStrategy(this.context);
		Map<Class<? extends Question>, QuestionType> qtMap = ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);

		// A list to store the queues of questions (and counter of questions)
		List<Queue<? extends Question>> questionQueueList = new ArrayList<Queue<? extends Question>>();
		int totalNumberOfQuestions = 0;
		int questionsLoaded = 0;

		// A list of table names for each question type
		List<String> tables = new ArrayList<String>();

		for (Class<? extends Question> key : qtMap.keySet()) {
			// Loop through each of the question types in configuration

			// Obtain questions from data file parser and add them to the list
			Queue<? extends Question> questions = parser.getQuestions(key);
			questionQueueList.add(questions);
			totalNumberOfQuestions += questions.size();

			// Obtain the table name for each question type
			String table = ConfigurationHelper.getInstance().getTableName(key, this.context);
			tables.add(table);
		}

		try {
			// Begin SQL insertion transaction
			db.beginTransaction();

			for (int i = 0; i < questionQueueList.size(); i++) {
				// Loop through the list and add each queue of questions

				// Create the database table for this set of questions
				SqliteDataControllerQueries.createQuestionsTable(db, tables.get(i));

				for (Question question : questionQueueList.get(i)) {
					// Loop through the questions within the queue

					// Insert the question into the database
					SqliteDataControllerQueries.insertIntoQuestionsTable(db, tables.get(i), question);
					Log.i(this.getClass().getName(), "Inserted question into the " + tables.get(i) + " table: " + question);

					// Increment loaded count
					questionsLoaded++;

					if (questionsLoaded % UPDATE_THRESHOLD == 0 || questionsLoaded == totalNumberOfQuestions) {
						// Notify observers if needed
						this.updateLoadingListeners(totalNumberOfQuestions, questionsLoaded);
					}
				}
			}

			// Mark SQL insertion transaction as successfully completed
			db.setTransactionSuccessful();
		}
		catch (SQLException e) {
			// An exception occurred while trying to create the database
			Log.e(this.getClass().getName(), "SQL Error while creating the database: " + e);
		}
		finally {
			// End SQL insertion transaction
			db.endTransaction();
		}

		// Create scores table
		SqliteDataControllerQueries.createScoreTable(db);

	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      int, int)
	 */
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop all old tables from the database
		Log.w(this.getClass().getName(),
			"Database is about to be updated from version " + oldVersion + " to version " + newVersion + ". All old data will lost");

		try {
			// Obtain the question types from the configuration file
			Map<Class<? extends Question>, QuestionType> qtMap = ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);

			for (Class<? extends Question> key : qtMap.keySet()) {
				// Loop through each of the question type entries and make a
				// table in the database to store each of the entries

				// The SQLite database table that the key maps to
				String table = ConfigurationHelper.getInstance().getTableName(key, this.context);

				db.execSQL("DROP TABLE IF EXISTS " + table);
				Log.w(this.getClass().getName(), "Dropping table '" + table + "' from database");
			}

			// Drop the scores table
			db.execSQL("DROP TABLE IF EXISTS " + SqliteDataControllerConstants.SCORE_TABLE_NAME);

			// Recreate the database
			this.onCreate(db);
		}
		catch (SQLException e) {
			// An exception occurred while dropping tables from the database
			Log.e(this.getClass().getName(), "Error while dropping tables from the database: " + e);
		}
	}

	/**
	 * A helper method that updates the observers with the progress of creating
	 * the database.
	 * 
	 * @param total
	 *            The total number of questions that are being loaded into the
	 *            database.
	 * @param currentNumber
	 *            The number of questions that have already been loaded into the
	 *            database.
	 */
	@SuppressWarnings("unchecked")
	public synchronized void updateLoadingListeners (int total, int currentNumber) {

		// Local copy of the list of loading listeners
		ArrayList<LoadingListener> list;

		synchronized (this) {
			// Return from method if the loading listener list is null
			if (this.observers == null) return;

			// Copy the list of loading listeners to the local copy
			list = (ArrayList<LoadingListener>) this.observers.clone();
		}

		for (LoadingListener listener : list) {
			// Repeat for each of the loading listeners
			listener.update(total, currentNumber);

			// Log the update
			Log.i(this.getClass().getName(), "Notified listener '" + listener + "': " + currentNumber + " of " + total + " questions loaded");
		}
	}

	/**
	 * Adds a loading listener to the helper.
	 * 
	 * @param listener
	 *            The listener to add to the helper.
	 */
	public void addLoadingListener (LoadingListener listener) {

		if (this.observers == null) {
			// Create list of listeners if it has not yet been created
			this.observers = new ArrayList<LoadingListener>();
		}

		if (!this.observers.contains(listener)) {
			// Add the listener to the list if it is not already in the list
			this.observers.add(listener);

			// Log the addition
			Log.i(this.getClass().getName(), "Added listener " + listener);
		}
	}

	/**
	 * Removes a loading listener from the helper.
	 * 
	 * @param listener
	 *            The loading listener to remove from the helper.
	 */
	public void removeLoadingListener (LoadingListener listener) {

		if (this.observers != null && this.observers.contains(listener)) {
			// Remove the listener if the list has been created and contains the
			// listener specified
			this.observers.remove(listener);

			// Log the removal
			Log.i(this.getClass().getName(), "Removed listener " + listener);
		}
	}
}

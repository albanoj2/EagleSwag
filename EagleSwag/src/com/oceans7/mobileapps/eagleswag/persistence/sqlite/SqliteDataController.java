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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.config.QuestionType;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Score;
import com.oceans7.mobileapps.eagleswag.persistence.DataController;

/**
 * A data controller that implements data storage and retrieval using a SQLite
 * database; data is stored using a relational database schema. The
 * SQLiteDataControllerMappingsParser is used to generate the table of mappings
 * from class type (for each question type: General, Engineering, etc.) and the
 * SqliteDataControllerConstants and SqliteDataControllerQueries are combined to
 * encapsulate the SQLite queries, table names, database version, etc. used by
 * the SQLite data controller.
 * <p/>
 * <strong>Note:</strong> all queries are found in the
 * SqliteDataControllerQueries class, and all data controller constants, such as
 * column names, column numbers, etc., are found in the
 * SqliteDataControllerConstants class.
 * 
 * @author Justin Albano
 */
public class SqliteDataController implements DataController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used to provide the get questions query with its needed
	 * arguments.
	 */
	private Context context;

	/**
	 * The writable database object used to retrieve and store data in the
	 * SQLite database.
	 */
	private SQLiteDatabase database;

	/**
	 * Database helper that manages many of the underlying tasks associated with
	 * SQLite database. This helper is used to generate the writable instance of
	 * the database used to store data for this class.
	 */
	private SqliteDataControllerHelper helper;

	/**
	 * A map of question classes to the database table names used. This is used
	 * to map a question type to a table in the database, which allows for the
	 * retrieval and storage of questions.
	 * 
	 * For example, if "GeneralQuestion.class" is used as the key in this map,
	 * the SQLite database table that holds the general questions will be
	 * returned.
	 */
	private Map<Class<? extends Question>, String> classToTableMap;

	/**
	 * Internal cache to speed up the retrieval of score data from the SQLite
	 * database.
	 */
	private ScoreCache cache;

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#open()
	 */
	@Override
	public void open (Context context) {

		// Store the context
		this.context = context;

		// Create the score cache
		this.cache = new ScoreCache();

		// Create the database helper
		this.helper = new SqliteDataControllerHelper(context);

		try {
			// Obtain a writable database reference
			this.database = this.helper.getWritableDatabase();
		}
		catch (SQLException e) {
			// The helper could not create a writable database
			Log.i(this.getClass().getName(), "Writable database cannot be created by SQLite database helper: " + e);
		}

		// Create the class key to database table map
		this.classToTableMap = new HashMap<Class<? extends Question>, String>();

		// Obtain the question types from the configuration file
		Map<Class<? extends Question>, QuestionType> qtMap = ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);

		for (Class<? extends Question> key : qtMap.keySet()) {
			// Loop through each of the entries and enter them into the map: the
			// key from each entry is used as the key in the map, and the SQLite
			// database table name is extracted from the question type and used
			// as the value in the table

			// Get the table name that maps to the key
			String table = ConfigurationHelper.getInstance().getTableName(key, context);

			// Add the entry to the class key to table name map
			this.classToTableMap.put(key, table);
			Log.i(this.getClass().getName(), "Added mapping: " + key.getCanonicalName() + " -> " + table);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#close()
	 */
	@Override
	public void close () {

		if (this.helper != null) {
			// Close the helper if it has been set
			this.helper.close();
		}
	}

	/**
	 * The data for the questions is retrieved from the SQLite database.
	 * The mappings from the question class (for example, GeneralQuestion.class)
	 * to the database table are retrieved from the
	 * SQLiteDataControllerMappingsParser. <br />
	 * <br/ >
	 * 
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getQuestions(java.lang.Class,
	 *      int)
	 * 
	 */
	@Override
	public <T extends Question> Queue<T> getQuestions (Class<T> key, int number) {

		// Create queue of requested question objects
		Queue<T> questions = new LinkedList<T>();

		if (number > 0) {
			// Continue only if there is data to retrieve from the database

			// Obtain the data for the questions from the database
			Cursor cursor = SqliteDataControllerQueries.getQuestions(this.context, this.database, this.classToTableMap.get(key), number);

			// Reset cursor
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				// Loop through the cursor

				// Question data
				int id = cursor.getInt(SqliteDataControllerConstants.QuestionColumns.ID.ordinal());
				String text = cursor.getString(SqliteDataControllerConstants.QuestionColumns.QUESTION.ordinal());
				int yesValue = cursor.getInt(SqliteDataControllerConstants.QuestionColumns.YES_VALUE.ordinal());
				int noValue = cursor.getInt(SqliteDataControllerConstants.QuestionColumns.NO_VALUE.ordinal());
				int usedCount = cursor.getInt(SqliteDataControllerConstants.QuestionColumns.USED_COUNT.ordinal());

				try {
					// Obtain the constructor for the supplied class
					Class<?>[] argTypes = new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class };
					Constructor<T> constructor = key.getDeclaredConstructor(argTypes);
					Object[] args = new Object[] { id, text, yesValue, noValue, usedCount };

					// Invoke the constructor to obtain the object
					T question = constructor.newInstance(args);

					// Add the new question to the queue
					questions.add(question);
					Log.i(this.getClass().getName(), "Added general question to " + key.getCanonicalName() + " queue: " + question);
				}
				catch (IllegalArgumentException e) {
					Log.e(this.getClass().getName(),
						"Illegal argument exception occurred while trying to instantiate new question using reflective method: " + e);
				}
				catch (InstantiationException e) {
					Log.e(this.getClass().getName(),
						"Instantiation exception occurred while trying to instantiate new question using reflective method: " + e);
				}
				catch (IllegalAccessException e) {
					Log.e(this.getClass().getName(),
						"Illegal access exception occurred while trying to instantiate new question using reflective method: " + e);
				}
				catch (InvocationTargetException e) {
					Log.e(this.getClass().getName(),
						"Invokation target exception occurred while trying to instantiate new question using reflective method: " + e);
				}
				catch (NoSuchMethodException e) {
					Log.e(this.getClass().getName(), "The constructor used to create the generic question object cannot be found: " + e);
				}

				// Increment the cursor
				cursor.moveToNext();
			}

			// Close the cursor
			cursor.close();
		}

		return questions;
	}

	/**
	 * TODO Add caching to obtain the total score {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getTotalScore(java.lang.String)
	 */
	@Override
	public int getTotalScore (String type) {

		if (!this.cache.isTotalInCache(type)) {
			// Obtain the total from database if it is not cached
			int total = SqliteDataControllerQueries.getTotalScore(this.database, type);

			// Set the value in cache
			this.cache.loadTotal(type, total);

			return total;
		}
		else {
			// Obtain the value from cache
			return this.cache.getTotal(type);
		}
	}

	/**
	 * TODO Add caching to obtain the average score {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getAverageScore(java.lang.String)
	 */
	@Override
	public int getAverageScore (String type) {

		if (!this.cache.isAverageInCache(type)) {
			// Obtain the total score from database since it is not yet cached
			double totalScore = SqliteDataControllerQueries.getTotalScore(this.database, type);

			// Obtain the number of entries
			int entries = (int) SqliteDataControllerQueries.getNumberOfScores(this.database, type);

			// Calculate the average
			int average = (int) Math.round(totalScore / entries);
			
			// Set the average in cache
			this.cache.loadAverage(type, average, entries);

			return average;
		}
		else {
			// Return the cached value of the average
			return this.cache.getAverage(type);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveQuestion(java.lang.Class,
	 *      com.oceans7.mobileapps.eagleswag.domain.Question)
	 */
	@Override
	public void saveQuestion (Class<? extends Question> key, Question question) {

		// Convert the key into the table name where the data will be saved
		String table = this.classToTableMap.get(key);

		// Save the question in the database
		SqliteDataControllerQueries.updateQuestion(this.database, table, question);

	}

	/**
	 * TODO Update the cache (see above) when saving scores {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveRoundScore(com.oceans7.mobileapps.eagleswag.domain.Score,
	 *      java.lang.String)
	 */
	@Override
	public void saveRoundScore (Score score, String type) {

		// Save the score in the database
		SqliteDataControllerQueries.insertIntoScoreTable(this.database, type, score);
		
		// Factor in the total and average values for this score
		this.cache.factorIntoTotal(type, score.getScore());
		this.cache.factorIntoAverage(type, score.getScore());

	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The classToTableMap.
	 */
	public Map<Class<? extends Question>, String> getClassToTableMap () {
		return this.classToTableMap;
	}

	public SQLiteDatabase getDatabase () {
		return this.database;
	}

}

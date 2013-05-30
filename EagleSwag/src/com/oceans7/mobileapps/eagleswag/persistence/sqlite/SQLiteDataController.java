/**
 * @author Justin Albano
 * @date May 27, 2013
 * @file SQLiteDataController.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A data controller that implements data storage and retrieval using a
 *       SQLite database; data is stored using a relational database schema. The
 *       SQLiteDataControllerMappingsParser is used to generate the table of
 *       mappings from class type (for each question type: General, Engineering,
 *       etc.) and the SQLiteDataControllerConstants and
 *       SQLiteDataControllerQueries are combined to encapsulate the SQLite
 *       queries, table names, database version, etc. used by the SQLite data
 *       controller.
 * 
 *       Note that all queries are found in the SQLiteDataControllerQueries
 *       class, and all data controller constants, such as table names, column
 *       numbers, etc., are found in the SQLiteDataControllerConstants class.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.DataController;

public class SQLiteDataController implements DataController {

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
	private SQLiteDataControllerHelper helper;

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

		// Create the database helper
		this.helper = new SQLiteDataControllerHelper(context);

		try {
			// Obtain a writable database reference
			this.database = this.helper.getWritableDatabase();
		}
		catch (SQLException e) {
			// The helper could not create a writable database
			Log.i(this.getClass().getName(), "Writable database cannot be created by SQLite database helper: " + e);
		}

		// Populate the class key to database table map
		SQLiteDataControllerMappingsParser parser = new SQLiteDataControllerMappingsParser(context);
		this.classToTableMap = parser.generateMappingsTable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#close()
	 */
	@Override
	public void close () {
		// Close the helper if it has been set
		if (this.helper != null) {
			this.helper.close();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getQuestions(java.lang.Class,
	 *      int)
	 * 
	 *      The data for the questions is retrieved from the SQLite database.
	 *      The mappings from the question class (for example,
	 *      GeneralQuestion.class) to the database table are retrieved from the
	 *      SQLiteDataControllerMappingsParser.
	 */
	@Override
	public <T extends Question> Queue<T> getQuestions (Class<T> key, int number) {

		// Create queue of requested question objects
		Queue<T> questions = new LinkedList<T>();

		if (number > 0) {
			// Continue only if there is data to retrieve from the database

			// Obtain the data for the questions from the database
			Cursor cursor = SQLiteDataControllerQueries.getQuestions(this.context, this.database, this.classToTableMap.get(key), number);

			// Reset cursor
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				// Loop through the cursor

				// Question data
				int id = cursor.getInt(SQLiteDataControllerConstants.Columns.ID.ordinal());
				String text = cursor.getString(SQLiteDataControllerConstants.Columns.QUESTION.ordinal());
				int yesValue = cursor.getInt(SQLiteDataControllerConstants.Columns.YES_VALUE.ordinal());
				int noValue = cursor.getInt(SQLiteDataControllerConstants.Columns.NO_VALUE.ordinal());
				int usedCount = cursor.getInt(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());

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
		SQLiteDataControllerQueries.updateQuestion(this.database, table, question);

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

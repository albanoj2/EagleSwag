/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataController.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
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

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.DataController;

public class SQLiteDataController implements DataController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The writable database object used to retrieve and store data in the
	 * SQLite database.
	 */
	private SQLiteDatabase database;

	/**
	 * Database helper that manages many of the underlying tasks associated with
	 * SQLite database.
	 */
	private SQLiteDataControllerHelper helper;

	/**
	 * A map of question classes to the database table names used. This is used
	 * to map a question type to a table in the database, which allows for the
	 * retrieval and storage of questions.
	 */
	private Map<Class<? extends Question>, String> classToTableMap;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public SQLiteDataController () {

		// Create and populate the question class to table name mappings
		this.classToTableMap = new HashMap<Class<? extends Question>, String>();
		this.classToTableMap.put(GeneralQuestion.class,
			SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE);
		this.classToTableMap.put(EngineeringQuestion.class,
			SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE);
		this.classToTableMap.put(PilotQuestion.class,
			SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#open()
	 */
	@Override
	public void open (Context context) throws SQLException {

		// Create the database helper
		this.helper = new SQLiteDataControllerHelper(context);

		// Obtain a writable database reference
		this.database = this.helper.getWritableDatabase();
	}

	/**
	 * 
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#close()
	 */
	@Override
	public void close () {
		// Close the helper if it has been set
		if (this.helper != null) this.helper.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Obtains a specified number of questions of the supplied type, T. The
	 * generic parameter, T, specified the type of the questions returned from
	 * this method (the type of the questions in the queue returned from this
	 * method). The supplied class (givenClass) determines the mapping of
	 * question type to the database table that contains the questions data.
	 * 
	 * @param key
	 *            The class of the question type. For example, if the supplied
	 *            type, T, is GeneralQuestion, the givenClass is
	 *            GeneralQuestion.class. This class is used as a key to map the
	 *            question type to the database table used to retrieve the
	 *            question data.
	 * @param number
	 *            The number of questions to retrieve from the database.
	 * @return
	 *         A queue containing the specified number of questions of type, T.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, int number) {

		// Create queue of requested question objects
		Queue<T> questions = new LinkedList<T>();

		if (number > 0) {
			// Continue only if there is data to retrieve from the database

			// Obtain the data for the questions from the database
			Cursor cursor = SQLiteDataControllerQueries.getQuestions(this.database,
				this.classToTableMap.get(key),
				number);

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
					Log.i(this.getClass().getName(),
						"Added general question to " + key.getCanonicalName() + " queue: " + question);
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
					Log.e(this.getClass().getName(),
						"The constructor used to create the generic question object cannot be found: " + e);
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
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveQuestion(java.lang.Class, com.oceans7.mobileapps.eagleswag.domain.Question)
	 */
	@Override
	public void saveQuestion (Class<? extends Question> key, Question question) {
		// TODO Auto-generated method stub
		
	}

	public SQLiteDatabase getDatabase () {
		return this.database;
	}

}

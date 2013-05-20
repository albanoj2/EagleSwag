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

import java.util.HashMap;
import java.util.LinkedList;
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
	 * A map of question classes to the database table names used.
	 */
	private HashMap<Class<?>, String> classToTableMap;

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
	 * {@inheritDoc}
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
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getGeneralQuestions(int)
	 */
	@Override
	public Queue<GeneralQuestion> getGeneralQuestions (int number) {

		// Queue to store the general questions
		Queue<GeneralQuestion> questions = new LinkedList<GeneralQuestion>();
		
		if (number > 0) {
			// There is data to retrieve from the database

			// Obtain the data from the database
			Cursor cursor = SQLiteDataControllerQueries.getQuestions(
				this.database, SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE, number);

			// Reset cursor
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				// Loop through the cursor

				// Question data
				long id = cursor.getLong(SQLiteDataControllerConstants.Columns.ID.ordinal());
				String text = cursor.getString(SQLiteDataControllerConstants.Columns.QUESTION.ordinal());
				long yesValue = cursor.getLong(SQLiteDataControllerConstants.Columns.YES_VALUE.ordinal());
				long noValue = cursor.getLong(SQLiteDataControllerConstants.Columns.NO_VALUE.ordinal());
				long usedCount = cursor.getLong(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());

				// Add the new question to the queue
				GeneralQuestion question = new GeneralQuestion(id, text, yesValue, noValue, usedCount);
				questions.add(question);
				Log.i(this.getClass().getName(), "Added general question: " + question);

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
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getEngineeringQuestions(int)
	 */
	@Override
	public Queue<EngineeringQuestion> getEngineeringQuestions (int number) {

		// Queue to store the engineering questions
		Queue<EngineeringQuestion> questions = new LinkedList<EngineeringQuestion>();

		if (number > 0) {
			// There is data to retrieve from the database

			// Obtain the data from the database
			Cursor cursor = SQLiteDataControllerQueries.getQuestions(
				this.database, SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE, number);

			// Reset cursor
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				// Loop through the cursor

				// Question data
				long id = cursor.getLong(SQLiteDataControllerConstants.Columns.ID.ordinal());
				String text = cursor.getString(SQLiteDataControllerConstants.Columns.QUESTION.ordinal());
				long yesValue = cursor.getLong(SQLiteDataControllerConstants.Columns.YES_VALUE.ordinal());
				long noValue = cursor.getLong(SQLiteDataControllerConstants.Columns.NO_VALUE.ordinal());
				long usedCount = cursor.getLong(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());

				// Add the new question to the queue
				EngineeringQuestion question = new EngineeringQuestion(id, text, yesValue, noValue, usedCount);
				questions.add(question);
				Log.i(this.getClass().getName(), "Added engineering question: " + question);

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
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#getPilotQuestion(int)
	 */
	@Override
	public Queue<PilotQuestion> getPilotQuestions (int number) {

		// Queue to store the pilot questions
		Queue<PilotQuestion> questions = new LinkedList<PilotQuestion>();

		if (number > 0) {
			// There is data to retrieve from the database

			// Obtain the data from the database
			Cursor cursor = SQLiteDataControllerQueries.getQuestions(
				this.database, SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE, number);

			// Reset cursor
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				// Loop through the cursor

				// Question data
				long id = cursor.getLong(SQLiteDataControllerConstants.Columns.ID.ordinal());
				String text = cursor.getString(SQLiteDataControllerConstants.Columns.QUESTION.ordinal());
				long yesValue = cursor.getLong(SQLiteDataControllerConstants.Columns.YES_VALUE.ordinal());
				long noValue = cursor.getLong(SQLiteDataControllerConstants.Columns.NO_VALUE.ordinal());
				long usedCount = cursor.getLong(SQLiteDataControllerConstants.Columns.USED_COUNT.ordinal());

				// Add the new question to the queue
				PilotQuestion question = new PilotQuestion(id, text, yesValue, noValue, usedCount);
				questions.add(question);
				Log.i(this.getClass().getName(), "Added pilot question: " + question);

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
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveGeneralQuestion(com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion)
	 */
	@Override
	public void saveQuestion (Question question) {
		// TODO Auto-generated method stub

	}
	
	public SQLiteDatabase getDatabase () {
		return this.database;
	}

}

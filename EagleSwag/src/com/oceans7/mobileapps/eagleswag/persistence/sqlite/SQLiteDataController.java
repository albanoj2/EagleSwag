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

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
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

		for (int i = 0; i < number; i++) {
			// Create the specified number of general questions
			GeneralQuestion question = new GeneralQuestion(0, null, 0, 0, 0);
			questions.add(question);
			Log.i("SQLite Data Controller", "Added general question: " + question);
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

		for (int i = 0; i < number; i++) {
			// Create the specified number of engineering questions
			EngineeringQuestion question = new EngineeringQuestion(0, null, 0, 0, 0);
			questions.add(question);
			Log.i("SQLite Data Controller", "Added engineering question: " + question);
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

		for (int i = 0; i < number; i++) {
			// Create the specified number of engineering questions
			PilotQuestion question = new PilotQuestion(0, null, 0, 0, 0);
			questions.add(question);
			Log.i("SQLite Data Controller", "Added pilot question: " + question);
		}

		return questions;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveGeneralQuestion(com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion)
	 */
	@Override
	public void saveGeneralQuestion (GeneralQuestion question) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#saveEngineeringQuestion(com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion)
	 */
	@Override
	public void saveEngineeringQuestion (EngineeringQuestion question) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataController#savePilotQuestion(com.oceans7.mobileapps.eagleswag.domain.PilotQuestion)
	 */
	@Override
	public void savePilotQuestion (PilotQuestion question) {
		// TODO Auto-generated method stub

	}

}

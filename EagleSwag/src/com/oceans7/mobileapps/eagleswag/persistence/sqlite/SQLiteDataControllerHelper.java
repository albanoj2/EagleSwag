/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerHelper.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A helper class used to create, update, and management the SQLite
 *       database. This helper class is an implementation of the
 *       SQLiteOpenHelper class provided by the SQLite framework for Android.
 *       This class deals with updating and crating the SQLite database used by
 *       the application. When the version number of the database is incremented
 *       in the SQLiteDataControllerConstants class, this helper automatically
 *       updates the database.
 * 
 * @see android.database.sqlite.SQLiteOpenHelper
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import java.util.Queue;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParser;
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParserFactory;

public class SQLiteDataControllerHelper extends SQLiteOpenHelper {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used to access the data file containing the questions to be
	 * loaded into the database upon startup.
	 */
	private Context context;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Creates the helper that is used for aiding in database management.
	 * 
	 * @param context
	 *            The context used for the database helper.
	 */
	public SQLiteDataControllerHelper (Context context) {
		super(context, SQLiteDataControllerConstants.DATABASE_NAME, null, SQLiteDataControllerConstants.DATABASE_VERSION);

		// Save the context
		this.context = context;
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 * 
	 *      TODO: This method may be reduced so that each question type does not
	 *      have repeated logic
	 */
	@Override
	public void onCreate (SQLiteDatabase db) {

		try {
			for (int i = 0; i < SQLiteDataControllerConstants.TABLES.length; i++) {
				// Create each questions table in the database
				SQLiteDataControllerQueries.createQuestionsTable(db, SQLiteDataControllerConstants.TABLES[i]);
			}
		}
		catch (SQLException e) {
			// An exception occurred while trying to create the database
			Log.e(this.getClass().getName(), "Error while creating the database: " + e);
		}

		// Obtain a data file parser
		DataFileParser parser = DataFileParserFactory.getInstance().getDataFileParser(context);

		// Get queues for each of the question categories
		Queue<GeneralQuestion> generalQuestions = parser.getGeneralQuestions();
		Queue<EngineeringQuestion> engineeringQuestions = parser.getEngineeringQuestions();
		Queue<PilotQuestion> pilotQuestions = parser.getPilotQuestions();

		for (GeneralQuestion question : generalQuestions) {

			// Insert the new general question
			SQLiteDataControllerQueries.insertIntoQuestionsTable(db, SQLiteDataControllerConstants.GENERAL_QUESTIONS_TABLE, question);
			Log.i(this.getClass().getName(), "Inserted general questions into the general table.");
		}

		for (EngineeringQuestion question : engineeringQuestions) {

			// Insert the new engineering question
			SQLiteDataControllerQueries.insertIntoQuestionsTable(db, SQLiteDataControllerConstants.ENGINEERING_QUESTIONS_TABLE, question);
			Log.i(this.getClass().getName(), "Inserted engineering questions into the engineering table.");
		}

		for (PilotQuestion question : pilotQuestions) {

			// Insert the new pilot question
			SQLiteDataControllerQueries.insertIntoQuestionsTable(db, SQLiteDataControllerConstants.PILOT_QUESTIONS_TABLE, question);
			Log.i(this.getClass().getName(), "Inserted pilot questions into the pilot table.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      int, int)
	 */
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop all old tables from the database
		Log.w(this.getClass().getName(),
			"Database is about to be updated from version " + oldVersion + " to version " + newVersion + ". All old data will lost");

		try {
			for (int i = 0; i < SQLiteDataControllerConstants.TABLES.length; i++) {
				// Iterate through each table and drop it from the database
				db.execSQL("DROP TABLE IF EXISTS " + SQLiteDataControllerConstants.TABLES[i]);
				Log.w(this.getClass().getName(), "Dropping table '" + SQLiteDataControllerConstants.TABLES[i] + "' from database");
			}
		}
		catch (SQLException e) {
			// An exception occurred while dropping tables from the database
			Log.e(this.getClass().getName(), "Error while dropping tables from the database: " + e);
		}

		// Recreate the database
		this.onCreate(db);
	}
}

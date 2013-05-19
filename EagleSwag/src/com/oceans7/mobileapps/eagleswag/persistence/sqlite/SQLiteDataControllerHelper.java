/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerHelper.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A helper class used to create, update, and management the SQLite
 *       database.
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
		super(context, SQLiteDataControllerUtil.DATABASE_NAME, null, SQLiteDataControllerUtil.DATABASE_VERSION);

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
	 */
	@Override
	public void onCreate (SQLiteDatabase db) {

		try {
			for (int i = 0; i < SQLiteDataControllerUtil.TABLES.length; i++) {
				// Create each questions table in the database
				db.execSQL(SQLiteDataControllerUtil.getCreateQuestionsTableQuery(SQLiteDataControllerUtil.TABLES[i]));
				Log.i(this.getClass().getName(), "Created table using query: " + SQLiteDataControllerUtil.getCreateQuestionsTableQuery(SQLiteDataControllerUtil.TABLES[i]));
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
			SQLiteDataControllerUtil.insertIntoQuestionsTable(db, SQLiteDataControllerUtil.GENERAL_QUESTIONS_TABLE_NAME, question);
		}

		for (EngineeringQuestion question : engineeringQuestions) {
			// Insert the new engineering question
			SQLiteDataControllerUtil.insertIntoQuestionsTable(db, SQLiteDataControllerUtil.GENERAL_QUESTIONS_TABLE_NAME, question);
		}

		for (PilotQuestion question : pilotQuestions) {
			// Insert the new pilot question
			SQLiteDataControllerUtil.insertIntoQuestionsTable(db, SQLiteDataControllerUtil.GENERAL_QUESTIONS_TABLE_NAME, question);
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
		Log.w(this.getClass().getName(), "Database is about to be updated from version " + oldVersion + " to version " + newVersion + ". All old data will lost");

		try {
			for (int i = 0; i < SQLiteDataControllerUtil.TABLES.length; i++) {
				// Iterate through each table and drop it from the database
				db.execSQL("DROP TABLE IF EXISTS " + SQLiteDataControllerUtil.TABLES[i]);
				Log.w(this.getClass().getName(), "Dropping table '" + SQLiteDataControllerUtil.TABLES[i] + "' from database");
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

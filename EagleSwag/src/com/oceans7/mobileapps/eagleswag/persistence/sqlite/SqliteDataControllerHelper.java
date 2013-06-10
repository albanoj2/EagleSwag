/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SqliteDataControllerHelper.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A helper class used to create, update, and management the SQLite
 *       database. This helper class is an implementation of the
 *       SQLiteOpenHelper class provided by the SQLite framework for Android.
 *       This class deals with updating and crating the SQLite database used by
 *       the application. When the version number of the database is incremented
 *       in the SqliteDataControllerConstants class, this helper automatically
 *       updates the database.
 * 
 * @see android.database.sqlite.SQLiteOpenHelper
 * 
 *      FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

import java.util.Map;
import java.util.Queue;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.config.QuestionType;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParser;

public class SqliteDataControllerHelper extends SQLiteOpenHelper {

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
	public SqliteDataControllerHelper (Context context) {
		super(context, SqliteDataControllerConstants.DATABASE_NAME, null, SqliteDataControllerConstants.DATABASE_VERSION);

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
	 *      The configuration data from the question type configuration file is
	 *      used to specify the name of the table the data for each question
	 *      type is placed into. The configuration data is obtained from the
	 *      configuration controller and a questions table in the database is
	 *      created for each question type/
	 */
	@Override
	public void onCreate (SQLiteDatabase db) {

		// RECORD: time stamp of the beginning of the creation
		long start = System.currentTimeMillis();

		// Obtain a data file parser
		DataFileParser parser = new DataFileParser();

		// Obtain the question types from the configuration file
		Map<Class<? extends Question>, QuestionType> qtMap = ConfigurationHelper.getInstance().getAllQuestionTypes(this.context);

		for (Class<? extends Question> key : qtMap.keySet()) {
			// Loop through each of the question type entries and make a table
			// in the database to store each of the entries

			// Get the SQLite database table name for the key
			String table = ConfigurationHelper.getInstance().getTableName(key, this.context);

			try {
				// Start a transaction for inserting the data in the database
				db.beginTransaction();

				// Create each questions table in the database
				SqliteDataControllerQueries.createQuestionsTable(db, table);

				// Obtain the questions from the data parser
				Queue<? extends Question> questions = parser.getQuestions(key, context);

				for (Question question : questions) {
					// Insert the new general question
					SqliteDataControllerQueries.insertIntoQuestionsTable(db, table, question);
					Log.i(this.getClass().getName(), "Inserted " + key.getCanonicalName() + " into the " + table + " table: " + question);
				}

				// Signal that the transaction was successful
				db.setTransactionSuccessful();
			}
			catch (SQLException e) {
				// An exception occurred while trying to create the database
				Log.e(this.getClass().getName(), "Error while creating the database '" + table + "': " + e);
			}
			finally {
				// End the previously started transaction
				db.endTransaction();
			}

			// RECORD: time stamp of the end of the creation
			long end = System.currentTimeMillis();
			Log.i(this.getClass().getName(), "SQLite database creation took " + (end - start) + "ms");

		}

		// Create scores table
		SqliteDataControllerQueries.createScoreTable(db);

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
}

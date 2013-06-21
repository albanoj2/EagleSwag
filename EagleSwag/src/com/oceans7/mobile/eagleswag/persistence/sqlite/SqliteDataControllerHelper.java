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

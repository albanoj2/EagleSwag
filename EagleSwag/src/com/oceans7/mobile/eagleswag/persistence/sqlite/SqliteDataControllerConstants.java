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

/**
 * A lookup dictionary for queries and table/column names to be used in the
 * SqliteDataController.
 * 
 * @author Justin Albano
 */
public class SqliteDataControllerConstants {

	/***************************************************************************
	 * Static Attributes
	 **************************************************************************/

	/**
	 * The name of the database file to be used as the SQLite database.
	 */
	public static final String DATABASE_NAME = "eagleswag.db";

	/**
	 * The version number of the database. When the version number is altered,
	 * and the application is run, the database is automatically updated (the
	 * SqliteDataControllerHelper runs its update logic to update the database).
	 */
	public static final int DATABASE_VERSION = 16;

	/**
	 * A numerical reference for each of the columns in a questions table.
	 */
	public static enum QuestionColumns {
		ID, QUESTION, YES_VALUE, NO_VALUE, USED_COUNT
	};

	// -------------------------------------------------------------------------
	// Question table column names
	// -------------------------------------------------------------------------

	/**
	 * The name of the ID column in a questions table.
	 */
	public static final String ID_COLUMN = "_id";

	/**
	 * The name of the question text column in a questions table.
	 */
	public static final String QUESTION_COLUMN = "question";

	/**
	 * The name of the yes point value column in a questions table.
	 */
	public static final String YES_VALUE_COLUMN = "yesValue";

	/**
	 * The name of the no point value column in a questions table.
	 */
	public static final String NO_VALUE_COLUMN = "noValue";

	/**
	 * The name of the used count column in a questions table.
	 */
	public static final String USED_COUNT_COLUMN = "usedCount";

	// -------------------------------------------------------------------------
	// Scores table
	// -------------------------------------------------------------------------

	public static final String SCORE_TABLE_NAME = "Scores";

	// TODO Documentation for each of the column names
	public static enum ScoresColumns {
		ID, SCORE, TIMESTAMP, TYPE
	}

	// TODO Documentation
	public static final String SCORE_ID_COLUMN = "_id";
	public static final String SCORE_SCORE_COLUMN = "score";
	public static final String SCORE_TIMESTAMP_COLUMN = "timestamp";
	public static final String SCORE_TYPE_COLUMN = "type";
}

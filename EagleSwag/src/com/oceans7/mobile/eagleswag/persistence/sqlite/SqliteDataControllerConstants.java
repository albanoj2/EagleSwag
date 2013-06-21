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
	public static final int DATABASE_VERSION = 17;

	/**
	 * A numerical reference for each of the columns in a questions table.
	 */
	public static enum QuestionsColumns {
		
		ID("_id"), QUESTION("question"), YES_VALUE("yesValue"), NO_VALUE("noValue"), USED_COUNT("usedCount");
		
		private final String name;

		private QuestionsColumns (String name) {
			this.name = name;
		}

		public String toString () {
			return this.name;
		}
	}

	// -------------------------------------------------------------------------
	// Question table column names
	// -------------------------------------------------------------------------

	/**
	 * The name of the ID column in a questions table.
	 */
	//public static final String ID_COLUMN = "_id";

	/**
	 * The name of the question text column in a questions table.
	 */
	//public static final String QUESTION_COLUMN = "question";

	/**
	 * The name of the yes point value column in a questions table.
	 */
	//public static final String YES_VALUE_COLUMN = "yesValue";

	/**
	 * The name of the no point value column in a questions table.
	 */
	//public static final String NO_VALUE_COLUMN = "noValue";

	/**
	 * The name of the used count column in a questions table.
	 */
	//public static final String USED_COUNT_COLUMN = "usedCount";

	// -------------------------------------------------------------------------
	// Scores table
	// -------------------------------------------------------------------------

	/**
	 * The name of the SQLite table containing score data.
	 */
	public static final String SCORE_TABLE_NAME = "Scores";

	/**
	 * Enumeration of the table column names for the SQLite database table
	 * containing the scores data.
	 * 
	 * @author Justin Albano
	 */
	public static enum ScoresColumns {

		ID("_id"), SCORE("score"), TIMESTAMP("timestamp"), TYPE("type");

		private final String name;

		private ScoresColumns (String name) {
			this.name = name;
		}

		public String toString () {
			return this.name;
		}
	}
}

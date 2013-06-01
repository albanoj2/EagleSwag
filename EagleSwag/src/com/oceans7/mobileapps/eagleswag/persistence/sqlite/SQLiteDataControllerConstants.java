/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerConstants.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A lookup dictionary for queries and table/column names to be used in
 *       the SQLiteDataController.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

public class SQLiteDataControllerConstants {

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
	 * SQLiteDataControllerHelper runs its update logic to update the database).
	 */
	public static final int DATABASE_VERSION = 13;

	/**
	 * A numerical reference for each of the columns in a questions table.
	 */
	public static enum Columns {
		ID, QUESTION, YES_VALUE, NO_VALUE, USED_COUNT
	};

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
}

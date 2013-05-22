/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file SQLiteDataControllerConstants.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 * 		TODO: Improve documentation
 *       A lookup dictionary for queries and table/column names to be used in
 *       the SQLiteDataController.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;


public class SQLiteDataControllerConstants {

	/***************************************************************************
	 * Static Attributes
	 **************************************************************************/

	public static final String DATABASE_NAME = "eagleswag.db";
	public static final int DATABASE_VERSION = 10;

	public static final String GENERAL_QUESTIONS_TABLE = "GeneralQuestions";
	public static final String ENGINEERING_QUESTIONS_TABLE = "EngineeringQuestions";
	public static final String PILOT_QUESTIONS_TABLE = "PilotQuestions";
	public static final String[] TABLES = { GENERAL_QUESTIONS_TABLE, ENGINEERING_QUESTIONS_TABLE, PILOT_QUESTIONS_TABLE };

	public static enum Columns {ID, QUESTION, YES_VALUE, NO_VALUE, USED_COUNT};

	public static final String ID_COLUMN = "_id";
	public static final String QUESTION_COLUMN = "question";
	public static final String YES_VALUE_COLUMN = "yesValue";
	public static final String NO_VALUE_COLUMN = "noValue";
	public static final String USED_COUNT_COLUMN = "usedCount";

	public static final String CREATE_DATABASE_QUERY = "CREATE DATABASE " + DATABASE_NAME;
}

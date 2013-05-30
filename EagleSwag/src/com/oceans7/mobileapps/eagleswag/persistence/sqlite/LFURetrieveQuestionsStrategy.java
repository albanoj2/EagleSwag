/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file LFURetrieveQuestionsStrategy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A least frequently used (LFU) strategy for obtaining questions from a
 *       questions table in an SQLite database. The used count of the questions
 *       is used as the variable which dictates the frequency of use for a
 *       question (a question with a lower used count is considered to have been
 *       used less frequently than a question with a higher used count).
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

public class LFURetrieveQuestionsStrategy implements RetrieveQuestionsStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.sqlite.RetrieveQuestionsStrategy#getQuery(java.lang.String)
	 */
	@Override
	public String getQuery (String table, int numberOfQuestions) {

		// Use a LFU query to obtain the questions
		return "SELECT * FROM " + table + " " + "ORDER BY " + SQLiteDataControllerConstants.USED_COUNT_COLUMN + " ASC " + "LIMIT " + numberOfQuestions;
	}

}

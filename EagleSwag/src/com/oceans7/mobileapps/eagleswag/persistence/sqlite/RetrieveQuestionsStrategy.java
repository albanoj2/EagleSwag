/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file RetrieveQuestionsStrategy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Interface for a strategy defining how to retrieve questions from the
 *       database. This strategy returns a query that dictates how questions
 *       should be retrieved from the database.
 */

package com.oceans7.mobileapps.eagleswag.persistence.sqlite;

public interface RetrieveQuestionsStrategy {

	/**
	 * A query that defines how questions should be retrieved from the database.
	 * This query contains the required logic for retrieving questions and is
	 * intended to be executed against an SQLite database.
	 * 
	 * @param table
	 *            The name of the table where the questions will be retrieved
	 *            from.
	 * @param numberOfQuestions
	 *            The number of questions to retrieve from the supplied table.
	 * @return
	 *         A query that defines how the questions will be retrieved from the
	 *         database.
	 */
	public String getQuery (String table, int numberOfQuestions);
}

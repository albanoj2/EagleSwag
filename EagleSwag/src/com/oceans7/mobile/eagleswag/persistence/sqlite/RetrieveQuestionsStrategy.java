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
 * Interface for a strategy defining how to retrieve questions from the
 * database. This strategy returns a query that dictates how questions should be
 * retrieved from the database.
 * 
 * @author Justin Albano
 */
public interface RetrieveQuestionsStrategy {
	
	/***************************************************************************
	 * Methods
	 **************************************************************************/

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

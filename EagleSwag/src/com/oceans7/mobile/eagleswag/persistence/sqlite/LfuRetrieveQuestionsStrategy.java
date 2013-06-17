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
 * A least frequently used (LFU) strategy for obtaining questions from a
 * questions table in an SQLite database. The used count of the questions is
 * used as the variable which dictates the frequency of use for a question (a
 * question with a lower used count is considered to have been used less
 * frequently than a question with a higher used count).
 * 
 * @author Justin Albano
 */
public class LfuRetrieveQuestionsStrategy implements RetrieveQuestionsStrategy {

	/***************************************************************************
	 * Methods
	 **************************************************************************/
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.persistence.sqlite.RetrieveQuestionsStrategy#getQuery(java.lang.String)
	 */
	@Override
	public String getQuery (String table, int numberOfQuestions) {

		// Use a LFU query to obtain the questions
		return "SELECT * FROM " + table + " " + "ORDER BY " + SqliteDataControllerConstants.QuestionsColumns.USED_COUNT + " ASC " + "LIMIT " + numberOfQuestions;
	}

}

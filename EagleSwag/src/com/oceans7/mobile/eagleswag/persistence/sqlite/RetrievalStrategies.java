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
 * Factory to create strategy for retrieving questions from the database.
 * 
 * @author Justin Albano
 */
public class RetrievalStrategies {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static RetrievalStrategies instance;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private RetrievalStrategies () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains a reference to the singleton instance of the factory.
	 * 
	 * @return
	 *         A singleton instance of the factory.
	 */
	public static synchronized RetrievalStrategies getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new RetrievalStrategies();
		}

		return instance;
	}

	/**
	 * Obtains the strategy for retrieving questions from the database.
	 * 
	 * @return
	 *         A strategy for accessing questions from the database.
	 */
	public RetrievalStrategy getRetrieveQuestionsStrategy () {
		return this.getLfuRetrieveQuestionsStrategy();
	}

	/**
	 * Creates a least frequently used question retrieval strategy.
	 * 
	 * @return
	 *         A least frequently used question retrieval strategy.
	 */
	public LfuRetrievalStrategy getLfuRetrieveQuestionsStrategy () {
		return new LfuRetrievalStrategy();
	}

}

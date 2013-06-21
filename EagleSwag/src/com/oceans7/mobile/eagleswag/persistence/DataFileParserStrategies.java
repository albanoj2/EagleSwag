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

package com.oceans7.mobile.eagleswag.persistence;

import android.content.Context;

import com.oceans7.mobile.eagleswag.persistence.parsers.JsonDataFileParserStrategy;

/**
 * Factory for the creation of the data file parser strategies.
 * 
 * @author Justin Albano
 */
public class DataFileParserStrategies {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static DataFileParserStrategies instance;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private DataFileParserStrategies () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains a singleton instance of the data file parser strategy factory.
	 * 
	 * @return
	 *         A singleton instance of the data controller factory.
	 */
	public static synchronized DataFileParserStrategies getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new DataFileParserStrategies();
		}

		return instance;
	}

	/**
	 * Obtains a data file parser strategy.
	 * 
	 * @param context
	 *            The context used to access the data files.
	 * 
	 * @return
	 *         A preset data file parser strategy.
	 */
	public DataFileParserStrategy getDataFileParserStrategy (Context context) {
		return this.getJsonDataFileParserStrategy(context);
	}

	/**
	 * Obtains a JSON data file parser strategy.
	 * 
	 * @param context
	 *            The context used to access the data files.
	 * 
	 * @return
	 *         A JSON data file parser strategy.
	 */
	public JsonDataFileParserStrategy getJsonDataFileParserStrategy (Context context) {
		return new JsonDataFileParserStrategy(context);
	}
}

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

import com.oceans7.mobile.eagleswag.persistence.sqlite.SqliteDataController;

/**
 * Factory used to create the data controller for persistent storage. The data
 * controller created varies depending on the class specified in the data
 * controller configuration file.
 * 
 * @author Justin Albano
 */
public class DataControllers {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of factory.
	 */
	private static DataControllers instance;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Hidden constructor (for singleton).
	 */
	private DataControllers () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains a singleton instance of the data controller factory.
	 * 
	 * @return
	 *         A singleton instance of the data controller factory.
	 */
	public static synchronized DataControllers getInstance () {

		if (instance == null) {
			// Lazy instantiation of the instance
			instance = new DataControllers();
		}

		return instance;
	}

	/**
	 * Obtains the data controller for persistence storage.
	 * 
	 * @param context
	 *            The context used to create the data controller.
	 * @return
	 *         The data controller (as specified by the data controller
	 *         configuration file).
	 */
	public DataController getDataController (Context context) {
		return this.getSqliteDataController(context);
	}

	/**
	 * Creates an SQLite data controller.
	 * 
	 * @param context
	 *            The context used to create the SQLite data controller.
	 * @return
	 *         An instance of a SQLite data controller.
	 */
	public SqliteDataController getSqliteDataController (Context context) {
		return new SqliteDataController(context);
	}

}

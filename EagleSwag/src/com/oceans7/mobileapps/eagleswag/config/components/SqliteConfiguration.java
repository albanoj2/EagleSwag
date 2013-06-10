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

package com.oceans7.mobileapps.eagleswag.config.components;

/**
 * Specification for the SQLite element of the question type configuration file.
 * 
 * @author Justin Albano
 */
public class SqliteConfiguration {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The name of the SQLite table that corresponds to the question type.
	 */
	private String table;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that provides for the initialization of the
	 * SQLite table name configuration data.
	 * 
	 * @param table
	 *            The name of the SQLite table that corresponds to the question
	 *            type.
	 */
	public SqliteConfiguration (String table) {

		// Set the SQLite configuration data
		this.setTable(table);
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The table.
	 */
	public String getTable () {
		return table;
	}

	/**
	 * @param table
	 *            The table to set.
	 */
	public void setTable (String table) {
		this.table = table;
	}

}

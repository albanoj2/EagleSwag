/**
 * @author Justin Albano
 * @date Jun 6, 2013
 * @file SqliteConfiguration.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Specification for the SQLite element of the question type configuration
 *       file.
 *       
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.config.components;

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

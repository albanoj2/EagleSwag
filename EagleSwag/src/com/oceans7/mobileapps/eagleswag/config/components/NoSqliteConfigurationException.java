/**
 * @author Justin Albano
 * @date Jun 7, 2013
 * @file NoSqliteConfigurationException.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       An exception that is thrown when the SQLite configuration data for a
 *       question type is null.
 */

package com.oceans7.mobileapps.eagleswag.config.components;

public class NoSqliteConfigurationException extends RuntimeException {
	
	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = 386540560241485662L;
	
	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that provides a mechanism for adding a custom
	 * message to the runtime exception.
	 * 
	 * @param message
	 *            A custom message to attribute to the exception.
	 */
	public NoSqliteConfigurationException (String message) {
		super("No configuration data specified for SQLite in the question type configuration file: " + message);
	}

}

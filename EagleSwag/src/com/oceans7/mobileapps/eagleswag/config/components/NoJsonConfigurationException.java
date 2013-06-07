/**
 * @author Justin Albano
 * @date Jun 7, 2013
 * @file NoJsonConfigurationException.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       An exception that is thrown when the JSON configuration data for a
 *       question type is null.
 */

package com.oceans7.mobileapps.eagleswag.config.components;

public class NoJsonConfigurationException extends RuntimeException {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -1499858910964341654L;

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
	public NoJsonConfigurationException (String message) {
		super("No configuration data specified for JSON in the question type configuration file: " + message);
	}

}

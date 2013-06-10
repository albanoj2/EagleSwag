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
 * An exception that is thrown when the SQLite configuration data for a question
 * type is null.
 * 
 * @author Justin Albano
 */
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

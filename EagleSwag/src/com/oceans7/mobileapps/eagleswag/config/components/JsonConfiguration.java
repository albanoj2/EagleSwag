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
 * Specification of the JSON configuration element of the question type
 * configuration file.
 * 
 * @author Justin Albano
 */
public class JsonConfiguration {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The ID within the JSON data file that marks where the data for a question
	 * type is located.
	 */
	private String id;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that provides for the initialization of the
	 * JSON ID for the data file for a question type.
	 * 
	 * @param id
	 *            The JSON ID that marks the location of the data for a question
	 *            type within a JSON data file.
	 */
	public JsonConfiguration (String id) {

		// Set the JSON configuration data
		this.setId(id);
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The id.
	 */
	public String getId () {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId (String id) {
		this.id = id;
	}

}

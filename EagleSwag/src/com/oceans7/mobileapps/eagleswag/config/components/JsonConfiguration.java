/**
 * @author Justin Albano
 * @date Jun 6, 2013
 * @file JsonConfiguration.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Specification of the JSON configuration element of the question type
 *       configuration file.
 */

package com.oceans7.mobileapps.eagleswag.config.components;

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

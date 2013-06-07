/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file Type.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO: Documentation
 * 
 */

package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.config.components.DataConfiguration;
import com.oceans7.mobileapps.eagleswag.config.components.JsonConfiguration;
import com.oceans7.mobileapps.eagleswag.config.components.SqliteConfiguration;

public class QuestionType {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private String name;
	private DataConfiguration dataConfiguration;
	private JsonConfiguration jsonConfiguration;
	private SqliteConfiguration sqliteConfiguration;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public QuestionType () {}

	public QuestionType (String name) {
		this.setName(name);
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The name.
	 */
	public String getName () {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName (String name) {
		this.name = name;
	}

	/**
	 * @return 
	 *		The dataConfiguration.
	 */
	public DataConfiguration getDataConfiguration () {
		return dataConfiguration;
	}

	/**
	 * @param dataConfiguration 
	 * 		The dataConfiguration to set.
	 */
	public void setDataConfiguration (DataConfiguration dataConfiguration) {
		this.dataConfiguration = dataConfiguration;
	}

	/**
	 * @return 
	 *		The jsonConfiguration.
	 */
	public JsonConfiguration getJsonConfiguration () {
		return jsonConfiguration;
	}

	/**
	 * @param jsonConfiguration 
	 * 		The jsonConfiguration to set.
	 */
	public void setJsonConfiguration (JsonConfiguration jsonConfiguration) {
		this.jsonConfiguration = jsonConfiguration;
	}

	/**
	 * @return 
	 *		The sqliteConfiguration.
	 */
	public SqliteConfiguration getSqliteConfiguration () {
		return sqliteConfiguration;
	}

	/**
	 * @param sqliteConfiguration 
	 * 		The sqliteConfiguration to set.
	 */
	public void setSqliteConfiguration (SqliteConfiguration sqliteConfiguration) {
		this.sqliteConfiguration = sqliteConfiguration;
	}

}

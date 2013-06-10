package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.config.components.DataConfiguration;
import com.oceans7.mobileapps.eagleswag.config.components.JsonConfiguration;
import com.oceans7.mobileapps.eagleswag.config.components.SqliteConfiguration;

/**
 * A question type specification that mimics a question type found in the
 * question type configuration file. This question type has pluggable components
 * for the persistence configuration of a question type. This allows for new
 * persistence configuration data to be added to the configuration file and be,
 * likewise, added to this question type object.
 * 
 * @author Justin Albano
 */
public class QuestionType {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The name of the question type.
	 */
	private String name;

	/**
	 * The data configuration for the question type. This configuration contains
	 * the information pertaining to the data file for a question type.
	 */
	private DataConfiguration dataConfiguration;

	/**
	 * The JSON persistence configuration. This configuration contains the
	 * information pertaining to the JSON data file parser.
	 * 
	 * @note This attribute may be null if no JSON configuration data is
	 *       specified within the question type configuration file.
	 */
	private JsonConfiguration jsonConfiguration;

	/**
	 * The SQLite persistence configuration. This configuration contains the
	 * information pertaining to the SQLite persistence layer.
	 * 
	 * @note This attribute may be null if no SQLite configuration data is
	 *       specified within the question type configuration file.
	 */
	private SqliteConfiguration sqliteConfiguration;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Default constructor.
	 */
	public QuestionType () {}

	/**
	 * Parameterized constructor that provides for the initialization of the
	 * name of the question type.
	 * 
	 * @param name
	 *            The name of the question type.
	 */
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
	 *         The dataConfiguration.
	 */
	public DataConfiguration getDataConfiguration () {
		return dataConfiguration;
	}

	/**
	 * @param dataConfiguration
	 *            The dataConfiguration to set.
	 */
	public void setDataConfiguration (DataConfiguration dataConfiguration) {
		this.dataConfiguration = dataConfiguration;
	}

	/**
	 * @return
	 *         The jsonConfiguration.
	 */
	public JsonConfiguration getJsonConfiguration () {
		return jsonConfiguration;
	}

	/**
	 * @param jsonConfiguration
	 *            The jsonConfiguration to set.
	 */
	public void setJsonConfiguration (JsonConfiguration jsonConfiguration) {
		this.jsonConfiguration = jsonConfiguration;
	}

	/**
	 * @return
	 *         The sqliteConfiguration.
	 */
	public SqliteConfiguration getSqliteConfiguration () {
		return sqliteConfiguration;
	}

	/**
	 * @param sqliteConfiguration
	 *            The sqliteConfiguration to set.
	 */
	public void setSqliteConfiguration (SqliteConfiguration sqliteConfiguration) {
		this.sqliteConfiguration = sqliteConfiguration;
	}

}

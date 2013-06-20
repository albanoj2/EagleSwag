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

package com.oceans7.mobile.eagleswag.config;

import java.util.Map;

import android.content.Context;

import com.oceans7.mobile.eagleswag.config.components.DataConfiguration;
import com.oceans7.mobile.eagleswag.config.components.JsonConfiguration;
import com.oceans7.mobile.eagleswag.config.components.NoJsonConfigurationException;
import com.oceans7.mobile.eagleswag.config.components.NoSqliteConfigurationException;
import com.oceans7.mobile.eagleswag.config.components.SqliteConfiguration;
import com.oceans7.mobile.eagleswag.domain.questions.Question;
import com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy;

/**
 * A helper class that contains methods for obtaining specific configuration
 * data for a question type. This encapsulates the logic for obtaining the
 * configuration data for a question type in a singular location.
 * 
 * <p>
 * <strong>Note:</strong> As more data is added to the configuration file, this
 * class can be expanded in order to encapsulate the logic for obtaining the new
 * configuration data for a question type.
 * </p>
 * 
 * @author Justin Albano
 */
public class ConfigurationHelper {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of the configuration helper.
	 */
	private static ConfigurationHelper instance;

	/**
	 * The configuration controller that is used to access the configuration
	 * data from the configuration file. This controller acts as the mediator
	 * between this helper class and the configuration file.
	 */
	private ConfigurationController configurationController;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Private constructor that obtains a reference to a configuration
	 * controller (which is used to access the configuration file).
	 */
	private ConfigurationHelper () {

		// Set the controller to the configuration proxy
		this.configurationController = new ConfigurationProxy();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains an instance of the singleton.
	 * 
	 * @return
	 *         A singleton instance of the configuration helper.
	 */
	public static synchronized ConfigurationHelper getInstance () {

		if (instance == null) {
			// Lazy instantiation of singleton instance
			instance = new ConfigurationHelper();
		}

		return instance;
	}

	/**
	 * Obtain a map of all of the question types in the configuration file. This
	 * method reduces the need to instantiate a configuration controller and
	 * then obtain the question types from the controller: Instead, this process
	 * is encapsulated within this method as a single operation.
	 * 
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 *         A map of question classes to question types. The key for the
	 *         table is a question class (such as GeneralQuestion.class).
	 */
	public Map<Class<? extends Question>, QuestionType> getAllQuestionTypes (Context context) {

		// Delegate the retrieval of the question types to the controller
		return this.configurationController.getQuestionTypes(context);
	}

	/**
	 * Obtains the data asset location for a question type in the configuration
	 * file. The data asset is a combination of the asset path and file name in
	 * the configuration file for the question type requested.
	 * 
	 * @param key
	 *            The question class whose data asset location is requested. For
	 *            example, if the data asset location of the general questions
	 *            is requested, the key would be GeneralQuestion.class. This key
	 *            is specified in the configuration file under the 'key'
	 *            element.
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 *         The location of the data asset for the question class specified.
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 */
	public <T extends Question> String getDataAsset (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}

		// Return the data asset of the question type
		DataConfiguration dataConfiguration = qt.getDataConfiguration();

		if (dataConfiguration != null) {
			// The asset location is set
			return dataConfiguration.getAsset();
		}
		else {
			return null;
		}
	}

	/**
	 * Obtains the data parsing strategy for a question type in the
	 * configuration file. The parser strategy is returned as a class object of
	 * the parser, as a subtype of the DataFileParserStrategy class. For
	 * example, if the parser is specified as 'XYZParserStrategy', the class
	 * returned by this method is 'Class<XYZParserStrategy extends
	 * DataFileParserStrategy>'.
	 * 
	 * @param key
	 *            The question class whose data asset location is requested. For
	 *            example, if the data asset location of the general questions
	 *            is requested, the key would be GeneralQuestion.class. This key
	 *            is specified in the configuration file under the 'key'
	 *            element.
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 */
	public <T extends Question> Class<? extends DataFileParserStrategy> getParserStrategy (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}

		// Return the parser strategy of the question type
		DataConfiguration dataConfiguration = qt.getDataConfiguration();

		if (dataConfiguration != null) {
			// The parser strategy for the type is specified
			return dataConfiguration.getParserStrategy();
		}
		else {
			return null;
		}

	}

	/**
	 * Obtains the SQLite table name for a question type in the configuration
	 * file (maybe be null).
	 * 
	 * @note The table name may be null if it is not specified for a question
	 *       type in the configuration file. This may occur if a different
	 *       persistent storage is method is used (not SQLite database).
	 * 
	 * @param key
	 *            The question class whose data asset location is requested. For
	 *            example, if the data asset location of the general questions
	 *            is requested, the key would be GeneralQuestion.class. This key
	 *            is specified in the configuration file under the 'key'
	 *            element.
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 *         The name of the SQLite database table that corresponds to the key
	 *         provided.
	 * 
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 * @throws NoSqliteConfigurationException
	 *             No SQLite table name configuration data set for the question
	 *             type supplied.
	 */
	public <T extends Question> String getTableName (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}

		// Return the SQLite database table name of the question type
		SqliteConfiguration sqliteConfiguration = qt.getSqliteConfiguration();

		if (sqliteConfiguration != null) {
			// The SQLite table is set
			return sqliteConfiguration.getTable();
		}
		else {
			// No SQLite table specified in the configuration file
			throw new NoSqliteConfigurationException("No database table name specified");
		}

	}

	/**
	 * Obtains the JSON ID tag for a question type in the configuration file
	 * (may be null).
	 * 
	 * @note The JSON ID may be null if it is not specified for a question type
	 *       in the configuration file. This may occur if another data file
	 *       controller is used (other than JSON).
	 * 
	 * @param key
	 *            The question class whose data asset location is requested. For
	 *            example, if the data asset location of the general questions
	 *            is requested, the key would be GeneralQuestion.class. This key
	 *            is specified in the configuration file under the 'key'
	 *            element.
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 *         The name of the JSON ID tag in the JSON data file that
	 *         corresponds to the key.
	 * 
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 * @throws NoJsonConfigurationException
	 *             No JSON ID configuration data set for the question type
	 *             supplied.
	 */
	public <T extends Question> String getJsonId (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}

		// Return the JSON element ID of the question type
		JsonConfiguration jsonConfiguration = qt.getJsonConfiguration();

		if (jsonConfiguration != null) {
			// The ID for the JSON configuration is set
			return jsonConfiguration.getId();
		}
		else {
			// No JSON table specified in the configuration file
			throw new NoJsonConfigurationException("No JSON ID specified");
		}

	}
}

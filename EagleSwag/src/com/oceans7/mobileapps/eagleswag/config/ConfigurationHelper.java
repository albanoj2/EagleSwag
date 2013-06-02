/**
 * @author Justin Albano
 * @date Jun 1, 2013
 * @file ConfigurationHelper.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A helper class that contains methods for obtaining specific
 *       configuration data for a question type. This encapsulates the logic for
 *       obtaining the configuration data for a question type in a singular
 *       location.
 * 
 * @note As more data is added to the configuration file, this class can be
 *       expanded in order to encapsulate the logic for obtaining the new
 *       configuration data for a question type.
 */

package com.oceans7.mobileapps.eagleswag.config;

import java.util.Map;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParserStrategy;

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

		// Obtain the configuration controller from the configuration factory
		this.configurationController = ConfigurationControllerFactory.getInstance().getController();
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
		else {
			// Return the data asset of the question type
			return qt.getDataAsset();
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
		else {
			// Return the parser strategy of the question type
			return qt.getParserStrategy();
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
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 */
	public <T extends Question> String getTableName (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}
		else {
			// Return the SQLite database table name of the question type
			return qt.getSqliteTable();
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
	 * @throws NoSuchQuestionTypeException
	 *             No question type with the key provided was found in the
	 *             configuration file.
	 */
	public <T extends Question> String getJsonId (Class<T> key, Context context) throws NoSuchQuestionTypeException {

		// Obtain the question type for the key from the configuration file
		QuestionType qt = this.configurationController.getQuestionTypes(context).get(key);

		if (qt == null) {
			// No such question type exists in the configuration file
			throw new NoSuchQuestionTypeException(key);
		}
		else {
			// Return the JSON element ID of the question type
			return qt.getJsonId();
		}

	}
}

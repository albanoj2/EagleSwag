/**
 * @author Justin Albano
 * @date Jun 1, 2013
 * @file ConfigurationHelper.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.config;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.persistence.DataFileParserStrategy;

public class ConfigurationHelper {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static ConfigurationHelper instance;
	private ConfigurationController configurationController;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	private ConfigurationHelper () {
		this.configurationController = ConfigurationControllerFactory.getInstance().getController();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	public static synchronized ConfigurationHelper getInstance () {

		if (instance == null) {
			// Lazy instantiation of singleton instance
			instance = new ConfigurationHelper();
		}

		return instance;
	}

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

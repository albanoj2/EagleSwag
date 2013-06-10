/**
 * @author Justin Albano
 * @date Jun 7, 2013
 * @file QuestionStrategy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 * TODO Documentation
 * FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

public abstract class QuestionStrategy {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The asset location of the question controller configuration resource.
	 */
	private static final String QUESTION_CONTROLLER_CONFIG_ASSET = "config/domain/question-distribution.cfg";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	List<Question> getQuestions (Context context, Class<? extends Question> specificKey, String propertiesKey) {

		// Create question list
		List<Question> questionList = new LinkedList<Question>();

		// Obtain the data controller from the data controller factory
		DataController dataController = DataControllerFactory.getInstance().getDataController(context);

		// Open the data controller
		dataController.open(context);

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			InputStream is = context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET);
			properties.load(is);
			is.close();

			// The number of specific topic questions that should be loaded
			int specificCount = Integer.parseInt(properties.getProperty("questions.distribution." + propertiesKey + ".specific"));
			Log.i(this.getClass().getName(), "(" + specificCount + ") topic specific questions should be loaded");

			// The number of general questions that should be loaded
			int generalQCount = Integer.parseInt(properties.getProperty("questions.distribution." + propertiesKey + ".general"));
			Log.i(this.getClass().getName(), "(" + generalQCount + ") general questions should be loaded");

			// Obtain specific topic questions and add them to question queue
			Queue<? extends Question> specificQuestions = dataController.getQuestions(specificKey, specificCount);
			questionList.addAll(specificQuestions);

			// Obtain the general questions and add them to the question queue
			Queue<GeneralQuestion> generalQuestions = dataController.getQuestions(GeneralQuestion.class, generalQCount);
			questionList.addAll(generalQuestions);

			// Shuffle the list before returning it
			Collections.shuffle(questionList);

			// Return the correctly built list
			return questionList;
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e(this.getClass().getName(), "Configuration file could not be found: " + e);
			return null;
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e(this.getClass().getName(), "IOException occurred while trying to access the confiuration file: " + e);
			return null;
		}
	}
	
	public abstract List<Question> getQuestions (Context context);
	
	public abstract String getName ();

}

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

package com.oceans7.mobile.eagleswag.domain.roundtype;

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

import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.questions.GeneralQuestion;
import com.oceans7.mobile.eagleswag.persistence.DataController;
import com.oceans7.mobile.eagleswag.persistence.DataControllers;

/**
 * A delegate dedicated to encapsulating the logic for obtaining a combination
 * of specific type questions and general questions. Concrete strategies should
 * use this class as a delegate for implementing the getQuestions() method of
 * the RoundType interface.
 * 
 * @author Justin Albano
 */
public class RoundTypeHelper {

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

	/**
	 * A delegate method to obtain a queue containing a combination of general
	 * and specific type questions. This method should be used to help create a
	 * concrete implementation of a question strategy.
	 * 
	 * @param context
	 *            The context used to access the questions stored in persistent
	 *            storeage.
	 * @param specificKey
	 *            The type (class) of questions that should be mixed with the
	 *            general questions in the collection of questions returned by
	 *            this method.
	 * @param propertiesKey
	 *            The key used in the properties file that contains the
	 *            distribution configuration data to specify the configuration
	 *            data for the concrete question strategy implementing the
	 *            RoundType interface.
	 * @return
	 *         A list containing a combination of general questions, and
	 *         questions of the class provided as the specificKey parameter.
	 */
	public List<Question> getQuestions (Context context, Class<? extends Question> specificKey, String propertiesKey) {

		// Create question list
		List<Question> questionList = new LinkedList<Question>();

		// Obtain the data controller from the data controller factory
		DataController dataController = DataControllers.getInstance().getDataController(context);

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
}

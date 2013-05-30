/**
 * @author Justin Albano
 * @date May 30, 2013
 * @file QuestionController.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Performs the operations required to obtain a list of engineering or
 *       pilot questions to be used in the user interface. This class acts as
 *       the facade between the user interface and the domain subsystem.
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

public class QuestionController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of the question controller.
	 */
	private static QuestionController instance;

	/**
	 * The context used to asset the controller configuration asset.
	 */
	private Context context;

	/**
	 * The asset location of the question controller configuration resource.
	 */
	private static final String QUESTION_CONTROLLER_CONFIG_ASSET = "config/domain/question-controller.cfg";

	/**
	 * The data controller that manages the storage and retrieval of questions.
	 */
	private DataController dataController;

	/***************************************************************************
	 * Constructor
	 **************************************************************************/

	/**
	 * Hidden constructor. Sets the data controller upon instantiation.
	 * 
	 * @param context
	 *            The context used to obtain the configuration resource file.
	 */
	private QuestionController (Context context) {

		// Set the context for the controller
		this.context = context;

		// Obtain the data controller from the data controller factory
		this.dataController = DataControllerFactory.getInstance().getDataController(context);

		// Open the data controller
		this.dataController.open(context);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtain the singleton instance for the controller.
	 * 
	 * @param context
	 *            The context used to obtain the configuration data for the
	 *            question controller.
	 * 
	 * @return
	 *         The singleton instance for the controller.
	 */
	public static QuestionController getInstance (Context context) {

		if (instance == null) {
			// Lazy instantiation of singleton
			instance = new QuestionController(context);
		}

		// Return the question controller
		return instance;
	}

	/**
	 * Loads a list of questions used for engineers. This list contains a mix
	 * of engineering and general questions, based on the ratio specified by the
	 * configuration file. Likewise, the number of total questions to be loaded
	 * into the list is specified in the configuration file.
	 * 
	 * @return
	 *         A list containing a mix of engineering and general questions,
	 *         based on the specification set in the configuration file for the
	 *         question controller.
	 */
	public List<Question> getEngineeringQuestions () {

		// Create question list
		List<Question> questionList = new LinkedList<Question>();

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			InputStream is = this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET);
			properties.load(is);
			is.close();

			// The number of engineering questions that should be loaded
			int engineeringQCount = Integer.parseInt(properties.getProperty("questionController.engineering.engineeringQuestions"));
			Log.i(this.getClass().getName(), "(" + engineeringQCount + ") engineering questions should be loaded");

			// The number of general questions that should be loaded
			int generalQCount = Integer.parseInt(properties.getProperty("questionController.engineering.generalQuestions"));
			Log.i(this.getClass().getName(), "(" + generalQCount + ") general questions should be loaded");

			// Obtain the engineering questions and add them to the question
			// queue
			Queue<EngineeringQuestion> engineeringQuestions = this.dataController.<EngineeringQuestion> getQuestions(EngineeringQuestion.class,
				generalQCount);
			questionList.addAll(engineeringQuestions);

			// Obtain the general questions and add them to the question queue
			Queue<GeneralQuestion> generalQuestions = this.dataController.<GeneralQuestion> getQuestions(GeneralQuestion.class, generalQCount);
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

	/**
	 * Loads a list of questions used for pilots. This list contains a mix
	 * of pilot and general questions, based on the ratio specified by the
	 * configuration file. Likewise, the number of total questions to be loaded
	 * into the list is specified in the configuration file.
	 * 
	 * @return
	 *         A list containing a mix of pilot and general questions, based on
	 *         the specification set in the configuration file for the question
	 *         controller.
	 */
	public List<Question> getPilotQuestions () {

		// Create question list
		List<Question> questionList = new LinkedList<Question>();

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			InputStream is = this.context.getAssets().open(QUESTION_CONTROLLER_CONFIG_ASSET);
			properties.load(is);
			is.close();

			// The number of pilot questions that should be loaded
			int pilotQCount = Integer.parseInt(properties.getProperty("questionController.pilot.pilotQuestions"));
			Log.i(this.getClass().getName(), "(" + pilotQCount + ") pilot questions should be loaded");

			// The number of general questions that should be loaded
			int generalQCount = Integer.parseInt(properties.getProperty("questionController.pilot.generalQuestions"));
			Log.i(this.getClass().getName(), "(" + generalQCount + ") general questions should be loaded");

			// Obtain the pilot questions and add them to the question queue
			Queue<PilotQuestion> pilotQuestions = this.dataController.<PilotQuestion> getQuestions(PilotQuestion.class, pilotQCount);
			questionList.addAll(pilotQuestions);

			// Obtain the general questions and add them to the question queue
			Queue<GeneralQuestion> generalQuestions = this.dataController.<GeneralQuestion> getQuestions(GeneralQuestion.class, generalQCount);
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

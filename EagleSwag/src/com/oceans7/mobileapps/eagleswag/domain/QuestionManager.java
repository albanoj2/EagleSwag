/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file QuestionManager.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Performs the operations required to obtain a queue of engineering or
 *       pilot questions to be used in the user interface. This class acts as
 *       the facade between the user interface and the domain subsystem.
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;

public class QuestionManager {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of the question manager.
	 */
	private static QuestionManager instance;

	/**
	 * The ID of the question manager configuration resource.
	 */
	private static final int QUESTION_MANAGER_CONFIG_RES = com.oceans7.mobileapps.eagleswag.R.raw.questionmanager;

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
	private QuestionManager (Context context) {

		try {
			// Obtain the class name of the data controller
			Properties properties = new Properties();
			properties.load(context.getResources().openRawResource(QUESTION_MANAGER_CONFIG_RES));
			String dataControllerName = properties.getProperty("questionManager.dataController.classname");
			Log.i("Question Manager", "Configuration file specified data controller as " + dataControllerName);

			// Reflexively set the data controller
			this.dataController = (DataController) Class.forName(dataControllerName).newInstance();
			Log.i("Question Manager", "Data controller set to " + dataControllerName);
		}
		catch (InstantiationException e) {
			// Error occurred while instantiating data controller
			Log.e("Question Manager", "Error occurred while instantiating data controller: " + e);
		}
		catch (IllegalAccessException e) {
			// Illegal access occurred when trying to instantiate
			Log.e("Question Manager", "Illegal access occurred while setting data controller: " + e);
		}
		catch (ClassNotFoundException e) {
			// The data controller class could not be found
			Log.e("Question Manager", "Data controller class cannot be found: " + e);
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e("Question Manager", "Configuration file could not be found: " + e);
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e("Question Manager", "IOException occurred while trying to access the confiuration file: " + e);
		}

	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtain the singleton instance for the manager.
	 * 
	 * @param context
	 *            The context used to obtain the configuration data for the
	 *            question manager.
	 * 
	 * @return
	 *         The singleton instance for the manager.
	 */
	public static QuestionManager getInstance (Context context) {

		if (instance == null) {
			// Lazy instantiation
			instance = new QuestionManager(context);
		}

		// Return the question manager
		return instance;
	}

	/**
	 * Loads a queue of questions used for engineers. This queue contains a mix
	 * of engineering and general questions, based on the ratio specified by the
	 * configuration file. Likewise, the number of total questions to be loaded
	 * into the queue is specified in the configuration file.
	 * 
	 * @param context
	 *            The context of the activity requesting the engineering
	 *            question queue.
	 * @return
	 *         A queue containing a mix of engineering and general questions,
	 *         based on the specification set in the configuration file for the
	 *         question manager.
	 */
	public Queue<Question> getEngineeringQuestions (Context context) {

		// Create question queue
		Queue<Question> questionQueue = new LinkedList<Question>();

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			properties.load(context.getResources().openRawResource(QUESTION_MANAGER_CONFIG_RES));

			// The number of engineering questions that should be loaded
			int engineeringQCount = Integer.parseInt(properties.getProperty("questionManager.engineering.engineeringQuestions"));
			Log.i("Question Manager", "(" + engineeringQCount + ") engineering questions should be loaded");

			// The number of general questions that should be loaded
			int generalQCount = Integer.parseInt(properties.getProperty("questionManager.engineering.generalQuestions"));
			Log.i("Question Manager", "(" + generalQCount + ") general questions should be loaded");

			// Obtain the engineering questions and add them to the question queue
			Queue<EngineeringQuestion> engineeringQuestions = this.dataController.getEngineeringQuestions(engineeringQCount);
			questionQueue.addAll(engineeringQuestions);

			// Obtain the general questions and add them to the question queue
			Queue<GeneralQuestion> generalQuestions = this.dataController.getGeneralQuestions(generalQCount);
			questionQueue.addAll(generalQuestions);

			// Return the correctly built queue
			return questionQueue;
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e("Question Manager", "Configuration file could not be found: " + e);
			return null;
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e("Question Manager", "IOException occurred while trying to access the confiuration file: " + e);
			return null;
		}

	}

	/**
	 * Loads a queue of questions used for pilots. This queue contains a mix
	 * of pilot and general questions, based on the ratio specified by the
	 * configuration file. Likewise, the number of total questions to be loaded
	 * into the queue is specified in the configuration file.
	 * 
	 * @param context
	 *            The context of the activity requesting the engineering
	 *            question queue.
	 * @return
	 *         A queue containing a mix of pilot and general questions, based on
	 *         the specification set in the configuration file for the question
	 *         manager.
	 */
	public Queue<Question> getPilotQuestions (Context context) {

		// Create question queue
		Queue<Question> questionQueue = new LinkedList<Question>();

		try {
			// Obtain the number of questions that "should" be loaded
			Properties properties = new Properties();
			properties.load(context.getResources().openRawResource(QUESTION_MANAGER_CONFIG_RES));

			// The number of pilot questions that should be loaded
			int pilotQCount = Integer.parseInt(properties.getProperty("questionManager.pilot.pilotQuestions"));
			Log.i("Question Manager", "(" + pilotQCount + ") pilot questions should be loaded");

			// The number of general questions that should be loaded
			int generalQCount = Integer.parseInt(properties.getProperty("questionManager.pilot.generalQuestions"));
			Log.i("Question Manager", "(" + generalQCount + ") general questions should be loaded");

			// Obtain the pilot questions and add them to the question queue
			Queue<PilotQuestion> pilotQuestions = this.dataController.getPilotQuestions(pilotQCount);
			questionQueue.addAll(pilotQuestions);

			// Obtain the general questions and add them to the question queue
			Queue<GeneralQuestion> generalQuestions = this.dataController.getGeneralQuestions(generalQCount);
			questionQueue.addAll(generalQuestions);

			// Return the correctly built queue
			return questionQueue;
		}
		catch (FileNotFoundException e) {
			// The configuration file could not be found
			Log.e("Question Manager", "Configuration file could not be found: " + e);
			return null;
		}
		catch (IOException e) {
			// IOException occurred while trying to access the properties file
			Log.e("Question Manager", "IOException occurred while trying to access the confiuration file: " + e);
			return null;
		}
	}
}

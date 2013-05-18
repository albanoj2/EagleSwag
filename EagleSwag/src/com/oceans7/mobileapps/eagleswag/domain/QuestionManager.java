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

public class QuestionManager {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Singleton instance of the question manager.
	 */
	private static QuestionManager instance = new QuestionManager();

	/***************************************************************************
	 * Constructor
	 **************************************************************************/

	/**
	 * Hidden constructor.
	 */
	private QuestionManager () {}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtain the singleton instance for the manager.
	 * 
	 * @return
	 *         The singleton instance for the manager.
	 */
	public static QuestionManager getInstance () {
		// Eager instantiation of the instance
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
			properties.load(context.getResources().openRawResource(com.oceans7.mobileapps.eagleswag.R.raw.questionmanager));

			// The number of engineering questions that should be loaded
			int engineeringQuestions = Integer.parseInt(properties.getProperty("questionManager.engineering.engineeringQuestions"));
			Log.i("Question Manager", "(" + engineeringQuestions + ") engineering questions should be loaded");

			// The number of general questions that should be loaded
			int generalQuestions = Integer.parseInt(properties.getProperty("questionManager.engineering.generalQuestions"));
			Log.i("Question Manager", "(" + generalQuestions + ") general questions should be loaded");

			for (int i = 0; i < engineeringQuestions; i++) {
				// Create the specified number of engineering questions
				// TODO Offload this task to the data manager

				EngineeringQuestion engQuestion = new EngineeringQuestion(0, null, 0, 0, 0);
				questionQueue.add(engQuestion);
				Log.i("Question Manager", "Added engineering question: " + engQuestion);
			}

			for (int i = 0; i < generalQuestions; i++) {
				// Create the specified number of general questions
				// TODO Offload this task to the data manager

				GeneralQuestion genQuestion = new GeneralQuestion(0, null, 0, 0, 0);
				questionQueue.add(genQuestion);
				Log.i("Question Manager", "Added general question: " + genQuestion);
			}

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
			properties.load(context.getResources().openRawResource(com.oceans7.mobileapps.eagleswag.R.raw.questionmanager));

			// The number of pilot questions that should be loaded
			int pilotQuestions = Integer.parseInt(properties.getProperty("questionManager.pilot.pilotQuestions"));
			Log.i("Question Manager", "(" + pilotQuestions + ") pilot questions should be loaded");

			// The number of general questions that should be loaded
			int generalQuestions = Integer.parseInt(properties.getProperty("questionManager.pilot.generalQuestions"));
			Log.i("Question Manager", "(" + generalQuestions + ") general questions should be loaded");

			for (int i = 0; i < pilotQuestions; i++) {
				// Create the specified number of pilot questions
				// TODO Offload this task to the data manager

				Question pilotQuestion = new PilotQuestion(0, null, 0, 0, 0);
				questionQueue.add(pilotQuestion);
				Log.i("Question Manager", "Added pilot question: " + pilotQuestion);
			}

			for (int i = 0; i < generalQuestions; i++) {
				// Create the specified number of general questions
				// TODO Offload this task to the data manager

				GeneralQuestion genQuestion = new GeneralQuestion(0, null, 0, 0, 0);
				questionQueue.add(genQuestion);
				Log.i("Question Manager", "Added general question: " + genQuestion);
			}

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

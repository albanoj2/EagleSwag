/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file QuestionManager.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.LinkedList;
import java.util.Queue;

public class QuestionManager {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static QuestionManager instance = new QuestionManager();

	/***************************************************************************
	 * Constructor
	 **************************************************************************/

	private QuestionManager () {
	}

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

	public Queue<Question> getEngineeringQuestions () {
		
		// Create question queue
		Queue<Question> questionQueue = new LinkedList<Question>();
		
		return questionQueue;
	}

	public Queue<Question> getPilotQuestions () {
		return null;
	}
}

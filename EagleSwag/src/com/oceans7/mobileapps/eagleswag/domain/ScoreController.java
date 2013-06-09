/**
 * @author Justin Albano
 * @date Jun 9, 2013
 * @file ScoreController.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Domain class responsible for retrieving the data (such as average score
 *       and total score) for scores that were previously saved in persistent
 *       storage.
 */

package com.oceans7.mobileapps.eagleswag.domain;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

public class ScoreController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used to access the data controller.
	 */
	private Context context;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that specifies context used to access the data
	 * controller.
	 * 
	 * @param context
	 *            The context used to access the data controller.
	 */
	public ScoreController (Context context) {
		this.context = context;
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtain the total score for a question strategy.
	 * 
	 * @param strategy
	 *            The strategy used to select which scores to return. For
	 *            example, if the scores for all engineering rounds are desired,
	 *            the EngineeringStrategy should be supplied.
	 * @return
	 *         The total score for the rounds associated with the supplied
	 *         strategy.
	 */
	public int getTotalScore (QuestionStrategy strategy) {

		// Create and open the data controller
		DataController dataController = DataControllerFactory.getInstance().getDataController(this.context);
		dataController.open(this.context);

		// Obtain the score from the data controller
		int score = dataController.getTotalScore(strategy.getName());

		// Close the data controller
		dataController.close();

		return score;
	}

	/**
	 * Obtain the average score for a question strategy.
	 * 
	 * @param strategy
	 *            The strategy used to select which average score to return. For
	 *            example, if the average score for all engineering rounds is
	 *            desired, the EngineeringStrategy should be supplied.
	 * @return
	 *         The average score for the rounds associated with the supplied
	 *         strategy.
	 */
	public int getAverageScore (QuestionStrategy strategy) {

		// Create and open the data controller
		DataController dataController = DataControllerFactory.getInstance().getDataController(this.context);
		dataController.open(this.context);

		// Obtain the average score from the data controller
		int average = dataController.getAverageScore(strategy.getName());

		// Close the data controller
		dataController.close();

		return average;
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The context.
	 */
	public Context getContext () {
		return context;
	}

	/**
	 * @param context
	 *            The context to set.
	 */
	public void setContext (Context context) {
		this.context = context;
	}

}

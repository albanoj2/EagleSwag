/**
 * @author Justin Albano
 * @date Jun 9, 2013
 * @file ScoreController.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

public class ScoreController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private DataController dataController;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that requires the context used to access the
	 * data for the previously saved rounds.
	 * 
	 * @param context
	 *            The context used to access the data for the previously saved
	 *            rounds.
	 */
	public ScoreController (Context context) {
		this.dataController = DataControllerFactory.getInstance().getDataController(context);
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
		return this.dataController.getTotalScore(strategy.getName());
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
		return this.dataController.getAverageScore(strategy.getName());
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The dataController.
	 */
	public DataController getDataController () {
		return dataController;
	}

	/**
	 * @param dataController
	 *            The dataController to set.
	 */
	public void setDataController (DataController dataController) {
		this.dataController = dataController;
	}

}

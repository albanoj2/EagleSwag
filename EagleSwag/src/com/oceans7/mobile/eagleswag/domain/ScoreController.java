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

package com.oceans7.mobile.eagleswag.domain;

import android.content.Context;

import com.oceans7.mobile.eagleswag.persistence.DataController;
import com.oceans7.mobile.eagleswag.persistence.DataControllers;

/**
 * Domain class responsible for retrieving the data (such as average score and
 * total score) for scores that were previously saved in persistent storage.
 * 
 * @author Justin Albano
 */
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
	 *            the EngineeringRoundType should be supplied.
	 * @return
	 *         The total score for the rounds associated with the supplied
	 *         strategy.
	 */
	public int getTotalScore (RoundType strategy) {

		// Create and open the data controller
		DataController dataController = DataControllers.getInstance().getDataController(this.context);

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
	 *            desired, the EngineeringRoundType should be supplied.
	 * @return
	 *         The average score for the rounds associated with the supplied
	 *         strategy.
	 */
	public int getAverageScore (RoundType strategy) {

		// Create and open the data controller
		DataController dataController = DataControllers.getInstance().getDataController(this.context);

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

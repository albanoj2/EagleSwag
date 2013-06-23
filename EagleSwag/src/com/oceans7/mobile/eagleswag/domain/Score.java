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
import android.util.Log;

import com.oceans7.mobile.eagleswag.persistence.DataController;
import com.oceans7.mobile.eagleswag.persistence.DataControllers;

/**
 * A score for a round of questions.
 * 
 * @author Justin Albano
 */
public class Score {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The value of the score.
	 */
	private int score;

	/**
	 * The timestamp of when the score was recorded.
	 */
	private long timestamp;

	/**
	 * The type of round the score is attributed to.
	 */
	private RoundType type;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor (automatically sets the timestamp)
	 * 
	 * @param type
	 *            The type of round the score is attributed to.
	 * 
	 * @param score
	 *            The score value.
	 */
	public Score (RoundType type, int score) {

		// Set the data for the score
		this.setScore(score);
		this.setTimestamp(System.currentTimeMillis() / 1000L);
		this.setType(type);
	}

	/**
	 * A constructor used to set only the type of round (the score defaults to
	 * 0).
	 * 
	 * @param type
	 *            The type of round the score is attributed to.
	 */
	public Score (RoundType type) {
		this(type, 0);
	}

	/**
	 * A constructor used to set only the score of the round (the round type is
	 * set to null).
	 * 
	 * @param score
	 *            The score.
	 */
	public Score (int score) {
		this(null, score);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Saves the round in persistent storage.
	 * 
	 * @param strategy
	 *            The strategy used to save the round.
	 */
	public void save (Context context) {

		// Obtain a reference to a data controller and open the controller
		DataController controller = DataControllers.getInstance().getDataController(context);

		// Save the score
		controller.saveRoundScore(this, this.getType());

		// Log the score at the time of saving
		Log.i(this.getClass().getName(), "Saving score of " + this.getScore());

		// Close the controller
		controller.close();

	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The score.
	 */
	public int getScore () {
		return score;
	}

	/**
	 * @param score
	 *            The score to set.
	 */
	public void setScore (int score) {
		this.score = score;
	}

	/**
	 * @return
	 *         The timestamp.
	 */
	public long getTimestamp () {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	public void setTimestamp (long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 *         The type.
	 */
	public String getType () {
		return type.getName();
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType (RoundType type) {
		this.type = type;
	}

}

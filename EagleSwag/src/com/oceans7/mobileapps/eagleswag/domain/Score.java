/**
 * @author Justin Albano
 * @date Jun 8, 2013
 * @file Score.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A score for a round of questions.
 *       
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.domain;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

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

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Default constructor (automatically sets the timestamp)
	 */
	public Score () {
		this.setTimestamp(System.currentTimeMillis() / 1000L);
	}

	/**
	 * Parameterized constructor that allows for the initialization of the score
	 * and the timestamp of when the score was recorded (automatically sets the
	 * timestamp).
	 * 
	 * @param score
	 *            The score value.
	 */
	public Score (int score) {

		// Set the data for the round score
		this.setScore(score);
		this.setTimestamp(System.currentTimeMillis() / 1000L);
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
	public void save (QuestionStrategy strategy, Context context) {

		// Obtain a reference to a data controller and open the controller
		DataController controller = DataControllerFactory.getInstance().getDataController(context);
		controller.open(context);

		// Save the score
		controller.saveRoundScore(this, strategy.getName());
		
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

}

/**
 * @author Justin Albano
 * @date Jun 8, 2013
 * @file Score.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A score for a round of questions.
 */

package com.oceans7.mobileapps.eagleswag.domain;

public class Score {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The value of the score.
	 */
	private double score;

	/**
	 * The timestamp of when the score was recorded.
	 */
	private long timestamp;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Default constructor.
	 */
	public Score () {}

	/**
	 * Parameterized constructor that allows for the initialization of the score
	 * and the timestamp of when the score was recorded.
	 * 
	 * @param score
	 *            The score value.
	 * @param timestamp
	 *            When the score was recorded (the UNIX timestamp).
	 */
	public Score (double score, long timestamp) {

		// Set the data for the round score
		this.setScore(score);
		this.setTimestamp(timestamp);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Saves the round in persistent storage.
	 */
	public void save () {}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The score.
	 */
	public double getScore () {
		return score;
	}

	/**
	 * @param score
	 *            The score to set.
	 */
	public void setScore (double score) {
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

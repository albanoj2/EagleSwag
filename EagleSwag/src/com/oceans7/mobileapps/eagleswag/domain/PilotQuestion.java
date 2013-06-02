/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file PilotQuestion.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       The concrete implementation of a general question.
 */

package com.oceans7.mobileapps.eagleswag.domain;


public class PilotQuestion extends Question {
	
	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * @param id
	 *            The ID of the question.
	 * @param text
	 *            The text of the question.
	 * @param yesValue
	 *            The yes value of the question.
	 * @param noValue
	 *            The no value of the question.
	 * @param usedCount
	 *            The used count of the question.
	 */
	public PilotQuestion (Integer id, String text, Integer yesValue, Integer noValue, Integer usedCount) {
		super(id, text, yesValue, noValue, usedCount);
	}

}

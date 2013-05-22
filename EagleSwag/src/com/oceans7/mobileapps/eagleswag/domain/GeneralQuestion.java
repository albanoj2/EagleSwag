/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file GeneralQuestion.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO: Documentation
 */

package com.oceans7.mobileapps.eagleswag.domain;

public class GeneralQuestion extends Question {

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
	public GeneralQuestion (Integer id, String text, Integer yesValue, Integer noValue, Integer usedCount) {
		super(id, text, yesValue, noValue, usedCount);
	}

}

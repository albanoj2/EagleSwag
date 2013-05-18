/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file EngineeringQuestion.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 */

package com.oceans7.mobileapps.eagleswag.domain;

public class EngineeringQuestion extends ConcreteQuestion {

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
	public EngineeringQuestion (int id, String text, int yesValue, int noValue, int usedCount) {
		super(id, text, yesValue, noValue, usedCount);
	}

}

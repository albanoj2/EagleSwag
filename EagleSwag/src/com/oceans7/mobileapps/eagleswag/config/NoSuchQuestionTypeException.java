/**
 * @author Justin Albano
 * @date Jun 1, 2013
 * @file NoSuchQuestionTypeException.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class NoSuchQuestionTypeException extends RuntimeException {

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -3917411392788086939L;

	public <T extends Question> NoSuchQuestionTypeException (Class<T> key) {
		super("Could not find question of type '" + key.getName() + "' in the configuration file");
	}
}

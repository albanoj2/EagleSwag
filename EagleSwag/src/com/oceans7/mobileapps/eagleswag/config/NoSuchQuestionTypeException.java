/**
 * @author Justin Albano
 * @date Jun 1, 2013
 * @file NoSuchQuestionTypeException.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Runtime exception thrown when a question type is requested, but the key
 *       cannot be found in the configuration file. As a runtime exception, this
 *       exception is unchecked and is not required to be caught.
 */

package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class NoSuchQuestionTypeException extends RuntimeException {
	
	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -3917411392788086939L;
	
	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Constructor that includes the key used to access the question type. The
	 * key is included in the exception message.
	 * 
	 * @param key
	 *            Key used to access the non-existent question type in the
	 *            configuration file.
	 */
	public <T extends Question> NoSuchQuestionTypeException (Class<T> key) {
		super("Could not find question of type '" + key.getName() + "' in the configuration file");
	}
}

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

package com.oceans7.mobileapps.eagleswag.config;

import com.oceans7.mobileapps.eagleswag.domain.Question;

/**
 * Runtime exception thrown when a question type is requested, but the key
 * cannot be found in the configuration file. As a runtime exception, this
 * exception is unchecked and is not required to be caught.
 * 
 * @author Justin Albano
 */
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

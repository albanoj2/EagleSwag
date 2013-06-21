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

package com.oceans7.mobile.eagleswag.config;

import java.util.Map;

import android.content.Context;

import com.oceans7.mobile.eagleswag.domain.Question;

/**
 * Interface defining the methods for controlling the configuration file that
 * contains question types. This interface defines the external operations in
 * order to provide for the proxy pattern used by the configuration system.
 * 
 * @author Justin Albano
 */
public interface ConfigurationController {

	/**
	 * Retrieves the question types stored in the configuration file.
	 * 
	 * @param context
	 *            The context used to access the configuration file.
	 * @return
	 *         A map containing the question types from the configuration file.
	 *         The key for the map is the class of the question type (for
	 *         example, GeneralQuestion.class for the GeneralQuestion type). The
	 *         map key is defined within the configuration file under the 'key'
	 *         element.
	 */
	public Map<Class<? extends Question>, QuestionType> getQuestionTypes (Context context);

}

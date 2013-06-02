/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfig.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Interface defining the methods for controlling the configuration file
 *       that contains question types. This interface defines the external
 *       operations in order to provide for the proxy pattern used by the
 *       configuration system.
 */

package com.oceans7.mobileapps.eagleswag.config;

import java.util.Map;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;

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

/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file ConfigurationProxy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A proxy for the ConfigurationParser. This proxy acts a cache for the
 *       parser. If the configuration has not been parsed, it will be parsed.
 *       Once the configuration file has been parsed, and loaded into memory,
 *       the proxy simply obtains the configuration data from the in-memory
 *       configuration data. This reduces the overhead associated with accessing
 *       the data in the configuration file. A visualization of this proxied
 *       cache is illustrated below:
 * 
 *       <pre>
 *       	  Proxy				 Parser
 *   ==========>|					|	1st access
 *       		|==================>|
 *       		|<==================|
 *   <==========|					|
 *       		|					|
 * 	 ==========>|					|	2nd access
 * 	 <==========|					|
 * 				|					|
 * 	 ==========>|					|	3rd access
 * 	 <==========|					|
 * 				|					|
 * </pre>
 */

package com.oceans7.mobileapps.eagleswag.config;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class ConfigurationProxy implements ConfigurationController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The configuration controller that is used to parse the configuration
	 * file. This parse acts as the delegate when the cache is empty (performs
	 * the parsing the first time the configuration file is accessed).
	 */
	private ConfigurationController delegate;

	/**
	 * A map of the question type keys (classes that extend the Question class)
	 * to the question types in the configuration file.
	 */
	private Map<Class<? extends Question>, QuestionType> questionTypeMap;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Constructor that sets the delegate (the configuration file parser) for
	 * the proxy.
	 */
	public ConfigurationProxy () {

		// Establish the configuration parser as the delegate
		this.delegate = new ConfigurationParser();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.config.ConfigurationController#getQuestionTypes(java.lang.Class)
	 */
	@Override
	public Map<Class<? extends Question>, QuestionType> getQuestionTypes (Context context) {

		if (this.questionTypeMap == null) {
			// Create the map from the delegate
			this.questionTypeMap = this.delegate.getQuestionTypes(context);
			Log.i(this.getClass().getName(), "Obtaining map from delegate");
		}

		// Ensure the map is not null
		assert this.questionTypeMap != null;

		// Return the map
		return this.questionTypeMap;
	}

}

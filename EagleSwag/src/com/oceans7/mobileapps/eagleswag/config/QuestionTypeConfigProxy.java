/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfigProxy.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.config;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class QuestionTypeConfigProxy implements QuestionTypeConfigController {
	
	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	
	private QuestionTypeConfigController delegate;
	private Map<Class<? extends Question>, QuestionType> questionTypeMap;
	
	/***************************************************************************
	 * Constructors
	 **************************************************************************/
	
	public QuestionTypeConfigProxy () {
		
		// Establish the configuration parser as the delegate
		this.delegate = new QuestionTypeConfigParser();
	}
	
	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * @see com.oceans7.mobileapps.eagleswag.config.QuestionTypeConfigController#getQuestionTypes(java.lang.Class)
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

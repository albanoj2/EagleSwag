/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file QuestionTypeConfig.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 * TODO Documentation
 */

package com.oceans7.mobileapps.eagleswag.config;

import java.util.Map;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public interface QuestionTypeConfigController {
	
	public Map<Class<? extends Question>, QuestionType> getQuestionTypes (Context context);

}

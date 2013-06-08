/**
 * @author Justin Albano
 * @date Jun 7, 2013
 * @file EngineeringStrategy.java
 * 
 * Oceans7 Software
 * EagleSwag Android Mobile App
 *
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.List;

import android.content.Context;

public class EngineeringStrategy extends QuestionStrategy {

	/**
	 * {@inheritDoc}
	 * @see com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy#getQuestions()
	 */
	@Override
	public List<Question> getQuestions (Context context) {
		return super.getQuestions(context, EngineeringQuestion.class, "engineer");
	} 
}

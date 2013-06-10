/**
 * @author Justin Albano
 * @date Jun 7, 2013
 * @file PilotStrategy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 *       
 *       TODO Documentation
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.List;

import android.content.Context;

public class PilotStrategy extends QuestionStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy#getQuestions(android.content.Context)
	 */
	@Override
	public List<Question> getQuestions (Context context) {
		return super.getQuestions(context, PilotQuestion.class, "pilot");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy#getName()
	 */
	@Override
	public String getName () {
		return "pilot";
	}

}

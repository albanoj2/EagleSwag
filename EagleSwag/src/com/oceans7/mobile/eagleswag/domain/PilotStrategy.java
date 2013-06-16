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

package com.oceans7.mobile.eagleswag.domain;

import java.util.List;

import android.content.Context;

/**
 * A concrete implementation of a question strategy for pilot. This strategy is
 * designed to obtain questions from the database for a pilot round of
 * questions. This question strategy will return a combination of pilot and
 * general questions (there may not be pilot questions, or there may not be
 * general questions, but there is the possibility of returning both). The name
 * of this pilot strategy is commonly used as a string key used to associate
 * scores in persistent storage with round of questions for an pilot.
 * 
 * @author Justin Albano
 */
public class PilotStrategy implements QuestionStrategy {

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.domain.QuestionStrategy#getQuestions(android.content.Context)
	 */
	@Override
	public List<Question> getQuestions (Context context) {
		
		// Create a delegate for obtaining questions from persistent storage
		QuestionStrategyDelegate delegate = new QuestionStrategyDelegate();
		return delegate.getQuestions(context, PilotQuestion.class, "pilot");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.domain.QuestionStrategy#getName()
	 */
	@Override
	public String getName () {
		return "pilot";
	}

}

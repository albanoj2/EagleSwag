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

package com.oceans7.mobile.eagleswag.domain.roundtype;

import java.util.List;

import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.RoundType;
import com.oceans7.mobile.eagleswag.domain.questions.EngineeringQuestion;

import android.content.Context;

/**
 * A concrete implementation of a question strategy for engineering. This
 * strategy is designed to obtain questions from the database for a engineering
 * round of questions. This question strategy will return a combination of
 * engineering and general questions (there may not be engineering questions, or
 * there may not be general questions, but there is the possibility of returning
 * both). The name of this engineering strategy is commonly used as a string key
 * used to associate scores in persistent storage with round of questions for an
 * engineering.
 * 
 * @author Justin Albano
 */
public class EngineeringRoundType implements RoundType {

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.domain.RoundType#getQuestions()
	 */
	@Override
	public List<Question> getQuestions (Context context) {
		
		// Create a delegate for obtaining questions from persistent storage
		RoundTypeHelper delegate = new RoundTypeHelper();
		return delegate.getQuestions(context, EngineeringQuestion.class, "engineer");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.domain.RoundType#getName()
	 */
	@Override
	public String getName () {
		return "engineering";
	}
}

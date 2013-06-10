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

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.List;

import android.content.Context;

/**
 * TODO Documentation
 * 
 * @author Justin Albano
 */
public class PilotStrategy extends QuestionStrategy {
	
	/***************************************************************************
	 * Methods
	 **************************************************************************/

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

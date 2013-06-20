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

import com.oceans7.mobile.eagleswag.domain.questions.Question;

import android.content.Context;

/**
 * An interface for a strategy that specifies how to obtain questions for a
 * specific type of round. A type of round is a series of questions designated
 * for a specific type of user (for example, an engineer or pilot). The round
 * type consists of a combination of general questions and questions for that
 * specific type (for example, engineering questions for an engineering type).
 * 
 * @author Justin Albano
 */
public interface RoundType {

	public List<Question> getQuestions (Context context);

	public String getName ();

}

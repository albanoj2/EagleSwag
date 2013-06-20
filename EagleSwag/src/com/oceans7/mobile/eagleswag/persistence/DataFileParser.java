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

package com.oceans7.mobile.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobile.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobile.eagleswag.config.NoSuchQuestionTypeException;
import com.oceans7.mobile.eagleswag.domain.questions.Question;

/**
 * Class that is responsible for parsing the data file associated with a
 * question type. The location of the data file, as well as the parser used to
 * parse that data file, is specified within the question type configuration
 * file.
 * 
 * @author Justin Albano
 */
public class DataFileParser {

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Obtains the questions of a given type from the data file associated with
	 * a question type. The location of the data file, and the parser used to
	 * parse the data file, is specified within the question type configuration
	 * file under the <data> element.
	 * 
	 * @param key
	 *            The type of questions to load from the data file. For example,
	 *            to load the general questions stored in the general questions
	 *            data file, the key GeneralQuestion.class should be used.
	 * @param context
	 *            The context used to access the data file for a question type.
	 * @return
	 *         A queue containing the questions loaded from the data file for
	 *         the question type specified.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context) {

		// Stub for the queue to return
		Queue<T> questions = null;

		try {
			// Select the strategy using the configuration file and create it
			Class<? extends DataFileParserStrategy> parserStrategyClass = ConfigurationHelper.getInstance().getParserStrategy(key, context);
			DataFileParserStrategy parserStrategy = parserStrategyClass.newInstance();

			// Obtain the questions from the data parser
			questions = parserStrategy.getQuestions(key, context);
		}
		catch (InstantiationException e) {
			// Could not instantiate the data file parser
			Log.e(this.getClass().getName(), "Could not instantiate parser strategy class: " + e);
		}
		catch (IllegalAccessException e) {
			// A call was made to the parser that cannot be made
			Log.e(this.getClass().getName(), "Could not access the parser strategy class: " + e);
		}
		catch (NoSuchQuestionTypeException e) {
			// There is no question type configuration associated with the key
			Log.e(this.getClass().getName(), "No configuration data associated with '" + key + "': " + e);
		}

		// Return the question queue (which may be null is there was an error)
		return questions;
	}

}

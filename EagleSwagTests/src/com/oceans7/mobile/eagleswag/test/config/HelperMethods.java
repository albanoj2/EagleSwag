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

package com.oceans7.mobile.eagleswag.test.config;

import java.util.Map;

import android.content.Context;
import android.test.AndroidTestCase;

import com.oceans7.mobile.eagleswag.config.ConfigurationController;
import com.oceans7.mobile.eagleswag.config.QuestionType;
import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.domain.questions.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.GeneralQuestion;

/**
 * Helper methods for configuration test cases.
 * 
 * @author Justin Albano
 */
public class HelperMethods extends AndroidTestCase {

	/***************************************************************************
	 * Helper Methods
	 **************************************************************************/

	/**
	 * Helper method that ensures the data in the question type configuration
	 * file is correctly parsed by the configuration proxy.
	 */
	public static void ensureDataIsParsedCorrectly (ConfigurationController controller, Context context) {

		// Obtain the types from the test configuration file
		Map<Class<? extends Question>, QuestionType> map = controller.getQuestionTypes(context);

		// Obtain the data from the question type
		QuestionType generalType = map.get(GeneralQuestion.class);
		String gSqliteTable = generalType.getSqliteConfiguration().getTable();

		// Ensure the general data parsed from the JSON file is accurate
		assertEquals("General => table:", "GeneralQuestions", gSqliteTable);

		// Obtain the data from the question type
		QuestionType engineeringType = map.get(EngineeringQuestion.class);
		String eSqliteTable = engineeringType.getSqliteConfiguration().getTable();

		// Ensure the engineering data parsed from the JSON file is accurate
		assertEquals("Engineering => table:", "EngineeringQuestions", eSqliteTable);
	}
}

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
import com.oceans7.mobile.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.Question;
import com.oceans7.mobile.eagleswag.persistence.JsonDataFileParserStrategy;

/**
 * TODO Documentation
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
		String gAsset = generalType.getDataConfiguration().getAsset();
		String gParserStrategy = generalType.getDataConfiguration().getParserStrategy().getName();
		String gJsonId = generalType.getJsonConfiguration().getId();
		String gSqliteTable = generalType.getSqliteConfiguration().getTable();

		// Ensure the general data parsed from the JSON file is accurate
		assertEquals("General => data asset:", "data/questions.json", gAsset);
		assertEquals("General => parser strategy:", JsonDataFileParserStrategy.class.getName(), gParserStrategy);
		assertEquals("General => JSON ID:", "generalQuestions", gJsonId);
		assertEquals("General => table:", "GeneralQuestions", gSqliteTable);

		// Obtain the data from the question type
		QuestionType engineeringType = map.get(EngineeringQuestion.class);
		String eAsset = engineeringType.getDataConfiguration().getAsset();
		String eParserStrategy = engineeringType.getDataConfiguration().getParserStrategy().getName();
		String eJsonId = engineeringType.getJsonConfiguration().getId();
		String eSqliteTable = engineeringType.getSqliteConfiguration().getTable();

		// Ensure the engineering data parsed from the JSON file is accurate
		assertEquals("Engineering => data asset:", "data/questions.json", eAsset);
		assertEquals("Engineering => parser strategy:", JsonDataFileParserStrategy.class.getName(), eParserStrategy);
		assertEquals("Engineering => JSON ID:", "engineeringQuestions", eJsonId);
		assertEquals("Engineering => table:", "EngineeringQuestions", eSqliteTable);
	}
}

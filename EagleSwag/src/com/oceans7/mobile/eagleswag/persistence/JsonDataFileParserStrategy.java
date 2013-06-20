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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobile.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobile.eagleswag.config.NoSuchQuestionTypeException;
import com.oceans7.mobile.eagleswag.domain.questions.Question;

/**
 * A data parser strategy for a JSON data file. This parser strategy extracts
 * the data for a question type from a JSON file and returns the extracted data
 * in a queue of questions.
 * 
 * @author Justin Albano
 */
public class JsonDataFileParserStrategy implements DataFileParserStrategy {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The title of the attribute associated with the text of a question stored
	 * in a JSON question data file.
	 */
	private static final String QUESTION_TEXT_ID = "text";

	/**
	 * The title of the attribute associated with the yes point value of a
	 * question stored in a JSON question data file.
	 */
	private static final String YES_VALUE_ID = "yesValue";

	/**
	 * The title of the attribute associated with the no point value of a
	 * question stored in a JSON question data file.
	 */
	private static final String NO_VALUE_ID = "noValue";

	/**
	 * The title of the attribute associated with the used count of a question
	 * stored in a JSON question data file.
	 */
	private static final String USED_COUNT_ID = "usedCount";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy#getQuestion(java.lang.Class,
	 *      android.content.Context)
	 */
	@Override
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context) {

		// The queue used to store the questions retrieved from the data file
		Queue<T> questions = new LinkedList<T>();

		try {
			// RECORD: time stamp of the beginning of the parsing
			long start = System.currentTimeMillis();

			// Obtain the JSON ID to parse for the key provided
			String id = ConfigurationHelper.getInstance().getJsonId(key, context);

			// Get the location of the asset for this key
			String asset = ConfigurationHelper.getInstance().getDataAsset(key, context);

			// The JSON parser to parse the data file
			JSONParser parser = new JSONParser();

			// Open the JSON file containing the questions
			InputStream jsonQuestions = context.getAssets().open(asset);

			// Parse the JSON file
			JSONObject jsonObj = (JSONObject) parser.parse(new InputStreamReader(jsonQuestions));
			jsonQuestions.close();

			// Obtain a JSON array for the question type supplied (dependent on
			// the ID supplied)
			JSONArray questionsArray = (JSONArray) jsonObj.get(id);

			for (Object question : questionsArray) {
				// Loop through each of the questions found in the data file

				// Convert the object to a JSON object
				JSONObject jsonQuestion = (JSONObject) question;

				// --------------------------------------------------------------
				// Extract the JSON values (note that the ID value is set to 0
				// because it will be supplied later by the data controller
				// (there is no ID associated with the questions in the
				// questions data file)
				// --------------------------------------------------------------
				int questionId = 0;
				String text = (String) jsonQuestion.get(QUESTION_TEXT_ID);
				int yesValue = Integer.parseInt((String) jsonQuestion.get(YES_VALUE_ID));
				int noValue = Integer.parseInt((String) jsonQuestion.get(NO_VALUE_ID));
				int usedCount = Integer.parseInt((String) jsonQuestion.get(USED_COUNT_ID));

				// Obtain the constructor for the supplied class
				Class<?>[] argTypes = new Class<?>[] { Integer.class, String.class, Integer.class, Integer.class, Integer.class };
				Constructor<T> constructor = key.getDeclaredConstructor(argTypes);
				Object[] args = new Object[] { questionId, text, yesValue, noValue, usedCount };

				// Invoke the constructor to obtain the object
				T questionToAdd = constructor.newInstance(args);

				// Add the new general question (ignoring the ID)
				questions.add(questionToAdd);

				// RECORD: time stamp of the end of the parsing
				long end = System.currentTimeMillis();
				Log.i(this.getClass().getName(), "JSON parsing took " + (end - start) + "ms");
			}
		}
		catch (IOException e) {
			// IO exception occurred while accessing the JSON data file
			Log.e(this.getClass().getName(), "IO exception occurred while parsing the JSON questions file for " + key.getCanonicalName() + ": " + e);
		}
		catch (ParseException e) {
			// A parse exception occurred while using the JSON parser object
			Log.e(this.getClass().getName(),
				"A parser exception occurred while using a JSON parser object to parse the JSON questions file for " + key.getCanonicalName() + ": " + e);
		}
		catch (NoSuchMethodException e) {
			// The selected constructor for the question could not be found
			Log.e(this.getClass().getName(), "Could not find the desired constructor for the " + key.getCanonicalName() + ": " + e);
		}
		catch (IllegalArgumentException e) {
			// The arguments to the question constructor are incorrect
			Log.e(this.getClass().getName(), "Invalid constructor arguments while trying to create new " + key.getCanonicalName() + ": " + e);
		}
		catch (InstantiationException e) {
			// The selected question object could not be instantiated
			Log.e(this.getClass().getName(), "Could not not instantiate an object for " + key.getCanonicalName() + ": " + e);
		}
		catch (IllegalAccessException e) {
			// The selected question constructor could not be accessed
			Log.e(this.getClass().getName(), "Could not access the desired constructor for " + key.getCanonicalName() + ": " + e);
		}
		catch (InvocationTargetException e) {
			// The constructor could not be invoked on the target object
			Log.e(this.getClass().getName(), "Could not invoke constructor on the target " + key.getCanonicalName() + ": " + e);
		}
		catch (NoSuchQuestionTypeException e) {
			// No configuration data can be found for the given key
			Log.e(this.getClass().getName(), "The class '" + key + "' has no configuration data associated with it in the configuration file: " + e);
		}

		// Return the queue containing the questions (which may be empty)
		return questions;
	}

}

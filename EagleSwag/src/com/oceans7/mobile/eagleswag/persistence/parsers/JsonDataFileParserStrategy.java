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

package com.oceans7.mobile.eagleswag.persistence.parsers;

import java.io.FileNotFoundException;
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

import com.oceans7.mobile.eagleswag.domain.questions.Question;
import com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy;

/**
 * A data parser strategy for a JSON data file. This parser strategy extracts
 * the data for a question type from a JSON file and returns the extracted data
 * in a queue of questions. The file name of the data file containing the
 * questions data to be parsed by this strategy follows a
 * "convention over configuration" paradigm. See
 * {@link #getQuestions(java.lang.Class, android.content.Context)} for more
 * information.
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

	/**
	 * The path (not including file name) of the directory containing the JSON
	 * data files to be parsed. This path is relative to the assets/ directory
	 * of the Android project structure.
	 */
	private static final String DATA_FILE_ASSET_PATH = "data/";

	/**
	 * The extension for the data files containing questions data.
	 */
	private static final String EXTENSION = ".json";

	/**
	 * The context used to access the data files.
	 */
	private Context context;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public JsonDataFileParserStrategy (Context context) {
		// Set the context used to access the data files
		this.setContext(context);
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * This parsing method uses the convention over configuration paradigm,
	 * where the name of the data file containing the questions data matches the
	 * class name (simple class name) of the key provided, with a ".json" file
	 * extension. For example, if GeneralQuestion.class is provided as the key,
	 * the data file name expected is "GeneralQuestion.json." Likewise, the
	 * object name for the array containing the questions data within the data
	 * file is simple class name of the key provided. For example, if
	 * GeneralQuestion.class is provided as a key, the format of the data file
	 * would be:
	 * 
	 * <pre>
	 * {
	 *   "GeneralQuestion": [
	 *     {
	 *       "text": "General question 1",
	 *       "yesValue": "10",
	 *       "noValue": "0",
	 *       "usedCount": "0"
	 *     },
	 *           .
	 *           .
	 *           .
	 *     ]
	 * }
	 * </pre>
	 * 
	 * If no data file is found for the key provided, an empty queue is
	 * returned.
	 * <p/>
	 * 
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobile.eagleswag.persistence.DataFileParserStrategy#getQuestion(java.lang.Class)
	 */
	@Override
	public <T extends Question> Queue<T> getQuestions (Class<T> key) {

		// The queue used to store the questions retrieved from the data file
		Queue<T> questions = new LinkedList<T>();

		// Set the object name for the data file and the name of the data file
		// to the simple name of the key. This is the implementation of the
		// "convention over configuration" mechanism for obtaining data.
		String id = key.getSimpleName();
		String dataFileName = key.getSimpleName() + EXTENSION;

		try {

			// The JSON parser to parse the data file
			JSONParser parser = new JSONParser();

			// Open the JSON file containing the questions
			InputStream dataFileInputStream = this.context.getAssets().open(DATA_FILE_ASSET_PATH + dataFileName);

			// Parse the JSON file
			JSONObject jsonObj = (JSONObject) parser.parse(new InputStreamReader(dataFileInputStream));
			dataFileInputStream.close();

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
			}
		}
		catch (FileNotFoundException e) {
			// A data file for the key provided cannot be found
			Log.e(this.getClass().getName(), "No data file named '" + dataFileName + "' found in '" + DATA_FILE_ASSET_PATH + "'");
		}
		catch (IOException e) {
			// IO exception occurred while accessing the JSON data file
			Log.e(this.getClass().getName(), "IO exception occurred while parsing " + dataFileName + ": " + e);
		}
		catch (ParseException e) {
			// A parse exception occurred while parsing a data file
			Log.e(this.getClass().getName(), "A parser exception occurred while parsing " + dataFileName + ": " + e);
		}
		catch (NoSuchMethodException e) {
			// A constructor with the specified format cannot be found for key
			Log.e(this.getClass().getName(), "Could not find the desired constructor for the " + id + ": " + e);
		}
		catch (IllegalArgumentException e) {
			// The arguments for the question constructor are incorrect
			Log.e(this.getClass().getName(), "Invalid constructor arguments while trying to create new " + id + ": " + e);
		}
		catch (InstantiationException e) {
			// The selected question object could not be instantiated
			Log.e(this.getClass().getName(), "Could not not instantiate an object for " + id + ": " + e);
		}
		catch (IllegalAccessException e) {
			// The selected question constructor could not be accessed
			Log.e(this.getClass().getName(), "Could not access the desired constructor for " + id + ": " + e);
		}
		catch (InvocationTargetException e) {
			// The constructor could not be invoked on the target object
			Log.e(this.getClass().getName(), "Could not invoke constructor on the target " + id + ": " + e);
		}

		// Return the queue containing the questions (which may be empty)
		return questions;
	}
	
	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return 
	 *		The context.
	 */
	public Context getContext () {
		return context;
	}

	/**
	 * @param context 
	 * 		The context to set.
	 */
	public void setContext (Context context) {
		this.context = context;
	}

}

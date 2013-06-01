/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file DataFileJSONParser.java
 * @version 1.0.0
 * 
 *          Oceans7 Software
 *          EagleSwag Android Mobile App
 * 
 *          Parser for JSON file containing questions data. This class is used
 *          to parse the JSON data file containing the question data for the
 *          application. The data from the JSON file is returned in segments:
 *          Each question category (General, Engineering, etc.) has a method
 *          dedicated to the return the questions for each category. For
 *          example, there is a method for general questions, which returns all
 *          of the general questions stored in the data file.
 * 
 *          TODO Update documentation
 */

package com.oceans7.mobileapps.eagleswag.persistence;

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

import com.oceans7.mobileapps.eagleswag.config.ConfigurationController;
import com.oceans7.mobileapps.eagleswag.config.ConfigurationControllerFactory;
import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.config.NoSuchQuestionTypeException;
import com.oceans7.mobileapps.eagleswag.domain.Question;

public class JSONDataFileParserStrategy implements DataFileParserStrategy {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final String QUESTION_TEXT_ID = "text";
	private static final String YES_VALUE_ID = "yesValue";
	private static final String NO_VALUE_ID = "noValue";
	private static final String USED_COUNT_ID = "usedCount";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Retrieves the questions (of the specified type) from the data file. This
	 * is a helper method intended to reduce the logic needed to support
	 * multiple types of questions that can be found in the JSON data file.
	 * 
	 * @param key
	 *            The class that specifies type of questions to create. For
	 *            example, if GeneralQuestion.class is supplied as the key, the
	 *            queue returned will be filled with objects of the concrete
	 *            class GeneralQuestion (although the interface for the objects
	 *            in the queue, as specified by the generic method parameter T,
	 *            may be different).
	 * @param id
	 *            The JSON heading used to obtain the questions. For example, to
	 *            obtain the general questions from the JSON file, use
	 *            "generalQuestions". The heading identifier is the containing
	 *            for each type of questions in the JSON data file. For example,
	 *            "generalQuestion" is used as the identifier to access all of
	 *            the questions under the heading "generalQuestions": [...] in
	 *            the JSON data file.
	 * @return
	 *         A queue containing the questions of the type specified.
	 * 
	 *         TODO Update documentation
	 */
	@Override
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context) {

		// The queue used to store the questions retrieved from the data file
		Queue<T> questions = new LinkedList<T>();

		try {
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

				// Extract the JSON values (note that the ID value is set to 0
				// because it will be supplied later by the data controller
				// (there is no ID associated with the questions in the
				// questions data file)
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
		catch (IOException e) {
			Log.e(this.getClass().getName(), "IOException occurred while parsing the JSON questions file for " + key.getCanonicalName() + ": " + e);
		}
		catch (ParseException e) {
			Log.e(this.getClass().getName(), "ParseException occurred while parsing the JSON questions file for " + key.getCanonicalName() + ": " + e);
		}
		catch (NoSuchMethodException e) {
			Log.e(this.getClass().getName(), "Could not find the desired constructor for the " + key.getCanonicalName() + ": " + e);
		}
		catch (IllegalArgumentException e) {
			Log.e(this.getClass().getName(),
				"Invalid arguments supplied to the constructor while trying to create new " + key.getCanonicalName() + ": " + e);
		}
		catch (InstantiationException e) {
			Log.e(this.getClass().getName(), "Could not not instantiate an object for " + key.getCanonicalName() + ": " + e);
		}
		catch (IllegalAccessException e) {
			Log.e(this.getClass().getName(), "Could not access the desired constructor for " + key.getCanonicalName() + ": " + e);
		}
		catch (InvocationTargetException e) {
			Log.e(this.getClass().getName(), "Could not invoke constructor on the target " + key.getCanonicalName() + ": " + e);
		}
		catch (NoSuchQuestionTypeException e) {
			Log.e(this.getClass().getName(), "The class '" + key + "' has no configuration data associated with it in the configuration file: " + e);
		}

		// Return the queue containing the questions
		return questions;
	}

}

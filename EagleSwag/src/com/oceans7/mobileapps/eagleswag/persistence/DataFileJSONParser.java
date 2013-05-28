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

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;

public class DataFileJSONParser implements DataFileParser {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context of the parser used to obtain the data.
	 */
	private Context context;

	/**
	 * The the location of the asset containing the question data.
	 */
	private String asset;

	private static final String GENERAL_QUESTIONS_ID = "generalQuestions";
	private static final String ENGINEERING_QUESTIONS_ID = "engineeringQuestions";
	private static final String PILOT_QUESTIONS_ID = "pilotQuestions";
	private static final String QUESTION_TEXT_ID = "text";
	private static final String YES_VALUE_ID = "yesValue";
	private static final String NO_VALUE_ID = "noValue";
	private static final String USED_COUNT_ID = "usedCount";

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataFileParser#setContext(android.content.Context)
	 */
	@Override
	public void setContext (Context context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataFileParser#setResourceID(int)
	 */
	@Override
	public void setAsset (String asset) {
		this.asset = asset;
	}

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
	 */
	private <T extends Question> Queue<T> getQuestions (Class<T> key, String id) {

		// The queue used to store the questions retrieved from the data file
		Queue<T> questions = new LinkedList<T>();

		// The JSON parser to parse the data file
		JSONParser parser = new JSONParser();

		try {
			// Open the JSON file containing the questions
			InputStream jsonQuestions = this.context.getAssets().open(this.asset);

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
			Log.e(this.getClass().getName(), "IOException occurred while parsing the JSON questions file for general questions: " + e);
		}
		catch (ParseException e) {
			Log.e(this.getClass().getName(), "ParseException occurred while parsing the JSON questions file for general questions: " + e);
		}
		catch (NoSuchMethodException e) {
			Log.e(this.getClass().getName(), "Could not find the desired constructor for the question class provided: " + e);
		}
		catch (IllegalArgumentException e) {
			Log.e(this.getClass().getName(), "Invalid arguments supplied to the constructor while trying to create new question: " + e);
		}
		catch (InstantiationException e) {
			Log.e(this.getClass().getName(), "Could not not instantiate an object for the question class provided: " + e);
		}
		catch (IllegalAccessException e) {
			Log.e(this.getClass().getName(), "Could not access the desired constructor for the question class provided: " + e);
		}
		catch (InvocationTargetException e) {
			Log.e(this.getClass().getName(), "Could not invoke constructor on the target specified: " + e);
		}

		// Return the queue containing the questions
		return questions;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataFileParser#getGeneralQuestions()
	 */
	@Override
	public Queue<GeneralQuestion> getGeneralQuestions () {
		return this.<GeneralQuestion> getQuestions(GeneralQuestion.class, GENERAL_QUESTIONS_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataFileParser#getEngineeringQuestions()
	 */
	@Override
	public Queue<EngineeringQuestion> getEngineeringQuestions () {
		return this.<EngineeringQuestion> getQuestions(EngineeringQuestion.class, ENGINEERING_QUESTIONS_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.persistence.DataFileParser#getPilotQuestions()
	 */
	@Override
	public Queue<PilotQuestion> getPilotQuestions () {
		return this.<PilotQuestion> getQuestions(PilotQuestion.class, PILOT_QUESTIONS_ID);
	}

}

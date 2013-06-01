/**
 * @author Justin Albano
 * @date May 18, 2013
 * @file DataFileParser.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       An interface for a parser of the data file containing questions. This
 *       is used as a standard interface to allow for the interchangeability of
 *       parsers for retrieving the questions for the application from the data
 *       file.
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.config.ConfigurationHelper;
import com.oceans7.mobileapps.eagleswag.config.NoSuchQuestionTypeException;
import com.oceans7.mobileapps.eagleswag.domain.Question;

public class DataFileParser {

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * TODO Update documentation
	 * TODO Change this constructor to select the strategy via configuration
	 * file.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context) {
		
		// Stub for the queue to return
		Queue<T> questions = null;
		
		try {
			// Select the strategy using the configuration file
			Class<? extends DataFileParserStrategy> parserStrategyClass = ConfigurationHelper.getInstance().getParserStrategy(key, context);
			DataFileParserStrategy parserStrategy = parserStrategyClass.newInstance();
			
			// Obtain the questions from the data parser
			questions = parserStrategy.getQuestions(key, context);
		}
		catch (InstantiationException e) {
			Log.e(this.getClass().getName(), "Could not instantiate parser strategy class: " + e);
		}
		catch (IllegalAccessException e) {
			Log.e(this.getClass().getName(), "Could not access the parser strategy class: " + e);
		}
		catch (NoSuchQuestionTypeException e) {
			Log.e(this.getClass().getName(), "No configuration data associated with '" + key + "'. Therefore, no parsing strategy could be found for the data file: " + e);
		}
		
		return questions;
	}

}

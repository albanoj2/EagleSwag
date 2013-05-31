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

import com.oceans7.mobileapps.eagleswag.domain.Question;

public class DataFileParser {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private DataFileParserStrategy strategy;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * TODO: Change this constructor to select the strategy via configuration
	 * file.
	 */
	public DataFileParser () {
		this.strategy = new JSONDataFileParserStrategy();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * TODO Update documentation
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context) {
		return this.strategy.getQuestions(key, context);
	}

}

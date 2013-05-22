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

import com.oceans7.mobileapps.eagleswag.domain.EngineeringQuestion;
import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.PilotQuestion;

public interface DataFileParser {

	/**
	 * Set the context that is used to access the data file.
	 * 
	 * @param context
	 *            The context that should be used to read from the data file.
	 */
	public void setContext (Context context);

	/**
	 * The location of the data file asset containing the questions data to
	 * parse. This location is relative to the assets directory of the Android
	 * project
	 * 
	 * @param asset
	 *            The location of the asset within the assets directory.
	 */
	public void setAsset (String asset);

	/**
	 * Obtain a list of the general questions in the data file.
	 * 
	 * @return
	 *         A queue of the general questions contained in the data file.
	 */
	public Queue<GeneralQuestion> getGeneralQuestions ();

	/**
	 * Obtain a list of the engineering questions in the data file.
	 * 
	 * @return
	 *         A queue of the engineering questions contained in the data file.
	 */
	public Queue<EngineeringQuestion> getEngineeringQuestions ();

	/**
	 * Obtain a list of the pilot questions in the data file.
	 * 
	 * @return
	 *         A queue of the pilot questions in the data file.
	 */
	public Queue<PilotQuestion> getPilotQuestions ();

}

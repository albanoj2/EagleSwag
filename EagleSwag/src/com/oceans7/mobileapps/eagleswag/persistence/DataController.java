/**
 * @author Justin Albano
 * @date May 27, 2013
 * @file DataManager.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       The manager of persistent storage. The data controller specifies the
 *       interface for any data controllers and includes methods for opening and
 *       closing the controller, as well as storing and retrieving questions
 *       from the controller.
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Score;

public interface DataController {

	/**
	 * Opens the data controller. This will establish any necessary connections
	 * to external services (such as a database) and open any files on the file
	 * system required necessary for storing and retrieving question data.
	 * 
	 * @param context
	 *            The Android context used to open any database connections or
	 *            files on the file system.
	 */
	public void open (Context context);

	/**
	 * Close the data controller. This will close all connections to any
	 * external service (such as a database) and close all open files on the
	 * file system.
	 */
	public void close ();

	/**
	 * Obtains a specified number of questions of the supplied type, T. The
	 * generic parameter, T, specified the type of the questions returned from
	 * this method (the type of the questions in the queue returned from this
	 * method). The supplied class (key) determines the mapping of question type
	 * to the database table that contains the questions data.
	 * 
	 * @param key
	 *            The class of the question type. For example, if the supplied
	 *            type, T, is GeneralQuestion, the givenClass is
	 *            GeneralQuestion.class. This class is used as a key to map the
	 *            question type to the database table used to retrieve the
	 *            question data.
	 * @param number
	 *            The number of questions to retrieve from the database.
	 * @return
	 *         A queue containing the specified number of questions of type, T.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, int number);
	
	/**
	 * TODO Documentation
	 * @param type
	 * @return
	 */
	public double getTotalScore (String type);
	
	/**
	 * TODO Documentation
	 * @param type
	 * @return
	 */
	public double getAverageScore (String type);

	/**
	 * Saves a question. The key parameter is used as a means of saving the
	 * question object to the correct location in the data controller (correct
	 * file, database table, etc.).
	 * 
	 * @param key
	 *            The key to correlate the data to a specific location (file,
	 *            database table, etc.).
	 * @param question
	 *            The question object to store.
	 */
	public void saveQuestion (Class<? extends Question> key, Question question);
	
	/**
	 * TODO Documentation
	 * @param score
	 * @param type
	 */
	public void saveRoundScore (Score score, String type);
}

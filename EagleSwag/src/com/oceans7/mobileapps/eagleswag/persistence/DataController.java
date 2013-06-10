/**
 * @author Justin Albano
 * @date Jun 9, 2013
 * @file DataController.java
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
	 * Obtains the total score (total number of points earned) for a specified
	 * type. A type is essentially a key that groups saved rounds together. For
	 * example, if an engineering round is started and saved, the round type is
	 * "engineering" (or whichever type is defined for an engineering round).
	 * 
	 * @param type
	 *            The type of round to retrieve the total score for.
	 * @return
	 *         The total score for the specified type.
	 */
	public int getTotalScore (String type);

	/**
	 * Obtains the arithmetic average score (total number of points divided by
	 * the number of scores) for a specified type. A type is essentially a key
	 * that groups saved rounds together. For example, if an engineering round
	 * is started and saved, the round type is "engineering" (or whichever type
	 * is defined for an engineering round).
	 * 
	 * @param type
	 *            The type of round to retrieve the arithmetic average for.
	 * @return
	 *         The arithmetic average for a specified key.
	 */
	public int getAverageScore (String type);

	/**
	 * Saves a question. The key parameter is used as a means of saving the
	 * question object to the correct location in the data controller (correct
	 * file, database table, etc.).<br />
	 * <br />
	 * <strong>Postconditions</strong>
	 * <ul>
	 * <li>The supplied question is permanently saved in persistent storage.</li>
	 * </ul>
	 * 
	 * @param key
	 *            The key to correlate the data to a specific location (file,
	 *            database table, etc.).
	 * @param question
	 *            The question object to store.
	 */
	public void saveQuestion (Class<? extends Question> key, Question question);

	/**
	 * Saves a score for a round. Upon saving the score, the score, and the data
	 * associated with the score, the data is permanently saved in persistent
	 * storage. <br />
	 * <br />
	 * <strong>Postconditions</strong>
	 * <ul>
	 * <li>The supplied score is permanently saved in persistent storage.</li>
	 * </ul>
	 * 
	 * @param score
	 *            The score object to store in persistent storage.
	 * @param type
	 *            The type of the round that the score originated from. For
	 *            example, if the score originated from an engineering round,
	 *            the type would be the type that correlates the score to an
	 *            engineering round, such as "engineering." Commonly, the name
	 *            of the question strategy used to begin a round is used due to
	 *            its consistency.
	 */
	public void saveRoundScore (Score score, String type);
}

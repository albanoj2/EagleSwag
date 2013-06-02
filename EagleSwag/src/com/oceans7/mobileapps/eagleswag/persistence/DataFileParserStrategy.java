/**
 * @author Justin Albano
 * @date May 31, 2013
 * @file DataFileParserStrategy.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Interface for a data file parser strategy. The data file parser
 *       strategy is required to provide a method for obtaining the questions
 *       from the data file containing question data.
 */

package com.oceans7.mobileapps.eagleswag.persistence;

import java.util.Queue;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.domain.Question;

public interface DataFileParserStrategy {

	/**
	 * Obtains the questions from the data file for a specific question type.
	 * For example, to retrieve the general questions from the data file, the
	 * key 'GeneralQuestion.class' is used.
	 * 
	 * @param key
	 *            Specifies which question type to retrieve from the data file.
	 *            This key also specifies the type of the elements contained in
	 *            the queue. For example, if GeneralQuestion.class is used as
	 *            the key, the general questions from the data file will be
	 *            returned, and the queue returned will contain elements of type
	 *            GeneralQuestion.
	 * @param context
	 *            The context used to access the data file.
	 * @return
	 *         A queue containing questions of the type specified by the key
	 *         provided.
	 */
	public <T extends Question> Queue<T> getQuestions (Class<T> key, Context context);
}

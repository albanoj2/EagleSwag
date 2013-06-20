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

package com.oceans7.mobile.eagleswag.domain;

import java.util.LinkedList;

import com.oceans7.mobile.eagleswag.domain.questions.Question;
import com.oceans7.mobile.eagleswag.domain.roundtype.RoundType;

import android.content.Context;
import android.util.Log;

/**
 * A round of questions. This round is started when a set of questions is
 * requested, and each time a question is answered, it is submitted to the
 * Round. Once all the questions in this set have been answered, the total score
 * for the round is calculated. Once the score is calculated, the round is
 * saved. When the round is saved, the questions submitted to the Round are
 * stored back in the database. Before storing the questions in the database,
 * the 'used count' of the question is incremented.
 * 
 * @author Justin Albano
 */
public class Round {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The list containing the questions that have been submitted as 'yes' for
	 * this round.
	 */
	private LinkedList<Question> questionsAnsweredYes;

	/**
	 * The list containing the questions that have been submitted as 'no' for
	 * this round.
	 */
	private LinkedList<Question> questionsAnsweredNo;

	/**
	 * The score of the round.
	 */
	private Score score;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public Round () {

		// Initialize the data structures to store the answered questions
		this.questionsAnsweredYes = new LinkedList<Question>();
		this.questionsAnsweredNo = new LinkedList<Question>();
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Submits a question as having been answered 'yes.'
	 * 
	 * @param question
	 *            The question that has been answered 'yes.'
	 */
	public void submitYesQuestion (Question question) {
		// Add the question to the list of questions answered 'yes'
		this.questionsAnsweredYes.add(question);

		// Log the submitted questions as having been answered yes
		Log.i(this.getClass().getName(),
			"Answered questions as 'yes' for [" + question.getYesPointValue() + "] points: " + question.getQuestionString());
	}

	/**
	 * Submits a question as having been answered 'no.'
	 * 
	 * @param question
	 *            The question that has been answered 'no.'
	 */
	public void submitNoQuestion (Question question) {
		// Add the question to the list of questions answered 'no'
		this.questionsAnsweredNo.add(question);

		// Log the submitted questions as having been answered no
		Log.i(this.getClass().getName(),
			"Answered questions as 'no' for [" + question.getNoPointValue() + "] points: " + question.getQuestionString());
	}

	/**
	 * @return
	 *         The number of questions that have been submitted as having been
	 *         answered 'yes'.
	 */
	public int numberOfQuestionsAnsweredYes () {
		return this.questionsAnsweredYes.size();
	}

	/**
	 * @return
	 *         The number of questions that have been submitted as having been
	 *         answered 'no'.
	 */
	public int numberOfQuestionsAnsweredNo () {
		return this.questionsAnsweredNo.size();
	}

	/**
	 * Calculates the score for the round. The score is calculated as the total
	 * earned points, divided by the total possible points, multiplied by 100.
	 * The total possible points are calculated by summing the maximum of the
	 * 'yes' and 'no' point values. This maximum defines the number of points
	 * that question "could have earned" and is therefore, the possible point
	 * value for the question.
	 * 
	 * @return
	 *         The score for the round based on the previously submitted 'yes'
	 *         and 'no' answered questions.
	 */
	public int calculateScore () {

		// Variables to store the running total and total possible points
		double runningTotal = 0;
		double totalPossiblePoints = 0;

		for (Question question : this.questionsAnsweredYes) {
			// Iterate through the questions answered 'yes' and tally the points
			runningTotal += question.getYesPointValue();
			totalPossiblePoints += Math.max(question.getYesPointValue(), question.getNoPointValue());

			Log.i(this.getClass().getName(),
				"Calculating possible points between [" + question.getYesPointValue() + "] [" + question.getNoPointValue() + "]: " + "[" + Math.max(question.getYesPointValue(),
					question.getNoPointValue()) + "]");
		}

		for (Question question : this.questionsAnsweredNo) {
			// Iterate through the questions answered 'no' and tally the points
			runningTotal += question.getNoPointValue();
			totalPossiblePoints += Math.max(question.getYesPointValue(), question.getNoPointValue());

			Log.i(this.getClass().getName(),
				"Calculating possible points between [" + question.getYesPointValue() + "] [" + question.getNoPointValue() + "]: " + "[" + Math.max(question.getYesPointValue(),
					question.getNoPointValue()) + "]");
		}

		if ((this.getNumberOfYesQuestions() + this.getNumberOfNoQuestions()) > 0) {
			// Calculate the score as earned/possible * 100 (if there were
			// questions answered). If there were no questions answered, the
			// possible points would be 0, and the score would be NaN
			int score = (int) Math.round((runningTotal / totalPossiblePoints) * 100);
			Log.i(this.getClass().getName(), "Score for round: " + score);

			// Set the score for the round
			this.score = new Score(score);

			return score;
		}
		else {
			// No questions were answered
			return 0;
		}
	}

	/**
	 * Save the questions for the round. Before saving each question, the used
	 * count for each question is incremented to reflect its use.
	 * 
	 * @param strategy
	 *            The question strategy used to save the round. If the strategy
	 *            is null, the score for the round will not be saved.
	 * 
	 * @param context
	 *            The context used to save the questions.
	 */
	public void save (RoundType strategy, Context context) {

		for (Question question : this.questionsAnsweredYes) {
			// Iterate through the 'yes' questions and save each

			// Increment the question used count and save the question
			question.incrementUsedCount();
			question.save(context);

			// Log the saved question
			Log.i(this.getClass().getName(), "Incremented used count and saved question: " + question);
		}

		for (Question question : this.questionsAnsweredNo) {
			// Iterate through the 'no' questions and save each

			// Increment the question used count and save the question
			question.incrementUsedCount();
			question.save(context);

			// Log the saved question
			Log.i(this.getClass().getName(), "Incremented used count and saved question: " + question);
		}

		if (strategy != null && this.score != null) {
			// Save score object if a strategy is provided and the score is set
			this.score.save(strategy, context);
		}
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	public int getNumberOfYesQuestions () {
		return this.questionsAnsweredYes.size();
	}

	public int getNumberOfNoQuestions () {
		return this.questionsAnsweredNo.size();
	}

}

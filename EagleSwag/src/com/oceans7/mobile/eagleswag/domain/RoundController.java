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

import java.util.List;

import android.content.Context;

/**
 * Manages a current round of questions. This manage begins by starting a round.
 * Next, the current question is retrieved by the external user. The external
 * user then answers the question as either 'yes' or 'no'. Once all questions
 * have been exhausted, the external user then ends the round. This process can
 * be repeated an indefinite number of times.
 * 
 * @author Justin Albano
 */
public class RoundController {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Context used to save the current round when a round is completed.
	 */
	private Context context;

	/**
	 * The current round.
	 */
	private Round currentRound;

	/**
	 * The questions for the current round.
	 */
	private List<Question> currentQuestions;

	/**
	 * The current question to answer.
	 */
	private Question currentQuestion;

	/**
	 * The current strategy used to start the round.
	 */
	private RoundType currentStrategy;

	/**
	 * Flag to track if a round has been started.
	 */
	private boolean hasRoundBeenStarted;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Private constructor that provides for the initialization of the context
	 * for the manager.
	 * 
	 * @param context
	 *            The context used to save the current round.
	 */
	public RoundController (Context context) {

		// Set the context
		this.setContext(context);

		// Set flag to 'round not started'
		this.hasRoundBeenStarted = false;
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * Start a new round for pilots. This initializes the current round and
	 * obtains questions for a pilot.
	 * 
	 * @param type
	 *            The type of round to start.
	 */
	public synchronized void startRound (RoundType type) {

		// Obtain the questions from the supplied strategy
		this.currentQuestions = type.getQuestions(this.context);

		// Create new round object
		this.currentRound = new Round(type);

		// Set the current strategy
		this.currentStrategy = type;

		// Set flag to 'round started'
		this.hasRoundBeenStarted = true;

		// Set the current question
		this.currentQuestion = this.currentQuestions.remove(0);
	}

	/**
	 * Answer the current question in the affirmative. This action submits the
	 * current questions as a 'yes' question for the current round.
	 * 
	 * @throws RoundNotStartedException
	 *             A round has not been started.
	 */
	public synchronized void answerCurrentQuestionYes () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted) {
			// A round has not been started
			throw new RoundNotStartedException("A round has not been started before attempting to answer a question as 'yes'");
		}
		else if (this.currentQuestion != null) {

			// Take the next question and answer it yes if it exists
			this.currentRound.submitYesQuestion(this.currentQuestion);

			// Set the next question as the current question
			this.currentQuestion = this.currentQuestions.remove(0);
		}
	}

	/**
	 * Answer the current question in the negative. This action submits the
	 * current questions as a 'no' question for the current round.
	 * 
	 * @throws RoundNotStartedException
	 *             A round has not been started.
	 */
	public synchronized void answerCurrentQuestionNo () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted) {
			// A round has not been started
			throw new RoundNotStartedException("A round has not been started before attempting to answer a question as 'no'");
		}
		else if (this.currentQuestion != null) {

			// Take the next question and answer it no if it exists
			this.currentRound.submitNoQuestion(this.currentQuestion);

			// Set the next question as the current question
			this.currentQuestion = this.currentQuestions.remove(0);
		}
	}

	/**
	 * Queries the manager to see if there are more questions to be answered for
	 * the current round.
	 * 
	 * @return
	 *         True if there are more questions to answer; false otherwise.
	 * @throws RoundNotStartedException
	 *             A round has not been started.
	 */
	public boolean hasMoreQuestions () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted) {
			// A round has not yet been started
			throw new RoundNotStartedException("A round has not been started before attempting to query if questions are available");
		}
		else {
			// A round is started and the query can be conducted
			return this.currentQuestions.size() > 0;
		}
	}

	/**
	 * Ends the current round. This saves the data for the current round and
	 * destroys the current round and current set of questions.
	 * 
	 * @return
	 *         The score for the current round.
	 * @throws RoundNotStartedException
	 *             A round has not been started.
	 */
	public synchronized int endRound () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted) {
			// A round has not be started yet
			throw new RoundNotStartedException("A round has not been started before attempted to end the current round");
		}
		else {

			// Record the score for the round
			int score = this.currentRound.calculateScore();

			// Save the current round
			this.currentRound.save(this.context);

			// Destroy the current round and set of questions
			this.currentRound = null;
			this.currentQuestions = null;

			// Remove the current strategy
			this.currentStrategy = null;

			// Set flag to 'round not started'
			this.hasRoundBeenStarted = false;

			return score;
		}
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	/**
	 * @return
	 *         The context.
	 */
	public Context getContext () {
		return context;
	}

	/**
	 * @param context
	 *            The context to set.
	 */
	public void setContext (Context context) {
		this.context = context;
	}

	/**
	 * @return
	 *         The currentRound.
	 */
	public Round getCurrentRound () {
		return currentRound;
	}

	/**
	 * @param currentRound
	 *            The currentRound to set.
	 */
	public void setCurrentRound (Round currentRound) {
		this.currentRound = currentRound;
	}

	/**
	 * @return
	 *         The currentQuestions.
	 */
	public List<Question> getCurrentQuestions () {
		return currentQuestions;
	}

	/**
	 * @return
	 *         The current question to answer.
	 */
	public Question getCurrentQuestion () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted) {
			// A round has not be started yet
			throw new RoundNotStartedException("A round has not been started before attempted to obtain the current question");
		}
		else {
			return currentQuestion;
		}
	}

	/**
	 * @return
	 *         The currentStrategy.
	 */
	public RoundType getCurrentStrategy () {
		return currentStrategy;
	}

}

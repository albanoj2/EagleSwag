/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file RoundManager.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.Queue;

import android.content.Context;

public class RoundManager {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static RoundManager instance;
	private Context context;
	private QuestionManager questionManager;
	private Round currentRound;
	private Queue<Question> currentQuestions;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	private RoundManager (Context context) {

		// Set the context
		this.setContext(context);

		// Instantiate the question manager
		this.questionManager = QuestionManager.getInstance(context);
	}

	/***************************************************************************
	 * Singleton getInstance Method
	 **************************************************************************/

	public static synchronized RoundManager getInstance (Context context) {

		if (instance == null) {
			// Lazy instantiation
			instance = new RoundManager(context);
		}

		return instance;
	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	public void startEngineeringRound () {

		// Obtain the questions from the question manager
		this.currentQuestions = this.questionManager.getEngineeringQuestions();

		// Create new round object
		this.currentRound = new Round();
	}

	public void startPilotRound () {

		// Obtain the questions from the question manager
		this.currentQuestions = this.questionManager.getPilotQuestions();

		// Create new round object
		this.currentRound = new Round();
	}

	public void answerCurrentQuestionYes () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted()) {
			// A round has not been started
						throw new RoundNotStartedException("A round has not been started before attempting to answer a question as 'yes'");
		}
		else {
			// Take the next question and answer it yes if it exists
			this.currentRound.submitYesQuestion(this.currentQuestions.remove());
		}
	}

	public void answerCurrentQuestionNo () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted()) {
			// A round has not been started
			throw new RoundNotStartedException("A round has not been started before attempting to answer a question as 'no'");
		}
		else {
			// Take the next question and answer it no if it exists
			this.currentRound.submitNoQuestion(this.currentQuestions.remove());
		}
	}

	public boolean hasMoreQuestions () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted()) {
			// A round has not yet been started
			throw new RoundNotStartedException("A round has not been started before attempting to query if questions are available");
		}
		else {
			// A round is started and the query can be conducted
			return this.currentQuestions.size() > 0;
		}
	}

	public void endRound () throws RoundNotStartedException {

		if (!this.hasRoundBeenStarted()) {
			// A round has not be started yet
			throw new RoundNotStartedException("A round has not been started before attempted to end the current round");
		}
		else {
			// Save the current round
			this.currentRound.save(this.context);

			// Destroy the current round and set of questions
			this.currentRound = null;
			this.currentQuestions = null;
		}
	}
	
	private boolean hasRoundBeenStarted () {
		return !(this.currentRound == null || this.currentQuestions == null);
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
	 *         The questionManager.
	 */
	public QuestionManager getQuestionManager () {
		return questionManager;
	}

	/**
	 * @param questionManager
	 *            The questionManager to set.
	 */
	public void setQuestionManager (QuestionManager questionManager) {
		this.questionManager = questionManager;
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
	public Queue<Question> getCurrentQuestions () {
		return currentQuestions;
	}

	/**
	 * @param currentQuestions
	 *            The currentQuestions to set.
	 */
	public void setCurrentQuestions (Queue<Question> currentQuestions) {
		this.currentQuestions = currentQuestions;
	}

}

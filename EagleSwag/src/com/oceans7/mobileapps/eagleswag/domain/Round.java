/**
 * @author Justin Albano
 * @date May 19, 2013
 * @file Round.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       A round of questions. This round is started when a set of questions is
 *       requested, and each time a question is answered, it is submitted to the
 *       Round. Once all the questions in this set have been answered, the total
 *       score for the round is calculated. Once the score is calculated, the
 *       round is saved. When the round is saved, the questions submitted to the
 *       Round are stored back in the database. Before storing the questions in
 *       the database, the 'used count' of the question is incremented.
 */

package com.oceans7.mobileapps.eagleswag.domain;

import java.util.LinkedList;

import android.content.Context;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

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
	public double calculateScore () {

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

		// Calculate the score as earned/possible * 100
		double score = (runningTotal / totalPossiblePoints) * 100;
		Log.i(this.getClass().getName(), "Score for round: " + score);

		return score;
	}

	/**
	 * Save the questions for the round. Before saving each question, the used
	 * count for each question is incremented to reflect its use.
	 * 
	 * @param context
	 *            The context used to save the questions.
	 */
	public void save (Context context) {

		// Obtain a reference to the data controller for the application
		DataController controller = DataControllerFactory.getInstance().getDataController(context);

		for (Question question : this.questionsAnsweredYes) {
			// Iterate through the 'yes' questions and save each

			// Increment the question used count and save the question
			question.incrementUsedCount();
			controller.saveQuestion(question);

			// Log the saved question
			Log.i(this.getClass().getName(),
				"Incremented used count and saved question: " + question);
		}

		for (Question question : this.questionsAnsweredNo) {
			// Iterate through the 'no' questions and save each

			// Increment the question used count and save the question
			question.incrementUsedCount();
			controller.saveQuestion(question);

			// Log the saved question
			Log.i(this.getClass().getName(),
				"Incremented used count and saved question: " + question);
		}
	}

}

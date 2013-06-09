/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file Question.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;

import android.content.Context;

public abstract class Question {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The ID of the question.
	 */
	private int id;

	/**
	 * The text associated with the question.
	 */
	private String question;

	/**
	 * The value of the question when answered in the affirmative.
	 */
	private int yesValue;

	/**
	 * The value of the question when answered in the negative.
	 */
	private int noValue;

	/**
	 * The number of times the question has been answered by the user.
	 */
	private int usedCount;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * @param id
	 *            The ID of the question.
	 * @param text
	 *            The text of the question.
	 * @param yesValue
	 *            The yes value of the question.
	 * @param noValue
	 *            The no value of the question.
	 * @param usedCount
	 *            The used count of the question.
	 */
	public Question (Integer id, String text, Integer yesValue, Integer noValue, Integer usedCount) {

		// Set the initial values of the question
		this.setId(id);
		this.setQuestionString(text);
		this.setYesPointValue(yesValue);
		this.setNoPointValue(noValue);
		this.usedCount = usedCount;

	}

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * TODO Documentation
	 * @param context
	 */
	public void save (Context context) {

		// Obtain a reference to a data controller and open the controller
		DataController controller = DataControllerFactory.getInstance().getDataController(context);
		controller.open(context);

		// Save the question
		controller.saveQuestion(this.getClass(), this);
		
		// Close the controller
		controller.close();
	}

	/***************************************************************************
	 * Getters & Setters
	 **************************************************************************/

	public long getId () {
		return this.id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getQuestionString () {
		return this.question;
	}

	public void setQuestionString (String text) {
		this.question = text;
	}

	public int getYesPointValue () {
		return this.yesValue;
	}

	public void setYesPointValue (int value) {
		this.yesValue = value;
	}

	public int getNoPointValue () {
		return this.noValue;
	}

	public void setNoPointValue (int value) {
		this.noValue = value;
	}

	public int getUsedCount () {
		return this.usedCount;
	}

	public void incrementUsedCount () {
		this.usedCount++;
	}

	public void setUsedCount (int usedCount) {
		this.usedCount = usedCount;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#incrementUsedCount()
	 */
	@Override
	public String toString () {
		return "[ID:] " + this.id + ", [Question:] " + this.question + ", " + "[Yes value:] " + this.yesValue + ", " + "[No value:] " + this.noValue + ", [Used count:] " + this.usedCount;
	}

}

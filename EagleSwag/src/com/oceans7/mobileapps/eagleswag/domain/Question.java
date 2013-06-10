package com.oceans7.mobileapps.eagleswag.domain;

import android.content.Context;

import com.oceans7.mobileapps.eagleswag.persistence.DataController;
import com.oceans7.mobileapps.eagleswag.persistence.DataControllerFactory;


/**
 * An abstract class that defines the interface for a question. All common
 * implementation for questions, such as saving the question, are present in
 * this class. The attributes of this class map directly to the question data
 * specified in the data file. To create a new type of question, simply extend
 * this class and provide a custom constructor for the new question type.
 * 
 * <p>
 * <strong>Note:</strong> If a new question type is added to the system, a new
 * class must be created that extends this abstract class.
 * </p>
 * 
 * @author Justin Albano
 */
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
	 * Saves the question in persistent storage.<br />
	 * <br />
	 * <strong>Postconditions</strong>
	 * <ul>
	 * <li>This question will be permanently stored in persistent storage</li>
	 * </ul>
	 * 
	 * @param context
	 *            The context used to store this question in persistent storage.
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

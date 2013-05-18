/**
 * @author Justin Albano
 * @date May 17, 2013
 * @file ConcreteQuestion.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.domain;

public class ConcreteQuestion implements Question {

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
	public ConcreteQuestion (int id, String text, int yesValue, int noValue, int usedCount) {

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
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getId()
	 */
	@Override
	public int getId () {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setId(int)
	 */
	@Override
	public void setId (int id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getQuestionString()
	 */
	@Override
	public String getQuestionString () {
		return this.question;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setQuestionString(java.lang.String)
	 */
	@Override
	public void setQuestionString (String text) {
		this.question = text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getYesPointValue()
	 */
	@Override
	public int getYesPointValue () {
		return this.yesValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setYesPointValue(int)
	 */
	@Override
	public void setYesPointValue (int value) {
		this.yesValue = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getNoPointValue()
	 */
	@Override
	public int getNoPointValue () {
		return this.noValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setNoPointValue(int)
	 */
	@Override
	public void setNoPointValue (int value) {
		this.noValue = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getUsedCount()
	 */
	@Override
	public int getUsedCount () {
		return this.usedCount;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#incrementUsedCount()
	 */
	@Override
	public void incrementUsedCount () {
		this.usedCount++;
	}

}

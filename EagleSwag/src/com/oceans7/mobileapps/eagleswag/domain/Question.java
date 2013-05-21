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

public abstract class Question {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The ID of the question.
	 */
	private long id;

	/**
	 * The text associated with the question.
	 */
	private String question;

	/**
	 * The value of the question when answered in the affirmative.
	 */
	private long yesValue;

	/**
	 * The value of the question when answered in the negative.
	 */
	private long noValue;

	/**
	 * The number of times the question has been answered by the user.
	 */
	private long usedCount;

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
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getId()
	 */
	public long getId () {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setId(long)
	 */
	public void setId (long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getQuestionString()
	 */
	public String getQuestionString () {
		return this.question;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setQuestionString(java.lang.String)
	 */
	public void setQuestionString (String text) {
		this.question = text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getYesPointValue()
	 */
	public long getYesPointValue () {
		return this.yesValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setYesPointValue(long)
	 */
	public void setYesPointValue (long value) {
		this.yesValue = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getNoPointValue()
	 */
	public long getNoPointValue () {
		return this.noValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#setNoPointValue(long)
	 */
	public void setNoPointValue (long value) {
		this.noValue = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#getUsedCount()
	 */
	public long getUsedCount () {
		return this.usedCount;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.oceans7.mobileapps.eagleswag.domain.Question#incrementUsedCount()
	 */
	public void incrementUsedCount () {
		this.usedCount++;
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

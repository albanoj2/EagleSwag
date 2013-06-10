/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file RoundNotStartedException.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       An exception thrown when a round manager attempts to perform operations
 *       on a round that has not been started. For example, when a round
 *       controller is instructed to answer a question 'yes' before the round
 *       controller has started a round.
 *       
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.domain;

public class RoundNotStartedException extends Exception {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = 5694935769917412832L;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/**
	 * Parameterized constructor that allows for a message to be added to the
	 * exception.
	 * 
	 * @param message
	 *            A message to attribute to the exception.
	 */
	public RoundNotStartedException (String message) {
		super(message);
	}
}

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

package com.oceans7.mobileapps.eagleswag.domain;

/**
 * An exception thrown when a round manager attempts to perform operations on a
 * round that has not been started. For example, when a round controller is
 * instructed to answer a question 'yes' before the round controller has started
 * a round.
 * 
 * @author Justin Albano
 */
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

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

package com.oceans7.mobile.eagleswag.test.domain;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobile.eagleswag.domain.RoundController;
import com.oceans7.mobile.eagleswag.domain.RoundNotStartedException;
import com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType;
import com.oceans7.mobile.eagleswag.domain.roundtype.PilotRoundType;

/**
 * Test cases for {@link com.oceans7.mobile.eagleswag.domain.RoundController}
 * 
 * @author Justin Albano
 */
public class RoundControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used for the test cases.
	 */
	private Context context;

	/**
	 * The round controller under test.
	 */
	private RoundController manager;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp () throws Exception {
		super.setUp();

		// Instantiate the manager
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");
		this.manager = new RoundController(context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#startEngineeringRound()}
	 * .
	 */
	public void testStartEngineeringRound () throws Exception {

		// Start engineering round
		this.manager.startRound(new EngineeringRoundType());

		// Ensure that the questions and round have been set in the manager
		assertNotNull("Question queue is set:", this.manager.getCurrentQuestions());
		assertTrue("Questions loaded:", this.manager.hasMoreQuestions());
		assertNotNull("Round is set:", this.manager.getCurrentRound());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#startPilotRound()}
	 * .
	 */
	public void testStartPilotRound () throws Exception {

		// Start engineering round
		this.manager.startRound(new PilotRoundType());

		// Ensure that the questions and round have been set in the manager
		assertNotNull("Question queue is set:", this.manager.getCurrentQuestions());
		assertTrue("Questions loaded:", this.manager.hasMoreQuestions());
		assertNotNull("Round is set:", this.manager.getCurrentRound());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#answerCurrentQuestionYes()}
	 * .
	 */
	public void testAnswerCurrentQuestionYes () throws Exception {

		// Start a round
		this.manager.startRound(new PilotRoundType());

		// Get the original number of questions answered as yes
		int originalYes = this.manager.getCurrentRound().getNumberOfYesQuestions();

		// Submit a yes question
		this.manager.answerCurrentQuestionYes();

		// Ensure that the number of questions answered yes has been incremented
		assertEquals("Number of yes questions incremented:", originalYes + 1, this.manager.getCurrentRound().getNumberOfYesQuestions());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#answerCurrentQuestionNo()}
	 * .
	 */
	public void testAnswerCurrentQuestionNo () throws Exception {

		// Start a round
		this.manager.startRound(new PilotRoundType());

		// Get the original number of questions answered as no
		int originalNo = this.manager.getCurrentRound().getNumberOfNoQuestions();

		// Submit a no question
		this.manager.answerCurrentQuestionNo();

		// Ensure that the number of questions answered no has been incremented
		assertEquals("Number of yes questions incremented:", originalNo + 1, this.manager.getCurrentRound().getNumberOfNoQuestions());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#endRound()}.
	 */
	public void testEndRound () throws Exception {

		// Start a round
		this.manager.startRound(new PilotRoundType());

		// End the round
		this.manager.endRound();

		// Ensure that the current round and questions has been erased
		assertNull("Current round unset:", this.manager.getCurrentRound());
		assertNull("Current questions unset:", this.manager.getCurrentQuestions());
		assertNull("Current strategy unset:", this.manager.getCurrentStrategy());
	}

	/**
	 * A walk-through of a sample engineering round to ensure that a round can
	 * be completed without any errors or bugs.
	 */
	public void testSampleEngineeringRound () throws Exception {

		// Start the round for an engineer
		this.manager.startRound(new EngineeringRoundType());

		while (this.manager.hasMoreQuestions()) {

			// Log each question that is displayed
			Log.d(this.getClass().getName(), "Question for engineer: " + this.manager.getCurrentQuestion());

			// Answer all questions as 'yes'
			this.manager.answerCurrentQuestionYes();
		}

		// End the round and log score
		double score = this.manager.endRound();
		Log.d(this.getClass().getName(), "Score for engineer: " + score);
	}

	/**
	 * A walk-through of a sample pilot round to ensure that a round can be
	 * completed without any errors or bugs.
	 */
	public void testSamplePilotRound () throws Exception {

		// Start the round for a pilot
		this.manager.startRound(new PilotRoundType());

		while (this.manager.hasMoreQuestions()) {

			// Log each question that is displayed
			Log.d(this.getClass().getName(), "Question for pilot: " + this.manager.getCurrentQuestion());

			// Answer all questions as 'yes'
			this.manager.answerCurrentQuestionYes();
		}

		// End the round and log score
		double score = this.manager.endRound();
		Log.d(this.getClass().getName(), "Score for pilot: " + score);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#answerCurrentQuestionYes()}
	 * 
	 */
	public void testAnswerYesBeforeRoundStarted () {

		// Create a new round controller
		RoundController roundController = new RoundController(this.context);

		try {
			// Answer a question before starting the round
			roundController.answerCurrentQuestionYes();
			fail("Answered a question yes before starting a round without receiving an exception");
		}
		catch (RoundNotStartedException e) {
			// An exception was thrown as expected
			assertTrue("RoundNotStartedException recieved when answering a question yes before starting a round", true);
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#answerCurrentQuestionNo()}
	 * 
	 */
	public void testAnswerNoBeforeRoundStarted () {

		// Create a new round controller
		RoundController roundController = new RoundController(this.context);

		try {
			// Answer a question before starting the round
			roundController.answerCurrentQuestionNo();
			fail("Answered a question no before starting a round without receiving an exception");
		}
		catch (RoundNotStartedException e) {
			// An exception was thrown as expected
			assertTrue("RoundNotStartedException recieved when answering a question no before starting a round", true);
		}
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.RoundController#endRound()}
	 * 
	 */
	public void testEndRoundBeforeRoundStarted () {

		// Create a new round controller
		RoundController roundController = new RoundController(this.context);

		try {
			// End round before starting the round
			roundController.endRound();
			fail("Ended a round before starting a round without receiving an exception");
		}
		catch (RoundNotStartedException e) {
			// An exception was thrown as expected
			assertTrue("RoundNotStartedException recieved when ending a round before starting a round", true);
		}
	}

}

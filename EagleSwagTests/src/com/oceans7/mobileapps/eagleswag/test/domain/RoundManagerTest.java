/**
 * @author Justin Albano
 * @date May 29, 2013
 * @file RoundManagerTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 */

package com.oceans7.mobileapps.eagleswag.test.domain;

import android.test.InstrumentationTestCase;

import com.oceans7.mobileapps.eagleswag.domain.RoundManager;

public class RoundManagerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private RoundManager manager;

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
		this.manager = RoundManager.getInstance(this.getInstrumentation().getTargetContext());
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
	 * {@link com.oceans7.mobileapps.eagleswag.domain.RoundManager#startEngineeringRound()}
	 * 
	 */
	public void testStartEngineeringRound () throws Exception {

		// Start engineering round
		this.manager.startEngineeringRound();

		// Ensure that the questions and round have been set in the manager
		assertNotNull("Question queue is set:", this.manager.getCurrentQuestions());
		assertTrue("Questions loaded:", this.manager.hasMoreQuestions());
		assertNotNull("Question manager is set:", this.manager.getQuestionManager());
		assertNotNull("Round is set:", this.manager.getCurrentRound());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.RoundManager#startPilotRound()}
	 * 
	 */
	public void testStartPilotRound () throws Exception {

		// Start engineering round
		this.manager.startPilotRound();

		// Ensure that the questions and round have been set in the manager
		assertNotNull("Question queue is set:", this.manager.getCurrentQuestions());
		assertTrue("Questions loaded:", this.manager.hasMoreQuestions());
		assertNotNull("Question manager is set:", this.manager.getQuestionManager());
		assertNotNull("Round is set:", this.manager.getCurrentRound());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.RoundManager#answerCurrentQuestionYes()}
	 * 
	 */
	public void testAnswerCurrentQuestionYes () throws Exception {

		// Start a round
		this.manager.startPilotRound();

		// Get the original number of questions answered as yes
		int originalYes = this.manager.getCurrentRound().getNumberOfYesQuestions();

		// Submit a yes question
		this.manager.answerCurrentQuestionYes();

		// Ensure that the number of questions answered yes has been incremented
		assertEquals("Number of yes questions incremented:", originalYes + 1, this.manager.getCurrentRound().getNumberOfYesQuestions());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.RoundManager#answerCurrentQuestionNo()}
	 * 
	 */
	public void testAnswerCurrentQuestionNo () throws Exception {

		// Start a round
		this.manager.startPilotRound();

		// Get the original number of questions answered as no
		int originalNo = this.manager.getCurrentRound().getNumberOfNoQuestions();

		// Submit a no question
		this.manager.answerCurrentQuestionNo();

		// Ensure that the number of questions answered no has been incremented
		assertEquals("Number of yes questions incremented:", originalNo + 1, this.manager.getCurrentRound().getNumberOfNoQuestions());
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.RoundManager#endRound()}
	 */
	public void testEndRound () throws Exception {

		// Start a round
		this.manager.startPilotRound();
		
		// End the round
		this.manager.endRound();
		
		// Ensure that the current round and questions has been erased
		assertTrue("Current round unset:", this.manager.getCurrentRound() == null);
		assertTrue("Current questions unset:", this.manager.getCurrentQuestions() == null);
	}

}

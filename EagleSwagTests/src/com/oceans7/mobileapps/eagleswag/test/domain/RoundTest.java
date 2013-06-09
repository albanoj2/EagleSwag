/**
 * @author Justin Albano
 * @date May 19, 2013
 * @file RoundTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       Test fixture for {@link com.oceans7.mobileapps.eagleswag.domain.Round}.
 * 
 *       Ensures that the submission of 'yes' and 'no' answered questions is
 *       properly recorded and that the score for a round is properly
 *       calculated, based on the answered questions submitted to the round.
 */

package com.oceans7.mobileapps.eagleswag.test.domain;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.oceans7.mobileapps.eagleswag.domain.GeneralQuestion;
import com.oceans7.mobileapps.eagleswag.domain.Question;
import com.oceans7.mobileapps.eagleswag.domain.Round;

public class RoundTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The Round object under test.
	 */
	private Round round;

	/**
	 * The context of the application under test.
	 */
	private Context context;

	/***************************************************************************
	 * Setup & Tear Down
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp () throws Exception {
		super.setUp();

		// Create the round object
		this.round = new Round();

		// Set the context of the test fixture
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown () throws Exception {
		super.tearDown();
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#submitYesQuestion(com.oceans7.mobileapps.eagleswag.domain.Question)}
	 * 
	 * Ensures that a question submitted as having been answered 'yes' is
	 * properly recorded by the Round, and the number of 'yes' answers for the
	 * Round has been incremented.
	 */
	public void testSubmitYesQuestion () {

		// Get the number of questions answered yes before submitting an answer
		int countBeforeAddition = this.round.numberOfQuestionsAnsweredYes();

		// Submit a new question as answered 'yes'
		this.round.submitYesQuestion(new GeneralQuestion(0, null, 0, 0, 0));

		// Get the number of questions answered yes after submitting an answer
		int countAfterAddition = this.round.numberOfQuestionsAnsweredYes();

		// Ensure that the number of 'yes' answers has been incremented
		assertEquals("The number of questions answered 'yes' incremented:",
			countBeforeAddition + 1,
			countAfterAddition);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#submitNoQuestion(com.oceans7.mobileapps.eagleswag.domain.Question)}
	 * 
	 * Ensures that a question submitted as having been answered 'no' is
	 * properly recorded by the Round, and the number of 'no' answers for the
	 * Round has been incremented.
	 */
	public void testSubmitNoQuestion () {

		// Get the number of questions answered no before submitting an answer
		int countBeforeAddition = this.round.numberOfQuestionsAnsweredNo();

		// Submit a new question as answered 'no'
		this.round.submitNoQuestion(new GeneralQuestion(0, null, 0, 0, 0));

		// Get the number of questions answered no after submitting an answer
		int countAfterAddition = this.round.numberOfQuestionsAnsweredNo();

		// Ensure that the number of 'no' answers has been incremented
		assertEquals("The number of questions answered 'no' incremented:",
			countBeforeAddition + 1,
			countAfterAddition);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#calculateScore()}
	 * 
	 * Ensures that the score for an example set of questions (with strictly
	 * positive point values) has been properly calculated.
	 */
	public void testCalculateScore () {

		// Create test questions
		Question question1 = new GeneralQuestion(0, null, 10, 5, 0);
		Question question2 = new GeneralQuestion(0, null, 3, 0, 0);
		Question question3 = new GeneralQuestion(0, null, 4, 6, 0);
		Question question4 = new GeneralQuestion(0, null, 7, 4, 0);

		// Submit the questions to the round
		this.round.submitYesQuestion(question1);
		this.round.submitYesQuestion(question2);
		this.round.submitNoQuestion(question3);
		this.round.submitNoQuestion(question4);

		// Calculate the theoretical score:
		// The total possible points should be 10 + 3 + 6 + 7, since this is the
		// total points a person can score on each question, combined. The
		// points earned are 10 + 3 + 6 + 4, since the first two questions were
		// answered 'yes' and the second two were answered 'no'
		double theoPossiblePoints = 10 + 3 + 6 + 7;
		double theoEarnedPoints = 10 + 3 + 6 + 4;
		double theoScore = (theoEarnedPoints / theoPossiblePoints) * 100;

		// Debugging: log the theoretical point value
		Log.d(this.getClass().getName(), "Theoretical score: " + theoScore);

		// The actual score received from the method under testing
		double actualScore = this.round.calculateScore();

		// Ensure that the theoretical and actual scores match
		assertEquals("Theoretical and actual scores match:", theoScore, actualScore);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#calculateScore()}
	 * 
	 * Ensures that the score for an example set of questions (with negative
	 * point values included) has been properly calculated.
	 */
	public void testCalculateScoreWithNegativePoints () {

		// Create test questions
		Question question1 = new GeneralQuestion(0, null, 10, -5, 0);
		Question question2 = new GeneralQuestion(0, null, -3, 0, 0);
		Question question3 = new GeneralQuestion(0, null, 4, 6, 0);
		Question question4 = new GeneralQuestion(0, null, 7, -4, 0);

		// Submit the questions to the round
		this.round.submitYesQuestion(question1);
		this.round.submitYesQuestion(question2);
		this.round.submitNoQuestion(question3);
		this.round.submitNoQuestion(question4);

		// Calculate the theoretical score:
		// The total possible points should be 10 + 0 + 6 + 7, since this is the
		// total points a person can score on each question, combined. The
		// points earned are 10 + (-3) + 6 + (-4), since the first two questions
		// were
		// answered 'yes' and the second two were answered 'no'
		double theoPossiblePoints = 10 + 0 + 6 + 7;
		double theoEarnedPoints = 10 - 3 + 6 - 4;
		double theoScore = (theoEarnedPoints / theoPossiblePoints) * 100;

		// Debugging: log the theoretical point value
		Log.d(this.getClass().getName(), "Theoretical score: " + theoScore);

		// The actual score received from the method under testing
		double actualScore = this.round.calculateScore();

		// Ensure that the theoretical and actual scores match
		assertEquals("Theoretical and actual scores match:", theoScore, actualScore);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.Round#save(android.content.Context)}
	 * 
	 * Ensure that the used count for each of the submitted and saved questions
	 * for a round are incremented after the saved method of the Round has been
	 * called.
	 */
	public void testIncrementQuestionsUsedCountWhenSaved () {

		// Test questions to submit as answered
		Question question1 = new GeneralQuestion(0, null, 0, 0, 0);
		Question question2 = new GeneralQuestion(0, null, 0, 0, 0);

		// Submit a new question as answered 'yes' and another as 'no'
		this.round.submitYesQuestion(question1);
		this.round.submitNoQuestion(question2);

		// Save the questions for the round
		this.round.save(null, this.context);

		// Ensure that the used count of the saved questions have been
		// incremented
		assertEquals("Question 1 used count incremented:", 1, question1.getUsedCount());
		assertEquals("Question 2 used count incremented:", 1, question2.getUsedCount());
	}

}

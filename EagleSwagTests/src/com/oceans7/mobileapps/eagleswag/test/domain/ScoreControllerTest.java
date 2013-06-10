/**
 * @author Justin Albano
 * @date Jun 9, 2013
 * @file ScoreControllerTest.java
 * 
 *       Oceans7 Software
 *       EagleSwag Android Mobile App
 * 
 *       TODO Documentation
 *       FIXME Properly update Javadocs
 */

package com.oceans7.mobileapps.eagleswag.test.domain;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.oceans7.mobileapps.eagleswag.domain.EngineeringStrategy;
import com.oceans7.mobileapps.eagleswag.domain.Score;
import com.oceans7.mobileapps.eagleswag.domain.ScoreController;

public class ScoreControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

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

		// Establish the mock context
		this.context = new RenamingDelegatingContext(this.getInstrumentation().getTargetContext(), "test_");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
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
	 * {@link com.oceans7.mobileapps.eagleswag.domain.ScoreController#getTotalScore(com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetTotalScore () {

		// Save some scores
		new Score(10).save(new EngineeringStrategy(), this.context);
		new Score(30).save(new EngineeringStrategy(), this.context);
		new Score(70).save(new EngineeringStrategy(), this.context);

		// Retrieve the scores from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int total = scoreController.getTotalScore(new EngineeringStrategy());

		// Ensure the total matches the combined scores previously saved
		assertEquals("Total score is correct:", 110, total);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.ScoreController#getAverageScore(com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetAverageScore () {

		// Save some scores
		new Score(0).save(new EngineeringStrategy(), this.context);
		new Score(50).save(new EngineeringStrategy(), this.context);
		new Score(100).save(new EngineeringStrategy(), this.context);

		// Retrieve the average from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int average = scoreController.getAverageScore(new EngineeringStrategy());

		// Ensure the average matches the combined scores previously saved
		assertEquals("Average score is correct:", 50, average);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobileapps.eagleswag.domain.ScoreController#getAverageScore(com.oceans7.mobileapps.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetAverageScoreRounding () {

		// Save some scores
		new Score(10).save(new EngineeringStrategy(), this.context);
		new Score(10).save(new EngineeringStrategy(), this.context);
		new Score(20).save(new EngineeringStrategy(), this.context);

		// Retrieve the average from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int average = scoreController.getAverageScore(new EngineeringStrategy());

		// Ensure the average matches the combined scores previously saved
		assertEquals("Average score is correct:", 13, average);
	}

}

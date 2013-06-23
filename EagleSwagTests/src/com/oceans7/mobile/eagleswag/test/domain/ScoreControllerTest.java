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

import com.oceans7.mobile.eagleswag.domain.Score;
import com.oceans7.mobile.eagleswag.domain.ScoreController;
import com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType;

/**
 * Test cases for {@link com.oceans7.mobile.eagleswag.domain.ScoreController}.
 * 
 * @author Justin Albano
 */
public class ScoreControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The context used for the test cases.
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
	 * {@link com.oceans7.mobile.eagleswag.domain.ScoreController#getTotalScore(com.oceans7.mobile.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetTotalScore () {

		// Save some scores
		new Score(new EngineeringRoundType(), 10).save(this.context);
		new Score(new EngineeringRoundType(), 30).save(this.context);
		new Score(new EngineeringRoundType(), 70).save(this.context);

		// Retrieve the scores from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int total = scoreController.getTotalScore(new EngineeringRoundType());

		// Ensure the total matches the combined scores previously saved
		assertEquals("Total score is correct:", 110, total);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.ScoreController#getAverageScore(com.oceans7.mobile.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetAverageScore () {

		// Save some scores
		new Score(new EngineeringRoundType(), 0).save(this.context);
		new Score(new EngineeringRoundType(), 50).save(this.context);
		new Score(new EngineeringRoundType(), 100).save(this.context);

		// Retrieve the average from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int average = scoreController.getAverageScore(new EngineeringRoundType());

		// Ensure the average matches the combined scores previously saved
		assertEquals("Average score is correct:", 50, average);
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.domain.ScoreController#getAverageScore(com.oceans7.mobile.eagleswag.domain.QuestionStrategy)}
	 * .
	 */
	public void testGetAverageScoreRounding () {

		// Save some scores
		new Score(new EngineeringRoundType(), 10).save(this.context);
		new Score(new EngineeringRoundType(), 10).save(this.context);
		new Score(new EngineeringRoundType(), 20).save(this.context);

		// Retrieve the average from the score controller
		ScoreController scoreController = new ScoreController(this.context);
		int average = scoreController.getAverageScore(new EngineeringRoundType());

		// Ensure the average matches the combined scores previously saved
		assertEquals("Average score is correct:", 13, average);
	}

}

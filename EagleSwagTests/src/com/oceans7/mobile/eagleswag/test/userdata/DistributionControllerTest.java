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

package com.oceans7.mobile.eagleswag.test.userdata;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.oceans7.mobile.eagleswag.domain.questions.EngineeringQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.GeneralQuestion;
import com.oceans7.mobile.eagleswag.domain.questions.PilotQuestion;
import com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType;
import com.oceans7.mobile.eagleswag.domain.roundtype.PilotRoundType;
import com.oceans7.mobile.eagleswag.userdata.DistributionController;

/**
 * TODO Class documentation
 * 
 * @author Justin Albano
 */
public class DistributionControllerTest extends InstrumentationTestCase {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The location (relative to the asset/ directory) of the configuration file
	 * for the question distributions.
	 */
	private static final String ASSET_LOCATION = "config/distribution.cfg";

	/**
	 * The distribution controller under test.
	 */
	private DistributionController distributionController;

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

		// Create distribution controller
		Context context = this.getInstrumentation().getContext();
		this.distributionController = new DistributionController(ASSET_LOCATION, context);
	}

	/***************************************************************************
	 * Test Cases
	 **************************************************************************/

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.userdata.DistributionController#getDistribution(com.oceans7.mobile.eagleswag.domain.RoundType, java.lang.Class)}
	 * .
	 */
	public void testGetDistribution () {

		// Ensure the test distribution data is correct
		assertEquals("Engineering round, general question:",
			5,
			this.distributionController.getDistribution(new EngineeringRoundType(), GeneralQuestion.class));
		assertEquals("Engineering round, engineering question:",
			5,
			this.distributionController.getDistribution(new EngineeringRoundType(), EngineeringQuestion.class));
	}

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.userdata.DistributionController#getDistribution(com.oceans7.mobile.eagleswag.domain.RoundType, java.lang.Class)}
	 * .
	 */
	public void testGetDefaultDistribution () {

		// Ensure the test distribution data is correct when no data is set
		assertEquals("Pilot round, general question:",
			DistributionController.DEFAULT_DISTRIBUTION,
			this.distributionController.getDistribution(new PilotRoundType(), GeneralQuestion.class));
		assertEquals("Pilot round, pilot question:",
			DistributionController.DEFAULT_DISTRIBUTION,
			this.distributionController.getDistribution(new PilotRoundType(), PilotQuestion.class));
	}

}

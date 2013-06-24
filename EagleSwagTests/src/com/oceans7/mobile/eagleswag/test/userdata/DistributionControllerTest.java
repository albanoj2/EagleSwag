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

import java.io.InputStream;
import java.util.Properties;

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
	 * The context used to access the configuration file.
	 */
	private Context context;

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
		this.context = this.getInstrumentation().getContext();
		this.distributionController = new DistributionController(ASSET_LOCATION, this.context);
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

	/**
	 * Test method for
	 * {@link com.oceans7.mobile.eagleswag.userdata.DistributionController#setDistribution(com.oceans7.mobile.eagleswag.domain.RoundType, java.lang.Class, int)}
	 * .
	 */
	public void testSetDefaultDistribution () throws Exception {

		// Load the original properties file
		Properties originalProps = new Properties();
		InputStream is = this.context.getAssets().open(ASSET_LOCATION);
		originalProps.load(is);
		is.close();

		// Obtain the original engineering round, engineering question
		int originalCount = this.distributionController.getDistribution(new EngineeringRoundType(), EngineeringQuestion.class);

		// Set a new value for the engineering round, engineering question
		this.distributionController.setDistribution(new EngineeringRoundType(), EngineeringQuestion.class, 10);

		// Ensure the value was actually set
		assertEquals("New value was set:", 10, this.distributionController.getDistribution(new EngineeringRoundType(), EngineeringQuestion.class));
		
		// Reset the properties file
		originalProps.store(this.context.openFileOutput(ASSET_LOCATION, Context.MODE_PRIVATE), "");
		
		// Ensure the properties file was reset
		assertEquals("File was reset:", originalCount, this.distributionController.getDistribution(new EngineeringRoundType(), EngineeringQuestion.class));
	}

}

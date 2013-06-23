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

package com.oceans7.mobile.eagleswag.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oceans7.mobileapps.eagleswag.R;

/**
 * The main activity that is displayed to the user as the splash screen for the
 * application. This activity contains the components that allow the user to
 * select which type of questions that he or she will answer.
 * 
 * @author Justin Albano
 */
public class SplashScreenActivity extends Activity {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * An enumeration for the type of question round that the user selected to
	 * answer.
	 * 
	 * @author Justin Albano
	 */
	public enum Usertype {
		ENGINEER, PILOT
	}

	/**
	 * The button to select an engineering round of questions.
	 */
	private Button bEngineer;

	/**
	 * The button to select a pilot round of questions.
	 */
	private Button bPilot;

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// Obtain the objects for each of the buttons
		this.bEngineer = (Button) findViewById(R.id.bEngineerQuestions);
		this.bPilot = (Button) findViewById(R.id.bPilotQuestions);

		// Set the onClick listener for the engineer button
		this.bEngineer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {

				// Setup the round controller for engineer and start the
				// activity for answering questions
				transitionToQuestionsAcitivity(v, Usertype.ENGINEER);
			}
		});

		// Set the onClick listener for the pilot button
		this.bPilot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {

				// Setup the round controller for pilot and start the activity
				// for answering questions
				transitionToQuestionsAcitivity(v, Usertype.PILOT);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

	/**
	 * Transitions to questions activity.
	 * 
	 * @param v
	 *            The View that initiated the transition.
	 * @param type
	 *            The type of round that should be started.
	 */
	public void transitionToQuestionsAcitivity (View v, Usertype type) {

		// Switch to the questions activity
		Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
		intent.putExtra("RoundType", type.ordinal());
		startActivity(intent);
	}

}

/**
 * TODO: Documentation
 */

package com.oceans7.mobileapps.eagleswag.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oceans7.mobileapps.eagleswag.R;

public class SplashScreenActivity extends Activity {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	public static final int ENGINEER_TYPE = 0;
	public static final int PILOT_TYPE = 1;
	private Button bEngineer;
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
				transitionToQuestionsAcitivity(v, ENGINEER_TYPE);
			}
		});

		// Set the onClick listener for the pilot button
		this.bPilot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {

				// Setup the round controller for pilot and start the activity
				// for answering questions
				transitionToQuestionsAcitivity(v, PILOT_TYPE);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

	public void transitionToQuestionsAcitivity (View v, int type) {

		// Switch to the questions activity
		Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
		intent.putExtra("QuestionType", type);
		startActivity(intent);
	}

}

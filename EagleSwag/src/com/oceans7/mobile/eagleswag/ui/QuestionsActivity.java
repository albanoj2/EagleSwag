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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextSwitcher;

import com.oceans7.mobile.eagleswag.domain.RoundController;
import com.oceans7.mobile.eagleswag.domain.RoundNotStartedException;
import com.oceans7.mobile.eagleswag.domain.roundtype.EngineeringRoundType;
import com.oceans7.mobile.eagleswag.domain.roundtype.PilotRoundType;
import com.oceans7.mobile.eagleswag.ui.SplashScreenActivity.Usertype;
import com.oceans7.mobileapps.eagleswag.R;

/**
 * Android activity that displays questions to the user and provides buttons for
 * answering the questions. The SplashScreenActivity passes the type of
 * questions to answer. Based on the type supplied, a round of questions is
 * started for the type specified. Once the round of questions has been
 * completed, the score for the round is passed to a ScoreActivity to display
 * the score for the user.
 * 
 * @author Justin Albano
 */
public class QuestionsActivity<T> extends Activity {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/**
	 * The round controller that manages the answering of questions by the user.
	 */
	private RoundController roundController;

	/**
	 * The text switch that displays the text for a question.
	 */
	private TextSwitcher tsQuestion;

	/**
	 * The button used to answer a question 'yes.'
	 */
	private Button bYes;

	/**
	 * The button used to answer a question 'no.'
	 */
	private Button bNo;

	/***************************************************************************
	 * Methods
	 **************************************************************************/

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);

		// Obtain a reference to the question text view and buttons
		this.tsQuestion = (TextSwitcher) findViewById(R.id.tsQuestion);
		this.bYes = (Button) findViewById(R.id.bYes);
		this.bNo = (Button) findViewById(R.id.bNo);

		// Configure the text switcher
		this.tsQuestion.setInAnimation(this, android.R.anim.fade_in);
		this.tsQuestion.setOutAnimation(this, android.R.anim.fade_out);

		// Create a round manager and configure it
		new LoadQuestionData().execute();

		// Setup the onClick listener for the yes button
		this.bYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {

				try {
					// Answer the current question as yes
					roundController.answerCurrentQuestionYes();

					// Display next question if another question is available
					nextQuestionIfPossible();

				}
				catch (RoundNotStartedException e) {
					// The round was not started prior to answering the question
					Log.e(this.getClass().getName(), "Round not started before answering the current question 'yes': " + e);
				}

			}
		});

		// Setup the onClick listener for the no button
		this.bNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {

				try {
					// Answer the current question as no
					roundController.answerCurrentQuestionNo();

					// Display next question if another question is available
					nextQuestionIfPossible();

				}
				catch (RoundNotStartedException e) {
					// The round was not started prior to answering the question
					Log.e(this.getClass().getName(), "Round not started before answering the current question 'no': " + e);
				}

			}
		});

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}

	/**
	 * Helper method to display the next question in the text area of the
	 * activity. If no more questions are available, the current round will be
	 * saved and the score will be displayed on the score activity.
	 */
	private void nextQuestionIfPossible () {

		if (this.roundController.hasMoreQuestions()) {
			// Display the next question if another question is available
			this.tsQuestion.setText(this.roundController.getCurrentQuestion().getQuestionString());
		}
		else {
			// No more questions left for the round

			// Save the current round and obtain the score for the round
			int score = this.roundController.endRound();

			// Render the buttons unusable before the transition occurs
			this.bYes.setOnClickListener(null);
			this.bNo.setOnClickListener(null);

			// Create an intent and send the score with the intent
			Intent intent = new Intent(this, ScoreActivity.class);
			intent.putExtra("Score", score);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * Asynchronous task that loads questions and begins the round of questions
	 * for the user. This task may be lengthy, depending on the number of
	 * questions that must be loaded.
	 * 
	 * @param <Object...>
	 *        The data type of the parameters to pass to the execute() method.
	 * @param <..., Integer,...>
	 *        The data type of the progress of loading (data type to
	 *        quantitatively measure the progress of loading; ie: percentage
	 *        complete).
	 * @param <..., Boolean>
	 *        The data type of the result of the task. In this case, the result
	 *        is Boolean, where true represents the task completely properly,
	 *        and false representing the task completing improperly.
	 */
	private class LoadQuestionData extends AsyncTask<Object, Integer, Boolean> {

		/***********************************************************************
		 * Attributes
		 **********************************************************************/

		/**
		 * The loading (progress) dialog displayed to the user while questions
		 * are being loaded for a round.
		 */
		private ProgressDialog progress;

		/***********************************************************************
		 * Methods
		 **********************************************************************/

		/**
		 * {@inheritDoc}
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 * 
		 *      Displays a loading dialog to the user while the questions for
		 *      the round are loaded.
		 */
		@Override
		protected void onPreExecute () {
			super.onPreExecute();

			// Display a progress dialog to the user
			this.progress = ProgressDialog.show(QuestionsActivity.this, "Loading Questions", "Loading question data...", true);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 * 
		 *      Loads the required questions and begins a round (the type of
		 *      round started depends on the data supplied from the
		 *      SplashScreenActivity that initiated this activity).
		 */
		@Override
		protected Boolean doInBackground (Object... arg0) {

			// Create the round controller
			roundController = new RoundController(QuestionsActivity.this);

			// Obtain the type of questions to load
			Usertype type = Usertype.values()[getIntent().getExtras().getInt("QuestionType")];
			Log.i(this.getClass().getName(), "Type: " + type);

			switch (type) {
			// Load the correct questions based on the type supplied

				case ENGINEER:
					// Engineer was selected
					roundController.startRound(new EngineeringRoundType());
					break;

				case PILOT:
					// Pilot was selected
					roundController.startRound(new PilotRoundType());
					break;
			}

			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 * 
		 *      Displays the first question to the user and dismisses the
		 *      loading dialog box.
		 */
		@Override
		protected void onPostExecute (Boolean success) {
			super.onPostExecute((Boolean) success);

			// Display the first question
			nextQuestionIfPossible();

			// Dismiss the loading bar
			this.progress.dismiss();
		}
	}

}

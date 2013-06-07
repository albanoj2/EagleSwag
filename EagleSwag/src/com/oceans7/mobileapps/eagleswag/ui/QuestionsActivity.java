/**
 * TODO: Documentation
 */

package com.oceans7.mobileapps.eagleswag.ui;

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

import com.oceans7.mobileapps.eagleswag.R;
import com.oceans7.mobileapps.eagleswag.domain.RoundController;
import com.oceans7.mobileapps.eagleswag.domain.RoundNotStartedException;

public class QuestionsActivity extends Activity {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private RoundController roundController;
	private TextSwitcher tsQuestion;
	private Button bYes;
	private Button bNo;

	/***************************************************************************
	 * Methods
	 **************************************************************************/

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
					// Answer the current question as yes and display next
					// question if another question is available
					roundController.answerCurrentQuestionYes();
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
					// Answer the current question as no and display next
					// question if another question is available
					roundController.answerCurrentQuestionNo();
					nextQuestionIfPossible();

				}
				catch (RoundNotStartedException e) {
					// The round was not started prior to answering the question
					Log.e(this.getClass().getName(), "Round not started before answering the current question 'no': " + e);
				}

			}
		});

	}

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

		try {

			if (this.roundController.hasMoreQuestions()) {
				// Display the next question if another question is available
				this.tsQuestion.setText(this.roundController.getCurrentQuestion().getQuestionString());
			}
			else {
				// No more questions left for the round

				// Save the current round and obtain the score for the round
				double score = this.roundController.endRound();
				
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
		catch (RoundNotStartedException e) {
			// The round was not started before trying to display next question
			Log.e(this.getClass().getName(), "Round not started: " + e);
		}
	}

	/**
	 * 
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

		private ProgressDialog progress;

		/***********************************************************************
		 * Methods
		 **********************************************************************/

		/**
		 * 
		 */
		@Override
		protected void onPreExecute () {
			super.onPreExecute();

			// Display a progress dialog to the user
			this.progress = ProgressDialog.show(QuestionsActivity.this, "Loading Questions", "Loading question data...", true);
		}

		/**
		 * 
		 */
		@Override
		protected Boolean doInBackground (Object... arg0) {

			// Create the round controller
			roundController = new RoundController(QuestionsActivity.this);

			// Obtain the type of questions to load
			int type = getIntent().getExtras().getInt("QuestionType");

			// Load the correct questions based on the type supplied
			switch (type) {

				case SplashScreenActivity.ENGINEER_TYPE:
					// Engineer was selected
					roundController.startEngineeringRound();
					break;

				case SplashScreenActivity.PILOT_TYPE:
					// Pilot was selected
					roundController.startPilotRound();
					break;
			}

			return true;
		}

		/**
		 * 
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

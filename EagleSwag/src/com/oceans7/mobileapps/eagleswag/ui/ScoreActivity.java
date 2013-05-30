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
import android.widget.TextView;

import com.oceans7.mobileapps.eagleswag.R;

public class ScoreActivity extends Activity {
	
	private TextView tvScore;
	private TextView tvScoreComments;
	private Button bBackToSplashPage;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		// Obtain the text view for the score and comments, and return button
		this.tvScore = (TextView) findViewById(R.id.tvScore);
		this.tvScoreComments = (TextView) findViewById(R.id.tvScoreComments);
		this.bBackToSplashPage = (Button) findViewById(R.id.bBackToSplashPage);
		
		// Set the onClick listener to go to the splash page
		this.bBackToSplashPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				// Return to splash page
				Intent intent = new Intent(ScoreActivity.this, SplashScreenActivity.class);
				startActivity(intent);
				finish();
			}
		});	
		
		// Get the score from the intent that started this activity
		double score = this.getIntent().getExtras().getDouble("Score");
		this.tvScore.setText(String.format("%.1f", score) + " of 100");
		
		// Set the comments for the score
		this.setComments(score);
		
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}
	
	private void setComments (double score) {
		
		// TODO: Fill in the comments for the score
		
		if (score >= 90) {
			// Score of [90, above)
			this.tvScoreComments.setText("Above 90...");
		}
		else if (score >= 80) {
			// Score of [80, 90)
			this.tvScoreComments.setText("Between 80 and 90...");
		}
		else if (score >= 70) {
			// Score of [70, 80)
			this.tvScoreComments.setText("Between 70 and 80...");
		}
		else if (score >= 60) {
			// Score of [60, 70)
			this.tvScoreComments.setText("Between 60 and 70...");
		}
		else if (score >= 50) {
			// Score of [50, 60)
			this.tvScoreComments.setText("Between 50 and 60...");
		}
		else if (score >= 40) {
			// Score of [40, 50)
			this.tvScoreComments.setText("Between 40 and 50...");
		}
		else if (score >= 30) {
			// Score of [30, 40)
			this.tvScoreComments.setText("Between 30 and 40...");
		}
		else if (score >= 20) {
			// Score of [20, 30)
			this.tvScoreComments.setText("Between 20 and 30...");
		}
		else if (score >= 10) {
			// Score of [10, 20)
			this.tvScoreComments.setText("Between 10 and 20...");
		}
		else {
			// Score of (10, less)
			this.tvScoreComments.setText("Less than 10...");
		}
	}

}

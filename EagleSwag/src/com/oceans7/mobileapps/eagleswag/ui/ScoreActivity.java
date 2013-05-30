/**
 * TODO: Documentation
 */

package com.oceans7.mobileapps.eagleswag.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.oceans7.mobileapps.eagleswag.R;

public class ScoreActivity extends Activity {
	
	private TextView tvScore;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
		// Obtain the text view for the score
		this.tvScore = (TextView) findViewById(R.id.tvScore);
		
		// Get the score from the intent that started this activity
		double score = this.getIntent().getExtras().getDouble("Score");
		this.tvScore.setText("Score: " + score);
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}

}

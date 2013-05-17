package com.oceans7.mobileapps.eagleswag.ui;

import com.oceans7.mobileapps.eagleswag.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LoadingAcitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_loading, menu);
        return true;
    }
    
}

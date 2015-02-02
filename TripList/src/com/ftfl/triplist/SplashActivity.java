package com.ftfl.triplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		/****** Create Thread that will sleep for 3 seconds *************/
		Thread mSplash = new Thread() {
			public void run() {
				try {
					// Thread will sleep for 3 seconds
					sleep(3 * 1000);
					// After 3 seconds redirect to another intent
					Intent iIntent = new Intent(getBaseContext(),
							HomeScreenActivity.class);
					startActivity(iIntent);
					// Remove activity
					finish();
				} catch (Exception e) {
				}
			}
		};
		// start thread
		mSplash.start();
	}
}

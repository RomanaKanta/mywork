package com.ftfl.icare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		prefs = this.getSharedPreferences("ICARE", Context.MODE_PRIVATE);
		

		/****** Create Thread that will sleep for 3 seconds *************/
		Thread mSplash = new Thread() {
			public void run() {
				try {
					// Thread will sleep for 3 seconds
					sleep(3 * 1000);
					String check = prefs.getString("Profile", "");

					if (!check.equals("")) {
						// After 2 seconds redirect to another intent
						Intent i = new Intent(getBaseContext(),
								MainActivity.class);
						startActivity(i);

					} else {
						// After 2 seconds redirect to another intent
						Intent i = new Intent(getBaseContext(),
								UserProfileCreationActivity.class);
						startActivity(i);
					}

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

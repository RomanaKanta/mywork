package com.ftfl.photowithlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	Button mRegristration = null;
	Button mRetrieve = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mRegristration = (Button)findViewById(R.id.breg);
		
		mRegristration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getBaseContext(),CameraActivity.class);
				startActivity(iIntent);
			}
		});
		
		mRetrieve = (Button)findViewById(R.id.bretr);
		mRetrieve.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getBaseContext(),RetrieveActivity.class);
				startActivity(iIntent);
			}
		});
	}

}

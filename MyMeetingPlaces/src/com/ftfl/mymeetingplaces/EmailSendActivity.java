package com.ftfl.mymeetingplaces;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailSendActivity extends Activity {
	EditText etSendAddress = null;
	EditText etSendCCAddress = null;
	EditText etSendSubject = null;
	EditText etSendMsg = null;
	Button btnSendButton = null;
	Bundle mBundle = null;

	String mEmail = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);

		etSendAddress = (EditText) findViewById(R.id.ETto);
		etSendCCAddress = (EditText) findViewById(R.id.ETcc);
		etSendSubject = (EditText) findViewById(R.id.ETsub);
		etSendMsg = (EditText) findViewById(R.id.ETmsg);
		btnSendButton = (Button) findViewById(R.id.send);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mEmail = mBundle.getString("email");

			if (mEmail.length() > 0) {

				// set values in view
				etSendAddress.setText(mEmail);
			}
		}

		btnSendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etSendAddress.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Where to send?",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (etSendMsg.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "What to send?",
							Toast.LENGTH_SHORT).show();
					return;
				}
				sendEmail();

				Intent mIntent = new Intent(getBaseContext(),
						ViewActivity.class);

				startActivity(mIntent);
				finish();
			}
		});
	}

	private void sendEmail() {
		String sendToAddress = etSendAddress.getText().toString();
		String sendCCAddress = etSendCCAddress.getText().toString();
		String sendSubject = etSendSubject.getText().toString();
		String sendMessage = etSendMsg.getText().toString();
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent
				.putExtra(Intent.EXTRA_EMAIL, new String[] { sendToAddress });
		emailIntent.putExtra(Intent.EXTRA_CC, new String[] { sendCCAddress });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, sendSubject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, sendMessage);
		emailIntent.setType("message/rfc822");
//String[] email={mTvContactEmail.getText().toString()};
//		
//		Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));		
//	    emailIntent.setType("text/plain");
//	    
//	    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
//	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//	    emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//	    
//	    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		try {
			startActivity(Intent.createChooser(emailIntent, "Send Mail"));
			finish();
			Toast.makeText(getApplicationContext(), "Finish sending mail",
					Toast.LENGTH_SHORT).show();
		} catch (ActivityNotFoundException exc) {
			Toast.makeText(getApplicationContext(), "No mail client found",
					Toast.LENGTH_SHORT).show();
		}

	}
}

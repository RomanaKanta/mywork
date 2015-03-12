package com.ftfl.mymeetingplaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.database.PlaceDataSource;
import com.ftfl.mymeetingplaces.modelclass.ModelClass;

public class EditActivity extends ActionBarActivity {

	// Variable Declaration
	int mID = 0;
	ImageView mDisImage = null;
	TextView mDisLat = null;
	TextView mDisLng = null;
	TextView mDisDate = null;
	TextView mDisTime = null;
	EditText mDisDescription = null;
	EditText mDisName = null;
	EditText mDisEmail = null;
	EditText mDisMobile = null;
	Button mBtnSave = null;
	Bundle mBundle = null;
	String mImagePath = "";
	String mDesEdit = "";
	String mNameEdit = "";
	String mEmailEdit = "";
	String mMobileEdit = "";
	String mSDisLat = "";
	String mSDisLng = "";
	String mSDisDate = "";
	String mSDisTime = "";
	Bitmap bitmap = null;
	ModelClass mUpdateinfo = null;
	PlaceDataSource mPlaceSource = null;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		// definition - gives variable a reference
		mDisImage = (ImageView) findViewById(R.id.disimg);
		mDisLat = (TextView) findViewById(R.id.dislatiTextView);
		mDisLng = (TextView) findViewById(R.id.dislongiTextView);
		mDisDate = (TextView) findViewById(R.id.disdateTextView);
		mDisTime = (TextView) findViewById(R.id.distimeTextView);
		mDisDescription = (EditText) findViewById(R.id.disdescripET);
		mDisName = (EditText) findViewById(R.id.disnameET);
		mDisEmail = (EditText) findViewById(R.id.disemailET);
		mDisMobile = (EditText) findViewById(R.id.dismobileET);
		mBtnSave = (Button) findViewById(R.id.saveagain);
		mPlaceSource = new PlaceDataSource(this);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mID = mBundle.getInt("id");

			if (mID > 0) {
				mUpdateinfo = mPlaceSource.getDetail(mID);

				// set values in view
				mDisLat.setText(mUpdateinfo.getmLatitude());
				mDisLat.setEnabled(true);
				mDisLat.setFocusable(false);
				mDisLat.setClickable(false);

				mDisLng.setText(mUpdateinfo.getmLongitude());
				mDisLng.setEnabled(true);
				mDisLng.setFocusable(false);
				mDisLng.setClickable(false);

				mDisDate.setText(mUpdateinfo.getmDate());
				mDisDate.setEnabled(true);
				mDisDate.setFocusable(false);
				mDisDate.setClickable(false);

				mDisTime.setText(mUpdateinfo.getmTime());
				mDisTime.setEnabled(true);
				mDisTime.setFocusable(false);
				mDisTime.setClickable(false);

				mDisDescription.setText(mUpdateinfo.getmDiscription());
				mDisDescription.setEnabled(true);
				mDisDescription.setFocusable(true);
				mDisDescription.setClickable(true);

				mDisName.setText(mUpdateinfo.getmName());
				mDisName.setEnabled(true);
				mDisName.setFocusable(true);
				mDisName.setClickable(true);

				mDisEmail.setText(mUpdateinfo.getmEmail());
				mDisEmail.setEnabled(true);
				mDisEmail.setFocusable(true);
				mDisEmail.setClickable(true);

				mDisMobile.setText(mUpdateinfo.getmMobile());
				mDisMobile.setEnabled(true);
				mDisMobile.setFocusable(true);
				mDisMobile.setClickable(true);

				mImagePath = mUpdateinfo.getmFileName();
				previewCapturedImage();
				mDisImage.setImageBitmap(bitmap);
			}
		}

		// OnClick Activity of save button
		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get values
				mNameEdit = mDisName.getText().toString();
				mEmailEdit = mDisEmail.getText().toString();
				mMobileEdit = mDisMobile.getText().toString();
				mDesEdit = mDisDescription.getText().toString();
				mSDisLat = mUpdateinfo.getmLatitude();
				mSDisLng = mUpdateinfo.getmLongitude();
				mSDisDate = mUpdateinfo.getmDate();
				mSDisTime = mUpdateinfo.getmTime();

				// check some condition before data insertion
				if (isEmailValid(mEmailEdit) == true) {
					if (mDesEdit.length() == 0 || mNameEdit.length() == 0
							|| mMobileEdit.length() < 11
							|| mEmailEdit.length() == 0) {
						Toast.makeText(getApplicationContext(), R.string.form,
								Toast.LENGTH_LONG).show();
					} else {
						// call model class
						mUpdateinfo = new ModelClass(mNameEdit, mEmailEdit,
								mMobileEdit, mSDisLat, mSDisLng, mDesEdit,
								mImagePath, mSDisDate, mSDisTime);

						// update data
						long updated = mPlaceSource
								.updateData(mID, mUpdateinfo);
						if (updated >= 0) {
							Toast.makeText(getApplicationContext(),
									R.string.updatecom, Toast.LENGTH_LONG)
									.show();

							Intent mIntent = new Intent(
									getApplicationContext(),
									ReviewActivity.class);
							startActivity(mIntent);

							// Remove activity
							finish();
						} else {
							Toast.makeText(getApplicationContext(),
									R.string.updateprob, Toast.LENGTH_LONG)
									.show();
						}
					}
				}
			}
		});
	}

	/**
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage() {
		try {

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			bitmap = BitmapFactory.decodeFile(mImagePath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	// email validation
	public boolean isEmailValid(String email) {
		Pattern pattern;
		Matcher matcher;

		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else {

			Toast.makeText(getApplicationContext(), R.string.form,
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}

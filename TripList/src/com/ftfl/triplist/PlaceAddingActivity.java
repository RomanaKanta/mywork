package com.ftfl.triplist;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.triplist.database.PlaceListDataSource;
import com.ftfl.triplist.modelclass.PlaceModelClass;

public class PlaceAddingActivity extends ActionBarActivity {

	// Variable Declaration
	EditText mETName = null;
	EditText mETPurpose = null;
	EditText mETAddress = null;
	EditText mETDistrict = null;
	EditText mETLatitude = null;
	EditText mETLongitude = null;
	EditText mETStartDate = null;
	EditText mETEndDate = null;
	EditText mETNote = null;
	TextView mTittle = null;
	ImageView imgPreview = null;
	Button mBtnSave = null;
	Button mBtnPhoto = null;

	String mName = "";
	String mPurpose = "";
	String mAddress = "";
	String mDistrict = "";
	String mLatitude = "";
	String mLongitude = "";
	String mStartDate = "";
	String mEndDate = "";
	String mNote = "";
	static String mCurrentPhotoPath = "";

	int mID = 0;
	Bundle mBundle = null;
	static File mediaFile = null;
	PlaceModelClass mPlace = null;
	PlaceListDataSource mPlaceListDataSource = null;

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	// directory name to store captured images
	private static final String IMAGE_DIRECTORY_NAME = "Trip_List";

	// file url to store image
	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		mPlaceListDataSource = new PlaceListDataSource(this);

		// definition - gives variable a reference
		mTittle = (TextView) findViewById(R.id.create);
		mETName = (EditText) findViewById(R.id.etName);
		mETPurpose = (EditText) findViewById(R.id.etPurpose);
		mETAddress = (EditText) findViewById(R.id.etAddress);
		mETDistrict = (EditText) findViewById(R.id.etDistrict);
		mETLatitude = (EditText) findViewById(R.id.etLatitude);
		mETLongitude = (EditText) findViewById(R.id.etLongitude);
		mETStartDate = (EditText) findViewById(R.id.etStartDate);
		mETEndDate = (EditText) findViewById(R.id.etEndDate);
		mETNote = (EditText) findViewById(R.id.etNote);
		mBtnSave = (Button) findViewById(R.id.bSave);
		mBtnPhoto = (Button) findViewById(R.id.bPhoto);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		// this part will execute if this activity called update
		if (mBundle != null) {
			mTittle.setText(R.string.update);
			mID = mBundle.getInt("id");

			if (mID > 0) {
				mPlaceListDataSource = new PlaceListDataSource(this);
				mPlace = mPlaceListDataSource.getDetail(mID);

				// set values in edit text what will update
				mETName.setText(mPlace.getmName());
				mETName.setEnabled(true);
				mETName.setFocusable(true);
				mETName.setClickable(true);

				mETPurpose.setText(mPlace.getmPurpose());
				mETPurpose.setEnabled(true);
				mETPurpose.setFocusable(true);
				mETPurpose.setClickable(true);

				mETAddress.setText(mPlace.getmAddress());
				mETAddress.setEnabled(true);
				mETAddress.setFocusable(true);
				mETAddress.setClickable(true);

				mETDistrict.setText(mPlace.getmDistrict());
				mETDistrict.setEnabled(true);
				mETDistrict.setFocusable(true);
				mETDistrict.setClickable(true);

				mETLatitude.setText(mPlace.getmLatitude());
				mETLatitude.setEnabled(true);
				mETLatitude.setFocusable(true);
				mETLatitude.setClickable(true);

				mETLongitude.setText(mPlace.getmLongitude());
				mETLongitude.setEnabled(true);
				mETLongitude.setFocusable(true);
				mETLongitude.setClickable(true);

				mETStartDate.setText(mPlace.getmStartDate());
				mETStartDate.setEnabled(true);
				mETStartDate.setFocusable(true);
				mETStartDate.setClickable(true);

				mETEndDate.setText(mPlace.getmEndDate());
				mETEndDate.setEnabled(true);
				mETEndDate.setFocusable(true);
				mETEndDate.setClickable(true);

				mETNote.setText(mPlace.getmNote());
				mETNote.setEnabled(true);
				mETNote.setFocusable(true);
				mETNote.setClickable(true);
			}
		}

		// on click activity of photo button
		mBtnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// capture picture
				captureImage();
				mCurrentPhotoPath = mediaFile.getAbsolutePath();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(), R.string.camerachack,
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

		// on click activity of save button
		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDate();

				if (mBundle != null) {
					mPlace = new PlaceModelClass(mName, mPurpose, mAddress,
							mDistrict, mLatitude, mLongitude, mStartDate,
							mEndDate, mNote, mCurrentPhotoPath);

					long updated = mPlaceListDataSource.updateData(mID, mPlace);
					if (updated >= 0) {
						Toast.makeText(getApplicationContext(),
								R.string.updatecom, Toast.LENGTH_LONG).show();

						Intent mIntent = new Intent(getApplicationContext(),
								HomeScreenActivity.class);

						startActivity(mIntent);
						finish();

					} else {
						Toast.makeText(getApplicationContext(),
								R.string.updateprob, Toast.LENGTH_LONG).show();
					}

				} else if (mETName.length() == 0 || mETPurpose.length() == 0
						|| mETAddress.length() == 0
						|| mETDistrict.length() == 0
						|| mETLatitude.length() == 0
						|| mETLongitude.length() == 0
						|| mETStartDate.length() == 0
						|| mETEndDate.length() == 0 || mETNote.length() == 0) {

					// show toast when not correctly completed
					Toast.makeText(getApplicationContext(), R.string.form,
							Toast.LENGTH_SHORT).show();

				} else if (mETPurpose.length() < 25) {
					Toast.makeText(getApplicationContext(), R.string.condition,
							Toast.LENGTH_SHORT).show();

				} else {
					mPlace = new PlaceModelClass(mName, mPurpose, mAddress,
							mDistrict, mLatitude, mLongitude, mStartDate,
							mEndDate, mNote, mCurrentPhotoPath);

					long inserted = mPlaceListDataSource.addNewPlace(mPlace);
					if (inserted >= 0) {
						Toast.makeText(getApplicationContext(),
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();

						Intent mIntent = new Intent(getApplicationContext(),
								HomeScreenActivity.class);
						startActivity(mIntent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.fail), Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});
	}

	void getDate() {
		mName = mETName.getText().toString();
		mPurpose = mETPurpose.getText().toString();
		mAddress = mETAddress.getText().toString();
		mDistrict = mETDistrict.getText().toString();
		mLatitude = mETLatitude.getText().toString();
		mLongitude = mETLongitude.getText().toString();
		mStartDate = mETStartDate.getText().toString();
		mEndDate = mETEndDate.getText().toString();
		mNote = mETNote.getText().toString();
	}

	/**
	 * Checking device has camera hardware or not
	 * */
	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/**
	 * Capturing Camera Image will launch camera app request image capture
	 */
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image
				Toast.makeText(getApplicationContext(), R.string.imagecaptured,
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(), R.string.imagecancled,
						Toast.LENGTH_SHORT).show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(), R.string.imagefailed,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * ------------ Helper Methods ----------------------
	 * */

	/**
	 * Creating file uri to store image
	 */
	public Uri getOutputMediaFileUri(int type) {
		Uri mUri = Uri.fromFile(getOutputMediaFile(type));
		return mUri;
	}

	/**
	 * returning image
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());

		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}

	// add & update screen menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_of_create, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.home:
			// go to home page
			Intent i = new Intent(getBaseContext(), HomeScreenActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

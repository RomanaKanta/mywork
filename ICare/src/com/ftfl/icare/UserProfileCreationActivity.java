package com.ftfl.icare;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.modelclass.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserProfileCreationActivity extends Activity {

	// Variable Declaration
	Button mSave = null;
	Button mUserPhoto = null;
	ImageView mImagePreview = null;
	EditText mUserNameET = null;
	TextView mBirthDateTV = null;
	TextView mTitle = null;
	EditText mHeightET = null;
	EditText mWeightET = null;
	EditText mSpecialInfoET = null;
	String mImagePath = "";
	String mUserName = "";
	String mBirthDate = "";
	String mHeight = "";
	String mWeight = "";
	String mSpecialInfo = "";
	String check = "";
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	int mID = 0;

	Bundle mBundle = null;
	static File mediaFile = null;
	Bitmap bitmap = null;
	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	// directory name to store captured images
	private static final String IMAGE_DIRECTORY_NAME = "ICARE_Gallery";

	// file url to store image
	private Uri fileUri;
	final Calendar mCalendar = Calendar.getInstance();
	UserModel mUser = null;
	Gson gson;
	Editor prefsEditor;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile_create);

		// definition - gives variable a reference
		prefs = this.getSharedPreferences("ICARE", Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		mTitle = (TextView) findViewById(R.id.protitle);
		mUserNameET = (EditText) findViewById(R.id.usernameet);
		mBirthDateTV = (TextView) findViewById(R.id.birthdateet);
		mHeightET = (EditText) findViewById(R.id.htet);
		mWeightET = (EditText) findViewById(R.id.wtet);
		mSpecialInfoET = (EditText) findViewById(R.id.specialinfoet);
		mUserPhoto = (Button) findViewById(R.id.userphotobtn);
		mImagePreview = (ImageView) findViewById(R.id.viewuserimage);
		mSave = (Button) findViewById(R.id.userprosavebtn);

		// OnClick Activity of date field
		mBirthDateTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						UserProfileCreationActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();
			}
		});

		// OnClick Activity of photo button
		mUserPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// capture picture
				captureImage();
				mImagePath = mediaFile.getAbsolutePath();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(this, R.string.camerachack, Toast.LENGTH_LONG)
					.show();
			// will close the app if the device does't have camera
			// finish();
		}

		String check = prefs.getString("Profile", "");
		if (!check.equals("")) {
			// get object
			Gson gson = new Gson();
			String jsonProfile = prefs.getString("Profile", null);
			Type type = new TypeToken<UserModel>() {
			}.getType();
			mUser = gson.fromJson(jsonProfile, type);
			mTitle.setText(R.string.editpro);
			mUserNameET.setText(mUser.getmUserName());
			mBirthDateTV.setText(mUser.getmBirthDate());
			mHeightET.setText(mUser.getmHeight());
			mWeightET.setText(mUser.getmWeight());
			mSpecialInfoET.setText(mUser.getmSprcialInfo());
			mYear = mUser.getmYear();
			mMonth = mUser.getmMonth();
			mDay = mUser.getmDay();
			mImagePath = mUser.getmPhoto();
			try {

				// bimatp factory
				BitmapFactory.Options options = new BitmapFactory.Options();

				// downsizing image as it throws OutOfMemory Exception for
				// larger
				// images
				options.inSampleSize = 8;

				bitmap = BitmapFactory.decodeFile(mImagePath, options);

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			mImagePreview.setImageBitmap(bitmap);
		}

		// OnClick Activity of save button
		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetDataFromView();

				if (mUserName.length() == 0 || mBirthDate.length() == 0
						|| mHeight.length() == 0 || mWeight.length() == 0
						|| mSpecialInfo.length() == 0) {
					// show toast when not correctly completed
					Toast.makeText(UserProfileCreationActivity.this,
							R.string.complete, Toast.LENGTH_SHORT).show();
				} else if (mImagePath.length() == 0) {
					Toast.makeText(UserProfileCreationActivity.this,
							R.string.takephoto, Toast.LENGTH_SHORT).show();
				} else {
					mUser = new UserModel(mUserName, mBirthDate, mHeight,
							mWeight, mSpecialInfo, mImagePath, mDay, mMonth,
							mYear);
					gson = new Gson();
					saveObject(mUser);

					Toast.makeText(getBaseContext(),
							getString(R.string.insert), Toast.LENGTH_LONG)
							.show();
					mediaFile = null;
					Intent mIntent = new Intent(
							UserProfileCreationActivity.this,
							MainActivity.class);
					startActivity(mIntent);
					finish();
				}
			}
		});
	}

	public void GetDataFromView() {
		mUserName = mUserNameET.getText().toString();
		mBirthDate = mBirthDateTV.getText().toString();
		mHeight = mHeightET.getText().toString();
		mWeight = mWeightET.getText().toString();
		mSpecialInfo = mSpecialInfoET.getText().toString();
	}

	public void saveObject(UserModel object) {

		String json = gson.toJson(object);
		prefsEditor.putString("Profile", json);
		prefsEditor.commit();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mBirthDateTV.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	/**
	 * Checking device has camera hardware or not
	 * */
	private boolean isDeviceSupportCamera() {
		if (this.getPackageManager().hasSystemFeature(
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			previewCapturedImage();
		}
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

			bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
			// set image in image view
			mImagePreview.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
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
}

package com.ftfl.mymeetingplaces;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.database.PlaceDataSource;
import com.ftfl.mymeetingplaces.modelclass.ModelClass;

public class RegistrationActivity extends ActionBarActivity implements
		LocationListener {

	// Variable Declaration
	EditText mETName = null;
	EditText mETEmail = null;
	EditText mETMobile = null;
	EditText mETLatitude = null;
	EditText mETLongitude = null;
	EditText mPlaceName = null;
	EditText mAgenda = null;
	ImageView mImage = null;
	Button mPhoto = null;
	Button mSave = null;
	String mName = "";
	String mEmail = "";
	String mMobile = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	String mPhotoPath = "";
	String mDate = "";
	String mTime = "";

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	// directory name to store captured images
	private static final String IMAGE_DIRECTORY_NAME = "My Photo";

	private Uri fileUri = null; // file url to store image
	File mediaFile = null;

	ModelClass mModelClass = null;
	PlaceDataSource mPlaceDataSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		// definition - gives variable a reference
		mETLatitude = (EditText) findViewById(R.id.latitude);
		mETLongitude = (EditText) findViewById(R.id.longitude);
		mPlaceName = (EditText) findViewById(R.id.placeEditText);
		mAgenda = (EditText) findViewById(R.id.agendaEditText);
		mETName = (EditText) findViewById(R.id.nameEditText);
		mETEmail = (EditText) findViewById(R.id.emailEditText);
		mETMobile = (EditText) findViewById(R.id.mobileEditText);
		mImage = (ImageView) findViewById(R.id.viewpimage);
		mSave = (Button) findViewById(R.id.save);
		mPhoto = (Button) findViewById(R.id.photo);
		mPlaceDataSource = new PlaceDataSource(this);

		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, this);

		// OnClick Activity of take photo button
		mPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// capture picture
				captureImage();

				// get image path
				mPhotoPath = mediaFile.getAbsolutePath();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(), R.string.camerachack,
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

		// OnClick Activity of save button
		mSave.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get values
				mName = mETName.getText().toString();
				mEmail = mETEmail.getText().toString();
				mMobile = mETMobile.getText().toString();
				mLatitude = mETLatitude.getText().toString();
				mLongitude = mETLongitude.getText().toString();
				mDescription = mPlaceName.getText().toString() + "  #  "
						+ mAgenda.getText().toString();// concate place name &
														// agenda

				// get current date
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
						Locale.getDefault());
				Date date = new Date();
				mDate = dateFormat.format(date);

				// get current time
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				mTime = sdf.format(cal.getTime());

				// check some condition before data insertion
				if (isEmailValid(mEmail) == true) {
					if (mLatitude.length() == 0 || mLongitude.length() == 0) {
						Toast.makeText(getApplicationContext(),
								R.string.latlngnotfound, Toast.LENGTH_LONG)
								.show();
					} else if (mPlaceName.length() == 0 || mName.length() == 0
							|| mMobile.length() < 11 || mEmail.length() == 0) {
						Toast.makeText(getApplicationContext(), R.string.form,
								Toast.LENGTH_LONG).show();
					} else if (mPhotoPath.length() == 0) {

						Toast.makeText(getApplicationContext(),
								R.string.phototake, Toast.LENGTH_LONG).show();
					} else {

						// call model class
						mModelClass = new ModelClass(mName, mEmail, mMobile,
								mLatitude, mLongitude, mDescription,
								mPhotoPath, mDate, mTime);

						// insert data
						long inserted = mPlaceDataSource.addNew(mModelClass);
						if (inserted >= 0) {
							Toast.makeText(getApplicationContext(),
									R.string.insert, Toast.LENGTH_SHORT).show();

							Intent mIntent = new Intent(
									getApplicationContext(), HomeActivity.class);
							startActivity(mIntent);

							// Remove activity
							finish();
						} else {
							Toast.makeText(getApplicationContext(),
									R.string.fail, Toast.LENGTH_LONG).show();
						}
					}
				}
			}
		});
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
	 * Capturing Camera Image will lauch camera app requrest image capture
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
				// display it in image view
				previewCapturedImage();
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
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage() {
		try {

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			// set image in image view
			mImage.setImageBitmap(bitmap);
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
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * returning image
	 */
	private File getOutputMediaFile(int type) {

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

	@Override
	public void onLocationChanged(Location loc) {

		// get latitude longitude
		double latitude = loc.getLatitude();
		double longitude = loc.getLongitude();

		// set latitude longitude in view
		mETLatitude.setText(Double.toString(latitude));
		mETLongitude.setText(Double.toString(longitude));
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "Gps Disabled",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "Gps Enabled",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

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

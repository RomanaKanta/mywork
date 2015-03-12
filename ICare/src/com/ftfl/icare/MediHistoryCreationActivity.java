package com.ftfl.icare;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.ftfl.icare.database.HistoryDataSource;
import com.ftfl.icare.modelclass.HistoryModel;

public class MediHistoryCreationActivity extends Activity {

	// Variable Declaration
	Button mSaveMediHistory = null;
	Button mUploadPrescription = null;
	ImageView mPrescription = null;
	EditText mDoctorET = null;
	EditText mDateET = null;
	EditText mReasonET = null;
	EditText mSuggestionET = null;

	String mDoctor = "";
	String mDate = "";
	String mReason = "";
	String mSuggestion = "";
	String mPrescriptionPath = "";
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;

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
	HistoryModel mHistoryModel = null;
	HistoryDataSource mHistoryDataSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical_history_create);

		mHistoryDataSource = new HistoryDataSource(this);

		// definition - gives variable a reference
		mDoctorET = (EditText) findViewById(R.id.mdhdocnametet);
		mDateET = (EditText) findViewById(R.id.mdhdateet);
		mReasonET = (EditText) findViewById(R.id.mdhreasonet);
		mSuggestionET = (EditText) findViewById(R.id.mdhsugget);
		mPrescription = (ImageView) findViewById(R.id.viewprescripimage);
		mUploadPrescription = (Button) findViewById(R.id.prescripbtn);
		mSaveMediHistory = (Button) findViewById(R.id.mdhsavebtn);

		// OnClick Activity of date field
		mDateET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						MediHistoryCreationActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();
			}
		});

		// OnClick Activity of image field
		mUploadPrescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// capture picture
				captureImage();
				mPrescriptionPath = mediaFile.getAbsolutePath();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getBaseContext(), R.string.camerachack,
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			// finish();
		}

		// OnClick Activity of save button
		mSaveMediHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetDataFromView();

				if (mDoctor.length() == 0 || mDate.length() == 0
						|| mReason.length() == 0 || mSuggestion.length() == 0
						|| mPrescriptionPath.length() == 0) {
					Toast.makeText(MediHistoryCreationActivity.this,
							R.string.complete, Toast.LENGTH_SHORT).show();
				} else {
					mHistoryModel = new HistoryModel(mDoctor, mDate, mReason,
							mSuggestion, mPrescriptionPath);

					long inserted = mHistoryDataSource
							.addNewHistory(mHistoryModel);
					if (inserted >= 0) {
						Toast.makeText(MediHistoryCreationActivity.this,
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();
						Intent iIntent = new Intent(getBaseContext(),
								DisplayMediHistoryActivity.class);
						startActivity(iIntent);
						finish();

					} else {
						Toast.makeText(MediHistoryCreationActivity.this,
								getString(R.string.fail), Toast.LENGTH_LONG)
								.show();
					}

				}
			}
		});
	}

	public void GetDataFromView() {
		mDoctor = mDoctorET.getText().toString();
		mDate = mDateET.getText().toString();
		mReason = mReasonET.getText().toString();
		mSuggestion = mSuggestionET.getText().toString();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mDateET.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	/**
	 * Checking device has camera hardware or not
	 * */
	private boolean isDeviceSupportCamera() {
		if (getBaseContext().getPackageManager().hasSystemFeature(
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
			mPrescription.setImageBitmap(bitmap);
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

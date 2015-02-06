package com.ftfl.photowithlocation;

import java.util.Calendar;

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
import android.widget.Toast;

import com.ftfl.photowithlocation.database.ImageListDataSource;
import com.ftfl.photowithlocation.modelclass.PhotoModelClass;

public class PreViewActivity extends ActionBarActivity {

	ImageView mImgPreview = null;
	EditText mRemark = null;
	Button mSave = null;
	String mPhotoPath = "";
	String mDate = "";
	String mTime = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	PhotoModelClass modelObj = null;
	ImageListDataSource mImageListDataSource = null;
	int cDay, cMonth, cYear, cHour, cMinute, cSecond = 0;
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_view);

		mImgPreview = (ImageView) findViewById(R.id.imgpreview);
		mRemark = (EditText) findViewById(R.id.etremark);
		mSave = (Button) findViewById(R.id.btnsave);

		// get the Intent that started this Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of this Activity
		Bundle mBundle = mIntent.getExtras();
		mPhotoPath = mBundle.getString("image");

		previewCapturedImage();
		mImgPreview.setImageBitmap(bitmap);

		Calendar calander = Calendar.getInstance();
		cDay = calander.get(Calendar.DAY_OF_MONTH);
		cMonth = calander.get(Calendar.MONTH) + 1;
		cYear = calander.get(Calendar.YEAR);
		cHour = calander.get(Calendar.HOUR);
		cMinute = calander.get(Calendar.MINUTE);
		cSecond = calander.get(Calendar.SECOND);
		// date.setText(mDate);
		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mLatitude = "23.908756";
				mLongitude = "90.234574322";
				mDescription = mRemark.getText().toString();
				mDate = "" + cDay + "-" + cMonth + "-" + cYear;
				mTime = "" + cHour + " : " + cMinute + " : " + cSecond;

				if (mLatitude.length() == 0 || mLongitude.length() == 0
						|| mDescription.length() == 0
						|| mPhotoPath.length() == 0 || mDate.length() == 0
						|| mTime.length() == 0) {
					Toast.makeText(getApplicationContext(), "data loss",
							Toast.LENGTH_SHORT).show();
				} else {
					modelObj = new PhotoModelClass(mLatitude, mLongitude,
							mDescription, mPhotoPath, mDate, mTime);

					long inserted = mImageListDataSource.addNew(modelObj);
					if (inserted >= 0) {
						Toast.makeText(getApplicationContext(),
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();

						Intent mIntent = new Intent(getApplicationContext(),
								MainActivity.class);
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

			bitmap = BitmapFactory.decodeFile(mPhotoPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
package com.ftfl.triplist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.triplist.database.PlaceListDataSource;
import com.ftfl.triplist.modelclass.PlaceModelClass;

public class ViewDetailActivity extends ActionBarActivity {

	// variable declaration
	TextView mShowName = null;
	TextView mShowPurpose = null;
	TextView mShowAddress = null;
	TextView mShowDistrict = null;
	TextView mShowLatitude = null;
	TextView mShowLongitude = null;
	TextView mShowStartDate = null;
	TextView mShowEndDate = null;
	TextView mShowNote = null;
	ImageView mShowImage = null;
	Button mShowMap = null;
	int mId = 0;
	PlaceModelClass mPlace = null;
	PlaceListDataSource mPlaceSource = null;
	String mPhotoPath = "";
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		// get the Intent that started this Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of this Activity
		Bundle mBundle = mIntent.getExtras();
		mId = mBundle.getInt("id");

		// definition - gives variable a reference
		mShowName = (TextView) findViewById(R.id.showName);
		mShowPurpose = (TextView) findViewById(R.id.showPurpose);
		mShowAddress = (TextView) findViewById(R.id.showAddress);
		mShowDistrict = (TextView) findViewById(R.id.showDistrict);
		mShowLatitude = (TextView) findViewById(R.id.showLatitude);
		mShowLongitude = (TextView) findViewById(R.id.showLongitude);
		mShowStartDate = (TextView) findViewById(R.id.showStartDate);
		mShowEndDate = (TextView) findViewById(R.id.showEndDate);
		mShowNote = (TextView) findViewById(R.id.showNote);
		mShowImage = (ImageView) findViewById(R.id.ivPhoto);
		mShowMap = (Button) findViewById(R.id.showMap);

		mPlaceSource = new PlaceListDataSource(this);

		// Getting All place detail
		mPlace = mPlaceSource.getDetail(mId);

		// set value in text view
		mShowName.setText(mPlace.getmName());
		mShowPurpose.setText(mPlace.getmPurpose());
		mShowAddress.setText(mPlace.getmAddress());
		mShowDistrict.setText(mPlace.getmDistrict());
		mShowLatitude.setText(mPlace.getmLatitude());
		mShowLongitude.setText(mPlace.getmLongitude());
		mShowStartDate.setText(mPlace.getmStartDate());
		mShowEndDate.setText(mPlace.getmEndDate());
		mShowNote.setText(mPlace.getmNote());

		// preview Captured Image from database
		mPhotoPath = mPlace.getmImage();
		if(mPhotoPath.length()>0){
		previewCapturedImage();		
		mShowImage.setImageBitmap(bitmap);
		}else{
			mShowImage.setImageResource(R.drawable.ic_launcher);
		}

		// on click activity of map show button
		mShowMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle mBundle = new Bundle();

				// put id into bundle
				mBundle.putInt("id", mId);
				Intent mIntent = new Intent(getApplicationContext(),
						MapActivity.class);
				mIntent.putExtras(mBundle);
				startActivity(mIntent);
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
			options.inSampleSize = 10;

			bitmap = BitmapFactory.decodeFile(mPhotoPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	// view screen menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_of_view, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.home:
			// go back to home page
			home();
			return true;

		case R.id.edit:
			// edit profile
			edit();
			return true;

		case R.id.delete:
			// delete profile
			delete();
			return true;

		case R.id.add:
			// add new profile
			add();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void home() {
		Intent mIntent = new Intent(getBaseContext(), HomeScreenActivity.class);
		startActivity(mIntent);
	}

	private void edit() {
		Bundle mBundle = new Bundle();
		mBundle.putInt("id", mId);
		Intent mIntent = new Intent(getBaseContext(), PlaceAddingActivity.class);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
	}

	private void delete() {
		mPlaceSource = new PlaceListDataSource(this);
		mPlaceSource.deleteData(mId);
		Intent mIntent = new Intent(getBaseContext(), HomeScreenActivity.class);
		startActivity(mIntent);
	}

	private void add() {
		Intent mIntent = new Intent(getBaseContext(), PlaceAddingActivity.class);
		startActivity(mIntent);
	}
}
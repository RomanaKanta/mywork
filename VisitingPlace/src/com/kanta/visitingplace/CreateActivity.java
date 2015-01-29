package com.kanta.visitingplace;

import com.kanta.visitingplace.database.PlaceListDataSource;
import com.kanta.visitingplace.modelclass.PlaceModelClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends ActionBarActivity {

	EditText mETName = null;
	EditText mETPurpose = null;
	EditText mETAddress = null;
	EditText mETDistrict = null;
	EditText mETLatitude = null;
	EditText mETLongitude = null;
	EditText mETStartDate = null;
	EditText mETEndDate = null;
	EditText mETNote = null;
	Button bSave = null;

	String mName = "";
	String mPurpose = "";
	String mAddress = "";
	String mDistrict = "";
	String mLatitude = "";
	String mLongitude = "";
	String mStartDate = "";
	String mEndDate = "";
	String mNote = "";

	PlaceModelClass place;
	PlaceListDataSource placeListDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		placeListDataSource = new PlaceListDataSource(this);

		// Initialization
		mETName = (EditText) findViewById(R.id.etName);
		mETPurpose = (EditText) findViewById(R.id.etPurpose);
		mETAddress = (EditText) findViewById(R.id.etAddress);
		mETDistrict = (EditText) findViewById(R.id.etDistrict);
		mETLatitude = (EditText) findViewById(R.id.etLatitude);
		mETLongitude = (EditText) findViewById(R.id.etLongitude);
		mETStartDate = (EditText) findViewById(R.id.etStartDate);
		mETEndDate = (EditText) findViewById(R.id.etEndDate);
		mETNote = (EditText) findViewById(R.id.etNote);
		bSave = (Button) findViewById(R.id.bSave);

		bSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDate();

				if (mETName.length() == 0 || mETPurpose.length() == 0
						|| mETAddress.length() == 0
						|| mETDistrict.length() == 0
						|| mETLatitude.length() == 0
						|| mETLongitude.length() == 0
						|| mETStartDate.length() == 0
						|| mETEndDate.length() == 0 || mETNote.length() == 0) {
					
					// show toast when not correctly completed
					Toast.makeText(getApplicationContext(),
							"Complete the form correctly", Toast.LENGTH_SHORT)
							.show();
				}else if(mETPurpose.length() < 225){
					Toast.makeText(getApplicationContext(),
							"Purpose should be at least 225 character", Toast.LENGTH_SHORT)
							.show();
				}else{
				place = new PlaceModelClass(mName, mPurpose, mAddress,
						mDistrict, mLatitude, mLongitude, mStartDate, mEndDate,
						mNote);
				long inserted = placeListDataSource.addNewPlace(place);
				if (inserted >= 0) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.insert), Toast.LENGTH_LONG)
							.show();

					Intent i = new Intent(getApplicationContext(),
							HomeActivity.class);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.fail), Toast.LENGTH_LONG).show();
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
			// profile found
			Intent i = new Intent(getBaseContext(), HomeActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

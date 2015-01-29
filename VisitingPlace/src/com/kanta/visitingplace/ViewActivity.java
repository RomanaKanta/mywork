package com.kanta.visitingplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kanta.visitingplace.database.PlaceListDataSource;
import com.kanta.visitingplace.modelclass.PlaceModelClass;

public class ViewActivity extends ActionBarActivity {
	TextView mShowName = null;
	TextView mShowPurpose = null;
	TextView mShowAddress = null;
	TextView mShowDistrict = null;
	TextView mShowLatitude = null;
	TextView mShowLongitude = null;
	TextView mShowStartDate = null;
	TextView mShowEndDate = null;
	TextView mShowNote = null;
	
	Button showMap;
	 
	 Long mId ;
	
	PlaceModelClass place = null;
	PlaceListDataSource placeSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		 // get the Intent that started this Activity
        Intent in = getIntent();
 
        // get the Bundle that stores the data of this Activity
        Bundle b = in.getExtras();
        
        mId = b.getLong("id");
        
		mShowName = (TextView) findViewById(R.id.showName);
		mShowPurpose = (TextView) findViewById(R.id.showPurpose);
		mShowAddress = (TextView) findViewById(R.id.showAddress);
		mShowDistrict = (TextView) findViewById(R.id.showDistrict);
		mShowLatitude = (TextView) findViewById(R.id.showLatitude);
		mShowLongitude = (TextView) findViewById(R.id.showLongitude);
		mShowStartDate = (TextView) findViewById(R.id.showStartDate);
		mShowEndDate = (TextView) findViewById(R.id.showEndDate);
		mShowNote = (TextView) findViewById(R.id.showNote);
		
		showMap = (Button)findViewById(R.id.showMap);

		placeSource = new PlaceListDataSource(this);
		place = placeSource.getDetail(mId);
		String mName = place.getmName();
		String mPurpose = place.getmPurpose();
		String mAddress = place.getmAddress();
		String mDistrict = place.getmDistrict();
		String mLatitude = place.getmLatitude();
		String mLongitude = place.getmLongitude();
		String mStartDate = place.getmStartDate();
		String mEndDate = place.getmEndDate();
		String mNote = place.getmNote();
		
		mShowName.setText(mName);
		mShowPurpose.setText(mPurpose);
		mShowAddress.setText(mAddress);
		mShowDistrict.setText(mDistrict);
		mShowLatitude.setText(mLatitude);
		mShowLongitude.setText(mLongitude);
		mShowStartDate.setText(mStartDate);
		mShowEndDate.setText(mEndDate);
		mShowNote.setText(mNote);
		
	
		showMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();

				// put id into bundle
				b.putLong("id", mId);
				Intent i = new Intent(getApplicationContext(),
						MapActivity.class);
				startActivity(i);
			}
		});
	}
	
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
			// profile found
			home();
			return true;
			
		case R.id.edit:
			// profile found
			edit();
			return true;
			
		case R.id.delete:
			// profile found
			delete();
			return true;
			
		case R.id.add:
			// add new
			add();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void home() {
		Intent i = new Intent(getBaseContext(), HomeActivity.class);
		startActivity(i);

	}
	
	private void edit() {
		Intent i = new Intent(getBaseContext(),CreateActivity.class);
		startActivity(i);

	}
	
	private void delete() {
		placeSource = new PlaceListDataSource(this);
		placeSource.deleteData(mId);
		Intent i = new Intent(getBaseContext(),HomeActivity.class);
		startActivity(i);

	}
	
	private void add() {
		Intent i = new Intent(getBaseContext(), CreateActivity.class);
		startActivity(i);

	}
}

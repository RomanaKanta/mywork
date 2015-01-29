package com.kanta.visitingplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kanta.visitingplace.database.PlaceListDataSource;
import com.kanta.visitingplace.modelclass.PlaceModelClass;

public class MapActivity extends ActionBarActivity {

	GoogleMap googleMap;
	PlaceListDataSource mPlaceDS = null;
	PlaceModelClass mPlace = null;
	Long mId;
	String mLatitude;
	String mLongitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);

		// get the Intent that started this Activity
		Intent in = getIntent();

		// get the Bundle that stores the data of this Activity
		Bundle b = in.getExtras();

		mId = b.getLong("id");

		mPlaceDS = new PlaceListDataSource(this);
		mPlace = mPlaceDS.getDetail(mId);
		mLatitude = mPlace.getmLatitude();
		mLongitude = mPlace.getmLongitude();
		// Converting ageBirth to double type
		double latitude = Long.parseLong(mLatitude);
		double longitude = Long.parseLong(mLongitude);
		 try {
	            // Loading map
	            initilizeMap();
	         // create marker
	            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
	             
	            // adding marker
	            googleMap.addMarker(marker);
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}
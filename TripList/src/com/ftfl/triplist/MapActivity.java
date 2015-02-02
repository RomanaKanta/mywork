package com.ftfl.triplist;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ftfl.triplist.database.PlaceListDataSource;
import com.ftfl.triplist.modelclass.PlaceModelClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {

	// variable declaration
	GoogleMap mGoogleMap = null;
	PlaceListDataSource mPlaceDS = null;
	PlaceModelClass mPlace = null;
	Location mLocation = null;
	LatLng mCurrentPosition = null;
	LatLng mPlacePosition = null;
	Bundle mBundle = null;
	int mId = 0;
	double mLatitude = 0;
	double mLongitude = 0;
	String mName = "";
	String msLatitude = "";
	String msLongitude = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);

		mPlaceDS = new PlaceListDataSource(this);

		// get the Intent that started this Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of this Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mId = mBundle.getInt("id");
			if (mId > 0) {
				mPlace = mPlaceDS.getDetail(mId);
				mName = mPlace.getmName();
				msLatitude = mPlace.getmLatitude();
				msLongitude = mPlace.getmLongitude();

				// Converting Latitude , Longitude to double type
				mLatitude = Double.parseDouble(msLatitude);
				mLongitude = Double.parseDouble(msLongitude);
			}
		}
		try {
			// Loading map
			initilizeMap();
			mPlacePosition = new LatLng(mLatitude, mLongitude);
			// create marker
			MarkerOptions marker = new MarkerOptions().position(mPlacePosition)
					.title(mName);

			// adding marker
			mGoogleMap.addMarker(marker);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			boolean enabledGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// Check if enabled and if not send user to the GSP settings
			// Better solution would be to display a dialog and suggesting to
			// go to the settings
			if (!enabledGPS) {
				Toast.makeText(this, R.string.gps, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
			// Enabling MyLocation Layer of Google Map
			mGoogleMap.setMyLocationEnabled(true);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			mLocation = locationManager.getLastKnownLocation(provider);
			LocationListener locationListener = new LocationListener() {
				@Override
				public void onLocationChanged(Location arg0) {
					// redraw the marker when get location update.
					drawMarker(mLocation);
				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
				}
			};
			locationManager.requestLocationUpdates(provider, 1000 * 20 * 1, 10,
					(android.location.LocationListener) locationListener);

			// Showing the current location in Google Map
			mGoogleMap.moveCamera(CameraUpdateFactory
					.newLatLng(mCurrentPosition));

			// Zoom in the Google Map
			mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initilizeMap() {
		if (mGoogleMap == null) {
			mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null) {
				Toast.makeText(getApplicationContext(), R.string.map,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void drawMarker(Location location) {
		mGoogleMap.clear();
		mCurrentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());
		mGoogleMap.addMarker(new MarkerOptions()
				.position(mCurrentPosition)
				.snippet(
						" Latitude:" + location.getLatitude() + " Longitude:"
								+ location.getLongitude())
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.title("ME"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}
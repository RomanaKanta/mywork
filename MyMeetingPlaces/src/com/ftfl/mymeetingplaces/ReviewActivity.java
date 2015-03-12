package com.ftfl.mymeetingplaces;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.adapter.CustomeAdapter;
import com.ftfl.mymeetingplaces.database.PlaceDataSource;
import com.ftfl.mymeetingplaces.modelclass.ModelClass;

public class ReviewActivity extends ActionBarActivity implements LocationListener  {

	// Variable Declaration
	Button mHome = null;
	TextView mLatit = null;
	TextView mLongit = null;
	TextView mItemID = null;
	ListView mList = null;
	int mID = 0;
	double latitude = 0;
	double longitude = 0;
	PlaceDataSource mDataSource = null;
	CustomeAdapter mAdapter = null;
	ArrayList<ModelClass> mPlacelist = null;
	private ProgressDialog progressDialog = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);

		// definition - gives variable a reference
		mList = (ListView) findViewById(R.id.placeList);
		mLatit = (TextView) findViewById(R.id.latitudeTextView);
		mLongit = (TextView) findViewById(R.id.longitudeTextView);
		mHome = (Button) findViewById(R.id.home);
		mDataSource = new PlaceDataSource(this);

		// start the progress dialog
		progressDialog = ProgressDialog.show(this, "", "Loading...");
		new Thread() {
			public void run() {
				try {
					// Thread will sleep for 3 seconds
					sleep(3 * 1000);
				} catch (Exception e) {
					Log.e("tag", e.getMessage());
				}
				// dismiss the progress dialog
				progressDialog.dismiss();
			}
		}.start();// start thread

		/* Use the LocationManager to obtain GPS locations */
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, this);

		// Getting All place list from database
		mPlacelist = mDataSource.getPlaceList();

		// OnClick Activity of home button
		mHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getBaseContext(),
						HomeActivity.class);
				startActivity(iIntent);

				// Remove activity
				finish();
			}
		});
	}

	// current location and list activity
	@Override
	public void onLocationChanged(Location loc) {

		// get latitude longitude
		latitude = loc.getLatitude();
		longitude = loc.getLongitude();

		// set latitude longitude in view
		mLatit.setText(Double.toString(latitude));
		mLongit.setText(Double.toString(longitude));

		// call custom adapter
		mAdapter = new CustomeAdapter(this, latitude, longitude, mPlacelist);

		// set each row in list using custom adapter
		mList.setAdapter(mAdapter);

		// OnClick Activity of each row in list
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// view is used to get the view. id is declared in the
				// listrow, which is hidden/gone
				mItemID = (TextView) view.findViewById(R.id.tvID);
				String msID = mItemID.getText().toString();
				mID = Integer.parseInt(msID);

				// Creating Bundle object
				Bundle mBundle = new Bundle();

				// put id into bundle
				mBundle.putInt("id", mID);
				Intent mIntent = new Intent(getBaseContext(),
						ViewActivity.class);

				// Storing bundle object into intent
				mIntent.putExtras(mBundle);
				startActivity(mIntent);

				// Remove activity
				finish();
			}
		});
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
}

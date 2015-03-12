package com.ftfl.icare;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.CenterListAdapter;

public class CareCenterAcitvity extends Activity {

	// Variable Declaration
	ListView mCenterList = null;
	TextView mLatitudeTV = null;
	TextView mLongitudeTV = null;
	String mLatitude = "";
	String mLongitude = "";
	CenterListAdapter mCenterListAdapter = null;
	Double mLat;
	Double mLng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_care_center);

		// definition - gives variable a reference
		mCenterList = (ListView) findViewById(R.id.centerlist);

		// care center informations
		String[] mCenterName = { "Dhaka Medical College & Hospital",
			                   	 "Dhaka Shishu Hospital",
				                 "Institute of Child and Mother Health, Dhaka",
				                 "Ibn Sina Hospitals, Dhaka" };

		String[] mCenterAddress = { "Road No 9, Dhaka 1230, Bangladesh",
				                    "kamal Soroni Road, Dhaka, Bangladesh",
				                    "Road No 11, Dhaka, Bangladesh",
				                    "Road No. 15A, Dhaka, Bangladesh" };

		String[] mCenterPhone = { "11111111111", 
				                  "22222222222", 
				                  "33333333333",
			    	              "44444444444" };

		String[] mCenterLatitude = { "23.8582805", 
				                     "23.8030226", 
				                     "23.810332",
				                     "23.7515336" };

		String[] mCenterLongitude = { "90.4007752", 
				                      "90.36198020000006",
				                      "90.41251809999994", 
				                      "90.36907199999996" };

		ArrayList<String> nameList = new ArrayList<String>();
		nameList.addAll(Arrays.asList(mCenterName));

		ArrayList<String> addressList = new ArrayList<String>();
		addressList.addAll(Arrays.asList(mCenterAddress));

		ArrayList<String> phoneList = new ArrayList<String>();
		phoneList.addAll(Arrays.asList(mCenterPhone));

		ArrayList<String> LatiList = new ArrayList<String>();
		LatiList.addAll(Arrays.asList(mCenterLatitude));

		ArrayList<String> lngList = new ArrayList<String>();
		lngList.addAll(Arrays.asList(mCenterLongitude));

		mCenterListAdapter = new CenterListAdapter(this, nameList, addressList,
				phoneList, LatiList, lngList);

		mCenterList.setAdapter(mCenterListAdapter);

		mCenterList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// view is used to get the view.latitude and longitude
						// is declared in the
						// listrow, which is hidden/gone
						mLatitudeTV = (TextView) view
								.findViewById(R.id.centerlat);
						mLongitudeTV = (TextView) view
								.findViewById(R.id.centerlng);
						mLatitude = mLatitudeTV.getText().toString();						
						mLongitude = mLongitudeTV.getText().toString();
						
						 mLat = Double.parseDouble(mLatitude);
						 mLng = Double.parseDouble(mLongitude);

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								CareCenterAcitvity.this);

						alertDialog.setNeutralButton("Location in MAP",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Creating Bundle object
										Bundle mBundle = new Bundle();

										// put id into bundle
										mBundle.putDouble("lat", mLat);
										mBundle.putDouble("lng", mLng);
										
//										// put id into bundle
//										mBundle.putString("lat", mLatitude);
//										mBundle.putString("lng", mLongitude);
										Intent iIntent = new Intent(
												CareCenterAcitvity.this,
												MapActivity.class);

										// Storing bundle object into intent
										iIntent.putExtras(mBundle);
										startActivity(iIntent);

										dialog.cancel();
									}
								});

						// Showing Alert Message
						alertDialog.show();
					}
				});
	}
}

package com.ftfl.triplist;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.triplist.adapter.PlaceListAdapter;
import com.ftfl.triplist.database.PlaceListDataSource;
import com.ftfl.triplist.modelclass.PlaceModelClass;

public class HomeScreenActivity extends ActionBarActivity {

	// Variable Declaration
	ListView mUpcomingList = null;
	TextView mItemID = null;
	PlaceListDataSource mPlaceSource = null;
	ArrayList<PlaceModelClass> mPlaceName = null;
	PlaceListAdapter mAdapter = null;
	int mID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// definition - gives variable a reference
		mUpcomingList = (ListView) findViewById(R.id.placeList);
		mPlaceSource = new PlaceListDataSource(this);

		// Getting All place list from database
		mPlaceName = mPlaceSource.getPlaceList();

		// call custom adapter
		mAdapter = new PlaceListAdapter(this, mPlaceName);

		// set each row in list using custom adapter
		mUpcomingList.setAdapter(mAdapter);

		// onItemClick activity
		mUpcomingList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
								ViewDetailActivity.class);
						// Storing bundle object into intent
						mIntent.putExtras(mBundle);
						startActivity(mIntent);
					}
				});
	}

	// home screen menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_of_home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.add:
			// add new
			Intent mIntent = new Intent(getBaseContext(), PlaceAddingActivity.class);
			startActivity(mIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
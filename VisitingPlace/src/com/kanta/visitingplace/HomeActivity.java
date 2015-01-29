package com.kanta.visitingplace;

import java.util.ArrayList;
import com.kanta.visitingplace.adapter.PlaceListAdapter;
import com.kanta.visitingplace.database.PlaceListDataSource;
import com.kanta.visitingplace.modelclass.PlaceModelClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity {

	ListView upcomingList = null;
	PlaceModelClass place;
	PlaceListDataSource placeSource;
	ArrayList<PlaceModelClass> placeName;
	PlaceListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// upcoming visiting place list
		upcomingList = (ListView) findViewById(R.id.placeList);

		placeSource = new PlaceListDataSource(this);
		placeName = placeSource.getPlaceList();

		adapter = new PlaceListAdapter(this, placeName);
		upcomingList.setAdapter(adapter);

		upcomingList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Creating Bundle object
						Bundle b = new Bundle();

						// put id into bundle
						b.putLong("id", id);
						Intent i = new Intent(getBaseContext(),
								ViewActivity.class);
						// Storing bundle object into intent
						i.putExtras(b);
						startActivity(i);
					}
				});

	}

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
			Intent i = new Intent(getBaseContext(), CreateActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

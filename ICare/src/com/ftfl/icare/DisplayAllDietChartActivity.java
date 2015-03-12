package com.ftfl.icare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.DietListAdapter;
import com.ftfl.icare.database.DietDataSource;
import com.ftfl.icare.modelclass.DietModel;

public class DisplayAllDietChartActivity extends Activity {
	
	// Variable Declaration
	ListView mAllDietList = null;	
	TextView mDietIDTV = null;
	DietModel mDietModel = null;
	DietDataSource mDietDataSource = null;
	ArrayList<DietModel> mModel = null;
	DietListAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_diet_chart);
		
		// definition - gives variable a reference
		mAllDietList =(ListView)findViewById(R.id.alldiet);
		mDietDataSource = new DietDataSource(this);
		mModel = mDietDataSource.getAllDietList();

		mAdapter = new DietListAdapter(this, mModel);
		mAllDietList.setAdapter(mAdapter);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add) {
			add();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void add() {
		Intent iIntent = new Intent(DisplayAllDietChartActivity.this,
				DietChartCreationActivity.class);
		startActivity(iIntent);
		
		
	}   
}

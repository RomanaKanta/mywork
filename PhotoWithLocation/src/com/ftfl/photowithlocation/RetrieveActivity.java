package com.ftfl.photowithlocation;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.photowithlocation.database.ImageListDataSource;
import com.ftfl.photowithlocation.modelclass.PhotoModelClass;

public class RetrieveActivity extends ActionBarActivity {

	// Variable Declaration
	ListView mImageList = null;
	TextView mItemID = null;
	ImageListDataSource mPlaceSource = null;
	ArrayList<PhotoModelClass> mObject = null;
	MyAdapter mAdapter = null;
	int mID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrieve);

		// definition - gives variable a reference
		mImageList = (ListView) findViewById(R.id.placeList);
		mPlaceSource = new ImageListDataSource(this);

		// Getting All place list from database
		mObject = mPlaceSource.getPlaceList();

		// call custom adapter
		mAdapter = new MyAdapter(this, mObject);

		// set each row in list using custom adapter
		mImageList.setAdapter(mAdapter);

	}	
	}
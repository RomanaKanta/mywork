package com.ftfl.icare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ftfl.icare.adapter.NavDrawerListAdapter;
import com.ftfl.icare.fragment.AboutUs;
import com.ftfl.icare.fragment.GrowthInfo;
import com.ftfl.icare.fragment.HealthInfo;
import com.ftfl.icare.fragment.Home;
import com.ftfl.icare.fragment.ImportantNotes;
import com.ftfl.icare.fragment.NutritionInfo;
import com.ftfl.icare.fragment.VaccinInfo;
import com.ftfl.icare.modelclass.DrawerModel;

public class MainActivity extends Activity {

	// Variable Declaration
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;
	private TypedArray navMenuIcons;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;

	Fragment fragment;

	private ArrayList<DrawerModel> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawerlist);

		navDrawerItems = new ArrayList<DrawerModel>();

		// adding nav drawer items to array

		navDrawerItems.add(new DrawerModel(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		navDrawerItems.add(new DrawerModel(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			 // get the Intent that started this Activity
	        Intent iIntent = getIntent();
	 
	        Bundle mBundle = null;
	        // get the Bundle that stores the data of this Activity
	        mBundle = iIntent.getExtras();
	 
	        // getting data from bundle
     
      
      if(mBundle!=null){
    	  int position = mBundle.getInt("position");
    	  displayView(position);
    	  
      }else{
			// on first time display view for first nav item
			displayView(0);
      }
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_settings:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new Home();
			break;
		case 1:
			fragment = new HealthInfo();
			break;
		case 2:
			fragment = new VaccinInfo();
			break;
		case 3:
			fragment = new GrowthInfo();
			break;
		case 4:
			fragment = new NutritionInfo();
			break;

		case 5:
			fragment = new ImportantNotes();
			break;

		case 6:
			fragment = new AboutUs();
			break;

		default:
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			// setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
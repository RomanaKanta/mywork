package com.ftfl.icare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.adapter.DietListAdapter;
import com.ftfl.icare.database.DietDataSource;
import com.ftfl.icare.modelclass.DietModel;

public class DisplayDietChartActivity extends Activity {

	// Variable Declaration
	ListView mDietList = null;
	TextView mViewAll = null;
	TextView mDietIDTV = null;
	DietModel mDietModel = null;
	DietDataSource mDietDataSource = null;
	ArrayList<DietModel> mModel = null;
	DietListAdapter mAdapter = null;
	String msDietID = "";
	int mDietID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_diet_chart);

		// definition - gives variable a reference
		mDietList = (ListView) findViewById(R.id.todaydiet);
		mViewAll = (TextView) findViewById(R.id.viewall);
		mDietDataSource = new DietDataSource(this);
		mModel = mDietDataSource.getTodayDietList();

		mAdapter = new DietListAdapter(this, mModel);
		mDietList.setAdapter(mAdapter);

		// OnClick Activity of each row of list
		mDietList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mDietIDTV = (TextView) view.findViewById(R.id.tvDietID);
				msDietID = mDietIDTV.getText().toString();
				mDietID = Integer.parseInt(msDietID);

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						DisplayDietChartActivity.this);

				// Setting "Update" Button
				alertDialog.setPositiveButton(R.string.edit,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Creating Bundle object
								Bundle mBundle = new Bundle();

								// put id into bundle
								mBundle.putInt("id", mDietID);
								Intent iIntent = new Intent(
										DisplayDietChartActivity.this,
										DietChartCreationActivity.class);

								// Storing bundle object into intent
								iIntent.putExtras(mBundle);
								startActivity(iIntent);

								// Remove activity
								finish();
								dialog.cancel();
							}
						});
				// Setting "Delete" Button
				alertDialog.setNegativeButton(R.string.delete,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Write your code here to invoke NO event
								AlertDialog.Builder iDeleteAlert = new AlertDialog.Builder(
										DisplayDietChartActivity.this);

								// set dialog message
								iDeleteAlert
										.setMessage(R.string.deletealart)
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked item
														// will be deleted

														mDietDataSource
																.deleteDietData(mDietID);
														Intent iIntent = new Intent(
																DisplayDietChartActivity.this,
																DisplayDietChartActivity.class);
														startActivity(iIntent);

														Toast.makeText(
																DisplayDietChartActivity.this,
																R.string.datadelete,
																Toast.LENGTH_SHORT)
																.show();

														dialog.cancel();
														finish();
													}
												})
										.setNegativeButton(
												"No",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, just close
														// the dialog box and do
														// nothing
														dialog.cancel();
													}
												});

								// create alert dialog
								AlertDialog alertDialog = iDeleteAlert.create();

								// show it
								alertDialog.show();
							}
						});
				// Showing Alert Message
				alertDialog.show();
			}
		});

		// OnClick Activity of view
		mViewAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(DisplayDietChartActivity.this,
						DisplayAllDietChartActivity.class);
				startActivity(iIntent);
			}
		});
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
		Intent iIntent = new Intent(DisplayDietChartActivity.this,
				DietChartCreationActivity.class);
		startActivity(iIntent);
		
		
	}   
}

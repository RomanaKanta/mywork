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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.adapter.VaccinationListAdapter;
import com.ftfl.icare.database.VaccinationDataSource;
import com.ftfl.icare.modelclass.VaccinationModel;

public class DisplayVaccinChartActivity extends Activity {

	// Variable Declaration
	ListView mRemainingVacList = null;
	ListView mTakenVacList = null;
	TextView mVaccineIDTV = null;
	VaccinationModel mVaccinationModel = null;
	VaccinationDataSource mVaccinationDataSource = null;
	ArrayList<VaccinationModel> mRemainingModel = null;
	ArrayList<VaccinationModel> mTakenModel = null;
	VaccinationListAdapter mAdapter = null;
	VaccinationListAdapter mTakenAdapter = null;
	String msVaccineID = "";
	int mVaccineID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_vcc_chart);

		// definition - gives variable a reference
		mRemainingVacList = (ListView) findViewById(R.id.remainingvcclist);
		mTakenVacList = (ListView) findViewById(R.id.takenvcclist);

		mVaccinationDataSource = new VaccinationDataSource(this);

		mTakenModel = mVaccinationDataSource.getTakenVaccineList();
		mTakenAdapter = new VaccinationListAdapter(this, mTakenModel);
		mTakenVacList.setAdapter(mTakenAdapter);
		
		// OnClick Activity of each row
		mTakenVacList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mVaccineIDTV = (TextView) view
								.findViewById(R.id.tvVaccineID);
						msVaccineID = mVaccineIDTV.getText().toString();
						mVaccineID = Integer.parseInt(msVaccineID);

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								DisplayVaccinChartActivity.this);

						// Setting "Update" Button
						alertDialog.setPositiveButton(R.string.edit,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Creating Bundle object
										Bundle mBundle = new Bundle();

										// put id into bundle
										mBundle.putInt("id", mVaccineID);
										Intent iIntent = new Intent(
												DisplayVaccinChartActivity.this,
												VaccinChartCreationActivity.class);

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
										// Write your code here to invoke NO
										// event
										AlertDialog.Builder iDeleteAlert = new AlertDialog.Builder(
												DisplayVaccinChartActivity.this);

										// set dialog message
										iDeleteAlert
												.setMessage(
														R.string.deletealart)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																// if this
																// button is
																// clicked item
																// will be
																// deleted

																mVaccinationDataSource
																		.deleteVaccineData(mVaccineID);
																Intent iIntent = new Intent(
																		DisplayVaccinChartActivity.this,
																		DisplayVaccinChartActivity.class);
																startActivity(iIntent);

																Toast.makeText(
																		DisplayVaccinChartActivity.this,
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
																// if this
																// button is
																// clicked, just
																// close
																// the dialog
																// box and do
																// nothing
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialog = iDeleteAlert
												.create();

										// show it
										alertDialog.show();
									}
								});
						// Showing Alert Message
						alertDialog.show();
					}
				});


		mRemainingModel = mVaccinationDataSource.getRemainingVaccineList();
		mAdapter = new VaccinationListAdapter(this, mRemainingModel);
		mRemainingVacList.setAdapter(mAdapter);

		// OnClick Activity of each row
		mRemainingVacList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mVaccineIDTV = (TextView) view
								.findViewById(R.id.tvVaccineID);
						msVaccineID = mVaccineIDTV.getText().toString();
						mVaccineID = Integer.parseInt(msVaccineID);

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								DisplayVaccinChartActivity.this);

						// Setting "Update" Button
						alertDialog.setPositiveButton(R.string.edit,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// Creating Bundle object
										Bundle mBundle = new Bundle();

										// put id into bundle
										mBundle.putInt("id", mVaccineID);
										Intent iIntent = new Intent(
												DisplayVaccinChartActivity.this,
												VaccinChartCreationActivity.class);

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
										// Write your code here to invoke NO
										// event
										AlertDialog.Builder iDeleteAlert = new AlertDialog.Builder(
												DisplayVaccinChartActivity.this);

										// set dialog message
										iDeleteAlert
												.setMessage(
														R.string.deletealart)
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																// if this
																// button is
																// clicked item
																// will be
																// deleted

																mVaccinationDataSource
																		.deleteVaccineData(mVaccineID);
																Intent iIntent = new Intent(
																		DisplayVaccinChartActivity.this,
																		DisplayVaccinChartActivity.class);
																startActivity(iIntent);

																Toast.makeText(
																		DisplayVaccinChartActivity.this,
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
																// if this
																// button is
																// clicked, just
																// close
																// the dialog
																// box and do
																// nothing
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialog = iDeleteAlert
												.create();

										// show it
										alertDialog.show();
									}
								});
						// Showing Alert Message
						alertDialog.show();
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
		Intent iIntent = new Intent(DisplayVaccinChartActivity.this,
				VaccinChartCreationActivity.class);
		startActivity(iIntent);
		
	}   
}
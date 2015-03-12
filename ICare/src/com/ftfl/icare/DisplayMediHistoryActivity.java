package com.ftfl.icare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.HistoryListAdapter;
import com.ftfl.icare.database.HistoryDataSource;
import com.ftfl.icare.modelclass.HistoryModel;

public class DisplayMediHistoryActivity extends Activity {

	// Variable Declaration
	TextView mListID = null;
	TextView mPrescriptionIDTV = null;
	ListView mHistoryList = null;
	HistoryDataSource mHistoryDataSource = null;
	ArrayList<HistoryModel> mModel = null;
	HistoryListAdapter mAdapter = null;
	Integer mID = 0;
	int mPrescription = 0;
	String msID = "";
	String mPrescriptionID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_medical_history);

		// definition - gives variable a reference
		mHistoryList = (ListView) findViewById(R.id.medihistory);
		mHistoryDataSource = new HistoryDataSource(this);
		mModel = mHistoryDataSource.getHisoryList();

		mAdapter = new HistoryListAdapter(this, mModel);
		mHistoryList.setAdapter(mAdapter);

		// OnClick Activity of each row
		mHistoryList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// view is used to get the view. id is declared in the
						// listrow, which is hidden/gone
						mPrescriptionIDTV = (TextView) view
								.findViewById(R.id.tvHistoryID);
						mPrescriptionID = mPrescriptionIDTV.getText()
								.toString();
						mPrescription = Integer.parseInt(mPrescriptionID);
						// Creating Bundle object
						Bundle mBundle = new Bundle();

						// put id into bundle
						mBundle.putInt("id", mPrescription);
						Intent iIntent = new Intent(
								DisplayMediHistoryActivity.this,
								PrescriptionViewActivity.class);

						// Storing bundle object into intent
						iIntent.putExtras(mBundle);
						startActivity(iIntent);
						;
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
		Intent iIntent = new Intent(getBaseContext(),
				MediHistoryCreationActivity.class);
		startActivity(iIntent);
		
	}   
}

package com.ftfl.icare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.DocListAdapter;
import com.ftfl.icare.database.DoctorDateSource;
import com.ftfl.icare.fragment.ViewDoctorDetail;
import com.ftfl.icare.modelclass.DoctorModel;

public class DisplayDocProfileActivity extends Activity {

	// Variable Declaration
	ListView mDoctorList = null;
	TextView mItemID = null;
	DoctorModel mDoctorModel = null;
	DoctorDateSource mDoctorDateSource = null;
	ArrayList<DoctorModel> mModel = null;
	DocListAdapter mAdapter = null;
	int mID = 0;
	Editor prefsEditor;
	SharedPreferences prefs;
	String msID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_doc);

		// definition - gives variable a reference
		prefs = this.getSharedPreferences("ICARE", Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		mDoctorDateSource = new DoctorDateSource(this);
		mModel = mDoctorDateSource.getDoctorList();

		mDoctorList = (ListView) findViewById(R.id.doclist);
		mAdapter = new DocListAdapter(DisplayDocProfileActivity.this, mModel);
		mDoctorList.setAdapter(mAdapter);

		// OnClick Activity of each row of list
		mDoctorList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// view is used to get the view. id is declared in the
						// listrow, which is hidden/gone
						mItemID = (TextView) view.findViewById(R.id.tvUserID);
						msID = mItemID.getText().toString();

						prefsEditor.putString("doctorid", msID);
						prefsEditor.commit();
						// Create new fragment and transaction
						Fragment newFragment = new ViewDoctorDetail();
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();

						// Replace whatever is in the fragment_container view
						// with this fragment,
						// and add the transaction to the back stack
						transaction.replace(R.id.viewdetail, newFragment);
						transaction.addToBackStack(null);

						// Commit the transaction
						transaction.commit();
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
		Intent iIntent = new Intent(DisplayDocProfileActivity.this,
				DoctorProfileCreationActivity.class);
		startActivity(iIntent);
		
	}   
}

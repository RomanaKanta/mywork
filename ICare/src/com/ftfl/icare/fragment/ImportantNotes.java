package com.ftfl.icare.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.AddNoteActivity;
import com.ftfl.icare.MainActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.adapter.NoteAdapter;
import com.ftfl.icare.database.NoteDataSource;
import com.ftfl.icare.modelclass.NoteModel;

public class ImportantNotes extends Fragment {
	ImageView mAddNote = null;
	ListView mNoteList = null;
	TextView mNoteId = null;
	String msNoteId = "";
	int mID = 0;
	NoteModel mNoteModel = null;
	NoteDataSource mNoteDataSource = null;
	ArrayList<NoteModel> mModel = null;
	NoteAdapter mAdapter = null;
	Editor prefsEditor;
	SharedPreferences prefs;

	public ImportantNotes() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_important_note,
				container, false);
		prefs = getActivity().getSharedPreferences("ICARE",
				Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();
		mNoteDataSource = new NoteDataSource(getActivity());
		mModel = mNoteDataSource.getNoteList();

		mAddNote = (ImageView) rootView.findViewById(R.id.addnote);
		mNoteList = (ListView) rootView.findViewById(R.id.notelist);

		mAdapter = new NoteAdapter(getActivity(), mModel);
		mNoteList.setAdapter(mAdapter);

		mNoteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mNoteId = (TextView) view.findViewById(R.id.tvNoteID);
				msNoteId = mNoteId.getText().toString();
				mID = Integer.parseInt(msNoteId);

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						getActivity());

				// Setting "Update" Button
				alertDialog.setPositiveButton(R.string.edit,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								prefsEditor.putString("noteid", msNoteId);
								prefsEditor.commit();
								Intent iIntent = new Intent(
										getActivity(),
										AddNoteActivity.class);
								startActivity(iIntent);
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
										getActivity());

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

														mNoteDataSource
																.deleteNote(mID);
														   // Creating Bundle object
									                    Bundle mBundle = new Bundle();
									                     
									                    // Storing data into bundle
									                    mBundle.putInt("position",5);
									                    
									                    // Creating Intent object
									                    Intent iIntent = new Intent(getActivity(),
																MainActivity.class);

									                    // Storing bundle object into intent
									                    iIntent.putExtras(mBundle);	               						
														startActivity(iIntent);
														getActivity().finish();

														Toast.makeText(
																getActivity(),
																R.string.deletenote,
																Toast.LENGTH_SHORT)
																.show();
														dialog.cancel();

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

		mAddNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						AddNoteActivity.class);
				startActivity(iIntent);
			}
		});

		return rootView;
	}
}
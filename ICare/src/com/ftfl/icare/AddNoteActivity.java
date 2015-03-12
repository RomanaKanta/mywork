package com.ftfl.icare;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.database.NoteDataSource;
import com.ftfl.icare.fragment.ImportantNotes;
import com.ftfl.icare.modelclass.NoteModel;

public class AddNoteActivity extends Activity {

	// Variable Declaration
	Button mSaveNote = null;
	EditText mDateET = null;
	EditText mNoteET = null;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	int mNoteId = 0;
	String mDate = "";
	String mNote = "";
	String msNoteID = "";
	String check = "";
	NoteModel mNoteModel = null;
	NoteDataSource mNoteDataSource = null;
	Editor prefsEditor;
	SharedPreferences prefs;
	final Calendar mCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);

		// definition - gives variable a reference
		mNoteDataSource = new NoteDataSource(this);
		prefs = this.getSharedPreferences("ICARE", Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();
		msNoteID = prefs.getString("noteid", "");

		mDateET = (EditText) findViewById(R.id.notedateet);
		mNoteET = (EditText) findViewById(R.id.noteet);
		mSaveNote = (Button) findViewById(R.id.notesavebtn);

		// OnClick Activity of date field
		mDateET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						AddNoteActivity.this, mDateSetListener, mYear, mMonth,
						mDay);
				dialog.show();
			}
		});

		// check condition and set value for update
		if (!msNoteID.equals("")) {
			mNoteId = Integer.parseInt(msNoteID);
			mNoteModel = mNoteDataSource.getNoteDetail(mNoteId);
			mDateET.setText(mNoteModel.getmDate());
			mNoteET.setText(mNoteModel.getmNote());
		}

		// OnClick Activity of save button
		mSaveNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get values from view
				mDate = mDateET.getText().toString();
				mNote = mNoteET.getText().toString();

				// update or save activity with condition
				if (!msNoteID.equals("")) {
					if (mDateET.length() == 0 || mNoteET.length() == 0) {
						Toast.makeText(getBaseContext(), R.string.miss,
								Toast.LENGTH_SHORT).show();
					} else {
						mNoteModel = new NoteModel(mDate, mNote);
						long updated = mNoteDataSource.updateNote(mNoteId,
								mNoteModel);
						if (updated >= 0) {
							Toast.makeText(getBaseContext(),
									getString(R.string.update),
									Toast.LENGTH_LONG).show();
							prefsEditor.putString("noteid", "");
							prefsEditor.commit();
							Intent iIntent = new Intent(AddNoteActivity.this,
									ImportantNotes.class);
							startActivity(iIntent);
							finish();

						} else {
							Toast.makeText(getBaseContext(),
									getString(R.string.fail), Toast.LENGTH_LONG)
									.show();
						}
					}
				} else if (mDateET.length() == 0 || mNoteET.length() == 0) {
					Toast.makeText(getBaseContext(), R.string.miss,
							Toast.LENGTH_SHORT).show();
				} else {
					mNoteModel = new NoteModel(mDate, mNote);
					long inserted = mNoteDataSource.addNewNote(mNoteModel);
					if (inserted >= 0) {
						Toast.makeText(getBaseContext(),
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();
						prefsEditor.putString("noteid", "");
						prefsEditor.commit();
						
						   // Creating Bundle object
	                    Bundle mBundle = new Bundle();
	                     
	                    // Storing data into bundle
	                    mBundle.putInt("position",5);
	                    
	                    // Creating Intent object
	                    Intent iIntent = new Intent(AddNoteActivity.this,
								MainActivity.class);

	                    // Storing bundle object into intent
	                    iIntent.putExtras(mBundle);	               						
						startActivity(iIntent);
						finish();

					} else {
						Toast.makeText(getBaseContext(),
								getString(R.string.fail), Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});
	}

	// call DateSetListener for set selected date in edittext field
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mDateET.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};
}

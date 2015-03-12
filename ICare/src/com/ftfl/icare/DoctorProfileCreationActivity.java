package com.ftfl.icare;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.icare.database.DoctorDateSource;
import com.ftfl.icare.modelclass.DoctorModel;

public class DoctorProfileCreationActivity extends Activity {

	// Variable Declaration
	TextView mDocTitle = null;
	EditText mDoctorNameET = null;
	EditText mSpecialistET = null;
	EditText mMobileET = null;
	EditText mEmailET = null;
	EditText mAddressET = null;
	EditText mAppointDateET = null;
	EditText mAppointTimeET = null;
	Button mSavedoc = null;

	String mDoctorName = "";
	String mSpecialist = "";
	String mMobile = "";
	String mEmail = "";
	String mAddress = "";
	String mAppointDate = "";
	String mAppointTime = "";
	String msID = "";
	String check = "";

	Integer mSettingHour = 0;
	Integer mSettingMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	Integer mID = 0;
	final Calendar mCalendar = Calendar.getInstance();
	Editor prefsEditor;
	SharedPreferences prefs;
	DoctorModel mDoctorModel = null;
	DoctorDateSource mDoctorDateSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_doc_profile_create);

		// definition - gives variable a reference
		prefs = this.getSharedPreferences("ICARE", Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();
		msID = prefs.getString("updatedoctorid", "");

		mDoctorDateSource = new DoctorDateSource(this);

		mDocTitle = (TextView) findViewById(R.id.doctitle);
		mDoctorNameET = (EditText) findViewById(R.id.docnameet);
		mSpecialistET = (EditText) findViewById(R.id.docspecet);
		mMobileET = (EditText) findViewById(R.id.docphoneet);
		mEmailET = (EditText) findViewById(R.id.docemailet);
		mAddressET = (EditText) findViewById(R.id.docaddresset);
		mAppointDateET = (EditText) findViewById(R.id.appointdateet);
		mAppointTimeET = (EditText) findViewById(R.id.appointtimeet);
		mSavedoc = (Button) findViewById(R.id.docprosavebtn);

		// for update
		if (!msID.equals("")) {
			mID = Integer.parseInt(msID);
			mDoctorModel = mDoctorDateSource.getDoctorDetail(mID);
			mDocTitle.setText(R.string.editpro);
			mDoctorNameET.setText(mDoctorModel.getmDocName());
			mSpecialistET.setText(mDoctorModel.getmSpecialist());
			mMobileET.setText(mDoctorModel.getmPhone());
			mEmailET.setText(mDoctorModel.getmEmail());
			mAddressET.setText(mDoctorModel.getmAddress());
			mAppointDateET.setText(mDoctorModel.getmDate());
			mAppointTimeET.setText(mDoctorModel.getmTime());
		}

		// OnClick Activity of date field
		mAppointDateET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						DoctorProfileCreationActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();
			}
		});

		// OnClick Activity of time field
		mAppointTimeET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
				mMinute = mCalendar.get(Calendar.MINUTE);

				TimePickerDialog timeDialog = new TimePickerDialog(
						DoctorProfileCreationActivity.this, timePickerListener,
						mHour, mMinute, false);
				timeDialog.show();
			}
		});

		// OnClick Activity of save button
		mSavedoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetDataFromView();

				// for update
				if (!msID.equals("")) {
					if (isEmailValid(mEmail) == true) {
						if (mDoctorName.length() == 0
								|| mSpecialist.length() == 0
								|| mAddress.length() == 0) {
							// show toast when not correctly completed
							Toast.makeText(DoctorProfileCreationActivity.this,
									R.string.complete, Toast.LENGTH_SHORT)
									.show();
						} else if (mMobile.length() < 11) {
							Toast.makeText(DoctorProfileCreationActivity.this,
									R.string.correctnum, Toast.LENGTH_SHORT)
									.show();
						} else if (mAppointDate.length() == 0
								|| mAppointTime.length() == 0) {
							Toast.makeText(DoctorProfileCreationActivity.this,
									R.string.docappoint, Toast.LENGTH_SHORT)
									.show();
						} else {

							mDoctorModel = new DoctorModel(mDoctorName,
									mSpecialist, mMobile, mEmail, mAddress,
									mAppointDate, mAppointTime);

							long Updated = mDoctorDateSource.updateDoctorData(
									mID, mDoctorModel);
							if (Updated >= 0) {
								Toast.makeText(
										DoctorProfileCreationActivity.this,
										getString(R.string.update),
										Toast.LENGTH_LONG).show();
								prefsEditor.putString("updatedoctorid", "");
								prefsEditor.commit();
								Intent iIntent = new Intent(
										DoctorProfileCreationActivity.this,
										DisplayDocProfileActivity.class);
								startActivity(iIntent);
								finish();

							} else {
								Toast.makeText(
										DoctorProfileCreationActivity.this,
										getString(R.string.fail),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				} else if (isEmailValid(mEmail) == true) {
					if (mDoctorName.length() == 0 || mSpecialist.length() == 0
							|| mAddress.length() == 0) {
						// show toast when not correctly completed
						Toast.makeText(DoctorProfileCreationActivity.this,
								R.string.complete, Toast.LENGTH_SHORT).show();
					} else if (mMobile.length() < 11) {
						Toast.makeText(DoctorProfileCreationActivity.this,
								R.string.correctnum, Toast.LENGTH_SHORT).show();
					} else if (mAppointDate.length() == 0
							|| mAppointTime.length() == 0) {
						Toast.makeText(DoctorProfileCreationActivity.this,
								R.string.docappoint, Toast.LENGTH_SHORT).show();
					} else {

						mDoctorModel = new DoctorModel(mDoctorName,
								mSpecialist, mMobile, mEmail, mAddress,
								mAppointDate, mAppointTime);

						long inserted = mDoctorDateSource
								.addNewDoctor(mDoctorModel);
						if (inserted >= 0) {
							Toast.makeText(DoctorProfileCreationActivity.this,
									getString(R.string.insert),
									Toast.LENGTH_LONG).show();
							Intent iIntent = new Intent(
									DoctorProfileCreationActivity.this,
									DisplayDocProfileActivity.class);
							startActivity(iIntent);
							finish();

						} else {
							Toast.makeText(DoctorProfileCreationActivity.this,
									getString(R.string.fail), Toast.LENGTH_LONG)
									.show();
						}
					}
				}
			}
		});
	}

	public void GetDataFromView() {
		mDoctorName = mDoctorNameET.getText().toString();
		mSpecialist = mSpecialistET.getText().toString();
		mMobile = mMobileET.getText().toString();
		mEmail = mEmailET.getText().toString();
		mAddress = mAddressET.getText().toString();
		mAppointDate = mAppointDateET.getText().toString();
		mAppointTime = mAppointTimeET.getText().toString();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mAppointDateET.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	// timepicker in dialogbox

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mSettingHour = hourOfDay;
			mSettingMinute = minute;
			int hour = 0;
			String st = "";
			if (hourOfDay > 12) {
				hour = hourOfDay - 12;
				st = "PM";
			}

			else if (hourOfDay == 12) {
				hour = hourOfDay;
				st = "PM";
			}

			else if (hourOfDay == 0) {
				hour = hourOfDay + 12;
				st = "AM";
			} else {
				hour = hourOfDay;
				st = "AM";
			}
			mAppointTimeET.setText(new StringBuilder().append(hour)
					.append(" : ").append(minute).append(" ").append(st));
		}
	};

	// email validation
	public boolean isEmailValid(String email) {
		Pattern pattern;
		Matcher matcher;

		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else {

			Toast.makeText(DoctorProfileCreationActivity.this,
					R.string.complete, Toast.LENGTH_SHORT).show();

			Toast.makeText(DoctorProfileCreationActivity.this,
					R.string.invalide, Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}

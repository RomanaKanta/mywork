package com.ftfl.icare;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.icare.database.DietDataSource;
import com.ftfl.icare.modelclass.DietModel;

public class DietChartCreationActivity extends Activity {

	// Variable Declaration
	EditText mFeastET = null;
	EditText mMenuET = null;
	EditText mDietDateET = null;
	EditText mDietTimeET = null;
	EditText mSummeryET = null;
	Button mSaveDietChart = null;

	String mFeast = "";
	String mMenu = "";
	String mDietDate = "";
	String mDietTime = "";
	String mAlarm = "";
	String mSummery = "";

	Integer mHourSetting = 0;
	Integer mMinuteSetting = 0;
	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	int mDietID = 0;

	final Calendar mCalendar = Calendar.getInstance();
	CheckBox mAlarmSetting = null;
	DietModel mDietModel = null;
	DietDataSource mDietDataSource = null;
	Bundle mBundle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diet_chart_create);
		mDietDataSource = new DietDataSource(this);

		// definition - gives variable a reference
		mFeastET = (EditText) findViewById(R.id.feastet);
		mMenuET = (EditText) findViewById(R.id.menuet);
		mDietDateET = (EditText) findViewById(R.id.dateet);
		mDietTimeET = (EditText) findViewById(R.id.timeet);
		mSummeryET = (EditText) findViewById(R.id.summeryet);
		mAlarmSetting = (CheckBox) findViewById(R.id.dietalarm);
		mSaveDietChart = (Button) findViewById(R.id.dietchartbtn);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mDietID = mBundle.getInt("id");

			if (mDietID > 0) {
				mDietModel = mDietDataSource.getDietDetail(mDietID);

				mFeastET.setText(mDietModel.getmFeast());
				mMenuET.setText(mDietModel.getmMenu());
				mDietDateET.setText(mDietModel.getmDate());
				mDietTimeET.setText(mDietModel.getmTime());
			}
		}

		// OnClick Activity of date field
		mDietDateET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						DietChartCreationActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();
			}
		});

		// OnClick Activity of time field
		mDietTimeET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
				mMinute = mCalendar.get(Calendar.MINUTE);

				TimePickerDialog timeDialog = new TimePickerDialog(
						DietChartCreationActivity.this, timePickerListener,
						mHour, mMinute, false);
				timeDialog.show();
			}
		});

		// OnClick Activity of save field
		mSaveDietChart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetDataFromView();
				if (mAlarmSetting.isChecked()) {
					mAlarm = "true";
					Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
					alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, mHourSetting);
					alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES,
							mMinuteSetting);
					alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(alarmIntent);
				} else {
					mAlarm = "false";
				}

				if (mBundle != null && mDietID > 0) {
					if (mFeast.length() == 0 || mMenu.length() == 0
							|| mDietDate.length() == 0
							|| mDietTime.length() == 0) {
						Toast.makeText(DietChartCreationActivity.this,
								R.string.complete, Toast.LENGTH_SHORT).show();
					} else {
						mDietModel = new DietModel(mFeast, mMenu, mDietTime,
								mDietDate, mAlarm);
						long updated = mDietDataSource.updateDietData(mDietID,
								mDietModel);
						if (updated >= 0) {
							Toast.makeText(DietChartCreationActivity.this,
									getString(R.string.update),
									Toast.LENGTH_LONG).show();
							Intent iIntent = new Intent(
									DietChartCreationActivity.this,
									DisplayDietChartActivity.class);
							startActivity(iIntent);

							finish();
						}
					}
				} else if (mFeast.length() == 0 || mMenu.length() == 0
						|| mDietDate.length() == 0 || mDietTime.length() == 0) {
					Toast.makeText(DietChartCreationActivity.this,
							R.string.complete, Toast.LENGTH_SHORT).show();
				} else {
					mDietModel = new DietModel(mFeast, mMenu, mDietTime,
							mDietDate, mAlarm);
					long inserted = mDietDataSource.addNewDiet(mDietModel);
					if (inserted >= 0) {
						Toast.makeText(DietChartCreationActivity.this,
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();
						Intent iIntent = new Intent(
								DietChartCreationActivity.this,
								DisplayDietChartActivity.class);
						startActivity(iIntent);

						finish();
					}
				}
			}
		});
	}

	public void GetDataFromView() {
		mFeast = mFeastET.getText().toString();
		mMenu = mMenuET.getText().toString();
		mDietDate = mDietDateET.getText().toString();
		mDietTime = mDietTimeET.getText().toString();
		// mSummery = mSummeryET.getText().toString();
	}

	// call DateSetListener for set selected date in edittext field
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mDietDateET.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	// timepicker in dialogbox
	// call TimeSetListener for set selected time in edittext field
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHourSetting = hourOfDay;
			mMinuteSetting = minute;
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
			mDietTimeET.setText(new StringBuilder().append(hour).append(" : ")
					.append(minute).append(" ").append(st));
		}
	};
}
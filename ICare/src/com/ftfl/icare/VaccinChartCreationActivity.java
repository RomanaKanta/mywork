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

import com.ftfl.icare.database.VaccinationDataSource;
import com.ftfl.icare.modelclass.VaccinationModel;

public class VaccinChartCreationActivity extends Activity {

	// Variable Declaration
	Button mSaveVaccine = null;
	EditText mVaccineNameET = null;
	EditText mVaccineDateET = null;
	EditText mVaccineTimeET = null;
	EditText mVaccineReasonET = null;
	EditText mVaccineAddressET = null;

	String mVaccineName = "";
	String mVaccineDate = "";
	String mVaccineTime = "";
	String mVaccineReason = "";
	String mVaccineAddress = "";
	String mAlarm = null;
	Bundle mBundle = null;
	int mVaccineID = 0;

	Integer mSettingHour = 0;
	Integer mSettingMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	Integer mAlarmHour = 0;
	Integer mAlamrMinute = 0;
	final Calendar mCalendar = Calendar.getInstance();
	CheckBox mVaccineAlarm = null;
	VaccinationModel mVaccinationModel = null;
	VaccinationDataSource mVaccinationDataSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_vcci_chart);

		mVaccinationDataSource = new VaccinationDataSource(this);

		// definition - gives variable a reference
		mVaccineNameET = (EditText) findViewById(R.id.vccnametet);
		mVaccineDateET = (EditText) findViewById(R.id.vccdateet);
		mVaccineTimeET = (EditText) findViewById(R.id.vcctimeet);
		mVaccineReasonET = (EditText) findViewById(R.id.vccreasonet);
		mVaccineAddressET = (EditText) findViewById(R.id.vccaddresset);
		mSaveVaccine = (Button) findViewById(R.id.addchartbtn);
		mVaccineAlarm = (CheckBox) findViewById(R.id.vaccinealarm);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mVaccineID = mBundle.getInt("id");

			if (mVaccineID > 0) {
				mVaccinationModel = mVaccinationDataSource
						.getVaccineDetail(mVaccineID);

				mVaccineNameET.setText(mVaccinationModel.getmVacName());
				mVaccineDateET.setText(mVaccinationModel.getmVacDate());
				mVaccineTimeET.setText(mVaccinationModel.getmVacTime());
				mVaccineReasonET.setText(mVaccinationModel.getmVacReason());
				mVaccineAddressET.setText(mVaccinationModel.getmVacAddress());
			}
		}

		// OnClick Activity of date field
		mVaccineDateET.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						VaccinChartCreationActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();
			}
		});

		// OnClick Activity of time field
		mVaccineTimeET.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
				mMinute = mCalendar.get(Calendar.MINUTE);

				TimePickerDialog timeDialog = new TimePickerDialog(
						VaccinChartCreationActivity.this, timePickerListener,
						mHour, mMinute, false);
				timeDialog.show();
			}
		});

		// OnClick Activity of save button
		mSaveVaccine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetDataFromView();
				if (mVaccineAlarm.isChecked()) {
					mAlarm = "1";
					Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
					alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, mAlarmHour);
					alarmIntent
							.putExtra(AlarmClock.EXTRA_MINUTES, mAlamrMinute);
					alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
					alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(alarmIntent);
				} else {
					mAlarm = "0";
				}

				if (mBundle != null && mVaccineID > 0) {
					if (mVaccineName.length() == 0
							|| mVaccineDate.length() == 0
							|| mVaccineTime.length() == 0
							|| mVaccineAddress.length() == 0
							|| mVaccineReason.length() == 0) {
						Toast.makeText(VaccinChartCreationActivity.this,
								R.string.complete, Toast.LENGTH_SHORT).show();
					} else {
						mVaccinationModel = new VaccinationModel(mVaccineName,
								mVaccineTime, mVaccineDate, mVaccineReason,
								mVaccineAddress, mAlarm);
						long updated = mVaccinationDataSource
								.updateVaccineData(mVaccineID,
										mVaccinationModel);
						if (updated >= 0) {
							Toast.makeText(VaccinChartCreationActivity.this,
									getString(R.string.update),
									Toast.LENGTH_LONG).show();
							Intent iIntent = new Intent(
									VaccinChartCreationActivity.this,
									DisplayVaccinChartActivity.class);
							startActivity(iIntent);

							finish();
						}
					}
				} else if (mVaccineName.length() == 0
						|| mVaccineDate.length() == 0
						|| mVaccineTime.length() == 0
						|| mVaccineAddress.length() == 0
						|| mVaccineReason.length() == 0) {
					Toast.makeText(VaccinChartCreationActivity.this,
							R.string.complete, Toast.LENGTH_SHORT).show();
				} else {
					mVaccinationModel = new VaccinationModel(mVaccineName,
							mVaccineTime, mVaccineDate, mVaccineReason,
							mVaccineAddress, mAlarm);
					long inserted = mVaccinationDataSource
							.addNewVaccine(mVaccinationModel);
					if (inserted >= 0) {
						Toast.makeText(VaccinChartCreationActivity.this,
								getString(R.string.insert), Toast.LENGTH_LONG)
								.show();
						Intent iIntent = new Intent(
								VaccinChartCreationActivity.this,
								DisplayVaccinChartActivity.class);
						startActivity(iIntent);

						finish();
					}
				}
			}
		});
	}

	public void GetDataFromView() {
		mVaccineName = mVaccineNameET.getText().toString();
		mVaccineDate = mVaccineDateET.getText().toString();
		mVaccineTime = mVaccineTimeET.getText().toString();
		mVaccineReason = mVaccineReasonET.getText().toString();
		mVaccineAddress = mVaccineAddressET.getText().toString();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
		
				mVaccineDateET.setText(new StringBuilder()
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
			mVaccineTimeET.setText(new StringBuilder().append(hour)
					.append(" : ").append(minute).append(" ").append(st));
		}
	};
}
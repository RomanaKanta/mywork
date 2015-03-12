package com.ftfl.icare.database;

import java.util.ArrayList;

import com.ftfl.icare.modelclass.DoctorModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DoctorDateSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;

	public DoctorDateSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new
	public long addNewDoctor(DoctorModel doctor) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_DOCTOR_NAME, doctor.getmDocName());
		values.put(DataBaseHelper.KEY_SPECIALIST, doctor.getmSpecialist());
		values.put(DataBaseHelper.KEY_PHONE, doctor.getmPhone());
		values.put(DataBaseHelper.KEY_EMAIL, doctor.getmEmail());
		values.put(DataBaseHelper.KEY_ADDRESS, doctor.getmAddress());
		values.put(DataBaseHelper.KEY_DATE, doctor.getmDate());
		values.put(DataBaseHelper.KEY_TIME, doctor.getmTime());
	
		long inserted = db.insert(DataBaseHelper.DOCTOR_TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteDoctorData(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.DOCTOR_TABLE_NAME, DataBaseHelper.KEY_DOCTOR_ID + "="
					+ eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateDoctorData(Integer id, DoctorModel doctor) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_DOCTOR_NAME, doctor.getmDocName());
		values.put(DataBaseHelper.KEY_SPECIALIST, doctor.getmSpecialist());
		values.put(DataBaseHelper.KEY_PHONE, doctor.getmPhone());
		values.put(DataBaseHelper.KEY_EMAIL, doctor.getmEmail());
		values.put(DataBaseHelper.KEY_ADDRESS, doctor.getmAddress());
		values.put(DataBaseHelper.KEY_DATE, doctor.getmDate());
		values.put(DataBaseHelper.KEY_TIME, doctor.getmTime());

		long updated = 0;
		try {
			updated = db.update(DataBaseHelper.DOCTOR_TABLE_NAME, values,
					DataBaseHelper.KEY_DOCTOR_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All doctor list
	public ArrayList<DoctorModel> getDoctorList() {
		ArrayList<DoctorModel> doctor_list = new ArrayList<DoctorModel>();
		open();
		Cursor cursor = db.query(DataBaseHelper.DOCTOR_TABLE_NAME, null, null, null,
				null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_DOCTOR_ID));
				String docname = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DOCTOR_NAME));
				String specialist = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_SPECIALIST));
				String phone = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_PHONE));
				String email = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_EMAIL));
				String address = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_ADDRESS));
				String date = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DATE));
				String time = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_TIME));

				DoctorModel doctor = new DoctorModel(id, docname, specialist,
						phone, email, address, date,
						time);
				doctor_list.add(doctor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return doctor list
		return doctor_list;
	}

	// Getting All doctor detail
	public DoctorModel getDoctorDetail(int id) {
		DoctorModel doctor_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DataBaseHelper.DOCTOR_TABLE_NAME
				+ " WHERE " + DataBaseHelper.KEY_DOCTOR_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String docname = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DOCTOR_NAME));
		String specialist = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_SPECIALIST));
		String phone = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PHONE));
		String email = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_EMAIL));
		String address = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_ADDRESS));
		String date = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DATE));
		String time = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_TIME));

		doctor_detail = new DoctorModel(id, docname, specialist,
				phone, email, address, date,
				time);


		cursor.moveToNext();

		cursor.close();
		db.close();

		// return doctor detail
		return doctor_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.DOCTOR_TABLE_NAME, new String[] {
				DataBaseHelper.KEY_DOCTOR_NAME, DataBaseHelper.KEY_SPECIALIST,
				DataBaseHelper.KEY_PHONE, DataBaseHelper.KEY_EMAIL,
				DataBaseHelper.KEY_ADDRESS, DataBaseHelper.KEY_DATE
				, DataBaseHelper.KEY_TIME,}, null,
				null, null, null, null);

		if (cursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}

}

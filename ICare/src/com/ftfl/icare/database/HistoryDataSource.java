package com.ftfl.icare.database;

import java.util.ArrayList;

import com.ftfl.icare.modelclass.HistoryModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HistoryDataSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;

	public HistoryDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new prescription
	public long addNewHistory(HistoryModel prescription) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_DOCTOR, prescription.getmDoctorName());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_DATE,
				prescription.getmPrescriptionDate());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_REASON,
				prescription.getmPrescriptionReason());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_SUGGESTION,
				prescription.getmDocSuggestion());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_PHOTO,
				prescription.getmPrescription());

		long inserted = db.insert(DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME,
				null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteHistoryData(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME,
					DataBaseHelper.KEY_PRESCRIPTION_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateVaccineData(Integer id, HistoryModel prescription) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_DOCTOR, prescription.getmDoctorName());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_DATE,
				prescription.getmPrescriptionDate());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_REASON,
				prescription.getmPrescriptionReason());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_SUGGESTION,
				prescription.getmDocSuggestion());
		values.put(DataBaseHelper.KEY_PRESCRIPTION_PHOTO,
				prescription.getmPrescription());

		long updated = 0;
		try {
			updated = db
					.update(DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME, values,
							DataBaseHelper.KEY_PRESCRIPTION_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All prescription list
	public ArrayList<HistoryModel> getHisoryList() {
		ArrayList<HistoryModel> prescription_list = new ArrayList<HistoryModel>();
		open();
		Cursor cursor = db.query(DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME,
				null, null, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_ID));
				String docname = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DOCTOR));
				String date = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_DATE));
				String reason = cursor
						.getString(cursor
								.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_REASON));
				String suggestion = cursor
						.getString(cursor
								.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_SUGGESTION));
				String photo = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_PHOTO));

				HistoryModel prescription = new HistoryModel(id, docname, date,
						reason, suggestion, photo);
				prescription_list.add(prescription);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return prescription list
		return prescription_list;
	}

	// Getting All prescription detail
	public HistoryModel getHistoryDetail(int id) {
		HistoryModel prescription_detail;
		open();

		String selectQuery = "SELECT  * FROM "
				+ DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME + " WHERE "
				+ DataBaseHelper.KEY_PRESCRIPTION_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String docname = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DOCTOR));
		String date = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_DATE));
		String reason = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_REASON));
		String suggestion = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_SUGGESTION));
		String photo = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PRESCRIPTION_PHOTO));

		prescription_detail = new HistoryModel(id, docname, date, reason,
				suggestion, photo);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return prescription detail
		return prescription_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.MEDICAL_HISTORY_TABLE_NAME,
				new String[] { DataBaseHelper.KEY_PRESCRIPTION_ID,
						DataBaseHelper.KEY_DOCTOR,
						DataBaseHelper.KEY_PRESCRIPTION_DATE,
						DataBaseHelper.KEY_PRESCRIPTION_REASON,
						DataBaseHelper.KEY_PRESCRIPTION_SUGGESTION,
						DataBaseHelper.KEY_PRESCRIPTION_PHOTO, }, null, null,
				null, null, null);

		if (cursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}
}

package com.ftfl.icare.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ftfl.icare.modelclass.DietModel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DietDataSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;
	public String mCurrentDate = "";

	public DietDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	@SuppressLint("SimpleDateFormat")
	public void getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);
	}

	// Adding new diet
	public long addNewDiet(DietModel diet) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_FEAST, diet.getmFeast());
		values.put(DataBaseHelper.KEY_MENU, diet.getmMenu());
		values.put(DataBaseHelper.KEY_DIET_DATE, diet.getmDate());
		values.put(DataBaseHelper.KEY_DIET_TIME, diet.getmTime());
		values.put(DataBaseHelper.KEY_ALARM, diet.getmAlarm());

		long inserted = db.insert(DataBaseHelper.DIET_TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteDietData(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.DIET_TABLE_NAME,
					DataBaseHelper.KEY_DIET_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateDietData(Integer id, DietModel diet) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_FEAST, diet.getmFeast());
		values.put(DataBaseHelper.KEY_MENU, diet.getmMenu());
		values.put(DataBaseHelper.KEY_DIET_DATE, diet.getmDate());
		values.put(DataBaseHelper.KEY_DIET_TIME, diet.getmTime());
		values.put(DataBaseHelper.KEY_ALARM, diet.getmAlarm());

		long updated = 0;
		try {
			updated = db.update(DataBaseHelper.DIET_TABLE_NAME, values,
					DataBaseHelper.KEY_DIET_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All diet list
	public ArrayList<DietModel> getTodayDietList() {
		ArrayList<DietModel> diet_list = new ArrayList<DietModel>();
		open();
		getCurrentDate();

		Cursor cursor = db.query(DataBaseHelper.DIET_TABLE_NAME, new String[] {
				DataBaseHelper.KEY_DIET_ID,
				DataBaseHelper.KEY_FEAST,
				DataBaseHelper.KEY_MENU,
				DataBaseHelper.KEY_DIET_DATE,
				DataBaseHelper.KEY_DIET_TIME,
				DataBaseHelper.KEY_ALARM,

		},
		DataBaseHelper.KEY_DIET_DATE + "= '" + mCurrentDate + "' ",
				null, null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_DIET_ID));
				String feast = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_FEAST));
				String menu = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_MENU));
				String date = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DIET_DATE));
				String time = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DIET_TIME));
				String alarm = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_ALARM));

				DietModel diet = new DietModel(id, feast, menu, time, date,
						alarm);
				diet_list.add(diet);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return diet list
		return diet_list;
	}
	
	// Getting All diet list
		public ArrayList<DietModel> getAllDietList() {
			ArrayList<DietModel> all_diet_list = new ArrayList<DietModel>();
			open();

			Cursor cursor = db.query(DataBaseHelper.DIET_TABLE_NAME, new String[] {
					DataBaseHelper.KEY_DIET_ID,
					DataBaseHelper.KEY_FEAST,
					DataBaseHelper.KEY_MENU,
					DataBaseHelper.KEY_DIET_DATE,
					DataBaseHelper.KEY_DIET_TIME,
					DataBaseHelper.KEY_ALARM,

			},null, null, null, null, null);

			// looping through all rows and adding to list
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					int id = cursor.getInt(cursor
							.getColumnIndex(DataBaseHelper.KEY_DIET_ID));
					String feast = cursor.getString(cursor
							.getColumnIndex(DataBaseHelper.KEY_FEAST));
					String menu = cursor.getString(cursor
							.getColumnIndex(DataBaseHelper.KEY_MENU));
					String date = cursor.getString(cursor
							.getColumnIndex(DataBaseHelper.KEY_DIET_DATE));
					String time = cursor.getString(cursor
							.getColumnIndex(DataBaseHelper.KEY_DIET_TIME));
					String alarm = cursor.getString(cursor
							.getColumnIndex(DataBaseHelper.KEY_ALARM));

					DietModel diet = new DietModel(id, feast, menu, time, date,
							alarm);
					all_diet_list.add(diet);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();

			// return diet list
			return all_diet_list;
		}

	// Getting All diet detail
	public DietModel getDietDetail(int id) {
		DietModel diet_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DataBaseHelper.DIET_TABLE_NAME
				+ " WHERE " + DataBaseHelper.KEY_DIET_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String feast = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_FEAST));
		String menu = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_MENU));
		String date = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DIET_DATE));
		String time = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DIET_TIME));
		String alarm = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_ALARM));
		diet_detail = new DietModel(id, feast, menu, time, date, alarm);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return diet detail
		return diet_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.DIET_TABLE_NAME, new String[] {
				DataBaseHelper.KEY_DIET_ID, DataBaseHelper.KEY_FEAST,
				DataBaseHelper.KEY_MENU, DataBaseHelper.KEY_DIET_DATE,
				DataBaseHelper.KEY_DIET_TIME, DataBaseHelper.KEY_ALARM, },
				null, null, null, null, null);

		if (cursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}

}

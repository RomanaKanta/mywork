package com.ftfl.mymeetingplaces.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.mymeetingplaces.modelclass.*;

public class PlaceDataSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;

	public PlaceDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new
	public long addNew(ModelClass place) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_NAME, place.getmName());
		values.put(DataBaseHelper.KEY_EMAIL, place.getmEmail());
		values.put(DataBaseHelper.KEY_MOBILE, place.getmMobile());
		values.put(DataBaseHelper.KEY_LATITUDE, place.getmLatitude());
		values.put(DataBaseHelper.KEY_LOGITUDE, place.getmLongitude());
		values.put(DataBaseHelper.KEY_DESCRIPTION, place.getmDiscription());
		values.put(DataBaseHelper.KEY_FILENAME, place.getmFileName());
		values.put(DataBaseHelper.KEY_DATE, place.getmDate());
		values.put(DataBaseHelper.KEY_TIME, place.getmTime());

		long inserted = db.insert(DataBaseHelper.TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteData(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.KEY_ID + "="
					+ eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateData(Integer id, ModelClass place) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_NAME, place.getmName());
		values.put(DataBaseHelper.KEY_EMAIL, place.getmEmail());
		values.put(DataBaseHelper.KEY_MOBILE, place.getmMobile());
		values.put(DataBaseHelper.KEY_LATITUDE, place.getmLatitude());
		values.put(DataBaseHelper.KEY_LOGITUDE, place.getmLongitude());
		values.put(DataBaseHelper.KEY_DESCRIPTION, place.getmDiscription());
		values.put(DataBaseHelper.KEY_FILENAME, place.getmFileName());
		values.put(DataBaseHelper.KEY_DATE, place.getmDate());
		values.put(DataBaseHelper.KEY_TIME, place.getmTime());

		long updated = 0;
		try {
			updated = db.update(DataBaseHelper.TABLE_NAME, values,
					DataBaseHelper.KEY_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All place list
	public ArrayList<ModelClass> getPlaceList() {
		ArrayList<ModelClass> place_list = new ArrayList<ModelClass>();
		open();
		Cursor cursor = db.query(DataBaseHelper.TABLE_NAME, null, null, null,
				null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_NAME));
				String email = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_EMAIL));
				String mobile = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_MOBILE));
				String latitude = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_LATITUDE));
				String longitude = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_LOGITUDE));
				String description = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DESCRIPTION));
				String filename = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_FILENAME));
				String date = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_DATE));
				String time = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_TIME));

				ModelClass place = new ModelClass(id, name, email, mobile,
						latitude, longitude, description, filename, date, time);
				place_list.add(place);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return place list
		return place_list;
	}

	// Getting All place detail
	public ModelClass getDetail(int id) {
		ModelClass place_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_NAME
				+ " WHERE " + DataBaseHelper.KEY_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_NAME));
		String email = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_EMAIL));
		String mobile = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_MOBILE));
		String latitude = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_LATITUDE));
		String longitude = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_LOGITUDE));
		String description = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DESCRIPTION));
		String filename = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_FILENAME));
		String date = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_DATE));
		String time = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_TIME));

		place_detail = new ModelClass(id, name, email, mobile, latitude,
				longitude, description, filename, date, time);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return place detail
		return place_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.TABLE_NAME, new String[] {
				DataBaseHelper.KEY_ID, DataBaseHelper.KEY_NAME,
				DataBaseHelper.KEY_EMAIL, DataBaseHelper.KEY_MOBILE,
				DataBaseHelper.KEY_LATITUDE, DataBaseHelper.KEY_LOGITUDE,
				DataBaseHelper.KEY_DESCRIPTION, DataBaseHelper.KEY_FILENAME,
				DataBaseHelper.KEY_DATE, DataBaseHelper.KEY_TIME, }, null,
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

package com.ftfl.triplist.database;

import java.util.ArrayList;

import com.ftfl.triplist.modelclass.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PlaceListDataSource {
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public PlaceListDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new
	public long addNewPlace(PlaceModelClass place) {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_NAME, place.getmName());
		values.put(DBHelper.KEY_PURPOSE, place.getmPurpose());
		values.put(DBHelper.KEY_ADDRESS, place.getmAddress());
		values.put(DBHelper.KEY_DISTRICT, place.getmDistrict());
		values.put(DBHelper.KEY_LATITUDE, place.getmLatitude());
		values.put(DBHelper.KEY_LOGITUDE, place.getmLongitude());
		values.put(DBHelper.KEY_START_DATE, place.getmStartDate());
		values.put(DBHelper.KEY_END_DATE, place.getmEndDate());
		values.put(DBHelper.KEY_NOTE, place.getmNote());
		values.put(DBHelper.KEY_PHOTO, place.getmImage());

		long inserted = db.insert(DBHelper.TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteData(Integer eId) {
		this.open();
		try {
			db.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateData(Integer id, PlaceModelClass place) {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_NAME, place.getmName());
		values.put(DBHelper.KEY_PURPOSE, place.getmPurpose());
		values.put(DBHelper.KEY_ADDRESS, place.getmAddress());
		values.put(DBHelper.KEY_DISTRICT, place.getmDistrict());
		values.put(DBHelper.KEY_LATITUDE, place.getmLatitude());
		values.put(DBHelper.KEY_LOGITUDE, place.getmLongitude());
		values.put(DBHelper.KEY_START_DATE, place.getmStartDate());
		values.put(DBHelper.KEY_END_DATE, place.getmEndDate());
		values.put(DBHelper.KEY_NOTE, place.getmNote());

		long updated = 0;
		try {
			updated = db.update(DBHelper.TABLE_NAME, values, DBHelper.KEY_ID
					+ "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All place list
	public ArrayList<PlaceModelClass> getPlaceList() {
		ArrayList<PlaceModelClass> place_list = new ArrayList<PlaceModelClass>();
		open();
		Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_NAME));
				String purpose = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_PURPOSE));
				String address = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_ADDRESS));
				String district = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_DISTRICT));
				String latitude = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_LATITUDE));
				String longitude = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_LOGITUDE));
				String startDate = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_START_DATE));
				String endDate = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_END_DATE));
				String note = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_NOTE));
				String photo = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_PHOTO));

				PlaceModelClass place = new PlaceModelClass(id, name, purpose,
						address, district, latitude, longitude, startDate,
						endDate, note, photo);
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
	public PlaceModelClass getDetail(int id) {
		PlaceModelClass place_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME
				+ " WHERE " + DBHelper.KEY_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String name = cursor
				.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
		String purpose = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_PURPOSE));
		String address = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_ADDRESS));
		String district = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_DISTRICT));
		String latitude = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_LATITUDE));
		String longitude = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_LOGITUDE));
		String startDate = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_START_DATE));
		String endDate = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_END_DATE));
		String note = cursor
				.getString(cursor.getColumnIndex(DBHelper.KEY_NOTE));
		String photo = cursor.getString(cursor
				.getColumnIndex(DBHelper.KEY_PHOTO));

		place_detail = new PlaceModelClass(id, name, purpose, address,
				district, latitude, longitude, startDate, endDate, note, photo);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return place detail
		return place_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DBHelper.TABLE_NAME, new String[] {
				DBHelper.KEY_ID, DBHelper.KEY_NAME, DBHelper.KEY_PURPOSE,
				DBHelper.KEY_ADDRESS, DBHelper.KEY_DISTRICT,
				DBHelper.KEY_LATITUDE, DBHelper.KEY_LOGITUDE,
				DBHelper.KEY_START_DATE, DBHelper.KEY_END_DATE,
				DBHelper.KEY_NOTE, DBHelper.KEY_PHOTO, }, null, null, null,
				null, null);

		if (cursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}
}

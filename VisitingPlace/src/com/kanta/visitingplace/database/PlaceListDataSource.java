package com.kanta.visitingplace.database;

import java.util.ArrayList;

import com.kanta.visitingplace.modelclass.PlaceModelClass;

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

		long inserted = db.insert(DBHelper.TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteData(long eId) {
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

	// Getting All place list
	public ArrayList<PlaceModelClass> getPlaceList() {
		ArrayList<PlaceModelClass> place_list = new ArrayList<PlaceModelClass>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;

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

				PlaceModelClass place = new PlaceModelClass(id, name, purpose,
						address, district, latitude, longitude, startDate,
						endDate, note);
				place_list.add(place);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return place list
		return place_list;
	}

	// Getting All place list
	public PlaceModelClass getDetail(long id) {
		PlaceModelClass place_detail;
		open();
		
		String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME + " WHERE "
				+ DBHelper.KEY_ID + "=" + id;

		Cursor cursor = db.query(DBHelper.TABLE_NAME, null, DBHelper.KEY_ID + "="
				+ id, null, null, null, null, null);
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

		place_detail = new PlaceModelClass(id, name, purpose, address,
				district, latitude, longitude, startDate, endDate, note);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return place detail
		return place_detail;
	}
	
	public boolean isEmpty(){
		this.open();		
		 Cursor cursor = db.query(DBHelper.TABLE_NAME, new String[] {
				DBHelper.KEY_ID, DBHelper.KEY_NAME, DBHelper.KEY_PURPOSE,
				DBHelper.KEY_ADDRESS, DBHelper.KEY_DISTRICT,
				DBHelper.KEY_LATITUDE, DBHelper.KEY_LOGITUDE,
				DBHelper.KEY_START_DATE, DBHelper.KEY_END_DATE,
				DBHelper.KEY_NOTE,},
				 null, null, null, null, null);
		 
		if(cursor.getCount() == 0) {
		this.close();
		return true;
		}
		else
		{
		this.close();
		return false;
		}
		}
}

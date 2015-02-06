package com.ftfl.photowithlocation.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ftfl.photowithlocation.modelclass.PhotoModelClass;

public class ImageListDataSource {
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public ImageListDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	// Adding new
	public long addNew(PhotoModelClass photo) {
		open();
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_LATITUDE, photo.getmLatitude());
		values.put(DBHelper.KEY_LOGITUDE, photo.getmLongitude());
		values.put(DBHelper.KEY_DESCRIPTION, photo.getmDiscription());
		values.put(DBHelper.KEY_PHOTO_PATH, photo.getmPath());
		values.put(DBHelper.KEY_DATE, photo.getmDate());
		values.put(DBHelper.KEY_TIME, photo.getmTime());

		long inserted = db.insert(DBHelper.TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// Getting All place list
	public ArrayList<PhotoModelClass> getPlaceList() {
		ArrayList<PhotoModelClass> list = new ArrayList<PhotoModelClass>();
		open();
		Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
				String latitude = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_LATITUDE));
				String longitude = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_LOGITUDE));
				String description = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_DESCRIPTION));
				String path = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_PHOTO_PATH));
				String date = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_DATE));
				String time = cursor.getString(cursor
						.getColumnIndex(DBHelper.KEY_TIME));

				PhotoModelClass place = new PhotoModelClass(id, latitude, longitude, description,
						path, date, time);
				list.add(place);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return place list
		return list;
	}

}

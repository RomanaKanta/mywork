package com.ftfl.icare.database;

import java.util.ArrayList;

import com.ftfl.icare.modelclass.UserModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDataSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;

	public UserDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new
	public long addNewUser(UserModel user) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_NAME, user.getmUserName());
		values.put(DataBaseHelper.KEY_BIRTH_DATE, user.getmBirthDate());
		values.put(DataBaseHelper.KEY_HEIGHT, user.getmHeight());
		values.put(DataBaseHelper.KEY_WEIGHT, user.getmWeight());
		values.put(DataBaseHelper.KEY_SPECIAL_INFO, user.getmSprcialInfo());
		values.put(DataBaseHelper.KEY_PHOTO_PATH, user.getmPhoto());
	
		long inserted = db.insert(DataBaseHelper.USER_TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteUserData(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.USER_TABLE_NAME, DataBaseHelper.KEY_USER_ID + "="
					+ eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateUserData(Integer id, UserModel user) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_NAME, user.getmUserName());
		values.put(DataBaseHelper.KEY_BIRTH_DATE, user.getmBirthDate());
		values.put(DataBaseHelper.KEY_HEIGHT, user.getmHeight());
		values.put(DataBaseHelper.KEY_WEIGHT, user.getmWeight());
		values.put(DataBaseHelper.KEY_SPECIAL_INFO, user.getmSprcialInfo());
		values.put(DataBaseHelper.KEY_PHOTO_PATH, user.getmPhoto());

		long updated = 0;
		try {
			updated = db.update(DataBaseHelper.USER_TABLE_NAME, values,
					DataBaseHelper.KEY_USER_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All user list
	public ArrayList<UserModel> getUserList() {
		ArrayList<UserModel> user_list = new ArrayList<UserModel>();
		open();
		Cursor cursor = db.query(DataBaseHelper.USER_TABLE_NAME, null, null, null,
				null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_USER_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_NAME));
				String birth = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_BIRTH_DATE));
				String height = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_HEIGHT));
				String weight = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_WEIGHT));
				String special = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_SPECIAL_INFO));
				String path = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_PHOTO_PATH));

				UserModel user = new UserModel( id, name, birth, height,
						weight,  special, path);
				user_list.add(user);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return user list
		return user_list;
	}

	// Getting All user detail
	public UserModel getUserDetail(int id) {
		UserModel user_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DataBaseHelper.USER_TABLE_NAME
				+ " WHERE " + DataBaseHelper.KEY_USER_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_NAME));
		String birth = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_BIRTH_DATE));
		String height = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_HEIGHT));
		String weight = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_WEIGHT));
		String special = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_SPECIAL_INFO));
		String path = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_PHOTO_PATH));

		user_detail = new UserModel( id, name, birth, height,
				weight,  special, path);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return user detail
		return user_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.USER_TABLE_NAME, new String[] {
				DataBaseHelper.KEY_USER_ID, DataBaseHelper.KEY_NAME,
				DataBaseHelper.KEY_BIRTH_DATE, DataBaseHelper.KEY_HEIGHT,
				DataBaseHelper.KEY_WEIGHT, DataBaseHelper.KEY_SPECIAL_INFO,
				DataBaseHelper.KEY_PHOTO_PATH, }, null,
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

package com.ftfl.mymeetingplaces.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MeetingPlaceDB";

	//table name
	public static final String TABLE_NAME = "meeting_information";

	// Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_MOBILE = "mobile";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LOGITUDE = "longitude";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_FILENAME = "filename";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	

	// table information
	public String CREATE_MEETING_TABLE = "create table " + TABLE_NAME + "("
			                          + KEY_ID + " integer primary key autoincrement, " 			                         
			                          + KEY_NAME + " text not null, "
			                          + KEY_EMAIL + " text not null, "
			                          + KEY_MOBILE + " text not null, "
			                          + KEY_LATITUDE + " text not null, "
			                          + KEY_LOGITUDE + " text not null, "
			                          + KEY_DESCRIPTION + " text not null, "
			                          + KEY_FILENAME + " text not null, "
			                          + KEY_DATE + " text not null, "
	                                  + KEY_TIME + " text not null);";

   // Create Database
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

   // Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MEETING_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DataBaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
	
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_MEETING_TABLE);
		onCreate(db);
	}

}

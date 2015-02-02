package com.ftfl.triplist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "TripList";

	//table name
	public static final String TABLE_NAME = "place_info";

	// Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PURPOSE = "purpose";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_DISTRICT = "district";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LOGITUDE = "longitude";
	public static final String KEY_START_DATE = "startdate";
	public static final String KEY_END_DATE = "enddate";
	public static final String KEY_NOTE = "note";
	public static final String KEY_PHOTO = "photo";

	// table information
	public String CREATE_PLACE_TABLE = "create table " + TABLE_NAME + "("
			                          + KEY_ID + " integer primary key autoincrement, " 
			                          + KEY_NAME + " text not null, "
			                          + KEY_PURPOSE + " text not null, " 
			                          + KEY_ADDRESS + " text not null, " 
			                          + KEY_DISTRICT + " text not null, "
			                          + KEY_LATITUDE + " text not null, "
			                          + KEY_LOGITUDE + " text not null, "
			                          + KEY_START_DATE + " text not null, "
			                          + KEY_END_DATE + " text not null, "
			                          + KEY_NOTE + " text not null, "
	                                  + KEY_PHOTO + " text not null);";

   // Create Database
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

   // Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PLACE_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
	
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_PLACE_TABLE);
		onCreate(db);
	}

}
package com.ftfl.icare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ICareDB";

	/********************* USER TABLE************************/
	//table name
	public static final String USER_TABLE_NAME = "user_information";

	// Table Columns names
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_BIRTH_DATE = "birth";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_WEIGHT = "weight";
	public static final String KEY_SPECIAL_INFO = "special_info";
	public static final String KEY_PHOTO_PATH = "photo_path";

	// table information
	public String CREATE_USER_TABLE = "create table " + USER_TABLE_NAME + "("
			+ KEY_USER_ID + " integer primary key autoincrement, " 
			+ KEY_NAME + " text not null, " 
			+ KEY_BIRTH_DATE + " text not null, "
			+ KEY_HEIGHT + " text not null, " 
			+ KEY_WEIGHT + " text not null, "
			+ KEY_SPECIAL_INFO + " text not null, " 
			+ KEY_PHOTO_PATH + " text not null);";

	/********************* DOCTOR TABLE************************/
	// table name
	public static final String DOCTOR_TABLE_NAME = "doctor_information";

	// Table Columns names
	public static final String KEY_DOCTOR_ID = "doctor_id";
	public static final String KEY_DOCTOR_NAME = "doctor_name";
	public static final String KEY_SPECIALIST = "specialist";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";

	// table information
	public String CREATE_DOCTOR_TABLE = "create table " + DOCTOR_TABLE_NAME + "(" 
			+ KEY_DOCTOR_ID + " integer primary key autoincrement, "
			+ KEY_DOCTOR_NAME + " text not null, " 
			+ KEY_SPECIALIST + " text not null, "
			+ KEY_PHONE + " text not null, " 
			+ KEY_EMAIL + " text not null, "
			+ KEY_ADDRESS + " text not null, " 
			+ KEY_DATE + " text not null, "
			+ KEY_TIME + " text not null);";

	/********************* DIET CHART TABLE************************/
	// table name
	public static final String DIET_TABLE_NAME = "diet_information";

	// Table Columns names
	public static final String KEY_DIET_ID = "diet_id";
	public static final String KEY_FEAST = "feast";
	public static final String KEY_MENU = "menu";
	public static final String KEY_DIET_DATE = "diet_date";
	public static final String KEY_DIET_TIME = "diet_time";
	public static final String KEY_ALARM = "alarm";

	// table information
	public String CREATE_DIET_TABLE = "create table " + DIET_TABLE_NAME + "("
			+ KEY_DIET_ID + " integer primary key autoincrement, "
			+ KEY_FEAST + " text not null, " 
			+ KEY_MENU + " text not null, "
			+ KEY_DIET_DATE + " text not null, " 
			+ KEY_DIET_TIME + " text not null, " 
			+ KEY_ALARM + " text not null);";

	/********************* VACCINATION CHART TABLE************************/
	// table name
	public static final String VACCINATION_TABLE_NAME = "vaccination_information";

	// Table Columns names
	public static final String KEY_VACCINATION_ID = "vaccination_id";
	public static final String KEY_VACCINATION_NAME = "vaccination_name";
	public static final String KEY_VACCINATION_DATE = "vaccination_date";
	public static final String KEY_VACCINATION_TIME = "vaccination_time";
	public static final String KEY_VACCINATION_REASON = "vaccination_reason";
	public static final String KEY_VACCINATION_ADDRESS = "vaccination_address";
	public static final String KEY_VACCINATION_ALARM = "vaccination_alarm";

	// table information
	public String CREATE_VACCINATION_TABLE = "create table "
			+ VACCINATION_TABLE_NAME + "("
			+ KEY_VACCINATION_ID + " integer primary key autoincrement, " 
			+ KEY_VACCINATION_NAME + " text not null, "
			+ KEY_VACCINATION_DATE + " text not null, " 
			+ KEY_VACCINATION_TIME + " text not null, " 
			+ KEY_VACCINATION_REASON + " text not null, "
			+ KEY_VACCINATION_ADDRESS + " text not null, "
			+ KEY_VACCINATION_ALARM + " text not null);";

	/********************* MEDICAL HISTORY TABLE************************/
	// table name
	public static final String MEDICAL_HISTORY_TABLE_NAME = "prescription_information";

	// Table Columns names
	public static final String KEY_PRESCRIPTION_ID = "prescription_id";
	public static final String KEY_DOCTOR = "prescription_doctor";
	public static final String KEY_PRESCRIPTION_DATE = "prescription_date";
	public static final String KEY_PRESCRIPTION_REASON = "prescription_reason";
	public static final String KEY_PRESCRIPTION_SUGGESTION = "prescription_suggestion";
	public static final String KEY_PRESCRIPTION_PHOTO = "prescription_photo";

	// table information
	public String CREATE_HISTORY_TABLE = "create table "
			+ MEDICAL_HISTORY_TABLE_NAME + "(" 
			+ KEY_PRESCRIPTION_ID + " integer primary key autoincrement, " 
			+ KEY_DOCTOR + " text not null, " 
			+ KEY_PRESCRIPTION_DATE + " text not null, "
			+ KEY_PRESCRIPTION_REASON + " text not null, " 
			+ KEY_PRESCRIPTION_SUGGESTION + " text not null, " 
			+ KEY_PRESCRIPTION_PHOTO + " text not null);";
	
	/********************* IMPROTANT NOTES WITH DATE************************/
	// table name
	public static final String IMPROTANT_NOTE_TABLE_NAME = "important_information";

	// Table Columns names
	public static final String KEY_NOTE_ID = "note_id";
	public static final String KEY_IMPORTANT_DATE = "note_date";
	public static final String KEY_NOTE = "note";

	// table information
	public String CREATE_IMPROTANT_NOTE_TABLE = "create table "
			+ IMPROTANT_NOTE_TABLE_NAME + "(" 
			+ KEY_NOTE_ID + " integer primary key autoincrement, " 
			+ KEY_IMPORTANT_DATE + " text not null, "  
			+ KEY_NOTE + " text not null);";

	/*********************** Create Database **********************/
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/********************** Creating Tables *********************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_DOCTOR_TABLE);
		db.execSQL(CREATE_DIET_TABLE);
		db.execSQL(CREATE_VACCINATION_TABLE);
		db.execSQL(CREATE_HISTORY_TABLE);
		db.execSQL(CREATE_IMPROTANT_NOTE_TABLE);
	}

	/************************* Upgrading database **********************/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DataBaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + CREATE_USER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_DOCTOR_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_DIET_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_VACCINATION_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_HISTORY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_IMPROTANT_NOTE_TABLE);
		onCreate(db);
	}

}

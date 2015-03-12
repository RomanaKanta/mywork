package com.ftfl.icare.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.modelclass.NoteModel;

public class NoteDataSource {
	private SQLiteDatabase db;
	private DataBaseHelper dbHelper;

	public NoteDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Adding new note
	public long addNewNote(NoteModel note) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_IMPORTANT_DATE, note.getmDate());
		values.put(DataBaseHelper.KEY_NOTE, note.getmNote());

		long inserted = db.insert(DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME, null, values);
		close();
		return inserted;
	}

	// delete data form database.
	public boolean deleteNote(Integer eId) {
		this.open();
		try {
			db.delete(DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME, DataBaseHelper.KEY_NOTE_ID + "="
					+ eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data not deleted");
			return false;
		}
		this.close();
		return true;
	}

	// update database by Id
	public long updateNote(Integer id, NoteModel note) {
		open();
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KEY_IMPORTANT_DATE, note.getmDate());
		values.put(DataBaseHelper.KEY_NOTE, note.getmNote());
		long updated = 0;
		try {
			updated = db.update(DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME, values,
					DataBaseHelper.KEY_NOTE_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data upgraion problem");
		}
		close();
		return updated;
	}

	// Getting All note list
	public ArrayList<NoteModel> getNoteList() {
		ArrayList<NoteModel> note_list = new ArrayList<NoteModel>();
		open();
		Cursor cursor = db.query(DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME, null, null, null,
				null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(DataBaseHelper.KEY_NOTE_ID));
				String date = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_IMPORTANT_DATE));
				String note = cursor.getString(cursor
						.getColumnIndex(DataBaseHelper.KEY_NOTE));
NoteModel impnote = new NoteModel(id, date, note);
				note_list.add(impnote);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		// return note list
		return note_list;
	}

	// Getting All note detail
	public NoteModel getNoteDetail(int id) {
		NoteModel note_detail;
		open();

		String selectQuery = "SELECT  * FROM " + DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME
				+ " WHERE " + DataBaseHelper.KEY_NOTE_ID + "=" + id;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String date = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_IMPORTANT_DATE));
		String note = cursor.getString(cursor
				.getColumnIndex(DataBaseHelper.KEY_NOTE));

		note_detail = new NoteModel(id, date, note);

		cursor.moveToNext();

		cursor.close();
		db.close();

		// return note detail
		return note_detail;
	}

	public boolean isEmpty() {
		this.open();
		Cursor cursor = db.query(DataBaseHelper.IMPROTANT_NOTE_TABLE_NAME, new String[] {
				DataBaseHelper.KEY_NOTE_ID, DataBaseHelper.KEY_IMPORTANT_DATE,
				DataBaseHelper.KEY_NOTE,}, null,
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

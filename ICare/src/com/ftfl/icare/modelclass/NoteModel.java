package com.ftfl.icare.modelclass;

public class NoteModel {
	Integer mNoteId = 0;
	String mDate = null;
	String mNote = null;
	public NoteModel() {
	}

	public NoteModel(int eNoteId, String eDate, String eNote) {
		this.mNoteId = eNoteId;
		this.mDate = eDate;
		this.mNote = eNote;
	}
	
	public NoteModel(String eDate, String eNote) {
		this.mDate = eDate;
		this.mNote = eNote;
	}

	public Integer getmNoteId() {
		return mNoteId;
	}

	public void setmNoteId(int mNoteId) {
		this.mNoteId = mNoteId;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmNote() {
		return mNote;
	}

	public void setmNote(String mNote) {
		this.mNote = mNote;
	}

}

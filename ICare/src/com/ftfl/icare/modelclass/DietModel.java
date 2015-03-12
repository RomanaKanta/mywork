package com.ftfl.icare.modelclass;

public class DietModel {
	int mDietId = 0;
	int mUserId = 0;
	String mFeast = null;
	String mMenu = null;
	String mTime = null;
	String mDate = null;
	String mSummery = null;
	String mAlarm = null;

	DietModel() {
	}

	public DietModel(String eFeast, String eMenu,
			String eTime, String eDate,String eAlarm) {
		this.mFeast = eFeast;
		this.mMenu = eMenu;
		this.mTime = eTime;
		this.mDate = eDate;
		this.mAlarm = eAlarm;
	}
	
	public DietModel(int eDietId,String eFeast, String eMenu,
			String eTime, String eDate,String eAlarm) {
		this.mDietId = eDietId;
		this.mFeast = eFeast;
		this.mMenu = eMenu;
		this.mTime = eTime;
		this.mDate = eDate;
		this.mAlarm = eAlarm;
	}

	public Integer getmDietId() {
		return mDietId;
	}

	public void setmDietId(int mDietId) {
		this.mDietId = mDietId;
	}

	public int getmUserId() {
		return mUserId;
	}

	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getmFeast() {
		return mFeast;
	}

	public void setmFeast(String mFeast) {
		this.mFeast = mFeast;
	}

	public String getmMenu() {
		return mMenu;
	}

	public void setmMenu(String mMenu) {
		this.mMenu = mMenu;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmSummery() {
		return mSummery;
	}

	public void setmSummery(String mSummery) {
		this.mSummery = mSummery;
	}

	public String getmAlarm() {
		return mAlarm;
	}

	public void setmAlarm(String mAlarm) {
		this.mAlarm = mAlarm;
	}
}

package com.ftfl.photowithlocation.modelclass;

public class PhotoModelClass {
	Integer mID = 0;
	String mLatitude = "";
	String mLongitude = "";
	String mDiscription = "";
	String mPath = "";
	String mDate = "";
	String mTime = "";

	public PhotoModelClass() {

	}

	public PhotoModelClass(String eLatitude, String eLongitude,
			String eDiscription, String ePath, String eDate, String eTime) {
		super();
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mDiscription = eDiscription;
		this.mPath = ePath;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public PhotoModelClass(Integer eid, String eLatitude, String eLongitude,
			String eDiscription, String ePath, String eDate, String eTime) {
		super();
		this.mID = eid;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mDiscription = eDiscription;
		this.mPath = ePath;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public Integer getmID() {
		return mID;
	}

	public void setmID(Integer mID) {
		this.mID = mID;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmDiscription() {
		return mDiscription;
	}

	public void setmDiscription(String mDiscription) {
		this.mDiscription = mDiscription;
	}

	public String getmPath() {
		return mPath;
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	
}

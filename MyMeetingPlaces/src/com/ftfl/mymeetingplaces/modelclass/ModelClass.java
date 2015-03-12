package com.ftfl.mymeetingplaces.modelclass;

public class ModelClass {
	Integer mID = 0;
	String mName = "";
	String mEmail = "";
	String mMobile = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDiscription = "";
	String mFileName = "";
	String mDate = "";
	String mTime = "";
	String mDistance = "";
	double mDlatitude = 0.0;
	double mDlongitude = 0.0;

	public ModelClass() {

	}

	public ModelClass(String eName, String eEmail, String eMobile,
			String eLatitude, String eLongitude, String eDiscription,
			String eFileName, String eDate, String eTime) {
		super();
		this.mName = eName;
		this.mEmail = eEmail;
		this.mMobile = eMobile;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mDiscription = eDiscription;
		this.mFileName = eFileName;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public ModelClass(Integer eID, String eName, String eEmail, String eMobile,
			String eLatitude, String eLongitude, String eDiscription,
			String eFileName, String eDate, String eTime) {
		super();
		this.mID = eID;
		this.mName = eName;
		this.mEmail = eEmail;
		this.mMobile = eMobile;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mDiscription = eDiscription;
		this.mFileName = eFileName;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Integer getmID() {
		return mID;
	}

	public void setmID(Integer mID) {
		this.mID = mID;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmMobile() {
		return mMobile;
	}

	public void setmMobile(String mMobile) {
		this.mMobile = mMobile;
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

	public String getmFileName() {
		return mFileName;
	}

	public void setmFileName(String mFileName) {
		this.mFileName = mFileName;
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

	public String getmDistance() {
		return mDistance;
	}

	public void setmDistance(String mDistance) {
		this.mDistance = mDistance;
	}

	public double getmDlatitude() {
		return mDlatitude;
	}

	public void setmDlatitude(double mDlatitude) {
		this.mDlatitude = mDlatitude;
	}

	public double getmDlongitude() {
		return mDlongitude;
	}

	public void setmDlongitude(double mDlongitude) {
		this.mDlongitude = mDlongitude;
	}

	

}

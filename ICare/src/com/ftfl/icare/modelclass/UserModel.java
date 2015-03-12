package com.ftfl.icare.modelclass;

public class UserModel {

	Integer mId = 0;
	Integer mDay = 0;
	Integer mMonth = 0;
	Integer mYear = 0;
	String mUserName = null;
	String mBirthDate = null;
	String mHeight = null;
	String mWeight = null;
	String mSprcialInfo = null;
	String mPhoto = null;

	UserModel() {
	}

	public UserModel(Integer eId, String eUserName, String eBirthDate, String eHeight,
			String eWeight, String eSprcialInfo, String ePhoto) {
		this.mId = eId;
		this.mUserName = eUserName;
		this.mBirthDate = eBirthDate;
		this.mHeight = eHeight;
		this.mWeight = eWeight;
		this.mSprcialInfo = eSprcialInfo;
		this.mPhoto = ePhoto;
	}
	
	public UserModel(String eUserName, String eBirthDate, String eHeight,
			String eWeight, String eSprcialInfo, String ePhoto, Integer eDay
			,Integer eMonth,Integer eYear) {
		this.mUserName = eUserName;
		this.mBirthDate = eBirthDate;
		this.mHeight = eHeight;
		this.mWeight = eWeight;
		this.mSprcialInfo = eSprcialInfo;
		this.mPhoto = ePhoto;
		this.mDay = eDay;
		this.mMonth = eMonth;
		this.mYear = eYear;
	}

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer id) {
		this.mId = id;
	}

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String userName) {
		this.mUserName = userName;
	}

	public String getmBirthDate() {
		return mBirthDate;
	}

	public void setmBirthDate(String birthDate) {
		this.mBirthDate = birthDate;
	}

	public String getmHeight() {
		return mHeight;
	}

	public void setmHeight(String height) {
		this.mHeight = height;
	}

	public String getmWeight() {
		return mWeight;
	}

	public void setmWeight(String weight) {
		this.mWeight = weight;
	}

	public String getmSprcialInfo() {
		return mSprcialInfo;
	}

	public void setmSprcialInfo(String sprcialInfo) {
		this.mSprcialInfo = sprcialInfo;
	}

	public String getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(String photo) {
		this.mPhoto = photo;
	}

	public Integer getmDay() {
		return mDay;
	}

	public void setmDay(Integer mDay) {
		this.mDay = mDay;
	}

	public Integer getmMonth() {
		return mMonth;
	}

	public void setmMonth(Integer mMonth) {
		this.mMonth = mMonth;
	}

	public Integer getmYear() {
		return mYear;
	}

	public void setmYear(Integer mYear) {
		this.mYear = mYear;
	}
	
	
}

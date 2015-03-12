package com.ftfl.icare.modelclass;


public class VaccinationModel {
	Integer mVacId = 0;
	Integer mUserId = 0;
	String mVacName = null;
	String mVacTime = null;
	String mVacDate = null;
	String mVacReason = null;
	String mVacAddress = null;
	String mVacAlarm = null;

	VaccinationModel() {
	}

	public VaccinationModel(String eVacName, String eVacTime,
			String eVacDate, String eVacReason, String eVacAddress, String eVacAlarm) {
		this.mVacName = eVacName;
		this.mVacTime = eVacTime;
		this.mVacDate = eVacDate;
		this.mVacReason = eVacReason;
		this.mVacAddress = eVacAddress;
		this.mVacAlarm = eVacAlarm;
	}
	
	public VaccinationModel(int eVacId, String eVacName, String eVacTime,
			String eVacDate, String eVacReason, String eVacAddress,
			String eVacAlarm) {
		this.mVacId = eVacId;
		this.mVacName = eVacName;
		this.mVacTime = eVacTime;
		this.mVacDate = eVacDate;
		this.mVacReason = eVacReason;
		this.mVacAddress = eVacAddress;
		this.mVacAlarm = eVacAlarm;
	}
	
	public VaccinationModel(int eVacId, int eUserId, String eVacName, String eVacTime,
			String eVacDate, String eVacReason, String eVacAddress,
			String eVacAlarm) {
		this.mVacId = eVacId;
		this.mUserId = eUserId;
		this.mVacName = eVacName;
		this.mVacTime = eVacTime;
		this.mVacDate = eVacDate;
		this.mVacReason = eVacReason;
		this.mVacAddress = eVacAddress;
		this.mVacAlarm = eVacAlarm;
	}
	
	public VaccinationModel(int eVacId, int eUserId, String eVacName, String eVacTime,
			String eVacDate, String eVacReason, String eVacAddress) {
		this.mVacId = eVacId;
		this.mUserId = eUserId;
		this.mVacName = eVacName;
		this.mVacTime = eVacTime;
		this.mVacDate = eVacDate;
		this.mVacReason = eVacReason;
		this.mVacAddress = eVacAddress;
	}

	public Integer getmVacId() {
		return mVacId;
	}

	public void setmVacId(int mVacId) {
		this.mVacId = mVacId;
	}

	public int getmUserId() {
		return mUserId;
	}

	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getmVacName() {
		return mVacName;
	}

	public void setmVacName(String mVacName) {
		this.mVacName = mVacName;
	}

	public String getmVacTime() {
		return mVacTime;
	}

	public void setmVacTime(String mVacTime) {
		this.mVacTime = mVacTime;
	}

	public String getmVacDate() {
		return mVacDate;
	}

	public void setmVacDate(String mVacDate) {
		this.mVacDate = mVacDate;
	}

	public String getmVacReason() {
		return mVacReason;
	}

	public void setmVacReason(String mVacReason) {
		this.mVacReason = mVacReason;
	}

	public String getmVacAddress() {
		return mVacAddress;
	}

	public void setmVacAddress(String mVacAddress) {
		this.mVacAddress = mVacAddress;
	}

	public String getmVacAlarm() {
		return mVacAlarm;
	}

	public void setmVacAlarm(String mVacAlarm) {
		this.mVacAlarm = mVacAlarm;
	}

}

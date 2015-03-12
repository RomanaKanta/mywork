package com.ftfl.icare.modelclass;

public class DoctorModel {
	Integer mDocId = 0;
	int mUserId = 0;
	String mDocName = null;
	String mSpecialist = null;
	String mPhone = null;
	String mEmail = null;
	String mAddress = null;
	String mDate = null;
	String mTime = null;

	DoctorModel() {
	}

	public DoctorModel(Integer eDocId, String eDocName, String eSpecialist,
			String ePhone, String eEmail, String eAddress, String eDate,
			String eTime) {
		this.mDocId = eDocId;
		this.mDocName = eDocName;
		this.mSpecialist = eSpecialist;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public DoctorModel(String eDocName, String eSpecialist,
			String ePhone, String eEmail, String eAddress, String eDate,
			String eTime) {
		this.mDocName = eDocName;
		this.mSpecialist = eSpecialist;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mDate = eDate;
		this.mTime = eTime;
	}
	
	public DoctorModel(int eDocId, int eUserId, String eDocName, String eSpecialist,
			String ePhone, String eEmail, String eAddress) {
		this.mDocId = eDocId;
		this.mUserId = eUserId;
		this.mDocName = eDocName;
		this.mSpecialist = eSpecialist;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
	}

	public Integer getmDocId() {
		return mDocId;
	}

	public void setmDocId(Integer mDocId) {
		this.mDocId = mDocId;
	}

	public int getmUserId() {
		return mUserId;
	}

	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getmDocName() {
		return mDocName;
	}

	public void setmDocName(String mDocName) {
		this.mDocName = mDocName;
	}

	public String getmSpecialist() {
		return mSpecialist;
	}

	public void setmSpecialist(String mSpecialist) {
		this.mSpecialist = mSpecialist;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
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

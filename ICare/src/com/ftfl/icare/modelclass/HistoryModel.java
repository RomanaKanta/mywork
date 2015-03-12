package com.ftfl.icare.modelclass;

public class HistoryModel {
	Integer mPrescriptionId = 0;
	int mUserId = 0;
	String mDoctorName = null;
	String mPrescriptionDate = null;
	String mPrescriptionReason = null;
	String mDocSuggestion = null;
	String mPrescriptionPhoto = null;

	HistoryModel() {
	}

	public HistoryModel(String eDoctorName,
			String ePrescriptionDate, String ePrescriptionReason,
			String eDocSuggestion, String ePrescription) {
		this.mDoctorName = eDoctorName;
		this.mPrescriptionDate = ePrescriptionDate;
		this.mPrescriptionReason = ePrescriptionReason;
		this.mDocSuggestion = eDocSuggestion;
		this.mPrescriptionPhoto = ePrescription;
	}
	
	public HistoryModel(int ePrescriptionId,String eDoctorName,
			String ePrescriptionDate, String ePrescriptionReason,
			String eDocSuggestion, String ePrescription) {
		this.mPrescriptionId = ePrescriptionId;
		this.mDoctorName = eDoctorName;
		this.mPrescriptionDate = ePrescriptionDate;
		this.mPrescriptionReason = ePrescriptionReason;
		this.mDocSuggestion = eDocSuggestion;
		this.mPrescriptionPhoto = ePrescription;
	}

	public Integer getmPrescriptionId() {
		return mPrescriptionId;
	}

	public void setmPrescriptionId(Integer mPrescriptionId) {
		this.mPrescriptionId = mPrescriptionId;
	}

	public int getmUserId() {
		return mUserId;
	}

	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getmDoctorName() {
		return mDoctorName;
	}

	public void setmDoctorName(String mDoctorName) {
		this.mDoctorName = mDoctorName;
	}

	public String getmPrescriptionDate() {
		return mPrescriptionDate;
	}

	public void setmPrescriptionDate(String mPrescriptionDate) {
		this.mPrescriptionDate = mPrescriptionDate;
	}

	public String getmPrescriptionReason() {
		return mPrescriptionReason;
	}

	public void setmPrescriptionReason(String mPrescriptionReason) {
		this.mPrescriptionReason = mPrescriptionReason;
	}

	public String getmDocSuggestion() {
		return mDocSuggestion;
	}

	public void setmDocSuggestion(String mDocSuggestion) {
		this.mDocSuggestion = mDocSuggestion;
	}

	public String getmPrescription() {
		return mPrescriptionPhoto;
	}

	public void setmPrescription(String mPrescription) {
		this.mPrescriptionPhoto = mPrescription;
	}
}

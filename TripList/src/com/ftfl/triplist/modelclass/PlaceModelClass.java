package com.ftfl.triplist.modelclass;

public class PlaceModelClass {
	Integer mID = 0;
	String mName = "";
	String mPurpose = "";
	String mAddress = "";
	String mDistrict = "";
	String mLatitude = "";
	String mLongitude = "";
	String mStartDate = "";
	String mEndDate = "";
	String mNote = "";
	String mImage = "";

	public PlaceModelClass() {

	}

	public PlaceModelClass(String eName, String ePurpose, String eAddress,
			String eDistrict, String eLatitude, String eLongitude,
			String eStartDate, String eEndDate, String eNote, String eImage) {
		super();
		this.mName = eName;
		this.mPurpose = ePurpose;
		this.mAddress = eAddress;
		this.mDistrict = eDistrict;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mStartDate = eStartDate;
		this.mEndDate = eEndDate;
		this.mNote = eNote;
		this.mImage = eImage;
	}

	public PlaceModelClass(Integer eid, String eName, String ePurpose,
			String eAddress, String eDistrict, String eLatitude,
			String eLongitude, String eStartDate, String eEndDate,
			String eNote, String eImage) {
		super();
		this.mID = eid;
		this.mName = eName;
		this.mPurpose = ePurpose;
		this.mAddress = eAddress;
		this.mDistrict = eDistrict;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mStartDate = eStartDate;
		this.mEndDate = eEndDate;
		this.mNote = eNote;
		this.mImage = eImage;
	}

	public Integer getId() {
		return mID;
	}

	public void setId(Integer id) {
		this.mID = id;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String eName) {
		this.mName = eName;
	}

	public String getmPurpose() {
		return mPurpose;
	}

	public void setmPurpose(String ePurpose) {
		this.mPurpose = ePurpose;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String eAddress) {
		this.mAddress = eAddress;
	}

	public String getmDistrict() {
		return mDistrict;
	}

	public void setmDistrict(String eDistrict) {
		this.mDistrict = eDistrict;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String eLatitude) {
		this.mLatitude = eLatitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String eLongitude) {
		this.mLongitude = eLongitude;
	}

	public String getmStartDate() {
		return mStartDate;
	}

	public void setmStartDate(String eStartDate) {
		this.mStartDate = eStartDate;
	}

	public String getmEndDate() {
		return mEndDate;
	}

	public void setmEndDate(String eEndDate) {
		this.mEndDate = eEndDate;
	}

	public String getmNote() {
		return mNote;
	}

	public void setmNote(String eNote) {
		this.mNote = eNote;
	}

	public String getmImage() {
		return mImage;
	}

	public void setmImage(String mImage) {
		this.mImage = mImage;
	}

}

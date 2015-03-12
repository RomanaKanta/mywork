package com.ftfl.icare.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.icare.R;

public class CenterListAdapter extends ArrayAdapter<String> {

	// Variable Declaration
	Activity mContext = null;
	ArrayList<String> centerNameList = null;
	ArrayList<String> centerAddressList = null;
	ArrayList<String> centerPhoneList = null;
	ArrayList<String> centerLatitudeList = null;
	ArrayList<String> centerLongitudeList = null;

	public CenterListAdapter(Activity context, ArrayList<String> nList,
			ArrayList<String> aList, ArrayList<String> pList,
			ArrayList<String> latList, ArrayList<String> lngList) {
		super(context, R.layout.center_list_row, nList);
		this.mContext = context;
		this.centerNameList = nList;
		this.centerAddressList = aList;
		this.centerPhoneList = pList;
		this.centerLatitudeList = latList;
		this.centerLongitudeList = lngList;
		// TODO Auto-generated constructor stub
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater mInflater = mContext.getLayoutInflater();
		View rowView = mInflater.inflate(R.layout.center_list_row, null, true);

		// definition - gives variable a reference
		TextView mCenterTextView = (TextView) rowView
				.findViewById(R.id.centername);
		TextView mAddressTextView = (TextView) rowView
				.findViewById(R.id.centeraddress);
		TextView mPhoneTexView = (TextView) rowView
				.findViewById(R.id.centerphone);
		TextView mLatTexView = (TextView) rowView.findViewById(R.id.centerlat);
		TextView mLngTexView = (TextView) rowView.findViewById(R.id.centerlng);

		// set text in view
		mCenterTextView.setText(centerNameList.get(position));
		mAddressTextView.setText(centerAddressList.get(position));
		mPhoneTexView.setText(centerPhoneList.get(position));
		mLatTexView.setText(centerLatitudeList.get(position));
		mLngTexView.setText(centerLongitudeList.get(position));

		return rowView;
	}
}

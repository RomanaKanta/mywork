package com.ftfl.icare.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.modelclass.DoctorModel;

public class DocListAdapter extends ArrayAdapter<DoctorModel> {
	Activity mContext;
	DoctorModel mDoctorModel;
	ArrayList<DoctorModel> mArrayObject;

	public DocListAdapter(Activity context,
			ArrayList<DoctorModel> objectArray) {
		super(context, R.layout.profile_list_row, objectArray);
		this.mContext = context;
		this.mArrayObject = objectArray;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {
		public TextView name;
		public TextView id;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mDoctorModel = mArrayObject.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.profile_list_row, parent, false);

			mVHolder = new ViewHolder();
			mVHolder.id = (TextView) rowView.findViewById(R.id.tvUserID);
			mVHolder.name = (TextView) rowView.findViewById(R.id.tvShowUser);
	
			rowView.setTag(mVHolder);
		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.id.setText(mDoctorModel.getmDocId().toString());
		mVHolder.name.setText(mDoctorModel.getmDocName().toString());

		return rowView;
	}
}
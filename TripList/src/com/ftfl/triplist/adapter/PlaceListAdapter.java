package com.ftfl.triplist.adapter;

import java.util.ArrayList;

import com.ftfl.triplist.R;
import com.ftfl.triplist.modelclass.*;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceListAdapter extends ArrayAdapter<PlaceModelClass> {
	Activity mContext = null;
	PlaceModelClass mPlace = null;
	ArrayList<PlaceModelClass> mArrayObject = null;

	public PlaceListAdapter(Activity context,
			ArrayList<PlaceModelClass> objectArray) {
		super(context, R.layout.list_row, objectArray);
		this.mContext = context;
		this.mArrayObject = objectArray;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {
		public TextView name = null;
		public TextView startDate = null;
		public TextView id = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mPlace = mArrayObject.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.list_row, parent, false);

			mVHolder = new ViewHolder();
			mVHolder.id = (TextView) rowView.findViewById(R.id.tvID);
			mVHolder.name = (TextView) rowView.findViewById(R.id.tvShowName);
			mVHolder.startDate = (TextView) rowView
					.findViewById(R.id.tvShowDate);
			rowView.setTag(mVHolder);
		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.id.setText(mPlace.getId().toString());
		mVHolder.startDate.setText(mPlace.getmStartDate().toString());
		mVHolder.name.setText(mPlace.getmName().toString());

		return rowView;
	}
}

package com.kanta.visitingplace.adapter;

import java.util.ArrayList;

import com.kanta.visitingplace.modelclass.PlaceModelClass;
import com.kanta.visitingplace.*;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceListAdapter extends ArrayAdapter<PlaceModelClass> {
	private final Activity context;

	static class ViewHolder {
		public TextView name, date;
	}

	ArrayList<PlaceModelClass> objectArray;

	public PlaceListAdapter(Activity context,
			ArrayList<PlaceModelClass> objectArray) {
		super(context, R.layout.list_row, objectArray);
		this.context = context;
		this.objectArray = objectArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_row, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.tvShowName);
			viewHolder.date = (TextView) rowView.findViewById(R.id.tvShowDate);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.date.setText(objectArray.get(position).getmStartDate());
		holder.name.setText(objectArray.get(position).getmName());

		return rowView;
	}
}

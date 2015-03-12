package com.ftfl.icare.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.modelclass.VaccinationModel;

public class VaccinationListAdapter extends ArrayAdapter<VaccinationModel> {
	private final Activity context;

	static class ViewHolder {
		public TextView mID;
		public TextView mVacciName;
		public TextView mVacciTime;
		public TextView mVacciReason;
		public TextView mVacciDate;
		public TextView mVacciAddress;
		public ImageView mAlarm;
	}

	ArrayList<VaccinationModel> objectArray;

	public VaccinationListAdapter(Activity context,
			ArrayList<VaccinationModel> objectArray) {
		super(context, R.layout.vaccination_list_row, objectArray);
		this.context = context;
		this.objectArray = objectArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.vaccination_list_row, parent,
					false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.mID = (TextView) rowView.findViewById(R.id.tvVaccineID);
			viewHolder.mVacciName = (TextView) rowView.findViewById(R.id.tvVaccName);
			viewHolder.mVacciTime = (TextView) rowView.findViewById(R.id.tvVaccTime);
			viewHolder.mVacciReason = (TextView) rowView.findViewById(R.id.tvReason);
			viewHolder.mVacciDate = (TextView) rowView.findViewById(R.id.tvVaccDate);

			viewHolder.mVacciAddress = (TextView) rowView.findViewById(R.id.tvAddress);
			viewHolder.mAlarm = (ImageView) rowView.findViewById(R.id.showvaccalarm);

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.mID.setText(objectArray.get(position).getmVacId().toString());
		holder.mVacciTime.setText(objectArray.get(position).getmVacTime());
		holder.mVacciDate.setText(objectArray.get(position).getmVacDate());
		holder.mVacciName.setText(objectArray.get(position).getmVacName());
		holder.mVacciReason.setText(objectArray.get(position).getmVacReason());
		holder.mVacciAddress.setText(objectArray.get(position).getmVacAddress());

		 if(objectArray.get(position).getmVacAlarm().equals("1")){

		 holder.mAlarm.setVisibility(View.VISIBLE);
		 }

		return rowView;

	}

}
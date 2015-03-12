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
import com.ftfl.icare.modelclass.DietModel;

public class DietListAdapter extends ArrayAdapter<DietModel> {
	private Activity context = null;

	static class ViewHolder {
		public TextView id = null;
		public TextView feast = null;
		public TextView time = null;
		public TextView menu = null;
		public TextView date = null;
		public ImageView image = null;
	}

	ArrayList<DietModel> objectArray;

	public DietListAdapter(Activity context,
			ArrayList<DietModel> objectArray) {
		super(context, R.layout.diet_list_row, objectArray);
		this.context = context;
		this.objectArray = objectArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.diet_list_row, parent,
					false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.id = (TextView) rowView.findViewById(R.id.tvDietID);
			viewHolder.feast = (TextView) rowView.findViewById(R.id.tvFeast);
			viewHolder.time = (TextView) rowView.findViewById(R.id.tvDietTime);
			viewHolder.menu = (TextView) rowView.findViewById(R.id.tvMenu);
			viewHolder.date = (TextView) rowView.findViewById(R.id.tvDietDate);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.showalarm);

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.id.setText(objectArray.get(position).getmDietId().toString());
		holder.feast.setText(objectArray.get(position).getmFeast());
		holder.time.setText(objectArray.get(position).getmTime());
		holder.menu.setText(objectArray.get(position).getmMenu());
		holder.date.setText(objectArray.get(position).getmDate());
		
		String str = objectArray.get(position).getmAlarm();
		if(str != null && str.equals("true")){
			 holder.image.setVisibility(View.VISIBLE);
		}
		
		return rowView;

	}

}
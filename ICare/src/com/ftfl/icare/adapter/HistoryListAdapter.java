package com.ftfl.icare.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.modelclass.HistoryModel;

public class HistoryListAdapter extends ArrayAdapter<HistoryModel> {
	private final Activity context;
	ArrayList<HistoryModel> objectArray;
	public String mImagePath = "";
	public String mImageID = "";
	Bitmap bitmap = null;

	static class ViewHolder {
		public TextView mHistoryID,mHistoryDate, mHistoryDoc, mHistoryReason, mHistorySugg;
		public ImageView mPrescription;
	}


	public HistoryListAdapter(Activity context,
			ArrayList<HistoryModel> objectArray) {
		super(context, R.layout.history_list_row, objectArray);
		this.context = context;
		this.objectArray = objectArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.history_list_row, parent,
					false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.mHistoryID = (TextView) rowView.findViewById(R.id.tvHistoryID);
			viewHolder.mHistoryDate = (TextView) rowView.findViewById(R.id.historydate);
			viewHolder.mHistoryDoc = (TextView) rowView.findViewById(R.id.historydoc);
			viewHolder.mHistoryReason = (TextView) rowView.findViewById(R.id.historyresone);
			viewHolder.mHistorySugg = (TextView) rowView.findViewById(R.id.historysugg);
			viewHolder.mPrescription = (ImageView) rowView.findViewById(R.id.precriptioniv);

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		mImageID = objectArray.get(position).getmPrescriptionId().toString();
		holder.mHistoryID.setText(mImageID);
		holder.mHistoryDate.setText(objectArray.get(position).getmPrescriptionDate());
		holder.mHistoryDoc.setText(objectArray.get(position).getmDoctorName());
		holder.mHistoryReason.setText(objectArray.get(position).getmPrescriptionReason());
		holder.mHistorySugg.setText(objectArray.get(position).getmDocSuggestion());
		mImagePath = objectArray.get(position).getmPrescription().toString();
		previewCapturedImage();
		holder.mPrescription.setImageBitmap(bitmap);

		return rowView;

	}
	
	/**
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage() {
		try {

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			bitmap = BitmapFactory.decodeFile(mImagePath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



}
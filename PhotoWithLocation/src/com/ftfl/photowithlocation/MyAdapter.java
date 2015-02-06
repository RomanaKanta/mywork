package com.ftfl.photowithlocation;

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

import com.ftfl.photowithlocation.modelclass.PhotoModelClass;

public class MyAdapter extends ArrayAdapter<PhotoModelClass> {
	Activity mContext = null;
	PhotoModelClass mPhoto = null;
	ArrayList<PhotoModelClass> mArrayObject = null;
	String mPhotoPath = "";
	Bitmap bitmap = null;

	public MyAdapter(Activity context,
			ArrayList<PhotoModelClass> objectArray) {
		super(context, R.layout.list_row, objectArray);
		this.mContext = context;
		this.mArrayObject = objectArray;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {
		public ImageView image = null;
		public TextView lati = null;
		public TextView longi = null;
		public TextView descrip = null;
		public TextView distance = null;
		public TextView id = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mPhoto = mArrayObject.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.list_row, parent, false);

			mVHolder = new ViewHolder();
			mVHolder.id = (TextView) rowView.findViewById(R.id.tvID);
			mVHolder.image = (ImageView) rowView.findViewById(R.id.showimg);
			mVHolder.lati = (TextView) rowView.findViewById(R.id.lat);
			mVHolder.longi = (TextView) rowView.findViewById(R.id.lon);
			mVHolder.descrip = (TextView) rowView.findViewById(R.id.discrip);
		//	mVHolder.distance = (TextView) rowView.findViewById(R.id.distance);
			
			rowView.setTag(mVHolder);
		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.id.setText(mPhoto.getmID().toString());
		previewCapturedImage();		
		mVHolder.image.setImageBitmap(bitmap);
		mVHolder.lati.setText(mPhoto.getmLatitude().toString());
		mVHolder.longi.setText(mPhoto.getmLongitude().toString());
		mVHolder.descrip.setText(mPhoto.getmDiscription().toString());
	//	mVHolder.lati.setText(mPhoto.getmLatitude().toString());
		
		return rowView;
	}
	
	/**
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage() {
		try {
			mPhotoPath= mPhoto.getmPath();
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 4;

			bitmap = BitmapFactory.decodeFile(mPhotoPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}

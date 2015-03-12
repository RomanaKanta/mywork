package com.ftfl.mymeetingplaces.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.mymeetingplaces.R;
import com.ftfl.mymeetingplaces.modelclass.ModelClass;

public class CustomeAdapter extends ArrayAdapter<ModelClass> {

	Activity mContext = null;
	ModelClass mPlace = null;
	ArrayList<ModelClass> mArrayObject = null;
	String mPath = "";
	Bitmap bitmap = null;
	double currentlatitude = 0;
	double currentlongitude = 0;

	public CustomeAdapter(Activity context, double elatitude, double elongitude,ArrayList<ModelClass> objectArray) {
		super(context, R.layout.list_row, objectArray);
		this.mContext = context;
		this.mArrayObject = objectArray;
		this.currentlatitude = elatitude;
		this.currentlongitude = elongitude;
	}                                            

	// holder Class to contain inflated xml file elements
	static class ViewHolder {
		public TextView id = null;
		public ImageView image = null;
		public TextView lati = null;
		public TextView longi = null;
		public TextView date = null;
		public TextView time = null;
		public TextView descrip = null;
		public TextView distance = null;

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
			mVHolder.image = (ImageView) rowView.findViewById(R.id.showimage);
			mVHolder.lati = (TextView) rowView.findViewById(R.id.showlat);
			mVHolder.longi = (TextView) rowView.findViewById(R.id.showlon);
			mVHolder.date = (TextView) rowView.findViewById(R.id.showdate);
			mVHolder.time = (TextView) rowView.findViewById(R.id.showtime);
			mVHolder.descrip = (TextView) rowView
					.findViewById(R.id.showdescrip);
			mVHolder.distance = (TextView) rowView
					.findViewById(R.id.setdistance);
			rowView.setTag(mVHolder);
		} else
			mVHolder = (ViewHolder) rowView.getTag();


		mVHolder.id.setText(mPlace.getmID().toString());
		mVHolder.lati.setText(mPlace.getmLatitude().toString());
		mVHolder.longi.setText(mPlace.getmLongitude().toString());
		mVHolder.date.setText(mPlace.getmDate().toString());
		mVHolder.time.setText(mPlace.getmTime().toString());
		mVHolder.descrip.setText(mPlace.getmDiscription().toString());
		mPath = mPlace.getmFileName();
		previewCapturedImage();
		mVHolder.image.setImageBitmap(bitmap);

		double mlati = Double.parseDouble(mPlace.getmLatitude());
		double mlongi = Double.parseDouble(mPlace.getmLongitude());
		
		// calculate distance
		Location imageLocation = new Location("Image Location");

		imageLocation.setLatitude(mlati);
		imageLocation.setLongitude(mlongi);

		Location currentLocation = new Location("Current Location");

		currentLocation.setLatitude(currentlatitude);
		currentLocation.setLongitude(currentlongitude);
		
		// get distance in kilometer
		double distance = imageLocation.distanceTo(currentLocation) / 1000;
		mVHolder.distance.setText(String.valueOf(distance).toString());
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

			bitmap = BitmapFactory.decodeFile(mPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}

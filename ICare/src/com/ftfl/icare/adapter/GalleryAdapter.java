package com.ftfl.icare.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class GalleryAdapter extends BaseAdapter {
	 private Context mContext;
	   public List<String> adapterImagePath = new ArrayList<String>();
	   public String imagePath;

	   // Constructor
	   public GalleryAdapter(Context c, List<String> eImagePath) {
	      mContext = c;
	      adapterImagePath = eImagePath;
	   }

	   public int getCount() {
	      return adapterImagePath.size();
	   }

	   public Object getItem(int position) {
	      return null;
	   }

	   public long getItemId(int position) {
	      return 0;
	   }

	   // create a new ImageView for each item referenced by the Adapter
	   public View getView(int position, View convertView, ViewGroup parent) {
	      ImageView imageView;
	      if (convertView == null) {
	      imageView = new ImageView(mContext);
	     imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
	      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	     imageView.setPadding(8, 8, 8, 8);
	     
	      } else {
	      imageView = (ImageView) convertView;
	      }

	     //imageView.setImageResource(mThumbIds[position]);
	      
	       imagePath =  adapterImagePath.get(position);
	      
	      DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(0)).cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///"+imagePath,imageView , options);
	      
	      
	      return imageView;
	   }


}
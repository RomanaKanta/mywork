package com.ftfl.icare;

import com.ftfl.icare.database.HistoryDataSource;
import com.ftfl.icare.modelclass.HistoryModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class PrescriptionViewActivity extends Activity {

	// Variable Declaration
	ImageView imageView = null;
	int mPrescriptionID = 0;
	String mImage = "";
	HistoryModel mHistoryModel = null;
	HistoryDataSource mHistoryDataSource = null;
	Bitmap bitmap = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription_view);

		// definition - gives variable a reference
		mHistoryDataSource = new HistoryDataSource(this);

		imageView = (ImageView) findViewById(R.id.PrescriptionView);
		Intent iIntent = getIntent();
		// Selected image id
		mPrescriptionID = iIntent.getExtras().getInt("id");

		mHistoryModel = mHistoryDataSource.getHistoryDetail(mPrescriptionID);
		mImage = mHistoryModel.getmPrescription();

		previewCapturedImage();
		imageView.setImageBitmap(bitmap);
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
			options.inSampleSize = 2;

			bitmap = BitmapFactory.decodeFile(mImage, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
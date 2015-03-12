package com.ftfl.icare.fragment;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.CareCenterAcitvity;
import com.ftfl.icare.DisplayDietChartActivity;
import com.ftfl.icare.DisplayDocProfileActivity;
import com.ftfl.icare.DisplayMediHistoryActivity;
import com.ftfl.icare.DisplayVaccinChartActivity;
import com.ftfl.icare.GalleryActivity;
import com.ftfl.icare.MainActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.UserProfileCreationActivity;
import com.ftfl.icare.modelclass.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Home extends Fragment {

	EditText input = null;
	TextView mNameTV = null;
	TextView mAgeTV = null;
	TextView mHeightTV = null;
	TextView mWeightTV = null;
	TextView mSpecialInformationTV = null;
	ImageView mUserImage = null;
	ImageView mEditProfile = null;
	UserModel mUser = null;
	TextView mDietTV = null;
	TextView mVaccinationTV = null;
	TextView mDoctorTV = null;
	TextView mMediHistoryTV = null;
	TextView mGalleryTV = null;
	TextView mCareCenterTV = null;
	TextView mEmergencyTV = null;
	String mEmergency = "";

	Bundle mBundle = null;
	String mImagePath = "";
	String chack = "";
	int mID = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	UserModel mUserModel = null;
	Bitmap bitmap = null;
	Gson gson;
	Editor prefsEditor;

	public Home() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		SharedPreferences prefs = getActivity().getSharedPreferences("ICARE",
				Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		// get object
		Gson gson = new Gson();
		String jsonProfile = prefs.getString("Profile", null);
		Type type = new TypeToken<UserModel>() {
		}.getType();
		mUserModel = gson.fromJson(jsonProfile, type);
		mYear = mUserModel.getmYear();
		mMonth = mUserModel.getmMonth();
		mDay = mUserModel.getmDay();
		mImagePath = mUserModel.getmPhoto();

		mEmergencyTV = (TextView) rootView.findViewById(R.id.emergence);
		mNameTV = (TextView) rootView.findViewById(R.id.username);
		mAgeTV = (TextView) rootView.findViewById(R.id.agetv);
		mHeightTV = (TextView) rootView.findViewById(R.id.httv);
		mWeightTV = (TextView) rootView.findViewById(R.id.wttv);
		mSpecialInformationTV = (TextView) rootView
				.findViewById(R.id.specialinfotv);
		mUserImage = (ImageView) rootView.findViewById(R.id.photoimageviev);
		mEditProfile = (ImageView) rootView.findViewById(R.id.proedit);

		mDietTV = (TextView) rootView.findViewById(R.id.dietTextView);
		mVaccinationTV = (TextView) rootView.findViewById(R.id.vccTextView);
		mDoctorTV = (TextView) rootView.findViewById(R.id.docprofileTextView);
		mMediHistoryTV = (TextView) rootView
				.findViewById(R.id.medicalhistoryTextView);
		mGalleryTV = (TextView) rootView.findViewById(R.id.galleryTextView);
		mCareCenterTV = (TextView) rootView
				.findViewById(R.id.carecenterTextView);

		mNameTV.setText(mUserModel.getmUserName());
		mAgeTV.setText(String.valueOf(this.getAge(mYear, mMonth, mDay)));
		mHeightTV.setText(mUserModel.getmHeight());
		mWeightTV.setText(mUserModel.getmWeight());
		mSpecialInformationTV.setText(mUserModel.getmSprcialInfo());
		previewCapturedImage();
		mUserImage.setImageBitmap(bitmap);

		chack = prefs.getString("emergency", "");

		mEmergencyTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!chack.equals("")) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							getActivity());

					// Setting "Delete" Button
					alertDialog.setPositiveButton("Call",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked item
									dialTheNumber();
									// Remove activity

								}
							});

					// Setting "Delete" Button
					alertDialog.setNegativeButton("Change Number",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked item
/******************************************************************************************/

									AlertDialog.Builder alert = new AlertDialog.Builder(
											getActivity());
									alert.setTitle("Set Emergency Number");
									alert.setMessage(chack);

									// Set an EditText view to get user input
									input = new EditText(getActivity());
									alert.setView(input);

									alert.setPositiveButton("Save",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int whichButton) {
													Editable value = input.getText();
													mEmergency = value.toString();
													if (mEmergency.length() < 11) {
														Toast.makeText(getActivity(),
																"Enter Number Correctly",
																Toast.LENGTH_LONG).show();
													} else {
														prefsEditor.putString("emergency",
																mEmergency);
														prefsEditor.commit();
														Toast.makeText(getActivity(),
																"inserted", Toast.LENGTH_LONG)
																.show();
														dialog.dismiss();
														Intent iIntent = new Intent(
																getActivity(),
																MainActivity.class);
														startActivity(iIntent);
														getActivity().finish();
													}
												}
											});

									// create alert dialog
									AlertDialog alertDia = alert.create();

									// show it
									alertDia.show();										
									
/*********************************************************************************************/

								}
							});

					// create alert dialog
					AlertDialog alert = alertDialog.create();

					// show it
					alert.show();

				} else {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							getActivity());
					alert.setTitle("Set Emergency Number");

					// Set an EditText view to get user input
					input = new EditText(getActivity());
					alert.setView(input);

					alert.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Editable value = input.getText();
									mEmergency = value.toString();
									if (mEmergency.length() < 11) {
										Toast.makeText(getActivity(),
												"Enter Number Correctly",
												Toast.LENGTH_LONG).show();
									} else {
										prefsEditor.putString("emergency",
												mEmergency);
										prefsEditor.commit();
										Toast.makeText(getActivity(),
												"inserted", Toast.LENGTH_LONG)
												.show();
										dialog.dismiss();
										Intent iIntent = new Intent(
												getActivity(),
												MainActivity.class);
										startActivity(iIntent);
										getActivity().finish();
									}
								}
							});

					// create alert dialog
					AlertDialog alertDia = alert.create();

					// show it
					alertDia.show();

				}
			}
		});

		mEditProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						UserProfileCreationActivity.class);
				startActivity(iIntent);
			}
		});

		mDietTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						DisplayDietChartActivity.class);
				startActivity(iIntent);
			}
		});
		mVaccinationTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						DisplayVaccinChartActivity.class);
				startActivity(iIntent);
			}
		});
		mDoctorTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						DisplayDocProfileActivity.class);
				startActivity(iIntent);
			}
		});
		mMediHistoryTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						DisplayMediHistoryActivity.class);
				startActivity(iIntent);
			}
		});
		mGalleryTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						GalleryActivity.class);
				startActivity(iIntent);
			}
		});
		mCareCenterTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iIntent = new Intent(getActivity(),
						CareCenterAcitvity.class);
				startActivity(iIntent);
			}
		});

		return rootView;
	}

	protected void dialTheNumber() {
		if (!chack.isEmpty()) {
			try {
				startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ chack)));
				Toast.makeText(getActivity(), "Finish Dialing",
						Toast.LENGTH_SHORT).show();
			} catch (Exception excp) {
				Toast.makeText(getActivity(), excp.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public int getAge(int year, int month, int day) {

		GregorianCalendar cal = new GregorianCalendar();
		int iYear, iMonth, iDay, iAge;

		iYear = cal.get(Calendar.YEAR);
		iMonth = cal.get(Calendar.MONTH);
		iDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day);
		iAge = iYear - cal.get(Calendar.YEAR);
		if ((iMonth < cal.get(Calendar.MONTH))
				|| ((iMonth == cal.get(Calendar.MONTH)) && (iDay < cal
						.get(Calendar.DAY_OF_MONTH)))) {
			--iAge;
		}
		if (iAge < 0)
			throw new IllegalArgumentException("Age < 0");
		return iAge;
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

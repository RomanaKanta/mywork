package com.ftfl.icare.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.DisplayDocProfileActivity;
import com.ftfl.icare.DoctorProfileCreationActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.DoctorDateSource;
import com.ftfl.icare.modelclass.DoctorModel;

public class ViewDoctorDetail extends Fragment {
	TextView mDocNameTV = null;
	TextView mSpecialistTV = null;
	TextView mMobileTV = null;
	TextView mEmailTV = null;
	TextView mAddressTV = null;
	TextView mAppointDateTV = null;
	TextView mAppointTimeTV = null;
	Button mNewAppointmentBtn = null;
	ImageView mEditDocIV = null;
	ImageView mPhoneIV = null;
	ImageView mEmailIV = null;
	ImageView mDeleteIV = null;
	Integer mID = 0;
	Editor prefsEditor;
	SharedPreferences prefs;
	String msID = "";
	String mSMsg = "";
	String mSEmail = "";
	DoctorModel mModel = null;
	DoctorDateSource mDoctorDateSource = null;

	public ViewDoctorDetail() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_view_doc_profile,
				container, false);
		prefs = getActivity().getSharedPreferences("ICARE",
				Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();

		msID = prefs.getString("doctorid", null);

		mID = Integer.parseInt(msID);

		mDocNameTV = (TextView) rootView.findViewById(R.id.docname);
		mSpecialistTV = (TextView) rootView.findViewById(R.id.viewspecialist);
		mMobileTV = (TextView) rootView.findViewById(R.id.viewdocphone);
		mEmailTV = (TextView) rootView.findViewById(R.id.viewdocemail);
		mAddressTV = (TextView) rootView.findViewById(R.id.viewdocaddress);
		mAppointDateTV = (TextView) rootView.findViewById(R.id.appointdate);
		mAppointTimeTV = (TextView) rootView.findViewById(R.id.appointtime);
		mNewAppointmentBtn = (Button) rootView.findViewById(R.id.doclocationbtn);
		mEditDocIV = (ImageView) rootView.findViewById(R.id.docedit);
		mPhoneIV = (ImageView) rootView.findViewById(R.id.calldoc);
		mEmailIV = (ImageView) rootView.findViewById(R.id.emaildoc);
		mDeleteIV = (ImageView) rootView.findViewById(R.id.docdelete);

		mDoctorDateSource = new DoctorDateSource(getActivity());
		mModel = mDoctorDateSource.getDoctorDetail(mID);

		mDocNameTV.setText(mModel.getmDocName());
		mSpecialistTV.setText(mModel.getmSpecialist());
		mMobileTV.setText(mModel.getmPhone());
		mEmailTV.setText(mModel.getmEmail());
		mAddressTV.setText(mModel.getmAddress());
		mAppointDateTV.setText(mModel.getmDate());
		mAppointTimeTV.setText(mModel.getmTime());

		// OnClick Activity of delete button
		mPhoneIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder callAlert = new AlertDialog.Builder(
						getActivity());

				// set dialog message
				callAlert.setPositiveButton("Call",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked item
								dialTheNumber();
							}
						}).setNegativeButton("SMS",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, just close

								// sms function
								AlertDialog.Builder alert = new AlertDialog.Builder(
										getActivity());
								alert.setTitle("SMS");
								alert.setMessage("Write Message");

								// Set an EditText view to get user input
								final EditText input = new EditText(
										getActivity());
								alert.setView(input);

								alert.setPositiveButton("Send",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												Editable value = input
														.getText();
												mSMsg = value.toString();
												sendSms();
											}
										});

								alert.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												// Canceled.
											}
										});
								alert.show();
							}
						});
				// create alert dialog
				AlertDialog alertDialog = callAlert.create();

				// show it
				alertDialog.show();
			}
		});

		mEmailIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendEmail();
			}
		});

		mEditDocIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prefsEditor.putString("updatedoctorid", msID);
				prefsEditor.commit();
				Intent iIntent = new Intent(getActivity(),
						DoctorProfileCreationActivity.class);
				startActivity(iIntent);
				getActivity().finish();
			}
		});

		mNewAppointmentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		// OnClick Activity of delete button
		mDeleteIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder iDeleteAlert = new AlertDialog.Builder(
						getActivity());

				// set dialog message
				iDeleteAlert
						.setMessage(R.string.deletealart)
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked item
										// will be deleted

										mDoctorDateSource.deleteDoctorData(mID);
										Toast.makeText(getActivity(),
												R.string.deletemsg,
												Toast.LENGTH_LONG).show();

										// go back to review activity after
										// delete operation
										Intent iIntent = new Intent(
												getActivity(),
												DisplayDocProfileActivity.class);
										startActivity(iIntent);

										// Remove activity
										getActivity().finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
				// create alert dialog
				AlertDialog alertDialog = iDeleteAlert.create();

				// show it
				alertDialog.show();
			}
		});
		return rootView;
	}

	protected void dialTheNumber() {
		if (!mMobileTV.getText().toString().isEmpty()) {
			try {
				startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ mMobileTV.getText())));
				Toast.makeText(getActivity(), "Finish Dialing",
						Toast.LENGTH_SHORT).show();
			} catch (Exception excp) {
				Toast.makeText(getActivity(), excp.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected void sendSms() {
		String sendNumber = mMobileTV.getText().toString();
		if (sendNumber != null && mSMsg != null) {
			try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(sendNumber, null, mSMsg, null, null);
				Toast.makeText(getActivity(), "SMS Sent", Toast.LENGTH_SHORT)
						.show();
			} catch (Exception exc) {
				Toast.makeText(getActivity(), "Send Failed", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public void sendEmail() {

		mSEmail = mEmailTV.getText().toString();
		Intent emailIntent = new Intent(Intent.ACTION_SEND,
				Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mSEmail });
		emailIntent.putExtra(Intent.EXTRA_CC, "");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "");
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Send Mail"));
	}
}

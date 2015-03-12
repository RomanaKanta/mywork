package com.ftfl.mymeetingplaces;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.database.PlaceDataSource;
import com.ftfl.mymeetingplaces.modelclass.ModelClass;

public class ViewActivity extends ActionBarActivity implements OnItemSelectedListener {

	// Variable Declaration
	int mID = 0;
	ImageView mIVImage = null;
	TextView mTVName = null;
	TextView mTVEmail = null;
	TextView mTVMobile = null;
	TextView mTVDate = null;
	TextView mTVTime = null;
	TextView mTVDesdription = null;
	TextView mTVLatitude = null;
	TextView mTVLongitude = null;
	ImageView mIBemail = null;
	ImageView mIBCall = null;
	ImageView mIBSms = null;
	ImageView mIBAddCon = null;
	Button mEdit = null;
	Button mDelete = null;
	Bundle mBundle = null;
    Spinner mSpinner = null;
    
	String mSName = "";
	String mSEmail = "";
	String mSMobile = "";
	String mSMsg = "";
	String mSImagePath = "";
	String mSDescription = "";
	String mSLat = "";
	String mSLng = "";
	String mSDate = "";
	String mSTime = "";
	Bitmap bitmap = null;
	ModelClass mViewDetail = null;
	PlaceDataSource mPlaceSource = null;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		// definition - gives variable a reference
		mIVImage = (ImageView) findViewById(R.id.viewimg);
		mTVName = (TextView) findViewById(R.id.viewnameTextView);
		mTVEmail = (TextView) findViewById(R.id.viewemailTextView);
		mTVMobile = (TextView) findViewById(R.id.viewmobileTextView);
		mTVDate = (TextView) findViewById(R.id.viewdateTextView);
		mTVTime = (TextView) findViewById(R.id.viewtimeTextView);
		mTVDesdription = (TextView) findViewById(R.id.viewdispTextView);
		mTVLatitude = (TextView) findViewById(R.id.viewlatTextView);
		mTVLongitude = (TextView) findViewById(R.id.viewnlongiTextView);
		mIBemail = (ImageView) findViewById(R.id.emailIB);
		mIBCall = (ImageView) findViewById(R.id.callIB);
		mIBSms = (ImageView) findViewById(R.id.smsIB);
		mIBAddCon = (ImageView) findViewById(R.id.addcIB);
		mEdit = (Button) findViewById(R.id.edit);
		mDelete = (Button) findViewById(R.id.delete);
		mPlaceSource = new PlaceDataSource(this);

		// get the Intent that started update Activity
		Intent mIntent = getIntent();

		// get the Bundle that stores the data of update Activity
		mBundle = mIntent.getExtras();

		if (mBundle != null) {
			mID = mBundle.getInt("id");

			if (mID > 0) {
				mViewDetail = mPlaceSource.getDetail(mID);
				init();
			}
		}
		
				mIBemail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendEmail();
			}
		});

		mIBAddCon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addToContact();
			}
		});

		mIBCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialTheNumber();
			}
		});

		mIBSms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("SMS");
				alert.setMessage("Write Message");

				// Set an EditText view to get user input
				final EditText input = new EditText(context);
				alert.setView(input);

				alert.setPositiveButton("Send",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Editable value = input.getText();
								mSMsg = value.toString();
								sendSms();
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();
			}
		});

		// OnClick Activity of edit button
		mEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Creating Bundle object
				Bundle mBundle = new Bundle();

				// put id into bundle
				mBundle.putInt("id", mID);
				Intent mIntent = new Intent(getBaseContext(),
						EditActivity.class);

				// Storing bundle object into intent
				mIntent.putExtras(mBundle);
				startActivity(mIntent);

				// Remove activity
				finish();
			}
		});
		// OnClick Activity of delete button
		mDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder iDeleteAlert = new AlertDialog.Builder(
						context);

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
										mPlaceSource.deleteData(mID);
										Toast.makeText(getApplicationContext(),
												R.string.deletemsg,
												Toast.LENGTH_LONG).show();

										// go back to review activity after
										// delete operation
										Intent mIntent = new Intent(
												getBaseContext(),
												ReviewActivity.class);
										startActivity(mIntent);

										// Remove activity
										finish();
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
	}

	public void init() {
		// set values in view
		mTVName.setText(mViewDetail.getmName());
		mTVEmail.setText(mViewDetail.getmEmail());
		mTVMobile.setText(mViewDetail.getmMobile());
		mTVDate.setText(mViewDetail.getmDate());
		mTVTime.setText(mViewDetail.getmTime());
		mTVDesdription.setText(mViewDetail.getmDiscription());
		mTVLatitude.setText(mViewDetail.getmLatitude());
		mTVLongitude.setText(mViewDetail.getmLongitude());
		mSImagePath = mViewDetail.getmFileName();
		previewCapturedImage();
		mIVImage.setImageBitmap(bitmap);
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

			bitmap = BitmapFactory.decodeFile(mSImagePath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	protected void dialTheNumber() {
		if (!mTVMobile.getText().toString().isEmpty()) {
			try {
				startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ mTVMobile.getText())));
				Toast.makeText(getApplicationContext(), "Finish Dialing",
						Toast.LENGTH_SHORT).show();
			} catch (Exception excp) {
				Toast.makeText(getApplicationContext(), excp.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected void sendSms() {
		String sendNumber = mTVMobile.getText().toString();
		if (sendNumber != null && mSMsg != null) {
			try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(sendNumber, null, mSMsg, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent",
						Toast.LENGTH_SHORT).show();
			} catch (Exception exc) {
				Toast.makeText(getApplicationContext(), "Send Failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void addToContact() {

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();

		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		ops.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				// Name of the person
				.withValue(StructuredName.DISPLAY_NAME, mViewDetail.getmName())
				.build());
		ops.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				// Number of the person
				.withValue(Phone.NUMBER, mViewDetail.getmMobile())
				// Type of mobile number
				.withValue(Phone.TYPE, Phone.TYPE_MOBILE).build());
		try {
			@SuppressWarnings("unused")
			ContentProviderResult[] res = getContentResolver().applyBatch(
					ContactsContract.AUTHORITY, ops);

			Toast.makeText(getApplicationContext(),
					"Successfully  Contract Added !!!!!!!", Toast.LENGTH_LONG)
					.show();
		} catch (RemoteException e) {
			// error
		} catch (OperationApplicationException e) {
			// error
		}
		Intent contacts = new Intent(Intent.ACTION_VIEW,
				ContactsContract.Contacts.CONTENT_URI);
		startActivity(contacts);
	}

	public void sendEmail() {

		mSEmail = mTVEmail.getText().toString();
		Intent emailIntent = new Intent(Intent.ACTION_SEND,
				Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] { mSEmail});
		emailIntent.putExtra(Intent.EXTRA_CC, "");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "");
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Send Mail"));
		finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		int item = (int) parent.getItemAtPosition(position);
		
		if(item ==R.drawable.call){
			dialTheNumber();
		}
		if(item ==R.drawable.mail){
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle("SMS");
			alert.setMessage("Write Message");

			// Set an EditText view to get user input
			final EditText input = new EditText(context);
			alert.setView(input);

			alert.setPositiveButton("Send",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Editable value = input.getText();
							mSMsg = value.toString();
							sendSms();
						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});

			alert.show();
		}
		if(item == R.drawable.addressbook){
			addToContact();
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}

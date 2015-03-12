package com.ftfl.icare.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.modelclass.NoteModel;

public class NoteAdapter extends ArrayAdapter<NoteModel> {
	Activity mContext;
	NoteModel mNoteModel;
	ArrayList<NoteModel> mArrayObject;

	public NoteAdapter(Activity context,
			ArrayList<NoteModel> objectArray) {
		super(context, R.layout.note_list_row, objectArray);
		this.mContext = context;
		this.mArrayObject = objectArray;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {
		public TextView name;
		public TextView startDate;
		public TextView id;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mNoteModel = mArrayObject.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.note_list_row, parent, false);

			mVHolder = new ViewHolder();
			mVHolder.id = (TextView) rowView.findViewById(R.id.tvNoteID);
			mVHolder.name = (TextView) rowView.findViewById(R.id.tvShowNote);
			mVHolder.startDate = (TextView) rowView
					.findViewById(R.id.tvShowDate);
			rowView.setTag(mVHolder);
		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.id.setText(mNoteModel.getmNoteId().toString());
		mVHolder.startDate.setText(mNoteModel.getmDate().toString());
		mVHolder.name.setText(mNoteModel.getmNote().toString());

		return rowView;
	}
}
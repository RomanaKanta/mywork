package com.ftfl.icare.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftfl.icare.R;

public class VaccinInfo extends Fragment {
	
	public VaccinInfo(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_vaccin_info, container, false);
         
        return rootView;
    }
}
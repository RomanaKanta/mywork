package com.ftfl.icare.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftfl.icare.R;

public class GrowthInfo extends Fragment {
	
	public GrowthInfo(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_growth_info, container, false);
         
        return rootView;
    }
}
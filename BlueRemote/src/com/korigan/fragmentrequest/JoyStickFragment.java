package com.korigan.fragmentrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korigan.blueremote.R;

public class JoyStickFragment extends AbstractRequestFragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.joystick_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private void initializeUI(){
		
	}
}

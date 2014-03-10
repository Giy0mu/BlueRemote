package com.korigan.fragmentrequest;

import com.korigan.blueremote.R;
import com.korigan.request.SleepRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SleepFragment extends AbstractRequestFragment{
	
	private Button mButSleep;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.sleep_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private void initializeUI() {
		mButSleep = (Button) mRoot.findViewById(R.id.butSleep);
		mButSleep.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SleepRequest req = new SleepRequest();
				mBTCommunication.write(req);			
			}
		});
	}
	
}

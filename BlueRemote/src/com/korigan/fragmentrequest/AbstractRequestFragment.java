package com.korigan.fragmentrequest;

import com.korigan.blueremote.BTCommunication;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

public abstract class AbstractRequestFragment extends Fragment {
	protected View mRoot;
	protected BTCommunication mBTCommunication;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mBTCommunication = BTCommunication.getInstance();
	}
}

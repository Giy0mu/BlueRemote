package com.korigan.fragmentrequest;

import com.korigan.blueremote.R;
import com.korigan.request.ClickRequest;
import com.korigan.request.KeyRequest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AltTabFragment extends AbstractRequestFragment {
	private Button mAltTab;
	private Button mTab;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.alttab_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private void initializeUI() {
		mAltTab = (Button) mRoot.findViewById(R.id.butAltTab);		
		mAltTab.setOnClickListener(new OnClickListener(){
			private byte mState = 1;
			@Override
			public void onClick(View v) {
				KeyRequest req = new KeyRequest();
				if(mState == 0) mState = 1;
				else mState = 0;
				req.setData(KeyEvent.KEYCODE_ALT_LEFT, mState);
				mBTCommunication.write(req);
			}
		});
		
		mTab = (Button) mRoot.findViewById(R.id.butTab);
		mTab.setOnTouchListener(new OnTouchListener(){
			private byte mState = 1;
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				KeyRequest req = new KeyRequest();
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){
					mState = 0;
				}
				else if(arg1.getAction() == MotionEvent.ACTION_UP){
					mState = 1;
				}
				req.setData(KeyEvent.KEYCODE_TAB, mState);
				mBTCommunication.write(req);
				return false;
			}
		});	
	}
}

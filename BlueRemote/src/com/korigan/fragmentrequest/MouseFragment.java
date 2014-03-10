package com.korigan.fragmentrequest;

import com.korigan.blueremote.R;
import com.korigan.request.ClickRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MouseFragment extends AbstractRequestFragment{
	private Button mButClick;
	private Button mButClick2;
	private Button mButScrollUp;
	private Button mButScrollDown;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.mouse_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private void initializeUI() {
		mButClick = (Button) mRoot.findViewById(R.id.butClick);
		mButClick.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ClickRequest req = new ClickRequest();
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){
					req.setPressed(ClickRequest.LEFT);
				}
				else if(arg1.getAction() == MotionEvent.ACTION_UP){
					req.setReleased(ClickRequest.LEFT);
				}
				mBTCommunication.write(req);
				return false;
			}
		});	
		
		mButClick2 = (Button) mRoot.findViewById(R.id.butClick2);
		mButClick2.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ClickRequest req = new ClickRequest();
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){
					req.setPressed(ClickRequest.RIGHT);
				}
				else if(arg1.getAction() == MotionEvent.ACTION_UP){
					req.setReleased(ClickRequest.RIGHT);
				}
				mBTCommunication.write(req);
				return false;
			}
		});	
		
		mButScrollUp = (Button) mRoot.findViewById(R.id.butScrollUp);
		mButScrollUp.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ClickRequest req = new ClickRequest();
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){
					req.setPressed(ClickRequest.MIDDLE); //TODO
				}
				else if(arg1.getAction() == MotionEvent.ACTION_UP){
					req.setReleased(ClickRequest.MIDDLE); //TODO
				}
				mBTCommunication.write(req);
				return false;
			}
		});	
		
		mButScrollDown = (Button) mRoot.findViewById(R.id.butScrollDown);
		mButScrollDown.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				ClickRequest req = new ClickRequest();
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){
					req.setPressed(ClickRequest.MIDDLE); //TODO
				}
				else if(arg1.getAction() == MotionEvent.ACTION_UP){
					req.setReleased(ClickRequest.MIDDLE); //TODO
				}
				mBTCommunication.write(req);
				return false;
			}
		});	
	}
}

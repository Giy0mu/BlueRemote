package com.korigan.fragmentrequest;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import com.korigan.blueremote.BTCommunication;
import com.korigan.blueremote.R;
import com.korigan.request.AbstractRequest;
import com.korigan.request.ClickRequest;
import com.korigan.request.MotionRequest;

public class TouchPadFragment extends AbstractRequestFragment {
	
	private SurfaceView mTouchPadView;
	private GestureDetector mGestureDetector;
	private AbstractRequest mReq ;
	private BTCommunication mBTCommunication;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.touchpad_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private void initializeUI(){
		mGestureDetector = new GestureDetector(getActivity(), new GestureListener());
		mBTCommunication = BTCommunication.getInstance();
		mTouchPadView = (SurfaceView) mRoot.findViewById(R.id.surfTouchPad);
		mTouchPadView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 return mGestureDetector.onTouchEvent(event);
			}
		});
		
	}
	
	private class GestureListener extends SimpleOnGestureListener{
		
		@Override
		public boolean onDown(MotionEvent e) {
		    return true;        
		}

		@Override
		public void onLongPress(MotionEvent e) {
			mReq = new ClickRequest();
        	((ClickRequest) mReq).setPressed(ClickRequest.LEFT);
        	mBTCommunication.write(mReq);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			mReq = new ClickRequest();
        	((ClickRequest) mReq).setPressed(ClickRequest.LEFT);
        	mBTCommunication.write(mReq);
        	((ClickRequest) mReq).setReleased(ClickRequest.LEFT); //TODO doesnt work
        	mBTCommunication.write(mReq);
        	return true;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
			mReq = new MotionRequest();
			((MotionRequest)mReq).setData(distanceX, distanceY);
			mBTCommunication.write(mReq);
			return true;
		}
		
        @Override
        public boolean onDoubleTap(MotionEvent e) {
        	mReq = new ClickRequest();
        	((ClickRequest) mReq).setReleased(ClickRequest.RIGHT);
        	mBTCommunication.write(mReq);
            return true;
        }
		
	}

}

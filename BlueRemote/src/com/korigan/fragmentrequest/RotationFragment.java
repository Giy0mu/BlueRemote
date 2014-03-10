package com.korigan.fragmentrequest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.korigan.blueremote.R;
import com.korigan.request.MotionRequest;

public class RotationFragment extends AbstractRequestFragment implements SensorEventListener {
	
	private Button mButRotation;
	private boolean mIsOn = false;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private float[] mPrevValues = new float[4];
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		mRoot = inflater.inflate(R.layout.rotation_layout, container, false); 
		initializeUI();
		return mRoot; 
    }
	
	private  SensorEventListener getSensorEventListener(){
		return this;
	}
	
	private void initializeUI() {
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if(mSensor == null){
			Log.e("RotationFragment", "This sensor isnt supported");
		}
		
		
		mButRotation = (Button) mRoot.findViewById(R.id.butRotation);
		mButRotation.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				mIsOn = !mIsOn;
				if(mIsOn){
					mSensorManager.registerListener(getSensorEventListener(), mSensor, SensorManager.SENSOR_DELAY_UI);
				}
				else{
					mSensorManager.unregisterListener(getSensorEventListener());
				}
			}
		});
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
	
	private float deltaX;
	private float deltaY;
	private float deltaZ;
	private float deltaT;
	private MotionRequest mReq = new MotionRequest();
	
	@Override
	public void onSensorChanged(SensorEvent event) { //TODO capacity to remove listener
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
			Log.e("RotationFragment", String.valueOf(event.sensor.getType()));
            return;
		}
//		Log.e("RotationFragment", "Sensor changed");
		if(mIsOn){
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				Log.e("RotationFragment", "Sensor will be sent");
//				deltaX = mPrevValues[0] - event.values[0]; 
//				mPrevValues[0] = event.values[0];
//				deltaY = mPrevValues[1] - event.values[1]; 
//				mPrevValues[1] = event.values[1];
//				deltaZ = mPrevValues[2] - event.values[2]; 
//				mPrevValues[2] = event.values[2];
//				deltaT = mPrevValues[3] - event.values[3]; 
//				mPrevValues[3] = event.values[3];
				
				
//				mReq.setData(deltaX, deltaY, deltaZ, deltaT);
				mReq.setData(0, 0);
				mBTCommunication.write(mReq);
			}
		}
		
	}
}

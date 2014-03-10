package com.korigan.request;

import java.nio.ByteBuffer;

import android.util.Log;

public class MotionRequest extends AbstractRequest{
	
	public MotionRequest(){
		mData = ByteBuffer.allocate(11);
		mData.put(STX);
		mData.put((byte) 0x06);
		mData.putFloat(0);
		mData.putFloat(0);
		mData.put(ETX);
	}
	
	public void setData(float dx, float dy){
		//TODO find x and y from data
		Log.e("MotionRequest", "SetData");
		int dxr = Math.round(dx);
		int dyr = Math.round(dy);
		mData.putInt(2, dxr);
		mData.putInt(6, dyr);
	}
	
}

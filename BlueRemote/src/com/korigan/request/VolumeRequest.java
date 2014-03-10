package com.korigan.request;

import java.nio.ByteBuffer;

public class VolumeRequest extends AbstractRequest  {
	
	public VolumeRequest(){
		mData = ByteBuffer.allocate(4);
		mData.put(STX);
		mData.put((byte) 0x02);
		mData.put((byte) 0);
		mData.put(ETX);
	}
	
	public void setVolume(byte vol){
		mData.put(2, vol);
	}
	public VolumeRequest(byte vol){
		this();
		setVolume(vol);
	}
}

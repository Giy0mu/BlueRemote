package com.korigan.request;

import java.nio.ByteBuffer;

public class SmsRequest extends AbstractRequest {
	public SmsRequest(){
		mData = ByteBuffer.allocate(11); //TODO good allocation
		mData.put(STX);
		mData.put((byte) 0x00);
		mData.putInt(0); //Size of body
		mData.putFloat(0);
		mData.put(ETX);
	}
	
	public void setMsg(String from, String msgBody){
		//TODO
	}

}

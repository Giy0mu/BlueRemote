package com.korigan.request;

import java.nio.ByteBuffer;

public class SleepRequest extends AbstractRequest {
	
	public SleepRequest(){
		mData = ByteBuffer.allocate(4);
		mData.put(STX);
		mData.put((byte) 0x01);
		mData.put((byte) 0x00);
		mData.put(ETX);
	}
}

package com.korigan.request;

import java.nio.ByteBuffer;

public class ClickRequest extends AbstractRequest{
	public static final byte RIGHT = 0;
	public static final byte LEFT = 1;
	public static final byte MIDDLE = 2;
	private byte flag;
	
	public ClickRequest(){
		flag = 0;
		mData = ByteBuffer.allocate(5);
		mData.put(STX);
		mData.put((byte) 0x03);
		mData.put((byte) flag);
		mData.put((byte) 0); //TODO
		mData.put(ETX);
	}
	
	public void setPressed(byte button){
		flag = 0;
		mData.put(2, flag);
		mData.put(3, button);
	}
	
	public void setReleased(byte button){
		flag = 1;
		mData.put(2, flag);
		mData.put(3, button);
	}
}

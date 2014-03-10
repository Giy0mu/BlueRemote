package com.korigan.request;

import java.nio.ByteBuffer;

public abstract class AbstractRequest {
	public final byte STX = 0x02;
	public final byte ETX = 0x03;
	public final byte HEADER_SIZE = 2;
	public final byte FOOTER_SIZE = 1;
	protected ByteBuffer mData = null;
	
	public ByteBuffer serialize(){
		return mData;
	}
}

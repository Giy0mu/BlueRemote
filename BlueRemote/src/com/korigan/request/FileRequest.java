package com.korigan.request;

import java.nio.ByteBuffer;

public class FileRequest extends AbstractRequest {
	
	public FileRequest(){
		mData = ByteBuffer.allocate(4);
		putHeader();
		mData.put((byte) 0);
		putFooter();
	}
	
	public FileRequest(String path){
		setPath(path);
	}
	
	public void setPath(String path){
		byte[] pathByte = path.getBytes();
		byte length = (byte) pathByte.length; //TODO check it's not > 255
		mData = ByteBuffer.allocate(HEADER_SIZE+1+length+FOOTER_SIZE);
		putHeader();
		mData.put(length);
		mData.put(pathByte);
		putFooter();
	}
	
	private void putHeader(){
		mData.put(STX);
		mData.put((byte) 0x04);
	}
	
	private void putFooter(){
		mData.put(ETX);
	}
	
	
}

package com.korigan.blueremote;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.util.Pair;

public class ConnectThread extends Thread{
	
	private final String TAG = "ConnectThread";
	protected BluetoothSocket mmSocket;
    protected String mSocketType;
    protected final UUID mUUID;
    
    private final int BUFFER_WRITE_MAX_SIZE = 100;
	private final int R_SIZE = 4; //TODO
	private final int W_SIZE = 12; //TODO
	
	private InputStream mInStream;
	private OutputStream mOutStream;
    
    private boolean mIsRunning = true;
    
    private ArrayBlockingQueue<Pair<ByteBuffer, Integer> > mBufferWQueue = null;
    
    public ConnectThread(BluetoothDevice device){
    	mSocketType = "Secure"; //TODO no idea what it changes
        mUUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");        
        mBufferWQueue = new ArrayBlockingQueue<Pair<ByteBuffer, Integer> >(BUFFER_WRITE_MAX_SIZE);
        setDevice(device);
    }
	
	private void setDevice(BluetoothDevice device) {
		BluetoothSocket tmp = null;
        try {
        	tmp = device.createRfcommSocketToServiceRecord(mUUID);
        }
        catch (IOException e) {
        	Log.e("ConnectThread", "createSocketToServiceRecord Exception");
        }
        mmSocket = tmp;
		
	}

	public synchronized void write(byte[] buffer, int length){
		if(!mBufferWQueue.offer(new Pair<ByteBuffer, Integer>(ByteBuffer.wrap(buffer), length))){
			Log.e(TAG, "Error: Impossible to add more request in Buffer queue (Graphic)");
		}
	}
	
	public void run(){
		// Make a connection to the BluetoothSocket
        try {
			mmSocket.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
        // Start the process thread
        setSocketInfo(mmSocket, mSocketType);


        int commandSize = 0;
		byte[] bufferR = new byte[R_SIZE];
		byte[] bufferW = new byte[W_SIZE];
		Pair<ByteBuffer,Integer> mCurPair = null;
		
		while(mIsRunning){
			//Fill buffer to be sent
			
			try {
				mCurPair = mBufferWQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				break;
			}
			bufferW = mCurPair.first.array();
			int sendSize = mCurPair.second;
			
			//Send buffer
			try {
				mOutStream.write(bufferW, 0, sendSize);
			} 
			catch (InterruptedIOException e){
				e.printStackTrace();
				Thread.currentThread().interrupt();
				stopRunning();
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
				stopRunning();
				break;
			}
			
			//Wait and read the answer
			try {
				commandSize = mInStream.read(bufferR, 0, R_SIZE);
			} 
			catch (InterruptedIOException e){
				e.printStackTrace();
				Thread.currentThread().interrupt();
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			if(commandSize != -1){
				//Check the answer
				readAnswer(bufferR);
			}
			else{
				mIsRunning = false;
			}
		}
		try {
			mOutStream.close();
			mInStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e("BTCommunication", "end of communication");
	}
	
	public void setSocketInfo(BluetoothSocket socket, String socketType){
        super.setPriority(MAX_PRIORITY);	        
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        // Get the BluetoothSocket input and output streams
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "temp sockets not created", e);
        }

        mInStream = tmpIn;
        mOutStream = tmpOut;
    }
	
	private int readAnswer(byte[] buffer){
		//TODO
		return 0;
	}
	
	public void stopRunning(){
		Log.e("BTCommunication", "stopRunning");
		mIsRunning = false;
	}
}

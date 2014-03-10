package com.korigan.blueremote;

import com.korigan.request.AbstractRequest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class BTCommunication {
	@SuppressWarnings("unused")
	private String TAG = "BTCommunication";
	
	protected int mState;
	protected int mConnectingState;
	protected BluetoothAdapter mAdapter;
	protected Handler mHandler;
	
	
    // Constants that indicate the current connection state
    public static final int STATE_SELECTING = 5;
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int STATE_DISCONNECTED = 4; 
    
    public static final int NONE_CONNECTED = 0;
    public static final int GRAPH_CONNECTED = 1; 
    public static final int SENSOR_CONNECTED = 2; 
    public static final int REQUEST_CONNECTED = 3; 
    
    private Context mContext;
    private String mDeviceName;
    private static BTCommunication mThis;
	
    private BTCommunication(){
    }
    
	public static BTCommunication getInstance(){
		if(mThis == null){
			mThis = new BTCommunication();
		}
		return mThis;
	}
	
	public void init(Context context, BluetoothAdapter adapter, Handler handler){
		mContext = context;
		mAdapter = adapter;
		mState = STATE_DISCONNECTED;
		mConnectingState = NONE_CONNECTED;
		mHandler = handler;
		
		Intent serviceIntent = new Intent(mContext, BlueLinkService.class);
		mContext.startService(serviceIntent);
		doBindService();
		
	}
	
	public void connect(BluetoothDevice mBluetoothDevice) {
		setState(STATE_CONNECTING, NONE_CONNECTED);
		mDeviceName = mBluetoothDevice.getName();
		
		Message msg = Message.obtain(null,BlueLinkService.MSG_START_CONNECTION);
		msg.obj = mBluetoothDevice;
		
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
      
		setState(STATE_CONNECTED, -1);
        mAdapter.cancelDiscovery();
		
	}
	
	public void disconnect() {	
		if(mState != STATE_DISCONNECTED){
			setState(STATE_DISCONNECTED, -1);
			Message msg = Message.obtain(null,BlueLinkService.MSG_STOP_CONNECTION);
            try {
				mService.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void setState(int state, int substate) {
		int arg2 = -1;
	
		
       	mState = state;
       	if(mState == STATE_DISCONNECTED){
       		mConnectingState = NONE_CONNECTED;
       	}
       	if(mState == STATE_CONNECTING){
       		arg2 = substate;
       	}
       	Message m = mHandler.obtainMessage(BTConnectFragment.MESSAGE_STATE_CHANGE, state, arg2);
       	if(mState == STATE_CONNECTED){
       		Bundle data = new Bundle();
       		data.putString(BTConnectFragment.DEVICE_NAME, mDeviceName);
       		m.setData(data);
       	}
       	m.sendToTarget();
	}
	
	public synchronized void write(AbstractRequest req){
		Message msg = Message.obtain(null,BlueLinkService.MSG_ADD_OBJECT_TO_SEND);
		msg.obj = req;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	//Service stuff
	/** Messenger for communicating with service. */
	Messenger mService = null;
	/** Flag indicating whether we have called bind on the service. */
	boolean mIsBound;
	
	/**
	 * Class for interacting with the main interface of the service.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className,
	            IBinder service) {
	        mService = new Messenger(service);
	        Log.e("IncomingHandler", "Attached.");
	        try {
	            Message msg = Message.obtain(null,
	            		0);
	            mService.send(msg);
	        } catch (RemoteException e) {
	        }

	        // As part of the sample, tell the user what happened.
	        Toast.makeText(mContext, "remote service connected",
	                Toast.LENGTH_SHORT).show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        mService = null;
	        Log.e("IncomingHandler", "Disconnected.");

	        // As part of the sample, tell the user what happened.
	        Toast.makeText(mContext, "remote service disconnected",
	                Toast.LENGTH_SHORT).show();
	    }
	};
	
	private void doBindService() {
		mContext.bindService(new Intent(mContext, 
	    		BlueLinkService.class), mConnection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT);
	    mIsBound = true;
	    Log.e("IncomingHandler", "Binding.");
	}

	private void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	    	mContext.unbindService(mConnection);
	        mIsBound = false;
	        Log.e("IncomingHandler", "Unbinding.");
	    }
	}
	
	public void stopService(){
		doUnbindService();
		Intent serviceIntent = new Intent(mContext, BlueLinkService.class);
		mContext.stopService(serviceIntent);
	}
}

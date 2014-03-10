package com.korigan.blueremote;

import com.korigan.activities.ConnectionCallbacks;
import com.korigan.preferences.PreferencesManager;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class BTConnectFragment extends Fragment {
	
	private static TextView mHomeTextView;
	private static SurfaceView mSurfGraphCo;
	private BluetoothDevice mBluetoothDevice;
	

//	private static ProgressBar mProgressBar;
	
	private static Context mCtx;
	private static View mRoot;
	
	private static BTCommunication mBTCommunication;
	private static BluetoothAdapter mBTAdapter;
    
	public final static int DISCONNECTED = 0;
	public final static int CONNECTING = 1;
	public final static int CONNECTED = 2;

//	private static Handler mActivityHandler = null;
	
	private PreferencesManager mPrefManager;

	@Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);                   
        //EPW Interface setup
        mCtx = getActivity();    
        mBTCommunication = BTCommunication.getInstance();

        //********************	  
        
        //Check preferences
//        mSettings = mCtx.getSharedPreferences(PREF_FILE, 0);
//        String preferedDevice = mSettings.getString("device", null);
//        if(preferedDevice != null){
//        	mPreferedDeviceSelected = true;
//        	mPreferedDevice = preferedDevice;
//        }
//        else{
//        	mPreferedDeviceSelected = false;
//        }
        mPrefManager = PreferencesManager.getInstance();
        //*****************
    }
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	private static ConnectionCallbacks mConnectionCallbacks;
	
	public void registerCallbacks(ConnectionCallbacks callbacks){
		mConnectionCallbacks = callbacks;
	}
	
//	public void registerHandler(Handler handler){
//		mActivityHandler = handler;
//	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mRoot = inflater.inflate(R.layout.fragment_layout, container, false);
		
//		mProgressBar = (ProgressBar) mRoot.findViewById(R.id.progressBar1);
		mHomeTextView = (TextView) mRoot.findViewById(R.id.textState1);
		mSurfGraphCo = (SurfaceView) mRoot.findViewById(R.id.surfGraphCo);
    	
    	//update widgets
    	mHomeTextView.setText("Disconnected");
//   		mProgressBar.setVisibility(View.GONE);
   		if(mPrefManager.isServerChosen()){
   			scan();
   		}
		return mRoot;
    }
	
	public void disconnect(){
		mBTCommunication.disconnect();
	}
	
	private void scan(){
    	// Indicate scanning in the title
    	getActivity().setProgressBarIndeterminateVisibility(true);
        discovery(mReceiver);
	}
	
	/**
	 * Start Bluetooth adapter and devices discovery
	 * @param receiver
	 * @return 0 if OK, an EPWError otherwise
	 */
	public int discovery(BroadcastReceiver receiver){
		//Get Bluetooth Adapter and check it
		mBTAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBTAdapter == null){
			//This device does not support Bluetooth
			Log.e("EPWInterface", "Bluetooth not supported");
			return -1;
		}
		//******************************************

		mBTCommunication.init(getActivity(), mBTAdapter, mHandler);

		//Register the BroadcastReceiver (See below)
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mCtx.registerReceiver(receiver, filter); // Don't forget to unregister during onDestroy
				
		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mCtx.registerReceiver(receiver, filter);
		//******************************************
		
		// If we're already discovering, stop it
        if (mBTAdapter.isDiscovering()) {
        	mBTAdapter.cancelDiscovery();
        }
        // Request discover from BluetoothAdapter
        mBTAdapter.startDiscovery();
        mBTCommunication.setState(BTCommunication.STATE_SELECTING, -1);
        //**************************************
		return 0;
	}
	
    
    /**
	 * Connect to an bluetooth device
	 * @param device : the chosen device to connect to
	 */
	public void connect(String device){
		mBTAdapter.cancelDiscovery();
        String address = device.substring(device.length() - 17);
        Log.e("EPWInterface", "Device address: "+address);
        
        // Get the BluetoothDevice object
        mBluetoothDevice = mBTAdapter.getRemoteDevice(address); // Get the device MAC address, which is the last 17 chars        
		//**************
        
        mBTCommunication.connect(mBluetoothDevice);
	}
	
	// Create a BroadcastReceiver 
 	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
 		@Override
 	    public void onReceive(Context context, Intent intent) {
 	        String action = intent.getAction();
 	        // When discovery finds a device
 	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
 	            //Get the BluetoothDevice object from the Intent
 	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
 	            //If the discoverted device is the favorite server, we connect
 	            if(mPrefManager.getFavoriteServer().equals(
 	            		device.getName() + "\n" + device.getAddress())){
 	            	connect(mPrefManager.getFavoriteServer());
 	            }
 	        } 
 	        // ******************************
 	        
 	        //When discovery finished
 	        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
 	        	getActivity().setProgressBarIndeterminateVisibility(false);
 	        	//TODO if we havent connect yet (we havent find the favorite server)
 	        	//alert the user and propose to choose one of the found device
 	        	
 	        	//TODO deal with "no device found" too
//                 if (mDevicesArrayAdapter.getCount() == 0) {
//                     mDevicesArrayAdapter.add("No device found");
//                 }
            }
 	        //***********************
 	    }
 	};
 	
 	private static TransitionDrawable mTransition;
 	
 	public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	
 	public static final Handler mHandler = new Handler(){

 	  	@Override
 	  	public void handleMessage(Message msg){
 	  		switch(msg.what){
 	           	case MESSAGE_STATE_CHANGE:
 	                switch (msg.arg1) {
// 	                case STATE_NONE:
// 	                	mHomeTextView.setText("Disconnected");
//	 	       	       	//update widgets
//	 	   	       		mProgressBar.setVisibility(View.GONE);
// 	                	break;
 	                case BTCommunication.STATE_CONNECTED:
 	                	String device = msg.getData().getString(DEVICE_NAME);
 	                	mHomeTextView.setText( "Connected to " + device);
	 	       	       	//update widgets
//	 	   	       		mProgressBar.setVisibility(View.GONE); 	                	
 	                	//Activity Handler 	                	
// 	                	mActivityHandler.obtainMessage(BTConnectFragment.CONNECTED).sendToTarget();
 	                	mConnectionCallbacks.onConnected();
 	                    break;
 	                case BTCommunication.STATE_SELECTING:
 	                	mHomeTextView.setText( "Looking for devices...");
//	 	   	       		mProgressBar.setVisibility(View.VISIBLE);
 	                	break;
 	                case BTCommunication.STATE_CONNECTING:
 	                	mHomeTextView.setText( "Connection...");
	 	   	       		//update widgets
//	 	   	       		mProgressBar.setVisibility(View.VISIBLE);
                		//Lighting up signals
                		mTransition = (TransitionDrawable) mSurfGraphCo.getBackground().getCurrent();
                		mTransition.startTransition(500);
 	                	//Activity Handler 	                	
// 	                	mActivityHandler.obtainMessage(BTConnectFragment.CONNECTING).sendToTarget();
 	                	mConnectionCallbacks.onConnecting();
 	                    break;
 	                case BTCommunication.STATE_DISCONNECTED:
 	                	mHomeTextView.setText("Disconnected");
 	                	//if we get here from a failed connection, we restart scan
	 	   	         	//update widgets
//	 	   	       		mProgressBar.setVisibility(View.GONE);
 	                	
		 	   	       	mTransition = (TransitionDrawable) mSurfGraphCo.getBackground().getCurrent();
	             		mTransition.reverseTransition(100);             		
 	                	//Activity Handler 	                	
// 	                	mActivityHandler.obtainMessage(BTConnectFragment.DISCONNECTED).sendToTarget();
 	                	mConnectionCallbacks.onDisconnected();
 	                    break;
 	                }
 	                break;
// 	            case MESSAGE_DEVICE_NAME:
// 	            	mDevice = msg.getData().getString(DEVICE_NAME);
// 	                break;
 	  			default:
 	  				Toast mHandlerReception = Toast.makeText(mCtx, "Message unknown received", Toast.LENGTH_LONG);
 	  				mHandlerReception.show();
 	  			break;
 	  		}
 	  }
 	};
	
 	
 	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.e("BTConnectFragment", "onDestroy");
		try{
			cancelDiscovery();
			disconnect();
			getActivity().unregisterReceiver(mReceiver);
		}
		catch(NullPointerException e){
			Log.e("BTConnectFragment", "NullPointerException during onDestroy");
		}
		mBTCommunication.stopService();
	}
 	
	public void cancelDiscovery(){
		if(mBTAdapter != null){
			mBTAdapter.cancelDiscovery();
		}
	}
}

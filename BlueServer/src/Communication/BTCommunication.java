package Communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.JOptionPane;

import Device.Device;
import Request.RequestProcessor;


public class BTCommunication {
	private final int MAX_CONNECTION = 4;
	private int mConnectionCount = 0;
	private final String mUUID = "fa87c0d0afac11de8a390800200c9a66";
	private CommunicationThread mCommunicationThread;	
	private LocalDevice mLocal;
	private ConnectionHandler mConnectionHandler;
	private ArrayList<String> mAuthorizedDeviceList;
	
	
	public BTCommunication(ConnectionHandler root, ArrayList<String> deviceList){
		mConnectionHandler = root;
		setAuthorizedDevice(deviceList);
		mCommunicationThread = new CommunicationThread(mUUID);
		
		//Set the local Bluetooth device as Discoverable
		try {
			mLocal = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			System.out.println("Error: No Bluetooth device found.");
		}
		try {
			mLocal.setDiscoverable(DiscoveryAgent.GIAC);
		} catch (BluetoothStateException e1) {
			e1.printStackTrace();
			System.out.println("Error: Impossible to change discoverability of the bluetooth device.");
			return;
		}
	}
	
	public void setAuthorizedDevice(ArrayList<String> deviceList){
		mAuthorizedDeviceList = deviceList;
	}
	
	/**
	 * Start Bluetooth listening and communication
	 */
	public void start(){
		//Start Bluetooth listening and communication
		mCommunicationThread.start();
	}
	
	private class CommunicationThread extends Thread{
		
		private String mUUID;
		private final int R_SIZE = 12; //TODO
		private final int W_SIZE = 4; //TODO
		
		private Device mDevice;
		private StreamConnection mConnection;
		private boolean mIsRunning;
		private RequestProcessor mRequestProcessor;
		
		private InputStream mInputStream = null;
		private OutputStream mOutputStream = null;
		
		public CommunicationThread(String UUID){
			super();
			mUUID = UUID;
			mConnection = null;
			mRequestProcessor = new RequestProcessor();
		}
				
		public void run(){
			boolean connectionAccepted = false;
			StreamConnectionNotifier notifier = null;
			StreamConnection connection = null;
			UUID uuid = new UUID(mUUID, false);
			String url = "btspp://localhost:" + uuid.toString() + ";name=BlueRemote";
			try {
				notifier = (StreamConnectionNotifier) Connector.open(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true){
				//Waiting for connection
				System.out.print("waiting for connection...\n");
				try {
					connection = notifier.acceptAndOpen();
					RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
					String address = dev.getBluetoothAddress();
					System.out.println(address);
					int answer = JOptionPane.YES_OPTION;
					//If the device isn't already registered, we ask permission in dialog box
					mDevice = new Device(address);
					if(!mAuthorizedDeviceList.contains(address)){
						answer = mConnectionHandler.notifyConnectionRequest(mDevice);
					}
					if(answer == JOptionPane.YES_OPTION){
						System.out.print("connection accepted.\n");
						//TODO enter address in devicelist
						setStreamConnection(connection);
						connectionAccepted = true;
					}
					else{
						mConnectionHandler.notifyConnectionRefused(mDevice);
						connectionAccepted = false;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(connectionAccepted){
					//Connecting Streams
					try {
						mOutputStream = mConnection.openOutputStream();
						mInputStream = mConnection.openInputStream();
					} 
					catch (InterruptedIOException e){
						e.printStackTrace();
						Thread.currentThread().interrupt();
						return;
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					mIsRunning = true;
					//Process loop
					mConnectionHandler.notifyConnectionAccepted(mDevice);
					processCommunication();
					mConnectionHandler.notifyDisconnection(mDevice);
				}
			}
		}
		
		private void processCommunication(){
			int commandSize = 0;
			byte[] bufferR = new byte[R_SIZE];
			byte[] bufferW = new byte[W_SIZE];
			
			
			while(mIsRunning){
				Arrays.fill(bufferR, (byte)0);
				try {
					commandSize = mInputStream.read(bufferR,0,R_SIZE);
				} 
				catch (InterruptedIOException e){
					Thread.currentThread().interrupt();
					break;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				if(commandSize != -1){
					System.out.println("Command received: "+Arrays.toString(bufferR));
					bufferW = mRequestProcessor.process(bufferR);
//					try {
//						mOutputStream.write(bufferW,0,W_SIZE);
//					} 
//					catch (InterruptedIOException e){
//						e.printStackTrace();
//						Thread.currentThread().interrupt();
//						break;
//					}
//					catch (IOException e) {
//						e.printStackTrace();
//					}
				}
				else{
					stopRunning();
				}
			}
			try {
				mOutputStream.close();
				mInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public void setStreamConnection(StreamConnection connection){
			mConnection = connection;
		}
		
		public void stopRunning(){
			mIsRunning = false;
		}
	}

	public void disconnect(Device d) {
//		System.out.println("TODO: disconnect");//TODO
		mCommunicationThread.stopRunning();
		mCommunicationThread.interrupt();
	}
}

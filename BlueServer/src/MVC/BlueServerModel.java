package MVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Communication.BTCommunication;
import Communication.ConnectionHandler;
import Device.Device;

public class BlueServerModel implements ConnectionHandler{
	private BTCommunication mBTCommunication;
	private File mDeviceData;
	private String[] mRegisteredDevicesString;
	private ArrayList<String> mRegisteredDevices;
	
	private ArrayList<ConnectionHandler> mConnectionHandler;
	
	public BlueServerModel(){
		mConnectionHandler = new ArrayList<ConnectionHandler>();
		checkPreferences();
		//Launch communication
		mBTCommunication = new BTCommunication(this, mRegisteredDevices);
		mBTCommunication.start();
	}
	
	public void addListener(ConnectionHandler h){
		mConnectionHandler.add(h);
	}
	
	public void removeListener(ConnectionHandler h){
		mConnectionHandler.remove(h);
	}
	
	
	private void checkPreferences(){
		mDeviceData = new File("data/devices.txt");
		
		//Loading data
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(mDeviceData);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStreamReader is = new InputStreamReader(fis);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = null;
		try {
			read = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(read != null){
			sb.append(read);
			try {
				read = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String device = new String(sb);
		mRegisteredDevicesString = device.split(";");
		mRegisteredDevices = new ArrayList<String>(mRegisteredDevicesString.length);
		for(int i=0; i<mRegisteredDevicesString.length;i++){
			mRegisteredDevices.add(mRegisteredDevicesString[i]);
		}	
		
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int notifyConnectionRequest(Device d) {
		int ret = 0;
		for(ConnectionHandler h : mConnectionHandler){
			ret += h.notifyConnectionRequest(d);
		}
		return ret;
	}

	@Override
	public int notifyConnectionAccepted(Device d) {
		System.out.println("connectionAccepted");
		for(ConnectionHandler h : mConnectionHandler){
			h.notifyConnectionAccepted(d);
		}
		return 0;
	}

	@Override
	public int notifyDisconnection(Device d) {
		System.out.println("end of thread");
		for(ConnectionHandler h : mConnectionHandler){
			h.notifyDisconnection(d);
		}
		
		return 0;
	}

	@Override
	public int notifyConnectionRefused(Device d) {
		System.out.print("Connection refused");
		for(ConnectionHandler h : mConnectionHandler){
			h.notifyConnectionRefused(d);
		}
		return 0;
	}

	public void registerNewAuthorizedDevice(String deviceName) {
		mDeviceData = new File("data/devices.txt");
		//Loading data
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(mDeviceData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		deviceName += ";";
		try {
			fos.write(deviceName.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checkPreferences();
		mBTCommunication.setAuthorizedDevice(mRegisteredDevices);
		
	}
	
	public void disconnect(Device d){
		mBTCommunication.disconnect(d);
	}
	
	
}

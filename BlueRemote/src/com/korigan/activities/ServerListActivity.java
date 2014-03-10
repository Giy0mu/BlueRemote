package com.korigan.activities;

import java.util.Set;
import com.korigan.blueremote.R;
import com.korigan.preferences.PreferencesManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ServerListActivity extends Activity{
	
	Intent mNextActivity;
	private BluetoothAdapter mBluetoothAdapter;
	
	private ListView mListServer;
	private ArrayAdapter<String> mArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_serverlist);
		
		mListServer = (ListView) findViewById(R.id.listViewServer);
		mArrayAdapter = new ArrayAdapter<String>(this,R.layout.device_name);
		mListServer.setAdapter(mArrayAdapter);
		mListServer.setOnItemClickListener(mDeviceClickListener);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    }
		}
		mNextActivity = new Intent(this, MainActivity.class);
	}
	
	// The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	PreferencesManager pm = PreferencesManager.getInstance();
        	pm.registerFavoriteServer(((TextView) v).getText().toString());
        	startActivity(mNextActivity);
        }
    };
}

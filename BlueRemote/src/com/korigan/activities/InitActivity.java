package com.korigan.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import com.korigan.blueremote.R;
import com.korigan.preferences.PreferencesManager;

public class InitActivity extends Activity {
	
	private final int REQUEST_ENABLE_BT = 42;
	private BluetoothAdapter mBluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_init);
		
		//Check for Bluetooth settings
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null){
			alertAndQuit("Shush, Bluetooth is not supported on your device!\nMaybe it's time to get a new one?");
		}
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		//Initiates the PreferencesManager singleton
		PreferencesManager pm = PreferencesManager.getInstance();
		pm.init(getApplicationContext());
		
		
		//Redirect users to the right activity
		if(pm.isServerChosen()){
			//launch MainActivity
			Intent nextActivity = new Intent(InitActivity.this, MainActivity.class);
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
			startActivity(nextActivity);
		}
		else{
			//launch ServerListActivity
			Intent nextActivity = new Intent(InitActivity.this, ServerListActivity.class);
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
			startActivity(nextActivity);
		}
		
		this.finish();
	}
	
	private void alertAndQuit(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false).setNeutralButton("Click here if you're sorry", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
 public void onActivityResult(int requestCode, int resultCode, Intent data) { 
     switch (requestCode) { 
     case REQUEST_ENABLE_BT: 
    	 if(resultCode != RESULT_OK){
    		 alertAndQuit("Ok then... Goodbye");
    	 }
         break; 
     } 
 } 
}

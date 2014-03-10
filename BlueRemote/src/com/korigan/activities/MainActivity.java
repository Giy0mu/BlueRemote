package com.korigan.activities;

import com.korigan.blueremote.BTConnectFragment;
import com.korigan.blueremote.R;
import com.korigan.fragmentrequest.AbstractRequestFragment;
import com.korigan.fragmentrequest.TouchPadFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class MainActivity extends Activity implements ConnectionCallbacks{

	private BTConnectFragment fragConnection;
	private AbstractRequestFragment fragComponent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.behind_menu);
		
		//Load the connection fragment
		if(findViewById(R.id.connectFragment) != null){
        	if(savedInstanceState != null){
        		return;
            }
        	fragConnection = new BTConnectFragment();
        	fragConnection.setArguments(getIntent().getExtras());
        	getFragmentManager().beginTransaction().add(R.id.connectFragment, fragConnection).commit();
        	fragConnection.registerCallbacks(this);
        }	
		
		//Load the component fragments
		if(findViewById(R.id.componentFragment) != null){
        	if(savedInstanceState != null){
        		return;
            }
        	fragComponent = new TouchPadFragment();
        	getFragmentManager().beginTransaction().add(R.id.componentFragment, fragComponent).commit();
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list,
				android.R.layout.simple_spinner_dropdown_item);
		
		actionBar.setListNavigationCallbacks(mSpinnerAdapter, mNavigationCallback);
		return true;
	}
	
	OnNavigationListener mNavigationCallback = new OnNavigationListener() {
	  @Override
	  public boolean onNavigationItemSelected(int position, long itemId) {
	    return true;
	  }
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.disconnection:
			fragConnection.disconnect();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnecting() {
		// TODO Auto-generated method stub
		
	}

}

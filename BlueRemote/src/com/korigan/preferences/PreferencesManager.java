package com.korigan.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
	private final String PREFS_NAME = "BlueLinkPreferences";
	private final String IS_SERVER_CHOSEN = "isServerChosen";
	private final String FAVORITE_SERVER = "favoriteServer";
	
	
	private static PreferencesManager mThis;
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mSettingsEditor;
	
	private boolean mIsFavoriteServerChosen;
	private String mFavoriteServer;
	
	
	private PreferencesManager(){
		
		//TODO cstctor
	}
	
	public static PreferencesManager getInstance(){
		if(mThis == null){
			mThis = new PreferencesManager();
		}
		return mThis;
	}
	
	public void init(Context context){
		// All objects are from android.context.Context
	      mSettings = context.getSharedPreferences(PREFS_NAME, 0);
	      mSettingsEditor = mSettings.edit();
	      
	      //Get values
	      mIsFavoriteServerChosen = mSettings.getBoolean(IS_SERVER_CHOSEN, false);
	      mFavoriteServer = mSettings.getString(FAVORITE_SERVER, null);
	}
	
	public void registerFavoriteServer(String favoriteServer){
		mIsFavoriteServerChosen = true;
		mFavoriteServer = favoriteServer;
		
		mSettingsEditor.putBoolean(IS_SERVER_CHOSEN, mIsFavoriteServerChosen);
		mSettingsEditor.putString(FAVORITE_SERVER, mFavoriteServer);
		
		mSettingsEditor.commit();
	}
	
	public boolean isServerChosen(){
		return mIsFavoriteServerChosen;
	}
	public String getFavoriteServer(){
		return mFavoriteServer;
	}
}

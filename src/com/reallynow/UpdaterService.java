package com.reallynow;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class UpdaterService extends IntentService {
	 static final String TAG = "UpdaterService";

	public UpdaterService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
	}	
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent");
		
		try 
		{
			boolean refresh = ((ReallyNowApp)getApplication()).refreshAllXRate();
			sendBroadcast(new Intent(ReallyNowApp.ACTION_DISPLAY_LATEST_DATA));
			Log.d(TAG, "SENDING " +  ReallyNowApp.ACTION_DISPLAY_LATEST_DATA + " refresh successful ? " + refresh);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}

	
}

package com.reallynow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.util.Log;

public class Scheduler extends BroadcastReceiver {
	 static final String TAG = "Scheduler";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		
		PendingIntent operation = PendingIntent.getService(context, -1, new Intent(ReallyNowApp.ACTION_GET_LATEST_DATA), PendingIntent.FLAG_UPDATE_CURRENT); 
		
		String delay = PreferenceManager.getDefaultSharedPreferences(context).getString("refresh", "10");
		
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmMgr.cancel(operation);
		alarmMgr.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 
				                       AlarmManager.INTERVAL_FIFTEEN_MINUTES, operation);
		
	}

}

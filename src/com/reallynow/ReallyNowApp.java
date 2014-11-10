package com.reallynow;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

public class ReallyNowApp  extends Application {

	static final String TAG = "ReallyNowAppObj";
	public static final String ACTION_GET_LATEST_DATA = "com.reallynow.GET_LATEST_DATA";
	public static final String ACTION_DISPLAY_LATEST_DATA = "com.reallynow.DISPLAY_LATEST_DATA";
	ReallyNowDB reallynowDB;
	SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		reallynowDB = new ReallyNowDB(this);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String str = prefs.getString("refresh", "10");
		Log.d(TAG, "OnCreate" + str);
	}

	public String getXRate(String fromCurr, String toCurr)
	{
		double xrate;
		Converter converter = new Converter();
		xrate = converter.getConvertedValue(1,fromCurr,toCurr);
		String xrate3Dec = String.format("%.5f", xrate);
		Log.d(TAG,xrate3Dec);
		return xrate3Dec;
	}

	public boolean refreshAllXRate()
	{
		Cursor cursor = reallynowDB.query();
		while(cursor.moveToNext())
		{
			String fromCurr = cursor.getString(cursor.getColumnIndex(ReallyNowDB.FROM_CURR));
			String toCurr = cursor.getString(cursor.getColumnIndex(ReallyNowDB.TO_CURR));
			String xrate = getXRate(fromCurr, toCurr);
			reallynowDB.insert(fromCurr, toCurr, xrate);
		}
		return true;
	}
	
}

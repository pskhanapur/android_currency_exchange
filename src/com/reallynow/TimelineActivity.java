package com.reallynow;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TimelineActivity extends ListActivity {
	static final String TAG = "Timeline";
	static final String[] FROM = { ReallyNowDB.FROM_CURR, ReallyNowDB.TO_CURR, ReallyNowDB.XRATE };
	static final int[] TO = {R.id.displayFromCurr, R.id.displayToCurr, R.id.displayXrate  };
	Cursor cursor;
	SimpleCursorAdapter adapter;
	TimelineReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		cursor = ((ReallyNowApp)getApplication()).reallynowDB.query();
		
		adapter = new SimpleCursorAdapter(this, R.layout.timeline, cursor, FROM, TO);
		adapter.setViewBinder(VIEW_BINDER);
		
		setListAdapter(adapter);
		setTitle("Exchange Rates");
	}
	
	static final ViewBinder VIEW_BINDER = new ViewBinder() {
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int column) {
			if(view.getId() == R.id.displayXrate ) 
					return false;
		
			if(view.getId() == R.id.displayFromCurr || view.getId() == R.id.displayToCurr ) 
			    ((TextView)view).setText(cursor.getString(column).toUpperCase());
			return true;
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
		Log.d(TAG, "unregisterReceiver " +  ReallyNowApp.ACTION_DISPLAY_LATEST_DATA );
		
	}

	@Override
	protected void onResume() {
		
		if(receiver == null) receiver = new TimelineReceiver();		
		registerReceiver(receiver, new IntentFilter(ReallyNowApp.ACTION_DISPLAY_LATEST_DATA));
		Log.d(TAG, "registerReceiver " +  ReallyNowApp.ACTION_DISPLAY_LATEST_DATA );
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
//			case R.id.start_service:
//				//Log.d(TAG,"onOptionsItemSelected start_service");
//				startService(new Intent(this, UpdaterService.class));
//				return true;
//			case R.id.stop_service :
//				//Log.d(TAG,"onOptionsItemSelected stop_service");
//				stopService(new Intent(this, UpdaterService.class));
//				return true;
			case R.id.prefs :
				//Log.d(TAG,"onOptionsItemSelected stop_service");
				startActivity(new Intent(this, PrefsActivity.class));
				return true; 
			case R.id.add_currency :
				//Log.d(TAG,"onOptionsItemSelected stop_service");
				startActivity(new Intent(this, AddCurrActivity.class));
				return true;  		
			case R.id.userRefresh :
				//Log.d(TAG,"onOptionsItemSelected stop_service");
				startService(new Intent(this, UpdaterService.class));
				return true;  				
	

		}
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	class TimelineReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			cursor = ((ReallyNowApp)getApplication()).reallynowDB.query();
			adapter.changeCursor(cursor);
			Log.d(TAG, " changeCursor ");
			
		}
		
	}


}
 	
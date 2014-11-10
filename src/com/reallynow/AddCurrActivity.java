package com.reallynow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class AddCurrActivity extends Activity implements OnClickListener {

	final static String TAG = "AddCurrActivity"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button addTracking = (Button) findViewById(R.id.AddTracking);
	
		
		addTracking.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		EditText fromCurr = (EditText) findViewById(R.id.FromCurr);
		EditText toCurr = (EditText) findViewById(R.id.ToCurr);
		
		String fromCurrStr = fromCurr.getText().toString();
		String toCurrStr = toCurr.getText().toString();
		
		if(fromCurrStr.length()==0)
		{
			fromCurrStr = "nok";
		}
		
		if(toCurrStr.length()==0)
		{
			toCurrStr = "inr";
		}
		
		new XRate().execute(fromCurrStr,toCurrStr);
		
		
	}
	
	class XRate extends AsyncTask<String,Void,String> {


		@Override
		protected String doInBackground(String... currs) {
			
			String xrate = ((ReallyNowApp)getApplication()).getXRate(currs[0], currs[1]);
			((ReallyNowApp)getApplication()).reallynowDB.insert(currs[0], currs[1], xrate);
			Log.d(TAG, "xrate " + xrate);
			return xrate;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(AddCurrActivity.this, "" + result, Toast.LENGTH_LONG).show();
		}
	}

}

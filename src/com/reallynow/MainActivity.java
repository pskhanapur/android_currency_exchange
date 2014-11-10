package com.reallynow;

import android.app.Activity;
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


public class MainActivity extends Activity implements OnClickListener {

	final static String TAG = "MainActivity"; 
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
		
		if(fromCurrStr.isEmpty())
		{
			fromCurrStr = "nok";
		}
		
		if(toCurrStr.isEmpty())
		{
			toCurrStr = "inr";
		}
		
		new XRate().execute(fromCurrStr,toCurrStr);
	}
	
	class XRate extends AsyncTask<String,Void,Double> {


		@Override
		protected Double doInBackground(String... currs) {
			Converter converter = new Converter();
			
			double xrate;
			
			xrate = converter.getConvertedValue(1,currs[0],currs[1]);
			
			//Convert 12GBP to USD and print the result to console
			Log.d(TAG,"" + xrate);
			
			return xrate;
		}
		
		@Override
		protected void onPostExecute(Double result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_LONG).show();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}

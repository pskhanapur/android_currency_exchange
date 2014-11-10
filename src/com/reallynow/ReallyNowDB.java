package com.reallynow;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReallyNowDB {
	public  final String TAG = "ReallyNowDB";
	public static final String FROM_CURR="fromCurr";
	public static final String XRATE="xrate";
	public static final String TO_CURR="toCurr";
	public static final String DB_NAME="reallynow.db";
	public static final int DB_VERSION=1;
	public static final String DB_TABLE_NAME = "savedconvertstable";
	
	Context context;
	DbHelper dbHelper;
	SQLiteDatabase db;
	
	public ReallyNowDB(Context context){
		this.context = context;
		dbHelper = new DbHelper();
		Log.d(TAG, "ReallyNowDB Constuction");
		
	}
	
	public void insert(String usersFromCurr, String usersToCurr, String xrate3Dec)
	{
		db = dbHelper.getWritableDatabase();
		
		String sql = "insert or replace into " + DB_TABLE_NAME + " (id," + FROM_CURR + "," + TO_CURR + "," + XRATE + ") " + 
		             "values((select id from " + DB_TABLE_NAME + " where fromCurr=\"" + usersFromCurr + "\" and " + TO_CURR + "=\""
	                  + usersToCurr + "\"),\"" + usersFromCurr + "\",\"" + usersToCurr + "\"," + xrate3Dec  + ");";
		db.execSQL(sql);
		Log.d(TAG, "insert with SQL:" + sql);
	}
	
	
	public Cursor query()
	{
		Log.d(TAG, "query");
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select id as _id,fromCurr,toCurr, xrate from " + DB_TABLE_NAME + "; ", null);
		return cursor;
	}
	
	
	class DbHelper extends SQLiteOpenHelper {

		public DbHelper() {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = String.format("CREATE TABLE %s " + "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s VARCHAR(3), %s VARCHAR(3), %s INTEGER, UNIQUE(fromCurr,toCurr) ON CONFLICT IGNORE); ",
					DB_TABLE_NAME, FROM_CURR,TO_CURR, XRATE);
			Log.d(TAG, "onCreate with SQL:" + sql);
			db.execSQL(sql);
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + DB_TABLE_NAME + " ;");
			onCreate(db);
		}		
	}

}

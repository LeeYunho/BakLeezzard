package com.android.subway.common;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.subway.IConstant;

public class DBAdapter
{
	public static final String KEY_STATIONNAME = "StationName";
	public static String DATABASE_NAME = IConstant.SD_PATH + IConstant.DB_PATH + "subway.db";
	private static final String TABLE_NAME = "Station";

	public static SQLiteDatabase mDb;

	public static void close()
	{
		try
		{
			mDb.close();
		} 
		catch (IllegalStateException e) 
		{
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	static public void open() 
	{
		mDb = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
	}

	static public Cursor selectStationToLine(String strLine) 
	{
		return mDb.query(TABLE_NAME, null, "StationLine = '" + strLine	+ "'", null, null, null, KEY_STATIONNAME, null);
	}

	static public Cursor selectStationToName(String strName)
	{
		return mDb.query(TABLE_NAME, null, "StationName like '%" + strName + "%'", null, null, null, KEY_STATIONNAME);
	}

	static public Cursor selectStationToStationLine(String strName,	String strLine) 
	{
		return mDb.query(TABLE_NAME, null, "StationName like '%" + strName + "%' AND StationLine = '" + strLine + "'", null, null, null, KEY_STATIONNAME);
	}

	public static void setDBname(String strDBname) 
	{
		DATABASE_NAME = strDBname;
	}
}
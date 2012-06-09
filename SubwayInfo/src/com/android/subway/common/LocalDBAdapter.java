package com.android.subway.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocalDBAdapter {
	public static final String STATIONNAME = "StationName";
	public static final String STATIONLINE = "StationLine";
	public static final String FREQUENCY = "Frequency";

	public static SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "localSubway.db";
	private static final String DATABASE_TABLE = "RecentStation";

	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT exists RecentStation( StationName, StationLine, Frequency int, primary key ( StationLine, StationName ));";

	public static void open(Context context) {
		mDb = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
		mDb.execSQL(DATABASE_CREATE);
	}

	public static void close() {
		try {
			mDb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 최근검색역 삽입
	 * 
	 * @param strStationName
	 * @param strStationLine
	 * @param strStationLocal
	 */
	public static void insertItem(String strStationName, String strStationLine) {
		int cnt = 1;

		ContentValues initialValues = new ContentValues();
		initialValues.put(STATIONNAME, strStationName);
		initialValues.put(STATIONLINE, strStationLine);
		initialValues.put(FREQUENCY, cnt);

		try {
			Cursor c = mDb.query(DATABASE_TABLE, null, "StationName = '" + strStationName + "' AND StationLine = '" + strStationLine + "'", null,	null, null, null, null);

			if (c.getCount() == 0)
				mDb.insert(DATABASE_TABLE, null, initialValues);
			else {
				while (c.moveToNext()) {
					cnt = c.getInt(c.getColumnIndexOrThrow(FREQUENCY));
				}

				initialValues.put(FREQUENCY, ++cnt);
				mDb.update(DATABASE_TABLE, initialValues, "StationName = '"	+ strStationName + "' AND StationLine = '" + strStationLine + "'", null);
			}

			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 최근검색역 리스트 조회
	 * 
	 * @return
	 */
	public static Cursor select() {
		return mDb.query(DATABASE_TABLE, null, null, null, null, null, FREQUENCY + " DESC", null);
	}

	/**
	 * 개별 역 삭제
	 * 
	 * @param strStationName
	 * @param strStationLine
	 * @param strStationLocal
	 */
	public static void deleteItem(String strStationName, String strStationLine) {
		mDb.delete(DATABASE_TABLE, "StationName = '" + strStationName + "' AND StationLine = '" + strStationLine + "'", null);
	}

	/**
	 * 최근검색역 리스트 전체 삭제
	 * 
	 * @param strStationLocal
	 */
	public static void deleteItemAll() {
		mDb.delete(DATABASE_TABLE, null, null);
	}
}
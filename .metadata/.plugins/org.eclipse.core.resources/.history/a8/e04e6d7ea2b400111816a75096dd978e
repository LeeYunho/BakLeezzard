package com.android.subway.ui;

import android.app.TabActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.android.subway.R;

public class Station extends TabActivity {

	//station 화면 구성
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO Auto-generated method stub
		
		Intent intent = this.getIntent();
		String strStationName = (intent.getExtras()).getString("StationName");
		String strStationLine = (intent.getExtras()).getString("StationLine");
		String strCID = (intent.getExtras()).getString("StationLocal");
		
		Intent StationInfoIntent = new Intent(Station.this,
				Tab1_ArrivalInfo.class);
		StationInfoIntent.putExtra("StationName", strStationName);
		StationInfoIntent.putExtra("StationLine", strStationLine);
		StationInfoIntent.putExtra("StationLocal", strCID);
		
		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("Arrival")
				.setIndicator("열차 도착 정보", getResources().getDrawable(R.drawable.subway))
				.setContent(StationInfoIntent));
		
		tabHost.addTab(tabHost.newTabSpec("Timetable")
				.setIndicator("시간표", getResources().getDrawable(R.drawable.exit))
				.setContent(new Intent(Station.this, Tab2_TimetableInfo.class)));
		
		tabHost.addTab(tabHost.newTabSpec("StationInfo")
				.setIndicator("역정보", getResources().getDrawable(R.drawable.info))
				.setContent(new Intent(Station.this, Tab3_StationInfo.class)));
		
	}
}

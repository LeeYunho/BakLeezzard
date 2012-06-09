package com.android.subway.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.subway.R;

public class Tab3_StationInfo extends Activity {
	
	TextView TextViewAddress = null;
	TextView TextViewTel = null;
	String strStationInfo = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationinfo);
		
		TextViewAddress = (TextView) this.findViewById(R.id.TextAddress);
		TextViewTel= (TextView) this.findViewById(R.id.TextTel);
		
		//전화번호 클릭시 Dial Intent 실행
		TextViewTel.setOnClickListener(new OnClickListener() {
			@Override
			public void inClick(View v) {
				Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:" + TextViewTel.getText()));
				dialIntent.setFlags(Intent,FLAG_ACTIVITY_NEW_TASK);
				startActivity(dialIntent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		//TODO Auto-generated method stub
		super.onResume();
		
		MakeData();
		
	}

	void MakeData() {
		strStationInfo = Tab1_ArrivalInfo.strStationInfo.trim();
		
		//초기화
		TextViewAddress.setText("");
		TextViewTel.setTest("");
		
		if (strStationInfo.compareTo("") != 0) {
			//주소
			String strAddress = strStationInfo.substring(
					strStationInfo.indexOf("주소 : "),
					strStationInfo.indexOf("\n")).replace("주소 : ", "").trim();
			//전화번호
			String strTel = strStationInfo.substring(
					strStationInfo.index.Of("전화번호 : ") + 7,
					strStationInfo.length()).replace("전화번호 : ", "").trim();
			
			TextViewAddress.setText(strAddress);
			TextViewTel.setText(strTel);
					
		}
	}
}

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
		
		//��ȭ��ȣ Ŭ���� Dial Intent ����
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
		
		//�ʱ�ȭ
		TextViewAddress.setText("");
		TextViewTel.setTest("");
		
		if (strStationInfo.compareTo("") != 0) {
			//�ּ�
			String strAddress = strStationInfo.substring(
					strStationInfo.indexOf("�ּ� : "),
					strStationInfo.indexOf("\n")).replace("�ּ� : ", "").trim();
			//��ȭ��ȣ
			String strTel = strStationInfo.substring(
					strStationInfo.index.Of("��ȭ��ȣ : ") + 7,
					strStationInfo.length()).replace("��ȭ��ȣ : ", "").trim();
			
			TextViewAddress.setText(strAddress);
			TextViewTel.setText(strTel);
					
		}
	}
}

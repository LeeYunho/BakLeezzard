﻿package com.android.subway.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import com.android.subway.ui.Tab1_ArrivalInfo;

public class CommonFunction 
{
	
	public static String[] MakeScheduleFirstLast(String strSchedule, String strDayOftheWeek) 
	{
		String[] strarrTemp = new String[4];
		
		int beginIndex = strSchedule.indexOf(strDayOftheWeek);
		int endIndex = strSchedule.indexOf("/>", beginIndex);
		
		if ( beginIndex == -1 || endIndex == -1 )
			return null;
		
		String strTemp = strSchedule.substring(beginIndex, endIndex).replace(strDayOftheWeek + "\n", "").replace("###", "### ");
		
		String[] scheduleArr = strTemp.split("//");
		
		String strForward = scheduleArr[0].split("###")[0];
		String strBackward = scheduleArr[0].split("###")[1];
		
		strarrTemp[0] = "첫차 - " + strBackward.split(",")[0].replace("(일반)", "").replace("-역촌", "").replace("-새절", "");
		strarrTemp[1] = "첫차 - " + strForward.split(",")[0].replace("(일반)", "").replace("-역촌", "").replace("-새절", "");
		
		String[] strForwardlast = scheduleArr[1].split("###")[0].split(",");
		String[] strBackwardlast = scheduleArr[1].split("###")[1].split(",");
    	
		strarrTemp[2] = "막차 - " + strBackwardlast[strBackwardlast.length - 1].replace("(일반)", "").replace("-역촌", "").replace("-새절", "");
		strarrTemp[3] = "막차 - " + strForwardlast[strForwardlast.length - 1].replace("(일반)", "").replace("-역촌", "").replace("-새절", "");
		
		Tab1_ArrivalInfo.strStationTimeleft = "<첫차>\n" + strBackward.trim().replace(",", "\n") + "<막차>\n" + scheduleArr[1].split("###")[1].replace(",", "\n").trim();
		Tab1_ArrivalInfo.strStationTimeright = "<첫차>\n" + strForward.trim().replace(",", "\n") + "<막차>\n" + scheduleArr[1].split("###")[0].replace(",", "\n").trim();
		
		return strarrTemp;
	}

	public static String getstrDayOftheWeek() 
	{
		Calendar cal = Calendar.getInstance();
		String strDayOftheWeek = "";

		int nHour = cal.get(Calendar.HOUR_OF_DAY);

		switch (cal.get(Calendar.DAY_OF_WEEK)) 
		{
		case 1:
			if (nHour >= 0 && nHour < 2) 
			{
				strDayOftheWeek = "<토요일>";
			}
			else
			{
				strDayOftheWeek = "<공휴일>";
			}
			break;
		case 2:
			if (nHour >= 0 && nHour < 2) 
			{
				strDayOftheWeek = "<공휴일>";
			}
			else
			{
				strDayOftheWeek = "<평일>";
			}
			break;
		case 3:
		case 4:
		case 5:
		case 6:
			strDayOftheWeek = "<평일>";
			break;
		case 7:
			if (nHour >= 0 && nHour < 2) 
			{
				strDayOftheWeek = "<평일>";
			}
			else
			{
				strDayOftheWeek = "<토요일>";
			}
			break;
		}

		return strDayOftheWeek;
	}

	public static String DownloadHtml(String strURL) 
	{
		StringBuilder html = new StringBuilder();

		try
		{
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn != null) 
			{
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) 
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),	"EUC-KR"));

					String line = "";

					while (true) 
					{
						line = br.readLine();

						if (line == null) 
						{
							break;
						}

						html.append(line + "\n");
					}

					br.close();
				}

				conn.disconnect();
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return html.toString();
	}
}

package com.android.subway.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.subway.IConstant;
import com.android.subway.R;
import com.android.subway.common.CommonFunction;
import com.android.subway.common.DBAdapter;


public class Tab1_ArrivalInfo extends Activity implements Runnable 
{
	static private final String tag = "Android_Tab1_ArrivalInfo";

	public static Context ctx;
	Dialog dialog;
	String[] arrStationName;

	Thread thread = null;
	private ProgressDialog progressDialog;

	/* subway.db */
	static String strCID = "";
	static String strStationName = "";
	static String strStationLine = "";
	static String strStationURL = "";
	static String strpreStation = "";
	static String strnextStation = "";
	static String strStationInfo = "";
	static String strStationExit = "";
	static String strStationSchedule = "";

	static final int nStationName = 0;
	static final int nStationLine = 1;
	static final int nStationURL = 2;
	static final int nPreStation = 3;
	static final int nNextStation = 4;
	static final int nStationInfo = 5;
	static final int nStationExit = 6;
	static final int nStationTime = 7;

	TextView txtViewStationName = null;
	TextView txtViewPreStation = null;
	TextView txtViewNextStation = null;

	TextView txtViewBackSchedule = null;
	TextView txtViewForSchedule = null;
	TextView txtViewBackScheduleTime = null;
	TextView txtViewForScheduleTime = null;
	TextView txtViewBackScheduleText = null;
	TextView txtViewForScheduleText = null;

	TextView txtViewBackFirst = null;
	TextView txtViewForFirst = null;
	TextView txtViewBackLast = null;
	TextView txtViewForLast = null;

	TextView txtViewBackward1 = null;
	TextView txtViewForward1 = null;

	TextView txtViewBackward2 = null;
	TextView txtViewForward2 = null;

	TextView txtViewFirstLastTime = null;
	TableRow tabrowFirstLastTimeDetail = null;
	TextView txtViewFirstLastTime1 = null;
	TextView txtViewFirstLastTime2 = null;

	String[] strarRealTimeSchedule = null;
	String[] strarForward = null;
	String[] strarBackward = null;
	String[] strarStationTime = null;

	static int MsgId = 0;

	public static String strStationTimeleft = "";
	public static String strStationTimeright = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.arrivalinfo);
		ctx = this;

		// Station Activity에서 전달받은 변수 처리
		Intent intent = this.getIntent();
		strStationName = (intent.getExtras()).getString("StationName");
		strStationLine = (intent.getExtras()).getString("StationLine");
		strCID = (intent.getExtras()).getString("StationLocal");

		txtViewStationName = (TextView) findViewById(R.id.TextStationName);
		txtViewPreStation = (TextView) findViewById(R.id.TextPreStation);
		txtViewNextStation = (TextView) findViewById(R.id.TextNextStation);

		txtViewBackSchedule = (TextView) findViewById(R.id.TextBackSchedule);
		txtViewForSchedule = (TextView) findViewById(R.id.TextForSchedule);
		txtViewBackScheduleTime = (TextView) findViewById(R.id.TextBackScheduleTime);
		txtViewForScheduleTime = (TextView) findViewById(R.id.TextForScheduleTime);
		txtViewBackScheduleText = (TextView) findViewById(R.id.TextBackScheduleText);
		txtViewForScheduleText = (TextView) findViewById(R.id.TextForScheduleText);

		txtViewBackFirst = (TextView) findViewById(R.id.TextFirstBackSchedule);
		txtViewForFirst = (TextView) findViewById(R.id.TextFirstForSchedule);
		txtViewBackLast = (TextView) findViewById(R.id.TextLastBackSchedule);
		txtViewForLast = (TextView) findViewById(R.id.TextLastForSchedule);

		txtViewBackward1 = (TextView) findViewById(R.id.TextBack1);
		txtViewForward1 = (TextView) findViewById(R.id.TextFor1);

		txtViewBackward2 = (TextView) findViewById(R.id.TextBack2);
		txtViewForward2 = (TextView) findViewById(R.id.TextFor2);

		txtViewFirstLastTime = (TextView) findViewById(R.id.firstlasttime);
		tabrowFirstLastTimeDetail = (TableRow) findViewById(R.id.firstlasttimedetail);
		txtViewFirstLastTime1 = (TextView) findViewById(R.id.firstlasttimedetail1);
		txtViewFirstLastTime2 = (TextView) findViewById(R.id.firstlasttimedetail2);

		txtViewFirstLastTime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (tabrowFirstLastTimeDetail.getVisibility() == View.GONE)
					tabrowFirstLastTimeDetail.setVisibility(View.VISIBLE);
				else
					tabrowFirstLastTimeDetail.setVisibility(View.GONE);
			}
		});

		txtViewPreStation.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// 이전역에서 역명만 갖고 오기
				strStationName = (String) txtViewPreStation.getText();
				strStationName = strStationName.replace("< ", "").replace(" ", "");

				// 이전역이 2개 이상일 경우 선택 다이얼로그를 띄운다.
				if (strStationName.indexOf(",") > 0)
				{
					arrStationName = strStationName.split(",");
					dialog = new AlertDialog.Builder(ctx).setItems(arrStationName, new DialogInterface.OnClickListener()
					{
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton)
								{
									// TODO Auto-generated method stub
									strStationName = arrStationName[whichButton];

									// 정보 수집
									collectArrivalData();
								}
							}).setNegativeButton(R.string.alert_dialog_cancle, new DialogInterface.OnClickListener() 
							{
								@Override
								public void onClick(DialogInterface dialog,	int whichButton) 
								{
									// TODO Auto-generated method stub
								}
							}).show();
					}
				else
				{
					// 정보 수집
					collectArrivalData();
				}
			}
		});

		txtViewNextStation.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// 다음역에서 역명만 갖고 오기
				strStationName = (String) txtViewNextStation.getText();
				strStationName = strStationName.replace(" >", "").replace(" ", "");

				// 다음역이 2개 이상일 경우 선택 다이얼로그를 띄운다.
				if (strStationName.indexOf(",") > 0) 
				{
					arrStationName = strStationName.split(",");
					dialog = new AlertDialog.Builder(ctx).setItems(arrStationName, new DialogInterface.OnClickListener() 
					{
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) 
								{
									// TODO Auto-generated method stub

									strStationName = arrStationName[whichButton];

									// 정보 수집
									collectArrivalData();
								}
							}).setNegativeButton(R.string.alert_dialog_cancle, new DialogInterface.OnClickListener() 
							{
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) 
								{
									// TODO Auto-generated method stub
								}
							}).show();
				} 
				else 
				{
					// 정보 수집
					collectArrivalData();
				}
			}
		});
	}

	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub

		// 프로그래스 다이얼로그 실행
		progressDialog = ProgressDialog.show(ctx, "", "Loading 중입니다.", true, true);

		thread = new Thread(this);

		try {
			// Thread 시작
			thread.start();
		}
		catch (IllegalStateException e) 
		{
		}

		super.onResume();
	}

	/**
	 * 제공하기 위한 데이터 생성 DB 조회 실시간 정보 수집
	 * 
	 * @return
	 */
	public boolean MakeStationInfo()
	{
		strarRealTimeSchedule = null;

		try
		{
			// DB Open
			DBAdapter.open();

			// 데이터 조회
			Cursor c = DBAdapter.selectStationToStationLine(strStationName, strStationLine);

			// 조회된 데이터가 0건이면 return
			if (c.getCount() == 0)
				return false;

			while (c.moveToNext()) 
			{
				// 조회된 데이터가 1건 이상일 경우 역명과 호선이 일치하는 데이터를 찾는다
				if (c.getCount() > 1) 
				{
					String strStationNameTemp = c.getString(nStationName);

					if (strStationNameTemp.indexOf("(") != -1) 
					{
						strStationNameTemp = c.getString(0).substring(0, strStationNameTemp.indexOf("("));
					}

					if (strStationName.compareTo(strStationNameTemp) != 0) 
					{
						continue;
					}
				}

				strStationName = c.getString(nStationName).trim(); // 역명
				strStationURL = c.getString(nStationURL); // 모바일서울 URL
				strnextStation = c.getString(nNextStation); // 이전역
				strpreStation = c.getString(nPreStation); // 다음역
				strStationInfo = c.getString(nStationInfo).trim(); // 역정보(주소,전화번호)
				strStationExit = c.getString(nStationExit).trim(); // 출구정보
				strStationSchedule = c.getString(nStationTime); // 첫차, 막차정보
			}

			c.close();
			DBAdapter.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		// 요일
		String strDayOftheWeek = CommonFunction.getstrDayOftheWeek();

		MsgId = IConstant.REALTIME;

		if ("".compareTo(strStationURL) == 0) 
		{
			MsgId = IConstant.NO_REALTIME;
		}

		// 실시간 정보 수집
		strarRealTimeSchedule = realtimeSchedule(strStationURL);

		// 모바일서울 페이지 오류로 실시간 정보 수집 못함
		if (strarRealTimeSchedule == null) 
		{
			MsgId = IConstant.PAGE_ERR;
		}

		// 첫차 막차정보 조회
		strarStationTime = CommonFunction.MakeScheduleFirstLast(strStationSchedule, strDayOftheWeek);

		// 최근검색 로컬DB에 추가
		//LocalDBAdapter.open(ctx);
		//LocalDBAdapter.insertItem(strStationName.replace("\n", ""), strStationLine);
		//LocalDBAdapter.close();

		return true;
	}

	/**
	 * @param stationURL
	 *            실시간 정보 수집 URL
	 * @return
	 */
	public String[] realtimeSchedule(String stationURL) 
	{
		// HTML 데이터 수신
		String StationPagedata = CommonFunction.DownloadHtml(stationURL).trim();
		String[] returnschedule;

		int nbeginIndex = 0;
		int nendIndex = 0;
		String strArrivaldata = "";
		int cnt = 0;

		if (StationPagedata.compareTo("") == 0) 
		{
			return null;
		}
		else
		{
			returnschedule = new String[4]; // 도착정보마다 * 상,하행 2개씩

			do {
				nbeginIndex = StationPagedata.indexOf("<li>", nendIndex);
				nendIndex = StationPagedata.indexOf("</li>", nbeginIndex);

				// <li> ~ </li> 태그가 없으므로 return
				if (nbeginIndex < 0)
					break;

				strArrivaldata = StationPagedata.substring(nbeginIndex,	nendIndex);

				Pattern pattern = Pattern.compile("<a href=\"(.+)\" class=\"lst_n\">(\\s*)<div class=\"p\">(\\s*)(.+)(\\s*)(.+)(\\s*)</div>");
				Matcher matches = pattern.matcher(strArrivaldata);

				// 일치하는 패턴이 없으므로 return
				if (!matches.find())
					continue;

				do 
				{
					String strDest = matches.group(4); // 행선지
					String strDestTime = matches.group(6); // 도착예정시간

					// <div class="bold"> [서동탄행 도착정보] </div> <br/> 0분 후 도착 예정
					if (strDest.indexOf("[") != -1) 
					{
						strDest = strDest.substring(strDest.indexOf("[") + 1, strDest.indexOf("]")); // '서동탄행 도착정보' 만 잘라내기
						strDest = strDest.replace("도착정보", "").trim(); // 행선지 정보만 잘라내기
					}

					// 모바일서울 역명 수정
					if (strDest.compareTo("디지털미디어시티행") == 0) 
					{
						strDest = "디엠시행";
					}
					if (strDest.compareTo("서울(경의선)행") == 0) 
					{
						strDest = "서울역행";
					}

					returnschedule[cnt] = strDest + "\n" + strDestTime; // 행선지 + 도착예정시간
				} while (matches.find()); // 패턴이 일치할 때 까지

				cnt++;
			} while (nbeginIndex >= 0); // <li> ~ </li> 태그가 존재 할 때 까지
		}

		return returnschedule;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_station, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.refrash) 
		{
			if (strStationURL.compareTo("") != 0)

				progressDialog = ProgressDialog.show(ctx, "", "Loading 중입니다.",	true, true);

			if (thread != null) 
			{
				try {
					thread.start();
				}
				catch (IllegalStateException e) 
				{
				}
			}
			else
			{
				thread = new Thread((Runnable) ctx);

				try
				{
					thread.start();
				}
				catch (IllegalStateException e) 
				{
				}
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		// 제공하기 위한 데이터 만들기
		MakeStationInfo();

		// Thread 안에서는 UI변경이 불가하므로 핸들러로 처리한다.
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			// TODO Auto-generated method stub

			// 역명의 길이가 10자 이상일 경우
			if (strStationName.length() > 10) 
			{
				Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");
				Matcher match = pattern.matcher(strStationName.trim());

				if (match.find())
					strStationName = match.group(1) + "\n(" + match.group(2) + ")";
				else
					txtViewStationName.setTextSize(23);
			}

			// 다음역의 길이가 8자 이상일 경우 사이즈 줄이기
			if (strnextStation.length() > 8)
				txtViewPreStation.setTextSize(16);
			else
				txtViewPreStation.setTextSize(20);

			// 이전역의 길이가 8자 이상일 경우 사이즈 줄이기
			if (strpreStation.length() > 8)
				txtViewNextStation.setTextSize(16);
			else
				txtViewNextStation.setTextSize(20);

			// 역명
			txtViewStationName.setText(strStationName.trim());

			// 이전역
			txtViewPreStation.setText("< " + strnextStation);

			// 다음역
			txtViewNextStation.setText(strpreStation + " >");

			// 2호선, 9호선은 행선방향이 반대로 되어있음
			if ((strStationLine.equals("2호선") || strStationLine.equals("9호선"))) 
			{
				// 첫차 막차 시간
				txtViewBackFirst.setText(strarStationTime[1]);
				txtViewForFirst.setText(strarStationTime[0]);
				txtViewBackLast.setText(strarStationTime[3]);
				txtViewForLast.setText(strarStationTime[2]);

				// 첫차 막차 정보
				txtViewFirstLastTime1.setText(strStationTimeright);
				txtViewFirstLastTime2.setText(strStationTimeleft);

				// 실시간 도착정보
				if (strarRealTimeSchedule != null) 
				{
					txtViewBackward1.setText(strarRealTimeSchedule[0]);
					txtViewBackward2.setText(strarRealTimeSchedule[1]);
					txtViewForward1.setText(strarRealTimeSchedule[2]);
					txtViewForward2.setText(strarRealTimeSchedule[3]);
				}
			} 
			else
			{
				// 첫차 막차 시간
				txtViewBackFirst.setText(strarStationTime[0]);
				txtViewForFirst.setText(strarStationTime[1]);
				txtViewBackLast.setText(strarStationTime[2]);
				txtViewForLast.setText(strarStationTime[3]);

				// 첫차 막차 정보
				txtViewFirstLastTime1.setText(strStationTimeleft);
				txtViewFirstLastTime2.setText(strStationTimeright);

				// 실시간 도착정보
				if (strarRealTimeSchedule != null) 
				{
					txtViewBackward1.setText(strarRealTimeSchedule[2]);
					txtViewBackward2.setText(strarRealTimeSchedule[3]);
					txtViewForward1.setText(strarRealTimeSchedule[0]);
					txtViewForward2.setText(strarRealTimeSchedule[1]);
				}
			}

			// 6호선 순환구간은 실시간 도착정보가 반대로 되어있다.
			if ((strStationLine.equals("6호선") && (strStationName.equals("역촌")
					|| strStationName.equals("불광")
					|| strStationName.equals("독바위")
					|| strStationName.equals("연신내")
					|| strStationName.equals("구산")))) 
			{
				// 실시간 도착정보
				if (strarRealTimeSchedule != null) 
				{
					txtViewBackward1.setText(strarRealTimeSchedule[2]);
					txtViewBackward2.setText(strarRealTimeSchedule[3]);
					txtViewForward1.setText(strarRealTimeSchedule[0]);
					txtViewForward2.setText(strarRealTimeSchedule[1]);
				}
			}

			// Toast 메세지.
			switch (MsgId) 
			{
			case IConstant.REALTIME:
				Toast.makeText(ctx, "실시간 열차 도착정보를 제공 합니다.", Toast.LENGTH_SHORT)
						.show();
				break;

			case IConstant.NETWORK_ERR:
				Toast.makeText(ctx,
						"네트워크에 접속 할 수 없습니다. 실제 열차 도착시간과 다를 수 있습니다.",
						Toast.LENGTH_SHORT).show();
				break;

			case IConstant.PAGE_ERR:
				Toast.makeText(ctx, strStationURL + "페이지 오류 입니다. ",
						Toast.LENGTH_SHORT).show();
				break;

			case IConstant.NO_REALTIME:
				Toast.makeText(ctx, "모바일서울에서 도착정보를 제공하지 않습니다.",
						Toast.LENGTH_SHORT).show();
				break;
			}

			// 도착 시간
			String[] strTemp = ((String) txtViewBackward1.getText()).split("\n");
			if (strTemp[0].compareTo("") != 0) 
			{
				txtViewBackSchedule.setText(strTemp[0]);
				txtViewBackScheduleTime.setText(strTemp[1].replace("분 후 도착 예정",	""));
				txtViewBackScheduleText.setText("분 후");
			} 
			else
			{
				txtViewBackSchedule.setText("");
				txtViewBackScheduleTime.setText("");
				txtViewBackScheduleText.setText("");
			}

			strTemp = ((String) txtViewForward1.getText()).split("\n");
			if (strTemp[0].compareTo("") != 0) 
			{
				txtViewForSchedule.setText(strTemp[0]);
				txtViewForScheduleTime.setText(strTemp[1].replace("분 후 도착 예정", ""));
				txtViewForScheduleText.setText("분 후");
			} 
			else
			{
				txtViewForSchedule.setText("");
				txtViewForScheduleTime.setText("");
				txtViewForScheduleText.setText("");
			}

			// 프로그래스 다이얼로그 없애기
			if (progressDialog != null)
				if (progressDialog.isShowing())
					progressDialog.dismiss();

			// 역선택 다이얼로그 없애기
			if (dialog != null)
				if (dialog.isShowing())
					dialog.dismiss();

			try 
			{
				if (thread != null)
					thread.stop();
			}
			catch (IllegalStateException e) 
			{
			}

			thread = null;
		}
	};

	/**
	 * 도착정보 수집
	 */
	public void collectArrivalData() 
	{
		// 프로그래스 다이얼로그 실행
		progressDialog = ProgressDialog.show(ctx, "", "Loading 중입니다.", true, true);

		if (thread != null) 
		{
			try
			{
				// 스레드 시작
				thread.start();
			}
			catch (IllegalStateException e) 
			{
			}
		} 
		else
		{
			// 스레드가 null 이라면 다시 생성
			thread = new Thread((Runnable) ctx);

			try 
			{
				thread.start();
			}
			catch (IllegalStateException e) 
			{
			}
		}
	}
}
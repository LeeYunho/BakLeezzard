import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class MakeSubwayData
{
	static String strCID; // 지역코드
	static String strLID; // 노선코드
	static String strSID; // 역코드
	static String strStationName; // 역명
	static String strStationLine; // 호선
	static String strStationURL; // URL(m.seoul.go.kr)
	static String strpreStation; // 이전역
	static String strnextStation; // 다음역
	static String strStationInfo; // 역정보(전화번호, 주소)
	static String strStationTime; // 첫차, 막차정보

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = null;
			Statement stat;
			PreparedStatement prep = null;
			
			conn = DriverManager.getConnection("jdbc:sqlite:subway.db");
			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists Station;");
			stat.executeUpdate("CREATE TABLE Station(StationName, StationLine, StationURL, preStation, nexStation, StationInfo, StationEit, StationTime, primary key (StationLine, stationName, StationURL));");
			prep = conn.prepareStatement("INSERT into Station values(?, ?, ?, ?, ?, ?, ?, ?);");
			
			StationList stationList = new StationList();
			ArrayList<StationList> stationlist = stationList.getStationList();
			
			for(int i=0; i<stationlist.size(); i++)
			{
				strCID = "1000"; // 수도권 코드 고정값
				strLID = "";
				strSID = "";
				strpreStation = "";
				strnextStation = "";
				strStationURL = "";
				strStationInfo = "";
				strStationTime = "";
				
				//Get CID,LID,SID
				String URL = String.format("http://traffic.map.naver.com/Subway/Pop_Subway.asp?CID=%s&SEFlag=1&SearchName=", strCID) + URLEncoder.encode(strStationName, "EUC-KR");
				String data = DownloadHtml(URL);
				
				Pattern StationPattern = Pattern.compile("javascript:SenData\\((.+),(.+),(.+),'(.+)'\\)(.+)(\\s+)(.+)>(.+)</td>");
				Matcher StationMatches = StationPattern.matcher(data);
				
				if (StationMatches.find()) {
					do {
						String strLine = StationMatches.group(8).trim();
						String strName = StationMatches.group(4).trim().replace("'","");
						int nIndex = strName.indexOf('(');
						
						if (nIndex >0) {
							strName = strName.substring(0, nIndex);
						}
						
						// 수집 역명, 호선과 일치하면 수집, 아니면 다음 조회 결과로 넘어감.
						if (strLine.compareTo(strStationLine)==0 && strName.compareTo(strStationName)==0) {
							strCID = StationMatches.group(1).trim(); // 지역코드
							strSID = StationMatches.group(2).trim(); // 지역코드
							strLID = StationMatches.group(3).trim(); // 지역코드
							break;
						} else
							continue;
					} while ( StationMatches.find());
				}else {
					continue;
				}
				
				// DB 추가
				prep.setString(1, strStationName);
				prep.setString(2, strStationLine);
				prep.setString(3, strStationURL);
				prep.setString(4, strpreStation);
				prep.setString(5, strnextStation);
				prep.setString(6, strStationInfo);
				prep.setString(8, strStationTime);
				
				prep.addBatch();
				
				// 데이터가 잘 수집되었는지 확인하기 위한 로그
				String strLog = String.format("%s, %s, %s, %s, %s, %s", strStationName, strStationLine, strStationURL, strpreStation, strnextStation, strStationInfo);
				System.out.println(strLog);
			} // DB와 연결이 끊어지지 않았다면 데이터를 삽입
			
			if(conn != null)
			{
				conn.setAutoCommit(false);
				prep.executeBatch();
				conn.setAutoCommit(true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	// HTML Url을 인자로 받아 HTML 내용을 리턴하는 함수
	public static String DownloadHtml(String strURL)
	{
		StringBuilder html = new StringBuilder();
		
		try
		{
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if(conn != null)
			{
				conn.setConnectTimeout(5000);
				conn.setUseCaches(false);
				
				if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "EUC-KR"));
					
					String line = "";
					
					while(true)
					{
						line = br.readLine();
						
						if(line == null)
							break;
						
						html.append(line + "\n");
					}
					br.close();
				}
				
				conn.disconnect();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return html.toString();
	}
	


}
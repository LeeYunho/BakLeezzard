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
	static String strCID; // �����ڵ�
	static String strLID; // �뼱�ڵ�
	static String strSID; // ���ڵ�
	static String strStationName; // ����
	static String strStationLine; // ȣ��
	static String strStationURL; // URL(m.seoul.go.kr)
	static String strpreStation; // ������
	static String strnextStation; // ������
	static String strStationInfo; // ������(��ȭ��ȣ, �ּ�)
	static String strStationTime; // ù��, ��������

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
				strCID = "1000"; // ������ �ڵ� ������
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
						
						// ���� ����, ȣ���� ��ġ�ϸ� ����, �ƴϸ� ���� ��ȸ ����� �Ѿ.
						if (strLine.compareTo(strStationLine)==0 && strName.compareTo(strStationName)==0) {
							strCID = StationMatches.group(1).trim(); // �����ڵ�
							strSID = StationMatches.group(2).trim(); // �����ڵ�
							strLID = StationMatches.group(3).trim(); // �����ڵ�
							break;
						} else
							continue;
					} while ( StationMatches.find());
				}else {
					continue;
				}
				
				// DB �߰�
				prep.setString(1, strStationName);
				prep.setString(2, strStationLine);
				prep.setString(3, strStationURL);
				prep.setString(4, strpreStation);
				prep.setString(5, strnextStation);
				prep.setString(6, strStationInfo);
				prep.setString(8, strStationTime);
				
				prep.addBatch();
				
				// �����Ͱ� �� �����Ǿ����� Ȯ���ϱ� ���� �α�
				String strLog = String.format("%s, %s, %s, %s, %s, %s", strStationName, strStationLine, strStationURL, strpreStation, strnextStation, strStationInfo);
				System.out.println(strLog);
			} // DB�� ������ �������� �ʾҴٸ� �����͸� ����
			
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
	
	
	
	// HTML Url�� ���ڷ� �޾� HTML ������ �����ϴ� �Լ�
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
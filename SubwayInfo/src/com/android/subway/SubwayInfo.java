package com.android.subway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class SubwayInfo extends Activity {

	//Station List
	static final String[] STATION = new String[]
	{
		"가능", "가산디지털단지", "간석", "개봉",
		"관악", "광명", "구로", "구일", "군포", "금정", "금천구청", "남영", "노량진", "녹양",
		"녹천", "당정", "대방", "덕계", "덕정", "도봉", "도봉산", "도원", "도화", "독산", "동대문",
		"동두천", "동두천중앙", "동묘앞", "동암", "동인천", "두정", "망월사", "명학", "방학", "배방",
		"백운", "병점", "보산", "봉명", "부개", "부천", "부평", "서동탄", "서울역", "서정리",
		"석계", "석수", "성균관대", "성북", "성환", "세류", "세마", "소사", "소요산", "송내",
		"송탄", "수원", "시청", "신길", "신도림", "신설동", "신이문", "신창", "쌍용", "아산",
		"안양", "양주", "역곡", "영등포", "오류동", "오산", "오산대", "온수", "온양온천", "외대앞",
		"용산", "월계", "의왕", "의정부", "인천", "제기동", "제물포", "종각", "종로3가", "종로5가",
		"주안", "중동", "지제", "지행", "직산", "진위", "창동", "천안", "청량리", "평택",
		"화서", "회기", "회룡", "강남", "강변", "건대입구", "교대", "구로디지털단지", "구의",
		"까치산", "낙성대", "당산", "대림", "도림천", "동대문역사문화공원", "뚝섬", "문래", "방배",
		"봉천", "사당", "삼성", "상왕십리", "서울대입구", "서초", "선릉", "성수", "신답", "신당",
		"신대방", "신림", "신정네거리", "신천", "신촌", "아현", "양천구청", "역삼", "영등포구청",
		"왕십리", "용답", "용두", "을지로3가", "을지로4가", "을지로입구", "이대", "잠실", "잠실나루",
		"종합운동장", "충정로", "한양대", "합정", "홍대입구", "가락시장", "경복궁", "경찰병원",
		"고속터미널", "구파발", "금호", "남부터미널", "녹번", "대곡", "대청", "대치", "대화", "도곡",
		"독립문", "동대입구", "마두", "매봉", "무악재", "백석", "불광", "삼송", "수서", "신사",
		"안국", "압구정", "약수", "양재", "연신내", "오금", "옥수", "원당", "일원", "잠원",
		"정발산", "주엽", "지축", "충무로", "학여울", "홍제", "화정", "경마공원", "고잔", "공단",
		"과천", "길음", "남태령", "노원", "당고개", "대공원", "대야미", "동작", "명동", "미아",
		"미아삼거리", "반월", "범계", "산본", "삼각지", "상계", "상록수", "선바위", "성신여대입구",
		"수리산", "수유", "숙대입구", "신길온천", "신용산", "쌍문", "안산", "오이도", "이촌", "인덕원",
		"정부과천청사", "정왕", "중앙", "총신대입구(이수)", "평촌", "한대앞", "한성대입구", "혜화",
		"회현", "강동", "개롱", "개화산", "거여", "고덕", "공덕", "광나루", "광화문", "군자",
		"굽은다리", "길동", "김포공항", "답십리", "둔촌동", "마곡", "마장", "마천", "마포", "명일",
		"목동", "발산", "방이", "방화", "상일동", "서대문", "송정", "신금호", "신정", "아차산",
		"애오개", "양평", "여의나루", "여의도", "영등포시장", "오목교", "올림픽공원", "우장산", "장한평",
		"천호", "청구", "행당", "화곡", "고려대", "광흥창", "구산", "녹사평", "대흥", "독바위",
		"돌곶이", "디지털미디어시티", "마포구청", "망원", "버티고개", "보문", "봉화산", "상수", "상월곡",
		"새절", "안암", "역촌", "월곡", "월드컵경기장", "응암", "이태원", "증산", "창신", "태릉입구",
		"한강진", "화랑대", "효창공원앞", "강남구청", "공릉", "광명사거리", "남구로", "남성", "내방",
		"논현", "뚝섬유원지", "마들", "먹골", "면목", "반포", "보라매", "사가정", "상도", "상봉",
		"수락산", "숭실대입구", "신대방삼거리", "신풍", "어린이대공원", "용마산", "이수", "장승배기",
		"장암", "중계", "중곡", "중화", "천왕", "철산", "청담", "하계", "학동", "강동구청",
		"남한산성입구", "단대오거리", "모란", "몽촌토성", "문정", "복정", "산성", "석촌", "송파",
		"수진", "신흥", "암사", "장지", "가양", "개화", "공항시장", "구반포", "국회의사당", "노들",
		"등촌", "마곡나루", "사평", "샛강", "선유도", "신논현", "신목동", "신반포", "신방화",
		"양천향교", "염창", "증미", "흑석", "개포동", "경원대", "구룡", "대모산입구", "미금", "보정",
		"서현", "수내", "야탑", "오리", "이매", "정자", "죽전", "태평", "한티", "간석오거리",
		"갈산", "경인교대입구", "계산", "계양", "국제업무지구", "귤현", "동막", "동수", "동춘",
		"문학경기장", "박촌", "부평구청", "부평삼거리", "부평시장", "선학", "센트럴파크", "신연수",
		"예술회관", "원인재", "인천대입구", "인천시청", "인천터미널", "임학", "작전", "지식정보단지",
		"캠퍼스타운", "테크노파크", "구리", "국수", "덕소", "도농", "도심", "망우", "서빙고", "신원",
		"아신", "양수", "양원", "양정", "오빈", "용문", "운길산", "원덕", "응봉", "중랑", "팔당",
		"한남", "가좌", "곡산", "금릉", "금촌", "능곡", "문산", "백마", "수색", "운정", "월롱",
		"일산", "탄현", "파주", "풍산", "행신", "화전", "검암", "운서", "공항화물청사", "인천국제공항",
		"가평", "갈매", "강촌", "굴봉산", "금곡", "김유정", "남춘천", "대성리", "마석", "백양리",
		"사릉", "상천", "청평", "춘천", "퇴계원", "평내호평"
	};
	

	//DB Copy
	public void docopy() {
		File outDir = null;
		File outfile = null;
		// 외장메모리 사용 가능 상태인지 확인
		
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			outDir = new File(IConstant.SD_PATH + IConstant.DB_PATH);
			
			outDir.mkdirs();//디렉토리 생성
			outfile = new File(outDir, "subway.db");
			
			InputStream is =null;
			OutputStream os = null;
			int size;
			
			try {
				//AssetManger를 이용하여 subway.DB 파일읽기
				is = getAssets().open("subway.db");
				size = is.available();
				
				outfile.createNewFile();//make file
				os = new FileOutputStream(outfile);
				byte[] buffer = new byte[size];
				
				is.read(buffer);
				os.write(buffer);
				
				is.close();
				os.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
					os.close();
				} catch (IOException e) {
					//TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
		
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtViewStationName = (AutoCompleteTextView)findViewById(R.id.txtViewStationName);
        btnSearch = (Button)this.findViewById(R.id.btnline);
        
        ctx = this;
        preferences = getPreferences(MODE_WORLD_WRITEABLE);
        strVersion = preferences.getString("VERSION", "");
        
        // SharePreferences에 저장된 버전과 app 버전이 다를 때 DB 파일 외장메모리로 복사
        if(getString(R.string.appversion).compareTo(strVersion) != 0)
        {
        	if(Environment.getExternalStoragesState().equals(Environment.MEDIA_MOUNTED)) //외장메모리가 사용 가능할 때
        	{
        		Update();
        	}
        	else
        	{
        		showDialog(IConstant.DIALOG_NOSDCARD);
        	}
        }
        
        registerForContextMenu(getListView()); // 컨텍스트 메뉴 등록
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onResume()
    {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	// 자동완성 텍스트뷰 구현
    	ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, STATION);
    	txtViewStationName.setAdapter(adapter);
    	txtViewStationName.setText("");
    }
}
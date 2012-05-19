package com.android.subway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class SubwayInfoActivity extends Activity {

	public void docopy() {
		File outDir = null;
		File outfile = null;
		// ����޸� ��� ���� �������� Ȯ��
		
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			outDir = new File(IConstant.SD_PATH + IConstant.DB_PATH);
			
			outDir.mkdirs();//���丮 ����
			outfile = new File(outDir, "subway.db");
			
			InputStream is =null;
			OutputStream os = null;
			int size;
			
			try {
				//AssetManger�� �̿��Ͽ� subway.DB �����б�
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
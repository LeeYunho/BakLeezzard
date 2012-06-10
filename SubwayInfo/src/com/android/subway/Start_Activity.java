package com.android.subway;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Start_Activity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
 setContentView(R.layout.start_activity);
        
        Handler handler = new Handler () {

            @Override
            public void handleMessage(Message msg)
            {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);

	    // TODO Auto-generated method stub
	}

}

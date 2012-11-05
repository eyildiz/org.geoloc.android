package org.geoloc.android.mapview;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashScreen extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.splash_screen);
		super.onCreate(savedInstanceState);
		
		Thread timer = new Thread(){
			
			public void run(){
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					startActivity(new Intent(SplashScreen.this,LoginActivity.class));
				}
			}
			
			
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
	


}

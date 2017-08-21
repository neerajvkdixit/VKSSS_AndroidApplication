package com.vksss.org.splashscreen;

import com.vksss.org.MainMenuActivity;
import com.vksss.org.R;
import com.vksss.org.VksssMainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class VkSplashScreen extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vksss_main);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent startActivityIntent = new Intent(VkSplashScreen.this, MainMenuActivity.class);
				startActivity(startActivityIntent);
				finish();
			}
		}, 4000);
	}

}

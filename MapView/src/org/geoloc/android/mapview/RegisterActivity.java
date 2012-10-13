package org.geoloc.android.mapview;

import org.geoloc.android.mapview.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends Activity{
	Button BackLoginBT;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        
	        BackLoginBT=(Button)findViewById(R.id.BackLoginBT); 
	        BackLoginBT.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
				startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
					
				}
			});
	 }
}

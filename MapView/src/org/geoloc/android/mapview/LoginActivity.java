package org.geoloc.android.mapview;


import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText UserNameET,PasswordET;
	Button LoginBT,RegisterBT;

	public static final String PrefFile = "PreferenceVars";
	public static final String PrefUser = "PreferenceUserVar";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       
        
        UserNameET=(EditText)findViewById(R.id.LoginUserNameET);
        PasswordET=(EditText)findViewById(R.id.LoginPasswordET);
        LoginBT=(Button)findViewById(R.id.LoginLoginBT);  
        RegisterBT=(Button)findViewById(R.id.LoginRegisterBT);  
        
        SharedPreferences prf = getSharedPreferences(PrefFile, MODE_PRIVATE);
        String usernameInFile = prf.getString(PrefUser, "");
        
        UserNameET.setText(usernameInFile);
		
        RegisterBT.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
				
			}
		});
        
        LoginBT.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(CustomHttpClient.checkInternetConnection(getApplicationContext()))
				{
					
					getSharedPreferences(PrefFile, MODE_PRIVATE)
					.edit()
					.putString(PrefUser, UserNameET.getText().toString())
					.commit();
										
					new BackgroundProcess("Signing in, please wait..").execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No internet connection.Please check your internet settings.",Toast.LENGTH_LONG ).show();
				}
			}
		});
        
    }

    @Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    
    class BackgroundProcess extends AsyncTask<Boolean, String, Boolean>{

    	ProgressDialog progressDialog;
    	String progressMessage;
    	
		public BackgroundProcess(String message) {
			super();
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressMessage = message;
		}

		@Override
		protected Boolean doInBackground(Boolean... params) {
		
			if(UserNameET.getText().toString().equals("") || PasswordET.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
				
			}
			else{
				Boolean result=null;
				try {
					result = CustomHttpClient.checkUser(UserNameET.getText().toString(), PasswordET.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if(result != null){
					return result;
				}else{
					Toast.makeText(getApplicationContext(), "Connection error. Please try again.", Toast.LENGTH_SHORT).show();
				}
			}
		
		return null;
		}

		@Override
		protected void onCancelled() {
			Toast.makeText(getApplicationContext(), "Ýþlem iptal edildi.", Toast.LENGTH_SHORT).show();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			/*
			 *  Progress dialog ekranda görüntüleniyorsa dismiss ile durdurulur.
			 */
			if(progressDialog.isShowing())
				progressDialog.dismiss();
			
			if(result != null){
				if(result){
					Toast.makeText(getApplicationContext(), "Signed in!", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(LoginActivity.this,MainActivity.class));
				}
				else{
					Toast.makeText(getApplicationContext(), "Please check your username and password.", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "Connection error.", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			/*
			 * setCancelable iþlemin iptal edilebilirliðini belirler.
			 */
			progressDialog.setCancelable(true); 
			progressDialog.setMessage(this.progressMessage);
			progressDialog.show();
			super.onPreExecute();
		}

    	
    }
}



package org.geoloc.android.mapview;

import org.geoloc.android.mapview.R;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	Button BackLoginBT;
	EditText FullNameET,EmailET,PasswordET;
	
	User newuser;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        
	        BackLoginBT=(Button)findViewById(R.id.BackLoginBT); 
	        FullNameET=(EditText)findViewById(R.id.FullNameET);
	        EmailET=(EditText)findViewById(R.id.EmailET);
	        PasswordET=(EditText)findViewById(R.id.PasswordET);
	        
	        BackLoginBT.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
				new BackgroundProcess("Registering please wait!").execute();
					
				}
			});
	 }

class BackgroundProcess extends AsyncTask<Boolean, String, Boolean>{

	ProgressDialog progressDialog;
	String progressMessage;
	
	public BackgroundProcess(String message) {
		super();
		progressDialog = new ProgressDialog(RegisterActivity.this);
		progressMessage = message;
	}

	@Override
	protected Boolean doInBackground(Boolean... params) {	
		
		
		newuser=new User();
		newuser.setUserFullName(FullNameET.getText().toString());
		newuser.setUserEmail(EmailET.getText().toString());
		newuser.setUserPassword(PasswordET.getText().toString());
		TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		newuser.setUserIMEI(Integer.valueOf("1923"));
		
		if(FullNameET.getText().toString().equals("")|| EmailET.getText().toString().equals("")||PasswordET.getText().toString().equals(""))
			Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else
		{
			Boolean result=null;
			try {
				Log.d("RegisterBackgroundProcess", "Connecting...");
				result = CustomHttpClient.registerUser(newuser);
				Log.d("RegisterBackgroundProcess", "Result ready.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if(result != null){
				Log.d("RegisterBackgroundProcess", "Result isn't null. Returning...");
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
		Log.d("RegisterBackgroundProcess", "onPostExecute : P.Dialog dismissing.");
		if(progressDialog.isShowing())
			progressDialog.dismiss();
		Log.d("RegisterBackgroundProcess", "onPostExecute : P.Dialog dismissed.");
		
		
        if(result != null)
        {
    		if(result){
    			Toast.makeText(getApplicationContext(), "Registration completed!", Toast.LENGTH_SHORT).show();
    			Log.d("RegisterBackgroundProcess", "onPostExecute : Registration successful.");
    		}
    		else{
    			Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
    			Log.d("RegisterBackgroundProcess", "onPostExecute : Registration unsuccessful !");
    		}
        }
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		Log.d("RegisterBackgroundProcess", "OnPreExecute starting.");
		progressDialog.setCancelable(true);
		progressDialog.setMessage(this.progressMessage);
		Log.d("RegisterBackgroundProcess", "OnPreExecute settings ready.");
		progressDialog.show();
		Log.d("RegisterBackgroundProcess", "OnPreExecute P.Dialog shown");
		super.onPreExecute();
	}

	
}
}

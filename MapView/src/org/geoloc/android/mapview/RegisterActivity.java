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
		newuser.setUserIMEI(Integer.valueOf(tm.getDeviceId()));
		
		if(FullNameET.getText().toString().equals("")|| EmailET.getText().toString().equals("")||PasswordET.getText().toString().equals(""))
			Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
		else
		{
			Boolean result=null;
			try {
				result = CustomHttpClient.registerUser(newuser);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
		if(progressDialog.isShowing())
			progressDialog.dismiss();
		
        if(result!=null)
        {
    		if(result){
    			Toast.makeText(getApplicationContext(), "Registration complicated!", Toast.LENGTH_SHORT).show();
    		}
    		else{
    			Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
    		}
        }
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		progressDialog.setCancelable(true);
		progressDialog.setMessage(this.progressMessage);
		progressDialog.show();
		super.onPreExecute();
	}

	
}
}

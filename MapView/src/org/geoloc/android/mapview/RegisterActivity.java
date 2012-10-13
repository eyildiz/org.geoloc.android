package org.geoloc.android.mapview;

import org.geoloc.android.mapview.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
		
		
		
		if(FullNameET.getText().toString().equals("")||EmailET.getText().toString().equals("")||PasswordET.getText().toString().equals(""))
			return Boolean.FALSE;
		else
			return Boolean.TRUE;

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
		
		if(result){
			newuser=new User();
			newuser.setUserFullName(FullNameET.getText().toString());
			newuser.setUserEmail(EmailET.getText().toString());
			newuser.setUserPassword(PasswordET.getText().toString());
		}
		else{
			Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
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

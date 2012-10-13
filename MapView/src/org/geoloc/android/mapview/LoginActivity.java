package org.geoloc.android.mapview;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText UserNameET,PasswordET;
	Button LoginBT,RegisterBT;
	
	String usernameText="nagi";
	String passwordText="adem";
  


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       
        
        UserNameET=(EditText)findViewById(R.id.UserNameET);
        PasswordET=(EditText)findViewById(R.id.PasswordET);
        LoginBT=(Button)findViewById(R.id.LoginBT);  
        RegisterBT=(Button)findViewById(R.id.RegisterBT);  
		
        RegisterBT.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
				
			}
		});
        
        LoginBT.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new BackgroundProcess("Signing in, please wait..").execute();
			}
		});
        
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
			
			// Ýleride bu method içinde web service ile kontrol gerçekleþtirecek bir method bulunacak.
			
			if(UserNameET.getText().toString().equals(usernameText) && PasswordET.getText().toString().equals(passwordText))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;

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
				Toast.makeText(getApplicationContext(), "Signed in!", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(getApplicationContext(), "Please check your username and password.", Toast.LENGTH_SHORT).show();
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



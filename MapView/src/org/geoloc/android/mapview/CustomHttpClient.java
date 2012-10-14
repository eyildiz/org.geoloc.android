package org.geoloc.android.mapview;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CustomHttpClient {

	private static  String URL = "http://107.20.156.81:8080/horse-0.0.1-SNAPSHOT/ws/";
	private static int TIMEOUT = 10000;
	
	public static boolean checkUser(String email, String password) throws JSONException{
		
		JSONObject obj = new JSONObject();
		obj.put("email", email);
		obj.put("hash", "12345");
		
		String JsonStr = obj.toString();
		Log.d("JsonString", JsonStr);
		
		String MethodName = "authenticate";
		String responseString = "false";
		
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		
		HttpPost request = new HttpPost(URL+MethodName);
		
		try {
			request.setEntity(new ByteArrayEntity(JsonStr.getBytes("UTF8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			HttpResponse response = client.execute(request);
			if(response != null){
				responseString = EntityUtils.toString(response.getEntity());
				Log.d("JsonString", responseString);
				
			}else{
				Log.d("JsonString", "Request alýnamadý!");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(responseString.equals("true"))
			return true;
		else
			return false;
	}
	public static boolean registerUser(User user) throws JSONException
	{

		JSONObject obj = new JSONObject();
		obj.put("userFullName", user.getUserFullName());
		obj.put("userEmail", user.getUserEmail());
		obj.put("userIMEI", user.getUserIMEI());
		obj.put("userPassword", user.getUserPassword());
			
		String JsonStr = obj.toString();
		Log.d("JsonString", JsonStr);
		
		String MethodName = "registerUser";
		String responseString = "false";
		
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		
		HttpPost request = new HttpPost(URL+MethodName);

		try {
			request.setEntity(new ByteArrayEntity(JsonStr.getBytes("UTF8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			HttpResponse response = client.execute(request);
			if(response != null){
				responseString = EntityUtils.toString(response.getEntity());
				Log.d("JsonString", responseString);
				
			}else{
				Log.d("JsonString", "Request alýnamadý!");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		if(responseString.equals("true"))
			return true;
		else
			return false;
	}

}


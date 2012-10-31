package org.geoloc.android.mapview;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CustomHttpClient {

	private static  String URL = "http://107.20.156.81:8080/horse-0.0.1-SNAPSHOT/ws/";
	private static int TIMEOUT = 10000;
	
	public static boolean checkUser(String email, String password) throws JSONException{
		
		JSONObject obj = new JSONObject();
		obj.put("email", email);
		obj.put("hash", password);
		
		String JsonStr = obj.toString();
		Log.d("CustomHTTPClient-CheckUser", "JSON POST Message is : "+JsonStr);
		
		String MethodName = "authenticate";
		String responseString = "false";
		
		Log.d("CustomHTTPClient-CheckUser", "Setting Parameters...");
		
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		Log.d("CustomHTTPClient-CheckUser", "Parameters set.");
		
		HttpClient client = new DefaultHttpClient(parameters);
		
		HttpPost request = new HttpPost(URL+MethodName);
		
		try {
			Log.d("CustomHTTPClient-CheckUser", "POST Message attaching to HTTP Post Request...");
			request.setEntity(new ByteArrayEntity(JsonStr.getBytes("UTF8")));
			Log.d("CustomHTTPClient-CheckUser", "POST Message attached to HTTP Post Request...");
		} catch (UnsupportedEncodingException e) {
			Log.e("CustomHTTPClient-CheckUser", "<ERROR> : POST Message attaching to HTTP Post Request...");
			e.printStackTrace();
		}
		
		try {
			Log.d("CustomHTTPClient-CheckUser", "Request executing, connecting to web service...");
			HttpResponse response = client.execute(request);
			Log.d("CustomHTTPClient-CheckUser", "Response Ready.");
			
			if(response != null){
				Log.d("CustomHTTPClient-CheckUser", "Response isn't null. Getting response message...");
				
				if(response.getEntity() == null)
				{
					Log.d("CustomHTTPClient-CheckUser", "Response isn't null but response message is empty!");
				}else{
					responseString = EntityUtils.toString(response.getEntity());
					Log.d("CustomHTTPClient-CheckUser", "Response string ready.");
					Log.d("CustomHTTPClient-CheckUser", "Server says : "+responseString);
				}
								
			}
			else{
				Log.d("CustomHTTPClient-CheckUser", "Null Response! May be connection problem occured.");
			}
		} catch (ClientProtocolException e) {
			Log.e("CustomHTTPClient-CheckUser", "<ERROR> : Protocol Exception");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("CustomHTTPClient-CheckUser", "<ERROR> : IOException ");
			e.printStackTrace();
		}
		
		if(responseString.equals("true"))
			return true;
		else
			return false;
	}
	
	public static boolean registerUser(User user) throws JSONException	{

		JSONObject obj = new JSONObject();
		obj.put("userFullName", user.getUserFullName());
		obj.put("userEmail", user.getUserEmail());
		obj.put("IMEI", user.getUserIMEI());
		obj.put("userPassword", user.getUserPassword());
		obj.put("username", "Default");
			
		String JsonStr = obj.toString();
		Log.d("CustomHTTPClient-RegisterUser", "JSON POST Message is : "+JsonStr);
		
		String MethodName = "registerUser";
		String responseString = "false";
		
		Log.d("CustomHTTPClient-RegisterUser", "Setting Parameters...");
		
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		
		Log.d("CustomHTTPClient-RegisterUser", "Parameters set.");
		
		HttpPost request = new HttpPost(URL+MethodName);

		try {
			Log.d("CustomHTTPClient-RegisterUser", "POST Message attaching to HTTP Post Request...");
			request.setEntity(new ByteArrayEntity(JsonStr.getBytes("UTF8")));
			Log.d("CustomHTTPClient-RegisterUser", "POST Message attached to HTTP Post Request...");
		} catch (UnsupportedEncodingException e) {
			Log.e("CustomHTTPClient-RegisterUser", "<ERROR> : POST Message attaching to HTTP Post Request...");
			e.printStackTrace();
		}
		
		try {
			Log.e("CustomHTTPClient-RegisterUser", "Request executing, connecting to webservice...");
			HttpResponse response = client.execute(request);
			Log.e("CustomHTTPClient-RegisterUser", "Execution completed, response ready.");
			
			if(response != null){
				Log.e("CustomHTTPClient-RegisterUser", "Response isn't null. Checking response message.");
				
				if(response.getEntity() == null)
				{
					Log.d("CustomHTTPClient-RegisterUser", "Response isn't null but response message is empty!");
				}else{
					responseString = EntityUtils.toString(response.getEntity());
					Log.d("CustomHTTPClient-RegisterUser", "Response string ready.");
					Log.d("CustomHTTPClient-RegisterUser", "Server says : "+responseString);
				}
				
			}else{
				Log.d("CustomHTTPClient-RegisterUser", "Null Response! May be connection problem occured.");
			
			}
		
		} catch (ClientProtocolException e) {
			Log.e("CustomHTTPClient-RegisterUser", "<ERROR> : Protocol Exception");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("CustomHTTPClient-RegisterUser", "<ERROR> : IOException ");
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<User> getAllUserLocations(){
		
		String methodName = "getAllUserLocations";
		String responseString = null;
		
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		HttpGet request = new HttpGet(URL+methodName);
		
		try {
			HttpResponse response = client.execute(request);
			if(response != null){
				responseString = EntityUtils.toString(response.getEntity());
				Log.d("getAllUserLocations", responseString);
			}
			else{
				Log.e("getAllUserLocations", "Response is null ! ");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}


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

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class CustomHttpClient {

	private static  String URL = "http://107.20.156.81:8080/GeoLocWebServices-0.1/ws/";
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
					return false;
				}else{
					responseString = EntityUtils.toString(response.getEntity());
					Log.d("CustomHTTPClient-RegisterUser", "Response string ready.");
					Log.d("CustomHTTPClient-RegisterUser", "Server says : "+responseString);
					return true;
				}
				
			}else{
				Log.d("CustomHTTPClient-RegisterUser", "Null Response! May be connection problem occured.");
				return false;
			}
		
		} catch (ClientProtocolException e) {
			Log.e("CustomHTTPClient-RegisterUser", "<ERROR> : Protocol Exception");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.e("CustomHTTPClient-RegisterUser", "<ERROR> : IOException ");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public static ArrayList<LocationData> getAllUserLocations(){
		
		String methodName = "getAllUserLocations";
		String responseString = null;
		
		Log.d("CustomHTTPClient-getAllUserLocations", "Setting Parameters...");
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		HttpGet request = new HttpGet(URL+methodName);
		
		Log.d("CustomHTTPClient-getAllUserLocations", "Parameters set.");
		
		
		try {
			Log.e("CustomHTTPClient-getAllUserLocations", "Request executing, connecting to webservice...");
			HttpResponse response = client.execute(request);
			Log.e("CustomHTTPClient-getAllUserLocations", "Execution completed, response ready.");
			
			if(response != null){
				Log.e("CustomHTTPClient-getAllUserLocations", "Response isn't null. Checking response message.");
				
				if(response.getEntity() == null)
				{
					Log.d("CustomHTTPClient-getAllUserLocations", "Response isn't null but response message is empty!");
					return null;
				}else{
					responseString = EntityUtils.toString(response.getEntity());
					Log.d("CustomHTTPClient-getAllUserLocations", "Response string ready.");
					Log.d("CustomHTTPClient-getAllUserLocations", "Server says : "+responseString);
					
					
					try {
						Log.d("CustomHTTPClient-getAllUserLocations", "JSON Parsing start.");
						JSONArray allData = new JSONArray(responseString);						
						
						
						ArrayList<LocationData> locations = new ArrayList<LocationData>();
						
						for(int i=0; i<allData.length(); i++){
							
							JSONObject jsonobj = allData.getJSONObject(i);
							LocationData location = new LocationData();
							
							location.setUserID(jsonobj.getInt("userID"));
							location.setLatitude(jsonobj.getDouble("latitude"));
							location.setLongitude(jsonobj.getDouble("longitude"));
							
							locations.add(location);
							
						}
						
						Log.d("CustomHTTPClient-getAllUserLocations", "Parsing completed : ");	
						Log.d("CustomHTTPClient-getAllUserLocations", "Result : "+allData.length() + " objects.");	
						
						return locations;
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}
				
			}else{
				Log.d("CustomHTTPClient-getAllUserLocations", "Null Response! May be connection problem occured.");
				return null;
			}
		
		} catch (ClientProtocolException e) {
			Log.e("CustomHTTPClient-getAllUserLocations", "<ERROR> : Protocol Exception");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e("CustomHTTPClient-getAllUserLocations", "<ERROR> : IOException ");
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<User> getAllUsers(){
		
		String methodName = "getAllUsers";
		String responseString = null;
		
		Log.d("CustomHTTPClient-getAllUsers", "Setting Parameters...");
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(parameters, TIMEOUT);
		
		HttpClient client = new DefaultHttpClient(parameters);
		HttpGet request = new HttpGet(URL+methodName);
		
		Log.d("CustomHTTPClient-getAllUsers", "Parameters set.");
		
		
		try {
			Log.e("CustomHTTPClient-getAllUsers", "Request executing, connecting to webservice...");
			HttpResponse response = client.execute(request);
			Log.e("CustomHTTPClient-getAllUsers", "Execution completed, response ready.");
			
			if(response != null){
				Log.e("CustomHTTPClient-getAllUsers", "Response isn't null. Checking response message.");
				
				if(response.getEntity() == null)
				{
					Log.d("CustomHTTPClient-getAllUsers", "Response isn't null but response message is empty!");
					return null;
				}else{
					responseString = EntityUtils.toString(response.getEntity());
					Log.d("CustomHTTPClient-getAllUsers", "Response string ready.");
					Log.d("CustomHTTPClient-getAllUsers", "Server says : "+responseString);
					
					
					try {
						Log.d("CustomHTTPClient-getAllUsers", "JSON Parsing start.");
						JSONArray allData = new JSONArray(responseString);	
						
						ArrayList<User> users = new ArrayList<User>();
						
						for(int i=0; i<allData.length(); i++){
							
							JSONObject JSONObjMember = allData.getJSONObject(i);
							LocationData location = new LocationData();
							
							JSONObject LocationJSONObj =JSONObjMember.getJSONObject("userLocation");
							
							location.setUserID(LocationJSONObj.getInt("userID"));
							location.setLatitude(LocationJSONObj.getDouble("latitude"));
							location.setLongitude(LocationJSONObj.getDouble("longitude"));
							
							User user = new User();
							user.setLocationData(location);
							user.setUserPassword(JSONObjMember.getString("passwordHash"));
							user.setUserID(JSONObjMember.getInt("userID"));
							user.setUserFullName(JSONObjMember.getString("userFullName"));
							user.setUserEmail(JSONObjMember.getString("userEmail"));
							user.setUserIMEI(JSONObjMember.getString("imei"));
							
							users.add(user);
																					
						}
						
						Log.d("CustomHTTPClient-getAllUsers", "JSON result parse completed.");
						Log.d("CustomHTTPClient-getAllUsers", "Number of objects : "+users.size());
						
						return users;
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}
				
			}else{
				Log.d("CustomHTTPClient-getAllUsers", "Null Response! May be connection problem occured.");
				return null;
			}
		
		} catch (ClientProtocolException e) {
			Log.e("CustomHTTPClient-getAllUsers", "<ERROR> : Protocol Exception");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e("CustomHTTPClient-getAllUsers", "<ERROR> : IOException ");
			e.printStackTrace();
			return null;
		}

	}
	
	public static boolean checkInternetConnection(Context c) {
		ConnectivityManager cm = (ConnectivityManager)c.getSystemService(c.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
		&& cm.getActiveNetworkInfo().isAvailable()
		&& cm.getActiveNetworkInfo().isConnected()) {
		return true;
		}
		else {
		Log.v("Internet", "Internet Connection Not Present");
		return false;
		} }
}


package org.geoloc.android.mapview;

public class User {
	
	private int userID;
	private String username;
	private String userFullName;
	private String userEmail;
	private String userPassword;
	private double userLatitude;
	private double userLongitude;
	private String avatarPath;
	private String userIMEI;
	
	private LocationData userLocation;
	
	public LocationData getLocationData(){
		return userLocation;
	}
	
	public void setLocationData(LocationData loc){
		this.userLocation  = loc;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserFullName() {
		return userFullName;
	}
	
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
	public double getUserLatitude() {
		return userLatitude;
	}
	
	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}
	
	public double getUserLongitude() {
		return userLongitude;
	}
	
	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}
	
	public String getAvatarPath() {
		return avatarPath;
	}
	
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	
	public String getUserIMEI() {
		return userIMEI;
	}
	
	public void setUserIMEI(String userIMEI) {
		this.userIMEI = userIMEI;
	}
	
	//
	
}
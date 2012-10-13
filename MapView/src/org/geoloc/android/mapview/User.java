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
	
	private int getUserID() {
		return userID;
	}
	private void setUserID(int userID) {
		this.userID = userID;
	}
	private String getUsername() {
		return username;
	}
	private void setUsername(String username) {
		this.username = username;
	}
	private String getUserFullName() {
		return userFullName;
	}
	private void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	private String getUserEmail() {
		return userEmail;
	}
	private void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	private String getUserPassword() {
		return userPassword;
	}
	private void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	private double getUserLatitude() {
		return userLatitude;
	}
	private void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}
	private double getUserLongitude() {
		return userLongitude;
	}
	private void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}
	private String getAvatarPath() {
		return avatarPath;
	}
	private void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

}

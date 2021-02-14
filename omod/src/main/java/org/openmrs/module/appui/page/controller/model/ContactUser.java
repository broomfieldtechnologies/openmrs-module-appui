package org.openmrs.module.appui.page.controller.model;

public class ContactUser {
    String email;
    String familyName;
    String givenName;
    String userName;
    String systemId;
    Integer userId;
    String uuId;
    String location;
    String locationId;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	@Override
	public String toString() {
		return "ContactUser [email=" + email + ", familyName=" + familyName + ", givenName=" + givenName + ", userName="
				+ userName + ", systemId=" + systemId + ", userId=" + userId + ", uuId=" + uuId + "]";
	}
    
}

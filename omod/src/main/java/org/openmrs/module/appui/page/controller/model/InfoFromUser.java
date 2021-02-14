/**
 * 
 */
package org.openmrs.module.appui.page.controller.model;

import java.util.List;

/**
 * @author Karthik.
 *
 */
public class InfoFromUser {
	private ContactUser contactUser;
	private ContactUserLocation contactUserLocation;
	private ContactUserEnterprise contactUserEnterprise;
	public ContactUserEnterprise getContactUserEnterprise() {
		return contactUserEnterprise;
	}
	public void setContactUserEnterprise(ContactUserEnterprise contactUserEnterprise) {
		this.contactUserEnterprise = contactUserEnterprise;
	}
	public ContactUser getContactUser() {
		return contactUser;
	}
	public void setContactUser(ContactUser contactUser) {
		this.contactUser = contactUser;
	}
	public ContactUserLocation getContactUserLocation() {
		return contactUserLocation;
	}
	public void setContactUserLocation(ContactUserLocation contactUserLocation) {
		this.contactUserLocation = contactUserLocation;
	}
	@Override
	public String toString() {
		return "InfoFromUser [contactUser=" + contactUser + ", contactUserLocation=" + contactUserLocation
				+ ", contactUserEnterprise=" + contactUserEnterprise + "]";
	}
}

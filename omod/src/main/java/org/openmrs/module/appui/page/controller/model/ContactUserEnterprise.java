package org.openmrs.module.appui.page.controller.model;

public class ContactUserEnterprise {
	private String name;
	private String guid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Override
	public String toString() {
		return "ContactUserEnterprise [name=" + name + ", guid=" + guid + "]";
	}
}

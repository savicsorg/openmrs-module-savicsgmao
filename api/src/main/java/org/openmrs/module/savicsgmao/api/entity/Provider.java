package org.openmrs.module.savicsgmao.api.entity;

import java.util.Date;
import java.util.UUID;

public class Provider implements java.io.Serializable {
	
	private Integer providerId;
	
	private String uuid;
	
	private String name;
	
	private String contact;
	
	private Date address;
	
	public Provider() {
		uuid = UUID.randomUUID().toString();
	}
	
	public Provider(Integer providerId, String uuid, String name, String contact, Date address) {
		this.providerId = providerId;
		this.uuid = uuid;
		this.name = name;
		this.contact = contact;
		this.address = address;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Date getAddress() {
		return address;
	}
	
	public void setAddress(Date address) {
		this.address = address;
	}
}

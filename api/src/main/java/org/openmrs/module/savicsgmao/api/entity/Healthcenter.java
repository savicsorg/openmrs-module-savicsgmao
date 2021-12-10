package org.openmrs.module.savicsgmao.api.entity;

// Generated Dec 9, 2021 11:58:16 AM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;

/**
 * Healthcenter generated by hbm2java
 */
public class Healthcenter extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer id;
	
	private District district;
	
	private String uuid;
	
	private String name;
	
	private String code;
	
	private Date lastmodified;
	
	private Date creation;
	
	private Set services = new HashSet(0);
	
	public Healthcenter() {
	}
	
	public Healthcenter(int id, District district, String uuid, String name, String code) {
		this.id = id;
		this.district = district;
		this.uuid = uuid;
		this.name = name;
		this.code = code;
	}
	
	public Healthcenter(int id, District district, String uuid, String name, String code, Date lastmodified, Date creation,
	    Set services) {
		this.id = id;
		this.district = district;
		this.uuid = uuid;
		this.name = name;
		this.code = code;
		this.lastmodified = lastmodified;
		this.creation = creation;
		this.services = services;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public District getDistrict() {
		return this.district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	@Override
	public String getUuid() {
		return this.uuid;
	}
	
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Date getLastmodified() {
		return this.lastmodified;
	}
	
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	public Date getCreation() {
		return this.creation;
	}
	
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
	public Set getServices() {
		return this.services;
	}
	
	public void setServices(Set services) {
		this.services = services;
	}
	
}

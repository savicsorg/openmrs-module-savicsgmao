package org.openmrs.module.savicsgmao.api.entity;

import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

/**
 * District generated by hbm2java
 */
public class District extends BaseOpenmrsData {
	
	private Integer districtId;
	
	private Region region;
	
	private String uuid;
	
	private String districtName;
	
	private String districtCode;
	
	private Set SiteLocations = new HashSet(0);
	
	public Integer getDistrictId() {
		return this.districtId;
	}
	
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	
	public Region getRegion() {
		return this.region;
	}
	
	public void setRegion(Region Region) {
		this.region = Region;
	}
	
	public String getDistrictName() {
		return this.districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getDistrictCode() {
		return this.districtCode;
	}
	
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	public Set getSiteLocations() {
		return this.SiteLocations;
	}
	
	public void setSiteLocations(Set SiteLocations) {
		this.SiteLocations = SiteLocations;
	}
	
	@Override
	public User getCreator() {
		return creator;
	}
	
	@Override
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Override
	public Integer getId() {
		return getDistrictId();
	}
	
	@Override
	public void setId(Integer id) {
		setDistrictId(id);
	}
}

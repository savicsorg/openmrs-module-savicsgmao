package org.openmrs.module.savicsgmao.api.entity;

import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

/**
 * SiteLocation generated by hbm2java
 */
public class SiteLocation extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer siteLocationId;
	
	private District District;
	
	private String uuid;
	
	private String locationName;
	
	private String locationCode;
	
	private Set Agents = new HashSet(0);
	
	public SiteLocation() {
	}
	
	public SiteLocation(District District, String uuid, String locationName, String locationCode) {
		this.District = District;
		this.uuid = uuid;
		this.locationName = locationName;
		this.locationCode = locationCode;
	}
	
	public SiteLocation(District District, String uuid, String locationName, String locationCode, Set Agents) {
		this.District = District;
		this.uuid = uuid;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.Agents = Agents;
	}
	
	public Integer getSiteLocationId() {
		return this.siteLocationId;
	}
	
	public void setSiteLocationId(Integer siteLocationId) {
		this.siteLocationId = siteLocationId;
	}
	
	public District getDistrict() {
		return this.District;
	}
	
	public void setDistrict(District District) {
		this.District = District;
	}
	
	public String getLocationName() {
		return this.locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getLocationCode() {
		return this.locationCode;
	}
	
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	public Set getAgents() {
		return this.Agents;
	}
	
	public void setAgents(Set Agents) {
		this.Agents = Agents;
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
		return getSiteLocationId();
	}
	
	@Override
	public void setId(Integer id) {
		setSiteLocationId(id);
	}
}

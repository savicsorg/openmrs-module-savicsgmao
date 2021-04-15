package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 13, 2021 3:01:21 PM by Hibernate Tools 4.3.1

/**
 * District generated by hbm2java
 */
public class District implements java.io.Serializable {
	
	private Integer districtId;
	
	private String uuid;
	
	private String districtName;
	
	private String districtCode;
	
	private int regionId;
	
	public District() {
	}
	
	public District(String uuid, String districtName, String districtCode, int regionId) {
		this.uuid = uuid;
		this.districtName = districtName;
		this.districtCode = districtCode;
		this.regionId = regionId;
	}
	
	public Integer getDistrictId() {
		return this.districtId;
	}
	
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
	public int getRegionId() {
		return this.regionId;
	}
	
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	
}

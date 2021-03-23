/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.SiteLocation")
@Table(name = "gmao_site_location", uniqueConstraints = { @UniqueConstraint(columnNames = { "location_code" }) })
public class SiteLocation extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "site_location_id", nullable = false)
	private Integer siteLocationId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "location_name", nullable = false, length = 250)
	private String locationName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "location_code", nullable = false, length = 250)
	private String locationCode;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "district_id", nullable = false)
	private int districtId;
	
	public SiteLocation() {
	}
	
	public SiteLocation(Integer siteLocationId) {
		this.siteLocationId = siteLocationId;
	}
	
	public SiteLocation(Integer siteLocationId, String locationName, String locationCode, int districtId) {
		this.siteLocationId = siteLocationId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.districtId = districtId;
	}
	
	public Integer getSiteLocationId() {
		return siteLocationId;
	}
	
	public void setSiteLocationId(Integer siteLocationId) {
		this.siteLocationId = siteLocationId;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getLocationCode() {
		return locationCode;
	}
	
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	public int getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (siteLocationId != null ? siteLocationId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof SiteLocation)) {
			return false;
		}
		SiteLocation other = (SiteLocation) object;
		if ((this.siteLocationId == null && other.siteLocationId != null)
		        || (this.siteLocationId != null && !this.siteLocationId.equals(other.siteLocationId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoSiteLocation[ siteLocationId=" + siteLocationId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getSiteLocationId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setSiteLocationId(intgr);
	}
	
}

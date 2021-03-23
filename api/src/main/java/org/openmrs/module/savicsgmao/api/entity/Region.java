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
@Entity(name = "savicsgmao.Region")
@Table(name = "gmao_region", uniqueConstraints = { @UniqueConstraint(columnNames = { "region_code" }) })
public class Region extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "region_id", nullable = false)
	private Integer regionId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "region_name", nullable = false, length = 250)
	private String regionName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "region_code", nullable = false, length = 250)
	private String regionCode;
	
	public Region() {
	}
	
	public Region(Integer regionId) {
		this.regionId = regionId;
	}
	
	public Region(Integer regionId, String regionName, String regionCode) {
		this.regionId = regionId;
		this.regionName = regionName;
		this.regionCode = regionCode;
	}
	
	public Integer getRegionId() {
		return regionId;
	}
	
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	public String getRegionName() {
		return regionName;
	}
	
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (regionId != null ? regionId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Region)) {
			return false;
		}
		Region other = (Region) object;
		if ((this.regionId == null && other.regionId != null)
		        || (this.regionId != null && !this.regionId.equals(other.regionId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoRegion[ regionId=" + regionId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getRegionId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setRegionId(intgr);
	}
	
}

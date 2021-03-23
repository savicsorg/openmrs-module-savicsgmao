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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.District")
@Table(name = "gmao_district", uniqueConstraints = { @UniqueConstraint(columnNames = { "district_code" }) })
public class District extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "district_id", nullable = false)
	private Integer districtId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "district_name", nullable = false, length = 250)
	private String districtName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "district_code", nullable = false, length = 250)
	private String districtCode;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "region_id", nullable = false)
	private int regionId;
	
	public District() {
	}
	
	public District(Integer districtId) {
		this.districtId = districtId;
	}
	
	public District(Integer districtId, String districtName, String districtCode, int regionId) {
		this.districtId = districtId;
		this.districtName = districtName;
		this.districtCode = districtCode;
		this.regionId = regionId;
	}
	
	public Integer getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	
	public String getDistrictName() {
		return districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getDistrictCode() {
		return districtCode;
	}
	
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	public int getRegionId() {
		return regionId;
	}
	
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (districtId != null ? districtId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof District)) {
			return false;
		}
		District other = (District) object;
		if ((this.districtId == null && other.districtId != null)
		        || (this.districtId != null && !this.districtId.equals(other.districtId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoDistrict[ districtId=" + districtId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getDistrictId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setDistrictId(intgr);
	}
	
}

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity
@Table(name = "gmao_agent")
public class Agent extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "agent_id", nullable = false)
	private Integer agentId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "full_name", nullable = false, length = 250)
	private String fullName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "agent_address", nullable = false, length = 250)
	private String agentAddress;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "phone_number", nullable = false, length = 250)
	private String phoneNumber;
	
	@Size(max = 250)
	@Column(name = "roll_number", length = 250)
	private String rollNumber;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "is_external", nullable = false)
	private short isExternal;
	
	@Column(name = "department_id")
	private Integer departmentId;
	
	@Column(name = "site_location_id")
	private Integer siteLocationId;
	
	public Agent() {
	}
	
	public Agent(Integer agentId) {
		this.agentId = agentId;
	}
	
	public Agent(Integer agentId, String fullName, String agentAddress, String phoneNumber, short isExternal) {
		this.agentId = agentId;
		this.fullName = fullName;
		this.agentAddress = agentAddress;
		this.phoneNumber = phoneNumber;
		this.isExternal = isExternal;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAgentAddress() {
		return agentAddress;
	}
	
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRollNumber() {
		return rollNumber;
	}
	
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	
	public short getIsExternal() {
		return isExternal;
	}
	
	public void setIsExternal(short isExternal) {
		this.isExternal = isExternal;
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public Integer getSiteLocationId() {
		return siteLocationId;
	}
	
	public void setSiteLocationId(Integer siteLocationId) {
		this.siteLocationId = siteLocationId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (agentId != null ? agentId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Agent)) {
			return false;
		}
		Agent other = (Agent) object;
		if ((this.agentId == null && other.agentId != null) || (this.agentId != null && !this.agentId.equals(other.agentId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoAgent[ agentId=" + agentId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getAgentId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setAgentId(intgr);
	}
	
}

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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.MaintenanceReason")
@Table(name = "gmao_maintenance_reason")
public class MaintenanceReason extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "maintenance_reason_id", nullable = false)
	private Integer maintenanceReasonId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "reason_code", nullable = false, length = 250)
	private String reasonCode;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "reason_name", nullable = false, length = 250)
	private String reasonName;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "details", nullable = false, length = 65535)
	private String details;
	
	public MaintenanceReason() {
	}
	
	public MaintenanceReason(Integer maintenanceReasonId) {
		this.maintenanceReasonId = maintenanceReasonId;
	}
	
	public MaintenanceReason(Integer maintenanceReasonId, String reasonCode, String reasonName, String details) {
		this.maintenanceReasonId = maintenanceReasonId;
		this.reasonCode = reasonCode;
		this.reasonName = reasonName;
		this.details = details;
	}
	
	public Integer getMaintenanceReasonId() {
		return maintenanceReasonId;
	}
	
	public void setMaintenanceReasonId(Integer maintenanceReasonId) {
		this.maintenanceReasonId = maintenanceReasonId;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public String getReasonName() {
		return reasonName;
	}
	
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (maintenanceReasonId != null ? maintenanceReasonId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof MaintenanceReason)) {
			return false;
		}
		MaintenanceReason other = (MaintenanceReason) object;
		if ((this.maintenanceReasonId == null && other.maintenanceReasonId != null)
		        || (this.maintenanceReasonId != null && !this.maintenanceReasonId.equals(other.maintenanceReasonId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoMaintenanceReason[ maintenanceReasonId=" + maintenanceReasonId
		        + " ]";
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceReasonId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setMaintenanceReasonId(intgr);
	}
	
}

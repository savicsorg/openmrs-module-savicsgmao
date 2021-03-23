/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.Maintenance")
@Table(name = "gmao_maintenance")
public class Maintenance extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "maintenance_id", nullable = false)
	private Integer maintenanceId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "maintenance_type_id", nullable = false)
	private int maintenanceTypeId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "designation", nullable = false, length = 250)
	private String designation;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "startdate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	
	@Column(name = "enddate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "details", nullable = false, length = 65535)
	private String details;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_id", nullable = false)
	private int equipmentId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "agent_id", nullable = false)
	private int agentId;
	
	public Maintenance() {
	}
	
	public Maintenance(Integer maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	
	public Maintenance(Integer maintenanceId, int maintenanceTypeId, String designation, Date startdate, String details,
	    int equipmentId, int agentId) {
		this.maintenanceId = maintenanceId;
		this.maintenanceTypeId = maintenanceTypeId;
		this.designation = designation;
		this.startdate = startdate;
		this.details = details;
		this.equipmentId = equipmentId;
		this.agentId = agentId;
	}
	
	public Integer getMaintenanceId() {
		return maintenanceId;
	}
	
	public void setMaintenanceId(Integer maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	
	public int getMaintenanceTypeId() {
		return maintenanceTypeId;
	}
	
	public void setMaintenanceTypeId(int maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public int getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public int getAgentId() {
		return agentId;
	}
	
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (maintenanceId != null ? maintenanceId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Maintenance)) {
			return false;
		}
		Maintenance other = (Maintenance) object;
		if ((this.maintenanceId == null && other.maintenanceId != null)
		        || (this.maintenanceId != null && !this.maintenanceId.equals(other.maintenanceId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoMaintenance[ maintenanceId=" + maintenanceId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setMaintenanceId(intgr);
	}
	
}

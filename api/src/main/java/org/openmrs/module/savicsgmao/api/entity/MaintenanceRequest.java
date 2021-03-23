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
@Entity(name = "savicsgmao.MaintenanceRequest")
@Table(name = "gmao_maintenance_request")
public class MaintenanceRequest extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "maintenance_request_id", nullable = false)
	private Integer maintenanceRequestId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "system_number", nullable = false, length = 250)
	private String systemNumber;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "department_id", nullable = false)
	private int departmentId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "register_number", nullable = false, length = 250)
	private String registerNumber;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "startdate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "nature_of_work", nullable = false, length = 250)
	private String natureOfWork;
	
	@Column(name = "enddate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "motif", nullable = false, length = 65535)
	private String motif;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "request_priority", nullable = false)
	private int requestPriority;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_id", nullable = false)
	private int equipmentId;
	
	public MaintenanceRequest() {
	}
	
	public MaintenanceRequest(Integer maintenanceRequestId) {
		this.maintenanceRequestId = maintenanceRequestId;
	}
	
	public MaintenanceRequest(Integer maintenanceRequestId, String systemNumber, int departmentId, String registerNumber,
	    Date startdate, String natureOfWork, String motif, int requestPriority, int equipmentId) {
		this.maintenanceRequestId = maintenanceRequestId;
		this.systemNumber = systemNumber;
		this.departmentId = departmentId;
		this.registerNumber = registerNumber;
		this.startdate = startdate;
		this.natureOfWork = natureOfWork;
		this.motif = motif;
		this.requestPriority = requestPriority;
		this.equipmentId = equipmentId;
	}
	
	public Integer getMaintenanceRequestId() {
		return maintenanceRequestId;
	}
	
	public void setMaintenanceRequestId(Integer maintenanceRequestId) {
		this.maintenanceRequestId = maintenanceRequestId;
	}
	
	public String getSystemNumber() {
		return systemNumber;
	}
	
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getRegisterNumber() {
		return registerNumber;
	}
	
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public String getNatureOfWork() {
		return natureOfWork;
	}
	
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getMotif() {
		return motif;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public int getRequestPriority() {
		return requestPriority;
	}
	
	public void setRequestPriority(int requestPriority) {
		this.requestPriority = requestPriority;
	}
	
	public int getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (maintenanceRequestId != null ? maintenanceRequestId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof MaintenanceRequest)) {
			return false;
		}
		MaintenanceRequest other = (MaintenanceRequest) object;
		if ((this.maintenanceRequestId == null && other.maintenanceRequestId != null)
		        || (this.maintenanceRequestId != null && !this.maintenanceRequestId.equals(other.maintenanceRequestId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoMaintenanceRequest[ maintenanceRequestId="
		        + maintenanceRequestId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceRequestId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setMaintenanceRequestId(intgr);
	}
	
}

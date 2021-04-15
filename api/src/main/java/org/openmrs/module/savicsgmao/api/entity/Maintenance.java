package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 13, 2021 3:01:21 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Maintenance generated by hbm2java
 */
public class Maintenance implements java.io.Serializable {
	
	private Integer maintenanceId;
	
	private String uuid;
	
	private int maintenanceTypeId;
	
	private String designation;
	
	private Date startdate;
	
	private Date enddate;
	
	private String details;
	
	private int equipmentId;
	
	private int agentId;
	
	public Maintenance() {
	}
	
	public Maintenance(String uuid, int maintenanceTypeId, String designation, Date startdate, String details,
	    int equipmentId, int agentId) {
		this.uuid = uuid;
		this.maintenanceTypeId = maintenanceTypeId;
		this.designation = designation;
		this.startdate = startdate;
		this.details = details;
		this.equipmentId = equipmentId;
		this.agentId = agentId;
	}
	
	public Maintenance(String uuid, int maintenanceTypeId, String designation, Date startdate, Date enddate, String details,
	    int equipmentId, int agentId) {
		this.uuid = uuid;
		this.maintenanceTypeId = maintenanceTypeId;
		this.designation = designation;
		this.startdate = startdate;
		this.enddate = enddate;
		this.details = details;
		this.equipmentId = equipmentId;
		this.agentId = agentId;
	}
	
	public Integer getMaintenanceId() {
		return this.maintenanceId;
	}
	
	public void setMaintenanceId(Integer maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public int getMaintenanceTypeId() {
		return this.maintenanceTypeId;
	}
	
	public void setMaintenanceTypeId(int maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	
	public String getDesignation() {
		return this.designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public Date getStartdate() {
		return this.startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Date getEnddate() {
		return this.enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getDetails() {
		return this.details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public int getEquipmentId() {
		return this.equipmentId;
	}
	
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public int getAgentId() {
		return this.agentId;
	}
	
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
}

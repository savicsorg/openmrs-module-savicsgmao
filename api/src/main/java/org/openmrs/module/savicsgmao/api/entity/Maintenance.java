package org.openmrs.module.savicsgmao.api.entity;

// Generated Dec 9, 2021 11:58:16 AM by Hibernate Tools 4.3.1

import java.util.Date;
import org.openmrs.BaseOpenmrsData;

/**
 * Maintenance generated by hbm2java
 */
public class Maintenance extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer id;
	
	private Equipment equipment;
	
	private MaintenanceRequest maintenanceRequest;
	
	private MaintenanceType maintenanceType;
	
	private String uuid;
	
	private String name;
	
	private String description;
	
	private Date startdate;
	
	private Date enddate;
	
	private short status;
	
	private String doneby;
	
	private Date lastmodified;
	
	private Date creation;
	
	public Maintenance() {
	}
	
	public Maintenance(int id, Equipment Equipment, MaintenanceType MaintenanceType, String uuid, String name,
	    String description, Date startdate, short status) {
		this.id = id;
		this.equipment = Equipment;
		this.maintenanceType = MaintenanceType;
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.startdate = startdate;
		this.status = status;
	}
	
	public Maintenance(int id, Equipment Equipment, MaintenanceRequest MaintenanceRequest, MaintenanceType MaintenanceType,
	    String uuid, String name, String description, Date startdate, Date enddate, short status, String doneby,
	    Date lastmodified, Date creation) {
		this.id = id;
		this.equipment = Equipment;
		this.maintenanceRequest = MaintenanceRequest;
		this.maintenanceType = MaintenanceType;
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.status = status;
		this.doneby = doneby;
		this.lastmodified = lastmodified;
		this.creation = creation;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Equipment getEquipment() {
		return this.equipment;
	}
	
	public void setEquipment(Equipment Equipment) {
		this.equipment = Equipment;
	}
	
	public MaintenanceRequest getMaintenanceRequest() {
		return this.maintenanceRequest;
	}
	
	public void setMaintenanceRequest(MaintenanceRequest MaintenanceRequest) {
		this.maintenanceRequest = MaintenanceRequest;
	}
	
	public MaintenanceType getMaintenanceType() {
		return this.maintenanceType;
	}
	
	public void setMaintenanceType(MaintenanceType MaintenanceType) {
		this.maintenanceType = MaintenanceType;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public short getStatus() {
		return this.status;
	}
	
	public void setStatus(short status) {
		this.status = status;
	}
	
	public String getDoneby() {
		return this.doneby;
	}
	
	public void setDoneby(String doneby) {
		this.doneby = doneby;
	}
	
	public Date getLastmodified() {
		return this.lastmodified;
	}
	
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	public Date getCreation() {
		return this.creation;
	}
	
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
}

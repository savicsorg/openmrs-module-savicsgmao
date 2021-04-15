package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 13, 2021 3:01:21 PM by Hibernate Tools 4.3.1

/**
 * MaintenanceType generated by hbm2java
 */
public class MaintenanceType implements java.io.Serializable {
	
	private Integer maintenanceTypeId;
	
	private String uuid;
	
	private String typeCode;
	
	private String typeName;
	
	public MaintenanceType() {
	}
	
	public MaintenanceType(String uuid, String typeCode, String typeName) {
		this.uuid = uuid;
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	
	public Integer getMaintenanceTypeId() {
		return this.maintenanceTypeId;
	}
	
	public void setMaintenanceTypeId(Integer maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTypeCode() {
		return this.typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}

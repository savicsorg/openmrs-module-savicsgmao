package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 13, 2021 3:01:21 PM by Hibernate Tools 4.3.1

import java.util.UUID;

/**
 * Task generated by hbm2java
 */
public class Task implements java.io.Serializable {
	
	private Integer taskId;
	
	private String uuid;
	
	private String taskName;
	
	private String taskProcedure;
	
	private int equipmentTypeId;
	
	public Task() {
		uuid = UUID.randomUUID().toString();
	}
	
	public Task(String uuid, String taskName, String taskProcedure, int equipmentTypeId) {
		this.uuid = uuid;
		this.taskName = taskName;
		this.taskProcedure = taskProcedure;
		this.equipmentTypeId = equipmentTypeId;
	}
	
	public Integer getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskProcedure() {
		return this.taskProcedure;
	}
	
	public void setTaskProcedure(String taskProcedure) {
		this.taskProcedure = taskProcedure;
	}
	
	public int getEquipmentTypeId() {
		return this.equipmentTypeId;
	}
	
	public void setEquipmentTypeId(int equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
}

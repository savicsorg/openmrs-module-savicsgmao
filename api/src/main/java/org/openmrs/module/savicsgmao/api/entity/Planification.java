package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 13, 2021 3:01:21 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Planification generated by hbm2java
 */
public class Planification implements java.io.Serializable {
	
	private Integer planificationId;
	
	private String uuid;
	
	private String designation;
	
	private String planificationCode;
	
	private Date planificationDate;
	
	private String observations;
	
	public Planification() {
	}
	
	public Planification(String uuid, String designation, String planificationCode, Date planificationDate,
	    String observations) {
		this.uuid = uuid;
		this.designation = designation;
		this.planificationCode = planificationCode;
		this.planificationDate = planificationDate;
		this.observations = observations;
	}
	
	public Integer getPlanificationId() {
		return this.planificationId;
	}
	
	public void setPlanificationId(Integer planificationId) {
		this.planificationId = planificationId;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getDesignation() {
		return this.designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getPlanificationCode() {
		return this.planificationCode;
	}
	
	public void setPlanificationCode(String planificationCode) {
		this.planificationCode = planificationCode;
	}
	
	public Date getPlanificationDate() {
		return this.planificationDate;
	}
	
	public void setPlanificationDate(Date planificationDate) {
		this.planificationDate = planificationDate;
	}
	
	public String getObservations() {
		return this.observations;
	}
	
	public void setObservations(String observations) {
		this.observations = observations;
	}
	
}
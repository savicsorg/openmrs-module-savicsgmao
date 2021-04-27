package org.openmrs.module.savicsgmao.api.entity;

import java.util.Date;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

/**
 * MaintenanceRequest generated by hbm2java
 */
public class MaintenanceRequest extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer maintenanceRequestId;
	
	private Department department;
	
	private Equipment equipment;
	
	private String uuid;
	
	private String systemNumber;
	
	private String applicantName;
	
	private String registerNumber;
	
	private Date startdate;
	
	private String natureOfWork;
	
	private Date enddate;
	
	private String motif;
	
	private int requestPriority;
	
	public Integer getMaintenanceRequestId() {
		return this.maintenanceRequestId;
	}
	
	public void setMaintenanceRequestId(Integer maintenanceRequestId) {
		this.maintenanceRequestId = maintenanceRequestId;
	}
	
	public Department getDepartment() {
		return this.department;
	}
	
	public void setDepartment(Department Department) {
		this.department = Department;
	}
	
	public Equipment getEquipment() {
		return this.equipment;
	}
	
	public void setEquipment(Equipment Equipment) {
		this.equipment = Equipment;
	}
	
	public String getSystemNumber() {
		return this.systemNumber;
	}
	
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}
	
	public String getApplicantName() {
		return this.applicantName;
	}
	
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	
	public String getRegisterNumber() {
		return this.registerNumber;
	}
	
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	
	public Date getStartdate() {
		return this.startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public String getNatureOfWork() {
		return this.natureOfWork;
	}
	
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	
	public Date getEnddate() {
		return this.enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getMotif() {
		return this.motif;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public int getRequestPriority() {
		return this.requestPriority;
	}
	
	public void setRequestPriority(int requestPriority) {
		this.requestPriority = requestPriority;
	}
	
	@Override
	public User getCreator() {
		return creator;
	}
	
	@Override
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceRequestId();
	}
	
	@Override
	public void setId(Integer id) {
		setMaintenanceRequestId(id);
	}
}

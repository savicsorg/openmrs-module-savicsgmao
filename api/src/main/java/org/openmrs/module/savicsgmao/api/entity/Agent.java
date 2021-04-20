package org.openmrs.module.savicsgmao.api.entity;

import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

/**
 * Agent generated by hbm2java
 */
public class Agent extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer agentId;
	
	private Department Department;
	
	private SiteLocation SiteLocation;
	
	private String uuid;
	
	private String fullName;
	
	private String agentAddress;
	
	private String phoneNumber;
	
	private String rollNumber;
	
	private byte isExternal;
	
	private Set Maintenances = new HashSet(0);
	
	public Agent() {
	}
	
	public Agent(String uuid, String fullName, String agentAddress, String phoneNumber, byte isExternal) {
		this.uuid = uuid;
		this.fullName = fullName;
		this.agentAddress = agentAddress;
		this.phoneNumber = phoneNumber;
		this.isExternal = isExternal;
	}
	
	public Agent(Department Department, SiteLocation SiteLocation, String uuid, String fullName, String agentAddress,
	    String phoneNumber, String rollNumber, byte isExternal, Set Maintenances) {
		this.Department = Department;
		this.SiteLocation = SiteLocation;
		this.uuid = uuid;
		this.fullName = fullName;
		this.agentAddress = agentAddress;
		this.phoneNumber = phoneNumber;
		this.rollNumber = rollNumber;
		this.isExternal = isExternal;
		this.Maintenances = Maintenances;
	}
	
	public Integer getAgentId() {
		return this.agentId;
	}
	
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public Department getDepartment() {
		return this.Department;
	}
	
	public void setDepartment(Department Department) {
		this.Department = Department;
	}
	
	public SiteLocation getSiteLocation() {
		return this.SiteLocation;
	}
	
	public void setSiteLocation(SiteLocation SiteLocation) {
		this.SiteLocation = SiteLocation;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAgentAddress() {
		return this.agentAddress;
	}
	
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRollNumber() {
		return this.rollNumber;
	}
	
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	
	public byte getIsExternal() {
		return this.isExternal;
	}
	
	public void setIsExternal(byte isExternal) {
		this.isExternal = isExternal;
	}
	
	public Set getMaintenances() {
		return this.Maintenances;
	}
	
	public void setMaintenances(Set Maintenances) {
		this.Maintenances = Maintenances;
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
	public String toString() {
		return "org.openmrs.module.savics.api.entity.Agent[ agentId=" + agentId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getAgentId();
	}
	
	@Override
	public void setId(Integer id) {
		setAgentId(id);
	}
	
}

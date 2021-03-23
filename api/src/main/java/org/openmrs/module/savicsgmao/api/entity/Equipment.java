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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.Equipment")
@Table(name = "gmao_equipment")
public class Equipment extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "equipment_id", nullable = false)
	private Integer equipmentId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_type_id", nullable = false)
	private int equipmentTypeId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "serial_number", nullable = false, length = 250)
	private String serialNumber;
	
	@Column(name = "acquisition_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date acquisitionDate;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_status", nullable = false)
	private int equipmentStatus;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "designation", nullable = false, length = 250)
	private String designation;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "localization", nullable = false, length = 250)
	private String localization;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "department_id", nullable = false)
	private int departmentId;
	
	@Column(name = "last_modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "equipment_weight", precision = 12)
	private Float equipmentWeight;
	
	@Column(name = "volume", precision = 12)
	private Float volume;
	
	@Column(name = "tracking")
	private Integer tracking;
	
	@Column(name = "in_service")
	private Short inService;
	
	@Column(name = "operating_state")
	private Integer operatingState;
	
	@Column(name = "commisionning_year")
	private Integer commisionningYear;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "comments", nullable = false, length = 65535)
	private String comments;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "provider_id", nullable = false)
	private int providerId;
	
	public Equipment() {
	}
	
	public Equipment(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public Equipment(Integer equipmentId, int equipmentTypeId, String serialNumber, int equipmentStatus, String designation,
	    String localization, int departmentId, String comments, int providerId) {
		this.equipmentId = equipmentId;
		this.equipmentTypeId = equipmentTypeId;
		this.serialNumber = serialNumber;
		this.equipmentStatus = equipmentStatus;
		this.designation = designation;
		this.localization = localization;
		this.departmentId = departmentId;
		this.comments = comments;
		this.providerId = providerId;
	}
	
	public Integer getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public int getEquipmentTypeId() {
		return equipmentTypeId;
	}
	
	public void setEquipmentTypeId(int equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public Date getAcquisitionDate() {
		return acquisitionDate;
	}
	
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}
	
	public int getEquipmentStatus() {
		return equipmentStatus;
	}
	
	public void setEquipmentStatus(int equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getLocalization() {
		return localization;
	}
	
	public void setLocalization(String localization) {
		this.localization = localization;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public Float getEquipmentWeight() {
		return equipmentWeight;
	}
	
	public void setEquipmentWeight(Float equipmentWeight) {
		this.equipmentWeight = equipmentWeight;
	}
	
	public Float getVolume() {
		return volume;
	}
	
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	
	public Integer getTracking() {
		return tracking;
	}
	
	public void setTracking(Integer tracking) {
		this.tracking = tracking;
	}
	
	public Short getInService() {
		return inService;
	}
	
	public void setInService(Short inService) {
		this.inService = inService;
	}
	
	public Integer getOperatingState() {
		return operatingState;
	}
	
	public void setOperatingState(Integer operatingState) {
		this.operatingState = operatingState;
	}
	
	public Integer getCommisionningYear() {
		return commisionningYear;
	}
	
	public void setCommisionningYear(Integer commisionningYear) {
		this.commisionningYear = commisionningYear;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public int getProviderId() {
		return providerId;
	}
	
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (equipmentId != null ? equipmentId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Equipment)) {
			return false;
		}
		Equipment other = (Equipment) object;
		if ((this.equipmentId == null && other.equipmentId != null)
		        || (this.equipmentId != null && !this.equipmentId.equals(other.equipmentId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoEquipment[ equipmentId=" + equipmentId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getEquipmentId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setEquipmentId(intgr);
	}
	
}

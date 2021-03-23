/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.MaintenanceType")
@Table(name = "gmao_maintenance_type")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "GmaoMaintenanceType.findAll", query = "SELECT g FROM GmaoMaintenanceType g") })
public class MaintenanceType extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "maintenance_type_id", nullable = false)
	private Integer maintenanceTypeId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "type_code", nullable = false, length = 250)
	private String typeCode;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "type_name", nullable = false, length = 250)
	private String typeName;
	
	public MaintenanceType() {
	}
	
	public MaintenanceType(Integer maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	
	public MaintenanceType(Integer maintenanceTypeId, String typeCode, String typeName) {
		this.maintenanceTypeId = maintenanceTypeId;
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	
	public Integer getMaintenanceTypeId() {
		return maintenanceTypeId;
	}
	
	public void setMaintenanceTypeId(Integer maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (maintenanceTypeId != null ? maintenanceTypeId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof MaintenanceType)) {
			return false;
		}
		MaintenanceType other = (MaintenanceType) object;
		if ((this.maintenanceTypeId == null && other.maintenanceTypeId != null)
		        || (this.maintenanceTypeId != null && !this.maintenanceTypeId.equals(other.maintenanceTypeId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoMaintenanceType[ maintenanceTypeId=" + maintenanceTypeId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceTypeId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setMaintenanceTypeId(intgr);
	}
	
}

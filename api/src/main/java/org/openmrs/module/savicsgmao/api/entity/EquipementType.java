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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.EquipementType")
@Table(name = "gmao_equipement_type")
public class EquipementType extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "equipment_type_id", nullable = false)
	private Integer equipmentTypeId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "type_name", nullable = false, length = 250)
	private String typeName;
	
	public EquipementType() {
	}
	
	public EquipementType(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
	public EquipementType(Integer equipmentTypeId, String typeName) {
		this.equipmentTypeId = equipmentTypeId;
		this.typeName = typeName;
	}
	
	public Integer getEquipmentTypeId() {
		return equipmentTypeId;
	}
	
	public void setEquipmentTypeId(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
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
		hash += (equipmentTypeId != null ? equipmentTypeId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof EquipementType)) {
			return false;
		}
		EquipementType other = (EquipementType) object;
		if ((this.equipmentTypeId == null && other.equipmentTypeId != null)
		        || (this.equipmentTypeId != null && !this.equipmentTypeId.equals(other.equipmentTypeId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoEquipementType[ equipmentTypeId=" + equipmentTypeId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getEquipmentTypeId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setEquipmentTypeId(intgr);
	}
	
}

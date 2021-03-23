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
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.EquipmentOperationItem")
@Table(name = "gmao_equipment_operation_item")
public class EquipmentOperationItem extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "equipment_operation_item_id", nullable = false)
	private Integer equipmentOperationItemId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_id", nullable = false)
	private int equipmentId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_operation_id", nullable = false)
	private int equipmentOperationId;
	
	public EquipmentOperationItem() {
	}
	
	public EquipmentOperationItem(Integer equipmentOperationItemId) {
		this.equipmentOperationItemId = equipmentOperationItemId;
	}
	
	public EquipmentOperationItem(Integer equipmentOperationItemId, int equipmentId, int equipmentOperationId) {
		this.equipmentOperationItemId = equipmentOperationItemId;
		this.equipmentId = equipmentId;
		this.equipmentOperationId = equipmentOperationId;
	}
	
	public Integer getEquipmentOperationItemId() {
		return equipmentOperationItemId;
	}
	
	public void setEquipmentOperationItemId(Integer equipmentOperationItemId) {
		this.equipmentOperationItemId = equipmentOperationItemId;
	}
	
	public int getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public int getEquipmentOperationId() {
		return equipmentOperationId;
	}
	
	public void setEquipmentOperationId(int equipmentOperationId) {
		this.equipmentOperationId = equipmentOperationId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (equipmentOperationItemId != null ? equipmentOperationItemId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof EquipmentOperationItem)) {
			return false;
		}
		EquipmentOperationItem other = (EquipmentOperationItem) object;
		if ((this.equipmentOperationItemId == null && other.equipmentOperationItemId != null)
		        || (this.equipmentOperationItemId != null && !this.equipmentOperationItemId
		                .equals(other.equipmentOperationItemId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoEquipmentOperationItem[ equipmentOperationItemId="
		        + equipmentOperationItemId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getEquipmentOperationItemId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setEquipmentOperationItemId(intgr);
	}
	
}

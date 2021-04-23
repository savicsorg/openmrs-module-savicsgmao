package org.openmrs.module.savicsgmao.api.entity;

// Generated Apr 19, 2021 4:44:31 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

/**
 * EquipmentType generated by hbm2java
 */
public class EquipmentType extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer equipmentTypeId;
	
	private String uuid;
	
	private String typeName;
	
	private Set Tasks = new HashSet(0);
	
	private Set Equipments = new HashSet(0);
	
	public EquipmentType() {
	}
	
	public EquipmentType(String uuid, String typeName) {
		this.uuid = uuid;
		this.typeName = typeName;
	}
	
	public EquipmentType(String uuid, String typeName, Set Tasks, Set Equipments) {
		this.uuid = uuid;
		this.typeName = typeName;
		this.Tasks = Tasks;
		this.Equipments = Equipments;
	}
	
	public Integer getEquipmentTypeId() {
		return this.equipmentTypeId;
	}
	
	public void setEquipmentTypeId(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Set getTasks() {
		return this.Tasks;
	}
	
	public void setTasks(Set Tasks) {
		this.Tasks = Tasks;
	}
	
	public Set getEquipments() {
		return this.Equipments;
	}
	
	public void setEquipments(Set Equipments) {
		this.Equipments = Equipments;
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
		return getEquipmentTypeId();
	}
	
	@Override
	public void setId(Integer id) {
		setEquipmentTypeId(id);
	}
	
}
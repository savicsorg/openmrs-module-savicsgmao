package org.openmrs.module.savicsgmao.api.entity;

import java.util.HashSet;
import java.util.Set;
import org.openmrs.BaseOpenmrsData;

/**
 * EquipmentTypee generated by hbm2java
 */
public class EquipmentType extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer id;
	
	private String uuid;
	
	private String name;
	
	private Set equipments = new HashSet(0);
	
	public EquipmentType() {
	}
	
	public EquipmentType(int id, String uuid, String name) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
	}
	
	public EquipmentType(int id, String uuid, String name, Set equipments) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.equipments = equipments;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getUuid() {
		return this.uuid;
	}
	
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set getEquipments() {
		return this.equipments;
	}
	
	public void setEquipments(Set Equipments) {
		this.equipments = Equipments;
	}
	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.api.dto;

/**
 * @author anatoleabe
 */
public class EquipmentFailureRate implements java.io.Serializable {
	
	private String name;
	
	private String serial;
	
	private Integer inactiveDays;
	
	private Integer equipmentId;
	
	private Integer countMaintenance;
	
	private Double rate;
	
	public EquipmentFailureRate(String name, Integer id, Integer countMaintenance) {
		this.name = name;
		this.equipmentId = id;
		this.countMaintenance = countMaintenance;
	}
	
	public EquipmentFailureRate() {
	}
	
	public Integer getCountMaintenance() {
		return countMaintenance;
	}
	
	public void setCountMaintenance(Integer countMaintenance) {
		this.countMaintenance = countMaintenance;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public String getSerial() {
		return serial;
	}
	
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public Integer getInactiveDays() {
		return inactiveDays;
	}
	
	public void setInactiveDays(Integer inactiveDays) {
		this.inactiveDays = inactiveDays;
	}
	
	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
}

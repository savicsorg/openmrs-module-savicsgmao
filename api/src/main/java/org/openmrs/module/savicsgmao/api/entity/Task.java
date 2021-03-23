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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.Task")
@Table(name = "gmao_task")
public class Task extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "task_id", nullable = false)
	private Integer taskId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "task_name", nullable = false, length = 250)
	private String taskName;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "task_procedure", nullable = false, length = 65535)
	private String taskProcedure;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipment_type_id", nullable = false)
	private int equipmentTypeId;
	
	public Task() {
	}
	
	public Task(Integer taskId) {
		this.taskId = taskId;
	}
	
	public Task(Integer taskId, String taskName, String taskProcedure, int equipmentTypeId) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskProcedure = taskProcedure;
		this.equipmentTypeId = equipmentTypeId;
	}
	
	public Integer getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskProcedure() {
		return taskProcedure;
	}
	
	public void setTaskProcedure(String taskProcedure) {
		this.taskProcedure = taskProcedure;
	}
	
	public int getEquipmentTypeId() {
		return equipmentTypeId;
	}
	
	public void setEquipmentTypeId(int equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (taskId != null ? taskId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Task)) {
			return false;
		}
		Task other = (Task) object;
		if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoTask[ taskId=" + taskId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getTaskId();
	}
	
	@Override
	public void setId(Integer taskId) {
		setTaskId(taskId);
	}
	
}

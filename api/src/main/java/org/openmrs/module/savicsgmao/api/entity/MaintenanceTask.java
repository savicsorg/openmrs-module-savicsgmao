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
@Entity(name = "savicsgmao.MaintenanceTask")
@Table(name = "gmao_maintenance_task")
public class MaintenanceTask extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "maintenance_task_id", nullable = false)
	private Integer maintenanceTaskId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "designation", nullable = false, length = 250)
	private String designation;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "details", nullable = false, length = 65535)
	private String details;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "executed", nullable = false)
	private short executed;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "task_id", nullable = false)
	private int taskId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "maintenance_id", nullable = false)
	private int maintenanceId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "equipement_id", nullable = false)
	private int equipementId;
	
	@Column(name = "planification_id")
	private Integer planificationId;
	
	public MaintenanceTask() {
	}
	
	public MaintenanceTask(Integer maintenanceTaskId) {
		this.maintenanceTaskId = maintenanceTaskId;
	}
	
	public MaintenanceTask(Integer maintenanceTaskId, String designation, String details, short executed, int taskId,
	    int maintenanceId, int equipementId) {
		this.maintenanceTaskId = maintenanceTaskId;
		this.designation = designation;
		this.details = details;
		this.executed = executed;
		this.taskId = taskId;
		this.maintenanceId = maintenanceId;
		this.equipementId = equipementId;
	}
	
	public Integer getMaintenanceTaskId() {
		return maintenanceTaskId;
	}
	
	public void setMaintenanceTaskId(Integer maintenanceTaskId) {
		this.maintenanceTaskId = maintenanceTaskId;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public short getExecuted() {
		return executed;
	}
	
	public void setExecuted(short executed) {
		this.executed = executed;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public int getMaintenanceId() {
		return maintenanceId;
	}
	
	public void setMaintenanceId(int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	
	public int getEquipementId() {
		return equipementId;
	}
	
	public void setEquipementId(int equipementId) {
		this.equipementId = equipementId;
	}
	
	public Integer getPlanificationId() {
		return planificationId;
	}
	
	public void setPlanificationId(Integer planificationId) {
		this.planificationId = planificationId;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (maintenanceTaskId != null ? maintenanceTaskId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof MaintenanceTask)) {
			return false;
		}
		MaintenanceTask other = (MaintenanceTask) object;
		if ((this.maintenanceTaskId == null && other.maintenanceTaskId != null)
		        || (this.maintenanceTaskId != null && !this.maintenanceTaskId.equals(other.maintenanceTaskId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoMaintenanceTask[ maintenanceTaskId=" + maintenanceTaskId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getMaintenanceTaskId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setMaintenanceTaskId(maintenanceTaskId);
	}
	
}

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.EquipmentOperation")
@Table(name = "gmao_equipment_operation")
public class EquipmentOperation extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "equipment_operation_id", nullable = false)
	private Integer equipmentOperationId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "operation_code", nullable = false, length = 250)
	private String operationCode;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "operation_type", nullable = false)
	private int operationType;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "operation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;
	
	@Column(name = "source_site_id")
	private Integer sourceSiteId;
	
	@Column(name = "destination_site_id")
	private Integer destinationSiteId;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "reason", nullable = false, length = 65535)
	private String reason;
	
	@Column(name = "local_approval")
	private Short localApproval;
	
	@Column(name = "local_approval_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date localApprovalDate;
	
	@Column(name = "central_approval")
	private Short centralApproval;
	
	@Column(name = "central_approval_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date centralApprovalDate;
	
	public EquipmentOperation() {
	}
	
	public EquipmentOperation(Integer equipmentOperationId) {
		this.equipmentOperationId = equipmentOperationId;
	}
	
	public EquipmentOperation(Integer equipmentOperationId, String operationCode, int operationType, Date operationDate,
	    String reason) {
		this.equipmentOperationId = equipmentOperationId;
		this.operationCode = operationCode;
		this.operationType = operationType;
		this.operationDate = operationDate;
		this.reason = reason;
	}
	
	public Integer getEquipmentOperationId() {
		return equipmentOperationId;
	}
	
	public void setEquipmentOperationId(Integer equipmentOperationId) {
		this.equipmentOperationId = equipmentOperationId;
	}
	
	public String getOperationCode() {
		return operationCode;
	}
	
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	
	public int getOperationType() {
		return operationType;
	}
	
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	
	public Date getOperationDate() {
		return operationDate;
	}
	
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	public Integer getSourceSiteId() {
		return sourceSiteId;
	}
	
	public void setSourceSiteId(Integer sourceSiteId) {
		this.sourceSiteId = sourceSiteId;
	}
	
	public Integer getDestinationSiteId() {
		return destinationSiteId;
	}
	
	public void setDestinationSiteId(Integer destinationSiteId) {
		this.destinationSiteId = destinationSiteId;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Short getLocalApproval() {
		return localApproval;
	}
	
	public void setLocalApproval(Short localApproval) {
		this.localApproval = localApproval;
	}
	
	public Date getLocalApprovalDate() {
		return localApprovalDate;
	}
	
	public void setLocalApprovalDate(Date localApprovalDate) {
		this.localApprovalDate = localApprovalDate;
	}
	
	public Short getCentralApproval() {
		return centralApproval;
	}
	
	public void setCentralApproval(Short centralApproval) {
		this.centralApproval = centralApproval;
	}
	
	public Date getCentralApprovalDate() {
		return centralApprovalDate;
	}
	
	public void setCentralApprovalDate(Date centralApprovalDate) {
		this.centralApprovalDate = centralApprovalDate;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (equipmentOperationId != null ? equipmentOperationId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof EquipmentOperation)) {
			return false;
		}
		EquipmentOperation other = (EquipmentOperation) object;
		if ((this.equipmentOperationId == null && other.equipmentOperationId != null)
		        || (this.equipmentOperationId != null && !this.equipmentOperationId.equals(other.equipmentOperationId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoEquipmentOperation[ equipmentOperationId="
		        + equipmentOperationId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getEquipmentOperationId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setEquipmentOperationId(intgr);
	}
	
}

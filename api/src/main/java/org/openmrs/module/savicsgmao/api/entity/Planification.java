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
@Entity(name = "savicsgmao.Planification")
@Table(name = "gmao_planification")
public class Planification extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "planification_id", nullable = false)
	private Integer planificationId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "designation", nullable = false, length = 250)
	private String designation;
	
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "planification_code", nullable = false, length = 65535)
	private String planificationCode;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "planification_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date planificationDate;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "observations", nullable = false)
	private int observations;
	
	public Planification() {
	}
	
	public Planification(Integer planificationId) {
		this.planificationId = planificationId;
	}
	
	public Planification(Integer planificationId, String designation, String planificationCode, Date planificationDate,
	    int observations) {
		this.planificationId = planificationId;
		this.designation = designation;
		this.planificationCode = planificationCode;
		this.planificationDate = planificationDate;
		this.observations = observations;
	}
	
	public Integer getPlanificationId() {
		return planificationId;
	}
	
	public void setPlanificationId(Integer planificationId) {
		this.planificationId = planificationId;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getPlanificationCode() {
		return planificationCode;
	}
	
	public void setPlanificationCode(String planificationCode) {
		this.planificationCode = planificationCode;
	}
	
	public Date getPlanificationDate() {
		return planificationDate;
	}
	
	public void setPlanificationDate(Date planificationDate) {
		this.planificationDate = planificationDate;
	}
	
	public int getObservations() {
		return observations;
	}
	
	public void setObservations(int observations) {
		this.observations = observations;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (planificationId != null ? planificationId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Planification)) {
			return false;
		}
		Planification other = (Planification) object;
		if ((this.planificationId == null && other.planificationId != null)
		        || (this.planificationId != null && !this.planificationId.equals(other.planificationId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoPlanification[ planificationId=" + planificationId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getPlanificationId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setPlanificationId(intgr);
	}
	
}

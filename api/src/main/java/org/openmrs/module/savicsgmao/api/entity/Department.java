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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.openmrs.BaseOpenmrsData;

/**
 * @author anatoleabe
 */
@Entity(name = "savicsgmao.Department")
@Table(name = "gmao_department", uniqueConstraints = { @UniqueConstraint(columnNames = { "department_code" }) })
public class Department extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "department_id", nullable = false)
	private Integer departmentId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "department_name", nullable = false, length = 250)
	private String departmentName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "department_code", nullable = false, length = 250)
	private String departmentCode;
	
	public Department() {
	}
	
	public Department(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public Department(Integer departmentId, String departmentName, String departmentCode) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.departmentCode = departmentCode;
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getDepartmentCode() {
		return departmentCode;
	}
	
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (departmentId != null ? departmentId.hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Department)) {
			return false;
		}
		Department other = (Department) object;
		if ((this.departmentId == null && other.departmentId != null)
		        || (this.departmentId != null && !this.departmentId.equals(other.departmentId))) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "org.openmrs.module.savicsgmao.api.entity.GmaoDepartment[ departmentId=" + departmentId + " ]";
	}
	
	@Override
	public Integer getId() {
		return getDepartmentId();
	}
	
	@Override
	public void setId(Integer intgr) {
		setDepartmentId(intgr);
	}
	
}

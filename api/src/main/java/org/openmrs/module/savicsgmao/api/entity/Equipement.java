package org.openmrs.module.savicsgmao.api.entity;

import java.util.Date;
import java.util.UUID;

public class Equipement implements java.io.Serializable {
	
	public Integer EquipmentId;
        private String uuid;
	
	public int EquipmentTypeId;
	
	public String SerialNumber;
	
	public Date AcquisitionDate;
	
	public Integer Status;
	
	public String Designation;
	
	public String Localization;
	
	public Integer DepartmentId;
	
	public Date LastModifiedDate;
	
	public Float Weight;
	
	public Float Volume;
	
	public Integer Tracking;
	
	public Integer InService;
	
	public Integer OperatingState;
	
	public Integer CommissioningYear;
	
	public String Comments;
	
	public Integer ProviderId;

    public Equipement(int EquipmentTypeId, String SerialNumber, Date AcquisitionDate, Integer Status, String Designation, String Localization, Integer DepartmentId, Date LastModifiedDate, Float Weight, Float Volume, Integer Tracking, Integer InService, Integer OperatingState, Integer CommissioningYear, String Comments, Integer ProviderId) {
        this.EquipmentTypeId = EquipmentTypeId;
        this.SerialNumber = SerialNumber;
        this.AcquisitionDate = AcquisitionDate;
        this.Status = Status;
        this.Designation = Designation;
        this.Localization = Localization;
        this.DepartmentId = DepartmentId;
        this.LastModifiedDate = LastModifiedDate;
        this.Weight = Weight;
        this.Volume = Volume;
        this.Tracking = Tracking;
        this.InService = InService;
        this.OperatingState = OperatingState;
        this.CommissioningYear = CommissioningYear;
        this.Comments = Comments;
        this.ProviderId = ProviderId;
    }

    public Equipement() {
        uuid = UUID.randomUUID().toString();
    }
        
        

    public Integer getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(Integer EquipmentId) {
        this.EquipmentId = EquipmentId;
    }

    public int getEquipmentTypeId() {
        return EquipmentTypeId;
    }

    public void setEquipmentTypeId(int EquipmentTypeId) {
        this.EquipmentTypeId = EquipmentTypeId;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    public Date getAcquisitionDate() {
        return AcquisitionDate;
    }

    public void setAcquisitionDate(Date AcquisitionDate) {
        this.AcquisitionDate = AcquisitionDate;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    public String getLocalization() {
        return Localization;
    }

    public void setLocalization(String Localization) {
        this.Localization = Localization;
    }

    public Integer getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(Integer DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public Date getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(Date LastModifiedDate) {
        this.LastModifiedDate = LastModifiedDate;
    }

    public Float getWeight() {
        return Weight;
    }

    public void setWeight(Float Weight) {
        this.Weight = Weight;
    }

    public Float getVolume() {
        return Volume;
    }

    public void setVolume(Float Volume) {
        this.Volume = Volume;
    }

    public Integer getTracking() {
        return Tracking;
    }

    public void setTracking(Integer Tracking) {
        this.Tracking = Tracking;
    }

    public Integer getInService() {
        return InService;
    }

    public void setInService(Integer InService) {
        this.InService = InService;
    }

    public Integer getOperatingState() {
        return OperatingState;
    }

    public void setOperatingState(Integer OperatingState) {
        this.OperatingState = OperatingState;
    }

    public Integer getCommissioningYear() {
        return CommissioningYear;
    }

    public void setCommissioningYear(Integer CommissioningYear) {
        this.CommissioningYear = CommissioningYear;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public Integer getProviderId() {
        return ProviderId;
    }

    public void setProviderId(Integer ProviderId) {
        this.ProviderId = ProviderId;
    }      
        
}

package org.openmrs.module.savicsgmao.api.entity;

// Generated Dec 9, 2021 11:58:16 AM by Hibernate Tools 4.3.1
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openmrs.BaseOpenmrsData;

/**
 * MaintenanceRequest generated by hbm2java
 */
public class MaintenanceRequest extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer id;
	
	private Equipment equipment;
	
	private String uuid;
	
	private String requestedby;
	
	private String registerNumber;
	
	private String natureOfWork;
	
	private Integer priority;
	
	private String motif;
	
	private Date lastmodified;
	
	private Date creation;
	
	private Set maintenances = new HashSet(0);
	
	public MaintenanceRequest() {
	}
	
	public MaintenanceRequest(int id, Equipment Equipment, String uuid, String requestedby, String registerNumber,
	    String natureOfWork, int priority, String motif) {
		this.id = id;
		this.equipment = Equipment;
		this.uuid = uuid;
		this.requestedby = requestedby;
		this.registerNumber = registerNumber;
		this.natureOfWork = natureOfWork;
		this.priority = priority;
		this.motif = motif;
	}
	
	public MaintenanceRequest(int id, Equipment Equipment, String uuid, String requestedby, String registerNumber,
	    String natureOfWork, int priority, String motif, Date lastmodified, Date creation, Set Maintenances) {
		this.id = id;
		this.equipment = Equipment;
		this.uuid = uuid;
		this.requestedby = requestedby;
		this.registerNumber = registerNumber;
		this.natureOfWork = natureOfWork;
		this.priority = priority;
		this.motif = motif;
		this.lastmodified = lastmodified;
		this.creation = creation;
		this.maintenances = Maintenances;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Equipment getEquipment() {
		return this.equipment;
	}
	
	public void setEquipment(Equipment Equipment) {
		this.equipment = Equipment;
	}
	
	public String getRequestedby() {
		return this.requestedby;
	}
	
	public void setRequestedby(String requestedby) {
		this.requestedby = requestedby;
	}
	
	public String getRegisterNumber() {
		return this.registerNumber;
	}
	
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	
	public String getNatureOfWork() {
		return this.natureOfWork;
	}
	
	public String getNatureOfWorkDisplay(String id) throws FileNotFoundException, IOException, ParseException {
		String value = "";
		if ("1".equals(id)) {
			value = "Maintenance";
		} else if ("2".equals(id)) {
			value = "Réparration";
		} else if ("3".equals(id)) {
			value = "Travail";
		} else if ("4".equals(id)) {
			value = "Autre";
		}
		return value;
	}
	
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public String getPriorityDisplay(int id) throws FileNotFoundException, IOException, ParseException {
		String value = "";
		if (id == 1) {
			value = "Normale";
		} else if (id == 2) {
			value = "Urgent";
		}
		return value;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String getMotif() {
		return this.motif;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public Date getLastmodified() {
		return this.lastmodified;
	}
	
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	public Date getCreation() {
		return this.creation;
	}
	
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
	public Set getMaintenances() {
		return this.maintenances;
	}
	
	public void setMaintenances(Set Maintenances) {
		this.maintenances = Maintenances;
	}
	
}

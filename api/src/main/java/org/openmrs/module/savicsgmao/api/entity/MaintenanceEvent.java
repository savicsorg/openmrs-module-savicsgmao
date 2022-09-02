package org.openmrs.module.savicsgmao.api.entity;

// Generated Dec 9, 2021 11:58:16 AM by Hibernate Tools 4.3.1
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.DateTime;
import org.openmrs.BaseOpenmrsData;

/**
 * MaintenanceRequest generated by hbm2java
 */
public class MaintenanceEvent extends BaseOpenmrsData implements java.io.Serializable {
	
	private Integer id;
	
	private Equipment equipment;
	
	private String uuid;
	
	private String name;
	
	private String description;
	
	private Date startdate;
	
	private Date enddate;
	
	private DateTime lastExecutionTime;
	
	private short status;
	
	private String frequency;
	
	private Integer priority;
	
	private Set maintenances = new HashSet(0);
	
	private Date lastmodified;
	
	private Date creation;
	
	private Integer numberOfPassDays;
	
	private Integer remainingDaysBeforeExecution;
	
	/**
	 * Interval used to determine the next execution time
	 */
	private long repeatInterval;
	
	/**
	 * Constants
	 */
	public static final int MILLISECONDS_PER_SECOND = 1000;
	
	public static final int SECONDS_PER_MINUTE = 60;
	
	public static final int MINUTES_PER_HOUR = 60;
	
	public static final int HOURS_PER_DAY = 24;
	
	public static final int DAYS_PER_WEEK = 7;
	
	public static final int DAYS_PER_MONTH = 30;
	
	public static final int DAYS_PER_SEMESTER = 90;
	
	public static final int DAYS_PER_YEAR = 365;
	
	public static final int DAILY = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	
	public static final int WEEKLY = DAILY * DAYS_PER_WEEK;
	
	public static final int MONTHLY = DAILY * DAYS_PER_MONTH;
	
	public static final int SEMESTER = DAILY * DAYS_PER_SEMESTER;
	
	public static final int ANNUALY = DAILY * DAYS_PER_YEAR;
	
	public MaintenanceEvent() {
	}
	
	public MaintenanceEvent(int id, Equipment Equipment, String uuid) {
		this.id = id;
		this.equipment = Equipment;
		this.uuid = uuid;
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
	
	public String getNatureOfWorkDisplay(String id) {
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
	
	public Integer getPriority() {
		return this.priority;
	}
	
	public String getPriorityDisplay(int id) {
		String value = "";
		if (id == 1) {
			value = "Normale";
		} else if (id == 2) {
			value = "Urgent";
		}
		return value;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
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
	
	public short getStatus() {
		return status;
	}
	
	public void setStatus(short status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Integer getNumberOfPassDays() {
		return numberOfPassDays;
	}
	
	public void setNumberOfPassDays(Integer numberOfPassDays) {
		this.numberOfPassDays = numberOfPassDays;
	}
	
	public DateTime getLastExecutionTime() {
		return lastExecutionTime;
	}
	
	public void setLastExecutionTime(DateTime lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}
	
	public long getRepeatInterval() {
		return repeatInterval;
	}
	
	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	
	public Integer getRemainingDaysBeforeExecution() {
		return remainingDaysBeforeExecution;
	}
	
	public void setRemainingDaysBeforeExecution(Integer remainingDaysBeforeExecution) {
		this.remainingDaysBeforeExecution = remainingDaysBeforeExecution;
	}
	
}

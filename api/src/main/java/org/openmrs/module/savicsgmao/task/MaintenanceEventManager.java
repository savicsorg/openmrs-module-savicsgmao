/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.savicsgmao.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceEvent;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceType;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * @author anatoleabe
 */
public class MaintenanceEventManager extends AbstractTask {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public void execute() {
		if (!isExecuting) {
			if (log.isDebugEnabled()) {
				log.debug("Savics GMAO - maintenance event monitor now activated");
			}
			
			startExecuting();
			try {
				DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				
				String activeEvents = "select * from (select *,  if ((`frequency` = '1' and remainingDays < 5), remainingDays - 1, remainingDays - 5) as remainingDaysBeforeExecution from \n"
				        + "(select *,  (repeat_interval_days - passedDays) as remainingDays from (\n"
				        + "select *, DATEDIFF(now(), IFNULL(`last_execution_time`, `startdate`)) AS passedDays, IF (`frequency` = '2', repeat_interval * 7, IF (`frequency` = '3', repeat_interval * 30, IF (`frequency` = '4', repeat_interval * 365, repeat_interval))) as repeat_interval_days\n"
				        + "from `gmao_maintenance_event`\n"
				        + "where status = 0 and startdate <= now() and (enddate >=now() or enddate is null) ) as events )\n"
				        + "as events2) as event3\n"
				        + "where ((frequency <> 0 && `repeat_interval` <> 0) or (frequency = 0 && `last_execution_time` is  null))";
				
				List<List<Object>> results = Context.getAdministrationService().executeSQL(activeEvents, true);
				for (List<Object> temp : results) {
					MaintenanceEvent me = new MaintenanceEvent();
					int col = 0;
					if (temp.get(0) != null && temp.get(1) != null) {
						me.setId(new Integer(temp.get(0).toString()));//0
						Integer equipmentId = new Integer(temp.get(1).toString());//1
						Equipment equipment = null;
						if (equipmentId != null) {
							equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class,
							    "id", equipmentId);
						}
						me.setEquipment(equipment);
						me.setUuid(temp.get(2).toString());
						me.setName(temp.get(3).toString());//3
						me.setDescription(temp.get(4).toString());//4
						
						Date myDate;
						if (temp.get(5) != null) {
							myDate = simpleDateFormat.parse(temp.get(5).toString());
							me.setStartdate(myDate);
						} else {
							me.setStartdate(null);
						}
						
						if (temp.get(6) != null) {//6
							myDate = simpleDateFormat.parse(temp.get(6).toString());
							me.setEnddate(myDate);
						} else {
							me.setEnddate(null);
						}
						me.setFrequency(temp.get(7).toString());//7
						me.setPriority((new Integer(temp.get(8).toString())));//8
						me.setStatus((new Integer(temp.get(9).toString())).shortValue());//9
						
						if (temp.get(10) != null) {//10
							myDate = simpleDateFormat.parse(temp.get(10).toString());
							me.setLastmodified(myDate);
						} else {
							me.setLastmodified(null);
						}
						
						if (temp.get(11) != null) {//11
							myDate = simpleDateFormat.parse(temp.get(11).toString());
							me.setCreation(myDate);
						} else {
							me.setCreation(null);
						}
						
						if (temp.get(12) != null) {//12
							myDate = simpleDateFormat.parse(temp.get(12).toString());
							me.setLastExecutionTime(myDate);
						} else {
							me.setLastExecutionTime(null);
						}
						
						me.setRepeatInterval(new Integer(temp.get(13).toString()));//13
						//me.setRepeatInterval(me.getIntervalInDays());//13
						Logger.getLogger(MaintenanceEventManager.class.getName()).log(Level.INFO,
						    "temp.get(14).toString() = {0}", temp.get(15).toString());
						me.setNumberOfPassDays((new Integer(temp.get(15).toString())));//14
						
						me.setRemainingDaysBeforeExecution(new Integer(temp.get(17).toString()));//16
					}
					Logger.getLogger(MaintenanceEventManager.class.getName()).log(Level.INFO, "getName = {0}", me.getName());
					Logger.getLogger(MaintenanceEventManager.class.getName()).log(Level.INFO, "getLastExecutionTime = {0}",
					    me.getLastExecutionTime());
					Logger.getLogger(MaintenanceEventManager.class.getName()).log(Level.INFO, "setNumberOfPassDays = {0}",
					    me.getNumberOfPassDays());
					
					if (me.getId() != null && me.getEquipment() != null && me.getRemainingDaysBeforeExecution() <= 0) {
						Maintenance maintenance = new Maintenance();
						maintenance.setEquipment(me.getEquipment());
						MaintenanceType maintenanceType = (MaintenanceType) Context.getService(GmaoService.class)
						        .getEntityByUuid(MaintenanceType.class, "b0000001-d22b-493e-98e8-4d1de0621a8v");
						maintenance.setLastmodified(new Date());
						maintenance.setCreation(new Date());
						
						maintenance.setName(me.getName());
						maintenance.setDescription(me.getDescription());
						maintenance.setReason("Maintenance planifiée");
						maintenance.setStatus(new Integer(0).shortValue());
						maintenance.setMaintenanceType(maintenanceType);
						maintenance.setMaintenanceEvent(me);
						
						Date duDate = Date.from(new Date().toInstant().plus(me.getIntervalInDays(), ChronoUnit.DAYS));
						
						if (me.getLastExecutionTime() != null) {
							if (!me.getFrequency().equals("1") && !me.getFrequency().equals("0")) {
								duDate = Date.from(me.getLastExecutionTime().toInstant()
								        .plus(me.getIntervalInDays() + 5, ChronoUnit.DAYS));
							} else {
								duDate = Date.from(me.getLastExecutionTime().toInstant()
								        .plus(me.getIntervalInDays() + 1, ChronoUnit.DAYS));
							}
							
						}
						
						maintenance.setDueDate(duDate);
						Context.getService(GmaoService.class).upsert(maintenance);
						me.setLastExecutionTime(new Date());
						Context.getService(GmaoService.class).upsert(me);
					}
				}
				
			}
			catch (APIException e) {
				log.error("Error while monitoring maintenance events", e);
			}
			catch (ParseException ex) {
				Logger.getLogger(MaintenanceEventManager.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally {
				stopExecuting();
			}
		}
	}
	
}

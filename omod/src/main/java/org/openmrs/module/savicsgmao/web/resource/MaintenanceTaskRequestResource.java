package org.openmrs.module.savicsgmao.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.IllegalPropertyException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.List;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceTask;
import org.openmrs.module.savicsgmao.api.entity.Planification;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenanceTask", supportedClass = MaintenanceTask.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceTaskRequestResource extends DelegatingCrudResource<MaintenanceTask> {
	
	@Override
	public MaintenanceTask newDelegate() {
		return new MaintenanceTask();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addProperty("details");
			description.addProperty("executed");
			description.addProperty("taskId");
			description.addProperty("maintenanceId");
			description.addProperty("equipementId");
			description.addProperty("planificationId");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addProperty("details");
			description.addProperty("executed");
			description.addProperty("taskId");
			description.addProperty("maintenanceId");
			description.addProperty("equipementId");
			description.addProperty("planificationId");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<MaintenanceTask> maintenanceTaskList = Context.getService(GmaoService.class).getAll(MaintenanceTask.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceTask>(context, maintenanceTaskList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("designation");
		List<MaintenanceTask> maintenanceTaskList = Context.getService(GmaoService.class).doSearch(MaintenanceTask.class,
		    "designation", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceTask>(context, maintenanceTaskList, false);
	}
	
	@Override
	public MaintenanceTask getByUniqueId(String uuid) {
		
		return (MaintenanceTask) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceTask.class, uuid);
	}
	
	@Override
	public MaintenanceTask save(MaintenanceTask maintenanceTask) {
		return (MaintenanceTask) Context.getService(GmaoService.class).upsert(maintenanceTask);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("designation") == null || propertiesToCreate.get("taskId") == null) {
			throw new ConversionException("Required properties: designation, taskId");
		}
		
		MaintenanceTask maintenanceTask = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(maintenanceTask);
		return ConversionUtil.convertToRepresentation(maintenanceTask, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		MaintenanceTask maintenanceTask = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(maintenanceTask);
		return ConversionUtil.convertToRepresentation(maintenanceTask, context.getRepresentation());
	}
	
	@Override
	protected void delete(MaintenanceTask maintenanceTask, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceTask);
	}
	
	@Override
	public void purge(MaintenanceTask maintenanceTask, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceTask);
	}
	
	private MaintenanceTask constructAgent(String uuid, SimpleObject properties) {
		MaintenanceTask maintenanceTask;
		
		Equipment equipment = null;
		if (properties.get("Equipment") != null) {
			Integer equipementId = properties.get("Equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "equipementId",
			    equipementId);
		}
		
		Maintenance maintenance = null;
		if (properties.get("Maintenance") != null) {
			Integer maintenanceId = properties.get("Maintenance");
			maintenance = (Maintenance) Context.getService(GmaoService.class).getEntityByid(Maintenance.class,
			    "maintenanceId", maintenanceId);
		}
		
		Planification planification = null;
		if (properties.get("Planification") != null) {
			Integer planificationId = properties.get("Planification");
			planification = (Planification) Context.getService(GmaoService.class).getEntityByid(Planification.class,
			    "planificationId", planificationId);
		}
		
		if (uuid != null) {
			maintenanceTask = (MaintenanceTask) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceTask.class,
			    uuid);
			if (maintenanceTask == null) {
				throw new IllegalPropertyException("Maintenance Task not exist");
			}
			
			if (properties.get("designation") != null) {
				maintenanceTask.setDesignation((String) properties.get("designation"));
			}
			
			if (properties.get("details") != null) {
				maintenanceTask.setDetails((String) properties.get("details"));
			}
			
			if (properties.get("executed") != null) {
				maintenanceTask.setExecuted((Byte) properties.get("executed"));
			}
			
			if (properties.get("taskId") != null) {
				maintenanceTask.setTaskId((Integer) properties.get("taskId"));
			}
			
			if (properties.get("maintenanceId") != null) {
				maintenanceTask.setMaintenance(maintenance);
			}
			
			if (properties.get("equipementId") != null) {
				maintenanceTask.setEquipment(equipment);
			}
			
			if (properties.get("planificationId") != null) {
				maintenanceTask.setPlanification(planification);
			}
			
		} else {
			maintenanceTask = new MaintenanceTask();
			if (properties.get("designation") == null || properties.get("taskId") == null) {
				throw new IllegalPropertyException("Required parameters: designation, taskId");
			}
			maintenanceTask.setDesignation((String) properties.get("designation"));
			maintenanceTask.setDetails((String) properties.get("details"));
			maintenanceTask.setExecuted((Byte) properties.get("executed"));
			maintenanceTask.setTaskId((Integer) properties.get("taskId"));
			maintenanceTask.setMaintenance(maintenance);
			maintenanceTask.setEquipment(equipment);
			maintenanceTask.setPlanification(planification);
		}
		
		return maintenanceTask;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenanceTask";
	}
	
}

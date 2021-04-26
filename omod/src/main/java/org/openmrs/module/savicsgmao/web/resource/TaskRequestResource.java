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
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Task;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/task", supportedClass = Task.class, supportedOpenmrsVersions = { "2.*.*" })
public class TaskRequestResource extends DelegatingCrudResource<Task> {
	
	@Override
	public Task newDelegate() {
		return new Task();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("taskName");
			description.addProperty("taskProcedure");
			description.addProperty("equipmentTypeId");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("taskName");
			description.addProperty("taskProcedure");
			description.addProperty("equipmentTypeId");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Task> taskList = Context.getService(GmaoService.class).getAll(Task.class, context.getLimit(),
		    context.getStartIndex());
		return new AlreadyPaged<Task>(context, taskList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("taskName");
		List<Task> taskList = Context.getService(GmaoService.class).doSearch(Task.class, "taskName", value,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Task>(context, taskList, false);
	}
	
	@Override
	public Task getByUniqueId(String uuid) {
		
		return (Task) Context.getService(GmaoService.class).getEntityByUuid(Task.class, uuid);
	}
	
	@Override
	public Task save(Task task) {
		return (Task) Context.getService(GmaoService.class).upsert(task);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("taskName") == null) {
			throw new ConversionException("Required properties: taskName");
		}
		
		Task task = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(task);
		return ConversionUtil.convertToRepresentation(task, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Task task = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(task);
		return ConversionUtil.convertToRepresentation(task, context.getRepresentation());
	}
	
	@Override
	protected void delete(Task task, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(task);
	}
	
	@Override
	public void purge(Task task, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(task);
	}
	
	private Task constructAgent(String uuid, SimpleObject properties) {
		Task task;
		EquipmentType equipmentType = null;
		if (properties.get("EquipmentType") != null) {
			Integer equipmentTypeId = properties.get("EquipmentType");
			equipmentType = (EquipmentType) Context.getService(GmaoService.class).getEntityByid(EquipmentType.class,
			    "equipmentTypeId", equipmentTypeId);
		}
		if (uuid != null) {
			task = (Task) Context.getService(GmaoService.class).getEntityByUuid(Task.class, uuid);
			if (task == null) {
				throw new IllegalPropertyException("Bed Type not exist");
			}
			
			if (properties.get("taskName") != null) {
				task.setTaskName((String) properties.get("taskName"));
			}
			
			if (properties.get("taskProcedure") != null) {
				task.setTaskProcedure((String) properties.get("taskProcedure"));
			}
			
			if (properties.get("equipmentTypeId") != null) {
				task.setEquipmentType(equipmentType);
			}
			
		} else {
			task = new Task();
			if (properties.get("fullName") == null || properties.get("phoneNumber") == null) {
				throw new IllegalPropertyException("Required parameters: fullName, phoneNumber");
			}
			task.setTaskName((String) properties.get("taskName"));
			task.setTaskProcedure((String) properties.get("taskProcedure"));
			task.setEquipmentType(equipmentType);
		}
		
		return task;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/task";
	}
	
}

package org.openmrs.module.savicsgmao.web.resource;

import java.util.Date;
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
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Department;
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipment", supportedClass = Equipment.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipmentRequestResource extends DelegatingCrudResource<Equipment> {
	
	@Override
	public Equipment newDelegate() {
		return new Equipment();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("Department");
			description.addProperty("EquipmentType");
			description.addProperty("serialNumber");
			description.addProperty("acquisitionDate");
			description.addProperty("equipmentStatus");
			description.addProperty("designation");
			description.addProperty("localization");
			description.addProperty("lastModifiedDate");
			description.addProperty("equipmentWeight");
			description.addProperty("volume");
			description.addProperty("acquisitionValue");
			description.addProperty("tracking");
			description.addProperty("inService");
			description.addProperty("operatingState");
			description.addProperty("commisionningYear");
			description.addProperty("comments");
			description.addProperty("providerId");
			description.addProperty("Maintenances");
			description.addProperty("MaintenanceRequests");
			description.addProperty("MaintenanceTasks");
			description.addProperty("EquipmentOperationItems");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("Department");
			description.addProperty("EquipmentType");
			description.addProperty("serialNumber");
			description.addProperty("acquisitionDate");
			description.addProperty("equipmentStatus");
			description.addProperty("designation");
			description.addProperty("localization");
			description.addProperty("lastModifiedDate");
			description.addProperty("equipmentWeight");
			description.addProperty("volume");
			description.addProperty("acquisitionValue");
			description.addProperty("tracking");
			description.addProperty("inService");
			description.addProperty("operatingState");
			description.addProperty("commisionningYear");
			description.addProperty("comments");
			description.addProperty("providerId");
			description.addProperty("Maintenances");
			description.addProperty("MaintenanceRequests");
			description.addProperty("MaintenanceTasks");
			description.addProperty("EquipmentOperationItems");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Equipment> equipmentList = Context.getService(GmaoService.class).getAll(Equipment.class, context.getLimit(),
		    context.getStartIndex());
		return new AlreadyPaged<Equipment>(context, equipmentList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("fullName");
		List<Equipment> equipmentList = Context.getService(GmaoService.class).doSearch(Equipment.class, "fullName", value,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Equipment>(context, equipmentList, false);
	}
	
	@Override
	public Equipment getByUniqueId(String uuid) {
		
		return (Equipment) Context.getService(GmaoService.class).getEntityByUuid(Equipment.class, uuid);
	}
	
	@Override
	public Equipment save(Equipment equipment) {
		return (Equipment) Context.getService(GmaoService.class).upsert(equipment);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("designation") == null || propertiesToCreate.get("serialNumber") == null) {
			throw new ConversionException("Required properties: designation, serialNumber");
		}
		
		Equipment equipment = this.constructEquipment(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(equipment);
		return ConversionUtil.convertToRepresentation(equipment, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Equipment equipment = this.constructEquipment(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(equipment);
		return ConversionUtil.convertToRepresentation(equipment, context.getRepresentation());
	}
	
	@Override
	protected void delete(Equipment equipment, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipment);
	}
	
	@Override
	public void purge(Equipment equipment, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipment);
	}
	
	private Equipment constructEquipment(String uuid, SimpleObject properties) {
		Equipment equipment;
		Department department = null;
		if (properties.get("Department") != null) {
			String departementID = properties.get("Department");
			department = (Department) Context.getService(GmaoService.class).getEntityByid(Department.class, "departmentId",
			    departementID);
		}
		EquipmentType equipmentType = null;
		if (properties.get("EquipmentType") != null) {
			String equipmentTypeId = properties.get("EquipmentType");
			equipmentType = (EquipmentType) Context.getService(GmaoService.class).getEntityByid(EquipmentType.class,
			    "equipmentTypeId", equipmentTypeId);
		}
		
		if (uuid != null) {
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByUuid(Equipment.class, uuid);
			if (equipment == null) {
				throw new IllegalPropertyException("Bed Type not exist");
			}
			
			if (properties.get("serialNumber") != null) {
				equipment.setSerialNumber((String) properties.get("serialNumber"));
			}
			
			if (properties.get("designation") != null) {
				equipment.setDesignation((String) properties.get("designation"));
			}
			
			if (properties.get("acquisitionDate") != null) {
				equipment.setAcquisitionDate((Date) properties.get("acquisitionDate"));
			}
			
			if (properties.get("equipmentStatus") != null) {
				equipment.setEquipmentStatus((Integer) properties.get("equipmentStatus"));
			}
			
			equipment.setDepartment(department);
			equipment.setEquipmentType(equipmentType);
			
		} else {
			equipment = new Equipment();
			if (properties.get("designation") == null || properties.get("serialNumber") == null) {
				throw new IllegalPropertyException("Required parameters: designation, serialNumber");
			}
			equipment.setSerialNumber((String) properties.get("serialNumber"));
			equipment.setDesignation((String) properties.get("designation"));
			equipment.setAcquisitionDate((Date) properties.get("acquisitionDate"));
			equipment.setEquipmentStatus((Integer) properties.get("equipmentStatus"));
			equipment.setDepartment(department);
			equipment.setEquipmentType(equipmentType);
		}
		
		return equipment;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipment";
	}
	
}

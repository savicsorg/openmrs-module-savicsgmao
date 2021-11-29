package org.openmrs.module.savicsgmao.web.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Department;
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipment", supportedClass = Equipment.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipmentRequestResource extends DelegatingCrudResource<Equipment> {
	
	@Override
	public Equipment newDelegate() {
		return new Equipment();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("department");
			description.addProperty("equipmentType");
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
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("department");
			description.addProperty("equipmentType");
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
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("department");
			description.addProperty("equipmentType");
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
			description.addSelfLink();
			return description;
		}
		return null;
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
		try {
			if (propertiesToCreate.get("designation") == null || propertiesToCreate.get("serialNumber") == null) {
				throw new ConversionException("Required properties: designation, serialNumber");
			}
			
			Equipment equipment = this.constructEquipment(null, propertiesToCreate);
			Context.getService(GmaoService.class).upsert(equipment);
			return ConversionUtil.convertToRepresentation(equipment, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(EquipmentRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		try {
			Equipment equipment = this.constructEquipment(uuid, propertiesToUpdate);
			Context.getService(GmaoService.class).upsert(equipment);
			return ConversionUtil.convertToRepresentation(equipment, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(EquipmentRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	protected void delete(Equipment equipment, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipment);
	}
	
	@Override
	public void purge(Equipment equipment, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipment);
	}
	
	private Equipment constructEquipment(String uuid, SimpleObject properties) throws ParseException {
		Equipment equipment;
		Department department = null;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (properties.get("department") != null) {
			Integer departementID = properties.get("department");
			department = (Department) Context.getService(GmaoService.class).getEntityByid(Department.class, "departmentId",
			    departementID);
		}
		EquipmentType equipmentType = null;
		if (properties.get("equipmentType") != null) {
			Integer equipmentTypeId = properties.get("equipmentType");
			equipmentType = (EquipmentType) Context.getService(GmaoService.class).getEntityByid(EquipmentType.class,
			    "equipmentTypeId", equipmentTypeId);
		}
		
		if (uuid != null) {
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByUuid(Equipment.class, uuid);
			if (equipment == null) {
				throw new IllegalPropertyException("Equipement Type not exist");
			}
			
			if (properties.get("serialNumber") != null) {
				equipment.setSerialNumber((String) properties.get("serialNumber"));
			}
			
			if (properties.get("designation") != null) {
				equipment.setDesignation((String) properties.get("designation"));
			}
			
			if (properties.get("acquisitionDate") != null) {
				equipment.setAcquisitionDate(simpleDateFormat.parse(properties.get("acquisitionDate").toString()));
			}
			
			if (properties.get("equipmentStatus") != null) {
				equipment.setEquipmentStatus(new Integer(properties.get("equipmentStatus").toString()));
			}
			
			if (properties.get("localization") != null) {
				equipment.setLocalization((String) properties.get("localization"));
			} else {
				equipment.setLocalization("");
			}
			
			if (properties.get("equipmentWeight") != null) {
				equipment.setEquipmentWeight(new Double(properties.get("equipmentWeight").toString()));
			}
			
			if (properties.get("volume") != null) {
				equipment.setVolume(new Double(properties.get("volume").toString()));
			}
			
			if (properties.get("acquisitionValue") != null) {
				equipment.setAcquisitionValue(new Double(properties.get("acquisitionValue").toString()));
			}
			
			if (properties.get("tracking") != null) {
				equipment.setTracking(new Integer(properties.get("tracking").toString()));
			}
			
			if (properties.get("inService") != null) {
				equipment.setInService((Boolean) properties.get("inService"));
			}
			
			if (properties.get("operatingState") != null) {
				equipment.setOperatingState(new Integer(properties.get("operatingState").toString()));
			}
			
			if (properties.get("commisionningYear") != null) {
				equipment.setCommisionningYear(new Integer(properties.get("commisionningYear").toString()));
			}
			
			if (properties.get("providerId") != null) {
				equipment.setProviderId(new Integer(properties.get("providerId").toString()));
			}
			
			if (properties.get("comments") != null) {
				equipment.setComments((String) properties.get("comments"));
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
			equipment.setAcquisitionDate(simpleDateFormat.parse(properties.get("acquisitionDate").toString()));
			equipment.setEquipmentStatus(new Integer(properties.get("equipmentStatus").toString()));
			
			if (properties.get("localization") != null) {
				equipment.setLocalization((String) properties.get("localization"));
			} else {
				equipment.setLocalization("");
			}
			
			if (properties.get("equipmentWeight") != null) {
				equipment.setEquipmentWeight(new Double(properties.get("equipmentWeight").toString()));
			}
			
			if (properties.get("volume") != null) {
				equipment.setVolume(new Double(properties.get("volume").toString()));
			}
			
			//equipment.setAcquisitionValue(new Double(properties.get("acquisitionValue").toString()));
			
			//equipment.setTracking(new Integer(properties.get("tracking").toString()));
			
			equipment.setInService((Boolean) properties.get("inService"));
			
			//equipment.setOperatingState(new Integer(properties.get("operatingState").toString()));
			
			if (properties.get("commisionningYear") != null) {
				equipment.setCommisionningYear(new Integer(properties.get("commisionningYear").toString()));
			}
			
			//equipment.setProviderId(new Integer(properties.get("providerId").toString()));
			if (properties.get("comments") != null) {
				equipment.setComments((String) properties.get("comments"));
			} else {
				equipment.setComments("");
			}
			
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

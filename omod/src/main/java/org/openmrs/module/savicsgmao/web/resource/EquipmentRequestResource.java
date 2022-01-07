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
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.api.entity.Site;
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
			description.addProperty("equipmentType");
			description.addProperty("site");
			description.addProperty("name");
			description.addProperty("serialNumber");
			description.addProperty("type");
			description.addProperty("model");
			description.addProperty("supplier");
			description.addProperty("power");
			description.addProperty("aftersaleservice");
			description.addProperty("responsibleperson");
			description.addProperty("serviceStatus");
			description.addProperty("useraffectedto");
			description.addProperty("replacementcomponent");
			description.addProperty("manuals");
			description.addProperty("explainMore");
			description.addProperty("tracking");
			description.addProperty("commisionningYear");
			description.addProperty("acquisitionDate");
			description.addProperty("acquisitionValue");
			description.addProperty("acquisitionMode");
			description.addProperty("volume");
			description.addProperty("weight");
			description.addProperty("comment");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipmentType");
			description.addProperty("site");
			description.addProperty("name");
			description.addProperty("serialNumber");
			description.addProperty("type");
			description.addProperty("model");
			description.addProperty("supplier");
			description.addProperty("power");
			description.addProperty("explainMore");
			description.addProperty("tracking");
			description.addProperty("aftersaleservice");
			description.addProperty("responsibleperson");
			description.addProperty("serviceStatus");
			description.addProperty("useraffectedto");
			description.addProperty("replacementcomponent");
			description.addProperty("manuals");
			description.addProperty("commisionningYear");
			description.addProperty("acquisitionDate");
			description.addProperty("acquisitionValue");
			description.addProperty("acquisitionMode");
			description.addProperty("volume");
			description.addProperty("weight");
			description.addProperty("comment");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipmentType");
			description.addProperty("site");
			description.addProperty("name");
			description.addProperty("serialNumber");
			description.addProperty("type");
			description.addProperty("model");
			description.addProperty("supplier");
			description.addProperty("power");
			description.addProperty("explainMore");
			description.addProperty("tracking");
			description.addProperty("aftersaleservice");
			description.addProperty("responsibleperson");
			description.addProperty("serviceStatus");
			description.addProperty("useraffectedto");
			description.addProperty("replacementcomponent");
			description.addProperty("manuals");
			description.addProperty("commisionningYear");
			description.addProperty("acquisitionDate");
			description.addProperty("acquisitionValue");
			description.addProperty("acquisitionMode");
			description.addProperty("volume");
			description.addProperty("weight");
			description.addProperty("comment");
			description.addProperty("lastmodified");
			description.addProperty("creation");
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
		String value = context.getParameter("name");
		List<Equipment> equipmentList = Context.getService(GmaoService.class).doSearch(Equipment.class, "name", value,
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
			if (propertiesToCreate.get("name") == null || propertiesToCreate.get("serialNumber") == null) {
				throw new ConversionException("Required properties: name, serialNumber");
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
		Site site = null;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (properties.get("site") != null) {
			Integer siteId = properties.get("site");
			site = (Site) Context.getService(GmaoService.class).getEntityByid(Site.class, "id", siteId);
		}
		EquipmentType equipmentType = null;
		if (properties.get("equipmentType") != null) {
			Integer equipmentTypeId = properties.get("equipmentType");
			equipmentType = (EquipmentType) Context.getService(GmaoService.class).getEntityByid(EquipmentType.class, "id",
			    equipmentTypeId);
		}
		
		if (uuid != null) {
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByUuid(Equipment.class, uuid);
			if (equipment == null) {
				throw new IllegalPropertyException("Equipement Type not exist");
			}
		} else {
			equipment = new Equipment();
		}
		
		if (properties.get("serialNumber") != null) {
			equipment.setSerialNumber((String) properties.get("serialNumber"));
		}
		
		if (properties.get("name") != null) {
			equipment.setName((String) properties.get("name"));
		}
		
		if (properties.get("acquisitionDate") != null) {
			equipment.setAcquisitionDate(simpleDateFormat.parse(properties.get("acquisitionDate").toString()));
		}
		
		if (properties.get("type") != null) {
			equipment.setType(properties.get("type").toString());
		}
		
		if (properties.get("model") != null) {
			equipment.setModel(properties.get("model").toString());
		}
		
		if (properties.get("supplier") != null) {
			equipment.setSupplier(properties.get("supplier").toString());
		}
		
		if (properties.get("power") != null) {
			equipment.setPower(properties.get("power").toString());
		}
		
		if (properties.get("aftersaleservice") != null) {
			equipment.setAftersaleservice(Boolean.valueOf(properties.get("aftersaleservice").toString()));
		}
		
		if (properties.get("responsibleperson") != null) {
			equipment.setResponsibleperson(properties.get("responsibleperson").toString());
		}
		
		if (properties.get("serviceStatus") != null) {
			equipment.setServiceStatus(new Integer(properties.get("serviceStatus").toString()));
		}
		
		if (properties.get("useraffectedto") != null) {
			equipment.setUseraffectedto(properties.get("useraffectedto").toString());
		}
		
		if (properties.get("replacementcomponent") != null) {
			equipment.setReplacementcomponent(new Integer(properties.get("replacementcomponent").toString()));
		}
		
		if (properties.get("tracking") != null) {
			equipment.setTracking(Integer.valueOf(properties.get("tracking").toString()));
		}
		
		if (properties.get("manuals") != null) {
			equipment.setManuals(Integer.valueOf(properties.get("manuals").toString()));
		}
		
		if (properties.get("commisionningYear") != null) {
			equipment.setCommisionningYear(new Integer(properties.get("commisionningYear").toString()));
		}
		
		if (properties.get("acquisitionValue") != null) {
			equipment.setAcquisitionValue(new Double(properties.get("acquisitionValue").toString()));
		}
		
		if (properties.get("explainMore") != null) {
			equipment.setExplainMore(properties.get("explainMore").toString());
		}
		
		if (properties.get("acquisitionMode") != null) {
			equipment.setAcquisitionMode(properties.get("acquisitionMode").toString());
		}
		
		if (properties.get("volume") != null) {
			equipment.setVolume(new Double(properties.get("volume").toString()));
		}
		
		if (properties.get("weight") != null) {
			equipment.setVolume(new Double(properties.get("weight").toString()));
		}
		
		if (properties.get("comment") != null) {
			equipment.setComment((String) properties.get("comment"));
		}
		
		equipment.setLastmodified(new Date());
		
		equipment.setSite(site);
		equipment.setEquipmentType(equipmentType);
		
		return equipment;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipment";
	}
	
}

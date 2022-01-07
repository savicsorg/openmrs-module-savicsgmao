package org.openmrs.module.savicsgmao.web.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenance", supportedClass = Maintenance.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceRequestResource extends DelegatingCrudResource<Maintenance> {
	
	@Override
	public Maintenance newDelegate() {
		return new Maintenance();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("maintenanceType");
			description.addProperty("maintenanceRequest");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("status");
			description.addProperty("doneby");
			description.addProperty("taskname");
			description.addProperty("taskdescription");
			description.addProperty("equipment");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("maintenanceType");
			description.addProperty("maintenanceRequest");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("status");
			description.addProperty("doneby");
			description.addProperty("taskname");
			description.addProperty("taskdescription");
			description.addProperty("equipment");
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
			description.addProperty("maintenanceType");
			description.addProperty("maintenanceRequest");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("status");
			description.addProperty("doneby");
			description.addProperty("taskname");
			description.addProperty("taskdescription");
			description.addProperty("equipment");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Maintenance> maintenanceList = Context.getService(GmaoService.class).getAll(Maintenance.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Maintenance>(context, maintenanceList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("doneby");
		List<Maintenance> maintenanceList = Context.getService(GmaoService.class).doSearch(Maintenance.class, "doneby",
		    value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Maintenance>(context, maintenanceList, false);
	}
	
	@Override
	public Maintenance getByUniqueId(String uuid) {
		
		return (Maintenance) Context.getService(GmaoService.class).getEntityByUuid(Maintenance.class, uuid);
	}
	
	@Override
	public Maintenance save(Maintenance maintenance) {
		return (Maintenance) Context.getService(GmaoService.class).upsert(maintenance);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		try {
			if (propertiesToCreate.get("doneby") == null) {
				throw new ConversionException("Required properties: doneby");
			}
			
			Maintenance maintenance = this.constructMaintenance(null, propertiesToCreate);
			Context.getService(GmaoService.class).upsert(maintenance);
			return ConversionUtil.convertToRepresentation(maintenance, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(MaintenanceRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		try {
			Maintenance maintenance = this.constructMaintenance(uuid, propertiesToUpdate);
			Context.getService(GmaoService.class).upsert(maintenance);
			return ConversionUtil.convertToRepresentation(maintenance, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(MaintenanceRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	protected void delete(Maintenance maintenance, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenance);
	}
	
	@Override
	public void purge(Maintenance maintenance, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenance);
	}
	
	private Maintenance constructMaintenance(String uuid, SimpleObject properties) throws ParseException {
		Maintenance maintenance;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer equipementId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "id", equipementId);
		}
		MaintenanceType maintenanceType = null;
		if (properties.get("maintenanceType") != null) {
			Integer id = properties.get("maintenanceType");
			maintenanceType = (MaintenanceType) Context.getService(GmaoService.class).getEntityByid(MaintenanceType.class,
			    "id", id);
		}
		MaintenanceRequest maintenanceRequest = null;
		if (properties.get("maintenanceRequest") != null) {
			Integer id = properties.get("maintenanceRequest");
			maintenanceRequest = (MaintenanceRequest) Context.getService(GmaoService.class).getEntityByid(
			    MaintenanceRequest.class, "id", id);
		}
		
		if (uuid != null) {
			maintenance = (Maintenance) Context.getService(GmaoService.class).getEntityByUuid(Maintenance.class, uuid);
			if (maintenance == null) {
				throw new IllegalPropertyException("Maintenance not exist");
			}
		} else {
			maintenance = new Maintenance();
			if (properties.get("maintenanceType") == null || properties.get("name") == null) {
				throw new IllegalPropertyException("Required parameters: maintenanceType, name");
			}
		}
		if (properties.get("maintenanceType") != null) {
			maintenance.setMaintenanceType(maintenanceType);
		}
		
		if (properties.get("name") != null) {
			maintenance.setName((String) properties.get("name"));
		}
		
		if (properties.get("maintenanceRequest") != null) {
			maintenance.setMaintenanceRequest(maintenanceRequest);
		}
		
		if (properties.get("description") != null) {
			maintenance.setDescription((String) properties.get("name"));
		}
		
		if (properties.get("doneby") != null) {
			maintenance.setDoneby((String) properties.get("doneby"));
		}
		
		if (properties.get("startdate") != null) {
			maintenance.setStartdate(simpleDateFormat.parse(properties.get("startdate").toString()));
		}
		
		if (properties.get("enddate") != null) {
			maintenance.setEnddate(simpleDateFormat.parse(properties.get("enddate").toString()));
		}
		
		if (properties.get("status") != null) {
			maintenance.setStatus(new Short(properties.get("status").toString()));
		}
		
		if (properties.get("equipment") != null) {
			maintenance.setEquipment(equipment);
		}
		
		return maintenance;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenance";
	}
	
}

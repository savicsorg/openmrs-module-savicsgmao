package org.openmrs.module.savicsgmao.web.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Healthcenter;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceEvent;
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
			description.addProperty("maintenanceEvent");
			description.addProperty("name");
			description.addProperty("reason");
			description.addProperty("description");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("dueDate");
			description.addProperty("status");
			description.addProperty("doneby");
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
			description.addProperty("maintenanceEvent");
			description.addProperty("name");
			description.addProperty("reason");
			description.addProperty("description");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("dueDate");
			description.addProperty("status");
			description.addProperty("doneby");
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
			description.addProperty("maintenanceEvent");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("dueDate");
			description.addProperty("status");
			description.addProperty("doneby");
			description.addProperty("name");
			description.addProperty("reason");
			description.addProperty("description");
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
		Long count = Context.getService(GmaoService.class).doCount(Maintenance.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Maintenance>(context, maintenanceList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("doneby");
		List<Maintenance> maintenanceList = Context.getService(GmaoService.class).doSearch(Maintenance.class, "doneby",
		    value, context.getLimit(), context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Healthcenter.class, "doneby", value);
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Maintenance>(context, maintenanceList, hasMore, count);
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
			Object maintenanceSerial = Context.getService(GmaoService.class).upsert(maintenance);
			
			MaintenanceRequest maintenanceRequest = null;
			if (propertiesToCreate.get("maintenanceRequest") != null) {
				DateFormat simpleDateFormatApprove = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				Integer id = propertiesToCreate.get("maintenanceRequest");
				maintenanceRequest = (MaintenanceRequest) Context.getService(GmaoService.class).getEntityByid(
				    MaintenanceRequest.class, "id", id);
				maintenanceRequest.setStatus("DONE");
				maintenanceRequest.setMaintenanceDate(new Date());
				maintenanceRequest.setCreation(simpleDateFormatApprove.parse(maintenanceRequest.getCreation().toString()));
				maintenanceRequest.setApproval(simpleDateFormatApprove.parse(maintenanceRequest.getApproval().toString()));
				maintenanceRequest.setLastmodified(new Date());
				Context.getService(GmaoService.class).upsert(maintenanceRequest);
			}
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
			
			Object maintenanceSerial = Context.getService(GmaoService.class).upsert(maintenance);
			
			MaintenanceRequest maintenanceRequest = null;
			if (propertiesToUpdate.get("maintenanceRequest") != null) {
				DateFormat simpleDateFormatApprove = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				Integer id = propertiesToUpdate.get("maintenanceRequest");
				maintenanceRequest = (MaintenanceRequest) Context.getService(GmaoService.class).getEntityByid(
				    MaintenanceRequest.class, "id", id);
				maintenanceRequest.setStatus("DONE");
				maintenanceRequest.setMaintenanceDate(new Date());
				maintenanceRequest.setCreation(simpleDateFormatApprove.parse(maintenanceRequest.getCreation().toString()));
				maintenanceRequest.setApproval(simpleDateFormatApprove.parse(maintenanceRequest.getApproval().toString()));
				maintenanceRequest.setLastmodified(new Date());
				Context.getService(GmaoService.class).upsert(maintenanceRequest);
			}
			
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
		
		if (maintenance.getMaintenanceRequest() != null) {
			try {
				DateFormat simpleDateFormatApprove = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				MaintenanceRequest maintenanceRequest = maintenance.getMaintenanceRequest();
				
				maintenanceRequest.setStatus("VALID");
				maintenanceRequest.setMaintenanceDate(null);
				maintenanceRequest.setCreation(simpleDateFormatApprove.parse(maintenanceRequest.getCreation().toString()));
				maintenanceRequest.setApproval(simpleDateFormatApprove.parse(maintenanceRequest.getApproval().toString()));
				maintenanceRequest.setLastmodified(new Date());
				
				Context.getService(GmaoService.class).upsert(maintenanceRequest);
			}
			catch (ParseException ex) {
				Logger.getLogger(MaintenanceRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	@Override
	public void purge(Maintenance maintenance, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenance);
	}
	
	private Maintenance constructMaintenance(String uuid, SimpleObject properties) throws ParseException {
		Maintenance maintenance;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
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
		
		MaintenanceEvent maintenanceEvent = null;
		if (properties.get("maintenanceEvent") != null) {
			Integer id = properties.get("maintenanceEvent");
			maintenanceEvent = (MaintenanceEvent) Context.getService(GmaoService.class).getEntityByid(
			    MaintenanceEvent.class, "id", id);
		}
		
		if (uuid != null) {
			maintenance = (Maintenance) Context.getService(GmaoService.class).getEntityByUuid(Maintenance.class, uuid);
			if (maintenance == null) {
				throw new IllegalPropertyException("Maintenance not exist");
			}
			maintenance.setLastmodified(new Date());
		} else {
			maintenance = new Maintenance();
			if (properties.get("maintenanceType") == null || properties.get("name") == null) {
				throw new IllegalPropertyException("Required parameters: maintenanceType, name");
			}
			maintenance.setCreation(new Date());
			maintenance.setLastmodified(new Date());
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
		
		if (properties.get("maintenanceEvent") != null) {
			maintenance.setMaintenanceEvent(maintenanceEvent);
		}
		
		if (properties.get("description") != null) {
			maintenance.setDescription((String) properties.get("description"));
		}
		
		if (properties.get("doneby") != null) {
			maintenance.setDoneby((String) properties.get("doneby"));
		}
		
		if (properties.get("reason") != null) {
			maintenance.setReason((String) properties.get("reason"));
		}
		
		if (properties.get("startdate") != null) {
			Date startdate = simpleDateFormat.parse(properties.get("startdate").toString());
			maintenance.setStartdate(startdate);
		}
		
		if (properties.get("enddate") != null) {
			Date enddate = simpleDateFormat.parse(properties.get("enddate").toString());
			maintenance.setEnddate(enddate);
		} else if (properties.get("enddate") == null || properties.get("enddate").equals("")) {
			maintenance.setEnddate(null);
		}
		
		if (properties.get("dueDate") != null) {
			Date dueDate = simpleDateFormat.parse(properties.get("dueDate").toString());
			maintenance.setDueDate(dueDate);
		} else if (properties.get("dueDate") == null || properties.get("dueDate").equals("")) {
			maintenance.setDueDate(null);
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

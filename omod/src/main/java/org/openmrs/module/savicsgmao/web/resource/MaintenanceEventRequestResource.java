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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceEvent;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenanceEvent", supportedClass = MaintenanceEvent.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceEventRequestResource extends DelegatingCrudResource<MaintenanceEvent> {
	
	@Override
	public MaintenanceEvent newDelegate() {
		return new MaintenanceEvent();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("priority");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("status");
			description.addProperty("frequency");
			description.addProperty("repeatInterval");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("priority");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("status");
			description.addProperty("frequency");
			description.addProperty("repeatInterval");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("name");
			description.addProperty("description");
			description.addProperty("priority");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("status");
			description.addProperty("frequency");
			description.addProperty("repeatInterval");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<MaintenanceEvent> maintenanceEventList = Context.getService(GmaoService.class).getAll(MaintenanceEvent.class,
		    context.getLimit(), context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(MaintenanceEvent.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<MaintenanceEvent>(context, maintenanceEventList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String name = context.getParameter("name");
		String status = context.getParameter("status");
		String id = context.getParameter("id");
		List<MaintenanceEvent> maintenanceEventList;
		Long count;
		if (name != null) {
			maintenanceEventList = Context.getService(GmaoService.class).doSearch(MaintenanceEvent.class, "name", name,
			    context.getLimit(), context.getStartIndex());
			count = Context.getService(GmaoService.class).doCount(MaintenanceEvent.class, "name", name);
		} else if (status != null) {
			maintenanceEventList = Context.getService(GmaoService.class).doSearch(MaintenanceEvent.class, "status", status,
			    context.getLimit(), context.getStartIndex());
			count = Context.getService(GmaoService.class).doCount(MaintenanceEvent.class, "status", status);
		} else if (id != null) {
			maintenanceEventList = Context.getService(GmaoService.class).doSearch(MaintenanceEvent.class, "id", id,
			    context.getLimit(), context.getStartIndex());
			count = Context.getService(GmaoService.class).doCount(MaintenanceEvent.class, "id", id);
		} else {
			maintenanceEventList = Context.getService(GmaoService.class).getAll(MaintenanceEvent.class, context.getLimit(),
			    context.getStartIndex());
			count = Context.getService(GmaoService.class).doCount(MaintenanceEvent.class);
		}
		
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<MaintenanceEvent>(context, maintenanceEventList, hasMore, count);
	}
	
	@Override
	public MaintenanceEvent getByUniqueId(String uuid) {
		
		return (MaintenanceEvent) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceEvent.class, uuid);
	}
	
	@Override
	public MaintenanceEvent save(MaintenanceEvent maintenanceEvent) {
		return (MaintenanceEvent) Context.getService(GmaoService.class).upsert(maintenanceEvent);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		System.out.println("----CREATION EVENT hhh 00---- uuid = ");
		try {
			if (propertiesToCreate.get("name") == null) {
				throw new ConversionException("Required properties: namennnnn");
			}
			
			MaintenanceEvent maintenanceEvent = this.constructAgent(null, propertiesToCreate);
			Context.getService(GmaoService.class).upsert(maintenanceEvent);
			System.out.println("----CREATION EVENT hhh 0---- uuid = " + maintenanceEvent.getUuid());
			return ConversionUtil.convertToRepresentation(maintenanceEvent, context.getRepresentation());
		}
		catch (ParseException ex) {
			ex.printStackTrace();
			Logger.getLogger(MaintenanceEventRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		try {
			System.out.println("----UPDATE EVENT 00---- uuid = ");
			MaintenanceEvent maintenanceEvent = this.constructAgent(uuid, propertiesToUpdate);
			Context.getService(GmaoService.class).upsert(maintenanceEvent);
			return ConversionUtil.convertToRepresentation(maintenanceEvent, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(MaintenanceEventRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	protected void delete(MaintenanceEvent maintenanceEvent, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceEvent);
	}
	
	@Override
	public void purge(MaintenanceEvent maintenanceEvent, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceEvent);
	}
	
	private MaintenanceEvent constructAgent(String uuid, SimpleObject properties) throws ParseException {
		System.out.println("----constructAgent EVENT 00---- uuid = ");
		MaintenanceEvent maintenanceEvent;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		DateFormat simpleDateFormatApprove = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer regionId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "id", regionId);
		}
		
		if (uuid != null) {
			maintenanceEvent = (MaintenanceEvent) Context.getService(GmaoService.class).getEntityByUuid(
			    MaintenanceEvent.class, uuid);
			if (maintenanceEvent == null) {
				throw new IllegalPropertyException("Maintenance Event not exist");
			}
		} else {
			maintenanceEvent = new MaintenanceEvent();
			if (properties.get("name") == null || properties.get("equipment") == null) {
				throw new IllegalPropertyException("Required parameters: equipment, name");
			}
			maintenanceEvent.setCreation(new Date());
		}
		if (properties.get("name") != null) {
			maintenanceEvent.setName((String) properties.get("name"));
		}
		
		if (properties.get("description") != null) {
			maintenanceEvent.setDescription(properties.get("description").toString());
		}
		
		if (properties.get("priority") != null) {
			maintenanceEvent.setPriority(Integer.valueOf(properties.get("priority").toString()));
		}
		
		if (properties.get("startdate") != null) {
			Date startdate = simpleDateFormat.parse(properties.get("startdate").toString());
			maintenanceEvent.setStartdate(startdate);
		}
		
		if (properties.get("enddate") != null) {
			Date enddate = simpleDateFormat.parse(properties.get("enddate").toString());
			maintenanceEvent.setEnddate(enddate);
		} else if (properties.get("enddate") == null || properties.get("enddate").equals("")) {
			maintenanceEvent.setEnddate(null);
		}
		
		if (properties.get("frequency") != null) {
			maintenanceEvent.setFrequency(properties.get("frequency").toString());
		}
		
		if (properties.get("status") != null) {
			maintenanceEvent.setStatus((new Short(properties.get("status").toString())));
		}
		
		if (properties.get("repeatInterval") != null) {
			maintenanceEvent.setRepeatInterval((new Long(properties.get("repeatInterval").toString())));
		}
		
		maintenanceEvent.setLastmodified(new Date());
		
		if (properties.get("equipment") != null) {
			maintenanceEvent.setEquipment(equipment);
		}
		System.out.println("----CREATION EVENT---- uuid = " + maintenanceEvent.getUuid());
		return maintenanceEvent;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenanceEvent";
	}
	
}

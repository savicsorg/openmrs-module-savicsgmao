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
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.MaintenanceRequest;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenanceRequest", supportedClass = MaintenanceRequest.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceRequestRequestResource extends DelegatingCrudResource<MaintenanceRequest> {
	
	@Override
	public MaintenanceRequest newDelegate() {
		return new MaintenanceRequest();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("requestedby");
			description.addProperty("registerNumber");
			description.addProperty("natureOfWork");
			description.addProperty("priority");
			description.addProperty("motif");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("equipment");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("requestedby");
			description.addProperty("registerNumber");
			description.addProperty("natureOfWork");
			description.addProperty("priority");
			description.addProperty("motif");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("equipment");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("requestedby");
			description.addProperty("registerNumber");
			description.addProperty("natureOfWork");
			description.addProperty("priority");
			description.addProperty("motif");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addProperty("equipment");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<MaintenanceRequest> maintenanceRequestList = Context.getService(GmaoService.class).getAll(
		    MaintenanceRequest.class, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceRequest>(context, maintenanceRequestList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("registerNumber");
		List<MaintenanceRequest> maintenanceRequestList = Context.getService(GmaoService.class).doSearch(
		    MaintenanceRequest.class, "registerNumber", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceRequest>(context, maintenanceRequestList, false);
	}
	
	@Override
	public MaintenanceRequest getByUniqueId(String uuid) {
		
		return (MaintenanceRequest) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceRequest.class, uuid);
	}
	
	@Override
	public MaintenanceRequest save(MaintenanceRequest maintenanceRequest) {
		return (MaintenanceRequest) Context.getService(GmaoService.class).upsert(maintenanceRequest);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("registerNumber") == null) {
			throw new ConversionException("Required properties: registerNumber");
		}
		
		MaintenanceRequest maintenanceRequest = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(maintenanceRequest);
		return ConversionUtil.convertToRepresentation(maintenanceRequest, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		MaintenanceRequest maintenanceRequest = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(maintenanceRequest);
		return ConversionUtil.convertToRepresentation(maintenanceRequest, context.getRepresentation());
	}
	
	@Override
	protected void delete(MaintenanceRequest maintenanceRequest, String reason, RequestContext context)
	        throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceRequest);
	}
	
	@Override
	public void purge(MaintenanceRequest maintenanceRequest, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceRequest);
	}
	
	private MaintenanceRequest constructAgent(String uuid, SimpleObject properties) {
		MaintenanceRequest maintenanceRequest;
		
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer regionId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "id", regionId);
		}
		
		if (uuid != null) {
			maintenanceRequest = (MaintenanceRequest) Context.getService(GmaoService.class).getEntityByUuid(
			    MaintenanceRequest.class, uuid);
			if (maintenanceRequest == null) {
				throw new IllegalPropertyException("Maintenance Request not exist");
			}
		} else {
			maintenanceRequest = new MaintenanceRequest();
			if (properties.get("registerNumber") == null) {
				throw new IllegalPropertyException("Required parameters: registerNumber");
			}
		}
		if (properties.get("registerNumber") != null) {
			maintenanceRequest.setRegisterNumber((String) properties.get("registerNumber"));
		}
		
		if (properties.get("requestedby") != null) {
			maintenanceRequest.setRequestedby(properties.get("requestedby").toString());
		}
		
		if (properties.get("natureOfWork") != null) {
			maintenanceRequest.setNatureOfWork((String) properties.get("natureOfWork"));
		}
		
		if (properties.get("priority") != null) {
			maintenanceRequest.setPriority(Integer.valueOf(properties.get("priority").toString()));
		}
		
		if (properties.get("motif") != null) {
			maintenanceRequest.setMotif(properties.get("motif").toString());
		}
		
		if (properties.get("natureOfWork") != null) {
			maintenanceRequest.setNatureOfWork((String) properties.get("natureOfWork"));
		}
		equipment.setLastmodified(new Date());
		
		if (properties.get("equipment") != null) {
			maintenanceRequest.setEquipment(equipment);
		}
		
		return maintenanceRequest;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenanceRequest";
	}
	
}

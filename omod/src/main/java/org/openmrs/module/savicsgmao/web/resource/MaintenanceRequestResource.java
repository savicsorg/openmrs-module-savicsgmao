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
import org.openmrs.module.savicsgmao.api.entity.Agent;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
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
			description.addProperty("designation");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("details");
			description.addProperty("equipment");
			description.addProperty("agent");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("maintenanceType");
			description.addProperty("designation");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("details");
			description.addProperty("equipment");
			description.addProperty("agent");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("maintenanceType");
			description.addProperty("designation");
			description.addProperty("startdate");
			description.addProperty("enddate");
			description.addProperty("details");
			description.addProperty("equipment");
			description.addProperty("agent");
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
		String value = context.getParameter("designation");
		List<Maintenance> maintenanceList = Context.getService(GmaoService.class).doSearch(Maintenance.class, "designation",
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
		if (propertiesToCreate.get("designation") == null) {
			throw new ConversionException("Required properties: designation");
		}
		
		Maintenance maintenance = this.constructMaintenance(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(maintenance);
		return ConversionUtil.convertToRepresentation(maintenance, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Maintenance maintenance = this.constructMaintenance(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(maintenance);
		return ConversionUtil.convertToRepresentation(maintenance, context.getRepresentation());
	}
	
	@Override
	protected void delete(Maintenance maintenance, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenance);
	}
	
	@Override
	public void purge(Maintenance maintenance, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenance);
	}
	
	private Maintenance constructMaintenance(String uuid, SimpleObject properties) {
		Maintenance maintenance;
		
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer equipementId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "equipementId",
			    equipementId);
		}
		Agent agent = null;
		if (properties.get("agent") != null) {
			Integer agentId = properties.get("agent");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Agent.class, "agentId", agentId);
		}
		MaintenanceType maintenanceType = null;
		if (properties.get("maintenanceType") != null) {
			Integer maintenanceTypeId = properties.get("maintenanceType");
			maintenanceType = (MaintenanceType) Context.getService(GmaoService.class).getEntityByid(MaintenanceType.class,
			    "maintenanceTypeId", maintenanceTypeId);
		}
		
		if (uuid != null) {
			maintenance = (Maintenance) Context.getService(GmaoService.class).getEntityByUuid(Maintenance.class, uuid);
			if (maintenance == null) {
				throw new IllegalPropertyException("Maintenance not exist");
			}
			
			if (properties.get("maintenanceType") != null) {
				maintenance.setMaintenanceType(maintenanceType);
			}
			
			if (properties.get("designation") != null) {
				maintenance.setDesignation((String) properties.get("designation"));
			}
			
			if (properties.get("startdate") != null) {
				maintenance.setStartdate((Date) properties.get("startdate"));
			}
			
			if (properties.get("enddate") != null) {
				maintenance.setEnddate((Date) properties.get("enddate"));
			}
			
			if (properties.get("details") != null) {
				maintenance.setDetails((String) properties.get("details"));
			}
			
			if (properties.get("equipment") != null) {
				maintenance.setEquipment(equipment);
			}
			
			if (properties.get("agent") != null) {
				maintenance.setAgent(agent);
			}
			
		} else {
			maintenance = new Maintenance();
			if (properties.get("maintenanceType") == null || properties.get("designation") == null) {
				throw new IllegalPropertyException("Required parameters: maintenanceType, designation");
			}
			maintenance.setMaintenanceType(maintenanceType);
			maintenance.setDesignation((String) properties.get("designation"));
			maintenance.setStartdate((Date) properties.get("startdate"));
			maintenance.setEnddate((Date) properties.get("enddate"));
			maintenance.setDetails((String) properties.get("details"));
			maintenance.setEquipment(equipment);
			maintenance.setAgent(agent);
		}
		
		return maintenance;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenance";
	}
	
}

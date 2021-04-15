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
import org.openmrs.module.savicsgmao.api.entity.MaintenanceReason;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenanceReason", supportedClass = MaintenanceReason.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceReasonRequestResource extends DelegatingCrudResource<MaintenanceReason> {
	
	@Override
	public MaintenanceReason newDelegate() {
		return new MaintenanceReason();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("reasonName");
			description.addProperty("reasonCode");
			description.addProperty("details");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("reasonName");
			description.addProperty("reasonCode");
			description.addProperty("details");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<MaintenanceReason> maintenanceReasonList = Context.getService(GmaoService.class).getAll(
		    MaintenanceReason.class, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceReason>(context, maintenanceReasonList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("reasonName");
		List<MaintenanceReason> maintenanceReasonList = Context.getService(GmaoService.class).doSearch(
		    MaintenanceReason.class, "reasonName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceReason>(context, maintenanceReasonList, false);
	}
	
	@Override
	public MaintenanceReason getByUniqueId(String uuid) {
		
		return (MaintenanceReason) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceReason.class, uuid);
	}
	
	@Override
	public MaintenanceReason save(MaintenanceReason maintenanceReason) {
		return (MaintenanceReason) Context.getService(GmaoService.class).upsert(maintenanceReason);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("reasonName") == null || propertiesToCreate.get("reasonCode") == null) {
			throw new ConversionException("Required properties: reasonName, reasonCode");
		}
		
		MaintenanceReason maintenanceReason = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(maintenanceReason);
		return ConversionUtil.convertToRepresentation(maintenanceReason, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		MaintenanceReason maintenanceReason = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(maintenanceReason);
		return ConversionUtil.convertToRepresentation(maintenanceReason, context.getRepresentation());
	}
	
	@Override
	protected void delete(MaintenanceReason maintenanceReason, String reason, RequestContext context)
	        throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceReason);
	}
	
	@Override
	public void purge(MaintenanceReason maintenanceReason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceReason);
	}
	
	private MaintenanceReason constructAgent(String uuid, SimpleObject properties) {
		MaintenanceReason maintenanceReason;
		if (uuid != null) {
			maintenanceReason = (MaintenanceReason) Context.getService(GmaoService.class).getEntityByUuid(
			    MaintenanceReason.class, uuid);
			if (maintenanceReason == null) {
				throw new IllegalPropertyException("Maintenance reason not exist");
			}
			
			if (properties.get("reasonName") != null) {
				maintenanceReason.setReasonName((String) properties.get("reasonName"));
			}
			
			if (properties.get("reasonCode") != null) {
				maintenanceReason.setReasonCode((String) properties.get("reasonCode"));
			}
			
			if (properties.get("details") != null) {
				maintenanceReason.setDetails((String) properties.get("details"));
			}
			
		} else {
			maintenanceReason = new MaintenanceReason();
			if (properties.get("designation") == null || properties.get("planificationCode") == null) {
				throw new IllegalPropertyException("Required parameters: designation, phoneNumber");
			}
			maintenanceReason.setReasonName((String) properties.get("reasonName"));
			maintenanceReason.setReasonCode((String) properties.get("reasonCode"));
			maintenanceReason.setDetails((String) properties.get("details"));
		}
		
		return maintenanceReason;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenanceReason";
	}
	
}

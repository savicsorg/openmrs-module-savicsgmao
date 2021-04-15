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
import org.openmrs.module.savicsgmao.api.entity.Planification;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/planification", supportedClass = Planification.class, supportedOpenmrsVersions = { "2.*.*" })
public class PlanificationRequestResource extends DelegatingCrudResource<Planification> {
	
	@Override
	public Planification newDelegate() {
		return new Planification();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addProperty("planificationCode");
			description.addProperty("planificationDate");
			description.addProperty("observations");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addProperty("planificationCode");
			description.addProperty("planificationDate");
			description.addProperty("observations");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Planification> planificationList = Context.getService(GmaoService.class).getAll(Planification.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Planification>(context, planificationList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("designation");
		List<Planification> planificationList = Context.getService(GmaoService.class).doSearch(Planification.class,
		    "designation", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Planification>(context, planificationList, false);
	}
	
	@Override
	public Planification getByUniqueId(String uuid) {
		
		return (Planification) Context.getService(GmaoService.class).getEntityByUuid(Planification.class, uuid);
	}
	
	@Override
	public Planification save(Planification planification) {
		return (Planification) Context.getService(GmaoService.class).upsert(planification);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("designation") == null || propertiesToCreate.get("planificationCode") == null) {
			throw new ConversionException("Required properties: designation, planificationCode");
		}
		
		Planification planification = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(planification);
		return ConversionUtil.convertToRepresentation(planification, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Planification planification = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(planification);
		return ConversionUtil.convertToRepresentation(planification, context.getRepresentation());
	}
	
	@Override
	protected void delete(Planification planification, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(planification);
	}
	
	@Override
	public void purge(Planification planification, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(planification);
	}
	
	private Planification constructAgent(String uuid, SimpleObject properties) {
		Planification planification;
		if (uuid != null) {
			planification = (Planification) Context.getService(GmaoService.class).getEntityByUuid(Planification.class, uuid);
			if (planification == null) {
				throw new IllegalPropertyException("Planification not exist");
			}
			
			if (properties.get("designation") != null) {
				planification.setDesignation((String) properties.get("designation"));
			}
			
			if (properties.get("planificationCode") != null) {
				planification.setPlanificationCode((String) properties.get("planificationCode"));
			}
			
			if (properties.get("planificationDate") != null) {
				planification.setPlanificationDate((Date) properties.get("planificationDate"));
			}
			
			if (properties.get("observations") != null) {
				planification.setObservations((String) properties.get("observations"));
			}
			
		} else {
			planification = new Planification();
			if (properties.get("designation") == null || properties.get("planificationCode") == null) {
				throw new IllegalPropertyException("Required parameters: designation, phoneNumber");
			}
			planification.setObservations((String) properties.get("observations"));
			planification.setPlanificationDate((Date) properties.get("planificationDate"));
			planification.setPlanificationCode((String) properties.get("planificationCode"));
			planification.setDesignation((String) properties.get("designation"));
		}
		
		return planification;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/planification";
	}
	
}

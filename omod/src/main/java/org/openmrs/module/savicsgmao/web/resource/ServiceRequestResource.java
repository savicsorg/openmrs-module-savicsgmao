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
import org.openmrs.module.savicsgmao.api.entity.Healthcenter;
import org.openmrs.module.savicsgmao.api.entity.Mouvement;
import org.openmrs.module.savicsgmao.api.entity.Service;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/service", supportedClass = Service.class, supportedOpenmrsVersions = { "2.*.*" })
public class ServiceRequestResource extends DelegatingCrudResource<Service> {
	
	@Override
	public Service newDelegate() {
		return new Service();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("healthcenter");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("healthcenter");
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
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("healthcenter");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Service> serviceList = Context.getService(GmaoService.class).getAll(Service.class, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Service.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Service>(context, serviceList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("name");
		List<Service> serviceList = Context.getService(GmaoService.class).doSearch(Service.class, "name", value,
		    context.getLimit(), context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Service.class, "name", value);
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Service>(context, serviceList, hasMore, count);
	}
	
	@Override
	public Service getByUniqueId(String uuid) {
		
		return (Service) Context.getService(GmaoService.class).getEntityByUuid(Service.class, uuid);
	}
	
	@Override
	public Service save(Service service) {
		return (Service) Context.getService(GmaoService.class).upsert(service);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("name") == null) {
			throw new ConversionException("Required properties: name");
		}
		
		Service service = this.constructService(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(service);
		return ConversionUtil.convertToRepresentation(service, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Service service = this.constructService(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(service);
		return ConversionUtil.convertToRepresentation(service, context.getRepresentation());
	}
	
	@Override
	protected void delete(Service service, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(service);
	}
	
	@Override
	public void purge(Service service, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(service);
	}
	
	private Service constructService(String uuid, SimpleObject properties) {
		Healthcenter healthcenter = null;
		Service service;
		
		if (properties.get("healthcenter") != null) {
			Integer healthcenterId = properties.get("healthcenter");
			healthcenter = (Healthcenter) Context.getService(GmaoService.class).getEntityByid(Healthcenter.class, "id",
			    healthcenterId);
		}
		
		if (uuid != null) {
			service = (Service) Context.getService(GmaoService.class).getEntityByUuid(Service.class, uuid);
			if (service == null) {
				throw new IllegalPropertyException("service not exist");
			}
			
			if (properties.get("name") != null) {
				service.setName((String) properties.get("name"));
			}
			
			if (properties.get("code") != null) {
				service.setCode((String) properties.get("code"));
			}
			
			if (properties.get("serviceId") != null) {
				service.setHealthcenter(healthcenter);
			}
		} else {
			service = new Service();
			if (properties.get("name") == null) {
				throw new IllegalPropertyException("Required parameters: name");
			}
			service.setName((String) properties.get("name"));
			service.setCode((String) properties.get("code"));
			service.setHealthcenter(healthcenter);
		}
		service.setLastmodified(new Date());
		
		return service;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/service";
	}
	
}

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
import org.openmrs.module.savicsgmao.api.entity.District;
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.api.entity.Service;
import org.openmrs.module.savicsgmao.api.entity.Healthcenter;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/healthcenter", supportedClass = Healthcenter.class, supportedOpenmrsVersions = { "2.*.*" })
public class HealthcenterRequestResource extends DelegatingCrudResource<Healthcenter> {
	
	@Override
	public Healthcenter newDelegate() {
		return new Healthcenter();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("district");
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
			description.addProperty("district");
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
			description.addProperty("district");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Healthcenter> siteList = Context.getService(GmaoService.class).getAll(Healthcenter.class, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Healthcenter.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Healthcenter>(context, siteList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("name");
		List<Healthcenter> siteList = Context.getService(GmaoService.class).doSearch(Healthcenter.class, "name", value,
		    context.getLimit(), context.getStartIndex());
		
		Long count = Context.getService(GmaoService.class).doCount(Healthcenter.class, "name", value);
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Healthcenter>(context, siteList, hasMore, count);
	}
	
	@Override
	public Healthcenter getByUniqueId(String uuid) {
		
		return (Healthcenter) Context.getService(GmaoService.class).getEntityByUuid(Healthcenter.class, uuid);
	}
	
	@Override
	public Healthcenter save(Healthcenter site) {
		return (Healthcenter) Context.getService(GmaoService.class).upsert(site);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("name") == null) {
			throw new ConversionException("Required properties: name");
		}
		
		Healthcenter site = this.constructHealthcenter(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(site);
		return ConversionUtil.convertToRepresentation(site, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Healthcenter site = this.constructHealthcenter(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(site);
		return ConversionUtil.convertToRepresentation(site, context.getRepresentation());
	}
	
	@Override
	protected void delete(Healthcenter site, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(site);
	}
	
	@Override
	public void purge(Healthcenter site, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(site);
	}
	
	private Healthcenter constructHealthcenter(String uuid, SimpleObject properties) {
		Healthcenter healthcenter;
		
		District district = null;
		if (properties.get("district") != null) {
			Integer districtId = properties.get("district");
			district = (District) Context.getService(GmaoService.class).getEntityByid(District.class, "id", districtId);
		}
		
		if (uuid != null) {
			healthcenter = (Healthcenter) Context.getService(GmaoService.class).getEntityByUuid(Healthcenter.class, uuid);
			if (healthcenter == null) {
				throw new IllegalPropertyException("healthcenter not exist");
			}
			
			if (properties.get("name") != null) {
				healthcenter.setName((String) properties.get("name"));
			}
			
			if (properties.get("code") != null) {
				healthcenter.setCode((String) properties.get("code"));
			}
			
			if (properties.get("districtId") != null) {
				healthcenter.setDistrict(district);
			}
		} else {
			healthcenter = new Healthcenter();
			if (properties.get("name") == null) {
				throw new IllegalPropertyException("Required parameters: name");
			}
			healthcenter.setName((String) properties.get("name"));
			healthcenter.setCode((String) properties.get("code"));
			healthcenter.setDistrict(district);
		}
		healthcenter.setLastmodified(new Date());
		
		return healthcenter;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/healthcenter";
	}
	
}

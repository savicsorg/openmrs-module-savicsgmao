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
import org.openmrs.module.savicsgmao.api.entity.Service;
import org.openmrs.module.savicsgmao.api.entity.Site;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/site", supportedClass = Site.class, supportedOpenmrsVersions = { "2.*.*" })
public class SiteRequestResource extends DelegatingCrudResource<Site> {
	
	@Override
	public Site newDelegate() {
		return new Site();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("service");
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
			description.addProperty("service");
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
			description.addProperty("service");
			description.addProperty("lastmodified");
			description.addProperty("creation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Site> siteList = Context.getService(GmaoService.class).getAll(Site.class, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Service.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Site>(context, siteList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("name");
		List<Site> siteList = Context.getService(GmaoService.class).doSearch(Site.class, "name", value, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Site.class, "name", value);
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Site>(context, siteList, hasMore, count);
	}
	
	@Override
	public Site getByUniqueId(String uuid) {
		
		return (Site) Context.getService(GmaoService.class).getEntityByUuid(Site.class, uuid);
	}
	
	@Override
	public Site save(Site site) {
		return (Site) Context.getService(GmaoService.class).upsert(site);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("name") == null) {
			throw new ConversionException("Required properties: name");
		}
		
		Site site = this.constructSite(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(site);
		return ConversionUtil.convertToRepresentation(site, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Site site = this.constructSite(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(site);
		return ConversionUtil.convertToRepresentation(site, context.getRepresentation());
	}
	
	@Override
	protected void delete(Site site, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(site);
	}
	
	@Override
	public void purge(Site site, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(site);
	}
	
	private Site constructSite(String uuid, SimpleObject properties) {
		Site site;
		
		Service service = null;
		if (properties.get("service") != null) {
			Integer serviceId = properties.get("service");
			service = (Service) Context.getService(GmaoService.class).getEntityByid(Service.class, "id", serviceId);
		}
		
		if (uuid != null) {
			site = (Site) Context.getService(GmaoService.class).getEntityByUuid(Site.class, uuid);
			if (site == null) {
				throw new IllegalPropertyException("site not exist");
			}
			
			if (properties.get("name") != null) {
				site.setName((String) properties.get("name"));
			}
			
			if (properties.get("code") != null) {
				site.setCode((String) properties.get("code"));
			}
			
			if (properties.get("serviceId") != null) {
				site.setService(service);
			}
		} else {
			site = new Site();
			if (properties.get("name") == null) {
				throw new IllegalPropertyException("Required parameters: name");
			}
			site.setName((String) properties.get("name"));
			site.setCode((String) properties.get("code"));
			site.setService(service);
		}
		site.setLastmodified(new Date());
		
		return site;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/site";
	}
	
}

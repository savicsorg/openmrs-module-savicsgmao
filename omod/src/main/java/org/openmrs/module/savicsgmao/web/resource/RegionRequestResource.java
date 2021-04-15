package org.openmrs.module.savicsgmao.web.resource;

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
import org.openmrs.module.savicsgmao.api.entity.Region;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/region", supportedClass = Region.class, supportedOpenmrsVersions = { "2.*.*" })
public class RegionRequestResource extends DelegatingCrudResource<Region> {
	
	@Override
	public Region newDelegate() {
		return new Region();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("regionName");
			description.addProperty("regionCode");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("regionName");
			description.addProperty("regionCode");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Region> regionList = Context.getService(GmaoService.class).getAll(Region.class, context.getLimit(),
		    context.getStartIndex());
		return new AlreadyPaged<Region>(context, regionList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("regionName");
		List<Region> regionList = Context.getService(GmaoService.class).doSearch(Region.class, "regionName", value,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Region>(context, regionList, false);
	}
	
	@Override
	public Region getByUniqueId(String uuid) {
		
		return (Region) Context.getService(GmaoService.class).getEntityByUuid(Region.class, uuid);
	}
	
	@Override
	public Region save(Region region) {
		return (Region) Context.getService(GmaoService.class).upsert(region);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("regionName") == null || propertiesToCreate.get("regionCode") == null) {
			throw new ConversionException("Required properties: regionName, regionCode");
		}
		
		Region region = this.constructRegion(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(region);
		return ConversionUtil.convertToRepresentation(region, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Region region = this.constructRegion(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(region);
		return ConversionUtil.convertToRepresentation(region, context.getRepresentation());
	}
	
	@Override
	protected void delete(Region region, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(region);
	}
	
	@Override
	public void purge(Region region, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(region);
	}
	
	private Region constructRegion(String uuid, SimpleObject properties) {
		Region region;
		if (uuid != null) {
			region = (Region) Context.getService(GmaoService.class).getEntityByUuid(Region.class, uuid);
			if (region == null) {
				throw new IllegalPropertyException("region not exist");
			}
			
			if (properties.get("regionName") != null) {
				region.setRegionName((String) properties.get("regionName"));
			}
			
			if (properties.get("regionCode") != null) {
				region.setRegionCode((String) properties.get("regionCode"));
			}
		} else {
			region = new Region();
			if (properties.get("regionName") == null || properties.get("regionCode") == null) {
				throw new IllegalPropertyException("Required parameters: regionName, regionCode");
			}
			region.setRegionName((String) properties.get("regionName"));
			region.setRegionCode((String) properties.get("regionCode"));
		}
		
		return region;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/region";
	}
	
}

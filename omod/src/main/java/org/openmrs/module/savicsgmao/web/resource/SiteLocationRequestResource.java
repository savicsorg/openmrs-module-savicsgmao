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
import org.openmrs.module.savicsgmao.api.entity.District;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.SiteLocation;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/siteLocation", supportedClass = SiteLocation.class, supportedOpenmrsVersions = { "2.*.*" })
public class SiteLocationRequestResource extends DelegatingCrudResource<SiteLocation> {
	
	@Override
	public SiteLocation newDelegate() {
		return new SiteLocation();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("locationName");
			description.addProperty("locationCode");
			description.addProperty("district");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("locationName");
			description.addProperty("locationCode");
			description.addProperty("district");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("locationName");
			description.addProperty("locationCode");
			description.addProperty("district");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<SiteLocation> siteLocationList = Context.getService(GmaoService.class).getAll(SiteLocation.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<SiteLocation>(context, siteLocationList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("locationName");
		List<SiteLocation> siteLocationList = Context.getService(GmaoService.class).doSearch(SiteLocation.class,
		    "locationName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<SiteLocation>(context, siteLocationList, false);
	}
	
	@Override
	public SiteLocation getByUniqueId(String uuid) {
		
		return (SiteLocation) Context.getService(GmaoService.class).getEntityByUuid(SiteLocation.class, uuid);
	}
	
	@Override
	public SiteLocation save(SiteLocation siteLocation) {
		return (SiteLocation) Context.getService(GmaoService.class).upsert(siteLocation);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("locationName") == null || propertiesToCreate.get("locationCode") == null) {
			throw new ConversionException("Required properties: locationName, locationCode");
		}
		
		SiteLocation siteLocation = this.constructSiteLocation(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(siteLocation);
		return ConversionUtil.convertToRepresentation(siteLocation, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		SiteLocation siteLocation = this.constructSiteLocation(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(siteLocation);
		return ConversionUtil.convertToRepresentation(siteLocation, context.getRepresentation());
	}
	
	@Override
	protected void delete(SiteLocation siteLocation, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(siteLocation);
	}
	
	@Override
	public void purge(SiteLocation siteLocation, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(siteLocation);
	}
	
	private SiteLocation constructSiteLocation(String uuid, SimpleObject properties) {
		SiteLocation siteLocation;
		
		District district = null;
		if (properties.get("district") != null) {
			Integer districtId = properties.get("district");
			district = (District) Context.getService(GmaoService.class).getEntityByid(District.class, "districtId",
			    districtId);
		}
		
		if (uuid != null) {
			siteLocation = (SiteLocation) Context.getService(GmaoService.class).getEntityByUuid(SiteLocation.class, uuid);
			if (siteLocation == null) {
				throw new IllegalPropertyException("siteLocation not exist");
			}
			
			if (properties.get("locationName") != null) {
				siteLocation.setLocationName((String) properties.get("locationName"));
			}
			
			if (properties.get("locationCode") != null) {
				siteLocation.setLocationCode((String) properties.get("locationCode"));
			}
			
			if (properties.get("districtId") != null) {
				siteLocation.setDistrict(district);
			}
		} else {
			siteLocation = new SiteLocation();
			if (properties.get("locationName") == null || properties.get("locationCode") == null) {
				throw new IllegalPropertyException("Required parameters: locationName, locationCode");
			}
			siteLocation.setLocationName((String) properties.get("locationName"));
			siteLocation.setLocationCode((String) properties.get("locationCode"));
			siteLocation.setDistrict(district);
		}
		
		return siteLocation;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/siteLocation";
	}
	
}

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
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.IllegalPropertyException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.List;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.District;
import org.openmrs.module.savicsgmao.api.entity.Region;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/district", supportedClass = District.class, supportedOpenmrsVersions = { "2.*.*" })
public class DistrictRequestResource extends DataDelegatingCrudResource<District> {
	
	@Override
	public District newDelegate() {
		return new District();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			System.out.println("");
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("districtName");
			description.addProperty("districtCode");
			description.addProperty("region");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("districtName");
			description.addProperty("districtCode");
			description.addProperty("region");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("districtName");
			description.addProperty("districtCode");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		System.out.println("---- doGetAll ");
		List<District> districtList = Context.getService(GmaoService.class).getAll(District.class, context.getLimit(),
		    context.getStartIndex());
		System.out.println(districtList.toString());
		return new AlreadyPaged<District>(context, districtList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("districtName");
		List<District> districtList = Context.getService(GmaoService.class).doSearch(District.class, "districtName", value,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<District>(context, districtList, false);
	}
	
	@Override
	public District getByUniqueId(String uuid) {
		
		return (District) Context.getService(GmaoService.class).getEntityByUuid(District.class, uuid);
	}
	
	@Override
	public District save(District district) {
		return (District) Context.getService(GmaoService.class).upsert(district);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("districtName") == null || propertiesToCreate.get("districtCode") == null) {
			throw new ConversionException("Required properties: districtName, districtCode");
		}
		System.out.println("-----------------------------");
		System.out.println(propertiesToCreate);
		System.out.println();
		District district = this.constructDistrict(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(district);
		return ConversionUtil.convertToRepresentation(district, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		District district = this.constructDistrict(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(district);
		return ConversionUtil.convertToRepresentation(district, context.getRepresentation());
	}
	
	@Override
	protected void delete(District district, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(district);
	}
	
	@Override
	public void purge(District district, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(district);
	}
	
	private District constructDistrict(String uuid, SimpleObject properties) {
		District district;
		Region region = null;
		System.out.println(">>>>Region ID " + properties.get("region"));
		if (properties.get("region") != null) {
			Integer regionId = properties.get("region");
			region = (Region) Context.getService(GmaoService.class).getEntityByid(Region.class, "regionId", regionId);
		}
		if (uuid != null) {
			district = (District) Context.getService(GmaoService.class).getEntityByUuid(District.class, uuid);
			if (district == null) {
				throw new IllegalPropertyException("district not exist");
			}
			
			if (properties.get("districtName") != null) {
				district.setDistrictName((String) properties.get("districtName"));
			}
			
			if (properties.get("districtCode") != null) {
				district.setDistrictCode((String) properties.get("districtCode"));
			}
			
			district.setRegion(region);
		} else {
			district = new District();
			if (properties.get("districtName") == null || properties.get("districtCode") == null) {
				throw new IllegalPropertyException("Required parameters: districtName, districtCode");
			}
			district.setDistrictName((String) properties.get("districtName"));
			district.setDistrictCode((String) properties.get("districtCode"));
			district.setRegion(region);
			System.out.println("region = " + region);
		}
		
		return district;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/district";
	}
	
}

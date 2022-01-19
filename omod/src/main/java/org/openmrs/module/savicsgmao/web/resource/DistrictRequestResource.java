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
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("regionid");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("code");
			description.addProperty("regionid");
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
			description.addProperty("regionid");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<District> districtList = Context.getService(GmaoService.class).getAll(District.class, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(District.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<District>(context, districtList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("name");
		List<District> districtList = Context.getService(GmaoService.class).doSearch(District.class, "name", value,
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
		if (propertiesToCreate.get("name") == null || propertiesToCreate.get("code") == null) {
			throw new ConversionException("Required properties: name, code");
		}
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
		
		if (uuid != null) {
			district = (District) Context.getService(GmaoService.class).getEntityByUuid(District.class, uuid);
			if (district == null) {
				throw new IllegalPropertyException("district not exist");
			}
			
			if (properties.get("name") != null) {
				district.setName((String) properties.get("name"));
			}
			
			if (properties.get("code") != null) {
				district.setCode((String) properties.get("code"));
			}
			
			if (properties.get("regionid") != null) {
				district.setRegionid(new Integer(properties.get("regionid").toString()));
			}
			
		} else {
			district = new District();
			if (properties.get("name") == null || properties.get("code") == null) {
				throw new IllegalPropertyException("Required parameters: name, code");
			}
			district.setName((String) properties.get("name"));
			district.setCode((String) properties.get("code"));
			district.setRegionid(new Integer(properties.get("regionid").toString()));
		}
		
		return district;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/district";
	}
	
}

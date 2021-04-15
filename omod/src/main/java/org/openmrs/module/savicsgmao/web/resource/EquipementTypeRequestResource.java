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
import org.openmrs.module.savicsgmao.api.entity.EquipementType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipementType", supportedClass = EquipementType.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipementTypeRequestResource extends DelegatingCrudResource<EquipementType> {
	
	@Override
	public EquipementType newDelegate() {
		return new EquipementType();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<EquipementType> equipementTypeList = Context.getService(GmaoService.class).getAll(EquipementType.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipementType>(context, equipementTypeList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("typeName");
		List<EquipementType> equipementTypeList = Context.getService(GmaoService.class).doSearch(EquipementType.class,
		    "typeName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipementType>(context, equipementTypeList, false);
	}
	
	@Override
	public EquipementType getByUniqueId(String uuid) {
		
		return (EquipementType) Context.getService(GmaoService.class).getEntityByUuid(EquipementType.class, uuid);
	}
	
	@Override
	public EquipementType save(EquipementType equipementType) {
		return (EquipementType) Context.getService(GmaoService.class).upsert(equipementType);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("typeName") == null || propertiesToCreate.get("typeName") == null) {
			throw new ConversionException("Required properties: typeName");
		}
		
		EquipementType equipementType = this.constructEquipementType(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(equipementType);
		return ConversionUtil.convertToRepresentation(equipementType, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		EquipementType equipementType = this.constructEquipementType(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(equipementType);
		return ConversionUtil.convertToRepresentation(equipementType, context.getRepresentation());
	}
	
	@Override
	protected void delete(EquipementType equipementType, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipementType);
	}
	
	@Override
	public void purge(EquipementType equipementType, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipementType);
	}
	
	private EquipementType constructEquipementType(String uuid, SimpleObject properties) {
		EquipementType equipementType;
		if (uuid != null) {
			equipementType = (EquipementType) Context.getService(GmaoService.class).getEntityByUuid(EquipementType.class,
			    uuid);
			if (equipementType == null) {
				throw new IllegalPropertyException("equipementType not exist");
			}
			
			if (properties.get("typeName") != null) {
				equipementType.setTypeName((String) properties.get("typeName"));
			}
		} else {
			equipementType = new EquipementType();
			if (properties.get("typeName") == null || properties.get("typeName") == null) {
				throw new IllegalPropertyException("Required parameters: typeName");
			}
			equipementType.setTypeName((String) properties.get("typeName"));
		}
		
		return equipementType;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipementType";
	}
	
}

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
import org.openmrs.module.savicsgmao.api.entity.EquipmentType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipmentType", supportedClass = EquipmentType.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipmentTypeRequestResource extends DelegatingCrudResource<EquipmentType> {
	
	@Override
	public EquipmentType newDelegate() {
		return new EquipmentType();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<EquipmentType> equipmentTypeList = Context.getService(GmaoService.class).getAll(EquipmentType.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipmentType>(context, equipmentTypeList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("typeName");
		List<EquipmentType> equipmentTypeList = Context.getService(GmaoService.class).doSearch(EquipmentType.class,
		    "typeName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipmentType>(context, equipmentTypeList, false);
	}
	
	@Override
	public EquipmentType getByUniqueId(String uuid) {
		
		return (EquipmentType) Context.getService(GmaoService.class).getEntityByUuid(EquipmentType.class, uuid);
	}
	
	@Override
	public EquipmentType save(EquipmentType equipmentType) {
		return (EquipmentType) Context.getService(GmaoService.class).upsert(equipmentType);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("typeName") == null || propertiesToCreate.get("typeName") == null) {
			throw new ConversionException("Required properties: typeName");
		}
		
		EquipmentType equipmentType = this.constructEquipmentType(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(equipmentType);
		return ConversionUtil.convertToRepresentation(equipmentType, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		EquipmentType equipmentType = this.constructEquipmentType(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(equipmentType);
		return ConversionUtil.convertToRepresentation(equipmentType, context.getRepresentation());
	}
	
	@Override
	protected void delete(EquipmentType equipmentType, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentType);
	}
	
	@Override
	public void purge(EquipmentType equipmentType, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentType);
	}
	
	private EquipmentType constructEquipmentType(String uuid, SimpleObject properties) {
		EquipmentType equipmentType;
		if (uuid != null) {
			equipmentType = (EquipmentType) Context.getService(GmaoService.class).getEntityByUuid(EquipmentType.class, uuid);
			if (equipmentType == null) {
				throw new IllegalPropertyException("equipmentType not exist");
			}
			
			if (properties.get("typeName") != null) {
				equipmentType.setTypeName((String) properties.get("typeName"));
			}
		} else {
			equipmentType = new EquipmentType();
			if (properties.get("typeName") == null || properties.get("typeName") == null) {
				throw new IllegalPropertyException("Required parameters: typeName");
			}
			equipmentType.setTypeName((String) properties.get("typeName"));
		}
		
		return equipmentType;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipmentType";
	}
	
}

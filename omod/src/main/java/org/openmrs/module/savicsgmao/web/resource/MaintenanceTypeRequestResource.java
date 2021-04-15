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
import org.openmrs.module.savicsgmao.api.entity.MaintenanceType;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/maintenanceType", supportedClass = MaintenanceType.class, supportedOpenmrsVersions = { "2.*.*" })
public class MaintenanceTypeRequestResource extends DelegatingCrudResource<MaintenanceType> {
	
	@Override
	public MaintenanceType newDelegate() {
		return new MaintenanceType();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("typeCode");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("typeCode");
			description.addProperty("typeName");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<MaintenanceType> maintenanceTypeList = Context.getService(GmaoService.class).getAll(MaintenanceType.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceType>(context, maintenanceTypeList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("typeName");
		List<MaintenanceType> maintenanceTypeList = Context.getService(GmaoService.class).doSearch(MaintenanceType.class,
		    "typeName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<MaintenanceType>(context, maintenanceTypeList, false);
	}
	
	@Override
	public MaintenanceType getByUniqueId(String uuid) {
		
		return (MaintenanceType) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceType.class, uuid);
	}
	
	@Override
	public MaintenanceType save(MaintenanceType maintenanceType) {
		return (MaintenanceType) Context.getService(GmaoService.class).upsert(maintenanceType);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("typeName") == null || propertiesToCreate.get("typeCode") == null) {
			throw new ConversionException("Required properties: typeName, typeCode");
		}
		
		MaintenanceType maintenanceType = this.constructDepartment(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(maintenanceType);
		return ConversionUtil.convertToRepresentation(maintenanceType, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		MaintenanceType maintenanceType = this.constructDepartment(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(maintenanceType);
		return ConversionUtil.convertToRepresentation(maintenanceType, context.getRepresentation());
	}
	
	@Override
	protected void delete(MaintenanceType maintenanceType, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceType);
	}
	
	@Override
	public void purge(MaintenanceType maintenanceType, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(maintenanceType);
	}
	
	private MaintenanceType constructDepartment(String uuid, SimpleObject properties) {
		MaintenanceType maintenanceType;
		if (uuid != null) {
			maintenanceType = (MaintenanceType) Context.getService(GmaoService.class).getEntityByUuid(MaintenanceType.class,
			    uuid);
			if (maintenanceType == null) {
				throw new IllegalPropertyException("maintenanceType not exist");
			}
			
			if (properties.get("typeName") != null) {
				maintenanceType.setTypeName((String) properties.get("typeName"));
			}
			
			if (properties.get("typeCode") != null) {
				maintenanceType.setTypeCode((String) properties.get("typeCode"));
			}
		} else {
			maintenanceType = new MaintenanceType();
			if (properties.get("typeName") == null || properties.get("typeCode") == null) {
				throw new IllegalPropertyException("Required parameters: typeName, typeCode");
			}
			maintenanceType.setTypeName((String) properties.get("typeName"));
			maintenanceType.setTypeCode((String) properties.get("typeCode"));
		}
		
		return maintenanceType;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/maintenanceType";
	}
	
}

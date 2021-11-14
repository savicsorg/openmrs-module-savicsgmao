package org.openmrs.module.savicsgmao.web.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import java.util.Map;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.EquipmentOperation;
import org.openmrs.module.savicsgmao.api.entity.EquipmentOperationItem;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipmentOperation", supportedClass = EquipmentOperation.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipmentOperationRequestResource extends DelegatingCrudResource<EquipmentOperation> {
	
	@Override
	public EquipmentOperation newDelegate() {
		return new EquipmentOperation();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("operationCode");
			description.addProperty("operationType");
			description.addProperty("operationDate");
			description.addProperty("sourceSiteId");
			description.addProperty("destinationSiteId");
			description.addProperty("reason");
			description.addProperty("localApproval");
			description.addProperty("localApprovalDate");
			description.addProperty("centralApproval");
			description.addProperty("centralApprovalDate");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("operationCode");
			description.addProperty("operationType");
			description.addProperty("operationDate");
			description.addProperty("sourceSiteId");
			description.addProperty("destinationSiteId");
			description.addProperty("reason");
			description.addProperty("localApproval");
			description.addProperty("localApprovalDate");
			description.addProperty("centralApproval");
			description.addProperty("centralApprovalDate");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<EquipmentOperation> equipmentOperationList = Context.getService(GmaoService.class).getAll(
		    EquipmentOperation.class, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipmentOperation>(context, equipmentOperationList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("operationCode");
		List<EquipmentOperation> equipmentOperationList = Context.getService(GmaoService.class).doSearch(
		    EquipmentOperation.class, "operationCode", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipmentOperation>(context, equipmentOperationList, false);
	}
	
	@Override
	public EquipmentOperation getByUniqueId(String uuid) {
		
		return (EquipmentOperation) Context.getService(GmaoService.class).getEntityByUuid(EquipmentOperation.class, uuid);
	}
	
	@Override
	public EquipmentOperation save(EquipmentOperation equipmentOperation) {
		List<EquipmentOperationItem> items = new ArrayList<EquipmentOperationItem>(
		        equipmentOperation.getEquipmentOperationItems());
		for (EquipmentOperationItem item : items) {
			Equipment toUpdate = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class,
			    "EquipmentId", item.getEquipment().getEquipmentId());
			if (equipmentOperation.getOperationType() == 1) { //output
				toUpdate.setInService(false);
				toUpdate.setEquipmentStatus(2);//disposed
				Context.getService(GmaoService.class).upsert(toUpdate);
			} else { //transfert
				toUpdate.setInService(false);
				toUpdate.setEquipmentStatus(1);//transfered
				Context.getService(GmaoService.class).upsert(toUpdate);
			}
		}
		return (EquipmentOperation) Context.getService(GmaoService.class).upsert(equipmentOperation);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("operationCode") == null || propertiesToCreate.get("operationType") == null) {
			throw new ConversionException("Required properties: operationCode, operationType");
		}
		
		EquipmentOperation equipmentOperation = this.constructEquipmentOperation(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(equipmentOperation);
		return ConversionUtil.convertToRepresentation(equipmentOperation, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		EquipmentOperation equipmentOperation = this.constructEquipmentOperation(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(equipmentOperation);
		return ConversionUtil.convertToRepresentation(equipmentOperation, context.getRepresentation());
	}
	
	@Override
	protected void delete(EquipmentOperation equipmentOperation, String reason, RequestContext context)
	        throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentOperation);
	}
	
	@Override
	public void purge(EquipmentOperation equipmentOperation, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentOperation);
	}
	
	private EquipmentOperation constructEquipmentOperation(String uuid, SimpleObject properties) {
		EquipmentOperation equipmentOperation;
		if (uuid != null) {
			equipmentOperation = (EquipmentOperation) Context.getService(GmaoService.class).getEntityByUuid(
			    EquipmentOperation.class, uuid);
			if (equipmentOperation == null) {
				throw new IllegalPropertyException("Bed Type not exist");
			}
			
			if (properties.get("operationCode") != null) {
				equipmentOperation.setOperationCode((String) properties.get("operationCode"));
			}
			
			if (properties.get("operationType") != null) {
				equipmentOperation.setOperationType((Integer) properties.get("operationType"));
			}
			
			if (properties.get("operationDate") != null) {
				equipmentOperation.setOperationDate((Date) properties.get("operationDate"));
			}
			
			if (properties.get("sourceSiteId") != null) {
				equipmentOperation.setSourceSiteId((Integer) properties.get("sourceSiteId"));
			}
			
			if (properties.get("reason") != null) {
				equipmentOperation.setReason((String) properties.get("reason"));
			}
			
		} else {
			equipmentOperation = new EquipmentOperation();
			if (properties.get("operationCode") == null || properties.get("operationType") == null) {
				throw new IllegalPropertyException("Required parameters: operationCode, operationType");
			}
			equipmentOperation.setOperationCode((String) properties.get("operationCode"));
			equipmentOperation.setOperationType((Integer) properties.get("operationType"));
			equipmentOperation.setOperationDate((Date) properties.get("operationDate"));
			equipmentOperation.setSourceSiteId((Integer) properties.get("sourceSiteId"));
		}
		
		return equipmentOperation;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipmentOperation";
	}
	
}

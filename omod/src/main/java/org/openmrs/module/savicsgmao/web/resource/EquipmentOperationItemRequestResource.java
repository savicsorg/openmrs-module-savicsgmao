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
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.EquipmentOperation;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.EquipmentOperationItem;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipmentOperationItem", supportedClass = EquipmentOperationItem.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipmentOperationItemRequestResource extends DelegatingCrudResource<EquipmentOperationItem> {
	
	@Override
	public EquipmentOperationItem newDelegate() {
		return new EquipmentOperationItem();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("equipmentOperation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("equipmentOperation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("equipment");
			description.addProperty("equipmentOperation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<EquipmentOperationItem> equipmentOperationItemList = Context.getService(GmaoService.class).getAll(
		    EquipmentOperationItem.class, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<EquipmentOperationItem>(context, equipmentOperationItemList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		throw new ResourceDoesNotSupportOperationException("doSearch not allowed on " + this.getClass() + " resource");
	}
	
	@Override
	public EquipmentOperationItem getByUniqueId(String uuid) {
		
		return (EquipmentOperationItem) Context.getService(GmaoService.class).getEntityByUuid(EquipmentOperationItem.class,
		    uuid);
	}
	
	@Override
	public EquipmentOperationItem save(EquipmentOperationItem equipmentOperationItem) {
		return (EquipmentOperationItem) Context.getService(GmaoService.class).upsert(equipmentOperationItem);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("equipment") == null || propertiesToCreate.get("equipmentOperation") == null) {
			throw new ConversionException("Required properties: equipmentId, equipmentOperation");
		}
		
		EquipmentOperationItem equipmentOperationItem = this.constructEquipmentOperationItem(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(equipmentOperationItem);
		return ConversionUtil.convertToRepresentation(equipmentOperationItem, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		EquipmentOperationItem equipmentOperationItem = this.constructEquipmentOperationItem(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(equipmentOperationItem);
		return ConversionUtil.convertToRepresentation(equipmentOperationItem, context.getRepresentation());
	}
	
	@Override
	protected void delete(EquipmentOperationItem equipmentOperationItem, String reason, RequestContext context)
	        throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentOperationItem);
	}
	
	@Override
	public void purge(EquipmentOperationItem equipmentOperationItem, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(equipmentOperationItem);
	}
	
	private EquipmentOperationItem constructEquipmentOperationItem(String uuid, SimpleObject properties) {
		EquipmentOperationItem equipmentOperationItem;
		
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer equipementId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "equipementId",
			    equipementId);
		}
		EquipmentOperation equipmentOperation = null;
		if (properties.get("equipmentOperation") != null) {
			Integer equipmentOperationId = properties.get("equipmentOperation");
			equipmentOperation = (EquipmentOperation) Context.getService(GmaoService.class).getEntityByid(
			    EquipmentOperation.class, "equipmentOperation", equipmentOperationId);
		}
		
		if (uuid != null) {
			equipmentOperationItem = (EquipmentOperationItem) Context.getService(GmaoService.class).getEntityByUuid(
			    EquipmentOperationItem.class, uuid);
			if (equipmentOperationItem == null) {
				throw new IllegalPropertyException("equipmentOperationItem not exist");
			}
			
			if (properties.get("equipment") != null) {
				equipmentOperationItem.setEquipment(equipment);
			}
			
			if (properties.get("equipmentOperation") != null) {
				equipmentOperationItem.setEquipmentOperation(equipmentOperation);
			}
		} else {
			equipmentOperationItem = new EquipmentOperationItem();
			if (properties.get("equipmentOperationItemName") == null || properties.get("equipmentOperationItemCode") == null) {
				throw new IllegalPropertyException(
				        "Required parameters: equipmentOperationItemName, equipmentOperationItemCode");
			}
			equipmentOperationItem.setEquipment(equipment);
			equipmentOperationItem.setEquipmentOperation(equipmentOperation);
		}
		
		return equipmentOperationItem;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipmentOperationItem";
	}
	
}

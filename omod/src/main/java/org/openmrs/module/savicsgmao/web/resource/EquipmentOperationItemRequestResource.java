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

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipmentOperationItem", supportedClass = EquipmentOperationItem.class, supportedOpenmrsVersions = {"2.*.*"})
public class EquipmentOperationItemRequestResource extends DelegatingCrudResource<EquipmentOperationItem> {

    @Override
    public EquipmentOperationItem newDelegate() {
        return new EquipmentOperationItem();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("equipmentId");
            description.addProperty("equipmentOperationId");
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        } else {
            description.addProperty("uuid");
            description.addProperty("equipmentId");
            description.addProperty("equipmentOperationId");
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }
        return description;
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        List<EquipmentOperationItem> equipmentOperationItemList = Context.getService(GmaoService.class).getAll(
                EquipmentOperationItem.class, context.getLimit(), context.getStartIndex());
        return new AlreadyPaged<EquipmentOperationItem>(context, equipmentOperationItemList, false);
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        String value = context.getParameter("equipmentId");
        List<EquipmentOperationItem> equipmentOperationItemList = Context.getService(GmaoService.class).doSearch(
                EquipmentOperationItem.class, "equipmentId", value, context.getLimit(), context.getStartIndex());
        return new AlreadyPaged<EquipmentOperationItem>(context, equipmentOperationItemList, false);
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
        if (propertiesToCreate.get("equipmentId") == null || propertiesToCreate.get("equipmentOperationId") == null) {
            throw new ConversionException("Required properties: equipmentId, equipmentOperationId");
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
        if (properties.get("Equipment") != null) {
            String equipementId = properties.get("Equipment");
            equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "equipementId", equipementId);
        }
        EquipmentOperation EquipmentOperation = null;
        if (properties.get("EquipmentOperation") != null) {
            String EquipmentOperationId = properties.get("EquipmentOperation");
            EquipmentOperation = (EquipmentOperation) Context.getService(GmaoService.class).getEntityByid(EquipmentOperation.class, "equipmentOperationId", EquipmentOperationId);
        }

        if (uuid != null) {
            equipmentOperationItem = (EquipmentOperationItem) Context.getService(GmaoService.class).getEntityByUuid(
                    EquipmentOperationItem.class, uuid);
            if (equipmentOperationItem == null) {
                throw new IllegalPropertyException("equipmentOperationItem not exist");
            }

            if (properties.get("equipmentId") != null) {
                equipmentOperationItem.setEquipment(equipment);
            }

            if (properties.get("equipmentOperationId") != null) {
                equipmentOperationItem.setEquipmentOperation(EquipmentOperation);
            }
        } else {
            equipmentOperationItem = new EquipmentOperationItem();
            if (properties.get("equipmentOperationItemName") == null || properties.get("equipmentOperationItemCode") == null) {
                throw new IllegalPropertyException(
                        "Required parameters: equipmentOperationItemName, equipmentOperationItemCode");
            }
            equipmentOperationItem.setEquipment(equipment);
            equipmentOperationItem.setEquipmentOperation(EquipmentOperation);
        }

        return equipmentOperationItem;
    }

    @Override
    public String getUri(Object instance) {
        return RestConstants.URI_PREFIX + "/equipmentOperationItem";
    }

}

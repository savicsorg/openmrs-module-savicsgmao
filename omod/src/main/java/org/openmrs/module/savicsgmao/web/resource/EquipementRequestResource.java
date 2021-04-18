package org.openmrs.module.savicsgmao.web.resource;

import java.time.LocalDateTime;
import java.util.Date;
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
import org.openmrs.module.savicsgmao.api.entity.Equipement;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.api.entity.Equipement;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/equipement", supportedClass = Equipement.class, supportedOpenmrsVersions = { "2.*.*" })
public class EquipementRequestResource extends DelegatingCrudResource<Equipement> {
	
	@Override
	public Equipement newDelegate() {
		return new Equipement();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("uuid");
			description.addProperty("designation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Equipement> oList = Context.getService(GmaoService.class).getAll(Equipement.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Equipement>(context, oList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("typeName");
		List<Equipement> oList = Context.getService(GmaoService.class).doSearch(Equipement.class,
		    "typeName", value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Equipement>(context, oList, false);
	}
	
	@Override
	public Equipement getByUniqueId(String uuid) {
		
		return (Equipement) Context.getService(GmaoService.class).getEntityByUuid(Equipement.class, uuid);
	}
	
	@Override
	public Equipement save(Equipement equipement) {
            equipement.LastModifiedDate = new Date();
		return (Equipement) Context.getService(GmaoService.class).upsert(equipement);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("designation") == null || propertiesToCreate.get("designation") == null) {
			throw new ConversionException("Required properties: designation");
		}
		
		Equipement o = this.constructEquipement(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(o);
		return ConversionUtil.convertToRepresentation(o, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Equipement o = this.constructEquipement(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(o);
		return ConversionUtil.convertToRepresentation(o, context.getRepresentation());
	}
	
	@Override
	protected void delete(Equipement o, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(o);
	}
	
	@Override
	public void purge(Equipement o, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(o);
	}
	
	private Equipement constructEquipement(String uuid, SimpleObject properties) {
		Equipement o;
		if (uuid != null) {
			o = (Equipement) Context.getService(GmaoService.class).getEntityByUuid(Equipement.class,
			    uuid);
			if (o == null) {
				throw new IllegalPropertyException("equipment not exist");
			}
			
			if (properties.get("designation") != null) {
				o.setDesignation((String) properties.get("designation"));
			}
		} else {
			o = new Equipement();
			if (properties.get("designation") == null || properties.get("designation") == null) {
				throw new IllegalPropertyException("Required parameters: designation");
			}
			o.setDesignation((String) properties.get("designation"));
		}
		
		return o;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/equipment";
	}
	
}

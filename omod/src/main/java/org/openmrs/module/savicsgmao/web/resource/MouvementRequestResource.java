package org.openmrs.module.savicsgmao.web.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.module.savicsgmao.api.entity.Equipment;
import org.openmrs.module.savicsgmao.api.entity.Healthcenter;
import org.openmrs.module.savicsgmao.api.entity.Maintenance;
import org.openmrs.module.savicsgmao.api.entity.Mouvement;
import org.openmrs.module.savicsgmao.api.entity.Site;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/mouvement", supportedClass = Mouvement.class, supportedOpenmrsVersions = { "2.*.*" })
public class MouvementRequestResource extends DelegatingCrudResource<Mouvement> {
	
	@Override
	public Mouvement newDelegate() {
		return new Mouvement();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("equipment");
			description.addProperty("uuid");
			description.addProperty("siteByDestinationId");
			description.addProperty("siteBySourceId");
			description.addProperty("type");
			description.addProperty("date");
			description.addProperty("motif");
			description.addProperty("localapproval");
			description.addProperty("localapprover");
			description.addProperty("centralapproval");
			description.addProperty("centralapprover");
			description.addProperty("status");
			description.addProperty("creation");
			description.addProperty("lastmodified");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("equipment");
			description.addProperty("uuid");
			description.addProperty("siteByDestinationId");
			description.addProperty("siteBySourceId");
			description.addProperty("type");
			description.addProperty("date");
			description.addProperty("motif");
			description.addProperty("localapproval");
			description.addProperty("localapprover");
			description.addProperty("centralapproval");
			description.addProperty("centralapprover");
			description.addProperty("status");
			description.addProperty("creation");
			description.addProperty("lastmodified");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("equipment");
			description.addProperty("uuid");
			description.addProperty("siteByDestinationId");
			description.addProperty("siteBySourceId");
			description.addProperty("type");
			description.addProperty("date");
			description.addProperty("motif");
			description.addProperty("localapproval");
			description.addProperty("localapprover");
			description.addProperty("centralapproval");
			description.addProperty("centralapprover");
			description.addProperty("status");
			description.addProperty("creation");
			description.addProperty("lastmodified");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Mouvement> mouvementList = Context.getService(GmaoService.class).getAll(Mouvement.class, context.getLimit(),
		    context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Mouvement.class);
		boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Mouvement>(context, mouvementList, hasMore, count);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("name");
		List<Mouvement> mouvementList = Context.getService(GmaoService.class).doSearch(Mouvement.class, "name", value,
		    context.getLimit(), context.getStartIndex());
		Long count = Context.getService(GmaoService.class).doCount(Mouvement.class, "name", value);
		Boolean hasMore = count > context.getStartIndex() + context.getLimit();
		return new AlreadyPaged<Mouvement>(context, mouvementList, hasMore, count);
	}
	
	@Override
	public Mouvement getByUniqueId(String uuid) {
		
		return (Mouvement) Context.getService(GmaoService.class).getEntityByUuid(Mouvement.class, uuid);
	}
	
	@Override
	public Mouvement save(Mouvement mouvement) {
		return (Mouvement) Context.getService(GmaoService.class).upsert(mouvement);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		try {
			System.out.println("--------------> propertiesToCreate " + propertiesToCreate.toString());
			if (propertiesToCreate.get("siteBySource") == null) {
				throw new ConversionException("Required properties: siteBySource");
			}
			
			Mouvement mouvement = this.constructMouvement(null, propertiesToCreate);
			System.out.println("Mouvement Apres save " + mouvement.toString());
			Context.getService(GmaoService.class).upsert(mouvement);
			return ConversionUtil.convertToRepresentation(mouvement, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(MouvementRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		try {
			Mouvement mouvement = this.constructMouvement(uuid, propertiesToUpdate);
			Context.getService(GmaoService.class).upsert(mouvement);
			
			if ("VALID".equals(mouvement.getStatus())) {//If a validation
				Equipment equipment = (Equipment) Context.getService(GmaoService.class).getEntityByUuid(Equipment.class,
				    mouvement.getEquipment().getUuid());
				equipment.setSite(mouvement.getSiteByDestinationId());
				Context.getService(GmaoService.class).upsert(equipment);
			}
			return ConversionUtil.convertToRepresentation(mouvement, context.getRepresentation());
		}
		catch (ParseException ex) {
			Logger.getLogger(MouvementRequestResource.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	@Override
	protected void delete(Mouvement mouvement, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(mouvement);
	}
	
	@Override
	public void purge(Mouvement mouvement, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(mouvement);
	}
	
	private Mouvement constructMouvement(String uuid, SimpleObject properties) throws ParseException {
		Healthcenter healthcenter = null;
		Mouvement mouvement;
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat simpleDateFormatApprove = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
		Site siteByDestination = null;
		if (properties.get("siteByDestination") != null) {
			Integer siteByDestinationId = properties.get("siteByDestination");
			siteByDestination = (Site) Context.getService(GmaoService.class).getEntityByid(Site.class, "id",
			    siteByDestinationId);
		}
		
		Site siteBySource = null;
		if (properties.get("siteBySource") != null) {
			Integer siteBySourceId = properties.get("siteBySource");
			siteBySource = (Site) Context.getService(GmaoService.class).getEntityByid(Site.class, "id", siteBySourceId);
		}
		
		Equipment equipment = null;
		if (properties.get("equipment") != null) {
			Integer equipementId = properties.get("equipment");
			equipment = (Equipment) Context.getService(GmaoService.class).getEntityByid(Equipment.class, "id", equipementId);
		}
		
		if (uuid != null) {
			mouvement = (Mouvement) Context.getService(GmaoService.class).getEntityByUuid(Mouvement.class, uuid);
			if (mouvement == null) {
				throw new IllegalPropertyException("mouvement not exist");
			}
		} else {
			mouvement = new Mouvement();
			if (properties.get("equipment") == null || properties.get("siteByDestination") == null
			        || properties.get("siteBySource") == null) {
				throw new IllegalPropertyException("Required parameters: equipment, siteByDestination, siteBySource");
			}
			mouvement.setCreation(new Date());
		}
		
		if (properties.get("type") != null) {
			mouvement.setType(Integer.valueOf(properties.get("type").toString()));
		}
		
		if (properties.get("date") != null) {
			mouvement.setDate(simpleDateFormat.parse(properties.get("date").toString()));
		}
		
		if (properties.get("localapproval") != null) {
			mouvement.setLocalapproval(simpleDateFormatApprove.parse(properties.get("localapproval").toString()));
		}
		
		if (properties.get("localapprover") != null) {
			mouvement.setLocalapprover(properties.get("localapprover").toString());
		}
		
		if (properties.get("centralapproval") != null) {
			mouvement.setLocalapproval(simpleDateFormatApprove.parse(properties.get("centralapproval").toString()));
		}
		
		if (properties.get("centralapprover") != null) {
			mouvement.setLocalapprover(properties.get("centralapprover").toString());
		}
		
		if (properties.get("status") != null) {
			mouvement.setStatus(properties.get("status").toString());
		}
		
		if (properties.get("motif") != null) {
			mouvement.setMotif(properties.get("motif").toString());
		}
		
		mouvement.setSiteBySourceId(siteBySource);
		mouvement.setSiteByDestinationId(siteByDestination);
		mouvement.setLastmodified(new Date());
		mouvement.setEquipment(equipment);
		
		return mouvement;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/mouvement";
	}
	
}

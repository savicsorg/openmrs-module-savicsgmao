package org.openmrs.module.savicsgmao.web.resource;

import java.util.ArrayList;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.List;
import org.openmrs.module.addresshierarchy.AddressHierarchyEntry;
import org.openmrs.module.savicsgmao.api.service.GmaoService;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/addressHierarchy", supportedClass = AddressHierarchyEntry.class, supportedOpenmrsVersions = { "2.*.*" })
public class AddressHierarchyRequestResource extends DelegatingCrudResource<AddressHierarchyEntry> {
	
	@Override
	public AddressHierarchyEntry newDelegate() {
		return new AddressHierarchyEntry();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("addressHierarchyEntryId");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("level");
			description.addProperty("parent");
			description.addProperty("userGeneratedId");
			description.addProperty("latitude");
			description.addProperty("longitude");
			description.addProperty("elevation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("addressHierarchyEntryId");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("level");
			description.addProperty("parent");
			description.addProperty("userGeneratedId");
			description.addProperty("latitude");
			description.addProperty("longitude");
			description.addProperty("elevation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("addressHierarchyEntryId");
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("level");
			description.addProperty("parent");
			description.addProperty("userGeneratedId");
			description.addProperty("latitude");
			description.addProperty("longitude");
			description.addProperty("elevation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<AddressHierarchyEntry> agentList = Context.getService(GmaoService.class).getAll(AddressHierarchyEntry.class,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<AddressHierarchyEntry>(context, agentList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String level = context.getParameter("level");
		List<AddressHierarchyEntry> agentList;
		if (level != null) {
			
			agentList = Context.getService(GmaoService.class).doSearch(AddressHierarchyEntry.class, "level.levelId", level,
			    context.getLimit(), context.getStartIndex());
		} else {
			String parent = context.getParameter("parent");
			agentList = Context.getService(GmaoService.class).doSearch(AddressHierarchyEntry.class,
			    "parent.addressHierarchyEntryId", parent, context.getLimit(), context.getStartIndex());
		}
		
		return new AlreadyPaged<AddressHierarchyEntry>(context, agentList, false);
	}
	
	@Override
	public AddressHierarchyEntry getByUniqueId(String uuid) {
		
		return (AddressHierarchyEntry) Context.getService(GmaoService.class).getEntityByUuid(AddressHierarchyEntry.class,
		    uuid);
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/addressHierarchy";
	}
	
	@Override
	protected void delete(AddressHierarchyEntry t, String string, RequestContext rc) throws ResponseException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public void purge(AddressHierarchyEntry t, RequestContext rc) throws ResponseException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public AddressHierarchyEntry save(AddressHierarchyEntry t) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}

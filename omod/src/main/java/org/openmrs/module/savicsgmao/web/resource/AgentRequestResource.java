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
import org.openmrs.module.savicsgmao.api.entity.Agent;
import org.openmrs.module.savicsgmao.api.entity.Department;
import org.openmrs.module.savicsgmao.api.entity.SiteLocation;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/agent", supportedClass = Agent.class, supportedOpenmrsVersions = { "2.*.*" })
public class AgentRequestResource extends DelegatingCrudResource<Agent> {
	
	@Override
	public Agent newDelegate() {
		return new Agent();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			System.out.println("");
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("fullName");
			description.addProperty("agentAddress");
			description.addProperty("phoneNumber");
			description.addProperty("rollNumber");
			description.addProperty("isExternal");
			description.addProperty("department");
			description.addProperty("siteLocation");
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("fullName");
			description.addProperty("agentAddress");
			description.addProperty("phoneNumber");
			description.addProperty("rollNumber");
			description.addProperty("isExternal");
			description.addProperty("department");
			description.addProperty("siteLocation");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("fullName");
			description.addProperty("agentAddress");
			description.addProperty("phoneNumber");
			description.addProperty("rollNumber");
			description.addProperty("isExternal");
			description.addProperty("department");
			description.addProperty("siteLocation");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Agent> agentList = Context.getService(GmaoService.class).getAll(Agent.class, context.getLimit(),
		    context.getStartIndex());
		return new AlreadyPaged<Agent>(context, agentList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("fullName");
		List<Agent> agentList = Context.getService(GmaoService.class).doSearch(Agent.class, "fullName", value,
		    context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Agent>(context, agentList, false);
	}
	
	@Override
	public Agent getByUniqueId(String uuid) {
		
		return (Agent) Context.getService(GmaoService.class).getEntityByUuid(Agent.class, uuid);
	}
	
	@Override
	public Agent save(Agent agent) {
		return (Agent) Context.getService(GmaoService.class).upsert(agent);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("fullName") == null || propertiesToCreate.get("phoneNumber") == null) {
			throw new ConversionException("Required properties: fullName, phoneNumber");
		}
		
		Agent agent = this.constructAgent(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(agent);
		return ConversionUtil.convertToRepresentation(agent, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Agent agent = this.constructAgent(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(agent);
		return ConversionUtil.convertToRepresentation(agent, context.getRepresentation());
	}
	
	@Override
	protected void delete(Agent agent, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(agent);
	}
	
	@Override
	public void purge(Agent agent, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(agent);
	}
	
	private Agent constructAgent(String uuid, SimpleObject properties) {
		Agent agent;
		Department department = null;
		if (properties.get("Department") != null) {
			Integer departementID = properties.get("Department");
			department = (Department) Context.getService(GmaoService.class).getEntityByid(Department.class, "departmentId",
			    departementID);
		}
		SiteLocation siteLocation = null;
		if (properties.get("SiteLocation") != null) {
			Integer siteLocationId = properties.get("SiteLocation");
			siteLocation = (SiteLocation) Context.getService(GmaoService.class).getEntityByid(SiteLocation.class,
			    "siteLocationId", siteLocationId);
		}
		
		if (uuid != null) {
			agent = (Agent) Context.getService(GmaoService.class).getEntityByUuid(Agent.class, uuid);
			if (agent == null) {
				throw new IllegalPropertyException("Bed Type not exist");
			}
			
			if (properties.get("fullName") != null) {
				agent.setFullName((String) properties.get("fullName"));
			}
			
			if (properties.get("phoneNumber") != null) {
				agent.setPhoneNumber((String) properties.get("phoneNumber"));
			}
			
			if (properties.get("agentAddress") != null) {
				agent.setAgentAddress((String) properties.get("agentAddress"));
			}
			
			if (properties.get("rollNumber") != null) {
				agent.setRollNumber((String) properties.get("rollNumber"));
			}
			
			agent.setDepartment(department);
			agent.setSiteLocation(siteLocation);
			
		} else {
			agent = new Agent();
			if (properties.get("fullName") == null || properties.get("phoneNumber") == null) {
				throw new IllegalPropertyException("Required parameters: fullName, phoneNumber");
			}
			agent.setFullName((String) properties.get("fullName"));
			agent.setPhoneNumber((String) properties.get("phoneNumber"));
			agent.setAgentAddress((String) properties.get("agentAddress"));
			agent.setRollNumber((String) properties.get("rollNumber"));
			agent.setDepartment(department);
			agent.setSiteLocation(siteLocation);
		}
		
		return agent;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/agent";
	}
	
}

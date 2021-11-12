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
import org.openmrs.module.savicsgmao.api.entity.Department;
import org.openmrs.module.savicsgmao.rest.v1_0.resource.GmaoRest;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;

@Resource(name = RestConstants.VERSION_1 + GmaoRest.GMAO_NAMESPACE + "/department", supportedClass = Department.class, supportedOpenmrsVersions = { "2.*.*" })
public class DepartmentRequestResource extends DelegatingCrudResource<Department> {
	
	@Override
	public Department newDelegate() {
		return new Department();
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("departmentName");
			description.addProperty("departmentCode");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		} else {
			description.addProperty("id");
			description.addProperty("uuid");
			description.addProperty("departmentName");
			description.addProperty("departmentCode");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}
		return description;
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		List<Department> departmentList = Context.getService(GmaoService.class).getAll(Department.class, context.getLimit(),
		    context.getStartIndex());
		return new AlreadyPaged<Department>(context, departmentList, false);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		String value = context.getParameter("departmentName");
		List<Department> departmentList = Context.getService(GmaoService.class).doSearch(Department.class, "departmentName",
		    value, context.getLimit(), context.getStartIndex());
		return new AlreadyPaged<Department>(context, departmentList, false);
	}
	
	@Override
	public Department getByUniqueId(String uuid) {
		
		return (Department) Context.getService(GmaoService.class).getEntityByUuid(Department.class, uuid);
	}
	
	@Override
	public Department save(Department department) {
		return (Department) Context.getService(GmaoService.class).upsert(department);
	}
	
	@Override
	public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
		if (propertiesToCreate.get("departmentName") == null || propertiesToCreate.get("departmentCode") == null) {
			throw new ConversionException("Required properties: departmentName, departmentCode");
		}
		
		Department department = this.constructDepartment(null, propertiesToCreate);
		Context.getService(GmaoService.class).upsert(department);
		return ConversionUtil.convertToRepresentation(department, context.getRepresentation());
	}
	
	@Override
	public Object update(String uuid, SimpleObject propertiesToUpdate, RequestContext context) throws ResponseException {
		Department department = this.constructDepartment(uuid, propertiesToUpdate);
		Context.getService(GmaoService.class).upsert(department);
		return ConversionUtil.convertToRepresentation(department, context.getRepresentation());
	}
	
	@Override
	protected void delete(Department department, String reason, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(department);
	}
	
	@Override
	public void purge(Department department, RequestContext context) throws ResponseException {
		Context.getService(GmaoService.class).delete(department);
	}
	
	private Department constructDepartment(String uuid, SimpleObject properties) {
		Department department;
		if (uuid != null) {
			department = (Department) Context.getService(GmaoService.class).getEntityByUuid(Department.class, uuid);
			if (department == null) {
				throw new IllegalPropertyException("department not exist");
			}
			
			if (properties.get("departmentName") != null) {
				department.setDepartmentName((String) properties.get("departmentName"));
			}
			
			if (properties.get("departmentCode") != null) {
				department.setDepartmentCode((String) properties.get("departmentCode"));
			}
		} else {
			department = new Department();
			if (properties.get("departmentName") == null || properties.get("departmentCode") == null) {
				throw new IllegalPropertyException("Required parameters: departmentName, departmentCode");
			}
			department.setDepartmentName((String) properties.get("departmentName"));
			department.setDepartmentCode((String) properties.get("departmentCode"));
		}
		
		return department;
	}
	
	@Override
	public String getUri(Object instance) {
		return RestConstants.URI_PREFIX + "/department";
	}
	
}

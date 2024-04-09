package gov.iti.hr.restcontrollers.resources.departments;

import gov.iti.hr.filters.DepartmentFilter;
import gov.iti.hr.models.DepartmentDTO;

import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.restcontrollers.resources.employees.EmployeeResourceService;
import gov.iti.hr.restcontrollers.resources.interfaces.DepartmentResource;
import gov.iti.hr.restcontrollers.utils.LinksUtil;
import gov.iti.hr.services.DepartmentService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/departments")
@Consumes({"application/json; qs=1", "application/xml; qs=0.75"})
@Produces({"application/json; qs=1", "application/xml; qs=0.75"})
public class DepartmentResourceService implements DepartmentResource {

    private final DepartmentService departmentService;
    @Context
    private UriInfo uriInfo;

    public DepartmentResourceService() {
        departmentService = new DepartmentService();
    }

    @Override
    public Response getDepartment(Integer id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        DepartmentResponse departmentResponse = new DepartmentResponse(departmentDTO);
        departmentResponse.setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, id.toString()));
        departmentResponse.getManager().setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, departmentResponse.getDepartmentId().toString()));
        return Response.ok(departmentResponse).build();
    }

    @Override
    public Response getDepartments(DepartmentFilter departmentFilter) {
        Integer count = departmentService.getDepartmentsCount();
        List<DepartmentDTO> departments = departmentService.findAllDepartments(departmentFilter);
        List<DepartmentResponse> departmentResponses = departments.stream().map(DepartmentResponse::new).toList();
        departmentResponses.forEach(departmentResponse -> departmentResponse.setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, departmentResponse.getDepartmentId().toString())));
        departmentResponses.forEach(departmentResponse -> departmentResponse.getManager().setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, departmentResponse.getDepartmentId().toString())));
        List<Link> links = LinksUtil.createPaginatedResourceLinks(uriInfo, departmentFilter.getPaginationBean(), count);
        DepartmentPaginationResponse departmentPaginationResponse = new DepartmentPaginationResponse(departmentResponses, links);
        return Response.ok(departmentPaginationResponse).build();
    }

    @Override
    public Response addDepartment(DepartmentDTO departmentDTO) {
        BeanValidator.validateBean(departmentDTO);
        BeanValidator.validateID(departmentDTO, HttpMethod.POST);
        Integer departmentId = departmentService.saveDepartment(departmentDTO);
        DepartmentResponse departmentResponse = new DepartmentResponse(departmentDTO);
        departmentResponse.setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, departmentId.toString()));
        departmentResponse.getManager().setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, departmentDTO.managerId().toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, DepartmentResourceService.class, departmentId.toString());
        return Response.created(uri).entity(departmentResponse).build();
    }

    @Override
    public Response deleteDepartment(Integer id) {
        departmentService.deleteDepartment(id);
        return Response.noContent().build();
    }

    @Override
    public Response deleteAllDepartments() {
        departmentService.deleteAll();
        return Response.noContent().build();
    }

    @Override
    public Response updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        BeanValidator.validateBean(departmentDTO);
        BeanValidator.validateID(departmentDTO, HttpMethod.PUT);
        DepartmentDTO updatedDepartmentDTO = new DepartmentDTO(id, departmentDTO.departmentName(), departmentDTO.managerId(), departmentDTO.managerDTO());
        departmentService.updateDepartment(updatedDepartmentDTO);
        DepartmentResponse departmentResponse = new DepartmentResponse(updatedDepartmentDTO);
        departmentResponse.setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, id.toString()));
        return Response.ok(departmentResponse).build();
    }
}

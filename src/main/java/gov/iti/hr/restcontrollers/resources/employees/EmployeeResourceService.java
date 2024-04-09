package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.models.EmployeeVacationDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.restcontrollers.resources.departments.DepartmentResourceService;
import gov.iti.hr.restcontrollers.resources.interfaces.EmployeeResource;
import gov.iti.hr.restcontrollers.resources.jobs.JobResourceService;
import gov.iti.hr.restcontrollers.utils.LinksUtil;
import gov.iti.hr.services.EmployeeService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.AbstractMap;
import java.util.List;

@Path("/employees")
@Consumes({"application/json; qs=1", "application/xml; qs=0.75"})
@Produces({"application/json; qs=1", "application/xml; qs=0.75"})
public class EmployeeResourceService implements EmployeeResource {

    private final EmployeeService employeeService;

    @Context
    private UriInfo uriInfo;

    public EmployeeResourceService() {
        employeeService = new EmployeeService();
    }

    @Override
    public Response getEmployee(Integer id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        EmployeeResponse employeeResponse = new EmployeeResponse(employeeDTO);
        employeeResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, id.toString()));
        employeeResponse.getDepartmentResponse().setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, employeeDTO.department().departmentId().toString()));
        employeeResponse.getJobResponse().setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, employeeDTO.job().jobId().toString()));
        if(employeeDTO.manager() != null) {
            employeeResponse.getManagerResponse().setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, employeeDTO.manager().employeeId().toString()));
        }
        return Response.ok(employeeResponse).build();
    }

    @Override
    public Response getEmployees(EmployeeFilter employeeFilter) {
        Integer count = employeeService.getEmployeesCount();
        List<EmployeeDTO> employees = employeeService.getAllEmployees(employeeFilter);
        List<EmployeeResponse> employeeResponses = employees.stream().map(EmployeeResponse::new).toList();
        employeeResponses.forEach(employeeResponse -> employeeResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, employeeResponse.getEmployeeId().toString())));
        List<Link> links = LinksUtil.createPaginatedResourceLinks(uriInfo, employeeFilter.getPaginationBean(), count);
        EmployeePaginationResponse employeePaginationResponse = new EmployeePaginationResponse(employeeResponses, links);
        return Response.ok(employeePaginationResponse).build();
    }

    @Override
    public Response addEmployee(EmployeeDTO employeeDTO) {
        BeanValidator.validateBean(employeeDTO);
        BeanValidator.validateID(employeeDTO, HttpMethod.POST);
        AbstractMap.SimpleEntry<Integer, EmployeeDTO> employee = employeeService.saveEmployee(employeeDTO);
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getValue());
        employeeResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, employee.getKey().toString()));
        employeeResponse.getDepartmentResponse().setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, employee.getValue().department().departmentId().toString()));
        employeeResponse.getJobResponse().setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, employee.getValue().job().jobId().toString()));
        employeeResponse.getManagerResponse().setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, employee.getValue().manager().employeeId().toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, EmployeeResourceService.class, employee.getKey().toString());
        return Response.created(uri).entity(employeeResponse).build();
    }

    @Override
    public Response addManager(ManagerDTO managerDTO) {
        BeanValidator.validateBean(managerDTO);
        BeanValidator.validateID(managerDTO, HttpMethod.POST);
        AbstractMap.SimpleEntry<Integer, ManagerDTO> manager = employeeService.saveManager(managerDTO);
        ManagerResponse managerResponse = new ManagerResponse(manager.getValue());
        managerResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, manager.getKey().toString()));
        managerResponse.getDepartmentResponse().setLink(LinksUtil.createSelfLink(uriInfo, DepartmentResourceService.class, manager.getValue().department().departmentId().toString()));
        managerResponse.getJobResponse().setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, manager.getValue().job().jobId().toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, EmployeeResourceService.class, manager.getKey().toString());
        return Response.created(uri).entity(managerResponse).build();
    }

    @Override
    public Response deleteEmployee(Integer id) {
        return null;
    }

    @Override
    public Response deleteAllEmployees() {
        return null;
    }

    @Override
    public Response updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public Response addVacation(Integer id, EmployeeVacationDTO employeeVacationDTO) {
        BeanValidator.validateBean(employeeVacationDTO);
        BeanValidator.validateID(employeeVacationDTO, HttpMethod.POST);
        employeeService.addVacation(id, employeeVacationDTO);
        return Response.ok("Vacation is Accepted").build();
    }
}

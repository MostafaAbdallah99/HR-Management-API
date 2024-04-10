package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.models.EmployeeVacationDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.restcontrollers.resources.interfaces.EmployeeResource;
import gov.iti.hr.restcontrollers.utils.LinksUtil;
import gov.iti.hr.services.EmployeeService;
import jakarta.ws.rs.Consumes;
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
        BeanValidator.validateID(employeeDTO);
        AbstractMap.SimpleEntry<Integer, EmployeeDTO> employee = employeeService.saveEmployee(employeeDTO);
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getValue());
        employeeResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, employee.getKey().toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, EmployeeResourceService.class, employee.getKey().toString());
        return Response.created(uri).entity(employeeResponse).build();
    }

    @Override
    public Response addManager(ManagerDTO managerDTO) {
        BeanValidator.validateBean(managerDTO);
        BeanValidator.validateID(managerDTO);
        AbstractMap.SimpleEntry<Integer, ManagerDTO> manager = employeeService.saveManager(managerDTO);
        ManagerResponse managerResponse = new ManagerResponse(manager.getValue());
        managerResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, manager.getKey().toString()));
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
        BeanValidator.validateBean(employeeDTO);

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(
                id,
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getHireDate(),
                employeeDTO.getSalary(),
                employeeDTO.getVacationBalance(),
                employeeDTO.getGender(),
                employeeDTO.getManagerEmail(),
                employeeDTO.getJobName(),
                employeeDTO.getDepartmentName(),
                employeeDTO.getManager(),
                employeeDTO.getJob(),
                employeeDTO.getDepartment()
                );
        employeeService.updateEmployee(updatedEmployeeDTO);
        EmployeeResponse employeeResponse = new EmployeeResponse(updatedEmployeeDTO);
        employeeResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, id.toString()));
        return Response.ok(employeeResponse).build();
    }

    @Override
    public Response addVacation(Integer id, EmployeeVacationDTO employeeVacationDTO) {
        BeanValidator.validateBean(employeeVacationDTO);
        BeanValidator.validateID(employeeVacationDTO);
        employeeService.addVacation(id, employeeVacationDTO);
        return Response.ok("Vacation is Accepted").build();
    }
}

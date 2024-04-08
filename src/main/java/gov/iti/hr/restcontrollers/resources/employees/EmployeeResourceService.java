package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.restcontrollers.resources.interfaces.EmployeeResource;
import gov.iti.hr.restcontrollers.utils.LinksUtil;
import gov.iti.hr.services.EmployeeService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

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
        return null;
    }

    @Override
    public Response getEmployees(EmployeeFilter employeeFilter) {
        return null;
    }

    @Override
    public Response addEmployee(EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public Response addManager(ManagerDTO managerDTO) {
        BeanValidator.validateBean(managerDTO);
        BeanValidator.validateID(managerDTO, HttpMethod.POST);
        Integer managerId = employeeService.saveManager(managerDTO);
        ManagerDTO newManagerDTO = new ManagerDTO(managerId, managerDTO.firstName(), managerDTO.lastName(), managerDTO.email(), managerDTO.phoneNumber(), managerDTO.hireDate(), managerDTO.salary(), managerDTO.vacationBalance(), managerDTO.gender(), managerDTO.jobName(), managerDTO.departmentName(), managerDTO.department(), managerDTO.job());
        ManagerResponse managerResponse = new ManagerResponse(newManagerDTO);
        managerResponse.setLink(LinksUtil.createSelfLink(uriInfo, EmployeeResourceService.class, managerId.toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, EmployeeResourceService.class, managerId.toString());
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
}

package gov.iti.hr.restcontrollers.resources.interfaces;


import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.models.EmployeeVacationDTO;
import gov.iti.hr.models.ManagerDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public interface EmployeeResource {
    @Path("/{id}")
    @GET
    Response getEmployee(@PathParam("id") Integer id);

    @GET
    Response getEmployees(@BeanParam EmployeeFilter employeeFilter);

    @POST
    Response addEmployee(EmployeeDTO employeeDTO);

    @POST
    @Path("/managers")
    Response addManager(ManagerDTO managerDTO);

    @DELETE
    @Path("/{id}")
    Response deleteEmployee(@PathParam("id") Integer id);

    @DELETE
    Response deleteAllEmployees();

    @PUT
    @Path("/{id}")
    Response updateEmployee(@PathParam("id") Integer id, EmployeeDTO employeeDTO);

    @POST
    @Path("/{id}/vacations")
    Response addVacation(@PathParam("id") Integer id, EmployeeVacationDTO employeeVacationDTO);
}

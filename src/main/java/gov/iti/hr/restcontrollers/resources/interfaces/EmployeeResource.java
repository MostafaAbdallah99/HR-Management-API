package gov.iti.hr.restcontrollers.resources.interfaces;


import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
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

    @DELETE
    @Path("/{id}")
    Response deleteEmployee(@PathParam("id") Integer id);

    @DELETE
    Response deleteAllEmployees();

    @PUT
    @Path("/{id}")
    Response updateEmployee(@PathParam("id") Integer id, EmployeeDTO employeeDTO);
}

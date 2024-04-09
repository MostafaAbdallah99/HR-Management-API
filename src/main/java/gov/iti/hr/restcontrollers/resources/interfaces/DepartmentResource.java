package gov.iti.hr.restcontrollers.resources.interfaces;

import gov.iti.hr.filters.DepartmentFilter;
import gov.iti.hr.models.DepartmentDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public interface DepartmentResource {
    @Path("/{id}")
    @GET
    Response getDepartment(@PathParam("id") Integer id);

    @GET
    Response getDepartments(@BeanParam DepartmentFilter departmentFilter);

    @POST
    Response addDepartment(DepartmentDTO departmentDTO);

    @DELETE
    @Path("/{id}")
    Response deleteDepartment(@PathParam("id") Integer id);

    @DELETE
    Response deleteAllDepartments();

    @PUT
    @Path("/{id}")
    Response updateDepartment(@PathParam("id") Integer id, DepartmentDTO departmentDTO);
}

package gov.iti.hr.restcontrollers.resources.interfaces;

import gov.iti.hr.filters.JobFilter;
import gov.iti.hr.models.JobDTO;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Response;



public interface JobResource {

    @Path("/{id}")
    @GET
    Response getJob(@PathParam("id") Integer id);

    @GET
    Response getJobs(@BeanParam JobFilter jobFilter);

    @POST
    Response addJob(JobDTO jobDTO);

    @DELETE
    @Path("/{id}")
    Response deleteJob(@PathParam("id") Integer id);

    @DELETE
    Response deleteAllJobs();

    @PUT
    @Path("/{id}")
    Response updateJob(@PathParam("id") Integer id, JobDTO jobDTO);
}

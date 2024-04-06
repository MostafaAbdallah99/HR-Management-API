package gov.iti.hr.restcontrollers;

import gov.iti.hr.models.JobDTO;
import gov.iti.hr.services.JobService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jobs")
public class JobResource {
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJob(@PathParam("id") Integer id) {
        JobService jobService = new JobService();
        JobDTO jobDTO = jobService.getJobById(id);
        return Response.ok(jobDTO).build();
    }
}

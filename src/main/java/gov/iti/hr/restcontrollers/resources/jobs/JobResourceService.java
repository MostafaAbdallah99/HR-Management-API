package gov.iti.hr.restcontrollers.resources.jobs;

import gov.iti.hr.models.JobDTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import gov.iti.hr.restcontrollers.resources.interfaces.JobResource;
import gov.iti.hr.restcontrollers.utils.LinksUtil;
import gov.iti.hr.services.JobService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/jobs")
@Consumes({"application/json; qs=1", "application/xml; qs=0.75"})
@Produces({"application/json; qs=1", "application/xml; qs=0.75"})
public class JobResourceService implements JobResource {
    private final JobService jobService;

    @Context
    private UriInfo uriInfo;

    public JobResourceService() {
        jobService = new JobService();
    }

    @Override
    public Response getJob(Integer id) {
        JobDTO jobDTO = jobService.getJobById(id);
        JobResponse jobResponse = new JobResponse(jobDTO);
        jobResponse.setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, id.toString()));
        return Response.ok(jobResponse).build();
    }

    @Override
    public Response getJobs(PaginationBean paginationBean) {
        Integer count = jobService.getJobsCount();
        List<JobDTO> jobs = jobService.getAllJobs(paginationBean);
        List<JobResponse> jobResponses = jobs.stream().map(JobResponse::new).toList();
        jobResponses.forEach(jobResponse -> jobResponse.setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, jobResponse.getJobId().toString())));
        List<Link> links = LinksUtil.createPaginatedResourceLinks(uriInfo, paginationBean, count);
        JobPaginationResponse jobPaginationResponse = new JobPaginationResponse(jobResponses, links);
        return Response.ok(jobPaginationResponse).build();
    }

    @Override
    public Response addJob(JobDTO jobDTO) {
        Integer jobId = jobService.saveJob(jobDTO);
        JobDTO newJobDTO = new JobDTO(jobId, jobDTO.jobTitle(), jobDTO.minSalary(), jobDTO.maxSalary());
        JobResponse jobResponse = new JobResponse(newJobDTO);
        jobResponse.setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, jobId.toString()));
        URI uri = LinksUtil.createUriAfterPostRequest(uriInfo, JobResourceService.class, jobId.toString());
        return Response.created(uri).entity(jobResponse).build();
    }

    @Override
    public Response deleteJob(Integer id) {
        jobService.deleteJob(id);
        return Response.noContent().build();
    }

    @Override
    public Response deleteAllJobs() {
        jobService.deleteAll();
        return Response.noContent().build();
    }

    @Override
    public Response updateJob(Integer id, JobDTO jobDTO) {
        JobDTO updatedJobDTO = new JobDTO(id, jobDTO.jobTitle(), jobDTO.minSalary(), jobDTO.maxSalary());
        jobService.updateJob(updatedJobDTO);
        JobResponse jobResponse = new JobResponse(updatedJobDTO);
        jobResponse.setLink(LinksUtil.createSelfLink(uriInfo, JobResourceService.class, id.toString()));
        return Response.ok(jobResponse).build();
    }

}

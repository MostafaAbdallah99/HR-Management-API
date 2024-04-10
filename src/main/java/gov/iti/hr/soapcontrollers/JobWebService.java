package gov.iti.hr.soapcontrollers;

import gov.iti.hr.filters.JobFilter;
import gov.iti.hr.models.JobDTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import gov.iti.hr.services.JobService;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class JobWebService {

    private final JobService jobService;

    public JobWebService() {
        jobService = new JobService();
    }

    public List<JobDTO> getJobs(@WebParam(name = "Offset") Integer offset, @WebParam(name = "Limit") Integer limit) {
        JobFilter jobFilter = new JobFilter();
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setLimit(limit);
        paginationBean.setOffset(offset);
        jobFilter.setPaginationBean(paginationBean);
        return jobService.getAllJobs(jobFilter);
    }

    public JobDTO getJobById(@WebParam(name = "JobId") Integer jobId) {
        return jobService.getJobById(jobId);
    }

    public String saveJob(
            @WebParam(name = "jobTitle") String jobTitle
            , @WebParam(name = "minSalary") Integer minSalary
            , @WebParam(name = "maxSalary") Integer maxSalary) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobTitle(jobTitle);
        jobDTO.setMinSalary(minSalary);
        jobDTO.setMaxSalary(maxSalary);
        return "Job saved successfully with id: " + jobService.saveJob(jobDTO);
    }

    public String updateJob(@WebParam(name = "JobDTO") JobDTO jobDTO) {
        jobService.updateJob(jobDTO.getJobId(), jobDTO);
        return "Job updated successfully";
    }

    public String deleteJob(@WebParam(name = "JobId") Integer jobId) {
        jobService.deleteJob(jobId);
        return "Job deleted successfully";
    }
}

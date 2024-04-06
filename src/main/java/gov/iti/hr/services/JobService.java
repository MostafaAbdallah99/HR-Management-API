package gov.iti.hr.services;

import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.mappers.JobMapper;
import gov.iti.hr.models.JobDTO;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.JobRepositoryImpl;

public class JobService {
    private final JobRepositoryImpl jobRepository;

    public JobService() {
        jobRepository = new JobRepositoryImpl();
    }

    public void saveJob(JobDTO jobDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Job job = JobMapper.INSTANCE.jobDTOToJob(jobDTO);
            jobRepository.save(job, entityManager);
        });
    }

    public JobDTO getJobById(Integer jobId) {
        return TransactionManager.doInTransaction(entityManager -> jobRepository.findById(jobId, entityManager)
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId)));
    }
}

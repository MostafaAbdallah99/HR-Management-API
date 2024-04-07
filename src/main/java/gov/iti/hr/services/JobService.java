package gov.iti.hr.services;

import gov.iti.hr.exceptions.EntityCreationException;
import gov.iti.hr.exceptions.InvalidPaginationException;
import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.mappers.JobMapper;
import gov.iti.hr.models.JobDTO;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.JobRepositoryImpl;
import gov.iti.hr.restcontrollers.beans.PaginationBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobService {
    private final JobRepositoryImpl jobRepository;

    public JobService() {
        jobRepository = new JobRepositoryImpl();
    }

    public Integer saveJob(JobDTO jobDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            Job job = JobMapper.INSTANCE.jobDTOToJob(jobDTO);
            if(jobRepository.save(job, entityManager)) {
                return job.getJobId();
            } else {
                throw new EntityCreationException("Job creation failed");
            }
        });
    }

    public void deleteJob(Integer jobId) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Optional<Job> job = jobRepository.findById(jobId, entityManager);
            job.ifPresent(j -> {
                if(!jobRepository.delete(j, entityManager)) {
                    throw new ResourceNotFoundException("Job not found with id: " + jobId);
                }
            });
        });
    }

    public void deleteAll() {
        TransactionManager.doInTransactionWithoutResult(jobRepository::deleteAll);
    }

    public void updateJob(JobDTO jobDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Job job = JobMapper.INSTANCE.jobDTOToJob(jobDTO);
            if(!jobRepository.update(job, entityManager)) {
                throw new ResourceNotFoundException("Job not found with id: " + jobDTO.jobId());
            }
        });
    }

    public JobDTO getJobById(Integer jobId) {
        return TransactionManager.doInTransaction(entityManager -> jobRepository.findById(jobId, entityManager)
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId)));
    }

    public List<JobDTO> getAllJobs(PaginationBean paginationBean) {
        validatePaginationParameters(paginationBean);
        return TransactionManager.doInTransaction(entityManager -> jobRepository.findAll(entityManager, paginationBean)
                .stream()
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .collect(Collectors.toList()));
    }

    public List<JobDTO> getJobsWithSalary(Integer min, Integer max, PaginationBean paginationBean) {
        validatePaginationParameters(paginationBean);
        return TransactionManager.doInTransaction(entityManager -> jobRepository.getJobsBySalaryRange(min, max, paginationBean, entityManager)
                .stream()
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .collect(Collectors.toList()));
    }

    public Integer getJobsCount() {
        return TransactionManager.doInTransaction(jobRepository::getJobsCount);
    }

    private void validatePaginationParameters(PaginationBean paginationBean) {
        boolean isLimitNegative = paginationBean.getLimit() < 0;
        boolean isOffsetNegative = paginationBean.getOffset() < 0;

        if (isLimitNegative && isOffsetNegative) {
            throw new InvalidPaginationException("Both limit and offset cannot be negative");
        } else if (isLimitNegative) {
            throw new InvalidPaginationException("Limit cannot be negative");
        } else if (isOffsetNegative) {
            throw new InvalidPaginationException("Offset cannot be negative");
        }
    }
}

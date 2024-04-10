package gov.iti.hr.services;

import gov.iti.hr.exceptions.BadRequestException;
import gov.iti.hr.exceptions.EntityCreationException;
import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.mappers.JobMapper;
import gov.iti.hr.models.JobDTO;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.EmployeeRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.JobRepositoryImpl;
import gov.iti.hr.filters.JobFilter;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JobService {
    private final JobRepositoryImpl jobRepository;
    private final EmployeeRepositoryImpl employeeRepository;
    private static final String JOB_NOT_FOUND_MSG = "Job not found with id: ";

    public JobService() {
        jobRepository = new JobRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
    }

    public Integer saveJob(JobDTO jobDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            validateJobCreation(0, jobDTO, entityManager);
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
            Optional.ofNullable(job).orElseThrow(() -> new ResourceNotFoundException("Job not found"))
                    .ifPresent(j -> {
                        employeeRepository.makeEmployeeUnemployed(j.getJobId(), entityManager);
                        jobRepository.delete(j, entityManager);
                    });
        });
    }

    public void deleteAll() {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            employeeRepository.makeEmployeesUnemployed(entityManager);
            jobRepository.deleteAll(entityManager);
        });
    }

    public void updateJob(Integer jobId, JobDTO jobDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            validateJobCreation(jobId, jobDTO, entityManager);
            jobRepository.findById(jobId, entityManager).ifPresentOrElse(j -> {
                Job job = JobMapper.INSTANCE.jobDTOToJob(jobDTO);
                jobRepository.update(job, entityManager);
            }, () -> {
                throw new ResourceNotFoundException(JOB_NOT_FOUND_MSG + jobId);
            });

        });
    }

    public JobDTO getJobById(Integer jobId) {
        return TransactionManager.doInTransaction(entityManager -> jobRepository.findById(jobId, entityManager)
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .orElseThrow(() -> new ResourceNotFoundException(JOB_NOT_FOUND_MSG + jobId)));
    }

    public List<JobDTO> getAllJobs(JobFilter jobFilter) {
        BeanValidator.validatePaginationParameters(jobFilter.getPaginationBean());
        return TransactionManager.doInTransaction(entityManager -> jobRepository.findAll(entityManager, jobFilter, Job.class)
                .stream()
                .map(JobMapper.INSTANCE::jobToJobDTO)
                .toList());
    }

    public Integer getJobsCount() {
        return TransactionManager.doInTransaction(jobRepository::count);
    }

    private void validateJobCreation(Integer jobId, JobDTO jobDTO, EntityManager entityManager) {
        if (jobDTO.getMaxSalary() < jobDTO.getMinSalary()) {
            throw new BadRequestException("Max salary cannot be less than min salary");
        }

        Optional<Job> job = jobRepository.findJobByName(jobDTO.getJobTitle(), entityManager);

        if (job.isPresent()) {
            if (jobId == 0) {
                throw new BadRequestException("Job with the same title already exists");
            } else if (!Objects.equals(jobId, job.get().getJobId())) {
                throw new BadRequestException("Job with the same title already exists");
            }
        }
    }
}

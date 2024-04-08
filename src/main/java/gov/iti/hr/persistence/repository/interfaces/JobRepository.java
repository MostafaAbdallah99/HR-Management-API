package gov.iti.hr.persistence.repository.interfaces;

import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.persistence.entities.Job;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface JobRepository {
    Integer getJobsCount(EntityManager entityManager);
    List<Job> getAllJobs(EntityManager entityManager, Filter filter);
    Optional<Job> findJobByName(String jobTitle, EntityManager entityManager);
}

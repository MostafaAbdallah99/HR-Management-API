package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.JobRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class JobRepositoryImpl extends GenericRepositoryImpl<Job, Integer> implements JobRepository {
    public JobRepositoryImpl() {
        super(Job.class);
    }

    @Override
    public Optional<Job> findJobByName(String jobTitle, EntityManager entityManager) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT j FROM Job j WHERE j.jobTitle = :jobTitle", Job.class)
                    .setParameter("jobTitle", jobTitle)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

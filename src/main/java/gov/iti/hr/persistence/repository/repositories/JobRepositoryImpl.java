package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.JobRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JobRepositoryImpl extends GenericRepositoryImpl<Job, Integer> implements JobRepository {
    public JobRepositoryImpl() {
        super(Job.class);
    }

    @Override
    public List<Job> getJobsBySalaryRange(int start, int limit, EntityManager entityManager) {
        return entityManager.createQuery("SELECT j FROM Job j WHERE j.minSalary >= :start AND j.maxSalary <= :limit", Job.class)
                .setParameter("start", start)
                .setParameter("limit", limit)
                .getResultList();
    }
}

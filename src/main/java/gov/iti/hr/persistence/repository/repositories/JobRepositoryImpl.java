package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.JobRepository;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JobRepositoryImpl extends GenericRepositoryImpl<Job, Integer> implements JobRepository {
    public JobRepositoryImpl() {
        super(Job.class);
    }

    @Override
    public List<Job> getJobsBySalaryRange(int start, int limit, PaginationBean paginationBean, EntityManager entityManager) {
        return entityManager.createQuery("SELECT j FROM Job j WHERE j.minSalary >= :start AND j.maxSalary <= :limit", Job.class)
                .setParameter("start", start)
                .setParameter("limit", limit)
                .setFirstResult(paginationBean.getOffset())
                .setMaxResults(paginationBean.getLimit())
                .getResultList();
    }

    @Override
    public Integer getJobsCount(EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(j) FROM Job j", Long.class).getSingleResult().intValue();
    }
}

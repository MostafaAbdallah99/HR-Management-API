package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.JobRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class JobRepositoryImpl extends GenericRepositoryImpl<Job, Integer> implements JobRepository {
    public JobRepositoryImpl() {
        super(Job.class);
    }

    @Override
    public Integer getJobsCount(EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(j) FROM Job j", Long.class).getSingleResult().intValue();
    }

    @Override
    public List<Job> getAllJobs(EntityManager entityManager, Filter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> query = cb.createQuery(Job.class);
        Root<Job> root = query.from(Job.class);
        List<Predicate> predicates = filter.getPredicates(cb, root);
        query.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<Job> jobTypedQuery = entityManager.createQuery(query);
        jobTypedQuery.setFirstResult(filter.getOffset());
        jobTypedQuery.setMaxResults(filter.getLimit());
        return jobTypedQuery.getResultList();
    }

    @Override
    public Optional<Job> findJobByName(String jobTitle, EntityManager entityManager) {
        return Optional.ofNullable(entityManager.createQuery("SELECT j FROM Job j WHERE j.jobTitle = :jobTitle", Job.class)
                .setParameter("jobTitle", jobTitle)
                .getSingleResult());
    }
}

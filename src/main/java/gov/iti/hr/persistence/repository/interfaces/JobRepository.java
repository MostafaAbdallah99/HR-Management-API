package gov.iti.hr.persistence.repository.interfaces;

import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface JobRepository {
    List<Job> getJobsBySalaryRange(int start, int limit, PaginationBean paginationBean, EntityManager entityManager);
    Integer getJobsCount(EntityManager entityManager);
}

package gov.iti.hr.persistence.repository.interfaces;

import gov.iti.hr.persistence.entities.Job;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface JobRepository {
    List<Job> getJobsBySalaryRange(int start, int limit, EntityManager entityManager);
}

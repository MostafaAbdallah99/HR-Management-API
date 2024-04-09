package gov.iti.hr.persistence.repository.interfaces;

import gov.iti.hr.persistence.entities.Job;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface JobRepository {
    Optional<Job> findJobByName(String jobTitle, EntityManager entityManager);
}

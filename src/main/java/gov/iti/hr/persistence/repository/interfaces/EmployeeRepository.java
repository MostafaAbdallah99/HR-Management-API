package gov.iti.hr.persistence.repository.interfaces;


import gov.iti.hr.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByEmail(String email, EntityManager entityManager);
}

package gov.iti.hr.persistence.repository.interfaces;


import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll(EntityManager entityManager, Filter filter);
    Optional<Employee> findByEmail(String email, EntityManager entityManager);
}

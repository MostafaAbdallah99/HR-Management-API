package gov.iti.hr.persistence.repository.interfaces;

import gov.iti.hr.persistence.entities.Department;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> findByName(String departmentName, EntityManager entityManager);
}

package gov.iti.hr.persistence.repository.interfaces;


import gov.iti.hr.persistence.entities.Department;
import gov.iti.hr.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByEmail(String email, EntityManager entityManager);
    void makeEmployeeUnemployed(Integer jobId, EntityManager entityManager);
    void makeEmployeesUnemployed(EntityManager entityManager);
    void removeEmployeeFromDepartment(Integer departmentId, EntityManager entityManager);
    void removeEmployeesFromDepartment(EntityManager entityManager);
    void setEmployeeDepartment(Integer employeeId, Integer departmentId, EntityManager entityManager);
    Optional<Department> hasDepartment(Integer employeeId, EntityManager entityManager);
}

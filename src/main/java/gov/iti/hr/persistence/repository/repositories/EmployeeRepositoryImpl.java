package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.EmployeeRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, Integer> implements EmployeeRepository {
    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }

    @Override
    public Optional<Employee> findByEmail(String email, EntityManager entityManager) {
        return Optional.ofNullable(entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}

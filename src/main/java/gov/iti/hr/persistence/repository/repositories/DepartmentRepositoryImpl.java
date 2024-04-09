package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Department;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.DepartmentRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class DepartmentRepositoryImpl extends GenericRepositoryImpl<Department, Integer> implements DepartmentRepository {
    public DepartmentRepositoryImpl() {
        super(Department.class);
    }

    @Override
    public Optional<Department> findByName(String departmentName, EntityManager entityManager) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT d FROM Department d WHERE d.departmentName = :departmentName", Department.class)
                    .setParameter("departmentName", departmentName)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

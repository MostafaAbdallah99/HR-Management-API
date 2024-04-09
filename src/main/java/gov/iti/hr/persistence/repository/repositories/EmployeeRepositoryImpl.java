package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.Department;
import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, Integer> implements EmployeeRepository {
    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }

    @Override
    public Optional<Employee> findByEmail(String email, EntityManager entityManager) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void makeEmployeeUnemployed(Integer jobId, EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.job = null WHERE e.job.jobId = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();
    }

    @Override
    public void makeEmployeesUnemployed(EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.job = null")
                .executeUpdate();
    }

    @Override
    public void removeEmployeeFromDepartment(Integer departmentId, EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.department = null, e.manager = null WHERE e.department.departmentId = :departmentId")
                .setParameter("departmentId", departmentId)
                .executeUpdate();
    }

    @Override
    public void removeEmployeesFromDepartment(EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.department = null, e.manager = null")
                .executeUpdate();
    }

    @Override
    public void setEmployeeDepartment(Integer employeeId, Integer departmentId, EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.department.departmentId = :departmentId WHERE e.employeeId = :employeeId")
                .setParameter("departmentId", departmentId)
                .setParameter("employeeId", employeeId)
                .executeUpdate();
    }

    @Override
    public Optional<Department> hasDepartment(Integer employeeId, EntityManager em) {
        try {
            return Optional.ofNullable(
                    em.createQuery("SELECT e.department FROM Employee e WHERE e.employeeId = :employeeId", Department.class)
                            .setParameter("employeeId", employeeId)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

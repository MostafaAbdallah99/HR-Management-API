package gov.iti.hr.services;

import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.filters.JobFilter;
import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.EmployeeRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepositoryImpl employeeRepository;
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found with id: ";

    public EmployeeService() {
        employeeRepository = new EmployeeRepositoryImpl();
    }

    public void deleteEmployee(Integer employeeId) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Optional<Employee> employee = employeeRepository.findById(employeeId, entityManager);
            employee.ifPresent(e -> {
                if(!employeeRepository.delete(e, entityManager)) {
                    throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employeeId);
                }
            });
        });
    }

    public void deleteAll() {
        TransactionManager.doInTransactionWithoutResult(employeeRepository::deleteAll);
    }

    public void updateEmployee(Employee employee) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            if(!employeeRepository.update(employee, entityManager)) {
                throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employee.getEmployeeId());
            }
        });
    }

    public Employee getEmployeeById(Integer employeeId) {
        return TransactionManager.doInTransaction(entityManager -> employeeRepository.findById(employeeId, entityManager)
                    .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employeeId))
        );
    }

    public Integer saveEmployee(Employee employee) {
        return TransactionManager.doInTransaction(entityManager -> {
            if(employeeRepository.save(employee, entityManager)) {
                return employee.getEmployeeId();
            } else {
                throw new ResourceNotFoundException("Employee creation failed");
            }
        });
    }

    public List<Employee> getAllEmployees(Filter filter) {
        return TransactionManager.doInTransaction(entityManager -> employeeRepository.findAll(entityManager, filter));
    }


}

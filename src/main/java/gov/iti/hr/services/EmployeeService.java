package gov.iti.hr.services;

import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.filters.JobFilter;
import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.mappers.DepartmentMapper;
import gov.iti.hr.mappers.JobMapper;
import gov.iti.hr.mappers.ManagerMapper;
import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.models.JobDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.entities.Job;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.DepartmentRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.EmployeeRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.JobRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepositoryImpl employeeRepository;
    private final JobRepositoryImpl jobRepository;
    private final DepartmentRepositoryImpl departmentRepository;
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found with id: ";

    public EmployeeService() {
        employeeRepository = new EmployeeRepositoryImpl();
        jobRepository = new JobRepositoryImpl();
        departmentRepository = new DepartmentRepositoryImpl();
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

    public Integer saveManager(ManagerDTO managerDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            DepartmentDTO departmentDTO = departmentRepository.findByName(managerDTO.departmentName(), entityManager)
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            JobDTO jobDTO = jobRepository.findJobByName(managerDTO.jobName(), entityManager)
                    .map(JobMapper.INSTANCE::jobToJobDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
            ManagerDTO newManagerDTO = new ManagerDTO(
                    managerDTO.managerId(),
                    managerDTO.firstName(),
                    managerDTO.lastName(),
                    managerDTO.email(),
                    managerDTO.phoneNumber(),
                    managerDTO.hireDate(),
                    managerDTO.salary(),
                    managerDTO.vacationBalance(),
                    managerDTO.gender(),
                    managerDTO.jobName(),
                    managerDTO.departmentName(), departmentDTO, jobDTO);
            Employee employee = ManagerMapper.INSTANCE.managerDTOToManager(newManagerDTO);
            if (employeeRepository.save(employee, entityManager)) {
                return employee.getEmployeeId();
            } else {
                throw new ResourceNotFoundException("Manager creation failed");
            }
        });
    }

    public List<Employee> getAllEmployees(Filter filter) {
        return TransactionManager.doInTransaction(entityManager -> employeeRepository.findAll(entityManager, filter));
    }


}

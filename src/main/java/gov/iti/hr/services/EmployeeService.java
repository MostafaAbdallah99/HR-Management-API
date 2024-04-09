package gov.iti.hr.services;

import gov.iti.hr.exceptions.BadRequestException;
import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.mappers.*;
import gov.iti.hr.models.*;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.entities.EmployeeVacation;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.DepartmentRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.EmployeeRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.EmployeeVacationRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.JobRepositoryImpl;

import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepositoryImpl employeeRepository;
    private final JobRepositoryImpl jobRepository;
    private final DepartmentRepositoryImpl departmentRepository;
    private final EmployeeVacationRepositoryImpl employeeVacationRepository;
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found with id: ";

    public EmployeeService() {
        employeeRepository = new EmployeeRepositoryImpl();
        jobRepository = new JobRepositoryImpl();
        departmentRepository = new DepartmentRepositoryImpl();
        employeeVacationRepository = new EmployeeVacationRepositoryImpl();
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

    public void updateEmployee(EmployeeDTO employeeDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Employee employee = EmployeeMapper.INSTANCE.employeeDTOToEmployee(employeeDTO);
            if(!employeeRepository.update(employee, entityManager)) {
                throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employee.getEmployeeId());
            }
        });
    }

    public EmployeeDTO getEmployeeById(Integer employeeId) {
        return TransactionManager.doInTransaction(entityManager -> employeeRepository.findById(employeeId, entityManager)
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeDTO)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employeeId)));
    }

    public AbstractMap.SimpleEntry<Integer, EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            DepartmentDTO departmentDTO = departmentRepository.findByName(employeeDTO.departmentName(), entityManager)
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            JobDTO jobDTO = jobRepository.findJobByName(employeeDTO.jobName(), entityManager)
                    .map(JobMapper.INSTANCE::jobToJobDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
            ManagerDTO managerDTO = employeeRepository.findByEmail(employeeDTO.managerEmail(), entityManager)
                    .map(ManagerMapper.INSTANCE::managerToManagerDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

            EmployeeDTO newEmployeeDTO = new EmployeeDTO(
                    employeeDTO.employeeId(),
                    employeeDTO.firstName(),
                    employeeDTO.lastName(),
                    employeeDTO.email(),
                    employeeDTO.phoneNumber(),
                    employeeDTO.hireDate(),
                    employeeDTO.salary(),
                    employeeDTO.vacationBalance(),
                    employeeDTO.gender(),
                    employeeDTO.managerEmail(),
                    employeeDTO.jobName(),
                    employeeDTO.departmentName(),
                    managerDTO,
                    jobDTO,
                    departmentDTO
            );
            Employee employee = EmployeeMapper.INSTANCE.employeeDTOToEmployee(newEmployeeDTO);
            if (employeeRepository.save(employee, entityManager)) {
                return new AbstractMap.SimpleEntry<>(employee.getEmployeeId(), newEmployeeDTO);
            } else {
                throw new ResourceNotFoundException("Manager creation failed");
            }
        });
    }

    public AbstractMap.SimpleEntry<Integer, ManagerDTO> saveManager(ManagerDTO managerDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            DepartmentDTO departmentDTO = departmentRepository.findByName(managerDTO.departmentName(), entityManager)
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            JobDTO jobDTO = jobRepository.findJobByName(managerDTO.jobName(), entityManager)
                    .map(JobMapper.INSTANCE::jobToJobDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
            ManagerDTO newManagerDTO = new ManagerDTO(
                    managerDTO.employeeId(),
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
                return new AbstractMap.SimpleEntry<>(employee.getEmployeeId(), newManagerDTO);
            } else {
                throw new ResourceNotFoundException("Manager creation failed");
            }
        });
    }

    public List<EmployeeDTO> getAllEmployees(EmployeeFilter employeeFilter) {
        BeanValidator.validatePaginationParameters(employeeFilter.getPaginationBean());
        return TransactionManager.doInTransaction(entityManager -> employeeRepository.findAll(entityManager, employeeFilter, Employee.class)
                .stream()
                .map(EmployeeMapper.INSTANCE::employeeToEmployeeDTO)
                .toList());
    }

    public Integer getEmployeesCount() {
        return TransactionManager.doInTransaction(employeeRepository::count);
    }

    public void addVacation(Integer employeeId, EmployeeVacationDTO employeeVacationDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Employee employee = employeeRepository.findById(employeeId, entityManager)
                    .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MSG + employeeId));
            Long vacationDays = ChronoUnit.DAYS.between(employeeVacationDTO.vacationStartDate(), employeeVacationDTO.vacationEndDate());
            if(employee.getVacationBalance() < vacationDays) {
                throw new BadRequestException("Vacation balance is not enough");
            } else {
                employee.setVacationBalance(employee.getVacationBalance() - vacationDays.intValue());
                employeeRepository.update(employee, entityManager);
                EmployeeDTO employeeDTO = EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employee);
                EmployeeVacationDTO newEmployeeVacationDTO = new EmployeeVacationDTO(
                        employeeVacationDTO.vacationId(),
                        employeeVacationDTO.vacationStartDate(),
                        employeeVacationDTO.vacationEndDate(),
                        employeeDTO
                );
                EmployeeVacation employeeVacation = EmployeeVacationMapper.INSTANCE.employeeVacationDTOToEmployeeVacation(newEmployeeVacationDTO);
                employeeVacationRepository.save(employeeVacation, entityManager);
            }
        });
    }
}

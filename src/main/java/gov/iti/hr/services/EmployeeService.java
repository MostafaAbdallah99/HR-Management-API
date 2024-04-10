package gov.iti.hr.services;

import gov.iti.hr.exceptions.BadRequestException;
import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.mappers.*;
import gov.iti.hr.models.*;
import gov.iti.hr.models.validation.BeanValidator;
import gov.iti.hr.persistence.entities.Department;
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
            Employee manager = employeeRepository.findByEmail(employeeDTO.getManagerEmail(), entityManager)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found not found"));
            JobDTO jobDTO = jobRepository.findJobByName(employeeDTO.getJobName(), entityManager)
                    .map(JobMapper.INSTANCE::jobToJobDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found"));



            ManagerDTO managerDTO = ManagerMapper.INSTANCE.managerToManagerDTO(manager);

            Department department = departmentRepository.findByName(employeeDTO.getDepartmentName(), entityManager)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            DepartmentDTO departmentDTO = new DepartmentDTO(
                    department.getDepartmentId(),
                    department.getDepartmentName(),
                    manager.getEmployeeId(),
                    department.getManager() == null ? null : ManagerMapper.INSTANCE.managerToManagerDTO(department.getManager()));


            EmployeeDTO newEmployeeDTO = new EmployeeDTO(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getFirstName(),
                    employeeDTO.getLastName(),
                    employeeDTO.getEmail(),
                    employeeDTO.getPhoneNumber(),
                    employeeDTO.getHireDate(),
                    employeeDTO.getSalary(),
                    employeeDTO.getVacationBalance(),
                    employeeDTO.getGender(),
                    employeeDTO.getManagerEmail(),
                    employeeDTO.getJobName(),
                    employeeDTO.getDepartmentName(),
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

            JobDTO jobDTO = jobRepository.findJobByName(managerDTO.getJobName(), entityManager)
                    .map(JobMapper.INSTANCE::jobToJobDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
            ManagerDTO newManagerDTO = new ManagerDTO(
                    managerDTO.getEmployeeId(),
                    managerDTO.getFirstName(),
                    managerDTO.getLastName(),
                    managerDTO.getEmail(),
                    managerDTO.getPhoneNumber(),
                    managerDTO.getHireDate(),
                    managerDTO.getSalary(),
                    managerDTO.getVacationBalance(),
                    managerDTO.getGender(),
                    managerDTO.getJobName(),
                    null, jobDTO);
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
            Long vacationDays = ChronoUnit.DAYS.between(employeeVacationDTO.getVacationStartDate(), employeeVacationDTO.getVacationEndDate());
            if(employee.getVacationBalance() < vacationDays) {
                throw new BadRequestException("Vacation balance is not enough");
            } else {
                employee.setVacationBalance(employee.getVacationBalance() - vacationDays.intValue());
                employeeRepository.update(employee, entityManager);
                EmployeeDTO employeeDTO = EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employee);
                EmployeeVacationDTO newEmployeeVacationDTO = new EmployeeVacationDTO(
                        employeeVacationDTO.getVacationId(),
                        employeeVacationDTO.getVacationStartDate(),
                        employeeVacationDTO.getVacationEndDate(),
                        employeeDTO
                );
                EmployeeVacation employeeVacation = EmployeeVacationMapper.INSTANCE.employeeVacationDTOToEmployeeVacation(newEmployeeVacationDTO);
                employeeVacationRepository.save(employeeVacation, entityManager);
            }
        });
    }
}

package gov.iti.hr.services;

import gov.iti.hr.exceptions.BadRequestException;
import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.filters.DepartmentFilter;
import gov.iti.hr.mappers.DepartmentMapper;
import gov.iti.hr.mappers.ManagerMapper;
import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.persistence.entities.Department;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.DepartmentRepositoryImpl;
import gov.iti.hr.persistence.repository.repositories.EmployeeRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final DepartmentRepositoryImpl departmentRepository;
    private final EmployeeRepositoryImpl employeeRepository;
    private static final String DEPARTMENT_NOT_FOUND_MSG = "Department not found with id: ";

    public DepartmentService() {
        departmentRepository = new DepartmentRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
    }

    public void deleteDepartment(Integer departmentId) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Optional<Department> department = departmentRepository.findById(departmentId, entityManager);
            department.ifPresentOrElse(d -> {
                employeeRepository.removeEmployeeFromDepartment(departmentId, entityManager);
                departmentRepository.delete(d, entityManager);
            }, () -> {
                throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId);
            });
        });
    }

    public void deleteAll() {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            employeeRepository.removeEmployeesFromDepartment(entityManager);
            departmentRepository.deleteAll(entityManager);
        });
    }

    public void updateDepartment(DepartmentDTO departmentDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Optional<Department> department = departmentRepository.findById(departmentDTO.departmentId(), entityManager);
            department.ifPresentOrElse(d -> {
                if(departmentDTO.managerId() != null) {

                    ManagerDTO managerDTO = employeeRepository.findReferenceById(departmentDTO.managerId(), entityManager)
                            .map(ManagerMapper.INSTANCE::managerToManagerDTO)
                            .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + departmentDTO.managerId()));
                    DepartmentDTO newdepartmentDTO = new DepartmentDTO(departmentDTO.departmentId(), departmentDTO.departmentName(), departmentDTO.managerId(), managerDTO);
                    Department newDepartment = DepartmentMapper.INSTANCE.departmentDTOToDepartment(newdepartmentDTO);
                    departmentRepository.update(newDepartment, entityManager);
                    employeeRepository.setEmployeeDepartment(departmentDTO.managerId(), departmentDTO.departmentId(), entityManager);
                } else {
                    Department newDepartment = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
                    departmentRepository.update(newDepartment, entityManager);
                }
            }, () -> {
                throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentDTO.departmentId());
            });
        });
    }

    public DepartmentDTO getDepartmentById(Integer departmentId) {
        return TransactionManager.doInTransaction(entityManager -> departmentRepository.findById(departmentId, entityManager)
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId))
        );
    }

    public Integer saveDepartment(DepartmentDTO departmentDTO) {
        return TransactionManager.doInTransaction(entityManager -> {
            if(departmentDTO.managerId() != null) {
                Department managerDepartment = employeeRepository.hasDepartment(departmentDTO.managerId(), entityManager)
                        .orElseThrow(() -> new BadRequestException("Manager with id: " + departmentDTO.managerId() + " does not exist"));
                if(managerDepartment.getDepartmentId() != null) {
                    throw new BadRequestException("Manager with id: " + departmentDTO.managerId() + " is already a manager of another department");
                }
                ManagerDTO managerDTO = employeeRepository.findReferenceById(departmentDTO.managerId(), entityManager)
                        .map(ManagerMapper.INSTANCE::managerToManagerDTO)
                        .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + departmentDTO.managerId()));
                DepartmentDTO newdepartmentDTO = new DepartmentDTO(null, departmentDTO.departmentName(), departmentDTO.managerId(), managerDTO);
                Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(newdepartmentDTO);
                departmentRepository.save(department, entityManager);
                employeeRepository.setEmployeeDepartment(departmentDTO.managerId(), department.getDepartmentId(), entityManager);
                return department.getDepartmentId();
            } else {
                Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
                departmentRepository.save(department, entityManager);
                return department.getDepartmentId();
            }
        });
    }

    public List<DepartmentDTO> findAllDepartments(DepartmentFilter departmentFilter) {
        return TransactionManager.doInTransaction(entityManager -> departmentRepository.findAll(entityManager, departmentFilter, Department.class)
                    .stream()
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .toList()
        );
    }


    public Integer getDepartmentsCount() {
        return TransactionManager.doInTransaction(departmentRepository::count);
    }
}

package gov.iti.hr.services;

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
            department.ifPresent(d -> {
                if(d.getManager() != null) {
                    entityManager.createQuery("UPDATE Employee e SET e.department = null WHERE e.department.departmentId = :departmentId")
                            .setParameter("departmentId", departmentId)
                            .executeUpdate();
                }
                if(!departmentRepository.delete(d, entityManager)) {
                    throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId);
                }
            });
        });
    }

    public void deleteAll() {
        TransactionManager.doInTransactionWithoutResult(departmentRepository::deleteAll);
    }

    public void updateDepartment(DepartmentDTO departmentDTO) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            ManagerDTO managerDTO = employeeRepository.findReferenceById(departmentDTO.managerId(), entityManager)
                    .map(ManagerMapper.INSTANCE::managerToManagerDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + departmentDTO.managerId()));
            DepartmentDTO newDepartmentDTO = new DepartmentDTO(departmentDTO.departmentId(), departmentDTO.departmentName(), departmentDTO.managerId(), managerDTO);
            Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(newDepartmentDTO);
            if(!departmentRepository.update(department, entityManager)) {
                throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentDTO.departmentId());
            }
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
            ManagerDTO managerDTO = employeeRepository.findReferenceById(departmentDTO.managerId(), entityManager)
                    .map(ManagerMapper.INSTANCE::managerToManagerDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + departmentDTO.managerId()));
            DepartmentDTO newDepartmentDTO = new DepartmentDTO(null, departmentDTO.departmentName(), departmentDTO.managerId(), managerDTO);
            Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(newDepartmentDTO);
            if(departmentRepository.save(department, entityManager)) {
                return department.getDepartmentId();
            } else {
                throw new ResourceNotFoundException("Department creation failed");
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

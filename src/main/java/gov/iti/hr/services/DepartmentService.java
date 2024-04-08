package gov.iti.hr.services;

import gov.iti.hr.exceptions.ResourceNotFoundException;
import gov.iti.hr.mappers.DepartmentMapper;
import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.persistence.entities.Department;
import gov.iti.hr.persistence.repository.TransactionManager;
import gov.iti.hr.persistence.repository.repositories.DepartmentRepositoryImpl;
import gov.iti.hr.restcontrollers.beans.PaginationBean;

import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final DepartmentRepositoryImpl departmentRepository;
    private static final String DEPARTMENT_NOT_FOUND_MSG = "Department not found with id: ";

    public DepartmentService() {
        departmentRepository = new DepartmentRepositoryImpl();
    }

    public void deleteDepartment(Integer departmentId) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            Optional<Department> department = departmentRepository.findById(departmentId, entityManager);
            department.ifPresent(d -> {
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
            Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
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
            Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
            if(departmentRepository.save(department, entityManager)) {
                return department.getDepartmentId();
            } else {
                throw new ResourceNotFoundException("Department creation failed");
            }
        });
    }

    public DepartmentDTO getDepartmentByName(String departmentName) {
        return TransactionManager.doInTransaction(entityManager -> departmentRepository.findByName(departmentName, entityManager)
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + departmentName))
        );
    }

    public List<DepartmentDTO> getAllDepartments(PaginationBean paginationBean) {
        return TransactionManager.doInTransaction(entityManager -> departmentRepository.findAll(entityManager, paginationBean)
                    .stream()
                    .map(DepartmentMapper.INSTANCE::departmentToDepartmentDTO)
                    .toList());
    }

    public Integer getDepartmentsCount() {
        return TransactionManager.doInTransaction(departmentRepository::count);
    }
}

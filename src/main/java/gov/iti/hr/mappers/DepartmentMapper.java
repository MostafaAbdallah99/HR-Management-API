package gov.iti.hr.mappers;

import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.persistence.entities.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    DepartmentDTO departmentToDepartmentDTO(Department department);
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}

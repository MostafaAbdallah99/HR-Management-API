package gov.iti.hr.mappers;

import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.persistence.entities.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    @Mapping(source = "manager", target = "managerDTO")
    DepartmentDTO departmentToDepartmentDTO(Department department);
    @Mapping(source = "managerDTO", target = "manager")
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}

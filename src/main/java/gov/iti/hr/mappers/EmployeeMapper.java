package gov.iti.hr.mappers;

import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.persistence.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}

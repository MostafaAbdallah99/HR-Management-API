package gov.iti.hr.mappers;

import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.persistence.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper {
    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);
    @Mapping(target="jobName", ignore=true)
    @Mapping(target="departmentName", ignore=true)
    ManagerDTO managerToManagerDTO(Employee manager);

    Employee managerDTOToManager(ManagerDTO managerDTO);
}

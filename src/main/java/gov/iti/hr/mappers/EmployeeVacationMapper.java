package gov.iti.hr.mappers;

import gov.iti.hr.models.EmployeeVacationDTO;
import gov.iti.hr.persistence.entities.EmployeeVacation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeVacationMapper {
    EmployeeVacationMapper INSTANCE = Mappers.getMapper(EmployeeVacationMapper.class);
    EmployeeVacationDTO employeeVacationToEmployeeVacationDTO(EmployeeVacation employeeVacation);
    EmployeeVacation employeeVacationDTOToEmployeeVacation(EmployeeVacationDTO employeeVacationDTO);
}

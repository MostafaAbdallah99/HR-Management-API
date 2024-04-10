package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class DepartmentDTO implements DTO{
    Integer departmentId;
    @NotNull(message = "Department name is required")
    String departmentName;
    Integer managerId;
    ManagerDTO managerDTO;

    @Override
    public Integer getDTOId() {
        return getDepartmentId();
    }
}


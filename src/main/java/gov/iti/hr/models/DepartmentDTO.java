package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.validation.constraints.NotNull;

public record DepartmentDTO(
        Integer departmentId,
        @NotNull(message = "Department name is required")
        String departmentName,
        Integer managerId,
        ManagerDTO managerDTO
) implements DTO {
    @Override
    public Integer getDTOId() {
        return departmentId();
    }
}

package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeVacationDTO(
        Integer vacationId,
        @NotNull(message = "Vacation start date is required")
        @FutureOrPresent(message = "Vacation start date should be in the future or present")
        @JsonbDateFormat("yyyy-MM-dd")
        LocalDate vacationStartDate,
        @NotNull(message = "Vacation end date is required")
        @JsonbDateFormat("yyyy-MM-dd")
        @FutureOrPresent(message = "Vacation end date should be in the future or present")
        LocalDate vacationEndDate,
        EmployeeDTO employee

)  implements DTO {
    @Override
    public Integer getDTOId() {
        return vacationId();
    }
}

package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import gov.iti.hr.persistence.entities.enums.Gender;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record ManagerDTO (
        Integer managerId,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @Email(message = "Email should be valid")
        @NotNull(message = "Email is required")
        String email,
        @Pattern(regexp="^\\+2\\d{11}$", message="Phone number should start with '+2' followed by 11 digits")
        @NotNull(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Hire date is required")
        @PastOrPresent(message = "Hire date should be in the past or present")
        @JsonbDateFormat("yyyy-MM-dd")
        LocalDate hireDate,
        @DecimalMin(value = "0.0", message = "Salary should be a positive number")
        BigDecimal salary,
        @Min(value = 0, message = "Vacation balance should be a positive number")
        Integer vacationBalance,
        @NotNull(message = "Gender is required")
        Gender gender,
        @NotNull(message = "Job is required")
        String jobName,
        @NotNull(message = "Department is required")
        String departmentName,
        DepartmentDTO department,
        JobDTO job
) implements DTO {
    @Override
    public Integer getDTOId() {
        return managerId();
    }
}

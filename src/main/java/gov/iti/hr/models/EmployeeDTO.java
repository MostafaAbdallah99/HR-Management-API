package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import gov.iti.hr.persistence.entities.enums.Gender;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDTO(
        Integer employeeId,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @Email(message = "Email should be valid")
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Phone number is required")
        @Pattern(regexp="^\\+2\\d{11}$", message="Phone number should start with '+2' followed by 11 digits")
        String phoneNumber,
        @NotNull(message = "Hire date is required")
        @PastOrPresent(message = "Hire date should be in the past or present")
        @JsonbDateFormat("yyyy-MM-dd")
        LocalDate hireDate,
        @DecimalMin(value = "0.0", message = "Salary should be a positive number")
        BigDecimal salary,
        @Min(value = 0, message = "Vacation balance should be a positive number")
        Integer vacationBalance,
        @NotNull(message = "Employee should have gender")
        Gender gender,
        @Email(message = "Manager Email should be valid")
        @NotNull(message = "Employee should have manager")
        String managerEmail,
        @NotNull(message = "Employee should have job")
        String jobName,
        @NotNull(message = "Employee should have department")
        String departmentName,
        ManagerDTO manager,
        JobDTO job,
        DepartmentDTO department
) implements DTO {
    @Override
    public Integer getDTOId() {
        return employeeId();
    }
}

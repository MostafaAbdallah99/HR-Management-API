package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

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
        Date hireDate,
        @DecimalMin(value = "0.0", message = "Salary should be a positive number")
        BigDecimal salary,
        @Min(value = 0, message = "Vacation balance should be a positive number")
        Integer vacationBalance,
        @NotNull(message = "Employee should have manager")
        ManagerDTO manager,
        @NotNull(message = "Employee should have job")
        JobDTO job,
        @NotNull(message = "Employee should have department")
        DepartmentDTO department
) implements DTO {
    @Override
    public Integer getDTOId() {
        return employeeId();
    }
}

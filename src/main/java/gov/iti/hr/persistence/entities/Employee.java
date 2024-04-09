package gov.iti.hr.persistence.entities;


import gov.iti.hr.persistence.entities.base.BaseEntity;
import gov.iti.hr.persistence.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "employees")
@Entity
public class Employee implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate hireDate;

    @Column(name ="salary", nullable = false, precision = 8, scale = 2)
    private BigDecimal salary;

    @Column(name = "vacation_balance", nullable = false, columnDefinition = "int default 21")
    private Integer vacationBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 6)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId != null && employeeId.equals(employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, email, phoneNumber, hireDate, salary, vacationBalance, gender);
    }

}

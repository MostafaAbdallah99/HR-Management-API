package gov.iti.hr.persistence.entities;

import gov.iti.hr.persistence.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee_vacation")
public class EmployeeVacation implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id", nullable = false)
    private Integer vacationId;

    @Column(name = "vacation_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate vacationStartDate;

    @Column(name = "vacation_end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate vacationEndDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeVacation that = (EmployeeVacation) o;
        return vacationId != null && vacationId.equals(that.vacationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacationId, vacationStartDate, vacationEndDate);
    }
}

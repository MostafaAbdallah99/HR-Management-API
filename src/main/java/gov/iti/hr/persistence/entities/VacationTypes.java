package gov.iti.hr.persistence.entities;

import gov.iti.hr.persistence.entities.base.BaseEntity;
import gov.iti.hr.persistence.entities.enums.VacationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vacation_types")
public class VacationTypes implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_type_id", nullable = false)
    private Integer vacationTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vacation_type_name", nullable = false, unique = true, length = 50)
    private VacationType vacationTypeName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationTypes that = (VacationTypes) o;
        return vacationTypeId != null && vacationTypeId.equals(that.vacationTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacationTypeId, vacationTypeName);
    }
}

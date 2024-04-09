package gov.iti.hr.persistence.entities;

import gov.iti.hr.persistence.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "departments")
@Entity
public class Department implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "department_name", nullable = false, length = 30)
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return departmentId != null && departmentId.equals(department.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName);
    }
}

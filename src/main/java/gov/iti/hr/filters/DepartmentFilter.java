package gov.iti.hr.filters;

import gov.iti.hr.filters.base.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentFilter extends Filter {
    @QueryParam("departmentName")
    private String departmentName;

    @Override
    public <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> departmentRoot) {
        List<Predicate> predicates = new ArrayList<>();
        Optional.ofNullable(departmentName).ifPresent(name ->
                predicates.add(cb.like(departmentRoot.get("departmentName"), "%" + name + "%"))
        );
        return predicates;
    }
}

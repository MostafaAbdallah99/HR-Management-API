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
public class JobFilter extends Filter {
    @QueryParam("jobTitle")
    private String jobTitle;
    @QueryParam("minSalary")
    private Integer minSalary;
    @QueryParam("maxSalary")
    private Integer maxSalary;

    @Override
    public <T> List<Predicate>  getPredicates(CriteriaBuilder cb, Root<T> jobRoot) {
        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(minSalary).ifPresent(min ->
                predicates.add(Optional.ofNullable(maxSalary)
                        .map(max -> cb.between(jobRoot.get("minSalary"), min, max))
                        .orElseGet(() -> cb.greaterThanOrEqualTo(jobRoot.get("minSalary"), min)))
        );

        Optional.ofNullable(maxSalary).ifPresent(max ->
                predicates.add(Optional.ofNullable(minSalary)
                        .map(min -> cb.between(jobRoot.get("minSalary"), min, max))
                        .orElseGet(() -> cb.lessThanOrEqualTo(jobRoot.get("maxSalary"), max)))
        );

        Optional.ofNullable(jobTitle).ifPresent(title ->
                predicates.add(cb.like(jobRoot.get("jobTitle"), "%" + title + "%"))
        );

        return predicates;
    }
}

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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVacationFilter extends Filter {
    @QueryParam("vacationStartDate")
    private Date vacationStartDate;
    @QueryParam("vacationEndDate")
    private Date vacationEndDate;

    @Override
    public <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> employeeVacationRoot) {
        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(vacationStartDate).ifPresent(date ->
                predicates.add(cb.equal(employeeVacationRoot.get("vacationStartDate"), date))
        );

        Optional.ofNullable(vacationEndDate).ifPresent(date ->
                predicates.add(cb.equal(employeeVacationRoot.get("vacationEndDate"), date))
        );


        return predicates;
    }

}

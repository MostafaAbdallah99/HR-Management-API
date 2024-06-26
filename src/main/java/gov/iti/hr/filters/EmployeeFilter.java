package gov.iti.hr.filters;

import gov.iti.hr.filters.base.Filter;
import gov.iti.hr.persistence.entities.enums.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFilter extends Filter {
    @QueryParam("firstName")
    private String firstName;
    @QueryParam("lastName")
    private String lastName;
    @QueryParam("email")
    private String email;
    @QueryParam("phoneNumber")
    private String phoneNumber;
    @QueryParam("hireDate")
    private Date hireDate;
    @QueryParam("salary")
    private BigDecimal salary;
    @QueryParam("vacationBalance")
    private Integer vacationBalance;
    @QueryParam("gender")
    private Gender gender;

    public <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(firstName).ifPresent(name ->
                predicates.add(cb.like(employeeRoot.get("firstName"), "%" + name + "%"))
        );

        Optional.ofNullable(lastName).ifPresent(name ->
                predicates.add(cb.like(employeeRoot.get("lastName"), "%" + name + "%"))
        );

        Optional.ofNullable(email).ifPresent(mail ->
                predicates.add(cb.like(employeeRoot.get("email"), "%" + mail + "%"))
        );

        Optional.ofNullable(phoneNumber).ifPresent(phone ->
                predicates.add(cb.like(employeeRoot.get("phoneNumber"), "%" + phone + "%"))
        );

        Optional.ofNullable(hireDate).ifPresent(date ->
                predicates.add(cb.equal(employeeRoot.get("hireDate"), date))
        );

        Optional.ofNullable(salary).ifPresent(sal ->
                predicates.add(cb.equal(employeeRoot.get("salary"), sal))
        );

        Optional.ofNullable(vacationBalance).ifPresent(balance ->
                predicates.add(cb.equal(employeeRoot.get("vacationBalance"), balance))
        );

        Optional.ofNullable(gender).ifPresent(g -> predicates.add(cb.equal(employeeRoot.get("gender"), g)));

        return predicates;
    }

}

package gov.iti.hr.filters.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface Filter {
    <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> jobRoot);
    Integer getOffset();
    Integer getLimit();
}

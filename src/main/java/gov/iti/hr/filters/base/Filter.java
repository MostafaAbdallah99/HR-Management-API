package gov.iti.hr.filters.base;

import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.BeanParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Filter {
    @BeanParam
    protected PaginationBean paginationBean;

    public abstract <T> List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> root);

    public Integer getOffset() {
        return paginationBean.getOffset();
    }

    public Integer getLimit() {
        return paginationBean.getLimit();
    }
}

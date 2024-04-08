package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.filters.interfaces.Filter;
import gov.iti.hr.persistence.entities.Employee;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, Integer> implements EmployeeRepository {
    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }

    @Override
    public List<Employee> findAll(EntityManager entityManager, Filter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        List<Predicate> predicates = filter.getPredicates(cb, root);
        query.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<Employee> jobTypedQuery = entityManager.createQuery(query);
        jobTypedQuery.setFirstResult(filter.getOffset());
        jobTypedQuery.setMaxResults(filter.getLimit());
        return jobTypedQuery.getResultList();
    }
}

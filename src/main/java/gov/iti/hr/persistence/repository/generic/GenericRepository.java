package gov.iti.hr.persistence.repository.generic;

import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> findById(ID id, EntityManager entityManager);

    Optional<T> findReferenceById(ID id, EntityManager entityManager);

    List<T> findAll(EntityManager entityManager, PaginationBean paginationBean);

    boolean save(T entity, EntityManager entityManager);

    boolean update(T entity, EntityManager entityManager);

    boolean delete(T entity, EntityManager entityManager);

    void deleteAll(EntityManager entityManager);

    Integer count(EntityManager entityManager);
}
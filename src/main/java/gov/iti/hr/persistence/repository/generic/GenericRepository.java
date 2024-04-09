package gov.iti.hr.persistence.repository.generic;

import gov.iti.hr.filters.base.Filter;
import gov.iti.hr.persistence.entities.base.BaseEntity;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T extends BaseEntity, ID> {
    Optional<T> findById(ID id, EntityManager entityManager);

    Optional<T> findReferenceById(ID id, EntityManager entityManager);

    boolean save(T entity, EntityManager entityManager);

    boolean update(T entity, EntityManager entityManager);

    boolean delete(T entity, EntityManager entityManager);

    void deleteAll(EntityManager entityManager);

    List<T> findAll(EntityManager entityManager, Filter filter, Class<T> entityClass);

    Integer count(EntityManager entityManager);
}
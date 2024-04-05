package gov.iti.hr.persistence.repository.generic;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface GenericRepository<T, ID> {
    T findById(ID id, EntityManager entityManager);

    T findReferenceById(ID id, EntityManager entityManager);

    List<T> findAll(EntityManager entityManager);

    boolean save(T entity, EntityManager entityManager);

    boolean update(T entity, EntityManager entityManager);

    boolean delete(T entity, EntityManager entityManager);
}
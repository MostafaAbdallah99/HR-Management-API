package gov.iti.hr.persistence.repository.generic;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> findById(ID id, EntityManager entityManager);

    Optional<T> findReferenceById(ID id, EntityManager entityManager);

    boolean save(T entity, EntityManager entityManager);

    boolean update(T entity, EntityManager entityManager);

    boolean delete(T entity, EntityManager entityManager);

    void deleteAll(EntityManager entityManager);

    Integer count(EntityManager entityManager);
}
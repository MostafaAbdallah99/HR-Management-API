package gov.iti.hr.persistence.repository.generic;


import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GenericRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {
    private final Class<T> entityClass;

    public GenericRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findById(ID id, EntityManager entityManager) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public Optional<T> findReferenceById(ID id, EntityManager entityManager) {
        return Optional.ofNullable(entityManager.getReference(entityClass, id));
    }

    @Override
    public List<T> findAll(EntityManager entityManager) {
        String query = String.format("SELECT t FROM %s t", entityClass.getSimpleName());
        return entityManager.createQuery(query, entityClass).getResultList();
    }

    @Override
    public boolean save(T entity, EntityManager entityManager) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(T entity, EntityManager entityManager) {
        try {
            entityManager.merge(entity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(T entity, EntityManager entityManager) {
        try {
            entityManager.remove(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

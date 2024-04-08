package gov.iti.hr.persistence.repository.generic;


import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public List<T> findAll(EntityManager entityManager, PaginationBean paginationBean) {
        String query = String.format("SELECT t FROM %s t", entityClass.getSimpleName());
        return entityManager.createQuery(query, entityClass)
                .setFirstResult(paginationBean.getOffset())
                .setMaxResults(paginationBean.getLimit())
                .getResultList();
    }

    @Override
    public boolean save(T entity, EntityManager entityManager) {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
            logger.error("Error saving entity {}", entity, e);
            return false;
        }
    }

    @Override
    public boolean update(T entity, EntityManager entityManager) {
        try {
            entityManager.merge(entity);
            return true;
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
            logger.error("Error updating entity {}", entity, e);
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

    @Override
    public void deleteAll(EntityManager entityManager) {
        entityManager.createQuery(String.format("DELETE FROM %s", entityClass.getSimpleName())).executeUpdate();
    }

    @Override
    public Integer count(EntityManager entityManager) {
        return entityManager.createQuery(String.format("SELECT COUNT(t) FROM %s t", entityClass.getSimpleName()), Long.class).getSingleResult().intValue();
    }

}

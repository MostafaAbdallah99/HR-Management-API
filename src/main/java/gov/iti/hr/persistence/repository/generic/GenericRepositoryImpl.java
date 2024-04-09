package gov.iti.hr.persistence.repository.generic;



import gov.iti.hr.filters.base.Filter;
import gov.iti.hr.persistence.entities.base.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GenericRepositoryImpl<T extends BaseEntity, ID extends Serializable> implements GenericRepository<T, ID> {
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
    public List<T> findAll(EntityManager entityManager, Filter filter, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        List<Predicate> predicates = filter.getPredicates(cb, root);
        query.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<T> jobTypedQuery = entityManager.createQuery(query);
        jobTypedQuery.setFirstResult(filter.getOffset());
        jobTypedQuery.setMaxResults(filter.getLimit());
        return jobTypedQuery.getResultList();
    }


    @Override
    public Integer count(EntityManager entityManager) {
        return entityManager.createQuery(String.format("SELECT COUNT(t) FROM %s t", entityClass.getSimpleName()), Long.class).getSingleResult().intValue();
    }

}

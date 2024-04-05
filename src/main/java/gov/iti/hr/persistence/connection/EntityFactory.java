package gov.iti.hr.persistence.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;


@Getter
public class EntityFactory {

    private final EntityManagerFactory entityManagerFactory;
    private static final String PERSISTENCE_UNIT_NAME = "api";

    private EntityFactory() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    private static class SingletonHelper {
        private static final EntityFactory INSTANCE = new EntityFactory();
    }

    public static EntityFactory getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

}
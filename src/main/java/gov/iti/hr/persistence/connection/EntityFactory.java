package gov.iti.hr.persistence.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;


@Getter
public class EntityFactory {
    private final EntityManagerFactory entityManagerFactory;

    private EntityFactory() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("api");
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
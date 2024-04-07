package gov.iti.hr.listener;

import gov.iti.hr.persistence.connection.EntityFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class ConfigListener implements ServletContextListener {

    private final EntityManagerFactory entityManagerFactory = EntityFactory.getInstance().getEntityManagerFactory();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        entityManagerFactory.close();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                Logger logger = LoggerFactory.getLogger(ConfigListener.class);
                logger.error("Error unregistering driver {}", driver, e);
            }
        }

    }
}

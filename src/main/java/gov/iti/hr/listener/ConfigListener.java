package gov.iti.hr.listener;

import gov.iti.hr.persistence.connection.EntityFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

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
                System.out.println("Error unregistering driver " + driver);
            }
        }

    }
}

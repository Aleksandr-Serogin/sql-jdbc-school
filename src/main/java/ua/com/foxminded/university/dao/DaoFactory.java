package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.util.ReadProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Date: Apr 27-2021 Class read properties file
 * and return connect to database
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public record DaoFactory(String nameFileProperties) implements IDaoFactory {

    /**
     * @return connect to database
     * @throws SQLException
     */
    @Override
    public Connection getConnect() throws SQLException {
        ReadProperties readProperties = new ReadProperties(nameFileProperties);
        String url = readProperties.getUrl();
        Properties props = new Properties();
        props.setProperty("user", readProperties.getUser());
        props.setProperty("password", readProperties.getPassword());
        return DriverManager.getConnection(url, props);
    }
}
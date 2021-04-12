package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.util.ReadProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory implements IDaoFactory {

    @Override
    public Connection getConnect(String  nameFileProperties) throws SQLException {
        ReadProperties readProperties = new ReadProperties (nameFileProperties);
        String url = readProperties.getUrl ();
        Properties props = new Properties();
        props.setProperty("user", readProperties.getUser ());
        props.setProperty("password", readProperties.getPassword ());
        System.out.println ( "Connected to the PostgreSQL server successfully." );
        return DriverManager.getConnection ( url, props );
    }
}
package ua.com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDaoFactory {

    Connection getConnect(String nameFileProperties) throws SQLException;
}
package ua.com.foxminded.university.util;

import ua.com.foxminded.university.dao.DaoFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Date: Apr 27-2021 Class run sql
 * script
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public class PostgreSqlRunSqlScript {

    /**
     * @param fileSql name of the run file
     * @param fileProperties name of the properties file
     */
    public void runSqlScript(String fileSql, String fileProperties) {
        DaoFactory daoFactory = new DaoFactory(fileProperties);
        Connection connection = null;
        Statement statement = null;
        ReadSqlFile readSqlFile = new ReadSqlFile();
        try {
            connection = daoFactory.getConnect();
            String sql = readSqlFile.readFile(fileSql);
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
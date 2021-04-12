package ua.com.foxminded.university.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import ua.com.foxminded.university.dao.DaoFactory;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSqlRunSqlScript {
//    CreateTableMySQL
    //DeletedTableMySQL
    public void runSqlScript(String fileSql, String fileProperties ) {
//        URL url = getClass ().getClassLoader ().getResource ( "init.bat" );
//        try {
//            Runtime.
//                    getRuntime().
//                    exec("cmd /c start "+url);
//        } catch (IOException e) {
//            e.printStackTrace ();
//        }


        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        Reader reader = null;
        try {
            connection = daoFactory.getConnect (fileProperties) ;
            ScriptRunner sr = new ScriptRunner(connection);
            URL url = getClass ().getClassLoader ().getResource ( fileSql );
            assert url != null;
            reader = new BufferedReader (new FileReader( url.getFile () ));
            sr.runScript ( reader );
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace ();
        }finally {
            try {
                assert reader != null;
                reader.close ();
                connection.close ();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace ();
            }
        }
    }
}
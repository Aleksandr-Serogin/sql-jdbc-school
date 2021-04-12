package ua.com.foxminded.university.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ReadSqlFile {

    public String readFile(String fileName){
        StringBuilder resultRead = new StringBuilder ();
        URL url = getClass ().getClassLoader ().getResource ( fileName );
        BufferedReader bufferedReader;
        String line;
        try {
            assert url != null;
            bufferedReader = new BufferedReader ( new FileReader ( url.getFile () ) );
            while ((line = bufferedReader.readLine ()) != null) {
                resultRead.append ( line );
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return resultRead.toString ();
    }
}

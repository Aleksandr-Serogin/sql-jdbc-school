package ua.com.foxminded.university.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ReadSqlFile {

    public  String readFile(String fileName)  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        assert inputStream != null;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            for (int length; (length = inputStream.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString(StandardCharsets.UTF_8);
    }
}
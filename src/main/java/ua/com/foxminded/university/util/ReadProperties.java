package ua.com.foxminded.university.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Date: Apr 27-2021 Class read file
 * with setting
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public class ReadProperties {

    private final String url;
    private final String user;
    private final String password;

    public ReadProperties(String fileName) {
        Properties prop = new Properties();
        InputStream streamReader = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            prop.load(streamReader);
        } catch (IOException e) {
            throw new NumberFormatException("File \"db.properties\" hav broken parameter.");
        }
        this.url = prop.getProperty("url");
        this.user = prop.getProperty("user");
        this.password = prop.getProperty("password");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
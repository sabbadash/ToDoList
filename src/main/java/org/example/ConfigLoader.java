package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The ConfigLoader class is responsible for loading configuration
 * settings for database connections.
 * The class reads database connection parameters such as URL, username, and password
 * from database.properties.
 */
public class ConfigLoader {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    /**
     * Constructor for ConfigLoader.
     * Loads database connection parameters from database.properties to an object.
     */
    public ConfigLoader() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("database.properties"));
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

}

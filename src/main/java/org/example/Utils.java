package org.example;

import java.sql.*;

public class Utils {
    private static final ConfigLoader config = new ConfigLoader();
    private static final String URL = config.getDbUrl();
    private static final String USERNAME = config.getDbUser();
    private static final String PASSWORD = config.getDbPassword();

    public static boolean isTableExists(String tableName) {
        boolean isExists = false;
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            String query = "SHOW TABLES LIKE '" + tableName + "'";
            isExists = statement.executeQuery(query).next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }

    public static void createTable(String creatingQuery) {
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            statement.execute(creatingQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

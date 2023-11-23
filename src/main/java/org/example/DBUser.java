package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBUser {
    private static final ConfigLoader config = new ConfigLoader();
    private static final String URL = config.getDbUrl();
    private static final String USERNAME = config.getDbUser();
    private static final String PASSWORD = config.getDbPassword();
    private static final String usersTableName = "users";

    private final String creatingTableQuery  = "CREATE TABLE `" + usersTableName + "` (" +
            "  `user_id` int NOT NULL AUTO_INCREMENT," +
            "  `username` varchar(45) NOT NULL," +
            "  `user_passwordhash` VARCHAR(128) NOT NULL," +
            "  PRIMARY KEY (`user_id`)," +
            "  UNIQUE KEY `user_id_UNIQUE` (`user_id`)," +
            "  UNIQUE KEY `username_UNIQUE` (`username`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;";

    public DBUser() {
        if(!Utils.isTableExists(usersTableName)) {
            Utils.createTable(creatingTableQuery);
        }
    }

    public boolean addUser(User user) {
        if(isUserExists(user)) {
            System.out.println("Username already exists :9");
            return false;
        }
        boolean isAdded = false;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sqlQuery = "INSERT INTO " + usersTableName + " (username, user_passwordhash) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                isAdded = preparedStatement.executeUpdate() > 0;
                if(isAdded)
                    user.setUser_id(getLastUserId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    public boolean removeUser(int user_id) {
        boolean isRemoved = false;

        ArrayList<User> userList = getAllUsers();
        if(userList.isEmpty()) {
            System.out.println("There are no users yet");
            return false;
        }

        String sqlQuery = "DELETE FROM " + usersTableName + " WHERE user_id = ?";
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ) {
            preparedStatement.setInt(1, user_id);
            isRemoved = preparedStatement.executeUpdate() == 1;             //if removed user returns 1, else 0
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return isRemoved;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> returnedUsers = new ArrayList<>();
        String query = "SELECT * FROM " + usersTableName;
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            while(resultSet.next()) {
                int  userId             = resultSet.getInt("user_id");
                String  username        = resultSet.getString("username");
                String  passwordHash    = resultSet.getString("user_passwordhash");

                User user = new User(username, passwordHash);
                user.setUser_id(userId);

                returnedUsers.add(user);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return returnedUsers;
    }

    private int getLastUserId() {
        int lastUserId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT user_id FROM " + usersTableName + " ORDER BY user_id DESC LIMIT 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        lastUserId = resultSet.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastUserId;
    }

    private boolean isUserExists(User user) {
        boolean isExists = false;
        String searchingUsername = user.getUsername();
        ArrayList<User> users = getAllUsers();

        for(User currentUser : users) {
            String currentUsername = currentUser.getUsername();
            if(currentUsername.equals(searchingUsername)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }

}

package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBList {

    private static final ConfigLoader config = new ConfigLoader();
    private static final String URL = config.getDbUrl();
    private static final String USERNAME = config.getDbUser();
    private static final String PASSWORD = config.getDbPassword();
    private static final String listsTableName = "todo_lists";

//    private final String creatingTableQuery = "CREATE TABLE `" + listsTableName + "` (" +
//            "  `list_id` int NOT NULL," +
//            "  `list_name` varchar(45) NOT NULL," +
//            "`fk_user_id` int NOT NULL," +
//            "  PRIMARY KEY (`list_id`)" +
//            "KEY `fk_user_id` (`fk_user_id`),\n" +
//            "CONSTRAINT `todo_lists_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`user_id`)" +
//            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;";

//    public DBList() {
//        if(!Utils.isTableExists("todo_lists")) {
//            Utils.createTable(creatingTableQuery);
//        }
//    }

    public static boolean createList(ToDoList list) {
        boolean isCreated = false;
        if(isListExists(list)) {
            System.out.println("You already have this list =)");
            return isCreated;
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sqlQuery = "INSERT INTO " + listsTableName + " (list_name, fk_user_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, list.getList_name());
                preparedStatement.setInt(2, list.getUser_id());

                isCreated = preparedStatement.executeUpdate() > 0;
                if(isCreated)
                    list.setList_id(getLastListId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isCreated;
    }

    public static ArrayList<ToDoList> getAllLists() {
        ArrayList<ToDoList> returnedLists = new ArrayList<>();
        String query = "SELECT * FROM " + listsTableName;
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            while(resultSet.next()) {
                int  list_id      = resultSet.getInt("list_id");
                String  list_name = resultSet.getString("list_name");
                int  user_id      = resultSet.getInt("fk_user_id");

                ToDoList list = new ToDoList(list_name, user_id);
                list.setList_id(list_id);

                returnedLists.add(list);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return returnedLists;
    }

    private static int getLastListId() {
        int lastListId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT list_id FROM " + listsTableName + " ORDER BY list_id DESC LIMIT 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        lastListId = resultSet.getInt("list_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastListId;
    }

    public static boolean isListExists(ToDoList list) {
        boolean isExists = false;
        String list_name = list.getList_name();
        int user_id = list.getUser_id();
        ArrayList<ToDoList> allLists = getAllLists();

        for(ToDoList currentList : allLists) {
            String currentListName = currentList.getList_name();
            int currentUserId = currentList.getUser_id();

            if(currentListName.equals(list_name) && currentUserId == user_id) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }

}

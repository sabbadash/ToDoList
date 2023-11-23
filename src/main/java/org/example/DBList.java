package org.example;

import java.sql.*;

public class DBList {

    private static final String URL = "jdbc:mysql://localhost:3306/todolist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String listsTableName = "todo_lists";

    private final String creatingTableQuery = "CREATE TABLE `" + listsTableName + "` (" +
            "  `list_id` int NOT NULL," +
            "  `user_id` varchar(45) NOT NULL," +
            "  `list_name` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`list_id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;";

    public DBList() {
        if(!Utils.isTableExists("todo_lists")) {
            Utils.createTable(creatingTableQuery);
        }
    }

}

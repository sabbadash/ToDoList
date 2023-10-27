package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/todolist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection  connection;

    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * method deletes task from db with given id
     * @param deletedId id we are looking for to delete
     * @return returns true if task was deleted, if not false
     */
    public boolean deleteTask(int deletedId) {
        boolean isDeleted = false;
        String query = "DELETE FROM task WHERE task_id = ?";          //preparedstatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, deletedId);            //setting task_id parameter
            isDeleted = preparedStatement.executeUpdate() == 1;             //if method deleted task returns 1, else 0

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return isDeleted;
    }

    /**
     * Connection to db in constructor
     * @return ArrayList<Task> with all tasks in table "task"
     */
    public ArrayList<Task> getAllTasks() {

        ArrayList<Task> returnedTasks = new ArrayList<Task>();
        String query = "SELECT * FROM task";        //setting query

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)                 //receiving resultSet of table(model)
        ){

            while(resultSet.next()) {
                int     id      = resultSet.getInt("task_id");
                String  name    = resultSet.getString("task_name");
                String  note    = resultSet.getString("task_note");

                Task task = new Task(id, name, note);

                returnedTasks.add(task);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return returnedTasks;
    }


    /**
     * Method returns the task with id given
     * @param searchedTaskId is the id of task which method is looking for
     * @return Task task
     */
    public Task getTask(int searchedTaskId) {

        Task returnedTask = null;
        String query = "SELECT * FROM task WHERE task_id = ?";          //preparedstatement

        try (
                Statement statement = this.getConnection().createStatement();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){

            preparedStatement.setInt(1, searchedTaskId);            //setting task_id parameter

            try (ResultSet resultSet = preparedStatement.executeQuery()) {      //executing query
                if (resultSet.next()) { // If a record exists
                    int     id      = resultSet.getInt("task_id");
                    String  name    = resultSet.getString("task_name");
                    String  note    = resultSet.getString("task_note");

                    returnedTask = new Task(id, name, note);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return returnedTask;
    }

    public void closeConnetion() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

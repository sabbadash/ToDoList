package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * идеи и вопросы:
 * 1) по логике методы занимающееся работой с бд, должны находиться в классе DBWorker.
 *
 * переносить все методы в ToDoList глупо, а если создать в ToDoList свою функцию, которая будет создавать свой
 * экземпляр класса DBWorker и вызывать этот метод у него, но из функции getAllTasks в ToDoList?
 *      чтобы была возможность обратиться к экземпляру тудулиста и у него попросить все задачи, а не каждый раз
 *      обращаться DBWorker.
 *      глупо ли? или можно сделать как-то иначе?
 *      тоже самое и с другими методами deleteTask(), getTask() и addTask(), можно ведь вызывать у экземпляра
 *      тудулиста, а он обратится к бд
 */
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


    //USERDB - ID
    public ArrayList<String> getAllLists() {
        ArrayList<String> returnedLists = new ArrayList<>();
        try {
            DatabaseMetaData dbm = connection.getMetaData();

            try (ResultSet tables = dbm.getTables(null, null, "%", new String[] { "TABLE" })) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    if(!tableName.equals("sys_config")) {
                        returnedLists.add(tableName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedLists;
    }


    //USERDB
    //сразу идейка, функцию можно занести в конструктор ToDoList и сразу создавать таблицу при создании экземпляра
    /**
     * добавить проверку на наличие таблицы в бд, можно вынести в отдельный метод
     * @param toDoList
     */
    public void createList(ToDoList toDoList) {

        String listName = toDoList.getListName();

        if (!listName.matches("[a-zA-Z_]+")) {
            throw new IllegalArgumentException("Invalid table name");
        }
        String query = "CREATE TABLE " + listName + " (" +
                "`task_id` int NOT NULL AUTO_INCREMENT," +
                "`task_name` varchar(45) NOT NULL," +
                "`task_note` varchar(45) DEFAULT NULL," +
                "PRIMARY KEY (`task_id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1";

        try (Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //DBLIST
    /**
     * Method adds new Task to the table
     * @param task is task wished to add to the list
     * @return true is task was successfully added
     */
    public void addTask(Task task) {        //boolean
        String taskName = task.getTaskName();
        String taskNote = task.getTaskNote();
        boolean isAdded = false;

        String query = "INSERT INTO " + task.getListName() + " (task_name, task_note) values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, taskName);
            preparedStatement.setString(2, taskNote);
//             isAdded = preparedStatement.executeUpdate() == 1;
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return isAdded;
    }


    //DBLIST/DBTASK
    /**
     * Method deletes task from table with given id
     * @param deletedId id method is looking for to delete
     * @return true if task was deleted, otherwise false
     */
    public boolean deleteTask(int deletedId, String toDoList) {
        boolean isDeleted = false;
        ArrayList<Task> taskList = getAllTasks(toDoList);
        if (taskList.isEmpty() || deletedId <= 0 || deletedId > taskList.size()) {
            System.out.println("Invalid index or empty task list");
            return false;
        }

        String query = "DELETE FROM " + toDoList + " WHERE task_id = ?";          //preparedstatement
        String resetingQuery = "ALTER TABLE " + toDoList + " AUTO_INCREMENT = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int actuallyDeletedId = taskList.get(--deletedId).getTaskId();
            preparedStatement.setInt(1, actuallyDeletedId);            //setting task_id parameter
            isDeleted = preparedStatement.executeUpdate() == 1;             //if method deleted task returns 1, else 0

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return isDeleted;
    }

    //DBLIST
    /**
     * Connection to db in constructor
     * @return ArrayList<Task> with all tasks in table
     */
    public ArrayList<Task> getAllTasks(String listName) {

        ArrayList<Task> returnedTasks = new ArrayList<Task>();
        String query = "SELECT * FROM " + listName;        //setting query

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


    ////DBLIST/DBTASK
    public boolean renameTask(int id, String toDoList, String newName) {
        String query = "UPDATE " + toDoList + " SET task_name = ? WHERE task_id = ?";
        ArrayList<Task> taskList = getAllTasks(toDoList);
        if (taskList.isEmpty() || id <= 0 || id > taskList.size()) {
            System.out.println("Invalid index or empty task list");
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, taskList.get(id - 1).getTaskId()); // convert 1-based to 0-based
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }



    //DBLIST
    /**
     * Method returns the task with id given
     * @param searchedTaskId is the id of task which method is looking for
     * @return Task task is
     */
    public Task getTask(int searchedTaskId, ToDoList toDoList) {

        Task returnedTask = null;
        String query = "SELECT * FROM " + toDoList.getListName() + " WHERE task_id = ?";          //preparedstatement

        try (
                Statement statement = connection.createStatement();
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
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

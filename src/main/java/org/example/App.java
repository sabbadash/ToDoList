package org.example;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DBWorker db = new DBWorker();

//        db.deleteTask(19);
//        ArrayList<Task> allTasks = db.getAllTasks();
//        for(int i = 0; i < allTasks.size(); i++) {
//            System.out.println(allTasks.get(i));
//        }
//        System.out.println("----");
//        System.out.println(db.getTask(1));
//        db.closeConnetion();
        Task task = new Task();
        task.setTaskName("купить хлеб");
        task.setTaskNote("белый");
        db.addTask(task);

    }
}
package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

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
//        Task task = new Task();
//        task.setTaskName("купить хлеб");
//        task.setTaskNote("белый");
//        db.addTask(task);
//
//        ToDoList list = new ToDoList("defaultechman");
//        db.createList(list);
//        Task task = new Task("new dog22", "blackwe", "defaultechman");
//        db.addTask(task);
//        db.getAllLists();


        while(true) {
            String listName;

            Scanner console = new Scanner(System.in);
            for(int i = 0; i < 7; i++) {
                System.out.println("");
            }

            ArrayList<String> allLists = db.getAllLists();
            for(int i = 0; i < allLists.size(); i++) {
                System.out.println(i + ") " + allLists.get(i));
            }
            System.out.print("Enter [exit] to exit\nPlease enter toDoList name: ");
            listName = console.nextLine();
            if(allLists.contains(listName)) {
                System.out.println("\n" + "Good choice! Your tasks: ");
                ArrayList<Task> tasks = db.getAllTasks(listName);
                for(int j = 0; j < tasks.size(); j++) {
                    System.out.println(j + ") " + tasks.get(j));
                }
                System.out.println("\t\t\t+ [add new task]" + "\n\t\t\t- [delete existing task]");
                String doing = console.nextLine();
                if(doing.equals("+")) {
                    System.out.print("\n\nType your task: ");
                    String taskName = console.nextLine();
                    System.out.print("Type notes(or nothing): ");
                    String taskNote = console.nextLine();
                    Task task = new Task(taskName, taskNote, listName);
                    db.addTask(task);
                    continue;
                } else if(doing.equals("-")) {
                    System.out.print("\n\nType task id to delete: ");
                    int ans = console.nextInt();
                    db.deleteTask(ans, listName);
                    continue;
                }

            } else if(listName.equals("exit")) {
                break;
            }else {
                System.out.println("\n\nThis list does not exist. Sorry XD");
                System.out.println("Would you like to create? y/n");
                String answer = console.nextLine();
                if(answer.equals("y")) {
                    ToDoList list = new ToDoList(listName);
                    db.createList(list);
                    System.out.println("Thank you! List created ;)");
                    continue;
                }
            }

        }


        db.closeConnetion();
    }
}
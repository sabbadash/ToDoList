package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class UIManager {
    private DBWorker db;
    public UIManager() {
        db = new DBWorker();
    }

    public void printListMenu() {

        Scanner console = new Scanner(System.in);
        ArrayList<String> lists = db.getAllLists();

        System.out.println("\nYour lists:");
        for(int i = 0; i < lists.size(); i++) {
            System.out.println("\t" + lists.get(i));
        }
        System.out.print("Enter [exit] to exit\n\nPlease enter toDoList name: ");
        String listName = console.nextLine();

        if(db.getAllLists().contains(listName)) {
            printAllTasks(listName);
        } else if(listName.equals("exit")) {
            System.out.println("Thank you for using application!");
            db.closeConnetion();
            System.exit(0);
        }else {
            System.out.println(
                    "\n\nThis list does not exist. Sorry XD" +
                    "\nWould you like to create? y/n");
            String answer = console.nextLine();
            if(answer.equals("y")) {
                ToDoList list = new ToDoList(listName);
                db.createList(list);
                System.out.println("Thank you! List created ;)");
            }
            printListMenu();
        }
    }

    public void printAllTasks(String listName) {
        ArrayList<Task> tasks = db.getAllTasks(listName);
        System.out.println("\nYour tasks (" + listName + "):");
        for(int j = 0; j < tasks.size(); j++) {
            System.out.println("\t" + (j + 1) + ") " + tasks.get(j));
        }
        getUserAction(listName);
    }

    public void getUserAction(String toDoList) {
        Scanner console = new Scanner(System.in);
        System.out.println("\t\t\t\t\t+ [add new task]"
                + "\n\t\t\t\t\t* [manage existing task]"
                + "\n\t\t\t\t\t.. [go back to lists]");
        String input = console.nextLine();

        if (input.equals("+")) {
            System.out.print("\n\nEnter task name: ");
            String taskName = console.nextLine();
            System.out.print("Type notes(or nothing): ");
            String taskNote = console.nextLine();

            Task task = new Task(taskName, taskNote, toDoList);
            db.addTask(task);
            printAllTasks(toDoList);
        } else if(input.equals("*")) {
            System.out.print("\nType task id: ");
            int taskId = console.nextInt();
            manageTaskMenu(taskId, toDoList);
        } else if(input.equals("..")) {
            printListMenu();
        }

    }

    public void manageTaskMenu(int taskId, String toDoList) {
        Scanner console = new Scanner(System.in);
        System.out.println("\t\t\t\t\tr [rename task " + taskId + "]"
                + "\n\t\t\t\t\td [delete task " + taskId + "]"
                + "\n\t\t\t\t\t.. [go back to tasks]");
        String action = console.nextLine();
        if(action.equals("r")) {
            System.out.print("Type new name for task(" + taskId + "):");
            String newName = console.nextLine();
            db.renameTask(taskId, toDoList, newName);
            printAllTasks(toDoList);
        } else if(action.equals("d")) {
            //deleting
            db.deleteTask(taskId, toDoList);
            printAllTasks(toDoList);
        } else if(action.equals("..")) {
            printAllTasks(toDoList);
        }
    }
}

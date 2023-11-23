package org.example;


/*
    DONE:
        TODO: [x] Implement storing db data in .properties file

    TODO: [ ] Implementation of locking todo list with password (*) PIN

    TODO: [ ] Implementation of task priority

    TODO: [ ] Implementation of task deadline (LocalDateTime)
          TODO: [ ] show time before deadline

    TODO: [ ] Completed Tasks List: Ability to view a list of completed tasks.

    TODO: [ ] Task Filtering: Filter tasks by
          TODO: [ ] status (for example, completed, active)
          TODO: [ ] priority
          TODO: [ ] deadline.

     TODO: [ ] Statistics and Reports: Generating simple reports on the work done for the week/month, statistics on completed tasks.

     TODO: [ ] Export Data: Ability to export a list of tasks to a file (eg CSV).
 */


import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        //DBWorker db = new DBWorker();

        //uiManager UI = new uiManager();
        User user = new User("logins5", "password");
        User user2 = new User("logins5w", "password");
//        User user3 = new User("logeins5seedsddff444ddrrryy", "password");
        DBUser dbu = new DBUser();
        dbu.addUser(user);
        dbu.addUser(user2);
//        dbu.addUser(user);
        ArrayList<User> users = dbu.getAllUsers();
        for(User user23 : users) {
            System.out.println(user23.getUsername());
        }
        dbu.removeUser(1);
        System.out.println("00000---------------000000000000000");
        ArrayList<User> users1 = dbu.getAllUsers();
        for(User user23 : users1) {
            System.out.println(user23.getUsername());
        }
        //UI.printListMenu();
    }
}
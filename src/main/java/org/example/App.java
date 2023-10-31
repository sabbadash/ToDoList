package org.example;

public class App {
    public static void main(String[] args) {
        DBWorker db = new DBWorker();

        uiManager UI = new uiManager();

        UI.printListMenu();
    }
}
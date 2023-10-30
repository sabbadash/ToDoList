package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        DBWorker db = new DBWorker();

        UIManager UI = new UIManager();

        UI.printListMenu();

    }

}
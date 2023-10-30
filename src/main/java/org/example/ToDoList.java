package org.example;

public class ToDoList {
    private String listName;

    public ToDoList(String listName) {
        this.listName = listName;
    }

    //сразу идейка, функцию можно занести в конструктор и сразу создавать таблицу при создании экземпляра
//    public void createList() {
//
//    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}

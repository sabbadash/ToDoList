package org.example;

public class ToDoList {
    private int list_id;
    private String list_name;
    private int user_id;

    public ToDoList(String list_name, int user_id) {
        this.list_name = list_name;
        this.user_id = user_id;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getList_name() {
        return list_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

package org.example;

public class Task {

    private int taskID;
    private String taskName;
    private String taskNote;

    private String listName;

    public Task(int id, String taskName, String taskNote, String listName) {
        this.taskID = id;
        this.taskName = taskName;
        this.taskNote = taskNote;
        this.listName = listName;
    }
    public Task(String taskName, String taskNote, String listName) {
        this.taskName = taskName;
        this.taskNote = taskNote;
        this.listName = listName;
    }
    public Task(int id, String taskName, String taskNote) {
        this.taskID = id;
        this.taskName = taskName;
        this.taskNote = taskNote;
    }
    public Task(String taskName, String taskNote) {
        this.taskName = taskName;
        this.taskNote = taskNote;
    }

    public Task(String taskName) {
        this(taskName, null);
    }

    public  Task() {
        this(null, null);
    }

    @Override
    public String toString() {
        return /*this.getTaskID() + " " + */this.getTaskName() + " " + this.getTaskNote();
    }
    //System.out.println(task);     -- ДА!
    //System.out.println(task.toString()); --- НЕ НУЖНО!
    //при написании имени класса у него автоматичеки вызывается метод toString()

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}

package org.example;

public class Task {

    private int taskId;
    private String taskName;
    private String taskNote;

    private String listName;

    public Task(int id, String taskName, String taskNote, String listName) {
        this.taskId = id;
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
        this.taskId = id;
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
        return this.getTaskName() + " " + this.getTaskNote();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

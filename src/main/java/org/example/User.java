package org.example;

public class User {
    private int user_id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = PasswordHasher.hashPassword(password);
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

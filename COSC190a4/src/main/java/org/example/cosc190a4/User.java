package org.example.cosc190a4;

import javafx.scene.paint.Color;

public class User {

    private String username;
    private String handle;
    private String textColor;
    private String email;


    public User() {
    }

    public User(String username, String handle, String textColor, String email) {
        this.username = username;
        this.handle = handle;
        this.textColor = textColor;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getHandle() {
        return handle;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", handle='" + handle + '\'' +
                ", textColor=" + textColor +
                ", email='" + email + '\'' +
                '}';
    }
}

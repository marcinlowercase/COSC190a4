package org.example.cosc190a4;

import javafx.scene.paint.Color;

public class User {

    private String username;
    private String handle;
    private Color textColor;
    private String email;


    public User() {
    }

    public User(String username, String handle, Color textColor, String email) {
        this.username = username;
        this.handle = handle;
        this.textColor = textColor;
        this.email = email;
    }

    public User(String username, String handle, String red, String green, String blue, String email) {
        this.username = username;
        this.handle = handle;
        this.textColor = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getHandle() {
        return handle;
    }

//    public Color getTextColor() {
//        return textColor;
//    }

    public Color getTextColor() {
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

    public void setTextColor(String red, String green, String blue) {
        this.textColor = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {

        return "{ \"username\": \""
                + this.username
                + "\", \"handle\": \""
                + handle
                + "\", \"red\": \""
                + (int) (textColor.getRed() * 255 )
                + "\", \"green\": \""
                + (int) (textColor.getGreen() * 255 )
                + "\", \"blue\": \""
                + (int) (textColor.getBlue() * 255 )
                + "\", \"email\": \""
                + email
                + "\" }";

    }
}

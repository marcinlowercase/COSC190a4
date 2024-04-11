package org.example.cosc190a4;

import javafx.scene.paint.Color;

public class User {

    private String username;
    private String handle;

    private int red;
    private int green;
    private int blue;

    private String email;


    public User() {
    }

//    public User(String username, String handle, Color textColor, String email) {
//        this.username = username;
//        this.handle = handle;
//        this.textColor = textColor;
//        this.email = email;
//    }

    public User(String username, String handle, String red, String green, String blue, String email) {
        this.username = username;
        this.handle = handle;
        this.red = Integer.parseInt(red);
        this.green = Integer.parseInt(green);
        this.blue = Integer.parseInt(blue);
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


    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public Color returnColor() {
        return Color.rgb(red, green, blue);
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
                + red
                + "\", \"green\": \""
                + green
                + "\", \"blue\": \""
                + blue
                + "\", \"email\": \""
                + email
                + "\" }";

    }
}

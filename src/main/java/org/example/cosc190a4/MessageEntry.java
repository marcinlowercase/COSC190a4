package org.example.cosc190a4;

import javafx.scene.paint.Color;

import java.util.Date;

public class MessageEntry {
    private int id;
    private Date timeStamp;
    private String userHandle;
    private String userEmail;

    private String chatMessage;
    private String userIP;
    private int userPort;
    private Color userPreferredColor;

    public MessageEntry(Date timeStamp, String userHandle, String userEmail, String chatMessage, Color userPreferredColor) {
        this.timeStamp = timeStamp;
        this.userHandle = userHandle;
        this.userEmail = userEmail;
        this.chatMessage = chatMessage;
        this.userPreferredColor = userPreferredColor;
    }



    public int getId() {
        return id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getUserIP() {
        return userIP;
    }

    public int getUserPort() {
        return userPort;
    }

    public Color getUserPreferredColor() {
        return userPreferredColor;
    }

    public void setUserPreferredColor(Color userPreferredColor) {
        this.userPreferredColor = userPreferredColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }

}

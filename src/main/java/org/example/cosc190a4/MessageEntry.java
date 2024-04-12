package org.example.cosc190a4;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Date;

public class MessageEntry implements Serializable {
    private int id;
    private Date timeStamp;
    private String userHandle;
    private String userEmail;

    private String chatMessage;
    private String userIP;
    private int userPort;
//    private Color userPreferredColor;
    private int userPreferredColorR;
    private int userPreferredColorG;
    private int userPreferredColorB;


    public MessageEntry( Date timeStamp, String userHandle, String userEmail, String chatMessage, int userPreferredColorR, int userPreferredColorG, int userPreferredColorB) {
        this.timeStamp = timeStamp;
        this.userHandle = userHandle;
        this.userEmail = userEmail;
        this.chatMessage = chatMessage;
        this.userPreferredColorR = userPreferredColorR;
        this.userPreferredColorG = userPreferredColorG;
        this.userPreferredColorB = userPreferredColorB;
//        this.userPreferredColor = Color.rgb(userPreferredColorR, userPreferredColorG, userPreferredColorB);
    }

//    public MessageEntry(Date timeStamp, String userHandle, String userEmail, String chatMessage, Color userPreferredColor) {
//        this.timeStamp = timeStamp;
//        this.userHandle = userHandle;
//        this.userEmail = userEmail;
//        this.chatMessage = chatMessage;
//        this.userPreferredColor = userPreferredColor;
//    }



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

//    public Color getUserPreferredColor() {
//        return userPreferredColor;
//    }

//    public void setUserPreferredColor(Color userPreferredColor) {
//        this.userPreferredColor = userPreferredColor;
//    }


    public int getUserPreferredColorR() {
        return userPreferredColorR;
    }

    public void setUserPreferredColorR(int userPreferredColorR) {
        this.userPreferredColorR = userPreferredColorR;
    }

    public int getUserPreferredColorG() {
        return userPreferredColorG;
    }

    public void setUserPreferredColorG(int userPreferredColorG) {
        this.userPreferredColorG = userPreferredColorG;
    }

    public int getUserPreferredColorB() {
        return userPreferredColorB;
    }

    public void setUserPreferredColorB(int userPreferredColorB) {
        this.userPreferredColorB = userPreferredColorB;
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

    @Override
    public String toString() {
        return "MessageEntry{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", userHandle='" + userHandle + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", chatMessage='" + chatMessage + '\'' +
                ", userIP='" + userIP + '\'' +
                ", userPort=" + userPort +
                ", userPreferredColorR=" + userPreferredColorR +
                ", userPreferredColorG=" + userPreferredColorG +
                ", userPreferredColorB=" + userPreferredColorB +
                '}';
    }
}

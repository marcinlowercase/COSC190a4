package org.example.cosc190a4;

import java.util.Date;

public class MessageEntry {
    private int id;
    private Date timeStamp;
    private String userHandle;
    private String userEmail;

    private String chatMessage;
    private String userIP;
    private int userPort;
    private String userPreferredColor;

    public MessageEntry(int id, Date timeStamp, String userHandle, String userEmail, String chatMessage, String userIP, int userPort, String userPreferredColor) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.userHandle = userHandle;
        this.userEmail = userEmail;
        this.chatMessage = chatMessage;
        this.userIP = userIP;
        this.userPort = userPort;
        this.userPreferredColor = userPreferredColor;
    }
}

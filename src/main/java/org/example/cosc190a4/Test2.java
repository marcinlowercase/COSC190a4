package org.example.cosc190a4;

import java.net.InetAddress;

public class Test2 {
    public static void main(String[] args) throws Exception {
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        System.out.println("Local IP Address: " + ipAddress);
    }

}

package org.example.cosc190a4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Operations extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Button testButton = new Button("Click me!");
//        testButton.setOnAction(actionEvent -> {
//            System.out.println("Clicked");
//        });
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(testButton);
//
//
//
//
//
//        Scene scene = new Scene(borderPane, 400, 400);
//
//
//        stage.setScene(scene);
//
//        stage.show();
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server started.Serving at port: " + 9999);

            while (true){

                Socket connectedClient = serverSocket.accept();

                DataInputStream inputStreamFromClient = new DataInputStream((connectedClient.getInputStream()));

                String messageFromClient = inputStreamFromClient.readUTF();

                System.out.println(messageFromClient);



                // Respond to client

                DataOutputStream outputStreamToClient = new DataOutputStream(connectedClient.getOutputStream());

                String messageToClient = "Hello" + new Date();
                outputStreamToClient.writeUTF(messageToClient);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

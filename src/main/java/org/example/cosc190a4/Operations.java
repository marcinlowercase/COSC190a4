package org.example.cosc190a4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Operations extends Application {


    StringBuilder logString = new StringBuilder();
    TextField logField = new TextField();
    HBox logBox = new HBox(logField);
    Button showLog = new Button("Show log");
    Button clearLog = new Button("Clear log");
    Button exit = new Button("Exit");
    HBox buttonHBox = new HBox(showLog, clearLog, exit);

    @Override
    public void start(Stage stage) throws Exception {



        // Three buttons
        logBox.setAlignment(Pos.CENTER);
        logBox.setPadding(new Insets(10, 10, 10, 10));
        logBox.setSpacing(10);




        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setBottom(buttonHBox);
        borderPane.setTop(logBox);


        Scene scene = new Scene(borderPane, 800, 400);



        // Exit Button
        exit.setOnAction(event -> turnOffServer());



        stage.setScene(scene);


        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setResizable(false);
//        stage.widthProperty().bind(scene.widthProperty().multiply(scene.heightProperty().divide(stage.heightProperty())));



        // log box size

        logField.setPrefWidth(stage.getWidth() * 0.7);
        logField.setPrefHeight(stage.getHeight() * 0.7);
        logField.prefWidthProperty().bind(stage.widthProperty().multiply(0.7));
        logField.prefHeightProperty().bind(stage.heightProperty().multiply(0.7));

        // Button bar size
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(20);

//        buttonHBox.setPrefHeight(stage.getHeight() * 0.2);
//        buttonHBox.prefHeightProperty().bind(stage.heightProperty().multiply(0.2));

        //Start server

        Thread startServerThread = new Thread(this::startServer);
        startServerThread.start();



//        try {
//            ServerSocket serverSocket = new ServerSocket(9999);
//            System.out.println("Server started.Serving at port: " + 9999);
//
//            while (true){
//
//                Socket connectedClient = serverSocket.accept();
//
//                DataInputStream inputStreamFromClient = new DataInputStream((connectedClient.getInputStream()));
//
//                String messageFromClient = inputStreamFromClient.readUTF();
//
//                System.out.println(messageFromClient);
//
//
//
//                // Respond to client
//
//                DataOutputStream outputStreamToClient = new DataOutputStream(connectedClient.getOutputStream());
//
//                String messageToClient = "Hello" + new Date();
//                outputStreamToClient.writeUTF(messageToClient);
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    private void turnOffServer() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText("Exit Confirmation");
        exitAlert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = exitAlert.showAndWait();

        if (result.get() == ButtonType.OK) Platform.exit();
        else exitAlert.close();
    }

    private void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(9999)){
            System.out.println("Server start in socket: " + 9999);

            while(true){
                Socket socket = serverSocket.accept();

//                System.out.println("Accepted connection from: " + socket.getInetAddress().getHostAddress());

                DataInputStream inputStreamFromClient = new DataInputStream(socket.getInputStream());


//                logString.append(inputStreamFromClient.readUTF());
//                logString.append("\n");
//                logField.setText(logString.toString());
                logField.appendText(inputStreamFromClient.readUTF() + "\n");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
